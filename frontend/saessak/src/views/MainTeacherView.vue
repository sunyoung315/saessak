<template>
	<div class="view-header">
		<p class="nav-title">{{ props.loginStore.teacherName }}</p>
		<p class="nav-title m-0">{{ year }}-{{ month }}-{{ day }}</p>
	</div>
	<div class="view-frame">
		<div class="flex">
			<div class="main-half">
				<TodoView />
			</div>
			<div class="main-half">달력 + 오늘의 생일자</div>
		</div>
		<div class="main-full">오늘의 식단표</div>
	</div>
</template>

<script setup>
import TodoView from '@/components/todo/TodoView.vue';

const props = defineProps({
	loginStore: Object,
});

const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + (today.getDay() + 1)).slice(-2);
</script>

<style scoped>
.main-half {
	@apply inline border-2 w-1/2 h-96 m-2 p-2;
}
.main-full {
	@apply border-2 w-auto h-96 m-2 p-2;
}
</style>
