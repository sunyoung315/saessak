<template>
	<div class="view-frame">
		<!-- Teacher Version -->
		<template v-if="isTeacher">
			<div class="book-flex">
				<div v-for="kid in kidsList" :key="kid.kidId">
					<RouterLink
						:to="{ name: 'BoardDetail', params: { id: `${kid.kidId}` } }"
						class="book-frame"
					>
						<div class="book-title-teacher">
							{{ kid.kidName.split('').join(' ') }}
						</div>
						<div class="book-photo-position">
							<img
								:src="`src/assets/${kid.kidProfile}`"
								alt="profile"
								class="book-photo-size"
							/>
						</div>
					</RouterLink>
				</div>
			</div>
		</template>

		<!-- Parent Version -->
		<template v-else>
			<div class="book-flex">
				<div v-for="board in myKidBoards" :key="board.boardId">
					<RouterLink
						:to="{ name: 'BoardDetail', params: { id: `${board.boardId}` } }"
						class="book-frame"
					>
						<div class="book-title-parent">
							{{ board.boardDate }}
						</div>
						<div class="book-photo-position">
							<img
								:src="`src/assets/${board.boardPath}`"
								alt="profile"
								class="book-photo-size"
							/>
						</div>
					</RouterLink>
				</div>
			</div>
		</template>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useBoardStore } from '@/store/board';
import { useUserStore } from '@/store/user';

// 임시 user 변수
const isTeacher = ref(true);
const classroomId = ref(2);
const kidId = ref(1);

const boardStore = useBoardStore();
const userStore = useUserStore();

// 선생님ver 반 아이들 리스트
const kidsList = ref([]);

const getKidsList = async classroomId => {
	await userStore.getKidsList(classroomId);
	kidsList.value = userStore.kidsList;
};

// 학부모ver 내 아이 알림장 리스트
const myKidBoards = ref([]);

const getMyKidBoards = async kidId => {
	await boardStore.getMyKidBoards(kidId);
	myKidBoards.value = boardStore.myKidBoards;
};

onMounted(async () => {
	await getMyKidBoards(kidId.value);
	await getKidsList(classroomId.value);
});
</script>

<style scoped>
.book-flex {
	@apply flex flex-wrap justify-between min-h-screen;
}
.book-frame {
	@apply bg-cover bg-[url('@/assets/BoardFrame.png')] inline-block w-[24rem] h-[29rem] p-6 m-1.5 mb-6 bg-white rounded-lg hover:bg-yellow-50 hover:bg-opacity-65;
}
.book-title-teacher {
	@apply relative left-[9.7rem] top-[2rem] font-extrabold text-lg;
}
.book-title-parent {
	@apply relative left-[8.3rem] top-[2.1rem] font-extrabold text-lg;
}
.book-photo-position {
	@apply relative left-[3rem] top-[5rem];
}
.book-photo-size {
	@apply w-[17rem] h-[16.5rem];
}
</style>
