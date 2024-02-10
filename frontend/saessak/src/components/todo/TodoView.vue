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

<style scoped>
::-webkit-scrollbar {
	width: 0.5rem;
}
/* 스크롤바의 트랙(경로)부분 */
::-webkit-scrollbar-track {
	background-color: #dcdcdc;
	border-radius: 1rem;
	box-shadow: inset 0px 0px 5px white;
}
/* 스크롤바의 핸들(드래그하는 부분) */
::-webkit-scrollbar-thumb {
	background-color: #777;
	border-radius: 1rem;
}
/* 스크롤바의 핸들을 호버 시 */
::-webkit-scrollbar-thumb:hover {
	background: #555;
}
</style>
