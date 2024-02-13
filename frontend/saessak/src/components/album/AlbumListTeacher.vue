<template>
	<div class="flex flex-col">
		<div class="flex justify-between m-2">
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
					{{ showToggle ? '아이별 보기' : '전체 보기' }}</span
				>
			</label>
			<div>
				<button
					type="button"
					@click="registAlbum()"
					class="text-white bg-gradient-to-r from-nav-green via-nav-green to-nav-green hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					추가
				</button>
			</div>
		</div>
		<!-- 아이 이름별 보기 -->
		<div v-if="showToggle">
			<div v-for="kid in recentAlbumList" :key="kid.kidId">
				<div v-if="kid.albumResponseDto.length > 0">
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<Carousel
						:items-to-show="5"
						:wrap-around="true"
						:autoplay="2000"
						v-if="kid.albumResponseDto"
					>
						<Slide
							v-for="file in kid.albumResponseDto.fileResponseDtoList"
							:key="file.fileId"
						>
							<div class="carousel__item">
								<img
									class="album px-2"
									:src="`${file.filePath}`"
									:for="kid.albumResponseDto.albumId"
									alt="image"
								/>
							</div>
						</Slide>
						<template #addons>
							<Navigation />
						</template>
					</Carousel>
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<div class="flex justify-between">
						<button
							class="bg-nav-green m-4 text-black font-bold py-2 px-4 rounded-full"
						>
							{{ kid.kidName }}
						</button>

						<span @click="goDetail(kid.kidId)" class="m-4 text-xl font-bold"
							>→ 전체 조회</span
						>
					</div>
				</div>
			</div>
		</div>
		<!-- Card 형식 : 전체 아이들 보기 -->
		<div v-else class="border p-4 rounded-lg">
			<!-- DatePicker 시작-->
			<VDatePicker v-model="date">
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
			<div v-for="album in albumAllList" :key="album.albumId">
				<div
					class="my-2 flex flex-wrap"
					v-if="
						isSameDate(album.albumDate, date) &&
						album.fileResponseDtoList.length > 0
					"
				>
					<p class="w-full text-2xl font-bold m-2">{{ album.albumTitle }}</p>
					<div
						v-for="file in album.fileResponseDtoList"
						:key="file.fileId"
						class="w-1/4 flex-shrink-0 flex flex-wrap"
					>
						<input
							type="checkbox"
							:id="file.fileId"
							:value="`${file.fileId}`"
							class="hidden peer"
						/>
						<label
							:for="file.fileId"
							class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
						>
							<img
								class="album rounded"
								:src="`${file.filePath}`"
								:for="file.fileId"
								alt="img"
							/>
						</label>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, defineComponent, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import { useAlbumStore } from '@/store/album';

const router = useRouter();
const albumStore = useAlbumStore();
const showToggle = ref(true);

const recentAlbumList = ref([]);
const albumAllList = ref([]);

onMounted(async () => {
	// 반 아이들 최신 앨범 리스트 조회 (Carousel)
	await albumStore.getRecentAlbumList;
	recentAlbumList.value = albumStore.recentAlbumList;
	// 반 전체 앨범 조회 (Card), 번호: classRoomId
	await albumStore.getAlbumAllList(1);
	albumAllList.value = albumStore.albumAllList;
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
function goDetail(kidId) {
	router.push({
		name: 'AlbumDetailTeacher',
		params: { id: kidId },
	});
}

function registAlbum() {
	router.push({
		name: 'AlbumCreate',
	});
}
// Btn 끝

// datePicker
const date = ref(new Date());
// 날짜 같은지 확인
function isSameDate(albumDate, date) {
	const albumDateObj = new Date(albumDate);
	return (
		albumDateObj.getFullYear() === date.getFullYear() &&
		albumDateObj.getMonth() === date.getMonth() &&
		albumDateObj.getDate() === date.getDate()
	);
}
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
