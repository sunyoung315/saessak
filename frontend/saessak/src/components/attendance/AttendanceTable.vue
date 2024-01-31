<template>
	<div class="view-frame p-6">
		<!-- datepicker -->
		<div class="flex justify-between items-center m-5">
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
							class="border border-gray-300 text-gray-900 text-sm font-bold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5"
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
						<th scope="col" class="col-name">이름</th>
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
					<tr
						class="one-row"
						v-for="oneKid in attendanceList"
						:key="oneKid.kidId"
					>
						<!-- 아이 이름 -->
						<th scope="row" class="kid-name">
							{{ oneKid.kidName }}
						</th>
						<td
							class="pl-2"
							v-for="formattedDate in formattedDates"
							:key="formattedDate"
						>
							<template
								v-for="attendance in oneKid.attendance"
								:key="attendance.attendanceId"
							>
								<!-- 과거 날짜 -->
								<template
									v-if="
										formattedDate !== today &&
										formattedDate === attendance.attendanceDate
									"
								>
									<!-- 아이 출석 기록 -->
									<template v-if="attendance.attendanceInTime">
										<!-- 1. 등원O 하원X -->
										<template v-if="!attendance.attendanceOutTime">
											<div class="flex">
												<p class="normal-value">
													{{ attendance.attendanceInTime }}
												</p>
												<p class="miss-value">미입력</p>
											</div>
										</template>
										<!-- 등원O, 하원O -->
										<template v-else>
											<div class="flex">
												<p class="normal-value">
													{{ attendance.attendanceInTime }}
												</p>
												<p class="normal-value">
													{{ attendance.attendanceOutTime }}
												</p>
											</div>
										</template>
									</template>
									<template v-else>
										<p class="no-value">결석</p>
									</template>
								</template>
							</template>
							<!-- 오늘 날짜 -->
							<div v-if="today === formattedDate" class="flex">
								<!-- 버튼 -->
								<template
									v-if="
										!store.todayInTimeList.find(
											one => one.kidId === oneKid.kidId,
										)
									"
								>
									<!-- 등원 버튼 누르기 전 -->
									<button
										type="submit"
										class="button"
										@click="inTimePush(oneKid.kidId)"
									>
										등원
									</button>
								</template>
								<template v-else>
									<p class="normal-value">
										{{
											store.todayInTimeList.find(
												one => one.kidId === oneKid.kidId,
											).inTime
										}}
									</p>
								</template>
								<template
									v-if="
										!store.todayOutTimeList.find(
											one => one.kidId === oneKid.kidId,
										)
									"
								>
									<button
										type="submit"
										class="button"
										@click="outTimePush(oneKid.kidId)"
									>
										하원
									</button>
								</template>
								<template v-else>
									<p class="normal-value">
										{{
											store.todayOutTimeList.find(
												one => one.kidId === oneKid.kidId,
											).outTime
										}}
									</p>
								</template>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { getWeekOfMonth, getMonth, addDays, format } from 'date-fns';
import { useAttendanceStore } from '@/store/attendance.js';

// input type="date"는 "yyyy-MM-dd" 형식의 문자열 -> 같은 형식으로 변환
let selectedDate = ref(new Date().toISOString().split('T')[0]);

// 오늘 날짜
const today = ref(format(new Date(), 'yyyy-MM-dd'));

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

// attendaneDate와 매칭하기 위한 yyyy-MM-dd 형식으로 추출
const formattedDates = computed(() => {
	return weekDays.value.map(date => format(date, 'yyyy-MM-dd'));
});

// 테이블 위의 선택 주차 표시
let weekly = computed(() => {
	if (selectedDate.value) {
		return `${year.value}년 ${month.value}월 ${weekOfMonth.value}주차`;
	}
	return '';
});

const store = useAttendanceStore();

// 주차별 출석부 요청 data
const listData = ref({
	year,
	month,
	week: weekOfMonth.value,
});

// 주차별 출석부
const attendanceList = ref([]);

// 주자별 출석부 비동기 호출
const getList = async () => {
	await store.getWeeklyList(listData.value);
	attendanceList.value = store.weeklyList;
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

			// listData 업데이트
			listData.value = {
				...listData.value,
				year: year.value,
				month: month.value,
				week: weekOfMonth.value,
			};

			// attendanceList 갱신
			await getList();
		}
	},
	{ immediate: true },
);

// 등원 요청
const inTimePush = async kidId => {
	await store.kidIn(kidId);
};

//하원 요청
const outTimePush = async kidId => {
	await store.kidOut(kidId);
};

// 오늘 날짜(기본값) 데이터로 마운트
onMounted(async () => {
	await getList();
});
</script>

<style scoped>
.table-box {
	@apply relative overflow-x-auto min-h-screen m-5;
}
.table {
	@apply w-full text-base text-left rtl:text-right text-gray-500 dark:text-gray-400;
}
.table-head {
	@apply text-gray-700 bg-nav-purple bg-opacity-30 dark:bg-gray-700 dark:text-gray-400;
}
.col-name {
	@apply pl-3 pr-6 py-3 w-1/12;
}
.col-date {
	@apply px-6 py-3 w-1/6;
}
.one-row {
	@apply bg-white border-b h-20;
}
.kid-name {
	@apply pl-3 pr-6 py-4 text-gray-900 whitespace-nowrap;
}
.normal-value {
	@apply w-20 my-2 mr-2 p-2 border rounded-md text-center text-gray-900 font-medium;
}
.miss-value {
	@apply w-20 my-2 mr-2 p-2 border rounded-md text-center text-gray-400 font-medium;
}
.no-value {
	@apply w-20 my-2 mr-2 p-2 border rounded-md text-center text-red-600 font-medium;
}
.button {
	@apply w-20 my-2 mr-2 p-2 border rounded-md bg-dark-navy text-center text-gray-100 font-medium;
}
</style>
