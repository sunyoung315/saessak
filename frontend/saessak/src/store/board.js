import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

const REST_BOARD_API = 'https://i10a706.p.ssafy.io/api/board';

export const useBoardStore = defineStore('board', () => {
	const thisYear = new Date().getFullYear();
	const years = ref([]);
	for (let i = 0; i < 10; i++) {
		years.value[i] = thisYear - i;
	}
	const months = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]);

	// 선생님ver 알림장이 작성되지 않은 아이들 리스트
	const kidList = ref([]);
	const getKidList = async () => {
		await axios({
			url: `${REST_BOARD_API}/teacher/day`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
			},
		}).then(resp => {
			kidList.value = resp.data.data;
		});
	};

	// 학부모ver 내 아이 알림장 리스트
	const myKidBoards = ref([]);

	// 학부모ver 내 아이 알림장 월별 리스트 요청
	const getMyKidMonthlyBoards = async (kidId, year, month) => {
		await axios({
			url: `${REST_BOARD_API}/month/${kidId}`,
			method: 'POST',
			data: { year, month },
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
			},
		}).then(resp => {
			myKidBoards.value = resp.data.data;
		});
	};

	// // 학부모ver 내 아이 알림장 리스트 요청
	// const getMyKidBoards = async kidId => {
	// 	await axios({
	// 		url: `${REST_BOARD_API}/${kidId}`,
	// 		method: 'GET',
	// 		headers: {
	// 			'Content-Type': 'application/json',
	// 			Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
	// 		},
	// 	}).then(resp => {
	// 		myKidBoards.value = resp.data.data;
	// 	});
	// };

	// 알림장 1개
	const oneBoard = ref({});
	// 검색할 날짜
	const date = ref('');

	// 알림장 상세보기(boardId)
	const getOneBoard = async boardId => {
		await axios({
			url: `${REST_BOARD_API}/detail/${boardId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			oneBoard.value = resp.data.data;
		});
	};

	// 알림장 상세보기(date)
	const getOneBoardByDate = async kidId => {
		await axios({
			url: `${REST_BOARD_API}/day/${kidId}`,
			method: 'POST',
			data: {
				boardDate: date.value,
			},
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			oneBoard.value = resp.data.data;
		});
	};

	// 알림장 상세보기(kidId, 최신 알림장)
	const getCurrentBoard = async kidId => {
		await axios({
			url: `${REST_BOARD_API}/current/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			oneBoard.value = resp.data.data;
			if (oneBoard.value) {
				date.value = oneBoard.value.boardDate;
			}
		});
	};

	// 선택 기간의 알림장 조회
	const summary = ref({});
	const boardList = ref([]);
	const getSummaryBoard = async (kidId, startDate, endDate) => {
		await axios({
			url: `${REST_BOARD_API}/summary/${kidId}`,
			method: 'POST',
			data: { startDate, endDate },
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			boardList.value = resp.data.data;
		});
	};

	// 성장도표 조회
	const tallList = ref([]);
	const weightList = ref([]);
	const getGrowthList = async gender => {
		await axios({
			url: 'https://i10a706.p.ssafy.io/api/growth',
			method: 'POST',
			data: {
				gender,
				startMonth: 1,
				endMonth: 100,
			},
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			tallList.value = resp.data.data.tallList;
			weightList.value = resp.data.data.weightList;
		});
	};

	// 선택기간 아이의 성장 기록 조회
	const myKidGrowthList = ref({});
	const getMyKidGrowthList = async (kidId, period) => {
		await axios({
			url: `${REST_BOARD_API}/physical/${kidId}`,
			method: 'POST',
			data: period,
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			myKidGrowthList.value = resp.data.data;
		});
	};

	// 알림장 있는 날짜 리스트
	const activeDates = ref([]);
	const getActiveDates = async kidId => {
		await axios({
			url: `${REST_BOARD_API}/exist/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
			},
		}).then(resp => {
			activeDates.value = resp.data.data;
		});
	};

	return {
		years,
		months,
		kidList,
		getKidList,
		getMyKidMonthlyBoards,
		myKidBoards,
		oneBoard,
		getOneBoard,
		date,
		getOneBoardByDate,
		getCurrentBoard,
		summary,
		boardList,
		getSummaryBoard,
		tallList,
		weightList,
		getGrowthList,
		myKidGrowthList,
		getMyKidGrowthList,
		activeDates,
		getActiveDates,
	};
});
