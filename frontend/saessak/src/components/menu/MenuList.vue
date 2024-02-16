<template>
	<div class="view-frame">
		<!-- datepicker -->
		<div class="datepicker">
			<VDatePicker
				:select-attribute="selectAttribute"
				:disabled-dates="disabledDates"
				v-model="selectedDate"
				@change="getWeekOfMonth"
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
			<p class="inline-block w-48 font-bold text-xl">{{ weekly }}</p>
			<p class="inline-block w-36"></p>
		</div>
		<!-- table -->
		<div class="table-box">
			<template v-if="store.weeklyMenu">
				<table class="table">
					<thead>
						<template v-for="(date, index) in weekDays" :key="date">
							<th scope="col" class="date-header">
								{{ format(date, 'd일') }} {{ dayOfTheWeek[index] }}
							</th>
						</template>
					</thead>
					<tbody>
						<tr>
							<td v-for="date in weekDays" :key="date" class="oneday-menu">
								<div class="block">
									<template v-for="menu in store.weeklyMenu" :key="menu.menuId">
										<div
											v-if="
												format(date, 'yyyy-MM-dd').toString() ===
													menu.menuDate && menu.menuType === '점심'
											"
											class="menu-card min-h-[30rem]"
										>
											<template v-if="props.loginStore.isTeacher">
												<div class="flex justify-between items-center">
													<div class="menu-type">
														{{ menu.menuType }}
													</div>
													<button
														@click="registPhotoModal.openModal(menu)"
														class="add-image-btn"
													>
														<svg
															xmlns="http://www.w3.org/2000/svg"
															width="20"
															height="16"
															viewBox="0 0 20 16"
															fill="none"
														>
															<path
																fill-rule="evenodd"
																clip-rule="evenodd"
																d="M18 0C19.1046 0 20 0.89543 20 2V14C20 15.1046 19.1046 16 18 16H2C0.89543 16 0 15.1046 0 14V2C0 0.89543 0.89543 0 2 0H18ZM18 2H2V14H2.584L8.9477 7.63809L12.421 10.615L18 4.607V2ZM18 7.545L12.5789 13.3847L9.052 10.362L5.414 14H18V7.545ZM5.5 4C6.32843 4 7 4.67157 7 5.5C7 6.32843 6.32843 7 5.5 7C4.67157 7 4 6.32843 4 5.5C4 4.67157 4.67157 4 5.5 4Z"
																fill="#FFFFFF"
															/>
														</svg>
													</button>
												</div>
											</template>
											<template v-else>
												<div class="menu-type">
													{{ menu.menuType }}
												</div>
											</template>
											<template v-if="menu.menuPath">
												<img
													:src="menu.menuPath"
													alt="점심사진"
													class="menu-image"
												/>
											</template>
											<template v-else>
												<img
													src="@/assets/tray.png"
													alt=""
													class="menu-image"
												/>
											</template>
											<template
												v-for="food in menu.foodList"
												:key="food.foodId"
											>
												<div class="food-item">
													<div class="food-title">{{ food.foodName }}</div>
													<div v-if="food.foodAllergy" class="food-allergy">
														( {{ food.foodAllergy.split('/').join(', ') }} )
													</div>
												</div>
											</template>
										</div>
									</template>
								</div>
							</td>
						</tr>
						<tr>
							<td v-for="date in weekDays" :key="date" class="oneday-menu">
								<div class="block">
									<template v-for="menu in store.weeklyMenu" :key="menu.menuId">
										<div
											v-if="
												format(date, 'yyyy-MM-dd').toString() ===
													menu.menuDate && menu.menuType === '간식'
											"
											class="menu-card min-h-[22.4rem]"
										>
											<template v-if="props.loginStore.isTeacher">
												<div class="flex justify-between items-center">
													<div class="menu-type">
														{{ menu.menuType }}
													</div>
													<button
														@click="registPhotoModal.openModal(menu)"
														class="w-7 h-7 mx-2 rounded bg-dark-navy flex justify-center items-center"
													>
														<svg
															xmlns="http://www.w3.org/2000/svg"
															width="20"
															height="16"
															viewBox="0 0 20 16"
															fill="none"
														>
															<path
																fill-rule="evenodd"
																clip-rule="evenodd"
																d="M18 0C19.1046 0 20 0.89543 20 2V14C20 15.1046 19.1046 16 18 16H2C0.89543 16 0 15.1046 0 14V2C0 0.89543 0.89543 0 2 0H18ZM18 2H2V14H2.584L8.9477 7.63809L12.421 10.615L18 4.607V2ZM18 7.545L12.5789 13.3847L9.052 10.362L5.414 14H18V7.545ZM5.5 4C6.32843 4 7 4.67157 7 5.5C7 6.32843 6.32843 7 5.5 7C4.67157 7 4 6.32843 4 5.5C4 4.67157 4.67157 4 5.5 4Z"
																fill="#FFFFFF"
															/>
														</svg>
													</button>
												</div>
											</template>
											<template v-else>
												<div class="menu-type">{{ menu.menuType }}</div>
											</template>
											<template v-if="menu.menuPath">
												<img
													:src="menu.menuPath"
													alt="간식사진"
													class="menu-image"
												/>
											</template>
											<template v-else>
												<img
													src="@/assets/tray.png"
													alt=""
													class="menu-image"
												/>
											</template>
											<template
												v-for="food in menu.foodList"
												:key="food.foodId"
											>
												<div class="food-item">
													<div class="food-title">{{ food.foodName }}</div>
													<div v-if="food.foodAllergy" class="food-allergy">
														( {{ food.foodAllergy.split('/').join(', ') }} )
													</div>
												</div>
											</template>
										</div>
									</template>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</template>
			<template v-else>
				<table class="table">
					<thead>
						<template v-for="(date, index) in weekDays" :key="date">
							<th scope="col" class="date-header">
								{{ format(date, 'd일') }} {{ dayOfTheWeek[index] }}
							</th>
						</template>
					</thead>
				</table>
				<div class="no-menu">등록된 식단표가 없습니다.</div>
			</template>
			<br />
			<div class="p-2">
				<div class="px-2 text-lg font-bold">※ 알레르기 유발식품</div>
				<template v-for="allergy in store.allergyList" :key="allergy.allergyId">
					<div class="inline-block px-2 pt-1">
						{{ allergy.allergyId }}. {{ allergy.allergyName }}
					</div>
				</template>
			</div>
		</div>
	</div>
	<MenuRegistPhoto ref="registPhotoModal" :getWeeklyMenu="getWeeklyMenu" />
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { getWeekOfMonth, getMonth, addDays, format } from 'date-fns';
import { useMenuStore } from '@/store/menu';
import MenuRegistPhoto from '@/components/menu/MenuRegistPhoto.vue';

