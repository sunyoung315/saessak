<template>
	<div class="view-frame p-4">
		<div class="flex justify-between items-center">
			<!-- DatePicker 시작-->
			<div class="datepicker">
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
				<button type="button" @click="goBack()" class="btn my-0 mx-4">
					목록
				</button>
			</div>
		</div>
		<div class="flex items-center" v-if="myKidAlbumDateList.length">
			<div
				class="m-4 flex flex-wrap"
				v-if="isSameDate(myKidAlbumDateList[0].albumDate, date)"
			>
				<p class="w-full text-2xl font-bold m-2 px-4">
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
						class="inline-flex items-center justify-between p-2 text-gray-500 bg-white border-2 border-gray-200 rounded-lg cursor-default"
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
			<div class="m-6">등록된 사진이 없습니다.</div>
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

// datePicker
const date = ref(new Date());
// 색상
const selectAttribute = ref({ highlight: 'green' });

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);

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

// 앨범 있는 날짜 목록
const activeDates = ref([]);
// 앨범이 없는 날짜 목록 추출
const disabledDates = ref([]);

onMounted(async () => {
	await getKidAlbumDateList();

	// datepicker에서 활성화시킬 날짜 호출
	await albumStore.getActiveDates(route.params.id);
	activeDates.value = albumStore.activeDates;

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
// datePicker 및 날짜 선택 시 데이터 연동 확인 끝

// 버튼
function goBack() {
	router.go(-1);
}
</script>

<style scoped>
.album-box {
	min-width: 250px;
	min-height: 200px;
}

.album {
	width: 250px;
	height: 200px;
}
</style>
