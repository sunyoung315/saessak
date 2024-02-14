<template>
	<div class="view-frame p-1.5">
		<div class="flex justify-end items-center mb-10">
			<button type="button" @click="registBoard()" class="btn mt-7 mr-4 mb-3">
				등록
			</button>
			<button type="button" @click="goBoardList()" class="btn mt-7 mr-7 mb-3">
				목록
			</button>
		</div>
		<div>
			<div class="block mb-5">
				<span class="content-title">이름</span>
				<div class="w-[70%] mx-[15%] block mt-1 mb-10">
					<select
						id="name"
						class="selection-input"
						:value="kidId"
						@change="
							kidId = $event.target.value;
							emptyKidId = false;
						"
						:class="{
							'border-2 border-red-500': emptyKidId,
							shake: shakeKidId,
						}"
						required
					>
						<template v-for="kid in boardStore.kidList" :key="kid.kidId">
							<option :value="kid.kidId">{{ kid.kidName }}</option>
						</template>
					</select>
				</div>
			</div>
			<label class="block mt-2 mb-5">
				<span class="content-title">
					내용
					<span
						class="text-red-500 whitespace-pre-line mx-2"
						v-if="contentExceedsLimit"
					>
						( 최대 500자까지만 작성 가능합니다. )
					</span>
				</span>
				<textarea
					id="contents"
					class="content-box p-4 text-lg mb-2"
					rows="6"
					v-model="newBoard.boardContent"
					@input="
						emptyContent = false;
						checkContentLength();
					"
					:class="{
						'!border-2 !border-red-500': emptyContent,
						shake: shakeContent,
					}"
					required
				></textarea>
				<p class="w-[70%] mx-[15%] pr-4 mb-10 text-right font-bold text-lg">
					{{ contentLength }} / 500자
				</p>
			</label>
		</div>
		<span class="content-title">건강기록 (선택)</span>
		<div class="content-box mb-0 p-2">
			<div class="record-flex">
				<span class="record-title">체온 체크 </span>
				<div class="number-input">
					<button
						type="button"
						@click="decrementTemp"
						:disabled="newBoard.boardTemperature === 0"
						class="minus-button"
						:class="{ 'hover:!bg-gray-200': !newBoard.boardTemperature }"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="14"
							height="2"
							viewBox="0 0 14 2"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M13 0C13.5523 0 14 0.447715 14 1C14 1.55228 13.5523 2 13 2H1C0.447715 2 0 1.55228 0 1C0 0.447715 0.447715 0 1 0L13 0Z"
								:fill="!newBoard.boardTemperature ? '#cccccc' : '#7E84A3'"
							/>
						</svg>
					</button>
					<input
						type="text"
						v-model="newBoard.boardTemperature"
						class="record-content"
						@input="inputTemperature()"
					/>
					<button type="button" @click="incrementTemp" class="plus-button">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="14"
							height="14"
							viewBox="0 0 14 14"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M7 0C7.55228 0 8 0.447715 8 1V5.999L13 6C13.5523 6 14 6.44772 14 7C14 7.55228 13.5523 8 13 8L8 7.999V13C8 13.5523 7.55228 14 7 14C6.44772 14 6 13.5523 6 13V7.999L1 8C0.447715 8 0 7.55228 0 7C0 6.44772 0.447715 6 1 6L6 5.999V1C6 0.447715 6.44772 0 7 0Z"
								fill="#7E84A3"
							/>
						</svg>
					</button>
				</div>
				<div class="unit">°C</div>
			</div>
			<div class="record-flex">
				<span class="record-title">수면 시간 </span>
				<div class="number-input">
					<button
						type="button"
						@click="decrementSleep"
						:disabled="newBoard.boardSleepTime === 0"
						class="minus-button"
						:class="{ 'hover:!bg-gray-200': !newBoard.boardSleepTime }"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="14"
							height="2"
							viewBox="0 0 14 2"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M13 0C13.5523 0 14 0.447715 14 1C14 1.55228 13.5523 2 13 2H1C0.447715 2 0 1.55228 0 1C0 0.447715 0.447715 0 1 0L13 0Z"
								:fill="!newBoard.boardSleepTime ? '#cccccc' : '#7E84A3'"
							/>
						</svg>
					</button>
					<input
						type="number"
						v-model="newBoard.boardSleepTime"
						class="record-content"
						@input="inputSleepTime()"
					/>
					<button type="button" @click="incrementSleep" class="plus-button">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="14"
							height="14"
							viewBox="0 0 14 14"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M7 0C7.55228 0 8 0.447715 8 1V5.999L13 6C13.5523 6 14 6.44772 14 7C14 7.55228 13.5523 8 13 8L8 7.999V13C8 13.5523 7.55228 14 7 14C6.44772 14 6 13.5523 6 13V7.999L1 8C0.447715 8 0 7.55228 0 7C0 6.44772 0.447715 6 1 6L6 5.999V1C6 0.447715 6.44772 0 7 0Z"
								fill="#7E84A3"
							/>
						</svg>
					</button>
				</div>
				<div class="unit">시간</div>
			</div>
			<div>
				<span class="record-title">배변 상태</span>
				<div class="group-button" role="group">
					<button
						type="button"
						@click="isFirst()"
						:class="buttonClass('first')"
						class="group-button-left"
					>
						보통
					</button>
					<button
						type="button"
						@click="isSecond()"
						:class="buttonClass('second')"
						class="group-button-center"
					>
						묽음
					</button>
					<button
						type="button"
						@click="isThird()"
						:class="buttonClass('third')"
						class="group-button-right"
					>
						딱딱함
					</button>
				</div>
			</div>
			<div class="record-flex">
				<span class="record-title">키/몸무게</span>
				<div class="number-input">
					<div class="number-input">
						<button
							type="button"
							@click="decrementTall"
							:disabled="newBoard.boardTall === 0"
							class="minus-button"
							:class="{ 'hover:!bg-gray-200': !newBoard.boardTall }"
						>
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width="14"
								height="2"
								viewBox="0 0 14 2"
								fill="none"
							>
								<path
									fill-rule="evenodd"
									clip-rule="evenodd"
									d="M13 0C13.5523 0 14 0.447715 14 1C14 1.55228 13.5523 2 13 2H1C0.447715 2 0 1.55228 0 1C0 0.447715 0.447715 0 1 0L13 0Z"
									:fill="!newBoard.boardTall ? '#cccccc' : '#7E84A3'"
								/>
							</svg>
						</button>
						<input
							type="number"
							v-model="newBoard.boardTall"
							class="record-content"
							@input="inputTall()"
						/>
						<button type="button" @click="incrementTall" class="plus-button">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width="14"
								height="14"
								viewBox="0 0 14 14"
								fill="none"
							>
								<path
									fill-rule="evenodd"
									clip-rule="evenodd"
									d="M7 0C7.55228 0 8 0.447715 8 1V5.999L13 6C13.5523 6 14 6.44772 14 7C14 7.55228 13.5523 8 13 8L8 7.999V13C8 13.5523 7.55228 14 7 14C6.44772 14 6 13.5523 6 13V7.999L1 8C0.447715 8 0 7.55228 0 7C0 6.44772 0.447715 6 1 6L6 5.999V1C6 0.447715 6.44772 0 7 0Z"
									fill="#7E84A3"
								/>
							</svg>
						</button>
					</div>
				</div>
				<div class="unit">cm</div>
				<div class="number-input">
					<button
						type="button"
						@click="decrementWeight"
						class="minus-button"
						:disabled="newBoard.boardWeight === 0"
						:class="{ 'hover:!bg-gray-200': !newBoard.boardWeight }"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="14"
							height="2"
							viewBox="0 0 14 2"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M13 0C13.5523 0 14 0.447715 14 1C14 1.55228 13.5523 2 13 2H1C0.447715 2 0 1.55228 0 1C0 0.447715 0.447715 0 1 0L13 0Z"
								:fill="!newBoard.boardWeight ? '#cccccc' : '#7E84A3'"
							/>
						</svg>
					</button>
					<input
						type="number"
						v-model="newBoard.boardWeight"
						class="record-content"
						@input="inputWeight()"
					/>
					<button type="button" @click="incrementWeight" class="plus-button">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="14"
							height="14"
							viewBox="0 0 14 14"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M7 0C7.55228 0 8 0.447715 8 1V5.999L13 6C13.5523 6 14 6.44772 14 7C14 7.55228 13.5523 8 13 8L8 7.999V13C8 13.5523 7.55228 14 7 14C6.44772 14 6 13.5523 6 13V7.999L1 8C0.447715 8 0 7.55228 0 7C0 6.44772 0.447715 6 1 6L6 5.999V1C6 0.447715 6.44772 0 7 0Z"
								fill="#7E84A3"
							/>
						</svg>
					</button>
				</div>
				<div class="unit">kg</div>
			</div>
		</div>
		<br /><br />
	</div>
