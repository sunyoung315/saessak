<template>
	<div class="view-frame">
		<!-- datepicker -->
		<div class="datepicker">
			<VDatePicker
				:select-attribute="selectAttribute"
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
			<table class="table">
				<thead class="table-head">
					<tr>
						<th scope="col" class="col-date">
							{{ formattedWeekDays[0] }} (월)
						</th>
						<th scope="col" class="col-date">
							{{ formattedWeekDays[1] }} (화)
						</th>
						<th scope="col" class="col-date">
							{{ formattedWeekDays[2] }} (수)
						</th>
						<th scope="col" class="col-date">
							{{ formattedWeekDays[3] }} (목)
						</th>
						<th scope="col" class="col-date">
							{{ formattedWeekDays[4] }} (금)
						</th>
					</tr>
				</thead>
				<tbody>
					<tr class="one-row">
						<td class="p-2">
							<div class="menu-type">점심</div>
							<img src="" alt="사진" class="menu-image" />
							<!-- <div class="food-item">
								<template v-for="food in foodList" :key="food.foodId">
									<div class="food-title">{{ food.foodName }}</div>
									<div class="food-allergy">( {{ food.foodAllergy }} )</div>
								</template>
							</div> -->
						</td>
					</tr>
				</tbody>
			</table>
			<div>
				<div>※ 알레르기 유발식품</div>
				<div>
					1. 난류 2. 우유 3. 메밀 4. 땅콩 5. 대두 6. 밀 7. 고등어 8. 게 9. 새우
					10. 돼지고기 11. 복숭아 12. 토마토 13. 아황산류 14. 호두 15. 닭고기
					16. 쇠고기 17. 오징어 18. 조개 19. 잣
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { getWeekOfMonth, getMonth, addDays, format } from 'date-fns';

// input type="date"는 "yyyy-MM-dd" 형식의 문자열 -> 같은 형식으로 변환
let selectedDate = ref(new Date().toISOString().split('T')[0]);

const selectAttribute = ref({ highlight: 'purple' });

// 해당년도, 월, 주차
const year = ref();
const month = ref();
const weekOfMonth = ref();

// 선택한 날짜의 월~금요일 날짜
const weekDays = ref([]);

// 테이블 thead에 표시할 일자만 추출
const formattedWeekDays = computed(() => {
	return weekDays.value.map(date => format(date, 'd일'));
});

// // attendaneDate와 매칭하기 위한 yyyy-MM-dd 형식으로 추출
// const formattedDates = computed(() => {
// 	return weekDays.value.map(date => format(date, 'yyyy-MM-dd'));
// });

// 테이블 위의 선택 주차 표시
let weekly = computed(() => {
	if (selectedDate.value) {
		return `${year.value}년 ${month.value}월 ${weekOfMonth.value}주차`;
	}
	return '';
});

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
		}
	},
	{ immediate: true },
);

// const menuList = ref([]);
// 오늘 날짜(기본값) 데이터로 마운트
onMounted(async () => {});
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
.col-date {
	@apply px-6 py-3 w-1/5;
}
.one-row {
	@apply bg-white border-b h-20;
}
.menu-type {
	@apply text-gray-900 font-extrabold text-lg p-2;
}
.food-item {
	@apply flex flex-wrap items-center p-1;
}
.food-title {
	@apply text-gray-900 font-bold pr-2;
}
.food-allergy {
	@apply text-gray-500 text-xs;
}
.menu-image {
	@apply m-1 h-auto border border-2;
}
</style>
