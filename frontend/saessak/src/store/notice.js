import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

const REST_NOTICE_API = 'https://i10a706.p.ssafy.io/api/notice';

export const useNoticeStore = defineStore('notice', () => {
	const fixedNoticeList = ref([]);

	const getFixedNoticeList = async kidId => {
		await axios({
			url: `${REST_NOTICE_API}/fixed/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
			},
		}).then(resp => {
			fixedNoticeList.value = resp.data.data;
		});
	};

	return { fixedNoticeList, getFixedNoticeList };
});
