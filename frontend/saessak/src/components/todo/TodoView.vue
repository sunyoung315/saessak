<template>
	<div class="h-100 w-full flex items-center justify-center bg-teal-lightest font-sans">
		<div class="bg-white rounded shadow p-6 m-4 w-full lg:w-3/4 lg:max-w-lg">
			<div class="mb-4">
				<h1 class="text-grey-darkest">Todo List</h1>
				<TodoCreate @create-todo="updateTodoList"></TodoCreate>
			</div>
			<div v-for="todo in todoList" :key="todo.todoId">
				<TodoDetail v-bind:todo="todo" @updated-todo="updateTodoList" />
			</div>
		</div>

	</div>
</template>

<script setup>
import { BaseTransitionPropsValidators, onMounted, ref, watch } from 'vue';
import { getTodoList } from '@/api/todo';
import TodoDetail from '@/components/todo/TodoDetail.vue';
import TodoCreate from '@/components/todo/TodoCreate.vue';

onMounted(() => {
	updateTodoList();
});
const updateTodoList = () => {
	// console.log("dothis")
	getTodoList(
		response => {
			// console.log(response)
			todoList.value = response.data.data;
		},
		error => {
			// console.log('fail');
		},
	);
}

const todoList = ref([]);
</script>

<style scoped></style>
