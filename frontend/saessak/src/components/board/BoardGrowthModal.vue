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
				<div class="group-growth-btn">
					<button @click="getTallList()" class="group-growth-left-btn">
						키
					</button>
					<button @click="getWeightList()" class="group-growth-right-btn">
						몸무게
					</button>
				</div>
				<button
					class="pr-5 pb-3 flowbit-modal__close pl-40"
					@click="closeModal"
				>
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
			<template v-if="isTall">
				<Chart
					:size="{ width: 1000, height: 500 }"
					:data="tallData"
					:margin="margin"
					:direction="direction"
					:axis="tallAxis"
				>
					<template #layers>
						<Line
							:dataKeys="['numberOfMonth', 'top3']"
							:lineStyle="{ stroke: 'lightgray', strokeWidth: 2 }"
							:dotStyle="{ r: 2 }"
						/>
						<Line
							:dataKeys="['numberOfMonth', 'top50']"
							:lineStyle="{ stroke: 'lightgray', strokeWidth: 2 }"
							:dotStyle="{ r: 2 }"
						/>
						<Line
							:dataKeys="['numberOfMonth', 'top97']"
							:lineStyle="{ stroke: 'lightgray', strokeWidth: 2 }"
							:dotStyle="{ r: 2 }"
						/>
						<Line
							:dataKeys="['numberOfMonth', 'myKidTall']"
							:lineStyle="{ stroke: 'black', strokeWidth: 2 }"
							:dotStyle="{ r: 4, stroke: 'black', strokeWidth: 2 }"
						/>
					</template>

					<template #widgets>
						<Tooltip
							borderColor="b"
							:config="{
								tallId: { hide: true },
								gender: { hide: true },
								numberOfMonth: {
									label: '개월수',
									color: 'navy',
									label: '만나이',
									format: val => val + ' 개월',
								},
								myKidTall: {
									label: `${kidName}`,
									color: 'navy',
									format: val => val.toFixed(1) + ' cm',
								},
								top3: {
									label: '3th',
									color: 'gray',
									format: val => val.toFixed(1) + ' cm',
								},
								top50: {
									label: '50th',
									color: 'gray',
									format: val => val.toFixed(1) + ' cm',
								},
								top97: {
									label: '97th',
									color: 'gray',
									format: val => val.toFixed(1) + ' cm',
								},
							}"
						/>
					</template>
				</Chart>
			</template>

			<!-- Weight -->
			<template v-else>
				<Chart
					:size="{ width: 1000, height: 500 }"
					:data="weightData"
					:margin="margin"
					:direction="direction"
					:axis="weightAxis"
				>
					<template #layers>
						<Line
							:dataKeys="['numberOfMonth', 'top5']"
							:lineStyle="{ stroke: 'lightgray', strokeWidth: 2 }"
							:dotStyle="{ r: 2 }"
						/>
						<Line
							:dataKeys="['numberOfMonth', 'top50']"
							:lineStyle="{ stroke: 'lightgray', strokeWidth: 2 }"
							:dotStyle="{ r: 2 }"
						/>
						<Line
							:dataKeys="['numberOfMonth', 'top95']"
							:lineStyle="{ stroke: 'lightgray', strokeWidth: 2 }"
							:dotStyle="{ r: 2 }"
						/>
						<Line
							:dataKeys="['numberOfMonth', 'myKidWeight']"
							:lineStyle="{ stroke: 'black', strokeWidth: 2 }"
							:dotStyle="{ r: 4, stroke: 'black', strokeWidth: 2 }"
						/>
					</template>

					<template #widgets>
						<Tooltip
							borderColor="navy"
							:config="{
								weightId: { hide: true },
								gender: { hide: true },
								numberOfMonth: {
									color: 'navy',
									label: '만나이',
									format: val => val + ' 개월',
								},
								myKidWeight: {
									label: `${kidName}`,
									color: 'navy',
									format: val => val.toFixed(1) + ' kg',
								},
								top5: {
									label: '5th',
									color: 'gray',
									format: val => val.toFixed(1) + ' kg',
								},
								top50: {
									label: '50th',
									color: 'gray',
									format: val => val.toFixed(1) + ' kg',
								},
								top95: {
									label: '95th',
									color: 'gray',
									format: val => val.toFixed(1) + ' kg',
								},
							}"
						/>
					</template>
				</Chart>
			</template>
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