</template>

<script setup>
import { onMounted, ref, watch, computed } from 'vue';
import { useRouter } from 'vue-router';
import { createBoard } from '@/api/board';
// import { useUserStore } from '@/store/user';
import { useBoardStore } from '@/store/board';

const router = useRouter();
// const userStore = useUserStore();
const boardStore = useBoardStore();

// '목록'으로 라우팅
const goBoardList = () => {
	router.push({ name: 'BoardList' });
};

// 작성할 수 있는 반 아이 리스트
onMounted(async () => {
	await boardStore.getKidList();
	// await userStore.getKidsList();
});

const kidId = ref(0);
// 새로운 알림장 변수
const newBoard = ref({
	kidId,
	boardDate: new Date(),
	boardContent: '',
	boardTemperature: 0.0,
	boardSleepTime: 0.0,
	boardPoopStatus: '',
	boardTall: 0.0,
	boardWeight: 0.0,
});

// 내용에 들어가는 글자수
const contentLength = ref(0);
// 최대 500자 확인
const contentExceedsLimit = computed(() => contentLength.value >= 500);

// 500자 초과해서 입력하면 500자까지만 자르기
const checkContentLength = () => {
	contentLength.value = newBoard.value.boardContent.length;
	if (contentLength.value > 500) {
		newBoard.value.boardContent = newBoard.value.boardContent.slice(0, 500);
	}
};

