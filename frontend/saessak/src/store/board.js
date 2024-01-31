import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

const REST_BOARD_API = 'https://i10a706.p.ssafy.io/api/board';

export const useBoardStore = defineStore('board', () => {
	// 학부모ver 내 아이 알림장 리스트
	const myKidBoards = ref([]);

	// 학부모ver 내 아이 알림장 리스트 요청
	const getMyKidBoards = async kidId => {
		await axios({
			url: `${REST_BOARD_API}/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + sessionStorage.getItem('accessToken'),
			},
		}).then(resp => {
			myKidBoards.value = resp.data.data;
		});
	};

	// 알림장 1개
	const oneBoard = ref({});
	// 검색할 날짜
	const date = ref('');
	// 알림장이 없을 경우
	const noContent = ref('');

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
		})
			.then(resp => {
				noContent.value = '';
				oneBoard.value = resp.data.data;
				date.value = oneBoard.value.boardDate;
			})
			.catch(error => {
				if (error.response.status === 500) {
					noContent.value = '등록된 알림장이 없습니다.';
				}
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
		})
			.then(resp => {
				noContent.value = '';
				oneBoard.value = resp.data.data;
				date.value = oneBoard.value.boardDate;
			})
			.catch(error => {
				if (error.response.status === 500) {
					noContent.value = '등록된 알림장이 없습니다.';
				}
			});
	};

	// 선택 기간의 알림장 조회
	const summary = ref({});
	const boardList = ref([]);
	const getSummaryBoard = async (kidId, { startDate, endDate }) => {
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

	// 임시 성별
	const gender = ref('M');
	// 성장도표 조회
	const tallList = ref([]);
	const weightList = ref([]);
	const getGrowthList = async () => {
		await axios({
			url: 'https://i10a706.p.ssafy.io/api/growth',
			method: 'POST',
			data: {
				gender: gender.value,
				startMonth: 0,
				endMonth: 100,
			},
			headers: {
				'Content-Type': 'application/json',
			},
		})
			.then(resp => {
				tallList.value = resp.data.data.tallList;
				weightList.value = resp.data.data.weightList;
			})
			.catch(err => {
				console.log(err);
			});
	};

	return {
		myKidBoards,
		getMyKidBoards,
		oneBoard,
		getOneBoard,
		date,
		getOneBoardByDate,
		noContent,
		getCurrentBoard,
		summary,
		boardList,
		getSummaryBoard,
		tallList,
		weightList,
		getGrowthList,
	};
});
