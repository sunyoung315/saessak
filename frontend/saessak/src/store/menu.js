import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

const REST_MENU_API = 'https://i10a706.p.ssafy.io/api/menu';

export const useMenuStore = defineStore('menu', () => {
	const allergyList = ref([
		{
			allergyId: 1,
			allergyName: '난류',
		},
		{
			allergyId: 2,
			allergyName: '우유',
		},
		{
			allergyId: 3,
			allergyName: '메밀',
		},
		{
			allergyId: 4,
			allergyName: '땅콩',
		},
		{
			allergyId: 5,
			allergyName: '대두',
		},
		{
			allergyId: 6,
			allergyName: '밀',
		},
		{
			allergyId: 7,
			allergyName: '고등어',
		},
		{
			allergyId: 8,
			allergyName: '게',
		},
		{
			allergyId: 9,
			allergyName: '새우',
		},
		{
			allergyId: 10,
			allergyName: '돼지고기',
		},
		{
			allergyId: 11,
			allergyName: '복숭아',
		},
		{
			allergyId: 12,
			allergyName: '토마토',
		},
		{
			allergyId: 13,
			allergyName: '아황산류',
		},
		{
			allergyId: 14,
			allergyName: '호두',
		},
		{
			allergyId: 15,
			allergyName: '닭고기',
		},
		{
			allergyId: 16,
			allergyName: '쇠고기',
		},
		{
			allergyId: 17,
			allergyName: '오징어',
		},
		{
			allergyId: 18,
			allergyName: '조개류',
		},
		{
			allergyId: 19,
			allergyName: '잣',
		},
	]);

	// 주차별 식단표
	const weeklyMenu = ref([]);

	// 선생님ver 식단표 호출
	const getTeacherWeeklyMenu = async weeklyDate => {
		axios({
			url: `${REST_MENU_API}/teacher/list`,
			method: 'POST',
			data: weeklyDate,
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + sessionStorage.getItem('accessToken'),
			},
		}).then(resp => {
			if (resp.data.data.length) {
				weeklyMenu.value = resp.data.data;
				for (let i = 0; i < weeklyMenu.value.length; i++) {
					if (weeklyMenu.value[i].menuDate === todayDate) {
						if (weeklyMenu.value[i].menuType === '점심') {
							isTodayLunch.value = true;
							let flatArr = []
								.concat(
									...weeklyMenu.value[i].foodList.map(food =>
										food.foodAllergy.split('/'),
									),
								)
								.filter(item => item !== '')
								.sort((a, b) => a - b);
							todayLunchAllergy.value = [...new Set(flatArr)];
						} else {
							isTodaySnack.value = true;
							let flatArr = []
								.concat(
									...weeklyMenu.value[i].foodList.map(food =>
										food.foodAllergy.split('/'),
									),
								)
								.filter(item => item !== '')
								.sort((a, b) => a - b);
							todaySnackAllergy.value = [...new Set(flatArr)];
						}
					}
				}
			} else {
				weeklyMenu.value = null;
			}
		});
	};

	// 학부모ver 식단표 호출
	const todayLunchAllergy = ref([]);
	const todaySnackAllergy = ref([]);

	const isTodayLunch = ref(false);
	const isTodaySnack = ref(false);

	const today = new Date();
	const year = today.getFullYear();
	const month = ('0' + (today.getMonth() + 1)).slice(-2);
	const day = ('0' + today.getDate()).slice(-2);
	const todayDate = `${year}-${month}-${day}`;
	// const todayDate = '2024-02-01';

	const getParentWeeklyMenu = async (weeklyDate, kidId) => {
		axios({
			url: `${REST_MENU_API}/parent/list/${kidId}`,
			method: 'POST',
			data: weeklyDate,
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + sessionStorage.getItem('accessToken'),
			},
		}).then(resp => {
			if (resp.data.data.length) {
				weeklyMenu.value = resp.data.data;
				for (let i = 0; i < weeklyMenu.value.length; i++) {
					if (weeklyMenu.value[i].menuDate === todayDate) {
						if (weeklyMenu.value[i].menuType === '점심') {
							isTodayLunch.value = true;
							let flatArr = []
								.concat(
									...weeklyMenu.value[i].foodList.map(food =>
										food.foodAllergy.split('/'),
									),
								)
								.filter(item => item !== '')
								.sort((a, b) => a - b);
							todayLunchAllergy.value = [...new Set(flatArr)];
						} else {
							isTodaySnack.value = true;
							let flatArr = []
								.concat(
									...weeklyMenu.value[i].foodList.map(food =>
										food.foodAllergy.split('/'),
									),
								)
								.filter(item => item !== '')
								.sort((a, b) => a - b);
							todaySnackAllergy.value = [...new Set(flatArr)];
						}
					}
				}
			} else {
				weeklyMenu.value = null;
			}
		});
	};

	return {
		allergyList,
		weeklyMenu,
		getTeacherWeeklyMenu,
		getParentWeeklyMenu,
		todayLunchAllergy,
		todaySnackAllergy,
		isTodayLunch,
		isTodaySnack,
	};
});
