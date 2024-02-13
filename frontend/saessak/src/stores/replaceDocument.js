import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

const REST_DOCUMENT_API = `http://localhost:8080/api/document/replacement`;

export const useReplacementStore = defineStore('replacement', () => {
	const replacementList = ref([]);
	const getReplacementList = async function (classroomId) {
		await axios
			.get(`${REST_DOCUMENT_API}/list/classroom/${classroomId}`)
			.then(response => {
				replacementList.value = response.data.data;
				// console.log(replacementList.value);
			});
	};

	return {
		replacementList,
		getReplacementList,
	};
});
