<template>
	<div class="view-frame p-4">
		<!-- Teacher Version -->
		<template v-if="props.loginStore.isTeacher">
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
								:src="kid.kidProfile"
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
				<div v-for="(board, index) in myKidBoards" :key="board.boardId">
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
						<template v-if="board.boardPath">
							<div class="book-photo-position">
								<img
									:src="board.boardPath"
									alt="profile"
									class="book-photo-size"
								/>
							</div>
						</template>
						<template v-else>
							<div class="relative left-[3.2rem] top-[3.6rem]">
								<img
									:src="`/toys/${toys[index % toys.length]}`"
									alt="toys"
									class="w-[8.5rem] h-[8.5rem]"
								/>
							</div>
						</template>
					</RouterLink>
				</div>
			</div>
		</template>
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useBoardStore } from '@/store/board';
import { useUserStore } from '@/store/user';

// 로그인 유저
const props = defineProps({
	loginStore: Object,
	year: Number,
	month: Number,
});

// const kidId = props.loginStore.isTeacher ? undefined : props.loginStore.curKid;
const kidId = props.loginStore.isTeacher
	? undefined
	: props.loginStore.kidList[0].kidId;

const boardStore = useBoardStore();
const userStore = useUserStore();

// 선생님ver 반 아이들 리스트
const kidsList = ref([]);

const getKidsList = async () => {
	await userStore.getKidsList();
	kidsList.value = userStore.kidsList;
};

const year = props.year;
const month = props.month;

// 학부모ver 내 아이 알림장 리스트
const myKidBoards = ref([]);

const getMyKidMonthlyBoards = async (year, month) => {
	await boardStore.getMyKidMonthlyBoards(kidId, year, month);
	myKidBoards.value = boardStore.myKidBoards;
};

watch(
	() => boardStore.myKidBoards,
	newVal => {
		myKidBoards.value = newVal;
	},
);

const toys = ref([
	'colors.png',
	'teddy-bear.png',
	'robot.png',
	'aeroplane.png',
	'rubber-duck.png',
	'bicycle.png',
	'blocks1.png',
	'bucket.png',
	'blocks2.png',
	'rocking-horse.png',
	'toy-train.png',
	'xylophone.png',
]);

onMounted(async () => {
	if (!props.loginStore.isTeacher) {
		await getMyKidMonthlyBoards(year, month);
		return;
	}
	if (props.loginStore.isTeacher) {
		await getKidsList();
		return;
	}
});
</script>

<style scoped>
.book-flex {
	@apply flex flex-wrap justify-start;
}
.book-frame {
	@apply bg-[url('@/assets/board-frame.png')] bg-book inline-block w-[13.77rem] h-[16.4rem] mx-3 my-4 rounded-lg bg-yellow-50 hover:my-0.5;
}
.book-title-teacher {
	@apply relative left-[4.4rem] top-[1.8rem] font-semibold text-base w-24 text-center;
}
.book-title-parent {
	@apply relative left-[5.5rem] top-[1.9rem] mb-1 font-semibold text-sm;
}
.book-photo-position {
	@apply relative left-[2.5rem] top-[3.2rem];
}
.book-photo-size {
	@apply w-[9.8rem] h-[9.4rem];
}
</style>
