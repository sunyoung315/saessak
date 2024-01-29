<template>
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
	<div>
		<div class="w-full">
			<div v-if="!showStatus">
				<div v-for="album in albumAllList" :key="album.albumId">
					<div
						class="my-2 flex flex-wrap"
						v-if="isSameDate(album.albumDate, date)"
					>
						<p class="w-full">{{ album.albumTitle }}</p>
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
								v-model="checked"
							/>
							<label
								:for="file.fileId"
								class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-blue-600 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
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
			<div v-else>
				<!-- 학부모 추가 -->
			</div>
		</div>
		<br />
		<span>Check 이미지 : {{ checked }}</span>
	</div>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue';
import { useAlbumStore } from '@/store/album';

const albumStore = useAlbumStore();

const props = defineProps({
	showStatus: Boolean,
	isTeacher: Boolean,
});

const checked = ref([]);

const albumAllList = ref([]);
const kidAlbumList = ref([]);
const AlbumDateAllList = ref([]);
const myKidAlbumDateList = ref([]);

onMounted(async () => {
	// 반 앨범 전체 조회 (선생님) // 번호: classroomId
	await albumStore.getAlbumAllList(2);
	albumAllList.value = albumStore.albumAllList;
	// 아이별 앨범 조회 (선생님) // 번호: kidId
	await albumStore.getkidAlbumList(1);
	kidAlbumList.value = albumStore.kidAlbumList;
	// 반 앨범 날짜 조회 (학부모) // 번호: classroomId
	// await albumStore.getAlbumDateAllList(1);
	// AlbumDateAllList.value = albumStore.AlbumDateAllList;
	// 아이별 앨범 날짜 조회 (학부모) // 번호: kidId
	// await albumStore.getkidAlbumDateList(1);
	// myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
});

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
	width: 100%;
	height: auto;
}
</style>
