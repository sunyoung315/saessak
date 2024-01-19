<template>
	<div class="container p-6">
		<h1 class="text-4xl font-extrabold mt-6 mr-6 ml-6 inline-block">알림장</h1>
		<div class="inline-flex rounded-md shadow-sm m-6">
			<RouterLink
				to="/summary"
				aria-current="page"
				class="px-6 py-3 text-m font-medium font-bold text-gray-900 bg-white border border-gray-200 rounded-s-lg hover:bg-gray-100 hover:text-blue-900 focus:z-10 focus:ring-2 focus:ring-blue-900 focus:text-blue-900"
			>
				요약레포트
			</RouterLink>
			<RouterLink
				to="/growth"
				class="px-6 py-3 text-m font-medium font-bold text-gray-900 bg-white border border-gray-200 rounded-e-lg hover:bg-gray-100 hover:text-blue-900 focus:z-10 focus:ring-2 focus:ring-blue-900 focus:text-blue-900"
			>
				성장 기록 확인
			</RouterLink>
		</div>

		<RouterView></RouterView>
	</div>
</template>

<script setup></script>

<style scoped></style>
