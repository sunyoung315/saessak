<template>
	<div class="view-frame">
		<!-- Teacher Version -->
		<template v-if="isTeacher">
			<div class="book-flex">
				<div v-for="kid in kidsList" :key="kid.kidId">
					<RouterLink
						:to="{
							name: 'BoardDetailTeacher',
							params: { id: `${kid.kidId}` },
						}"
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
						:to="{
							name: 'BoardDetailParent',
							params: { id: `${board.boardId}` },
						}"
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
const isTeacher = ref(false);
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
	@apply flex flex-wrap justify-start;
}
.book-frame {
	@apply bg-[url('@/assets/BoardFrame.png')] bg-72 inline-block w-[18rem] h-[21.5rem] p-6 mx-7 my-4 bg-white rounded-lg hover:bg-yellow-50 hover:bg-opacity-65;
}
.book-title-teacher {
	@apply relative left-[6.7rem] top-[1rem] font-extrabold text-base;
}
.book-title-parent {
	@apply relative left-[5.4rem] top-[1.1rem] font-extrabold text-base;
}
.book-photo-position {
	@apply relative left-[1.8rem] top-[3.1rem];
}
.book-photo-size {
	@apply w-[17rem] h-[16.5rem];
}
</style>
