<template>
	<div
		class="container ml-12 mr-16 w-11/12 border border-gray-200 shadow rounded-lg"
	>
		<div class="flex justify-end items-center">
			<button
				type="button"
				@click="registAlbum()"
				class="mt-8 mr-6 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
			>
				등록
			</button>
			<button
				type="button"
				@click="goBack()"
				class="mt-8 mr-8 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
			>
				목록
			</button>
		</div>
		<div>
			<label class="block mt-2 mb-5">
				<span class="text-gray-700 ml-36 text-xl font-bold">제목</span>
				<input
					type="text"
					class="block mt-1 ml-32 mb-10 w-5/12 rounded-md border border-neutral-300 shadow focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
					rows="6"
					placeholder="제목을 입력해주세요."
				/>
			</label>
		</div>
		<span class="text-gray-700 ml-36 text-xl font-bold">날짜</span>
		<div
			class="block ml-32 mb-0 mt-1 bg-white w-3/12 border border-neutral-300 rounded-lg shadow"
		>
			<!-- DatePicker 시작-->
			<div class="flex jucenterstify- items-center">
				<VDatePicker v-model="date">
					<template #default="{ inputValue, togglePopover }">
						<input class="px-3 py-2" :value="inputValue" />
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
			</div>
			<!-- DatePicker 끝-->
		</div>
		<div>
			<span class="text-gray-700 ml-36 text-xl font-bold">첨부파일</span>
			<div class="w-32 ml-36 h-32 border-2 border-dotted border-blue-500 mb-4">
				<div v-if="images" class="w-full h-full flex items-center">
					<img :src="images" alt="image" />
				</div>
				<div
					v-else
					class="w-full h-full flex items-center justify-center cursor-pointer hover:bg-pink-100"
					@click="clickInputTag()"
				>
					<input
						ref="image"
						id="input"
						type="file"
						name="image"
						accept="image/*"
						multiple="multiple"
						class="hidden"
						@change="uploadImage()"
					/>
					<svg
						class="w-8 h-8"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						stroke="currentColor"
					>
						<path
							stroke-linecap="round"
							stroke-linejoin="round"
							stroke-width="2"
							d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"
						/>
					</svg>
				</div>
			</div>
		</div>
		<div></div>
	</div>
</template>

<script setup>
import router from '@/router';
import axios from 'axios';
import { ref, onMounted } from 'vue';

// 이미지 업로드
let images = ref('');
const imageRef = ref(null);

const uploadImage = () => {
	let form = new FormData();
	let image = imageRef.value.files[0];

	form.append('image', image);

	// post 경로 변경 필요함.
	axios
		.post('/upload', form, {
			headers: { 'Content-Type': 'multipart/form-data' },
		})
		.then(({ data }) => {
			images.value = data;
		})
		.catch(err => console.log(err));
};

const clickInputTag = () => {
	if (imageRef.value) {
		imageRef.value.click();
	}
};

const date = ref(new Date());

onMounted(() => {
	// DOM이 마운트된 후 실행될 코드
	imageRef.value = document.getElementById('input');
});
// 이미지 업로드 끝

// 버튼 기능
function registAlbum() {
	console.log('regist');
}

function goBack() {
	router.go(-1);
}
// 버튼 기능 끝
</script>

<style scoped></style>
