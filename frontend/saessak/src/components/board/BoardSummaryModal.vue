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
					<div class="report-period">
						<VDatePicker v-model.range="range">
							<template #default="{ inputValue, inputEvents }">
								<div class="flex justify-center items-center">
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
											:value="inputValue.start"
											v-on="inputEvents.start"
											class="datepicker-input"
										/>
									</div>
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
											:value="inputValue.end"
											v-on="inputEvents.end"
											class="datepicker-input"
										/>
									</div>
								</div>
							</template>
						</VDatePicker>
						<!-- 완료될 때까지 버튼 비활성화 해놓음! -->
						<button
							type="button"
							@click="getSummaryBoard(props.kidId)"
							class="btn m-0 ml-2"
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
				<template v-if="!summary && isCreating">
					<div
						class="h-[25rem] w-[50rem] px-4 py-12 border border-gray-200 shadow rounded-lg flex justify-center items-center"
					>
						<span class="loader"></span>
					</div>
				</template>
				<template v-else-if="!summary && !isCreating">
					<div>확인하고 싶은 알림장 기간을 선택하세요 :)</div>
				</template>
				<template v-else>
					<div class="whitespace-pre-line text-base">
						{{ summary }}
					</div>
				</template>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useBoardStore } from '@/store/board';
import OpenAI from 'openai';

// Modal//////////////////////////
const isOpen = ref(false);

const openModal = () => {
	isOpen.value = true;
};
const closeModal = () => {
	isOpen.value = false;
};

defineExpose({ openModal });
///////////////////////////////////

const props = defineProps({
	kidId: Number,
});

const range = ref({
	start: new Date(),
	end: new Date(),
});

const isCreating = ref(false);

const store = useBoardStore();

// 선택 기간의 알림장 1줄 string으로 변환된 정보
let content = ref('');
// OpenAI로 요약한 정보
const summary = ref('');

// OpenAI 요약
const API_KEY = import.meta.env.VITE_OPENAI_API_KEY;

const getGPTResponse = async () => {
	try {
		// console.log('요약 시작');
		const openai = new OpenAI({
			apiKey: API_KEY,
			dangerouslyAllowBrowser: true,
		});

		const prompt = `${store.boardList.length}일치의 알림장인 ${content.value} 를 유치원 선생님 말투로 100~150자로 요약해주는데, 날짜를 쓰고 엔터를 치고 요약을 쓰는 형태로 써줘.`;

		const response = await openai.chat.completions.create({
			messages: [
				{
					role: 'user',
					content: prompt,
				},
			],
			model: 'gpt-4-0125-preview',
		});
		summary.value = response.choices[0].message.content;
	} catch (error) {
		summary.value = '오류가 발생했습니다!';
	}
};

const getSummaryBoard = async kidId => {
	isCreating.value = true;
	summary.value = '';

	const startDate = `${range.value.start.getFullYear()}-${('0' + (range.value.start.getMonth() + 1)).slice(-2)}-${('0' + range.value.start.getDate()).slice(-2)}`;
	const endDate = `${range.value.end.getFullYear()}-${('0' + (range.value.end.getMonth() + 1)).slice(-2)}-${('0' + range.value.end.getDate()).slice(-2)}`;

	await store.getSummaryBoard(kidId, startDate, endDate);

	// boardContent 하나의 String으로 연결
	for (let i = 0; i < store.boardList.length; i++) {
		content.value +=
			store.boardList[i].boardDate +
			' 의 알림장 :  \n' +
			store.boardList[i].boardContent +
			'\n';
	}

	if (store.boardList.length) {
		getGPTResponse();
	} else {
		summary.value = '조회된 알림장이 없습니다.';
	}
};
</script>

<style scoped>
.flowbit-modal__container {
	width: 60rem;
	height: 40rem;
	max-width: none;
}
.report-period {
	@apply flex justify-start mx-16 mt-3;
}
.loader {
	position: relative;
	width: 108px;
	display: flex;
	justify-content: space-between;
}
.loader::after,
.loader::before {
	content: '';
	display: inline-block;
	width: 48px;
	height: 48px;
	background-color: #fff;
	background-image: radial-gradient(circle 14px, #0d161b 100%, transparent 0);
	background-repeat: no-repeat;
	border: black solid 3px;
	border-radius: 50%;
	animation:
		eyeMove 7s infinite,
		blink 7s infinite;
}
@keyframes eyeMove {
	0%,
	10% {
		background-position: 0px 0px;
	}
	13%,
	40% {
		background-position: -15px 0px;
	}
	43%,
	70% {
		background-position: 15px 0px;
	}
	73%,
	90% {
		background-position: 0px 15px;
	}
	93%,
	100% {
		background-position: 0px 0px;
	}
}
@keyframes blink {
	0%,
	10%,
	12%,
	20%,
	22%,
	40%,
	42%,
	60%,
	62%,
	70%,
	72%,
	90%,
	92%,
	98%,
	100% {
		height: 48px;
	}
	11%,
	21%,
	41%,
	61%,
	71%,
	91%,
	99% {
		height: 18px;
	}
}
</style>
