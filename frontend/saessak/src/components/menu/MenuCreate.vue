<template>
	<div class="view-frame">
		<div class="table-box">
			<div class="flex justify-between">
				<div class="flex items-center m-2">
					<div class="mr-4 text-lg text-black font-bold">날짜</div>
					<div>
						<VDatePicker
							:select-attribute="selectAttribute"
							:disabled-dates="disabledDates"
							v-model="menuDate"
						>
							<template #default="{ inputValue, inputEvents }">
								<div class="relative max-w-sm">
									<div
										class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
									>
										<svg
											class="w-4 h-4 text-gray-900 dark:text-gray-400"
											aria-hidden="true"
											xmlns="http://www.w3.org/2000/svg"
											fill="currentColor"
											viewBox="0 0 20 20"
										>
											<path
												d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"
											/>
										</svg>
									</div>
									<input
										:value="inputValue"
										v-on="inputEvents"
										class="datepicker-input"
									/>
								</div>
							</template>
						</VDatePicker>
					</div>
				</div>
				<button @click="createMenuList(menuList)" class="btn my-2 mx-2">
					등록
				</button>
			</div>
			<table class="table">
				<thead class="table-head">
					<tr>
						<th scope="col" class="col">분류</th>
						<th scope="col" class="col-food">음식</th>
						<th scope="col" class="col-allergy">알레르기 식품</th>
						<th scope="col" class="w-1/12"></th>
					</tr>
				</thead>
				<tbody>
					<tr class="one-row" v-for="(menu, index) in menuList" :key="index">
						<td class="col">
							<select
								id="menu-type"
								class="selection-input w-36"
								:value="menu.menuType"
								@change="
									menu.menuType = $event.target.value;
									menu.emptyMenuType = false;
								"
								:class="{
									'border-2 border-red-500': menu.emptyMenuType,
									shake: menu.shakeMenuType,
								}"
								required
							>
								<option value="점심">점심</option>
								<option value="간식">간식</option>
							</select>
						</td>
						<td class="col-food">
							<input
								type="text"
								class="input w-56"
								v-model="menu.foodName"
								@input="menu.emptyFoodName = false"
								:class="{
									'border-2 border-red-500': menu.emptyFoodName,
									shake: menu.shakeFoodName,
								}"
								required
							/>
						</td>
						<td class="pl-3">
							<template v-for="allergy in allergyList" :key="allergy.allergyId">
								<span class="checkbox-frame">
									<input
										type="checkbox"
										class="checkbox"
										:value="allergy.allergyId"
										v-model="menu.foodAllergy"
										:id="`${allergy.allergyId}${index}`"
									/>
									<label
										class="checkbox-label"
										:for="`${allergy.allergyId}${index}`"
										>{{ allergy.allergyName }}</label
									>
								</span>
							</template>
						</td>
						<td class="col-btn">
							<button @click="deleteOneRow(index)">삭제</button>
						</td>
					</tr>
					<tr class="one-row h-2">
						<button
							@click="addOneRow"
							class="text-center text-dark-navy text-lg font-bold m-7"
						>
							+ 메뉴 추가
						</button>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useMenuStore } from '@/store/menu';
import { format } from 'date-fns';
import { useRouter } from 'vue-router';
import { createMenu } from '@/api/menu';

const selectAttribute = ref({ highlight: 'blue' });

const disabledDates = ref([
	{
		repeat: {
			weekdays: [1, 7],
		},
	},
]);

const menuDate = ref(new Date());

watch(menuDate, newVal => {
	const newValue = new Date(newVal);
	const year = newValue.getFullYear();
	const month = ('0' + (newValue.getMonth() + 1)).slice(-2);
	const day = ('0' + newValue.getDate()).slice(-2);
	const newDate = `${year}-${month}-${day}`;

	for (let menu of menuList.value) {
		menu.menuDate = newDate;
	}
});

const store = useMenuStore();

const allergyList = store.allergyList;

const menuList = ref([
	{
		menuDate: format(menuDate.value, 'yyyy-MM-dd').toString(),
		menuType: '',
		foodName: '',
		foodAllergy: [],
	},
]);

// 행 추가 함수
const addOneRow = () => {
	menuList.value = [
		...menuList.value,
		{
			menuDate: format(menuDate.value, 'yyyy-MM-dd').toString(),
			menuType: '',
			foodName: '',
			foodAllergy: [],
		},
	];
};

// 행 삭제 함수
const deleteOneRow = index => {
	menuList.value.splice(index, 1);
};

const router = useRouter();

// 널값처리
const checkEmptyFields = () => {
	let hasEmptyFields = false;
	for (let menu of menuList.value) {
		if (!menu.foodName.trim()) {
			menu.emptyFoodName = true;
			menu.shakeFoodName = true;
			hasEmptyFields = true;
			setTimeout(() => {
				menu.shakeFoodName = false;
			}, 1000);
		}
		if (!menu.menuType.trim()) {
			menu.emptyMenuType = true;
			menu.shakeMenuType = true;
			hasEmptyFields = true;
			setTimeout(() => {
				menu.shakeMenuType = false;
			}, 1000);
		}
	}
	return hasEmptyFields;
};

const createMenuList = async menuList => {
	if (checkEmptyFields()) {
		return;
	}
	// proxy객체는 for...in구문, join 직접 사용 불가
	menuList.forEach(menu => {
		menu.foodAllergy = menu.foodAllergy
			// 알레르기 식품 랜덤으로 선택했어도 오름차순으로 정렬되게
			.sort((a, b) => {
				return a - b;
			})
			// string으로
			.map(allergy => allergy.toString())
			// '/'로 구분하여 배열 string으로 변환
			.join('/');
	});
	await createMenu(menuList);
	// await store.getTeacherWeeklyMenu();
	router.push({ name: 'MenuList' });
};
</script>

<style scoped>
.table-box {
	@apply relative overflow-x-auto min-h-screen m-3;
}
.table {
	@apply w-full mt-3 text-base text-left rtl:text-right text-gray-500 dark:text-gray-400;
}
.table-head {
	@apply text-gray-700 bg-nav-navy bg-opacity-30 dark:bg-gray-700 dark:text-gray-400;
}
.col {
	@apply p-3 w-[14%];
}
.col-food {
	@apply p-3 w-[21%];
}
.col-allergy {
	@apply p-3 w-[60%];
}
.col-btn {
	@apply w-[5%] text-dark-navy font-bold;
}
.one-row {
	@apply bg-white border-b h-20;
}
.checkbox-frame {
	@apply pr-3 py-1 inline-block text-black;
}
.checkbox {
	@apply px-1;
}
.checkbox-label {
	@apply px-1 text-base;
}
@keyframes shake {
	0% {
		transform: translateX(0px);
	}
	25% {
		transform: translateX(-2px);
	}
	50% {
		transform: translateX(0px);
	}
	75% {
		transform: translateX(2px);
	}
	100% {
		transform: translateX(0px);
	}
}
.shake {
	animation: shake 0.2s;
	animation-iteration-count: 3;
}
</style>
