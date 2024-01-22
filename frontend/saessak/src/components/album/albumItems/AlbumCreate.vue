<template>
	<div>
		<div class="container mx-auto px-4 py-4">
			<div class="flex justify-end">
				<button>등록</button>
				<button>목록 돌아가기</button>
			</div>
			<div class="flex flex-col">
				<h1>제목</h1>
				<input type="text" name="date" id="id" placeholder="제목을 입력해주세요." />
				<h1>날짜</h1>
				<!-- DatePicker 시작-->
				<VDatePicker v-model="date">
					<template #default="{ togglePopover }">
						<button class="px-3 py-2 bg-green-300 text-sm text-white font-semibold rounded-md" @click="togglePopover">
							Select date
						</button>
					</template>
				</VDatePicker>
				<!-- DatePicker 끝-->
				<h1>첨부파일</h1>
				<div>
					<h1>첨부파일 박스</h1>
					<div class="w-32 h-32 border-2 border-dotted border-blue-500">
						<div v-if="images" class="w-full h-full flex items-center">
							<img :src="images" alt="image">
						</div>
						<div v-else class="w-full h-full flex items-center justify-center cursor-pointer hover:bg-pink-100"
							@click="clickInputTag()">
							<input ref="image" id="input" type="file" name="image" accept="image/*" multiple="multiple" class="hidden"
								@change="uploadImage()">
							<svg class="w-8 h-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
								stroke="currentColor">
								<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
									d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
							</svg>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue';

let images = ref('');
const imageRef = ref(null);

const uploadImage = () => {
  let form = new FormData();
  let image = imageRef.value.files[0];

  form.append('image', image);

	// post 경로 변경 필요함.
  axios.post('/upload', form, {
    headers: { 'Content-Type': 'multipart/form-data' }  
  }).then(({ data }) => {
    images.value = data;
  }).catch(err => console.log(err));
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
</script>

<style scoped></style>
