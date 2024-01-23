<template>
	<div
		class="flowbit-modal fixed inset-0 flex items-center justify-center z-50"
		v-if="isOpen"
	>
		<div
			class="flowbit-modal__overlay absolute inset-0 bg-black opacity-50"
		></div>
		<div
			class="flowbit-modal__container bg-white max-w-md m-8 md:m-0 rounded-lg shadow-lg z-50 overflow-y-auto"
		>
			<!-- Title & Close Btn -->
			<div
				class="flowbit-modal__header px-4 py-2 flex justify-between items-center"
			>
				<div>
					<div class="mx-20 mt-10 inline-block text-xl font-extrabold">
						요약레포트
					</div>
					<!-- VDatePicker -->
					<div class="flex justify-start mx-16 mt-3">
						<VDatePicker
							v-model="startDate"
							:select-attribute="selectAttribute"
							:disabled-dates="disabledStartDates"
						>
							<template #default="{ inputValue, inputEvents }">
								<div class="relative max-w-sm">
									<div
										class="absolute inset-y-0 start-0 flex items-center ps-3.5 pb-2 pointer-events-none"
									>
										<svg
											class="w-4 h-4 text-gray-900 dark:text-gray-400"
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
										class="border border-gray-300 text-gray-900 text-sm font-bold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-40 ps-10 p-2.5"
									/>
								</div>
							</template>
						</VDatePicker>
						<div class="p-2">
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
									d="M13.6129 6.2097C13.2206 5.90468 12.6534 5.93241 12.2929 6.29289L12.2097 6.3871C11.9047 6.77939 11.9324 7.34662 12.2929 7.70711L15.5852 11H5C4.44772 11 4 11.4477 4 12C4 12.5523 4.44772 13 5 13H15.5852L12.2929 16.2929L12.2097 16.3871C11.9047 16.7794 11.9324 17.3466 12.2929 17.7071C12.6834 18.0976 13.3166 18.0976 13.7071 17.7071L18.7071 12.7071L18.7903 12.6129C19.0953 12.2206 19.0676 11.6534 18.7071 11.2929L13.7071 6.29289L13.6129 6.2097Z"
									fill="#000000"
								/>
							</svg>
						</div>
						<VDatePicker
							v-model="endDate"
							:select-attribute="selectAttribute"
							:disabled-dates="disabledEndDates"
						>
							<template #default="{ inputValue, inputEvents }">
								<div class="relative max-w-sm">
									<div
										class="absolute inset-y-0 start-0 flex items-center ps-3.5 pb-2 pointer-events-none"
									>
										<svg
											class="w-4 h-4 text-gray-900 dark:text-gray-400"
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
										class="border border-gray-300 text-gray-900 text-sm font-bold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-40 ps-10 p-2.5"
									/>
								</div>
							</template>
						</VDatePicker>
						<button
							type="button"
							@click="getGPTResponse()"
							class="ml-2 text-gray-900 hover:text-white border border-gray-800 hover:bg-gray-900 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 dark:border-gray-600 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-800"
						>
							조회
						</button>
					</div>
				</div>
				<button class="pr-5 pb-12 flowbit-modal__close" @click="closeModal">
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

			<!-- content -->
			<div class="mx-20 my-5">
				<div>
					<h1 class="font-extrabold text-xl">본문</h1>
				</div>
				<div class="whitespace-pre-line">{{ content }}</div>
				<br />
				<div>
					<h1 class="font-extrabold text-xl">요약</h1>
				</div>
				<div class="whitespace-pre-line">{{ summary }}</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, watch } from 'vue';
import OpenAI from 'openai';

// Modal
const isOpen = ref(false);

const openModal = () => {
	isOpen.value = true;
};
const closeModal = () => {
	isOpen.value = false;
};

defineExpose({ openModal });

// VDatePicker
const startDate = ref(null);
const endDate = ref(null);

const selectAttribute = ref({ highlight: 'yellow' });

const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);
const disabledStartDates = ref([
	{
		start: tomorrow,
		end: null,
	},
]);

