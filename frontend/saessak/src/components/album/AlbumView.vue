<template>
	<div>
		<div class="flex justify-between items-center">
			<div class="flex">
				<p>앨범 제목</p>
				<label class="relative inline-flex items-center me-5 cursor-pointer">
					<input type="checkbox" value="" class="sr-only peer" checked />
					<div
						class="w-11 h-6 bg-gray-200 rounded-full peer dark:bg-gray-700 peer-focus:ring-4 peer-focus:ring-green-300 dark:peer-focus:ring-green-800 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-green-600"
					></div>
					<span
						class="ms-3 text-sm font-medium text-gray-900 dark:text-gray-300"
						>전체 보기</span
					>
				</label>
			</div>
			<div>
				<button
					@click="download"
					type="button"
					class="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					다운로드
				</button>
			</div>
		</div>
		<div class="flex flex-wrap justify-around">
			<div v-for="album in albumList" :key="album.id" class="my-2">
				<input
					type="checkbox"
					:id="album.id"
					:value="`${album.id}`"
					class="hidden peer"
					v-model="checked"
				/>
				<label
					:for="album.id"
					class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-blue-600 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
				>
					<img
						class="album rounded-t-lg"
						:src="`src/assets/${album.image}`"
						:for="album.id"
						alt="img"
					/>
				</label>
			</div>
		</div>
		<br />
		<span>Check 이미지 : {{ checked }}</span>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const checked = ref([]);

// 더미 데이터
const albumList = [
	{
		id: '사진1',
		name: '박나은',
		image: 'image1.png',
		date: '2022-01-02',
		checked: false,
	},
	{
		id: '사진2',
		name: '박나은',
		image: 'image2.png',
		date: '2022-02-07',
		checked: false,
	},
	{
		id: '사진3',
		name: '박나은',
		image: 'image3.png',
		date: '2022-03-02',
		checked: false,
	},
	{
		id: '사진4',
		name: '박건후',
		image: 'image4.png',
		date: '2022-03-11',
		checked: false,
	},
	{
		id: '사진5',
		name: '박나은',
		image: 'image5.png',
		date: '2022-07-02',
		checked: false,
	},
	{
		id: '사진6',
		name: '박건후',
		image: 'image6.png',
		date: '2022-08-04',
		checked: false,
	},
	{
		id: '사진7',
		name: '박건후',
		image: 'image7.png',
		date: '2022-09-16',
		checked: false,
	},
	{
		id: '사진8',
		name: '박건후',
		image: 'image8.png',
		date: '2022-11-25',
		checked: false,
	},
	{
		id: '사진9',
		name: '박건후',
		image: 'image9.png',
		date: '2022-12-08',
		checked: false,
	},
]; // 더미 데이터 끝

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

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
