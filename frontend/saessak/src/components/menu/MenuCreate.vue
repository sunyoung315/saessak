<template>
	<div class="view-frame">
		<div class="table-box">
			<div class="flex justify-between">
				<div class="flex items-center m-2">
					<div class="mr-4">날짜</div>
					<div>
						<VDatePicker :select-attribute="selectAttribute" v-model="menuDate">
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
				<button @click="createMenu(menuList)" class="btn my-2 mx-2">
					등록
				</button>
			</div>
			<table class="table">
				<thead class="table-head">
					<tr>
						<th scope="col" class="col">분류</th>
						<th scope="col" class="col-food">음식</th>
						<th scope="col" class="col-allergy">알레르기 식품</th>
					</tr>
				</thead>
				<tbody>
					<tr class="one-row" v-for="(menu, index) in menuList" :key="index">
						<td class="col">
							<select
								id="menu-type"
								class="selection-input w-36"
								v-model="menu.menuType"
								required
							>
								<option value="점심">점심</option>
								<option value="간식">간식</option>
							</select>
						</td>
						<td class="col-food">
							<input type="text" class="input w-56" v-model="menu.foodName" />
						</td>
						<td>
							<template v-for="allergy in allergyList" :key="allergy.allergyId">
								<span class="checkbox-frame">
									<input
										type="checkbox"
										class="checkbox"
										:value="allergy.allergyId"
										v-model="menu.foodAllergy"
									/>
									<span class="checkbox-text">{{ allergy.allergyName }}</span>
								</span>
							</template>
						</td>
					</tr>
					<tr class="one-row h-2">
						<button
							@click="addOneRow"
							class="text-center text-black text-lg font-bold m-7"
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
import { ref } from 'vue';
import { useMenuStore } from '@/store/menu';
import { format } from 'date-fns';

const selectAttribute = ref({ highlight: 'blue' });

const menuDate = ref(new Date());

const store = useMenuStore();

const allergyList = store.allergyList;

const menuList = ref([
	{
		menuDate: format(menuDate.value, 'yyyy-MM-dd'),
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
			menuDate: format(menuDate.value, 'yyyy-MM-dd'),
			menuType: '',
			foodName: '',
			foodAllergy: [],
		},
	];
};

const createMenu = async menuList => {
	// proxy객체는 for...in구문, join 직접 사용 불가
	menuList.forEach(menu => {
		menu.foodAllergy = menu.foodAllergy
			.map(allergy => allergy.toString())
			.join('/');
	});
	for (let i = 0; i < menuList.length; i++) {
		console.log(menuList[i]);
		await store.createMenu(menuList[i]);
	}
};
</script>

<style scoped>
.table-box {
	@apply relative overflow-x-auto min-h-screen m-3;
}
.table {
	@apply w-full text-base text-left rtl:text-right text-gray-500 dark:text-gray-400;
}
.table-head {
	@apply text-gray-700 bg-nav-navy bg-opacity-30 dark:bg-gray-700 dark:text-gray-400;
}
.col {
	@apply p-3 w-1/6;
}
.col-food {
	@apply p-3 w-1/4;
}
.col-allergy {
	@apply p-3 w-5/12;
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
.checkbox-text {
	@apply px-1 text-base;
}
</style>
