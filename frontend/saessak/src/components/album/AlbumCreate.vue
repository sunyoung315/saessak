<template>
	<div class="container mt-8 ml-12 mr-16 w-11/12 border border-gray-200 shadow rounded-lg">
		<div class="flex justify-end items-center">
			<button type="button" @click="registAlbum()"
				class="text-white bg-gradient-to-r m-4 from-nav-green via-nav-green to-nav-green hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">
				등록
			</button>
			<button type="button" @click="goBack()"
				class="text-white bg-gradient-to-r m-4 mr-8 from-nav-green via-nav-green to-nav-green hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">
				목록
			</button>
		</div>
		<div>
			<label class="block mt-2 mb-5">
				<span class="content-title">제목</span>
				<input type="text"
					class="block ml-32 mt-2 shadow appearance-none border rounded w-5/12 py-2 px-3 text-gray-700 leading-tight"
					rows="6" placeholder="제목을 입력해주세요." v-model="title" />
			</label>
		</div>
		<span class="content-title">날짜</span>
		<div class="block ml-32 mb-4 mt-1 bg-white w-3/12 border border-neutral-300 rounded-lg shadow">
			<!-- DatePicker 시작-->
			<div class="flex jucenterstify- items-center">
				<VDatePicker v-model="date">
					<template #default="{ inputValue, togglePopover }">
						<input class="px-3 py-2" :value="inputValue" />
						<button class="px-3 py-2" @click="togglePopover">
							<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
								<path fill-rule="evenodd" clip-rule="evenodd"
									d="M16 2C16.5523 2 17 2.44772 17 3V4H20C21.1046 4 22 4.89543 22 6V20C22 21.1046 21.1046 22 20 22H4C2.89543 22 2 21.1046 2 20V6C2 4.89543 2.89543 4 4 4H7V3C7 2.44772 7.44772 2 8 2C8.55228 2 9 2.44772 9 3V4H15V3C15 2.44772 15.4477 2 16 2ZM20 11H4V20H20V11ZM7 6H4V9H20V6H17V7C17 7.55228 16.5523 8 16 8C15.4477 8 15 7.55228 15 7V6H9V7C9 7.55228 8.55228 8 8 8C7.44772 8 7 7.55228 7 7V6Z"
									fill="#000000" />
							</svg>
						</button>
					</template>
				</VDatePicker>
			</div>
			<!-- DatePicker 끝-->
		</div>

		<span class="content-title">첨부파일 (사진, 동영상: {{ count }}EA)</span>
		<div class="flex items-center ml-32 w-full">
			<label
				class="flex flex-col justify-center w-4/5 h-64 mb-8 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
				<div class="flex flex-col items-center justify-center pt-5 pb-6" @dragover.prevent @drop="onDrop">
					<input ref="image" id="input" type="file" name="image" accept="image/*" multiple="multiple"
						class="hidden" @change="uploadImage()" />
					<svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
						xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
							d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
					</svg>
					<p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
						<span class="font-semibold">Click to upload</span> or drag and drop
					</p>
					<p class="text-xs text-gray-500 dark:text-gray-400">
						SVG, PNG, JPG or GIF (MAX. 800x400px)
					</p>
				</div>
				<hr />
				<p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
					<span class="font-semibold m-3">업로드 파일</span>
				</p>
				<div class="flex flex-wrap">
					<span class="flex-wrap flex-row m-2" v-for="(fileName, index) in uploadedFileNames" :key="index">
						{{ fileName }}
					</span>
				</div>
			</label>
		</div>
	</div>
</template>

<script setup>
import router from '@/router';
import axios from 'axios';
import { ref, onMounted } from 'vue';

const title = ref('');
const date = ref(new Date());
const formattedDate = formatDate(date.value);

// 이미지 업로드
let count = ref(0);
let uploadedFileNames = ref([]);

let form = new FormData();
let images = ref([]);
let imageRef = ref(null);

const uploadImage = () => {
	let imageFiles = imageRef.value.files;

	// 업로드 파일명 출력 및 카운트
	count.value += imageFiles.length; // 업로드된 파일 수만큼 카운트 증가

	for (let i = 0; i < imageFiles.length; i++) {
		// console.log('업로드 파일명:', imageFiles[i].name);
		form.append('images', imageFiles[i]); // 각 파일을 폼에 추가
		images.value.append = imageFiles[i];
		uploadedFileNames.value.push(imageFiles[i].name); // 업로드한 파일 이름 저장
	}
};

onMounted(() => {
	imageRef.value = document.getElementById('input');
});
// 이미지 업로드 끝

// 버튼 기능
function registAlbum() {
	// console.log('제목: ' + title.value);
	// console.log('날짜: ' + formattedDate);
	// 앨범 정보 추가
	form.append('albumTitle', title.value);
	form.append('albumDate', formattedDate);
	form.append('images', images.value);

	// console.log(form);
	const token = sessionStorage.getItem('accessToken');

	// post 경로 변경 필요함.
	axios
		.post(`https://i10a706.p.ssafy.io/ai/album`, form, {
			headers: {
				'Content-Type': 'multipart/form-data',
				Authorization: 'Bearer ' + token,
			},
		})
		.then(response => {
			console.log(response);
		})
		.catch(err => console.log(err));

	// 앨범 생성 후 목록 돌아가기
	router.push({
		name: 'AlbumList',
	});
}

function goBack() {
	router.go(-1);
}

const onDrop = event => {
	event.preventDefault();
	imageRef.value.files = event.dataTransfer.files;
	uploadImage();
};
// 버튼 기능 끝

// 날짜 포맷 변경
function formatDate(date) {
	const year = date.getFullYear();
	const month = ('0' + (date.getMonth() + 1)).slice(-2);
	const day = ('0' + date.getDate()).slice(-2);
	return `${year}-${month}-${day}`;
}
</script>

<style scoped>
.content-title {
	@apply ml-36 text-gray-900 text-xl font-bold;
}
</style>
