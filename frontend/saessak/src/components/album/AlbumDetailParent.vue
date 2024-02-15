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
				<button @click="download" type="button" class="btn mt-7 mr-4 mb-3">
					다운로드
				</button>
				<button type="button" @click="goBack()" class="btn mt-7 mr-7 mb-3">
					목록
				</button>
			</div>
		</div>
		<!--내 아이만 보기 -->
		<div v-if="props.showToggle">
			<div class="flex items-center" v-if="myKidAlbumDateList.length">
				<div class="w-full">
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
							class="w-1/4 flex-shrink-0 flex flex-wrap p-2"
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
								class="inline-flex items-center justify-between p-2 text-gray-500 bg-white border-2 border-gray-200 rounded-lg cursor-pointer peer-checked:border-nav-green hover:text-gray-600 peer-checked:text-gray-600 hover:bg-gray-50 peer-checked:border-4"
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

			<div v-else class="m-6">등록된 사진이 없습니다.</div>
		</div>
		<!-- 전체보기 -->
		<div v-else>
			<div class="flex items-center" v-if="albumDateAllList.length">
				<div class="w-full">
					<div v-for="album in albumDateAllList" :key="album.albumId">
						<div
							class="m-4 flex flex-wrap"
							v-if="
								isSameDate(album.albumDate, date) &&
								album.fileResponseDtoList.length
							"
						>
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
									:value="`${file.filePath}`"
									class="hidden peer"
									v-model="checked"
								/>
								<label
									:for="file.fileId"
									class="inline-flex items-center justify-between p-2 text-gray-500 bg-white border-2 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-nav-green hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 peer-checked:border-4"
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
			<div v-else class="m-6">등록된 사진이 없습니다.</div>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAlbumStore } from '@/store/album';
import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3';

const {
	VITE_AWS_REGION,
	VITE_AWS_ACCESS_KEY,
	VITE_AWS_SECRET_KEY,
	VITE_AWS_BUCKET_NAME,
} = import.meta.env;

const props = defineProps({
	loginStore: Object,
	showToggle: Boolean,
});

const route = useRoute();
const router = useRouter();
const albumStore = useAlbumStore();
const checked = ref([]);

// 내 아이
let kidId = props.loginStore.kidList[0].kidId;
// 내 아이 앨범 조회
const myKidAlbumDateList = ref([]);
const getKidAlbumDateList = async () => {
	await albumStore.getKidAlbumDateList(route.params.id, date.value);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
};

// 반 전체 앨범 날짜별 조회
const albumDateAllList = ref([]);
const postAlbumDateAllList = async () => {
	const formattedDate = formatDate(date.value);
	await albumStore.postAlbumDateAllList(kidId, formattedDate);
	albumDateAllList.value = albumStore.albumDateAllList;
};

// datePicker
const date = ref(new Date(route.query.date));
function formatDate(date) {
	const year = date.getFullYear();
	const month = `0${date.getMonth() + 1}`.slice(-2); // 월은 0부터 시작하므로 1을 더해줍니다.
	const day = `0${date.getDate()}`.slice(-2);

	return `${year}-${month}-${day}`;
}

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

// 날짜 변경 감지
watch(date, async newDate => {
	await albumStore.getKidAlbumDateList(route.params.id, newDate);
	myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
	await albumStore.postAlbumDateAllList(kidId, newDate);
	albumDateAllList.value = albumStore.albumDateAllList;
});

// 앨범 있는 날짜 목록
const activeDates = ref([]);
// 앨범이 없는 날짜 목록 추출
const disabledDates = ref([]);

onMounted(async () => {
	await getKidAlbumDateList();
	await postAlbumDateAllList();

	// datepicker에서 활성화시킬 날짜 호출
	await albumStore.getActiveDates(route.params.id);
	activeDates.value = albumStore.activeDates;

	// 알림장이 있는 날짜들 중 가장 오래된 날짜
	const startDate = new Date(activeDates.value[activeDates.value.length - 1]);
	// 앨범 있는 날짜들 중 가장 최근 날짜
	const endDate = new Date(activeDates.value[0]);

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

////////////////// File Download 시작
// key 설정
const s3Client = new S3Client({
	region: VITE_AWS_REGION, // AWS 리전 (예: us-east-1)
	credentials: {
		accessKeyId: VITE_AWS_ACCESS_KEY,
		secretAccessKey: VITE_AWS_SECRET_KEY,
	},
});

// s3
const bucketName = VITE_AWS_BUCKET_NAME;
const removeUrl = `https://${bucketName}.s3.${VITE_AWS_REGION}.amazonaws.com/`;

const download = async () => {
	for (let i = 0; i < checked.value.length; i++) {
		const fileKey = checked.value[i].replace(removeUrl, '');
		// console.log(fileKey)
		try {
			// S3 객체 다운로드 명령 생성
			const getObjectCommand = new GetObjectCommand({
				Bucket: bucketName,
				Key: fileKey,
			});

			// S3 객체 다운로드
			const { Body, ContentType } = await s3Client.send(getObjectCommand);
			const byteArray = await Body.transformToByteArray();
			const type = ContentType.split('/')[1];
			const blob = new Blob([byteArray], { type: ContentType });

			const downloadLink = document.createElement('a');
			downloadLink.href = URL.createObjectURL(blob);
			downloadLink.download = `image${i}.` + type;
			downloadLink.click();
			downloadLink.remove();
			URL.revokeObjectURL(downloadLink.href);
		} catch (error) {
			console.log(error);
		}
	}
}; // File Download 끝
</script>

<style scoped>
.album {
	width: 250px;
	height: 200px;
}
</style>
