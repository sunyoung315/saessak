<template>
	<div
		class="container relative mx-16 mb-10 p-8 w-auto border border-gray-200 shadow rounded-lg bg bg-purple-bg"
	>
		<div class="flex justify-end m-2">
			<div
				class="flex items-center my-2 mx-5 inline-block text-black font-bold"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					id="Bold"
					viewBox="0 0 24 24"
					class="size-4 mx-1 fill-blue-500"
				>
					<path
						d="M12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,21a9,9,0,1,1,9-9A9.01,9.01,0,0,1,12,21Z"
					/>
				</svg>
				<span>: 출석</span>
			</div>
			<div
				class="flex items-center my-2 mx-5 inline-block text-black font-bold"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					id="Bold"
					viewBox="0 0 24 24"
					class="size-4 mx-1 fill-red-500"
				>
					<path
						d="M12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,21a9,9,0,1,1,9-9A9.01,9.01,0,0,1,12,21Z"
					/>
				</svg>
				<span>: 결석</span>
			</div>
		</div>
		<VCalendar :attributes="attributes" expanded :rows="1" class="p-8">
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
		highlight: {
			color: 'blue',
			fillMode: 'outline',
		},
		// dot: 'blue',
		popover: { label: `등원: ${attendanceInTime}, 하원: ${attendanceOutTime}` },
	}));

const absentDates = attendanceList
	.filter(({ attendanceInTime }) => !attendanceInTime)
	.map(({ attendanceDate }) => ({
		key: 'absent',
		dates: [attendanceDate],
		highlight: {
			color: 'red',
			fillMode: 'outline',
		},
		// dot: 'red',
		popover: { label: '결석' },
	}));

const attributes = ref([...attendedDates, ...absentDates]);
</script>

<style scoped>
.vc-day-content {
	height: 100px !important;
}
:deep(.vc-week) {
	padding: 40px;
}
</style>
