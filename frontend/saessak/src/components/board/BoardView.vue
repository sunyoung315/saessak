<template>
	<!-- Parent Version -->
	<div v-if="!isTeacher" class="view-header">
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
	<div v-else class="view-header">
		<div class="view-title-teacher">
			<span class="nav-title">알림장</span>
			<template v-if="path !== 'create'">
				<button type="button" @click="goCreateBoard()" class="btn">작성</button>
			</template>
		</div>
	</div>

	<RouterView :loginStore="loginStore" />
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import BoardSummaryModal from '@/components/board/BoardSummaryModal.vue';
import BoardGrowthModal from '@/components/board/BoardGrowthModal.vue';

const router = useRouter();
const route = useRoute();

// 요약레포트 Modal
const summaryModal = ref();
// 성장기록확인 Modal
const growthModal = ref();

// 로그인 유저 확인
const loginStore = JSON.parse(localStorage.getItem('loginStore'));
const isTeacher = loginStore.isTeacher;

const goCreateBoard = () => {
	router.push({ name: 'BoardCreate' });
};

// 작성 화면에서는 '작성' 버튼이 보이지 않게 하기 위해 path 확인
const path = computed(() => {
	return route.path.substr(7);
});
</script>

<style scoped>
.view-title-teacher {
	@apply flex justify-between items-center;
}
.view-title-parent {
	@apply flex items-center;
}
</style>
