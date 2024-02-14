<template>
	<div class="flex mb-2 mx-2 items-center">
		<p
			class="w-full"
			:class="todo.completed ? 'line-through text-green' : 'text-grey-darkest'"
		>
			{{ todo.content }}
		</p>
		<button class="p-2" @click="toggle">
			<svg
				xmlns="http://www.w3.org/2000/svg"
				width="16"
				height="12"
				viewBox="0 0 16 12"
				fill="none"
			>
				<path
					fill-rule="evenodd"
					clip-rule="evenodd"
					d="M13.9526 0.704158C14.3344 0.317582 14.9592 0.319966 15.338 0.709443C15.7059 1.08768 15.7039 1.69061 15.3335 2.06641L5.544 12L0.664505 7.03253C0.295079 6.65645 0.293583 6.05419 0.661134 5.67627C1.0403 5.28641 1.66581 5.28432 2.04757 5.67164L5.544 9.21899L13.9526 0.704158Z"
					fill="#000000"
				/>
			</svg>
		</button>
		<button class="p-2" @click="delTodo">
			<svg
				xmlns="http://www.w3.org/2000/svg"
				width="14"
				height="18"
				viewBox="0 0 14 18"
				fill="none"
			>
				<path
					fill-rule="evenodd"
					clip-rule="evenodd"
					d="M13 5V16C13 17.1046 12.1046 18 11 18H3C1.89543 18 1 17.1046 1 16V5H13ZM11 7H3V16H11V7ZM7 0C7.55228 0 8 0.447715 8 1V2H13C13.5523 2 14 2.44772 14 3C14 3.55228 13.5523 4 13 4H1C0.447715 4 0 3.55228 0 3C0 2.44772 0.447715 2 1 2H6V1C6 0.447715 6.44772 0 7 0Z"
					fill="#000000"
				/>
			</svg>
		</button>
	</div>
</template>

<script setup>
import { deleteTodo, toggleTodo } from '@/api/todo';
const props = defineProps(['todo']);
const emits = defineEmits(['updatedTodo']);
const todo = props.todo;

const delTodo = () => {
	// console.log('delete', todo.todoId);
	deleteTodo(todo.todoId, () => {
		emits('updatedTodo');
	});
};

const toggle = () => {
	toggleTodo(todo.todoId, () => {
		todo.completed = !todo.completed;
	});
};
</script>

<script scope></script>
