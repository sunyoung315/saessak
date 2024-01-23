<template>
	<div
		class="container mx-16 mb-10 p-1.5 flex flex-wrap justify-normar w-auto border border-gray-200 shadow rounded-lg"
	>
		<!-- Teacher Version -->
		<template v-if="isTeacher">
			<div v-for="child in childrenList" :key="child.kidId">
				<RouterLink
					:to="{ name: 'BoardDetail', params: { id: `${child.kidId}` } }"
					class="m-2.5 bg-cover bg-[url('@/assets/BoardFrame.png')] inline-block w-80 h-96 p-6 bg-white rounded-lg hover:bg-gray-100"
				>
					<div class="relative left-28 top-5 font-extrabold text-lg">
						{{ child.kidName.split('').join(' ') }}
					</div>
					<div class="relative left-9 top-16 w-56">
						<img
							:src="`src/assets/${child.kidProfile}`"
							alt="profile"
							class="w-56 h-52"
						/>
					</div>
				</RouterLink>
			</div>
		</template>

		<!-- Parent Version -->
		<template v-else>
			<div v-for="board in boardList" :key="board.boardId">
				<RouterLink
					:to="{ name: 'BoardDetail', params: { id: `${board.boardId}` } }"
					class="m-2.5 bg-cover bg-[url('@/assets/BoardFrame.png')] inline-block w-80 h-96 p-6 bg-white rounded-lg hover:bg-gray-100"
				>
					<div class="relative left-24 top-5 font-extrabold text-lg">
						{{ board.boardDate }}
					</div>
					<div class="relative left-9 top-16 w-56">
						<img
							:src="`src/assets/${board.boardPath}`"
							alt="profile"
							class="w-56 h-52"
						/>
					</div>
				</RouterLink>
			</div>
		</template>
	</div>
</template>

<script setup>
import { ref } from 'vue';
const isTeacher = ref(false);

const childrenList = [
	{
		kidId: 1,
		kidName: '박나은',
		// kidProfile: 'http://s3.url',
		kidProfile: 'image3.png', //s3 rul
	},
	{
		kidId: 2,
		kidName: '박건후',
		// kidProfile: 'http://s3.url',
		kidProfile: 'image5.png',
	},
	{
		kidId: 3,
		kidName: '박진우',
		// kidProfile: 'http://s3.url',
		kidProfile: 'image8.png',
	},
	{
		kidId: 4,
		kidName: '윌리엄',
		// kidProfile: 'http://s3.url',
		kidProfile: 'image9.png',
	},
];

const boardList = [
	{
		boardId: 1,
		kidId: 1,
		classroomId: 1,
		boardDate: '2024-01-12',
		// boardPath: 'http://s3.url',
		boardPath: 'image1.png',
	},
	{
		boardId: 2,
		kidId: 1,
		classroomId: 1,
		boardDate: '2024-01-13',
		// boardPath: 'http://s3.url',
		boardPath: 'image4.png',
	},
	{
		boardId: 3,
		kidId: 1,
		classroomId: 1,
		boardDate: '2024-01-14',
		// boardPath: 'http://s3.url',
		boardPath: 'image6.png',
	},
	{
		boardId: 4,
		kidId: 1,
		classroomId: 1,
		boardDate: '2024-01-15',
		// boardPath: 'http://s3.url',
		boardPath: 'image8.png',
	},
];
</script>

<style scoped></style>
