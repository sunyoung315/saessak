import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_ATTENDANCE_API = 'http://i10a706.p.ssafy.io:8081/api/attendance';

export const useAttendanceStore = defineStore('attendance', () => {
	// 선생님ver 주차별 출석부
	const weeklyList = ref([]);

	// 선생님ver 주차별 출석부 조회
	const getWeeklyList = async data => {
		await axios({
			url: `${REST_ATTENDANCE_API}/teacher/list`,
			method: 'POST',
			data,
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			weeklyList.value = resp.data.data;
			// 시간 형식 파싱(hh:mm)
			weeklyList.value.map(kid => {
				kid.attendance = kid.attendance.map(record => {
					if (record.attendanceInTime) {
						let date = new Date(`1970-01-01T${record.attendanceInTime}Z`);
						let hours = date.getHours().toString().padStart(2, '0');
						let minutes = date.getMinutes().toString().padStart(2, '0');
						record.attendanceInTime = `${hours}:${minutes}`;
					}
					if (record.attendanceOutTime) {
						let date = new Date(`1970-01-01T${record.attendanceOutTime}Z`);
						let hours = date.getHours().toString().padStart(2, '0');
						let minutes = date.getMinutes().toString().padStart(2, '0');
						record.attendanceOutTime = `${hours}:${minutes}`;
					}
					return record;
				});
				return kid;
			});
		});
	};

	// 학부모ver 내 아이의 출석부
	const oneKidList = ref([]);

	// 학부모ver 내 아이의 출석부 조회
	const getOneKidList = async kidId => {
		await axios({
			url: `${REST_ATTENDANCE_API}/parent/list/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			oneKidList.value = resp.data.data;
		});
	};

	return { weeklyList, getWeeklyList, oneKidList, getOneKidList };
});
