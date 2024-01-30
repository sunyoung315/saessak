import axios from 'axios';

const LOCAL_API = 'http://localhost:8080/api/board';
// const REST_BOARD_API = 'https://i10a706.p.ssafy.io/api%EB%A1%9C';

const createBoard = async data => {
	await axios({
		url: `${LOCAL_API}`,
		// url: `${REST_BOARD_API}`,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		data,
	});
};

export { createBoard };
