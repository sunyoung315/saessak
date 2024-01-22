<template>
	<div class="container p-6 flex items-center">
		<span class="text-4xl m-5 font-extrabold inline-block">알림장</span>
		<div class="inline-flex rounded-md shadow-sm m-6">
			<button
				@click="summaryModal.openModal"
				aria-current="page"
				class="px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-s-lg hover:bg-dark-navy hover:text-white focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy"
			>
				요약레포트
			</button>
			<button
				@click="growthModal.openModal"
				class="px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-e-lg hover:bg-dark-navy hover:text-white focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy"
			>
				성장 기록 확인
			</button>
		</div>

		<BoardSummaryModal ref="summaryModal" />
		<BoardGrowthModal ref="growthModal" />
	</div>
</template>

<script setup>
import { ref } from 'vue';
import BoardSummaryModal from '@/components/board/BoardSummaryModal.vue';
import BoardGrowthModal from '@/components/board/BoardGrowthModal.vue';

const summaryModal = ref();
const growthModal = ref();
</script>

<style scoped></style>
