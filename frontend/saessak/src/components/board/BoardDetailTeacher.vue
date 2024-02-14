<template>
	<div class="view-frame">
		<div class="flex justify-end items-center">
			<button type="button" @click="goBoardList()" class="btn mt-5 mr-7 mb-3">
				목록
			</button>
		</div>
		<!-- datepicker -->
		<div class="block mb-5">
			<span class="content-title">날짜</span>
			<div class="w-[70%] mx-[15%] block mt-1 mb-10">
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
								class="datepicker-input"
							/>
						</div>
					</template>
				</VDatePicker>
			</div>
		</div>
		<!-- Contents -->
		<template v-if="store.oneBoard">
			<div class="block mt-2 mb-5">
				<span class="content-title">내용</span>
				<textarea
					id="contents"
					class="content-box mb-10 p-4 text-lg"
					rows="6"
					:value="store.oneBoard.boardContent"
					readonly
				></textarea>
			</div>
			<span class="content-title">건강기록</span>
			<div class="content-box mb-0 p-2">
				<template v-if="store.oneBoard.boardTemperature">
					<div class="record-flex">
						<span class="record-title">체온 체크 </span>
						<input
							type="text"
							class="record-content"
							:value="store.oneBoard.boardTemperature"
							readonly
						/>
						<div class="unit">°C</div>
					</div>
				</template>
				<template v-if="store.oneBoard.boardSleepTime">
					<div class="record-flex">
						<span class="record-title">수면 시간 </span>
						<div class="relative flex items-center">
							<input
								type="text"
								class="record-content"
								:value="store.oneBoard.boardSleepTime"
								readonly
							/>
						</div>
						<div class="unit">시간</div>
					</div>
				</template>
				<template v-if="store.oneBoard.boardPoopStatus">
					<div>
						<span class="record-title">배변 상태</span>
						<div class="group-button" role="group">
							<template v-if="store.oneBoard.boardPoopStatus === '보통'">
								<button
									type="button"
									class="group-button-left-item-focus"
									disabled
								>
									보통
								</button>
							</template>
							<template v-else>
								<button type="button" class="group-button-left-item" disabled>
									보통
								</button>
							</template>
							<template v-if="store.oneBoard.boardPoopStatus === '묽음'">
								<button
									type="button"
									class="group-button-center-item-focus"
									disabled
								>
									묽음
								</button>
							</template>
							<template v-else>
								<button type="button" class="group-button-center-item" disabled>
									묽음
								</button>
							</template>
							<template v-if="store.oneBoard.boardPoopStatus === '딱딱함'">
								<button
									type="button"
									class="group-button-right-item-focus"
									disabled
								>
									딱딱함
								</button>
							</template>
							<template v-else>
								<button type="button" class="group-button-right-item" disabled>
									딱딱함
								</button>
							</template>
						</div>
					</div>
				</template>
				<template v-if="store.oneBoard.boardTall || store.oneBoard.boardWeight">
					<div class="record-flex">
						<span class="record-title">키/몸무게</span>
						<input
							type="text"
							class="record-content"
							:value="store.oneBoard.boardTall"
							readonly
						/>
						<div class="unit">cm</div>
						<input
							type="text"
							class="record-content"
							:value="store.oneBoard.boardWeight"
							readonly
						/>
						<div class="unit">kg</div>
					</div>
				</template>
			</div>
		</template>
		<template v-else>
			<div class="no-content">등록된 알림장이 없습니다.</div>
		</template>
		<br /><br />
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useBoardStore } from '@/store/board';

const router = useRouter();

// 목록으로 router 이동
const goBoardList = () => {
	router.push({ name: 'BoardList' });
};

const route = useRoute();

// url 파리미터
const kidId = ref(route.params.id);

const store = useBoardStore();

// 알림장 상세정보
const oneBoard = ref({});

// 알림장 상세조회(kidId, 최신 알림장)
const getOneBoard = async kidId => {
	await store.getCurrentBoard(kidId);
	oneBoard.value = store.oneBoard;
	if (oneBoard.value) {
		store.date = oneBoard.value.boardDate;
	}
};

