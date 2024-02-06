import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

// const REST_ALBUM_API = `http://localhost:8080/api/album`;
const REST_ALBUM_API = `https://i10a706.p.ssafy.io/api/album`;
const token = sessionStorage.getItem('accessToken');

export const useAlbumStore = defineStore('album', () => {
	// 반 전체 앨범 조회 (선생님)
	const albumTeacherList = ref([]);
	const getAlbumTeacherList = async function () {
		await axios
			.get(`${REST_ALBUM_API}/classroom`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token,
				},
			})
			.then(response => {
				albumTeacherList.value = response.data.data;
			});
	};

	// 반 전체 앨범 조회 (학부모)
	const albumParentList = ref([]);
	const getAlbumParentList = async function (kidId) {
		await axios.get(`${REST_ALBUM_API}/classroom/${kidId}`).then(response => {
			albumParentList.value = response.data.data;
			// console.log(albumAllList.value[0].fileResponseDtoList);
		});
	};

	// 반 앨범 날짜 조회(Carousel - 학부모)
	// const albumDateAllList = ref([]);
	// const getAlbumDateAllList = async function (classroomId, albumDate) {
	// 	await axios
	// 		.post(`${REST_ALBUM_API}/classroom/${classroomId}`, { albumDate })
	// 		.then(response => {
	// 			getAlbumDateAllList.value = response.data.data;
	// 			// console.log(getAlbumDateAllList.value[1].fileResponseDtoList);
	// 		});
	// };

	// 아이별 앨범 조회
	const kidAlbumList = ref([]);
	const getKidAlbumList = async function (kidId) {
		await axios.get(`${REST_ALBUM_API}/kid/${kidId}`).then(response => {
			kidAlbumList.value = response.data.data;
			// console.log(kidAlbumList);
		});
	};

	// 아이별 앨범날짜 조회 (Carousel - 학부모, CardDetail - 선생님)
	const myKidAlbumDateList = ref([]);
	const getKidAlbumDateList = async function (kidId, albumDate) {
		await axios
			.post(`${REST_ALBUM_API}/kid/${kidId}`, { albumDate })
			.then(response => {
				myKidAlbumDateList.value = response.data.data;
				// console.log('myKidAlbumDateList: ' + myKidAlbumDateList.value);
				// console.log(myKidAlbumDateList.value[0]);
			});
	};

	// 반 아이들의 가장 최신 앨범 리스트 (Carousel-선생님)
	const recentAlbumList = ref([]);
	const getRecentAlbumList = async function () {
		// console.log(recentAlbumList);
		await axios
			.get(`${REST_ALBUM_API}/classroom/kid`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token,
				},
			})
			.then(response => {
				recentAlbumList.value = response.data.data;
				// console.log('recentAlbumList: ' + recentAlbumList.value);
			})
			.catch(error => {
				console.log(error);
			});
	};

	return {
		albumTeacherList,
		getAlbumTeacherList,
		albumParentList,
		getAlbumParentList,
		// albumDateAllList,
		// getAlbumDateAllList,
		kidAlbumList,
		getKidAlbumList,
		myKidAlbumDateList,
		getKidAlbumDateList,
		recentAlbumList,
		getRecentAlbumList,
	};
});
