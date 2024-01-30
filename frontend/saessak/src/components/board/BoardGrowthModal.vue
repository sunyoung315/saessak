<template>
	<div
		class="flowbit-modal fixed inset-0 flex items-center justify-center z-50"
		v-if="isOpen"
	>
		<div
			class="flowbit-modal__overlay absolute inset-0 bg-black opacity-50"
		></div>
		<div
			class="flowbit-modal__container bg-white w-full max-w-md m-8 md:m-0 rounded-lg shadow-lg z-50 overflow-y-auto"
		>
			<!-- Title & Close Btn -->
			<div
				class="flowbit-modal__header px-4 py-2 flex justify-between items-center"
			>
				<div class="p-6 text-xl font-extrabold">성장 기록 확인</div>
				<button class="pr-5 pb-3 flowbit-modal__close" @click="closeModal">
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
							d="M16.694 7.30602C17.102 7.71405 17.102 8.37559 16.694 8.78361L13.4783 11.9993L16.694 15.2164C17.102 15.6244 17.102 16.286 16.694 16.694C16.286 17.102 15.6244 17.102 15.2164 16.694L12.0007 13.4769L8.78361 16.694C8.37559 17.102 7.71405 17.102 7.30602 16.694C6.89799 16.286 6.89799 15.6244 7.30602 15.2164L10.5231 11.9993L7.30602 8.78361C6.89799 8.37559 6.89799 7.71405 7.30602 7.30602C7.71405 6.89799 8.37559 6.89799 8.78361 7.30602L12.0007 10.5217L15.2164 7.30602C15.6244 6.89799 16.286 6.89799 16.694 7.30602Z"
							fill="#7E84A3"
						/>
					</svg>
				</button>
			</div>

			<!-- Tall -->
			<Chart
				:size="{ width: 800, height: 400 }"
				:data="data"
				:margin="margin"
				:direction="direction"
				:axis="axis"
			>
				<template #layers>
					<Line
						:dataKeys="['numberOfMonth', 'top3']"
						:lineStyle="{ stroke: 'lightgray' }"
					/>
					<Line
						:dataKeys="['numberOfMonth', 'top50']"
						:lineStyle="{ stroke: 'lightgray' }"
					/>
					<Line
						:dataKeys="['numberOfMonth', 'top97']"
						:lineStyle="{ stroke: 'lightgray' }"
					/>
					<Line
						:dataKeys="['numberOfMonth', 'myTall']"
						:lineStyle="{ stroke: 'red' }"
					/>
				</template>

				<template #widgets>
					<Tooltip
						borderColor="black"
						:config="{
							tallId: { hide: true },
							gender: { hide: true },
							numberOfMonth: { label: '개월수', color: 'black' },
							myTall: { hide: false, label: '내아이 키', color: 'red' },
							top3: { label: '3th', color: 'gray' },
							top50: { label: '50th', color: 'gray' },
							top97: { label: '97th', color: 'gray' },
						}"
					/>
				</template>
			</Chart>
		</div>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';

// Modal //////////////////
const isOpen = ref(false);

const openModal = () => {
	isOpen.value = true;
};

const closeModal = () => {
	isOpen.value = false;
};

defineExpose({ openModal });
////////////////////////////

// chartjs //////////////////////////////////////////////
import { Chart, Line, Tooltip } from 'vue3-charts';
import { useBoardStore } from '@/store/board';

// 임시 kidId
const kidId = ref(1);
const gender = ref('F');
const store = useBoardStore();

const tallList = ref([]);
const weightList = ref([]);
const myKidBoard = ref({});

const getGrowthList = async kidId => {
	await store.getGrowthList();
	await store.getCurrentBoard(kidId);
	tallList.value = store.tallList;
	weightList.value = store.weightList;
	myKidBoard.value = store.oneBoard;
	// console.log(tallList.value);
	// console.log(store.weightList);
};

const data = ref(tallList.value);

onMounted(async () => {
	await getGrowthList(kidId.value);
	data.value = tallList.value;
	for (let i = 0; i < data.value.length; i++) {
		// 아이의 개월수에 맞는 데이터 찾기
		if (data.value[i].numberOfMonth === 40) {
			// 최근 키 데이터에 넣기
			data.value[i].myTall = 100;
			break;
		}
	}
});

const axis = ref({
	primary: {
		type: 'band',
		format: val => {
			// 여자아이 + 개월수가 일치할 때
			if (val == 40 && gender.value === 'F') {
				return '👧';
				// 남자이아 + 개월수가 일치할 때
			} else if (val === 40 && gender.value === 'M') {
				return '🧑';
				// 10개월 단위
			} else if (val % 10 === 0 && val != 100) {
				return val;
			}
			return '';
		},
	},
	secondary: {
		domain: [45, 150],
		type: 'linear',
		ticks: 8,
	},
});

const direction = ref('horizontal');
const margin = ref({
	left: 140,
	top: 20,
	right: 0,
	bottom: 0,
});
</script>

<style scoped>
.flowbit-modal__container {
	width: 60rem;
	height: 35rem;
	max-width: none;
}
</style>
