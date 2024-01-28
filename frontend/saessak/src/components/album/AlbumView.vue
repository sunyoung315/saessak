<template>
	<div class="container p-6 flex items-center">
		<div class="flex justify-between">
			<div class="flex justify-start">
				<span class="text-4xl m-5 font-extrabold inline-block"
					><RouterLink to="/album">앨범</RouterLink></span
				>
				<label class="relative inline-flex items-center me-5 cursor-pointer">
					<input
						type="checkbox"
						class="sr-only peer"
						checked
						v-model="showStatus"
					/>
					<div
						class="w-11 h-6 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-dark-navy peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-8 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-dark-navy"
					></div>
					<span
						class="text-xl m-5 font-extrabold inline-block text-gray-900 dark:text-gray-300"
					>
						{{ showStatus ? '전체 보기' : '아이별 보기' }}</span
					>
				</label>
				<!-- DatePicker 시작-->
				<VDatePicker v-model="date" v-if="showStatus">
					<template #default="{ inputValue, togglePopover }">
						<input class="px-3 py-2 mt-6 mr-6 border" :value="inputValue" />
						<button class="px-3 py-2" @click="togglePopover">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width="24"
								height="24"
								viewBox="0 0 24 24"
								fill="none"
							>
								<path
									fill-rule="evenodd"
									clip-rule="evenodd"
									d="M16 2C16.5523 2 17 2.44772 17 3V4H20C21.1046 4 22 4.89543 22 6V20C22 21.1046 21.1046 22 20 22H4C2.89543 22 2 21.1046 2 20V6C2 4.89543 2.89543 4 4 4H7V3C7 2.44772 7.44772 2 8 2C8.55228 2 9 2.44772 9 3V4H15V3C15 2.44772 15.4477 2 16 2ZM20 11H4V20H20V11ZM7 6H4V9H20V6H17V7C17 7.55228 16.5523 8 16 8C15.4477 8 15 7.55228 15 7V6H9V7C9 7.55228 8.55228 8 8 8C7.44772 8 7 7.55228 7 7V6Z"
									fill="#000000"
								/>
							</svg>
						</button>
					</template>
				</VDatePicker>
				<!-- DatePicker 끝-->
			</div>
			<div class="flex justify-end">
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
	</div>
	<RouterView :showStatus="showStatus" :isTeacher="isTeacher" />
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

// showStatus: true = all, false = 아이별
const showStatus = ref(false);
const isTeacher = ref(true);

// datePicker
const date = ref(new Date());

// Btn
function registAlbum() {
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
