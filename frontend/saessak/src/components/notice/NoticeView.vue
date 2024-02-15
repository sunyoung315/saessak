<template>
	<!-- Parent Version -->
	<div class="view-header flex justify-between">
		<div class="view-title-parent">
			<span class="nav-title">공지사항</span>
		</div>
		<div class="flex justify-end items-center">
			<template v-if="isTeacher && path !== 'create'">
				<button type="button" @click="goCreateBoard()" class="btn">작성</button>
			</template>
		</div>
	</div>
	<RouterView />
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

// 로그인 유저 확인
const loginStore = JSON.parse(localStorage.getItem('loginStore'));
const isTeacher = loginStore.isTeacher;

const goCreateBoard = () => {
	router.push({ name: 'NoticeCreate' });
};

// 작성 화면에서는 '작성' 버튼이 보이지 않게 하기 위해 path 확인
const path = computed(() => {
	//console.log(route.path.substr(8))
	return route.path.substr(8);
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
