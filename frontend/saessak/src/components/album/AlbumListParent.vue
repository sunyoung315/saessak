<template>
	<div>
		<!-- 내 아이 보기 -->
		<div v-if="props.showToggle">
			<div
				v-for="album in currentPageAlbums"
				:key="album.albumId"
				class="border rounded-md shadow bg-gray-50 mb-4 p-4 pb-2"
			>
				<div
					class="flex justify-start items-center"
					v-if="album.fileResponseDtoList.length != 0"
				>
					<button
						disabled
						class="bg-nav-green m-4 text-black font-bold py-2 px-4 rounded-full"
					>
						<p>{{ album.albumDate }}</p>
					</button>
					<p class="text-2xl font-bold ml-3">{{ album.albumTitle }}</p>
				</div>
				<div v-if="album.fileResponseDtoList.length != 0">
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<Carousel :items-to-show="5" :wrap-around="false" snapAlign="start">
						<Slide v-for="file in album.fileResponseDtoList" :key="file.fileId">
							<div class="carousel__item">
								<img
									class="album px-2"
									:src="`${file.filePath}`"
									:for="album.fileResponseDtoList.fileId"
									alt="image"
								/>
							</div>
						</Slide>
						<template #addons>
							<Navigation />
						</template>
					</Carousel>
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<div class="flex justify-end items-center">
						<button
							@click="goDetail(kidId, album.albumDate)"
							class="my-2 mx-1 px-4 py-1 text-xl font-bold rounded-md hover:bg-gray-200 pointer-cursor"
						>
							→ 상세 조회
						</button>
					</div>
				</div>
			</div>
			<div v-if="!kidAlbumList.length" class="m-6">등록된 앨범이 없습니다.</div>
		</div>
		<!-- 반 전체 보기 -->
		<div v-else>
			<div
				v-for="album in currentPageAlbums"
				:key="album.albumId"
				class="border rounded-md shadow bg-gray-50 mb-4 p-4 pb-2"
			>
				<div
					class="flex justify-start items-center"
					v-if="album.fileResponseDtoList.length != 0"
				>
					<button
						disabled
						class="bg-nav-green m-4 text-black font-bold py-2 px-4 rounded-full"
					>
						<p>{{ album.albumDate }}</p>
					</button>
					<p class="text-2xl font-bold ml-3">{{ album.albumTitle }}</p>
				</div>
				<div v-if="album.fileResponseDtoList.length != 0">
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<Carousel :items-to-show="5" :wrap-around="false" snapAlign="start">
						<Slide v-for="file in album.fileResponseDtoList" :key="file.fileId">
							<div class="carousel__item">
								<img
									class="album px-2"
									:src="`${file.filePath}`"
									:for="album.fileResponseDtoList.fileId"
									alt="image"
								/>
							</div>
						</Slide>
						<template #addons>
							<Navigation />
						</template>
					</Carousel>
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<div class="flex justify-end items-center">
						<div
							@click="goDetail(kidId, album.albumDate)"
							class="my-2 mx-1 px-4 py-1 text-xl font-bold rounded-md hover:bg-gray-200 pointer-cursor"
						>
							→ 상세 조회
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="flex justify-center font-bold" v-if="kidAlbumList.length">
			<button @click="movePage(currentPage - 1)" :disabled="currentPage === 0">
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="7"
					height="12"
					viewBox="0 0 7 12"
					fill="none"
				>
					<path
						fill-rule="evenodd"
						clip-rule="evenodd"
						d="M6.70711 0.292893C6.34662 -0.0675907 5.77939 -0.0953203 5.3871 0.209705L5.29289 0.292893L0.292893 5.29289C-0.0675907 5.65338 -0.0953203 6.22061 0.209705 6.6129L0.292893 6.70711L5.29289 11.7071C5.68342 12.0976 6.31658 12.0976 6.70711 11.7071C7.06759 11.3466 7.09532 10.7794 6.7903 10.3871L6.70711 10.2929L2.415 6L6.70711 1.70711C7.06759 1.34662 7.09532 0.779392 6.7903 0.387101L6.70711 0.292893Z"
						:fill="currentPage === 0 ? '#cccccc' : '#000000'"
					/>
				</svg>
			</button>
			<button
				v-for="(page, index) in totalPage"
				:key="index"
				@click="movePage(index)"
				class="rounded p-2 text-base px-3 mx-2"
				:class="{ 'bg-nav-green bg-opacity-50': currentPage === index }"
			>
				{{ index + 1 }}
			</button>
			<button
				@click="movePage(currentPage + 1)"
				:disabled="currentPage === totalPage - 1"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="7"
					height="12"
					viewBox="0 0 7 12"
					fill="none"
				>
					<path
						d="M0.292893 0.292893C0.653377 -0.0675907 1.22061 -0.0953203 1.6129 0.209705L1.70711 0.292893L6.70711 5.29289C7.06759 5.65338 7.09532 6.22061 6.7903 6.6129L6.70711 6.70711L1.70711 11.7071C1.31658 12.0976 0.683418 12.0976 0.292893 11.7071C-0.0675907 11.3466 -0.0953203 10.7794 0.209705 10.3871L0.292893 10.2929L4.585 6L0.292893 1.70711C-0.0675907 1.34662 -0.0953203 0.779392 0.209705 0.387101L0.292893 0.292893Z"
						:fill="currentPage === totalPage - 1 ? '#cccccc' : '#000000'"
					/>
				</svg>
			</button>
		</div>
	</div>
