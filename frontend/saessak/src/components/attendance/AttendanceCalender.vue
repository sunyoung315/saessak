<template>
	<div
		class="container mx-16 mb-10 p-8 w-auto border border-gray-200 shadow rounded-lg bg bg-nav-purple"
	>
		<div class="flex bg bg-white inline-block w-80 m-2 border rounded-md">
			<div class="my-2 mx-5 inline-block text-black font-bold">
				파란색
				<svg
					xmlns="http://www.w3.org/2000/svg"
					id="Layer_1"
					data-name="Layer 1"
					viewBox="0 0 24 24"
					class="size-2 inline-block fill-blue-500"
				>
					<path d="M12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Z" />
				</svg>
				: 출석
			</div>
			<div class="my-2 mx-5 inline-block text-black font-bold">
				빨간색
				<svg
					xmlns="http://www.w3.org/2000/svg"
					id="Layer_1"
					data-name="Layer 1"
					viewBox="0 0 24 24"
					class="size-2 inline-block fill-red-500"
				>
					<path d="M12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Z" />
				</svg>
				: 결석
			</div>
		</div>
		<VCalendar :attributes="attributes" expanded :rows="2" class="p-8">
			<template #day="{ date, attributes }">
				<div class="v-day">
					<div>{{ date.day }}</div>
					<div v-if="attributes.length" class="v-attributes">
						<div v-for="attr in attributes" :key="attr.key">
							{{ attr.title }}
						</div>
					</div>
				</div>
			</template>
		</VCalendar>
	</div>
</template>

<script setup>
import { ref } from 'vue';

const attendanceList = [
	{
		attendanceId: 1,
		attendanceDate: '2024-01-22',
		attendanceInTime: '09:00',
		attendanceOutTime: '16:00',
	},
	{
		attendanceId: 2,
		attendanceDate: '2024-01-23',
		attendanceInTime: '09:00',
		attendanceOutTime: '16:00',
	},
	{
		attendanceId: 3,
		attendanceDate: '2024-01-24',
		attendanceInTime: '09:00',
		attendanceOutTime: '16:00',
	},
	{
		attendanceId: 4,
		attendanceDate: '2024-01-25',
		attendanceInTime: '',
		attendanceOutTime: '',
	},
	{
		attendanceId: 5,
		attendanceDate: '2024-01-26',
		attendanceInTime: '09:00',
		attendanceOutTime: '16:00',
	},
];

const attendedDates = attendanceList
	.filter(({ attendanceInTime }) => attendanceInTime)
	.map(({ attendanceDate, attendanceInTime, attendanceOutTime }) => ({
		key: 'attended',
		dates: [attendanceDate],
		dot: 'blue',
		popover: { label: `등원: ${attendanceInTime}, 하원: ${attendanceOutTime}` },
	}));

const absentDates = attendanceList
	.filter(({ attendanceInTime }) => !attendanceInTime)
	.map(({ attendanceDate }) => ({
		key: 'absent',
		dates: [attendanceDate],
		dot: 'red',
		popover: { label: '결석' },
	}));

const attributes = ref([...attendedDates, ...absentDates]);
</script>

<style scoped>
.vc-day-content {
	height: 100px !important;
}
</style>
