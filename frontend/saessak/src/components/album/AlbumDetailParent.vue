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
	</div>
	<!-- DatePicker 시작-->
	<div class="container p-6 border rounded-lg">
		<div class="flex justify-between">
			<div>
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
			</div>
			<div>
				<button
					@click="download"
					type="button"
					class="text-white bg-gradient-to-r from-nav-green via-nav-green to-nav-green hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					다운로드
				</button>
				<button
					type="button"
					@click="goBack()"
					class="text-white bg-gradient-to-r from-nav-green via-nav-green to-nav-green hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					목록
				</button>
			</div>
		</div>
		<!-- DatePicker 끝-->
		<!--전체 보기 -->
		<div v-if="showToggle">
			<div class="flex items-center" v-if="albumAllList.length > 0">
				<div class="w-full">
					<div v-for="album in albumAllList" :key="album.albumId">
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
const myKidAlbumDateList = ref([]);
const albumAllList = ref([]);

const classroomId = 1; // 내 아이의 클래스id로 변경 필요

onMounted(async () => {
	// 내 아이 앨범 조회 // 번호: kidId
	await albumStore.getkidAlbumDateList(route.params.id, date.value);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;

	// 반 전체 앨범 조회 // 번호: classroomId
	await albumStore.getAlbumAllList(classroomId, date.value);
	albumAllList.value = albumStore.albumAllList;
});

// datePicker
const date = ref(new Date());
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
	await albumStore.getkidAlbumDateList(route.params.id, newDate);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
	await albumStore.getAlbumAllList(classroomId, newDate);
	albumAllList.value = albumStore.albumAllList;
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
</style>
