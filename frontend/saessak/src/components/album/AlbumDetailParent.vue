<template>
	<div>
		<div class="ml-16">
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
	</div>
	<div class="view-frame">
		<div class="flex justify-between">
			<!-- DatePicker 시작-->
			<div class="block mb-5">
				<!-- <span class="content-title">날짜</span> -->
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
			<!-- DatePicker 끝-->
			<div>
				<button @click="download" type="button" class="btn m-0">
					다운로드
				</button>
				<button type="button" @click="goBack()" class="btn m-0 ml-6">
					목록
				</button>
			</div>
		</div>
		<!--전체 보기 -->
		<div v-if="showToggle">
			<div class="flex items-center" v-if="albumParentList.length > 0">
				<div class="w-full">
					<div v-for="album in albumParentList" :key="album.albumId">
						<div
							class="my-2 flex flex-wrap"
							v-if="
								isSameDate(album.albumDate, date) &&
								album.fileResponseDtoList.length > 0
							"
						>
							<p class="w-full text-2xl font-bold ml-4">
								{{ album.albumTitle }}
							</p>
							<div
								v-for="file in album.fileResponseDtoList"
								:key="file.fileId"
								class="w-1/4 flex-shrink-0 flex flex-wrap"
							>
								<input
									type="checkbox"
									:id="file.fileId"
									:value="`${file.filePath}`"
									class="hidden peer"
									v-model="checked"
								/>
								<label
									:for="file.fileId"
									class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-nav-green hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
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
			<!-- <span>Check 이미지 : {{ checked }}</span> -->
		</div>
		<!-- 내 아이 보기 -->
		<div v-else>
			<div class="flex items-center" v-if="myKidAlbumDateList.length > 0">
				<div class="w-full">
					<div v-for="album in myKidAlbumDateList" :key="album.albumId">
						<div
							class="my-2 flex flex-wrap"
							v-if="
								isSameDate(album.albumDate, date) &&
								album.fileResponseDtoList.length > 0
							"
						>
							<p class="w-full text-2xl font-bold m-2">
								{{ album.albumTitle }}
							</p>
							<div
								v-for="file in album.fileResponseDtoList"
								:key="file.fileId"
								class="w-1/4 flex-shrink-0 flex flex-wrap"
							>
								<input
									type="checkbox"
									:id="file.fileId"
									:value="`${file.filePath}`"
									class="hidden peer"
									v-model="checked"
								/>
								<label
									:for="file.fileId"
									class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-nav-green hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
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
			<!-- <span>Check 이미지 : {{ checked }}</span> -->
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAlbumStore } from '@/store/album';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const albumStore = useAlbumStore();

const checked = ref([]);
const showToggle = ref(true);
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
// 내 아이
let kidId = loginStore.kidList[0].kidId;

// 내 아이 앨범 조회 // 번호: kidId
const myKidAlbumDateList = ref([]);
const getKidAlbumDateList = async () => {
	await albumStore.getKidAlbumDateList(route.params.id, date.value);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
};

// 반 전체 앨범 조회 // 번호: classroomId
const albumParentList = ref([]);
const getAlbumParentList = async () => {
	await albumStore.getAlbumParentList(kidId, date.value);
	albumParentList.value = albumStore.albumParentList;
};

onMounted(async () => {
	await getKidAlbumDateList();
	await getAlbumParentList();
});

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

// 같은 날짜 체크
function isSameDate(albumDate, date) {
	const albumDateObj = new Date(albumDate);
	return (
		albumDateObj.getFullYear() === date.getFullYear() &&
		albumDateObj.getMonth() === date.getMonth() &&
		albumDateObj.getDate() === date.getDate()
	);
}

watch(date, async newDate => {
	await albumStore.getKidAlbumDateList(route.params.id, newDate);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
	await albumStore.getAlbumParentList(kidId, newDate);
	albumParentList.value = albumStore.albumParentList;
});
// datePicker 및 날짜 선택 시 데이터 연동 확인 끝

// 버튼
function goBack() {
	router.go(-1);
}

// File Download 시작
const download = async () => {
	for (let i = 0; i < checked.value.length; i++) {
		const fileUrl = checked.value[i]; // 파일 경로를 체크된 이미지의 파일 경로로 변경
		const res = await axios({
			method: 'get',
			url: fileUrl,
			responseType: 'blob',
		});
		console.log(res.data);
		const newUrl = window.URL.createObjectURL(res.data);
		const a = document.createElement('a');
		a.href = newUrl;
		// 아래 a.download = 저장되는 이미지 파일 이름
		a.download = `image${i}.png`; // 다운로드 되는 파일 이름을 image0, image1, ... 로 설정
		a.click();
		a.remove();
		window.URL.revokeObjectURL(newUrl);
	}
}; // File Download 끝
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}

.content-title {
	@apply text-gray-900 text-xl font-bold;
}
</style>
