<template>
	<div>
		<div class="flex justify-between items-center">
			<span class="text-4xl m-5 font-extrabold inline-block"
				><RouterLink to="/album">앨범 제목</RouterLink></span
			>
			<label class="relative inline-flex items-center me-5 cursor-pointer">
				<input type="checkbox" class="sr-only peer" checked />
				<div
					class="w-11 h-6 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-green-300 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-green-600"
				></div>
				<span class="ms-3 text-sm font-medium text-gray-900 dark:text-gray-300"
					>전체 보기</span
				>
			</label>
			<!-- DatePicker 시작-->
			<VDatePicker v-model="date">
				<template #default="{ togglePopover }">
					<button
						class="px-3 py-2 bg-green-300 text-sm text-white font-semibold rounded-md"
						@click="togglePopover"
					>
						Select date
					</button>
				</template>
			</VDatePicker>
			<!-- DatePicker 끝-->
			<div>
				<button
					@click="download"
					type="button"
					class="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					다운로드
				</button>
				<button class="addBtn" @click="addAlbum">추가</button>
			</div>
		</div>
		<!-- <div v-if="user === 'teacher'">
			<AlbumTeacherView />
		</div>
		<div v-else>
			<AlbumParentsView />
		</div> -->
		<!-- <AlbumCardList /> -->
		<!-- <AlbumCarouselList /> -->
		<!-- <AlbumCreate /> -->
		<RouterView />
	</div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
// import AlbumParentsView from '@/components/album/AlbumParentsView.vue';
// import AlbumTeacherView from '@/components/album/AlbumTeacherView.vue';

// import AlbumCardList from '@/components/album/albumItems/AlbumCardList.vue';
// import AlbumCarouselList from '@/components/album/albumItems/AlbumCarouselList.vue';
// import AlbumCreate from '@/components/album/albumItems/AlbumCreate.vue';

const router = useRouter();

// user
// const user = 'teacher';

// 추가 버튼
const addAlbum = () => {
	router.push({ name: 'AlbumCreate' });
};

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

// DatePicker 시작
const date = ref(new Date());
// DatePicker 끝
</script>

<style scoped></style>
