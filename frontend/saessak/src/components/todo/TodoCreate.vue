<template>
	<div class="flex mt-4">
		<input
			class="shadow appearance-none border rounded w-full py-2 px-3 mr-2 text-grey-darker"
			placeholder="오늘 할 일을 기록하세요."
			v-model="content"
			type="text"
			@keyup.enter="createTodo"
		/>
		<button class="btn m-0 p-4" @click="createTodo">
			<svg
				xmlns="http://www.w3.org/2000/svg"
				width="14"
				height="14"
				viewBox="0 0 14 14"
				fill="none"
			>
				<path
					fill-rule="evenodd"
					clip-rule="evenodd"
					d="M7 0C7.55228 0 8 0.447715 8 1V5.999L13 6C13.5523 6 14 6.44772 14 7C14 7.55228 13.5523 8 13 8L8 7.999V13C8 13.5523 7.55228 14 7 14C6.44772 14 6 13.5523 6 13V7.999L1 8C0.447715 8 0 7.55228 0 7C0 6.44772 0.447715 6 1 6L6 5.999V1C6 0.447715 6.44772 0 7 0Z"
					fill="#ffffff"
				/>
			</svg>
		</button>
	</div>
</template>

<script setup>
import { postTodo } from '@/api/todo';
import { ref } from 'vue';
const content = ref('');
const emits = defineEmits(['createTodo']);
const createTodo = async () => {
	postTodo(content.value, () => {
		emits('createTodo');
		content.value = '';
	});
};
</script>

<script scope></script>
