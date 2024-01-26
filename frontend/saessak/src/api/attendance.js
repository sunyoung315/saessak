import { ref } from 'vue';
import axios from 'axios';

const REST_ATTENDANCE_API = 'http://i10a706.p.ssafy.io:8081/api/attendance';

// 등원 시간
const inTime = ref('');
// 하원 시간
const outTime = ref('');

const kidIn = async kidId => {
	await axios({
		url: `${REST_ATTENDANCE_API}/in/${kidId}`,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
	}).then(resp => {
		let time = resp.data.data.attendanceTime;
		let date = new Date(`1970-01-01T${time}Z`);
		let hours = date.getHours().toString().padStart(2, '0');
		let minutes = date.getMinutes().toString().padStart(2, '0');
		inTime.value = `${hours}:${minutes}`;
	});
};

const kidOut = async kidId => {
	await axios({
		url: `${REST_ATTENDANCE_API}/out/${kidId}`,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
	}).then(resp => {
		let time = resp.data.attendanceTime;
		let date = new Date(`1970-01-01T${time}Z`);
		let hours = date.getHours().toString().padStart(2, '0');
		let minutes = date.getMinutes().toString().padStart(2, '0');
		outTime.value = `${hours}:${minutes}`;
	});
};

export { inTime, outTime, kidIn, kidOut };
