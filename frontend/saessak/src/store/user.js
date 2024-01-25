import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_USER_API = 'http://localhost:8080/api/user';

export const useUserStore = defineStore('user', () => {
	const kidList = ref([]);

	let getKidList = async function (classroomId) {
		await axios({
			url: `${REST_USER_API}/kid/${classroomId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			kidList.value = resp.data;
		});
	};

	return { kidList, getKidList };
});
