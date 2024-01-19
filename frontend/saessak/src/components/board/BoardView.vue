<template>
	<div>
		<h2>BoardView</h2>
		<button
			type="button"
			@click="getSummaryText()"
			class="text-gray-900 hover:text-white border border-gray-800 hover:bg-gray-900 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 dark:border-gray-600 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-800"
		>
			요약레포트
		</button>
		<div>
			<b>본문</b>
		</div>
		<div>{{ content }}</div>
		<div>
			<b>요약</b>
		</div>
		<div>{{ summary }}</div>
	</div>
</template>

<script setup>
// import axios from 'axios';
import { ref } from 'vue';

const API_URL = '/text-summary/v1/summarize';

const API_KEY_ID = import.meta.env.VITE_CLOVA_API_KEY_ID;
const API_KEY = import.meta.env.VITE_CLOVA_API_KEY;

// 임시 Date
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
// 임시 Data 끝

// boardContent 하나의 String으로 연결
let content = '';
for (let i = 0; i < boardList.length; i++) {
	content +=
		'[' + boardList[i].boardDate + ']\n' + boardList[i].boardContent + '\n';
}
console.log(content);

const docObject = {
	content,
};

const optionObject = {
	language: 'ko',
	model: 'general',
	tone: 1,
	summaryCount: 3,
};

// 요청으로 보낼 데이터 하나의 Object로 생성
const requestData = {
	document: docObject,
	option: optionObject,
};

// API 호출을 위한 요청 설정
const requestOptions = {
	method: 'POST',
	mode: 'cors',
	headers: {
		'Content-Type': 'application/json',
		'X-NCP-APIGW-API-KEY-ID': API_KEY_ID,
		'X-NCP-APIGW-API-KEY': API_KEY,
	},
	body: JSON.stringify(requestData),
};

const summary = ref('');

const getSummaryText = async function () {
	try {
		const response = await fetch(API_URL, requestOptions);
		const data = await response.json();
		summary.value = data.summary;
		console.log(data);
	} catch (err) {
		console.log(err);
		return null;
	}
};
</script>

<style scoped></style>
