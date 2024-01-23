<template>
	<div
		class="container ml-12 mr-16 w-11/12 border border-gray-200 shadow rounded-lg"
	>
		<RouterLink
			:to="{ path: `/detail/${id}` }"
			class="bg-[url('assets/BoardFrame.png')] inline-block w-80 max-w-sm m-2 p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100"
			>이동</RouterLink
		>
	</div>
</template>

<script setup></script>

<style scoped></style>
