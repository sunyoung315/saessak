import axios from 'axios';

const REST_BOARD_API = 'https://i10a706.p.ssafy.io/api/board';

const createBoard = async data => {
	await axios({
		url: `${REST_BOARD_API}`,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
		},
		data,
	});
};

export { createBoard };
