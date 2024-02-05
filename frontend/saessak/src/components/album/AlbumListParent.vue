<template>
	<div>
		<div class="ml-6">
			<label class="relative inline-flex items-center me-5 cursor-pointer">
				<input
					type="checkbox"
					class="sr-only peer"
					checked
					v-model="showToggle"
				/>
				<div
					class="w-12 h-7 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-nav-green peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-6 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-nav-green"
				></div>
				<span
					class="text-xl m-5 font-extrabold inline-block text-gray-900 dark:text-gray-300"
				>
					{{ showToggle ? '전체 보기' : '내 아이 보기' }}</span
				>
			</label>
		</div>
		<!-- 반 전체 보기 -->
		<div v-if="showToggle">
			<div v-for="album in currentPageAlbums" :key="album.albumId">
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
					<p class="text-2xl font-bold ml-4">{{ album.albumTitle }}</p>
				</div>
				<div v-if="album.fileResponseDtoList.length != 0">
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<Carousel :items-to-show="5" :wrap-around="false">
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
					<div
						@click="goDetail(kidId, album.albumDate)"
						class="m-4 text-xl font-bold flex justify-end cursor-pointer"
					>
						→ 전체 조회
					</div>
				</div>
			</div>
		</div>
		<!-- 내 아이만 보기 -->
		<div v-else>
			<div v-for="album in currentPageAlbums" :key="album.albumId">
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
					<p class="text-2xl font-bold ml-4">{{ album.albumTitle }}</p>
				</div>
				<div v-if="album.fileResponseDtoList.length != 0">
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<Carousel :items-to-show="5" :wrap-around="false">
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
					<div
						@click="goDetail(kidId, album.albumDate)"
						class="m-4 text-xl font-bold flex justify-end cursor-pointer"
					>
						→ 전체 조회
					</div>
				</div>
			</div>
		</div>
		<div class="flex justify-center text-2xl font-bold">
			<button
				@click="movePage(currentPage - 1)"
				:disabled="currentPage === 0"
				:class="{ 'text-gray-200': currentPage === 0 }"
			>
				←
			</button>

			<button
				v-for="(page, index) in totalPage"
				:key="index"
				@click="movePage(index)"
				class="m-2 rounded-lg p-2"
				:class="{ 'bg-nav-green': currentPage === index }"
			>
				{{ index + 1 }}
			</button>
			<button
				@click="movePage(currentPage + 1)"
				:disabled="currentPage === totalPage - 1"
				:class="{ 'text-gray-200': currentPage === totalPage - 1 }"
			>
				→
			</button>
		</div>
	</div>
</template>

<script setup>
import { ref, defineComponent, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import { useAlbumStore } from '@/store/album';

const router = useRouter();
const albumStore = useAlbumStore();
const showToggle = ref(true);

let loginStore = JSON.parse(localStorage.getItem('loginStore'));
// 내 아이
let kidId = loginStore.kidList[0].kidId;

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

///////////////////////////////////////////////// Pagination
const currentPage = ref(0); // 현재 페이지 기록
const currentPageAlbums = computed(() => {
	// 현재 페이지에 맞는 앨범 데이터 반환
	return showToggle.value
		? albumParentListDivided.value[currentPage.value]
		: kidAlbumListDivided.value[currentPage.value];
});

const totalPage = computed(() => {
	// 전체 페이지 개수 계산
	return showToggle.value
		? Math.ceil(albumParentListDivided.value.length)
		: Math.ceil(kidAlbumListDivided.value.length);
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
	// console.log('goDetail: ' + albumDate);
	router.push({
		name: 'AlbumDetailParent',
		params: { id: kidId },
		query: { date: albumDate },
	});
}
// Btn
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