// 아이 선택하면 최근 키/체중 가져오기
watch(kidId, async newVal => {
	await boardStore.getCurrentBoard(newVal);
	if (boardStore.oneBoard) {
		newBoard.value.boardTall = boardStore.oneBoard.boardTall;
		newBoard.value.boardWeight = boardStore.oneBoard.boardWeight;
	} else {
		newBoard.value.boardTall = 0;
		newBoard.value.boardWeight = 0;
	}
});

// 증감 버튼 함수 ////////////////////////////////////
const incrementTemp = () => {
	newBoard.value.boardTemperature = parseFloat(
		parseFloat(newBoard.value.boardTemperature + 0.1).toFixed(1),
	);
};

const decrementTemp = () => {
	if (parseFloat(newBoard.value.boardTemperature) >= 0.1) {
		newBoard.value.boardTemperature = parseFloat(
			parseFloat(newBoard.value.boardTemperature - 0.1).toFixed(1),
		);
	} else {
		newBoard.value.boardTemperature = 0;
	}
};

const inputTemperature = () => {
	if (parseFloat(newBoard.value.boardTemperature) < 0) {
		newBoard.value.boardTemperature = '';
	}
};

const incrementSleep = () => {
	newBoard.value.boardSleepTime = parseFloat(
		parseFloat(newBoard.value.boardSleepTime + 0.5).toFixed(1),
	);
};

const decrementSleep = () => {
	if (parseFloat(newBoard.value.boardSleepTime) >= 0.5) {
		newBoard.value.boardSleepTime = parseFloat(
			parseFloat(newBoard.value.boardSleepTime - 0.5).toFixed(1),
		);
	} else {
		newBoard.value.boardSleepTime = 0;
	}
};

const inputSleepTime = () => {
	if (parseFloat(newBoard.value.boardSleepTime) < 0) {
		newBoard.value.boardSleepTime = '';
	}
};

const incrementTall = () => {
	newBoard.value.boardTall = parseFloat(
		parseFloat(newBoard.value.boardTall + 0.1).toFixed(1),
	);
};

const decrementTall = () => {
	if (parseFloat(newBoard.value.boardTall) >= 0.1) {
		newBoard.value.boardTall = parseFloat(
			parseFloat(newBoard.value.boardTall - 0.1).toFixed(1),
		);
	} else {
		newBoard.value.boardTall = 0;
	}
};

const inputTall = () => {
	if (parseFloat(newBoard.value.boardTall) < 0) {
		newBoard.value.boardTall = '';
	}
};

const incrementWeight = () => {
	newBoard.value.boardWeight = parseFloat(
		parseFloat(newBoard.value.boardWeight + 0.1).toFixed(1),
	);
};

