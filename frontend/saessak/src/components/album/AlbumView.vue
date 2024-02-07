<template>
	<div class="view-header">
		<div class="flex justify-between">
			<div>
				<span class="nav-title">앨범</span>
				<div class="group-btn">
					<label class="relative inline-flex items-center me-5 cursor-pointer">
						<input
							type="checkbox"
							class="sr-only peer"
							checked
							v-model="showToggle"
						/>
						<div
							class="w-12 h-7 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-nav-green peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-6 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-nav-green"
						></div>
						<span
							v-if="loginStore.isTeacher"
							class="text-xl m-5 font-extrabold inline-block text-gray-900 dark:text-gray-300"
						>
							{{ showToggle ? '아이별 보기' : '전체 보기' }}</span
						>
						<span
							v-else
							class="text-xl m-5 font-extrabold inline-block text-gray-900 dark:text-gray-300"
						>
							{{ showToggle ? '전체 보기' : '내 아이 보기' }}</span
						>
					</label>
				</div>
			</div>
			<div v-if="loginStore.isTeacher">
				<button type="button" @click="registAlbum()" class="btn">추가</button>
			</div>
		</div>
		<RouterView :loginStore="loginStore" :showToggle="showToggle" />
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const showToggle = ref(true);

let loginStore = JSON.parse(localStorage.getItem('loginStore'));

function registAlbum() {
	router.push({
		name: 'AlbumCreate',
	});
}
</script>

<style scoped></style>