watch(startDate, () => {
	const start = startDate.value;
	const pastDates = [];
	const futureDates = [];

	const beforeStart = new Date(start);
	beforeStart.setDate(start.getDate() - 1);
	pastDates.push({ start: null, end: beforeStart });

	const end = new Date(start);
	if (end.getDate() + 7 > today.getDate()) {
		end.setDate(today.getDate() + 1);
	} else {
		end.setDate(end.getDate() + 7);
	}

	futureDates.push({ start: end, end: null });

	disabledEndDates.value = [...pastDates, ...futureDates];
});

const startSevenDays = new Date(startDate.value);
startSevenDays.setDate(startSevenDays.getDate() + 7);
const disabledEndDates = ref([
	{
		start: startSevenDays.value,
		end: null,
	},
]);

// OpenAI 요약
const API_KEY = import.meta.env.VITE_OPENAI_API_KEY;

// Data
const boardList = [
	{
		boardDate: '2024-01-14',
		boardContent:
			'안녕하세요? 오늘은 성남시립식물원으로 현장학습에 다녀왔어요. 아침에 비가 내려 우리 친구들이 다칠까 걱정했었는데, 다행히 출발하기 전 비가 그쳐 안전하게 다녀올 수 있었습니다. 친구들이 씩씩하게 선생님 손과 친구들 손을 잡고 차가 멈춘 후 차례차례 줄 서서 횡단보도를 건너서 식물원에 갔습니다. 처음 보는 나무들과 식물들에 관심을 가지며 이름을 물어보기도 하고 열대관에서 자라는 식물들과 나무들의 특성들도 알아보았답니다~!! 마지막에는 다양한 씨앗들을 구경하며 신기해하였어요^^ 어린이집에 돌아와서는 어린이집 안에서 할 수 있는 여러 가지 식물 보호 방법을 생각해보고, 실천해보기로 약속했답니다!',
	},
	{
		boardDate: '2024-01-15',
		boardContent:
			'안녕하세요? 이번주에는 즐거운 운동과 휴식이라는 주제로 한주를 보냈습니다. 여름에 하면 더 즐거운 운동에 대해 알아보는 시간을 가졌어요.  또 물놀이를 하기 전 해야할 것과 물놀이를 할 때 주의해야할 행동도 알아보는 시간을 가졌습니다. 활동을 확장하여 더운 여름을 이겨낼 수 있는 방법에 대해 그림으로 표현해 보는 시간도 가졌는데, 지수는 지난 주에 다녀온 물놀이가 즐거웠는지, 아주 멋진 작풉을 완성하였습니다.',
	},
	{
		boardDate: '2024-01-16',
		boardContent:
			'제일 먼저 오는 친구가 거북이 밥을 주는데, 요즘  지수가 1등으로 출석해서 거북이 밥을 매일 주고 있습니다. 다른 친구들보다 일찍 오는 편이라 아침에 관찰하는 시간을 많이 가지고 있어요. 거북이 뿐만 아니라 교실에서 키우고 있는 식물에도 관심이 많아요. 어제보다 얼마나 자랐는지, 저번 주 보다 얼마나 자랐는지.. 저는 잘 느끼지 못하는 부분을 지수가 말해 줄때 얼마나 대견한지 몰라요. 지수의 뛰어난 관찰력, 칭찬 많이 해주세요^^',
	},
];

// boardContent 하나의 String으로 연결
let content = '';
for (let i = 0; i < boardList.length; i++) {
	content +=
		boardList[i].boardDate +
		' 의 알림장 :  \n' +
		boardList[i].boardContent +
		'\n';
}

const summary = ref('');

const getGPTResponse = async () => {
	try {
		const openai = new OpenAI({
			apiKey: API_KEY,
			dangerouslyAllowBrowser: true,
		});

		const prompt = `${boardList.length}일치의 알림장인 ${content} 를 날짜별로 150자 정도로 유치원 선생님 말투로 요약해줘.`;

		const response = await openai.chat.completions.create({
			messages: [
				{
					role: 'user',
					content: prompt,
				},
			],
			model: 'gpt-3.5-turbo',
		});
		console.log('요약 결과 : ', response.choices[0].message.content);
		summary.value = response.choices[0].message.content;
	} catch (error) {
		console.log('에러 발생 : ', error);
	}
};
</script>

<style scoped>
.flowbit-modal__container {
	width: 75rem;
	height: 40rem;
	max-width: none;
}
</style>
