<template>
	<!-- Parent Version -->
	<div v-if="!isTeacher" class="view-header flex justify-between">
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
		<template v-if="!params">
			<div class="block mt-10 ml-32 mr-6 flex items-center">
				<select id="name" class="selection-input w-24" v-model="year" required>
					<template v-for="year in years" :key="year">
						<option :value="year">{{ year }}</option>
					</template>
				</select>
				<span class="px-3">년</span>
				<select id="name" class="selection-input w-16" v-model="month" required>
					<template v-for="month in months" :key="month">
						<option :value="month">{{ month }}</option>
					</template>
				</select>
				<span class="pl-3 pr-5">월</span>
				<button class="btn m-0" @click="getList(year, month)">조회</button>
			</div>
		</template>
		<BoardSummaryModal ref="summaryModal" :kidId="kidId" />
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

	<RouterView :loginStore="loginStore" :year="year" :month="month" />
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import BoardSummaryModal from '@/components/board/BoardSummaryModal.vue';
import BoardGrowthModal from '@/components/board/BoardGrowthModal.vue';
import { useBoardStore } from '@/store/board';

const router = useRouter();
const route = useRoute();

// 요약레포트 Modal
const summaryModal = ref();
// 성장기록확인 Modal
const growthModal = ref();

const store = useBoardStore();
const years = store.years;
const months = store.months;

const year = ref(new Date().getFullYear());
const month = ref(new Date().getMonth() + 1);

// 로그인 유저 확인
const loginStore = JSON.parse(localStorage.getItem('loginStore'));
const isTeacher = loginStore.isTeacher;
// const kidId = loginStore.isTeacher ? undefined : props.loginStore.curKid;
const kidId = loginStore.isTeacher ? undefined : loginStore.kidList[0].kidId;

const goCreateBoard = () => {
	router.push({ name: 'BoardCreate' });
};

const getList = async (year, month) => {
	await store.getMyKidMonthlyBoards(kidId, year, month);
};

// 작성 화면에서는 '작성' 버튼이 보이지 않게 하기 위해 path 확인
const path = computed(() => {
	return route.path.substr(7);
});

const params = computed(() => {
	return route.params.id;
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
