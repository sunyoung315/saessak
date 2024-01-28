import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

const LOCAL_API = 'http://localhost:8080/api/board';
// const REST_BOARD_API = 'http://i10a706.p.ssafy.io:8081/api/board';

export const useBoardStore = defineStore('board', () => {
	// 학부모ver 내 아이 알림장 리스트
	const myKidBoards = ref([]);

	// 학부모ver 내 아이 알림장 리스트 요청
	const getMyKidBoards = async kidId => {
		await axios({
			url: `${LOCAL_API}/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			myKidBoards.value = resp.data.data;
		});
	};

	// 알림장 1개
	const oneBoard = ref({});
	// 알림장 상세보기(boardId)
	const getOneBoard = async boardId => {
		await axios({
			url: `${LOCAL_API}/detail/${boardId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			oneBoard.value = resp.data.data;
		});
	};

	const date = ref('');
	const noContent = ref('');
	// 알림장 상세보기(date)
	const getOneBoardByDate = async kidId => {
		await axios({
			url: `${LOCAL_API}/day/${kidId}`,
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
			})
			.catch(() => {
				noContent.value = '등록된 알림장이 없습니다.';
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
	};
});
