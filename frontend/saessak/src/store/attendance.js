import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { format } from 'date-fns';

const LOCAL_API = 'http://localhost:8080/api/attendance';
// const REST_ATTENDANCE_API = 'http://i10a706.p.ssafy.io:8081/api/attendance';

export const useAttendanceStore = defineStore('attendance', () => {
	// 선생님ver 주차별 출석부
	const weeklyList = ref([]);

	// 오늘 날짜
	const today = ref(format(new Date(), 'yyyy-MM-dd'));

	// 오늘 등원 시간 리스트
	const todayInTimeList = ref([]);
	// 오늘 하원 시간 리스트
	const todayOutTimeList = ref([]);

	// 선생님ver 주차별 출석부 조회
	const getWeeklyList = async data => {
		await axios({
			// url: `${REST_ATTENDANCE_API}/teacher/list`,
			url: `${LOCAL_API}/teacher/list`,
			method: 'POST',
			data,
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			weeklyList.value = resp.data.data;
			// 주차별 출석부 리스트
			// 시간 형식 파싱(hh:mm)
			weeklyList.value.map(kid => {
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
			//오늘 등원 리스트
			todayInTimeList.value = weeklyList.value
				.map(kid => {
					const attendance = kid.attendance.find(
						oneday => oneday.attendanceDate === today.value,
					);
					if (attendance) {
						return { kidId: kid.kidId, inTime: attendance.attendanceInTime };
					}
				})
				.filter(Boolean);
			// 오늘 하원 리스트
			todayOutTimeList.value = weeklyList.value
				.map(kid => {
					const attendance = kid.attendance.find(
						oneday => oneday.attendanceDate === today.value,
					);
					if (attendance) {
						return { kidId: kid.kidId, outTime: attendance.attendanceOutTime };
					}
				})
				.filter(Boolean);
		});
	};

	// 학부모ver 내 아이의 출석부
	const oneKidList = ref([]);

	// 학부모ver 내 아이의 출석부 조회
	const getOneKidList = async kidId => {
		await axios({
			// url: `${REST_ATTENDANCE_API}/parent/list/${kidId}`,
			url: `${LOCAL_API}/parent/list/${kidId}`,
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			oneKidList.value = resp.data.data;
			// 시간 형식 파싱(hh:mm)
			oneKidList.value.map(record => {
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
		});
	};

	// 등원 알람 요청
	const kidIn = async kidId => {
		await axios({
			// url: `${REST_ATTENDANCE_API}/in/${kidId}`,
			url: `${LOCAL_API}/in/${kidId}`,
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			let time = resp.data.data.attendanceTime;
			let date = new Date(`1970-01-01T${time}`);
			let hours = date.getHours().toString().padStart(2, '0');
			let minutes = date.getMinutes().toString().padStart(2, '0');
			todayInTimeList.value.push({ kidId, inTime: `${hours}:${minutes}` });
		});
	};

	// 하원 알랑 요청
	const kidOut = async kidId => {
		await axios({
			// url: `${REST_ATTENDANCE_API}/out/${kidId}`,
			url: `${LOCAL_API}/out/${kidId}`,
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(resp => {
			let time = resp.data.data.attendanceTime;
			let date = new Date(`1970-01-01T${time}`);
			let hours = date.getHours().toString().padStart(2, '0');
			let minutes = date.getMinutes().toString().padStart(2, '0');
			todayOutTimeList.value.push({ kidId, outTime: `${hours}:${minutes}` });
		});
	};

	return {
		weeklyList,
		getWeeklyList,
		oneKidList,
		getOneKidList,
		kidIn,
		kidOut,
		todayInTimeList,
		todayOutTimeList,
	};
});
