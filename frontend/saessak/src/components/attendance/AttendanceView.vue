<template>
	<div class="view-container">
		<span class="nav-title">출석부</span>
	</div>
	<template v-if="isTeacher">
		<AttendanceTable />
	</template>
	<template v-else>
		<AttendanceCalendar />
	</template>
</template>

<script setup>
import { ref } from 'vue';
import AttendanceTable from '@/components/attendance/AttendanceTable.vue';
import AttendanceCalendar from '@/components/attendance/AttendanceCalendar.vue';
// import OriginalCalendar from '@/components/attendance/OriginalCalendar.vue';

const isTeacher = ref(true);
</script>

<style scoped></style>
