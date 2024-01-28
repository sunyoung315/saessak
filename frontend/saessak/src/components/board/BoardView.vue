<template>
	<!-- Parent Version -->
	<div v-if="!isTeacher" class="view-container">
		<div class="view-title-parent">
			<span class="nav-title">알림장</span>
			<div class="group-btn">
				<button
					@click="summaryModal.openModal"
					aria-current="page"
					class="group-btn-left-item"
				>
					요약레포트
				</button>
				<button @click="growthModal.openModal" class="group-btn-right-item">
					성장 기록 확인
				</button>
			</div>
		</div>
		<BoardSummaryModal ref="summaryModal" />
		<BoardGrowthModal ref="growthModal" />
	</div>

	<!-- Teacher Version -->
	<div v-else class="view-container">
		<div class="view-title-teacher">
			<span class="nav-title">알림장</span>
			<button type="button" @click="goCreateBoard()" class="btn">작성</button>
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

<style scoped>
.view-title-teacher {
	@apply flex justify-between items-center;
}
.view-title-parent {
	@apply flex items-center;
}
</style>
