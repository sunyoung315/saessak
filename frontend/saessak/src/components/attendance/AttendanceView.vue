<template>
	<div class="view-header">
		<span class="nav-title">출석부</span>
	</div>
	<template v-if="isTeacher">
		<AttendanceTable />
	</template>
	<template v-else>
		<AttendanceCalendar />
		<!-- <OriginalCalendar /> -->
	</template>
</template>

<script setup>
import { ref } from 'vue';
import AttendanceTable from '@/components/attendance/AttendanceTable.vue';
import AttendanceCalendar from '@/components/attendance/AttendanceCalendar.vue';
// import OriginalCalendar from '@/components/attendance/OriginalCalendar.vue';

const loginStore = localStorage.getItem('loginStore');

const isTeacher = ref(JSON.parse(loginStore).isTeacher);
</script>

<style scoped></style>
