import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

// const REST_ALBUM_API = `http://http://localhost:8080/api/album`;
const REST_ALBUM_API = `http://i10a706.p.ssafy.io:8081/api/album`;

export const useAlbumStore = defineStore('album', () => {
	// 반 전체 앨범 조회
	const AlbumAllList = ref([]);
	const getAlbumAllList = async function (classroom) {
		await axios.get(`${REST_ALBUM_API}/class/${classroom}`).then(response => {
			AlbumAllList.value = response.data.data;
			// console.log(AlbumAllList.value[1].fileResponseDtoList);
		});
	};
	// 아이별 앨범 조회 (선생님용)
	const kidAlbumList = ref([]);
	const getkidAlbumList = async function (kidId) {
		await axios.get(`${REST_ALBUM_API}/kid/${kidId}`).then(response => {
			kidAlbumList.value = response.data.data;
			// console.log(kidAlbumList);
		});
	};

	return {
		AlbumAllList,
		getAlbumAllList,
		kidAlbumList,
		getkidAlbumList,
	};
});
