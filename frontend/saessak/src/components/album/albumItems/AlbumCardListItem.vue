<template>
	<div>
		<div class="flex flex-wrap justify-around">
			<div v-for="album in albumAllList" :key="album.albumId" class="my-2">
				<div v-for="file in album.fileResponseDtoList" :key="file.fileId">
					<input
						type="checkbox"
						:id="file.fileId"
						:value="`${file.fileId}`"
						class="hidden peer"
						v-model="checked"
					/>
					<label
						:for="file.fileId"
						class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-blue-600 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
					>
						<img
							class="album rounded-t-lg"
							:src="`src/assets/${file.image}`"
							:for="file.fileId"
							alt="img"
						/>
					</label>
				</div>
			</div>
		</div>
		<br />
		<span>Check 이미지 : {{ checked }}</span>
	</div>
</template>

<script setup>
import { ref } from 'vue';
const checked = ref([]);

const albumAllList = [
	{
		albumId: 1,
		albumDate: '2024-11-11',
		albumTitle: '1월 생일자 사진',
		fileResponseDtoList: [
			{
				fileId: 1,
				fileName: '1사진',
				filePath: 'uuid1',
				image: 'image1.png',
			},
			{
				fileId: 2,
				fileName: '2사진',
				filePath: 'uuid2',
				image: 'image2.png',
			},
			{
				fileId: 3,
				fileName: '3사진',
				filePath: 'uuid3',
				image: 'image3.png',
			},
			{
				fileId: 4,
				fileName: '4사진',
				filePath: 'uuid4',
				image: 'image4.png',
			},
		],
	},
	{
		albumId: 3,
		albumDate: '2024-06-13',
		albumTitle: '6월 생일자 사진',
		fileResponseDtoList: [
			{
				fileId: 5,
				fileName: '5사진',
				filePath: 'uuid5',
				image: 'image5.png',
			},
			{
				fileId: 6,
				fileName: '6사진',
				filePath: 'uuid6',
				image: 'image6.png',
			},
			{
				fileId: 8,
				fileName: '8사진',
				filePath: 'uuid8',
				image: 'image8.png',
			},
		],
	},
	{
		albumId: 4,
		albumDate: '2024-12-13',
		albumTitle: '12월 생일자 사진',
		fileResponseDtoList: [
			{
				fileId: 7,
				fileName: '7사진',
				filePath: 'uuid7',
				image: 'image7.png',
			},
		],
	},
];

// 더미 데이터
const albumList = [
	{
		id: '사진1',
		name: '박나은',
		image: 'image1.png',
		date: '2022-01-02',
		checked: false,
	},
	{
		id: '사진2',
		name: '박나은',
		image: 'image2.png',
		date: '2022-02-07',
		checked: false,
	},
	{
		id: '사진3',
		name: '박나은',
		image: 'image3.png',
		date: '2022-03-02',
		checked: false,
	},
	{
		id: '사진4',
		name: '박건후',
		image: 'image4.png',
		date: '2022-03-11',
		checked: false,
	},
	{
		id: '사진5',
		name: '박나은',
		image: 'image5.png',
		date: '2022-07-02',
		checked: false,
	},
	{
		id: '사진6',
		name: '박건후',
		image: 'image6.png',
		date: '2022-08-04',
		checked: false,
	},
	{
		id: '사진7',
		name: '박건후',
		image: 'image7.png',
		date: '2022-09-16',
		checked: false,
	},
	{
		id: '사진8',
		name: '박건후',
		image: 'image8.png',
		date: '2022-11-25',
		checked: false,
	},
	{
		id: '사진9',
		name: '박건후',
		image: 'image9.png',
		date: '2022-12-08',
		checked: false,
	},
]; // 더미 데이터 끝
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
