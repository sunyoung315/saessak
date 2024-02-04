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
				<div
					v-if="
						kid.albumResponseDto &&
						kid.albumResponseDto.fileResponseDtoList.length > 0
					"
				>
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
							disabled
						>
							{{ kid.kidName }}
						</button>

						<button @click="goDetail(kid.kidId)" class="m-4 text-xl font-bold">
							→ 전체 조회
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Card 형식 : 전체 아이들 보기 -->
		<div v-else class="border p-4 rounded-lg">
			<div class="block mb-5">
				<div class="block mt-1 mb-10">
					<VDatePicker
						v-model="date"
						:select-attribute="selectAttribute"
						:disabled-dates="disabledDates"
					>
						<template #default="{ inputValue, inputEvents }">
							<div class="relative max-w-sm">
								<div
									class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
								>
									<svg
										class="w-4 h-4 text-gray-900"
										aria-hidden="true"
										xmlns="http://www.w3.org/2000/svg"
										fill="currentColor"
										viewBox="0 0 20 20"
									>
										<path
											d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"
										/>
									</svg>
								</div>
								<input
									:value="inputValue"
									v-on="inputEvents"
									class="datepicker-input text"
								/>
							</div>
						</template>
					</VDatePicker>
				</div>
			</div>
			<div v-for="album in albumTeacherList" :key="album.albumId">
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

// 반 아이들 최신 앨범 리스트 조회 (Carousel)
const recentAlbumList = ref([]);
const getRecentAlbumList = async () => {
	await albumStore.getRecentAlbumList();
	recentAlbumList.value = albumStore.recentAlbumList;
};

const albumTeacherList = ref([]);
const getAlbumTeacherList = async () => {
	await albumStore.getAlbumTeacherList();
	albumTeacherList.value = albumStore.albumTeacherList;
};

onMounted(async () => {
	await getRecentAlbumList();
	await getAlbumTeacherList();
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
// 색상
const selectAttribute = ref({ highlight: 'green' });
const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);
const disabledDates = ref([
	{
		start: tomorrow,
		end: null,
	},
]);
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
