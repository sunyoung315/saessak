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
			<div class="block mt-1 ml-32 mb-10">
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
		<template v-if="!store.noContent">
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
			</div>
		</template>
		<template v-else>
			<div class="no-content">{{ store.noContent }}</div>
		</template>
		<br /><br />
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useBoardStore } from '@/store/board';

// 임시 data
const kidId = ref(1);

const router = useRouter();
const route = useRoute();
const store = useBoardStore();

// url 파리미터
const boardId = ref(route.params);

// 목록으로 router 이동
const goBoardList = () => {
	router.push({ name: 'BoardList' });
};

// 알림장 1개
const oneBoard = ref({});

// 알림장 상세조회(boardId)
const getOneBoard = async boardId => {
	await store.getOneBoard(boardId);
	oneBoard.value = store.oneBoard;
};

// datepicker 설정
const date = ref(oneBoard.value.boardDate);

const selectAttribute = ref({ highlight: 'yellow' });

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);
const disabledDates = ref([
	{
		start: tomorrow,
		end: null,
	},
]);

// datepicker 날짜 변화 감지
watch(date, newVal => {
	if (newVal) {
		const newValue = new Date(newVal);
		const year = newValue.getFullYear();
		const month = ('0' + (newValue.getMonth() + 1)).slice(-2);
		const day = ('0' + newValue.getDate()).slice(-2);
		const newDate = `${year}-${month}-${day}`;
		store.date = newDate;
		// 알림장 상세조회(date)
		store.getOneBoardByDate(kidId.value);
	}
});

onMounted(async () => {
	await getOneBoard(boardId.value.id);
	date.value = oneBoard.value.boardDate;
});
</script>

<style scoped>
.content-title {
	@apply ml-36 text-gray-900 text-xl font-bold;
}
.content-box {
	@apply block ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
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
	@apply mx-36 mt-8 text-lg;
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
</style>
