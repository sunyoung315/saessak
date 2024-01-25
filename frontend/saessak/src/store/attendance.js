import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_ATTENDANCE_API = 'http://localhost:8080/api/attendance';

export const useAttendanceStore = defineStore('attendance', () => {
	const attendanceList = ref([]);

	const getList = async data => {
		await axios({
			url: `${REST_ATTENDANCE_API}/list`,
			method: 'POST',
			data,
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			attendanceList.value = resp.data.data;
			// 시간 형식 파싱
			attendanceList.value.map(kid => {
				kid.attendance = kid.attendance.map(record => {
					if (record.attendanceInTime) {
						let date = new Date(`1970-01-01T${record.attendanceInTime}`);
						let hours = date.getHours().toString().padStart(2, '0');
						let minutes = date.getMinutes().toString().padStart(2, '0');
						record.attendanceInTime = `${hours}:${minutes}`;
					}
					if (record.attendanceOutTime) {
						let date = new Date(`1970-01-01T${record.attendanceOutTime}`);
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

	return { attendanceList, getList };
});
