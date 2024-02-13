<template>
	<div class="flex items-center justify-between overflow-y-auto">
		<h1>Oops!</h1>
		<h2>404 Not Found</h2>
		<div>요청한 페이지를 찾을 수 없습니다.</div>
		<RouterLink to="/">
			<button
				class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
			>
				HOME 돌아가기
			</button>
		</RouterLink>
	</div>
</template>

<script setup></script>

<style scoped></style>
