<template>
	<div>
		<span class="text-3xl mt-10 ml-14 mb-3 mr-5 font-extrabold inline-block"
			>출석부</span
		>
	</div>
	<template v-if="isTeacher">
		<AttendanceTable />
	</template>
	<template v-else>
		<AttendanceCalender />
	</template>
</template>

<script setup>
import { ref } from 'vue';
import AttendanceTable from '@/components/attendance/AttendanceTable.vue';
import AttendanceCalender from '@/components/attendance/AttendanceCalender.vue';
const isTeacher = ref(false);
</script>

<style scoped></style>
