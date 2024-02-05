import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

// const REST_DOCUMENT_API = `http://localhost:8080/api/allergy`;
const REST_DOCUMENT_API = `http://i10a706.p.ssafy.io:8081/api/allergy`;
const token = sessionStorage.getItem('accessToken');

export const useAllergyStore = defineStore('allergy', () => {
	// 식품 알레르기 동의서 입력
	const registAllergy = ref([]);
	const PostRegistAllergy = async function (data) {
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
				registAllergy.value = response.data.data;
				console.log('pinia: ' + registAllergy.value);
			})
			.catch(error => {
				console.error('error: ', error);
			});
	};

	// 식품 알레르기 동의서 목록 조회 (선생님)
	const allergyList = ref([]);
	const getAllergyList = async function () {
		await axios
			.get(`${REST_DOCUMENT_API}/list`, {
				headers: {
					Authorization: 'Bearer ' + token,
				},
			})
			.then(response => {
				allergyList.value = response.data.data;
				// console.log(allergyList.value);
			});
	};

	// 목록 조회 (세부 내용)
	const myKidAllergyList = ref([]);
	const getmyAllergyList = async function (kidId) {
		await axios.get(`${REST_DOCUMENT_API}/${kidId}`).then(response => {
			myKidAllergyList.value = response.data.data;
			// console.log(myKidAllergyList.value);
		});
	};

	// 알레르기 동의 확인 버튼
	const allergyCheckList = ref([]);
	const getallergyCheckList = async function (kidId) {
		await axios.get(`${REST_DOCUMENT_API}/check/${kidId}`).then(response => {
			allergyCheckList.value = response.data.data;
			// console.log(allergyCheckList.value);
		});
	};

	return {
		registAllergy,
		PostRegistAllergy,
		allergyList,
		getAllergyList,
		myKidAllergyList,
		getmyAllergyList,
		allergyCheckList,
		getallergyCheckList,
	};
});