const decrementWeight = () => {
	if (parseFloat(newBoard.value.boardWeight) >= 0.1) {
		newBoard.value.boardWeight = parseFloat(
			parseFloat(newBoard.value.boardWeight - 0.1).toFixed(1),
		);
	} else {
		newBoard.value.boardWeight = 0;
	}
};

const inputWeight = () => {
	if (parseFloat(newBoard.value.boardWeight) < 0) {
		newBoard.value.boardWeight = '';
	}
};
/////////////////////////////////////////////////

// group button 변경 함수 ///////////////////////
const isFirst = () => {
	if (newBoard.value.boardPoopStatus === '보통') {
		newBoard.value.boardPoopStatus = '';
	} else {
		newBoard.value.boardPoopStatus = '보통';
	}
};
const isSecond = () => {
	if (newBoard.value.boardPoopStatus === '묽음') {
		newBoard.value.boardPoopStatus = '';
	} else {
		newBoard.value.boardPoopStatus = '묽음';
	}
};
const isThird = () => {
	if (newBoard.value.boardPoopStatus === '딱딱함') {
		newBoard.value.boardPoopStatus = '';
	} else {
		newBoard.value.boardPoopStatus = '딱딱함';
	}
};

const buttonClass = button => {
	if (button === 'first' && newBoard.value.boardPoopStatus === '보통') {
		return 'group-button-left-selected';
	} else if (button === 'second' && newBoard.value.boardPoopStatus === '묽음') {
		return 'group-button-center-selected';
	} else if (
		button === 'third' &&
		newBoard.value.boardPoopStatus === '딱딱함'
	) {
		return 'group-button-right-selected';
	} else {
		return '';
	}
};
///////////////////////////////////////////////

const emptyKidId = ref(false);
const shakeKidId = ref(false);

const emptyContent = ref(false);
const shakeContent = ref(false);

// 널값처리
const checkEmptyFields = () => {
	let hasEmptyFields = false;
	if (!newBoard.value.kidId) {
		emptyKidId.value = true;
		shakeKidId.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeKidId.value = false;
		}, 1000);
	}
	if (!newBoard.value.boardContent.trim()) {
		emptyContent.value = true;
		shakeContent.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeContent.value = false;
		}, 1000);
	}
	return hasEmptyFields;
};

// 알림장 등록 요청
const registBoard = () => {
	if (checkEmptyFields()) {
		return;
	}
	if (boardStore.oneBoard) {
		if (newBoard.value.boardTall === boardStore.oneBoard.boardTall) {
			newBoard.value.boardTall = 0;
		}
		if (newBoard.value.boardWeight === boardStore.oneBoard.boardWeight) {
			newBoard.value.boardWeight = 0;
		}
	}

	createBoard(newBoard.value);
	router.push({ name: 'BoardList' });
};
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
	@apply block w-20 h-10 py-2.5 bg-white border border-neutral-300 text-center text-gray-900 text-base;
}
.unit {
	@apply pl-3 pr-6;
}
.no-content {
	@apply mx-36 mt-8 text-lg;
}
.group-button {
	@apply inline-flex h-10 rounded-md shadow-sm;
}
.group-button-left {
	@apply px-5 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg;
}
.group-button-left-selected {
	@apply px-5 py-2 text-base rounded-s-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.group-button-center {
	@apply px-5 py-2 text-base font-medium text-gray-900 bg-white border-t border-b border-gray-200;
}
.group-button-center-selected {
	@apply px-5 py-2 text-base z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.group-button-right {
	@apply px-5 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg;
}
.group-button-right-selected {
	@apply px-5 py-2 text-base rounded-e-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.minus-button {
	@apply bg-gray-200 hover:bg-gray-300 border border-gray-300 rounded-s-lg p-3 h-10 focus:ring-gray-100 focus:ring-2 focus:outline-none;
}
.plus-button {
	@apply bg-gray-200 hover:bg-gray-300 border border-gray-300 rounded-e-lg p-3 h-10 focus:ring-gray-100 focus:ring-2 focus:outline-none;
}
.button-icon {
	@apply w-3 h-3 text-gray-900;
}
.number-input {
	@apply relative flex items-center;
}

@keyframes shake {
	0% {
		transform: translateX(0px);
	}
	25% {
		transform: translateX(-2px);
	}
	50% {
		transform: translateX(0px);
	}
	75% {
		transform: translateX(2px);
	}
	100% {
		transform: translateX(0px);
	}
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

/* input number 화살표 제거 */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}
</style>
