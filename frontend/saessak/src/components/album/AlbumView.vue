<template>
	<div class="container p-6 flex items-center">
		<div class="flex justify-between">
			<div class="flex justify-start">
				<span class="text-4xl m-5 font-extrabold inline-block"
					><RouterLink to="/album">앨범</RouterLink></span
				>
				<label
					v-if="showToggle"
					class="relative inline-flex items-center me-5 cursor-pointer"
				>
					<input
						type="checkbox"
						class="sr-only peer"
						checked
						v-model="showStatus"
					/>
					<div
						class="w-11 h-6 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-dark-navy peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-8 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-4 after:w-5 after:transition-all peer-checked:bg-dark-navy"
					></div>
					<span
						class="text-xl m-5 font-extrabold inline-block text-gray-900 dark:text-gray-300"
					>
						{{ showStatus ? '전체 보기' : '아이별 보기' }}</span
					>
				</label>
			</div>
		</div>
		<div class="flex justify-end ml-auto">
			<div v-if="isTeacher">
				<button
					type="button"
					@click="registAlbum()"
					class="mt-6 mr-6 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-3 py-1.5 text-center me-2 mb-2"
				>
					추가
				</button>
			</div>
			<div v-else>
				<button
					@click="download"
					type="button"
					class="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					다운로드
				</button>
			</div>
		</div>
	</div>
	<RouterView
		:showStatus="showStatus"
		:isTeacher="isTeacher"
		:showToggle="showToggle"
	/>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

// showStatus: true = all, false = 아이별
const showStatus = ref(false);
const showToggle = ref(true);
const isTeacher = ref(true);

// datePicker
// const date = ref(new Date());

// Btn
function registAlbum() {
	showToggle.value = false;
	router.push({
		name: 'AlbumCreate',
	});
}
// Btn

// File Download 시작
const download = async () => {
	// fileUrl을 나중에 s3에 있는 주소를 맞춰서 가져올 수 있도록 변경
	const fileUrl =
		'https://saessack-photo-album.s3.ap-northeast-2.amazonaws.com/test.png';
	const res = await axios({
		method: 'get',
		url: fileUrl,
		responseType: 'blob',
	});
	const newUrl = window.URL.createObjectURL(res.data);
	const a = document.createElement('a');
	a.href = newUrl;
	// 아래 a.download = 저장되는 이미지 파일 이름
	a.download = 'image.png';
	a.click();
	a.remove();
	window.URL.revokeObjectURL(newUrl);
}; // File Download 끝
</script>

<style scoped></style>