</template>

<script setup>
import { ref, defineComponent, onMounted, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import { useAlbumStore } from '@/store/album';

const router = useRouter();
const albumStore = useAlbumStore();

const props = defineProps({
	loginStore: Object,
	showToggle: Boolean,
});

// 내 아이
let kidId = props.loginStore.kidList[0].kidId;

// 반 전체 앨범 조회
const albumParentList = ref([]);
const albumParentListDivided = ref([]);
const getAlbumParentList = async () => {
	await albumStore.getAlbumParentList(kidId);
	albumParentList.value = albumStore.albumParentList;
	// 날짜순으로 정렬
	albumParentList.value.sort(
		(a, b) => new Date(b.albumDate) - new Date(a.albumDate),
	);
	// albumDate를 5개씩 데이터로 나눠서 담기
	albumParentListDivided.value = divideAlbumsByFive(albumParentList.value);
};

// 내 아이 앨범 조회, 번호: kidId
const kidAlbumList = ref([]);
const kidAlbumListDivided = ref([]);
const getKidAlbumList = async () => {
	await albumStore.getKidAlbumList(kidId);
	kidAlbumList.value = albumStore.kidAlbumList;
	// 날짜순으로 정렬
	kidAlbumList.value.sort(
		(a, b) => new Date(b.albumDate) - new Date(a.albumDate),
	);
	// albumDate를 5개씩 데이터로 나눠서 담기
	kidAlbumListDivided.value = divideAlbumsByFive(kidAlbumList.value);
};

///////////////////////////////////////////////// Pagination
// 데이터 5개씩 분리
function divideAlbumsByFive(albums) {
	let divided = [];
	let filteredAlbums = albums.filter(
		album => album.fileResponseDtoList.length > 0,
	);

	for (let i = 0; i < filteredAlbums.length; i += 5) {
		divided.push(filteredAlbums.slice(i, i + 5));
	}

	return divided;
}
// 현재 페이지 기록
const currentPage = ref(0);

// 페이지 변화 체크
watch(
	() => props.showToggle,
	(newVal, oldVal) => {
		if (newVal !== oldVal) {
			currentPage.value = 0;
		}
	},
);

const currentPageAlbums = computed(() => {
	// 현재 페이지에 맞는 앨범 데이터 반환
	return props.showToggle
		? kidAlbumListDivided.value[currentPage.value]
		: albumParentListDivided.value[currentPage.value];
});

const totalPage = computed(() => {
	// 전체 페이지 개수 계산
	return props.showToggle
		? Math.ceil(kidAlbumListDivided.value.length)
		: Math.ceil(albumParentListDivided.value.length);
});

// 페이지 이동 함수
function movePage(num) {
	if (num < 0 || num >= totalPage.value) {
		return;
	}
	currentPage.value = num;
}
///////////////////////////////////////////////////

onMounted(async () => {
	await getAlbumParentList(kidId);
	await getKidAlbumList(kidId);
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

// Btn
function goDetail(kidId, albumDate) {
	router.push({
		name: 'AlbumDetailParent',
		params: { id: kidId },
		query: { date: albumDate },
		state: { showToggle: props.showToggle },
	});
}
// Btn
</script>

<style scoped>
.album {
	width: 250px;
	height: 200px;
}
</style>
