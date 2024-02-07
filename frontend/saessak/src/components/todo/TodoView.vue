<template>
	<div class="h-100 w-full flex items-center justify-center">
		<div class="rounded w-full">
			<div class="mb-4">
				<TodoCreate @create-todo="updateTodoList"></TodoCreate>
			</div>
			<div class="h-52 bg-gray-100 p-2 rounded overflow-y-scroll">
				<div v-for="todo in todoList" :key="todo.todoId">
					<TodoDetail v-bind:todo="todo" @updated-todo="updateTodoList" />
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { getTodoList } from '@/api/todo';
import TodoDetail from '@/components/todo/TodoDetail.vue';
import TodoCreate from '@/components/todo/TodoCreate.vue';

onMounted(() => {
	updateTodoList();
});
const updateTodoList = () => {
	getTodoList(response => {
		todoList.value = response.data.data;
	});
};

const todoList = ref([]);
</script>

<style scoped></style>
