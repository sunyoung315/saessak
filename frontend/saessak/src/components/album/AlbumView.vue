<template>
	<div>
		<div class="flex justify-between items-center">
			<div class="flex">
				<p>AlbumView</p>
				<label class="relative inline-flex items-center me-5 cursor-pointer">
					<input type="checkbox" value="" class="sr-only peer" checked />
					<div
						class="w-11 h-6 bg-gray-200 rounded-full peer dark:bg-gray-700 peer-focus:ring-4 peer-focus:ring-green-300 dark:peer-focus:ring-green-800 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-green-600"
					></div>
					<span
						class="ms-3 text-sm font-medium text-gray-900 dark:text-gray-300"
						>전체 보기</span
					>
				</label>
			</div>
			<div>
				<button
					type="button"
					class="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					추가
				</button>
			</div>
		</div>
		<div class="flex flex-wrap justify-around">
			<div v-for="album in albumList" :key="album.id">
				<input
					type="checkbox"
					:id="album.id"
					value="album.image"
					class="hidden peer"
					required=""
				/>
				<label
					:for="album.id"
					class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-2 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-blue-600 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
				>
					<img
						class="album rounded-t-lg"
						:src="`src/assets/${album.image}`"
						:for="album.id"
						alt="img"
					/>
				</label>
			</div>
		</div>
	</div>
</template>

<script setup>
const albumList = [
	{ id: '사진1', image: 'image1.png' },
	{ id: '사진2', image: 'image2.png' },
	{ id: '사진3', image: 'image3.png' },
	{ id: '사진4', image: 'image4.png' },
	{ id: '사진5', image: 'image5.png' },
	{ id: '사진6', image: 'image6.png' },
	{ id: '사진7', image: 'image7.png' },
	{ id: '사진8', image: 'image8.png' },
	{ id: '사진9', image: 'image9.png' },
];
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
