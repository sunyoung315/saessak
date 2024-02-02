<template>
	<div class="view-header">
		<!-- Parent Version -->
		<template v-if="!isTeacher">
			<span class="nav-title">식단표</span>
		</template>

		<!-- Teacher Version -->
		<template v-else>
			<div class="view-title-teacher">
				<span class="nav-title">식단표</span>
				<template v-if="path !== 'create'">
					<button type="button" @click="goCreateMenu()" class="btn">
						작성
					</button>
				</template>
			</div>
		</template>
	</div>

	<RouterView :loginStore="loginStore" />
</template>

<script setup>
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

// 로그인 유저 확인
const loginStore = JSON.parse(localStorage.getItem('loginStore'));
const isTeacher = loginStore.isTeacher;

// 작성 버튼 클릭 시 작성 화면으로
const goCreateMenu = () => {
	router.push({ name: 'MenuCreate' });
};

// 작성 화면에서 '작성' 버튼이 보이지 않게 하기 위해서 path 확인
const path = computed(() => {
	return route.path.substr(7);
});
</script>

<style scoped>
.view-title-teacher {
	@apply flex justify-between items-center;
}
</style>
