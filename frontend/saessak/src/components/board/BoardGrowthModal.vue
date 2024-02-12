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
				<apexchart
					type="line"
					height="480"
					width="900"
					:options="chartOptionsTall"
					:series="seriesTall"
					class="px-28 py-12"
				></apexchart>
			</template>

			<!-- Weight -->
			<template v-else>
				<apexchart
					type="line"
					height="480"
					width="900"
					:options="chartOptionsWeight"
					:series="seriesWeight"
					class="px-28 py-12"
				></apexchart>
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

// ApexChart //////////////////////////////////////////////
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
const beforeOneYear = new Date(
	new Date().setFullYear(new Date().getFullYear() - 7),
);
/////////////////////////////////////////////////////

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
	await getGrowthList(gender.value);
};

// ref를 사용하여 반응형 변수 생성
const seriesTall = ref([
	{
		name: '3th',
		data: [],
	},
	{
		name: '50th',
		data: [],
	},
	{
		name: '97th',
		data: [],
	},
	{
		name: kidName.value,
		data: [],
	},
]);

const seriesWeight = ref([
	{
		name: '5th',
		data: [],
	},
	{
		name: '50th',
		data: [],
	},
	{
		name: '95th',
		data: [],
	},
	{
		name: kidName.value,
		data: [],
	},
]);

const chartOptionsTall = ref({
	colors: ['#D3D3D3', '#A9A9A9', '#808080', '#A91B0D'],
	chart: {
		height: 350,
		type: 'line',
		fontFamily: 'Pretendard-Regular',
		animations: {
			enabled: false,
		},
	},
	markers: {
		size: 5,
	},
	dataLabels: {
		enabled: false,
	},
	stroke: {
		curve: 'straight',
		width: 3,
	},
	xaxis: {
		categories: tallList.value.map(tall => tall.numberOfMonth),
		title: {
			text: '개월수',
			style: {
				fontSize: '14px',
			},
		},
		labels: {
			rotate: 0,
			style: {
				fontSize: '10px',
			},
		},
	},
	yaxis: {
		title: {
			text: '키(cm)',
			style: {
				fontSize: '14px',
			},
		},
		labels: {
			style: {
				fontSize: '14px',
			},
		},
		min: 45,
		max: 145,
	},
	legend: {
		fontSize: '14px',
		position: 'top',
	},
	tooltip: {
		shared: true,
		x: {
			formatter: value => `${value} 개월`,
		},
		y: {
			formatter: value => {
				if (value) {
					return `${value} cm`;
				}
			},
		},
	},
});

const chartOptionsWeight = ref({
	colors: ['#D3D3D3', '#A9A9A9', '#808080', '#A91B0D'],
	chart: {
		height: 350,
		type: 'line',
		fontFamily: 'Pretendard-Regular',
		animations: {
			enabled: false,
		},
	},
	markers: {
		size: 5,
	},
	dataLabels: {
		enabled: false,
	},
	stroke: {
		curve: 'straight',
		width: 3,
	},
	xaxis: {
		categories: weightList.value.map(weight => weight.numberOfMonth),
		title: {
			text: '개월수',
			style: {
				fontSize: '14px',
			},
		},
		labels: {
			rotate: 0,
			style: {
				fontSize: '10px',
			},
		},
	},
	yaxis: {
		title: {
			text: '몸무게(kg)',
			style: {
				fontSize: '14px',
			},
		},
		labels: {
			style: {
				fontSize: '14px',
			},
		},
		min: 0,
		max: 40,
	},
	legend: {
		fontSize: '14px',
		position: 'top',
	},
	tooltip: {
		shared: true,
		x: {
			formatter: value => `${value} 개월`,
		},
		y: {
			formatter: value => {
				if (value) {
					return `${value} kg`;
				}
			},
		},
	},
});

onMounted(async () => {
	await getMyKidGrowthList(kidId, period.value);
	// Tall Chart /////////////////////////////////////////////
	kidName.value = myKidGrowthList.value.kidName;
	// 키 3th 데이터 추출
	seriesTall.value[0].data = tallList.value.map(tall => ({
		x: tall.numberOfMonth,
		y: tall.top3,
	}));
	// 키 50th 데이터 추출
	seriesTall.value[1].data = tallList.value.map(tall => ({
		x: tall.numberOfMonth,
		y: tall.top50,
	}));
	// 키 97th 데이터 추출
	seriesTall.value[2].data = tallList.value.map(tall => ({
		x: tall.numberOfMonth,
		y: tall.top97,
	}));
	// 내 아이의 키 데이터 추출
	seriesTall.value[3].name = kidName.value;

	// 1~100개월
	const allMonths = Array.from({ length: 100 }, (_, i) => i + 1);

	// x: numberOfMonth, y: boardTall로 데이터 추출
	const myKidTallData = myKidGrowthList.value.physicalDtoList.map(tall => ({
		x:
			Math.abs(
				new Date(tall.boardDate).getFullYear() -
					myKidBirthday.value.getFullYear(),
			) *
				12 +
			Math.abs(
				new Date(tall.boardDate).getMonth() - myKidBirthday.value.getMonth(),
			),
		y: tall.boardTall,
	}));

	// numberOfMonth의 중복값 제거(가장 큰 boardTall로 설정)
	const temp = Object.values(
		myKidTallData.reduce((acc, cur) => {
			if (!acc[cur.x] || acc[cur.x].y < cur.y) {
				acc[cur.x] = cur;
			}
			return acc;
		}, {}),
	);

	// numberOfMonth가 1~100 중 없는 데이터 null로 처리
	const newTallData = allMonths.map(month => {
		const data = temp.find(tall => tall.x === month);

		return {
			x: month,
			y: data ? data.y : null,
		};
	});

	// 데이터 차트에 넣기
	seriesTall.value[3].data = newTallData;
	/////////////////////////////////////////////////////////////

	// Weight Chart /////////////////////////////////////////////
	seriesWeight.value[0].data = weightList.value.map(weight => ({
		x: weight.numberOfMonth,
		y: weight.top5,
	}));
	seriesWeight.value[1].data = weightList.value.map(weight => ({
		x: weight.numberOfMonth,
		y: weight.top50,
	}));
	seriesWeight.value[2].data = weightList.value.map(weight => ({
		x: weight.numberOfMonth,
		y: weight.top95,
	}));
	seriesWeight.value[3].name = kidName.value;

	// x: numberOfMonth, y: boardTall로 데이터 추출
	const myKidWeightData = myKidGrowthList.value.physicalDtoList.map(weight => ({
		x:
			Math.abs(
				new Date(weight.boardDate).getFullYear() -
					myKidBirthday.value.getFullYear(),
			) *
				12 +
			Math.abs(
				new Date(weight.boardDate).getMonth() - myKidBirthday.value.getMonth(),
			),
		y: weight.boardWeight,
	}));

	// numberOfMonth의 중복값 제거(가장 큰 boardTall로 설정)
	const temp2 = Object.values(
		myKidWeightData.reduce((acc, cur) => {
			if (!acc[cur.x] || acc[cur.x].y < cur.y) {
				acc[cur.x] = cur;
			}
			return acc;
		}, {}),
	);

	// numberOfMonth가 1~100 중 없는 데이터 null로 처리
	const newWeightData = allMonths.map(month => {
		const data = temp2.find(tall => tall.x === month);

		return {
			x: month,
			y: data ? data.y : null,
		};
	});

	// 데이터 차트에 넣기
	seriesWeight.value[3].data = newWeightData;
	////////////////////////////////////////////////////////////
});
</script>

<style scoped>
.flowbit-modal__container {
	width: 70rem;
	height: 42rem;
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
