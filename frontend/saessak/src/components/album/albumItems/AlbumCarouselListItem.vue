<template>
	<div>
		<!-- 선생님 Carousel : 아이별 조회이지만 현재 더미 데이터가 따로 없어서 s3에 있는 전체 리스트 가져옴-->
		<div v-if="isTeacher">
			<div v-for="album in AlbumAllList" :key="album.albumId">
				<img src="@/assets/film.png" alt="필름" />
				<Carousel :items-to-show="5" :wrap-around="true">
					<Slide
						v-for="file in album.fileResponseDtoList"
						:key="file.fileId"
						@click="goDetail"
					>
						<div class="carousel__item">
							<img
								class="album"
								:src="`${file.filePath}`"
								:for="album.albumId"
								alt="image"
							/>
						</div>
					</Slide>
					<template #addons>
						<Navigation />
					</template>
				</Carousel>
				<img src="@/assets/film.png" alt="필름" />
				<div class="flex justify-between">
					<span class="m-4">{{ album.albumId }}</span>
					<span class="m-4">→ 전체 조회</span>
				</div>
			</div>
		</div>
		<!-- 부모님별 Carousel : 날짜별-->
		<div v-else>
			<div v-for="album in kidAlbumDummyList" :key="album.albumId">
				<Carousel :items-to-show="5" :wrap-around="true">
					<Slide
						v-for="file in album.fileResponseDtoList"
						:key="file.fileId"
						@click="goDetail"
					>
						<div class="carousel__item">
							<img
								class="album"
								:src="`src/assets/${file.image}`"
								:for="album.albumId"
								alt="image"
							/>
						</div>
					</Slide>
					<template #addons>
						<Navigation />
					</template>
				</Carousel>
				<div class="flex justify-between">
					<span>{{ album.albumId }}</span>
					<span>전체 조회</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, defineComponent, defineProps, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import { useAlbumStore } from '@/store/album';

// user
const router = useRouter();
const isTeacher = ref(true);

const albumStore = useAlbumStore();

const kidAlbumList = ref([]);
const AlbumAllList = ref([]);

onMounted(async () => {
	// 아이별 조회 // kidId: 번호
	await albumStore.getkidAlbumList(1);
	kidAlbumList.value = albumStore.kidAlbumList;
	// 반 전체 조회 // classroom 번호
	await albumStore.getAlbumAllList(1);
	AlbumAllList.value = albumStore.AlbumAllList;
});

const props = defineProps({
	kid: {
		type: String,
	},
});

// carousel 시작
import 'vue3-carousel/dist/carousel.css';
defineComponent({
	name: 'WrapAround',
	components: {
		Carousel,
		Slide,
		Navigation,
	},
});
// carousel 끝

// 아이별 앨범 조회 (선생님)
const kidAlbumDummyList = [
	{
		albumId: 333333,
		albumDate: '2024-11-11',
		albumTitle: '1월 생일자 사진',
		fileResponseDtoList: [
			{
				fileId: 21,
				fileName: '1사진',
				filePath: 'http://asda',
				image: 'image1.png',
			},
			{
				fileId: 22,
				fileName: '2사진',
				filePath: 'http://asda',
				image: 'image2.png',
			},
			{
				fileId: 23,
				fileName: '3사진',
				filePath: 'http://asda',
				image: 'image3.png',
			},
		],
	},
	{
		albumId: 44444444444444,
		albumDate: '2024-11-11',
		albumTitle: '2월 생일자 사진',
		fileResponseDtoList: [
			{
				fileId: 24,
				fileName: '4사진',
				filePath: 'http://asda',
				image: 'image4.png',
			},
			{
				fileId: 25,
				fileName: '5사진',
				filePath: 'http://asda',
				image: 'image5.png',
			},
		],
	},
];

function goDetail() {
	console.log('해당 아이의 상세 페이지로 이동');
	router.push({
		name: 'AlbumDetail',
	});
}
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