// datepicker 설정
const date = ref(oneBoard.value.boardDate);

const selectAttribute = ref({ highlight: 'yellow' });

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);

// 알림장이 있는 날짜 목록
const activeDates = ref([]);
// 알림장이 없는 날짜 목록 추출
const disabledDates = ref([]);

// datepicker 날짜 변화 감지
watch(date, newVal => {
	if (newVal) {
		const newValue = new Date(newVal);
		const year = newValue.getFullYear();
		const month = ('0' + (newValue.getMonth() + 1)).slice(-2);
		const day = ('0' + newValue.getDate()).slice(-2);
		const newDate = `${year}-${month}-${day}`;
		// 알림장 상세조회(date)
		if (store.date !== newDate) {
			store.date = newDate;
			store.getOneBoardByDate(kidId.value);
		}
	}
});

// getOneBoard함수에서 store.date 업데이트될 때 date값도 함께 업데이트
watch(
	() => store.date,
	newVal => {
		date.value = newVal;
	},
);

onMounted(async () => {
	await getOneBoard(kidId.value);
	if (store.oneBoard) {
		date.value = store.oneBoard.boardDate;
	}

	// datepicker에서 활성화시킬 날짜 호출
	await store.getActiveDates(kidId.value);
	activeDates.value = store.activeDates;

	// 알림장이 있는 날짜들 중 가장 오래된 날짜
	const startDate = new Date(activeDates.value[activeDates.value.length - 1]);
	// 알림장이 있는 날짜들 중 가장 최근 날짜
	const endDate = new Date(activeDates.value[0]);

	// 알림장 있는 기간 중 알림장이 없는 날짜 disabledDates 배열에 추출
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

	// 알림장 있는 가장 과거 날짜 이전의 날짜들 모두 비활성화
	disabledDates.value.push({
		start: null,
		end: startBefore,
	});

	// 알림장 있는 가장 최근 날짜 이후의 날짜들 모두 비활성화
	disabledDates.value.push({
		start: endAfter,
		end: null,
	});
});
</script>

<style scoped>
.content-title {
	@apply w-[70%] mx-[15%] px-2 text-gray-900 text-xl font-bold;
}
.content-box {
	@apply block w-[70%] mx-[15%] mt-1 rounded-md border border-neutral-300 shadow;
}
.record-title {
	@apply inline-block m-5 text-gray-700 text-base font-extrabold;
}
.record-flex {
	@apply flex items-center;
}
.record-content {
	@apply block w-20 h-11 py-2.5 bg-gray-100 rounded-md border border-neutral-300 text-center text-gray-900 text-base;
}
.unit {
	@apply pl-3 pr-6;
}
.no-content {
	@apply w-[68%] mx-[16%] text-lg;
}
.group-button {
	@apply inline-flex h-11 rounded-md shadow-sm;
}
.group-button-left-item {
	@apply h-11 px-6 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}
.group-button-left-item-focus {
	@apply h-11 px-6 py-2 text-base border border-gray-200 rounded-s-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.group-button-center-item {
	@apply h-11 px-6 py-2 text-base font-medium text-gray-900 bg-white border-t border-b border-gray-200 focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}
.group-button-center-item-focus {
	@apply h-11 px-6 py-2 text-base border-t border-b border-gray-200 z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.group-button-right-item {
	@apply h-11 px-6 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}
.group-button-right-item-focus {
	@apply h-11 px-6 py-2 text-base border border-gray-200 rounded-e-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}

.shake {
	animation: shake 0.2s;
	animation-iteration-count: 3;
}

::-webkit-scrollbar {
	width: 0.5rem;
}
/* 스크롤바의 트랙(경로)부분 */
::-webkit-scrollbar-track {
	background-color: #dcdcdc;
	border-radius: 1rem;
	box-shadow: inset 0px 0px 5px white;
}
/* 스크롤바의 핸들(드래그하는 부분) */
::-webkit-scrollbar-thumb {
	background-color: #777;
	border-radius: 1rem;
}
/* 스크롤바의 핸들을 호버 시 */
::-webkit-scrollbar-thumb:hover {
	background: #555;
}
</style>
