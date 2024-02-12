<template>
	<div class="view-header flex justify-between">
		<div class="flex items-center">
			<span class="nav-title">앨범</span>
			<div class="group-btn" v-show="groupBtnVisible">
				<label class="relative inline-flex items-center me-5 cursor-pointer">
					<input
						type="checkbox"
						class="sr-only peer"
						checked
						v-model="showToggle"
					/>
					<div
						class="w-12 h-7 mr-5 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-nav-green peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-1 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 peer-checked:bg-nav-green"
					></div>
					<span
						v-if="loginStore.isTeacher"
						class="text-xl font-extrabold inline-block text-gray-900"
					>
						{{ showToggle ? '아이별 보기' : '전체 보기' }}</span
					>
					<span
						v-else
						class="text-xl font-extrabold inline-block text-gray-900"
					>
						{{ showToggle ? '내 아이 보기' : '전체 보기' }}</span
					>
				</label>
			</div>
		</div>
		<div v-if="loginStore.isTeacher && path !== 'create'">
			<button type="button" @click="registAlbum()" class="btn">추가</button>
		</div>
	</div>
	<RouterView :loginStore="loginStore" :showToggle="showToggle" />
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const showToggle = ref(true);

let loginStore = JSON.parse(localStorage.getItem('loginStore'));

// '추가' 버튼 클릭 시 보이지 않게 하기 위해 path 확인
const path = computed(() => {
	return route.path.substr(7);
});

function registAlbum() {
	router.push({
		name: 'AlbumCreate',
	});
}

const groupBtnVisible = computed(() => {
	// URL path가 '/album/:id' 형태일 때 (':id'는 아무 숫자나 문자열이 될 수 있음)
	if (/^\/album\/.+$/.test(route.path)) {
		// 로그인한 사용자가 선생님이면 false, 그렇지 않으면 true
		return !loginStore.isTeacher;
	} else {
		// 그 외의 경우에는 항상 true
		return true;
	}
});
</script>

<style scoped></style>
