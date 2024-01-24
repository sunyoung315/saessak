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
							<th scope="col" class="px-6 py-3 w-1/6">월</th>
							<th scope="col" class="px-6 py-3 w-1/6">화</th>
							<th scope="col" class="px-6 py-3 w-1/6">수</th>
							<th scope="col" class="px-6 py-3 w-1/6">목</th>
							<th scope="col" class="px-6 py-3 w-1/6">금</th>
						</tr>
					</thead>
					<tbody>
						<tr
							class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
							v-for="attendance in attendanceList"
							:key="attendance.kidId"
						>
							<th
								scope="row"
								class="pl-3 pr-6 py-4 font-semibold text-gray-900 whitespace-nowrap"
							>
								{{ attendance.kidName }}
							</th>
							<td
								class="pl-3 pr-2 py-2"
								v-for="oneDay in attendance.attendance"
								:key="oneDay.attendanceId"
							>
								<template v-if="oneDay.attendanceInTime">
									<template v-if="!oneDay.attendanceOutTime">
										<div class="flex">
											<p
												class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold"
											>
												{{ oneDay.attendanceInTime }}
											</p>
											<p
												class="my-2 p-2 border rounded-md w-16 text-center font-semibold text-gray-400"
											>
												미입력
											</p>
										</div>
									</template>
									<template v-else>
										<div class="flex">
											<p
												class="my-2 mr-2 p-2 border rounded-md w-16 text-center font-semibold"
											>
												{{ oneDay.attendanceInTime }}
											</p>
											<p
												class="my-2 p-2 border rounded-md w-16 text-center font-semibold"
											>
												{{ oneDay.attendanceOutTime }}
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
							</td>
							<template v-if="attendance.attendance.length < 5">
								<div class="flex pl-3 pr-2 py-2">
									<button
										type="submit"
										class="w-16 my-2 mr-2 p-2 border rounded-md text-center h-9 align-center font-semibold bg-dark-navy text-white"
										@click="inTimePush"
									>
										등원
									</button>
									<button
										type="submit"
										class="w-16 my-2 mr-2 p-2 border rounded-md text-center h-9 align-center font-semibold bg-dark-navy text-white"
										@click="OutTimePush"
									>
										하원
									</button>
								</div>
							</template>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { getWeekOfMonth, getMonth } from 'date-fns';

// input type="date"는 "yyyy-MM-dd" 형식의 문자열 -> 같은 형식으로 변환
let selectedDate = ref(new Date().toISOString().split('T')[0]);

let weekly = computed(() => {
	if (selectedDate.value) {
		let date = new Date(selectedDate.value);
		// 일요일 기준(0), 월요일 기준(1)
		let weekOfMonth = getWeekOfMonth(date, { weekStartsOn: 0 });
		let month = getMonth(date) + 1;
		let year = date.getFullYear();
		return `${year}년 ${month}월 ${weekOfMonth}주차`;
	}
	return '';
});

const attendanceList = [
	{
		kidId: 100,
		kidName: '박나은',
		attendance: [
			{
				attendanceId: 1,
				attendanceDate: '24/01/22',
				attendanceInTime: '09:01',
				attendanceOutTime: '16:01',
			},
			{
				attendanceId: 2,
				attendanceDate: '24/01/23',
				attendanceInTime: '09:01',
				attendanceOutTime: '16:01',
			},
			{
				attendanceId: 3,
				attendanceDate: '24/01/24',
				attendanceInTime: '09:01',
				attendanceOutTime: '',
			},
			{
				attendanceId: 4,
				attendanceDate: '24/01/25',
				attendanceInTime: '09:01',
				attendanceOutTime: '16:01',
			},
			// {
			// 	attendanceId: 5,
			// 	attendanceDate: '24/01/26',
			// 	attendanceInTime: '09:01',
			// 	attendanceOutTime: '16:01',
			// },
		],
	},
	{
		kidId: 200,
		kidName: '박건후',
		attendance: [
			{
				attendanceId: 1,
				attendanceDate: '24/01/22',
				attendanceInTime: '09:00',
				attendanceOutTime: '16:00',
			},
			{
				attendanceId: 2,
				attendanceDate: '24/01/23',
				attendanceInTime: '09:00',
				attendanceOutTime: '16:00',
			},
			{
				attendanceId: 3,
				attendanceDate: '24/01/24',
				attendanceInTime: '09:00',
				attendanceOutTime: '16:00',
			},
			{
				attendanceId: 4,
				attendanceDate: '24/01/25',
				attendanceInTime: '',
				attendanceOutTime: '',
			},
			// {
			// 	attendanceId: 5,
			// 	attendanceDate: '24/01/26',
			// 	attendanceInTime: '09:00',
			// 	attendanceOutTime: '16:00',
			// },
		],
	},
];
</script>

<style scoped></style>
