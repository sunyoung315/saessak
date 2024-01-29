// import { ref } from 'vue';
import axios from 'axios';

const LOCAL_API = 'http://localhost:8080/api/board';
// const REST_BOARD_API = 'http://i10a706.p.ssafy.io:8081/api/board';

const createBoard = async data => {
	await axios({
		// url: `${REST_BOARD_API}`,
		url: `${LOCAL_API}`,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		data,
	});
};

export { createBoard };
