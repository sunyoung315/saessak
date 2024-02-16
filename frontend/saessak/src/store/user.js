import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_USER_API = 'https://i10a706.p.ssafy.io/api/user';

export const useUserStore = defineStore('user', () => {
	const kidsList = ref([]);

	let getKidsList = async () => {
		await axios({
			url: `${REST_USER_API}/kid/list`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
			},
		}).then(resp => {
			kidsList.value = resp.data.data;
		});
	};

	return { kidsList, getKidsList };
});
