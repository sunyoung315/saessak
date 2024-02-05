import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

// const REST_DOCUMENT_API = `http://localhost:8080/api/document/replacement`;
const REST_DOCUMENT_API = `http://i10a706.p.ssafy.io:8081/api/document/replacement`;
const token = sessionStorage.getItem('accessToken');

export const useReplacementStore = defineStore('replacement', () => {
	// 대리인 귀가 동의서 입력
	const registReplacementId = ref([]);
	const PostRegistReplacement = async function (data) {
		console.log('registReplacement 들어올 때 : ' + registReplacementId.value);
		await axios({
			url: `${REST_DOCUMENT_API}/`,
			method: 'POST',
			headers: {
				Authorization: 'Bearer ' + token,
				'Content-Type': 'application/json',
			},
			data,
		})
			.then(response => {
				registReplacementId.value = response.data.data;
				console.log('registReplacement 실행 후 : ' + registReplacementId.value);
			})
			.catch(error => {
				console.error('실행 제대로 안됨: ', error);
			});
	};
	
	// 대리인 귀가 동의서 전자서명 입력
	const PostRegistFileReplacement = async function (data) {
		await axios({
			url: `${REST_DOCUMENT_API}/sign`,
			method: 'POST',
			headers: {
				Authorization: 'Bearer ' + token,
				'Content-Type': 'application/json',
			},
			data,
		})
		.then(response => {
			console.log('registReplacement 실행 후 : ' + response);
		})
		.catch(error => {
			console.error('Failed to post replacement: ', error);
		});
	}

	// 대리인 목록 조회
	const replacementList = ref([]);
	const getReplacementList = async function () {
		await axios
			.get(`${REST_DOCUMENT_API}/list/classroom`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token,
				},
			})
			.then(response => {
				replacementList.value = response.data.data;
				// console.log(replacementList.value);
			});
	};

	// 대리인 목록 조회
	const myKidReplacementList = ref([]);
	const getmyKidReplacementList = async function (kidId) {
		await axios.get(`${REST_DOCUMENT_API}/list/kid/${kidId}`).then(response => {
			myKidReplacementList.value = response.data.data;
			// console.log(myKidReplacementList.value);
		});
	};

	// 대리인 목록 상세 조회
	const replacemenDetailtList = ref([]);
	const getReplacementDetailList = async function (replacementId) {
		await axios.get(`${REST_DOCUMENT_API}/${replacementId}`).then(response => {
			replacemenDetailtList.value = response.data.data;
			// console.log(replacemenDetailtList.value);
		});
	};

	// 대리인 귀가 동의서 확인 버튼
	const replacemenChecktList = ref([]);
	const getreplacemenChecktList = async function (replacementId) {
		await axios
			.get(`${REST_DOCUMENT_API}/check/${replacementId}`)
			.then(response => {
				replacemenChecktList.value = response.data.data;
				// console.log(replacemenChecktList.value);
			});
	};
	return {
		registReplacementId,
		PostRegistReplacement,
		PostRegistFileReplacement,
		replacementList,
		getReplacementList,
		myKidReplacementList,
		getmyKidReplacementList,
		replacemenDetailtList,
		getReplacementDetailList,
		replacemenChecktList,
		getreplacemenChecktList,
	};
});
