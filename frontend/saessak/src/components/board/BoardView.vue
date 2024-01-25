<template>
	<!-- Parent Version -->
	<div v-if="!isTeacher" class="container p-6 flex items-center">
		<div>
			<span class="text-3xl mt-10 ml-14 mb-3 mr-5 font-extrabold inline-block"
				>알림장</span
			>
		</div>
		<div class="inline-flex rounded-md shadow-sm ml-6 mt-5">
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

	<!-- Teacher Version -->
	<div v-else class="container p-6">
		<div class="flex justify-between items-center">
			<span class="text-3xl mt-10 ml-14 mb-3 font-extrabold inline-block"
				>알림장</span
			>
			<button
				type="button"
				@click="goCreateBoard()"
				class="mt-9 mr-12 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
			>
				작성
			</button>
		</div>
	</div>
	<RouterView />
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import BoardSummaryModal from '@/components/board/BoardSummaryModal.vue';
import BoardGrowthModal from '@/components/board/BoardGrowthModal.vue';

const router = useRouter();

const summaryModal = ref();
const growthModal = ref();

const isTeacher = ref(true);

const goCreateBoard = () => {
	router.push({ name: 'BoardCreate' });
};
</script>

<style scoped></style>