// 성장 기록 키/몸부게 화면 전환 이벤트
const isTall = ref(true);
const getTallList = () => {
	isTall.value = true;
};
const getWeightList = () => {
	isTall.value = false;
};

const loginStore = JSON.parse(localStorage.getItem('loginStore'));
// const kidId = loginStore.isTeacher ? undefined : props.loginStore.curKid;
const kidId = loginStore.isTeacher ? undefined : loginStore.kidList[0].kidId;

const store = useBoardStore();

// 성장도표 키/몸무게
const tallList = ref([]);
const weightList = ref([]);

// 조회한 성장도표의 최대, 최소값
const tallMin = ref(0);
const tallMax = ref(0);
const weightMin = ref(0);
const weightMax = ref(0);

// 아이의 성장 기록
const myKidGrowthList = ref([]);

// 임시 데이터 ///////////////////////////////////////
const kidName = ref(myKidGrowthList.value.kidName);
// 아이의 성별
const gender = ref(myKidGrowthList.value.gender);
// 아이의 생년월일
const myKidBirthday = ref(new Date(myKidGrowthList.value.kidBirthday));
// 아이 개월수
let myKidMonths = ref(0);

const today = new Date();
// 조회 기간
const beforeOneYear = new Date(new Date().setMonth(new Date().getMonth() - 12));
/////////////////////////////////////////////////////

// 내 아이의 12개월치 성장 기록 조회
const period = ref({
	startDate: beforeOneYear,
	endDate: today,
});

// 성장 도표 조회
const getGrowthList = async (gender, myKidMonths) => {
	await store.getGrowthList(gender, myKidMonths);
	tallList.value = store.tallList;
	weightList.value = store.weightList;
};

// 내 아이의 성장기록 가져오기
const getMyKidGrowthList = async (kidId, period) => {
	await store.getMyKidGrowthList(kidId, period);
	myKidGrowthList.value = store.myKidGrowthList;
	// 아이의 성별 확인
	gender.value = store.myKidGrowthList.gender;
	// 아이의 이름 확인
	kidName.value = store.myKidGrowthList.kidName;
	// 아이의 생년월일 확인
	myKidBirthday.value = new Date(store.myKidGrowthList.kidBirthday);
	// 아이 개월수
	myKidMonths.value =
		Math.abs(today.getFullYear() - myKidBirthday.value.getFullYear()) * 12 +
		Math.abs(today.getMonth() - myKidBirthday.value.getMonth());
	await getGrowthList(gender.value, myKidMonths.value);
};

// 성장 도표 데이터 (키/몸무게)
const tallData = ref(tallList.value);
const weightData = ref(weightList.value);

const tallAxis = ref({
	primary: {
		type: 'band',
		format: val => {
			// 여자아이 + 개월수가 일치할 때
			if (val == myKidMonths.value && gender.value === 'F') {
				return '👧';
				// 남자이아 + 개월수가 일치할 때
			} else if (val === myKidMonths.value && gender.value === 'M') {
				return '🧑';
				// 10개월 단위
			} else if (val % 10 === 0 && val != 100) {
				return val;
			}
			return '';
		},
	},
	secondary: {
		domain: [tallMin.value, tallMax.value],
		type: 'linear',
		ticks: 8,
	},
});