const registPhotoModal = ref();

// input type="date"는 "yyyy-MM-dd" 형식의 문자열 -> 같은 형식으로 변환
let selectedDate = ref(new Date().toISOString().split('T')[0]);

const selectAttribute = ref({ highlight: 'blue' });

const disabledDates = ref([
	{
		repeat: {
			weekdays: [1, 7],
		},
	},
]);

const dayOfTheWeek = ['(월)', '(화)', '(수)', '(목)', '(금)'];

// 해당년도, 월, 주차
const year = ref();
const month = ref();
const weekOfMonth = ref();

// 선택한 날짜의 월~금요일 날짜
const weekDays = ref([]);

// 테이블 위의 선택 주차 표시
let weekly = computed(() => {
	if (selectedDate.value) {
		return `${year.value}년 ${month.value}월 ${weekOfMonth.value}주차`;
	}
	return '';
});

// 주차별 식단표 요청 data
const weeklyDate = ref({
	year,
	month,
	week: weekOfMonth.value,
});

const props = defineProps({
	loginStore: Object,
});

const store = useMenuStore();

// 주차별 식단표 비동기 호출
const getWeeklyMenu = async () => {
	weeklyDate.value = {
		year: year.value,
		month: month.value,
		week: weekOfMonth.value,
	};
	if (props.loginStore.isTeacher) {
		await store.getTeacherWeeklyMenu(weeklyDate.value);
	} else {
		await store.getParentWeeklyMenu(
			weeklyDate.value,
			props.loginStore.kidList[0].kidId,
		);
	}
};

// input에서 날짜 선택으로 인한 변화 추적
watch(
	selectedDate,
	async newVal => {
		if (newVal) {
			let date = new Date(newVal);
			// 일요일 기준(0), 월요일 기준(1)
			weekOfMonth.value = getWeekOfMonth(date, { weekStartsOn: 1 });
			month.value = getMonth(date) + 1;
			year.value = date.getFullYear();

			// 주차에 해당하는 날짜 계산
			const startOfWeek = addDays(date, -(date.getDay() || 7) + 1); // 주의 첫번째 요일 (월요일)
			weekDays.value = Array.from({ length: 5 }, (_, i) =>
				addDays(startOfWeek, i),
			);

			await getWeeklyMenu();
		}
	},
	{ immediate: true },
);

// 오늘 날짜(기본값) 데이터로 마운트
onMounted(async () => {
	await getWeeklyMenu();
});
</script>

<style scoped>
.table-box {
	@apply min-h-screen m-3  border border-gray-200 rounded p-1;
}
.table {
	@apply w-full text-base text-left text-gray-500;
}
.date-header {
	@apply pt-4 pb-2 text-lg text-black text-center;
}
.oneday-menu {
	@apply w-1/5 align-top;
}
.menu-card {
	@apply p-2 m-1 bg-slate-50 rounded-md border border-gray-200 shadow-md;
}
.menu-type {
	@apply text-gray-900 font-extrabold text-xl py-2 px-2;
}
.food-item {
	@apply flex flex-wrap items-center p-1;
}
.add-image-btn {
	@apply w-7 h-6 mx-1 mt-1 rounded bg-dark-navy flex justify-center items-center;
}
.food-title {
	@apply text-gray-900 font-bold text-lg pr-2 py-0.5;
}
.food-allergy {
	@apply text-gray-500 text-base;
}
.menu-image {
	@apply w-full h-52 border-gray-300 border-2 p-1 mb-2;
}
.no-menu {
	@apply text-base font-semibold bg-slate-50 rounded-md border border-gray-200 shadow-md m-1 pl-10 pt-10 pb-20;
}
</style>
