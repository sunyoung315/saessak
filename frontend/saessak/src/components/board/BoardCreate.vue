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
				<div class="block mt-1 ml-32 mb-10">
					<select id="name" class="selection-input" v-model="kidId" required>
						<template v-for="kid in userStore.kidsList" :key="kid.kidId">
							<option :value="kid.kidId">{{ kid.kidName }}</option>
						</template>
					</select>
				</div>
			</div>
			<label class="block mt-2 mb-5">
				<span class="content-title">내용</span>
				<textarea
					id="contents"
					class="content-box mb-10 p-4 text-lg"
					rows="6"
					v-model="newBoard.boardContent"
					required
				></textarea>
			</label>
		</div>
		<span class="content-title">건강기록 (선택)</span>
		<div class="content-box mb-0 p-2">
			<div class="record-flex">
				<span class="record-title">체온 체크 </span>
				<div class="number-input">
					<button type="button" @click="decrementTemp" class="minus-button">
						<svg
							class="button-icon"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 2"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M1 1h16"
							/>
						</svg>
					</button>
					<input
						type="text"
						v-model="newBoard.boardTemperature"
						class="record-content"
					/>
					<button type="button" @click="incrementTemp" class="plus-button">
						<svg
							class="button-icon"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 18"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M9 1v16M1 9h16"
							/>
						</svg>
					</button>
				</div>
				<div class="unit">°C</div>
			</div>
			<div class="record-flex">
				<span class="record-title">수면 시간 </span>
				<div class="number-input">
					<button type="button" @click="decrementSleep" class="minus-button">
						<svg
							class="button-icon"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 2"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M1 1h16"
							/>
						</svg>
					</button>
					<input
						type="text"
						v-model="newBoard.boardSleepTime"
						class="record-content"
					/>
					<button type="button" @click="incrementSleep" class="plus-button">
						<svg
							class="button-icon"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 18"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M9 1v16M1 9h16"
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
						<button type="button" @click="decrementTall" class="minus-button">
							<svg
								class="button-icon"
								aria-hidden="true"
								xmlns="http://www.w3.org/2000/svg"
								fill="none"
								viewBox="0 0 18 2"
							>
								<path
									stroke="currentColor"
									stroke-linecap="round"
									stroke-linejoin="round"
									stroke-width="2"
									d="M1 1h16"
								/>
							</svg>
						</button>
						<input
							type="text"
							v-model="newBoard.boardTall"
							class="record-content"
						/>
						<button type="button" @click="incrementTall" class="plus-button">
							<svg
								class="button-icon"
								aria-hidden="true"
								xmlns="http://www.w3.org/2000/svg"
								fill="none"
								viewBox="0 0 18 18"
							>
								<path
									stroke="currentColor"
									stroke-linecap="round"
									stroke-linejoin="round"
									stroke-width="2"
									d="M9 1v16M1 9h16"
								/>
							</svg>
						</button>
					</div>
				</div>
				<div class="unit">cm</div>
				<div class="number-input">
					<button type="button" @click="decrementWeight" class="minus-button">
						<svg
							class="button-icon"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 2"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M1 1h16"
							/>
						</svg>
					</button>
					<input
						type="text"
						v-model="newBoard.boardWeight"
						class="record-content"
					/>
					<button type="button" @click="incrementWeight" class="plus-button">
						<svg
							class="button-icon"
							aria-hidden="true"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 18 18"
						>
							<path
								stroke="currentColor"
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M9 1v16M1 9h16"
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
import { onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { createBoard } from '@/api/board';
import { useUserStore } from '@/store/user';
import { useBoardStore } from '@/store/board';

const router = useRouter();
const userStore = useUserStore();
const boardStore = useBoardStore();

// '목록'으로 라우팅
const goBoardList = () => {
	router.push({ name: 'BoardList' });
};

// 작성할 수 있는 반 아이 리스트
onMounted(async () => {
	await userStore.getKidsList();
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

// 아이 선택하면 최근 키/체중 가져오기
watch(kidId, async newVal => {
	await boardStore.getCurrentBoard(newVal);
	if (!boardStore.noContent) {
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
	newBoard.value.boardTemperature = parseFloat(
		parseFloat(newBoard.value.boardTemperature - 0.1).toFixed(1),
	);
};

const incrementSleep = () => {
	newBoard.value.boardSleepTime = parseFloat(
		parseFloat(newBoard.value.boardSleepTime + 0.5).toFixed(1),
	);
};

const decrementSleep = () => {
	newBoard.value.boardSleepTime = parseFloat(
		parseFloat(newBoard.value.boardSleepTime - 0.5).toFixed(1),
	);
};

const incrementTall = () => {
	newBoard.value.boardTall = parseFloat(
		parseFloat(newBoard.value.boardTall + 0.1).toFixed(1),
	);
};

const decrementTall = () => {
	newBoard.value.boardTall = parseFloat(
		parseFloat(newBoard.value.boardTall - 0.1).toFixed(1),
	);
};

const incrementWeight = () => {
	newBoard.value.boardWeight = parseFloat(
		parseFloat(newBoard.value.boardWeight + 0.1).toFixed(1),
	);
};

const decrementWeight = () => {
	newBoard.value.boardWeight = parseFloat(
		parseFloat(newBoard.value.boardWeight - 0.1).toFixed(1),
	);
};
/////////////////////////////////////////////////

// group button 변경 함수 ///////////////////////
const isFirst = () => {
	newBoard.value.boardPoopStatus = '보통';
};
const isSecond = () => {
	newBoard.value.boardPoopStatus = '묽음';
};
const isThird = () => {
	newBoard.value.boardPoopStatus = '딱딱함';
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

// 알림장 등록 요청
const registBoard = () => {
	createBoard(newBoard.value);
	router.push({ name: 'BoardList' });
};
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
	@apply px-5 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}
.group-button-left-selected {
	@apply px-5 py-2 text-base rounded-s-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.group-button-center {
	@apply px-5 py-2 text-base font-medium text-gray-900 bg-white border-t border-b border-gray-200 focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}
.group-button-center-selected {
	@apply px-5 py-2 text-base z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.group-button-right {
	@apply px-5 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}
.group-button-right-selected {
	@apply px-5 py-2 text-base rounded-e-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
.minus-button {
	@apply bg-gray-100 hover:bg-gray-200 border border-gray-300 rounded-s-lg p-3 h-10 focus:ring-gray-100 focus:ring-2 focus:outline-none;
}
.plus-button {
	@apply bg-gray-100 hover:bg-gray-200 border border-gray-300 rounded-e-lg p-3 h-10 focus:ring-gray-100 focus:ring-2 focus:outline-none;
}
.button-icon {
	@apply w-3 h-3 text-gray-900;
}
.number-input {
	@apply relative flex items-center;
}
</style>
