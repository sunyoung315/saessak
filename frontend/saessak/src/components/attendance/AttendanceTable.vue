<template>
	<div
		class="container mx-16 mb-10 p-8 w-auto border border-gray-200 shadow rounded-lg"
	>
		<!-- datepicker -->
		<div class="flex justify-between items-center m-5">
			<input
				type="date"
				v-model="selectedDate"
				@change="getWeekOfMonth"
				class="inline-block bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-36 py-2 px-4"
			/>
			<p class="inline-block w-48 font-bold text-xl">{{ weekly }}</p>
			<p class="inline-block w-36"></p>
		</div>
		<!-- table -->
		<div class="block">
			<div class="relative overflow-x-auto min-h-screen">
				<table
					class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400"
				>
					<thead
						class="text-xs text-gray-700 bg-nav-purple dark:bg-gray-700 dark:text-gray-400"
					>
						<tr>
							<th scope="col" class="pl-3 pr-6 py-3 w-1/12">이름</th>
							<th scope="col" class="px-6 py-3 w-1/6">
								{{ formattedWeekDays[0] }} (월)
							</th>
							<th scope="col" class="px-6 py-3 w-1/6">
								{{ formattedWeekDays[1] }} (화)
							</th>
							<th scope="col" class="px-6 py-3 w-1/6">
								{{ formattedWeekDays[2] }} (수)
							</th>
							<th scope="col" class="px-6 py-3 w-1/6">
								{{ formattedWeekDays[3] }} (목)
							</th>
							<th scope="col" class="px-6 py-3 w-1/6">
								{{ formattedWeekDays[4] }} (금)
							</th>
						</tr>
					</thead>
					<tbody>
						<tr
							class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
							v-for="oneKid in attendanceList"
							:key="oneKid.kidId"
						>
							<!-- 아이 이름 -->
							<th
								scope="row"
								class="pl-3 pr-6 py-4 font-semibold text-gray-900 whitespace-nowrap"
							>
								{{ oneKid.kidName }}
							</th>
							<td
								class="pl-3 pr-2"
								v-for="formattedDate in formattedDates"
								:key="formattedDate"
							>
								<template v-if="today === formattedDate">
									<!-- 버튼 -->
									<div class="flex pl-3 pr-2 py-2">
										<!-- 등원 버튼 누르기 전 -->
										<div>
											<button
												type="submit"
												class="w-16 my-2 mr-2 p-2 border rounded-md text-center h-9 align-center font-semibold bg-dark-navy text-white"
												@click="inTimePush(oneKid.kidId)"
											>
												등원
											</button>
										</div>
										<!-- 등원 버튼 누른 후 -->
										<!-- <div>
											<p
												class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold"
											>
												{{ todayInTime }}
											</p>
										</div> -->
										<!-- 하원 버튼 누르기 전 -->
										<!-- <div v-if="!inTime"> -->
										<button
											type="submit"
											class="w-16 my-2 mr-2 p-2 border rounded-md text-center h-9 align-center font-semibold bg-dark-navy text-white"
											@click="outTimePush(oneKid.kidId)"
										>
											하원
										</button>
										<!-- </div> -->
										<!-- 하원 버튼 누른 후 -->
										<!-- <div v-else>
											<p
												class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold"
											>
												{{ todayOutTime }}
											</p>
										</div> -->
									</div>
								</template>
								<template v-else>
									<template
										v-for="attendance in oneKid.attendance"
										:key="attendance.attendanceId"
									>
										<template
											v-if="formattedDate === attendance.attendanceDate"
										>
											<!-- 아이 출석 기록 -->
											<template v-if="attendance.attendanceInTime">
												<!-- 1. 등원O 하원X -->
												<template v-if="!attendance.attendanceOutTime">
													<div class="flex">
														<p
															class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold"
														>
															{{ attendance.attendanceInTime }}
														</p>
														<p
															class="my-2 p-2 border rounded-md w-16 text-center font-semibold text-gray-400"
														>
															미입력
														</p>
													</div>
												</template>
												<!-- 등원O, 하원O -->
												<template v-else>
													<div class="flex">
														<p
															class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold"
														>
															{{ attendance.attendanceInTime }}
														</p>
														<p
															class="my-2 p-2 border rounded-md w-16 text-center font-semibold"
														>
															{{ attendance.attendanceOutTime }}
														</p>
													</div>
												</template>
											</template>
											<template v-else>
												<p
													class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold text-red-400"
												>
													결석
												</p>
											</template>
										</template>
									</template>
								</template>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { getWeekOfMonth, getMonth, addDays, format } from 'date-fns';
import { useAttendanceStore } from '@/store/attendance.js';
import { kidIn, kidOut } from '@/api/attendance.js';

// input type="date"는 "yyyy-MM-dd" 형식의 문자열 -> 같은 형식으로 변환
let selectedDate = ref(new Date().toISOString().split('T')[0]);

// 오늘 날짜
const today = format(new Date(), 'yyyy-MM-dd');

// 해당년도, 월, 주차
const year = ref();
const month = ref();
const weekOfMonth = ref();

// 선택한 날짜의 월~금요일 날짜
const weekDays = ref([]);

// input에서 날짜 선택으로 인한 변화 추적
watch(
	selectedDate,
	newVal => {
		if (newVal) {
			let date = new Date(newVal);
			// 일요일 기준(0), 월요일 기준(1)
			weekOfMonth.value = getWeekOfMonth(date, { weekStartsOn: 0 });
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

// 임시 반 데이터
const classroomId = 1;
////////////////

// 주차별 출석부 요청 data
const listData = ref({
	classroomId,
	year,
	month,
	week: weekOfMonth,
});

// 주차별 출석부
const attendanceList = ref([]);

// 주자별 출석부 비동기 호출
const getList = async () => {
	await store.getWeeklyList(listData.value);
	attendanceList.value = store.weeklyList;
};

// 오늘 날짜(기본값) 데이터로 마운트
onMounted(async () => {
	await getList();
});

const inTimePush = async kidId => {
	await kidIn(kidId);
	await store.getWeeklyList(listData.value);
	attendanceList.value = store.weeklyList;
};

const outTimePush = async kidId => {
	await kidOut(kidId);
	await store.getWeeklyList(listData.value);
	attendanceList.value = store.weeklyList;
};
</script>

<style scoped></style>
