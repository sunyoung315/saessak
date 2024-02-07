<template>
	<div class="h-100 w-full flex items-center justify-center">
		<div class="rounded w-full">
			<div class="mb-2">
				<TodoCreate @create-todo="updateTodoList" />
			</div>
			<div
				class="h-[13.5rem] bg-white border border-gray-200 shadow-md px-4 py-3 rounded overflow-auto"
			>
				<template v-if="todoList.length">
					<div v-for="todo in todoList" :key="todo.todoId">
						<TodoDetail v-bind:todo="todo" @updated-todo="updateTodoList" />
					</div>
				</template>
				<template v-else>
					<div class="text-gray-400">등록된 오늘의 할 일이 없습니다.</div>
				</template>
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