const weightAxis = ref({
	primary: {
		type: 'band',
		format: val => {
			// 여자아이 + 개월수가 일치할 때
			if (val == myKidMonths.value && gender.value === 'F') {
				return '👧';
				// 남자이아 + 개월수가 일치할 때
			} else if (val === myKidMonths.value && gender.value === 'M') {
				return '🧑';
				// 10개월 단위
			} else if (val % 10 === 0 && val != 100) {
				return val;
			}
			return '';
		},
	},
	secondary: {
		domain: [weightMin.value, weightMax.value],
		type: 'linear',
		ticks: 8,
	},
});

const direction = ref('horizontal');
const margin = ref({
	left: 120,
	top: 25,
	right: 0,
	bottom: 0,
});

onMounted(async () => {
	await getMyKidGrowthList(kidId, period.value);
	tallData.value = tallList.value;
	weightData.value = weightList.value;

	tallMin.value =
		tallList.value[0].top3 - 5 >= 0 ? tallList.value[0].top3 - 2 : 0;
	tallMax.value = tallList.value[tallList.value.length - 1].top97 + 2;
	weightMin.value =
		weightList.value[0].top5 - 5 >= 0 ? weightList.value[0].top5 - 2 : 0;
	weightMax.value = weightList.value[weightList.value.length - 1].top95 + 2;

	tallAxis.value.secondary.domain[0] = tallMin.value;
	tallAxis.value.secondary.domain[1] = tallMax.value;
	weightAxis.value.secondary.domain[0] = weightMin.value;
	weightAxis.value.secondary.domain[1] = weightMax.value;

	for (let j = 0; j < myKidGrowthList.value.physicalDtoList.length; j++) {
		let calMonth =
			Math.abs(
				new Date(
					myKidGrowthList.value.physicalDtoList[j].boardDate,
				).getFullYear() - myKidBirthday.value.getFullYear(),
			) *
				12 +
			Math.abs(
				new Date(
					myKidGrowthList.value.physicalDtoList[j].boardDate,
				).getMonth() - myKidBirthday.value.getMonth(),
			);
		for (let i = 0; i < tallData.value.length; i++) {
			// 아이의 개월수에 맞는 데이터 찾기
			if (tallData.value[i].numberOfMonth === calMonth) {
				// 최근 키 데이터에 넣기
				tallData.value[i].myKidTall =
					myKidGrowthList.value.physicalDtoList[j].boardTall;
				break;
			}
		}
	}
	for (let j = 0; j < myKidGrowthList.value.physicalDtoList.length; j++) {
		let calMonth =
			Math.abs(
				new Date(
					myKidGrowthList.value.physicalDtoList[j].boardDate,
				).getFullYear() - myKidBirthday.value.getFullYear(),
			) *
				12 +
			Math.abs(
				new Date(
					myKidGrowthList.value.physicalDtoList[j].boardDate,
				).getMonth() - myKidBirthday.value.getMonth(),
			);
		for (let i = 0; i < tallData.value.length; i++) {
			// 아이의 개월수에 맞는 데이터 찾기
			if (tallData.value[i].numberOfMonth === calMonth) {
				// 최근 몸무게 데이터에 넣기
				weightData.value[i].myKidWeight =
					myKidGrowthList.value.physicalDtoList[j].boardWeight;
				break;
			}
		}
	}
});
</script>

<style scoped>
.flowbit-modal__container {
	width: 70rem;
	height: 40rem;
	max-width: none;
}
:deep(.layer-axis-x) {
	@apply text-sm;
}
:deep(.layer-axis-y) {
	@apply text-sm;
}
.group-growth-btn {
	@apply inline-flex rounded-md shadow-sm ml-6 mt-7 h-10;
}
.group-growth-left-btn {
	@apply px-6 h-10 text-base font-bold text-dark-navy bg-white border border-gray-200 rounded-l-lg hover:bg-dark-navy hover:text-white focus:bg-dark-navy focus:text-white;
}
.group-growth-right-btn {
	@apply px-6 h-10 text-base font-bold text-dark-navy bg-white border border-gray-200 rounded-r-lg hover:bg-dark-navy hover:text-white focus:bg-dark-navy focus:text-white;
}
</style>
