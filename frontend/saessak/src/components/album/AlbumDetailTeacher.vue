<template>
	<div class="view-frame p-4">
		<div class="flex justify-between">
			<!-- DatePicker 시작-->
			<div class="block mt-1 mb-4">
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
			<!-- DatePicker 끝-->
			<div>
				<button type="button" @click="goBack()" class="btn m-0">목록</button>
			</div>
		</div>
		<div class="flex items-center" v-if="myKidAlbumDateList.length">
			<!-- <p>{{ myKidAlbumDateList[0].albumDate }}</p> -->
			<div
				class="my-2 flex flex-wrap"
				v-if="isSameDate(myKidAlbumDateList[0].albumDate, date)"
			>
				<p class="w-full text-2xl font-bold m-4">
					{{ myKidAlbumDateList[0].albumTitle }}
				</p>
				<div
					v-for="file in myKidAlbumDateList[0].fileResponseDtoList"
					:key="file.fileId"
					class="w-1/4 flex-shrink-0 flex flex-wrap album-box p-2"
				>
					<input
						type="checkbox"
						:id="file.fileId"
						:value="`${file.fileId}`"
						class="hidden peer"
					/>
					<label
						:for="file.fileId"
						class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-2 border-gray-200 rounded-lg cursor-pointer"
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
		<div v-else>
			<p>등록된 앨범이 없습니다.</p>
		</div>
		<br />
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAlbumStore } from '@/store/album';

const route = useRoute();
const router = useRouter();
const albumStore = useAlbumStore();

// 아이별 앨범 조회 (선생님) // 번호: kidId
const myKidAlbumDateList = ref([]);
const getKidAlbumDateList = async () => {
	await albumStore.getKidAlbumDateList(route.params.id, date.value);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
};

onMounted(async () => {
	await getKidAlbumDateList();
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
});
// datePicker 및 날짜 선택 시 데이터 연동 확인 끝

// 버튼
function goBack() {
	router.go(-1);
}
</script>

<style scoped>
.album-box {
	min-width: 300px;
	min-height: 250px;
}

.album {
	width: 300px;
	height: 250px;
}
</style>
