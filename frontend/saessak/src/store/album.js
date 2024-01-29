import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

const REST_ALBUM_API = `http://localhost:8080/api/album`;
// const REST_ALBUM_API = `http://i10a706.p.ssafy.io:8081/api/album`;

export const useAlbumStore = defineStore('album', () => {
	// 반 전체 앨범 조회
	const albumAllList = ref([]);
	const getAlbumAllList = async function (classroomId) {
		await axios
			.get(`${REST_ALBUM_API}/classroom/${classroomId}`)
			.then(response => {
				albumAllList.value = response.data.data;

				// console.log(albumAllList.value[0].fileResponseDtoList);
			});
	};
	// 반 앨범 날짜 조회(Carousel - 학부모)
	const albumDateAllList = ref([]);
	const getAlbumDateAllList = async function (classroomId) {
		await axios
			.post(`${REST_ALBUM_API}/classroom/${classroomId}`)
			.then(response => {
				getAlbumDateAllList.value = response.data.data;
				// console.log(getAlbumDateAllList.value[1].fileResponseDtoList);
			});
	};

	// 아이별 앨범 조회
	const kidAlbumList = ref([]);
	const getkidAlbumList = async function (kidId) {
		await axios.get(`${REST_ALBUM_API}/kid/${kidId}`).then(response => {
			kidAlbumList.value = response.data.data;
			// console.log(kidAlbumList);
		});
	};

	// 아이별 앨범날짜 조회 (Carousel - 학부모, CardDetail - 선생님)
	const myKidAlbumDateList = ref([]);
	const getkidAlbumDateList = async function (kidId, albumDate) {
		await axios
			.post(`${REST_ALBUM_API}/kid/${kidId}`, { albumDate })
			.then(response => {
				myKidAlbumDateList.value = response.data.data;
				console.log('myKidAlbumDateList: ' + myKidAlbumDateList.value);
				console.log(myKidAlbumDateList.value[0]);
			});
	};

	// 반 아이들의 가장 최신 앨범 리스트 (Carousel-선생님)
	const recentAlbumList = ref([]);
	const getRecentAlbumList = async function (classroomId) {
		await axios
			.get(`${REST_ALBUM_API}/classroom/kid/${classroomId}`)
			.then(response => {
				recentAlbumList.value = response.data.data;
				// console.log('recentAlbumList: ' + recentAlbumList.value);
			});
	};

	return {
		albumAllList,
		getAlbumAllList,
		albumDateAllList,
		getAlbumDateAllList,
		kidAlbumList,
		getkidAlbumList,
		myKidAlbumDateList,
		getkidAlbumDateList,
		recentAlbumList,
		getRecentAlbumList,
	};
});
