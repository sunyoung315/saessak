import axios from 'axios';

const REST_MENU_API = 'https://i10a706.p.ssafy.io/api/menu';

const createMenu = async data => {
	console.log(data);
	await axios({
		url: `${REST_MENU_API}/`,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			Authorization: 'Bearer ' + sessionStorage.getItem('accessToken'),
		},
		data,
	})
		.then(resp => {
			console.log(resp.data.data);
		})
		.catch(err => {
			console.log(err);
		});
};

const registMenuPhoto = async formData => {
	try {
		await axios({
			url: `${REST_MENU_API}/photo`,
			method: 'POST',
			data: formData,
			headers: {
				'Content-Type': 'multipart/form-data',
				Authorization: 'Bearer ' + sessionStorage.getItem('accessToken'),
			},
		});
		// 사진 등록 성공
		return true;
	} catch (error) {
		// 사진 등록 실패
		return false;
	}
};

export { createMenu, registMenuPhoto };
