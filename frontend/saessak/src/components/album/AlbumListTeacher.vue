<template>
	<div class="flex flex-col">
		<!-- Card 형식 : 전체 아이들 보기 -->
		<div v-if="!props.showToggle">
			<div v-for="kid in recentAlbumList" :key="kid.kidId">
				<div
					v-if="
						kid.albumResponseDto &&
						kid.albumResponseDto.fileResponseDtoList.length > 0
					"
					class="border rounded-md shadow bg-gray-50 mb-4 p-4 pb-2"
				>
					<img class="px-2" src="@/assets/film.png" alt="필름" />
					<Carousel
						:items-to-show="5"
						:wrap-around="false"
						snapAlign="start"
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
					<div class="flex justify-between items-center">
						<button
							class="inline-block bg-nav-green mx-4 my-2 text-black font-bold w-20 h-10 rounded-full"
							disabled
						>
							{{ kid.kidName }}
						</button>

						<button
							@click="goDetail(kid.kidId)"
							class="my-2 mx-1 px-4 py-1 text-xl font-bold rounded-md hover:bg-gray-200 pointer-cursor"
						>
							→ 상세 조회
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Carousel 아이 이름별 보기 -->
		<div v-else>
			<div class="datepicker px-2">
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
			<div v-if="albumClassroomDateList.length">
				<div v-for="album in albumClassroomDateList" :key="album.albumId">
					<div class="m-4 flex flex-wrap" v-if="albumClassroomDateList != null">
						<p class="w-full text-2xl font-bold m-2 px-4">
							{{ album.albumTitle }}
						</p>
						<div
							v-for="file in album.fileResponseDtoList"
							:key="file.fileId"
							class="w-1/4 flex-shrink-0 flex flex-wrap p-2"
						>
							<input
								type="checkbox"
								:id="file.fileId"
								:value="`${file.fileId}`"
								class="hidden peer"
							/>
							<label
								:for="file.fileId"
								class="inline-flex items-center justify-between p-2 text-gray-500 bg-white border-2 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 cursor-default"
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
			<div v-else class="m-6">
				<p>등록된 사진이 없습니다.</p>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, defineComponent, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import { useAlbumStore } from '@/store/album';

const router = useRouter();
const albumStore = useAlbumStore();

const props = defineProps({
	showToggle: Boolean,
});

// datePicker
const date = ref(new Date());
// 색상
const selectAttribute = ref({ highlight: 'green' });
// 날짜
const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);

function formatDate(date) {
	const year = date.getFullYear();
	const month = `0${date.getMonth() + 1}`.slice(-2); // 월은 0부터 시작하므로 1을 더해주기
	const day = `0${date.getDate()}`.slice(-2);

	return `${year}-${month}-${day}`;
}

watch(date, async newDate => {
	const albumDate = formatDate(newDate);
	await postAlbumClassroomDateList(albumDate);
});

// 반 아이들 최신 앨범 리스트 조회 (Carousel)
const recentAlbumList = ref([]);
const getRecentAlbumList = async () => {
	await albumStore.getRecentAlbumList();
	recentAlbumList.value = albumStore.recentAlbumList;
};

// 반 앨범 날짜별 조회
const albumClassroomDateList = ref([]);
const postAlbumClassroomDateList = async () => {
	const albumDate = formatDate(date.value);
	await albumStore.postAlbumClassroomDateList(albumDate);
	albumClassroomDateList.value = albumStore.albumClassroomDateList;
};

// 앨범 있는 날짜 목록
const activeDates = ref([]);
// 앨범이 없는 날짜 목록 추출
const disabledDates = ref([]);

onMounted(async () => {
	await getRecentAlbumList();
	await postAlbumClassroomDateList();

	// datepicker에서 활성화시킬 날짜 호출
	await albumStore.getActiveClassDates();
	activeDates.value = albumStore.activeClassDates;

	// 알림장이 있는 날짜들 중 가장 오래된 날짜
	const startDate = new Date(activeDates.value[activeDates.value.length - 1]);
	// 앨범 있는 날짜들 중 가장 최근 날짜
	const endDate = new Date(activeDates.value[0]);

	// DatePicker의 초기 날짜를 가장 최근 앨범 날짜로 설정
	date.value = endDate;

	// 앨범 있는 기간 중 앨범이 없는 날짜 disabledDates 배열에 추출
	for (let d = new Date(startDate); d <= endDate; d.setDate(d.getDate() + 1)) {
		const dateStr = d.toISOString().split('T')[0];
		if (!activeDates.value.includes(dateStr)) {
			disabledDates.value.push(dateStr);
		}
	}

	const startBefore = new Date(startDate);
	startBefore.setDate(startBefore.getDate() - 1);
	const endAfter = new Date(endDate);
	endAfter.setDate(endAfter.getDate() + 1);

	// 앨범 있는 가장 과거 날짜 이전의 날짜들 모두 비활성화
	disabledDates.value.push({
		start: null,
		end: startBefore,
	});

	// 앨범 있는 가장 최근 날짜 이후의 날짜들 모두 비활성화
	disabledDates.value.push({
		start: endAfter,
		end: null,
	});
});

// carousel 시작

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
</script>

<style scoped>
.album {
	width: 250px;
	height: 200px;
}
</style>
