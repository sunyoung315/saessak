<template>
	<div class="view-frame p-4">
		<div class="flex justify-end items-center">
			<button type="button" @click="registAlbum()" class="btn">등록</button>
			<button type="button" @click="goBack()" class="btn">목록</button>
		</div>

		<span class="content-title">날짜</span>
		<div class="ml-32 mb-4">
			<!-- DatePicker 시작-->
			<VDatePicker v-model="date" :select-attribute="selectAttribute">
				<template #default="{ inputValue, inputEvents }">
					<div class="relative max-w-sm">
						<div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
							<svg class="w-4 h-4 text-gray-900" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
								fill="currentColor" viewBox="0 0 20 20">
								<path
									d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
							</svg>
						</div>
						<input :value="inputValue" v-on="inputEvents" class="datepicker-input text" />
					</div>
				</template>
			</VDatePicker>
			<!-- DatePicker 끝-->
		</div>
		<div>
			<label class="block mt-2 mb-5 w-full">
				<span class="content-title">제목</span>
				<input type="text" class="content-box mb-8 p-2 text-lg" rows="6" placeholder="제목을 입력해주세요." v-model="title"
					@input="emptyTitle = false" :class="{
						'!border-2 !border-red-500': emptyTitle,
						shake: shakeTitle,
					}" required />
			</label>
		</div>

		<span class="content-title">첨부파일 (사진, 동영상: {{ count }}EA)</span>
		<div class="flex items-center ml-32 w-full">
			<label
				class="flex flex-col justify-center w-3/4 h-64 mb-8 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
				<div class="flex flex-col items-center justify-center pt-5 pb-6" @dragover.prevent @drop="onDrop">
					<input ref="image" id="input" type="file" name="image" accept="image/*" multiple="multiple"
						class="hidden" @change="uploadImage()" required />
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
import Swal from 'sweetalert2'

const title = ref('');
const date = ref(new Date());
const formattedDate = formatDate(date.value);
const selectAttribute = ref({ highlight: 'green' });

// 이미지 업로드
let count = ref(0);
let uploadedFileNames = ref([]);

// 파일, 이미지, 링크 변수 지정
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
	if (checkEmptyFields()) {
		// 빈 필드가 있다면 등록 중단
		return;
	}

	if (count.value === 0) {
		Swal.fire({
			icon: 'warning',
			title: '앨범 이미지를 업로드해주세요!',
			confirmButtonText: '확인'
		})
		return;
	}

	// 앨범 정보 추가
	form.append('albumTitle', title.value);
	form.append('albumDate', formattedDate);
	form.append('images', images.value);

	// console.log(form);
	const token = localStorage.getItem('accessToken');

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

////////////////////// Null 값 처리
const emptyTitle = ref(false);
const shakeTitle = ref(false);

const checkEmptyFields = () => {
	let hasEmptyFields = false;
	if (!title.value.trim()) {
		emptyTitle.value = true;
		shakeTitle.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeTitle.value = false;
		}, 1000);
	}

	return hasEmptyFields;
};
///////////////////////
</script>

<style scoped>
.content-title {
	@apply ml-36 text-gray-900 text-xl font-bold;
}

.content-box {
	@apply block ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
}

@keyframes shake {
	0% {
		transform: translateX(0px);
	}

	25% {
		transform: translateX(-2px);
	}

	50% {
		transform: translateX(0px);
	}

	75% {
		transform: translateX(2px);
	}

	100% {
		transform: translateX(0px);
	}
}

.shake {
	animation: shake 0.2s;
	animation-iteration-count: 3;
}
</style>
