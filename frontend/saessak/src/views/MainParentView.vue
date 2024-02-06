<template>
	<div class="main-view">
		<div class="main-header">
			<p class="main-title">{{ props.loginStore.kidList[0].kidName }}</p>
			<p class="main-title">{{ year }}-{{ month }}-{{ day }}</p>
		</div>
		<div class="flex">
			<div class="main-two-third">
				<div class="main-half w-2/3">
					<div class="flex items-end">
						<img src="/icons/board.png" alt="icon" class="w-12" />
						<div class="title">오늘의 알림장</div>
					</div>
					<div class="board-content text-ellipsis overflow-auto scrollbar-hide">
						{{ boardStore.oneBoard.boardContent }}
					</div>
				</div>
				<div class="main-half w-1/3 border-l-2">
					<div class="flex items-end">
						<img src="/icons/board2.png" alt="icon" class="w-12" />
						<div class="title">건강 기록</div>
					</div>
					<div class="board-content">
						<div class="health-item">
							<span class="health-title">체온 : </span>
							<span class="health-content">{{
								boardStore.oneBoard.boardTemperature
							}}</span>
							<span class="pl-1">°C</span>
						</div>
						<div class="health-item">
							<span class="health-title">수면시간 : </span>
							<span class="health-content">{{
								boardStore.oneBoard.boardSleepTime
							}}</span>
							<span class="pl-1">시간</span>
						</div>
						<div class="health-item">
							<span class="health-title">배변상태 : </span>
							<span class="health-content">{{
								boardStore.oneBoard.boardPoopStatus
							}}</span>
						</div>
						<div class="health-item">
							<span class="health-title">키 : </span>
							<span class="health-content">{{
								boardStore.oneBoard.boardTall
							}}</span>
							<span class="pl-1">cm</span>
						</div>
						<div class="health-item">
							<span class="health-title">몸무게 : </span>
							<span class="health-content">{{
								boardStore.oneBoard.boardWeight
							}}</span>
							<span class="pl-1">kg</span>
						</div>
						<RouterLink
							:to="{
								name: 'BoardDetailParent',
								params: { id: boardStore.oneBoard.boardId },
							}"
							class="block font-bold text-right mt-6 mr-2"
							>→ 자세히 보기</RouterLink
						>
					</div>
				</div>
			</div>
			<div class="main-one-third">
				<div class="flex items-end">
					<img src="/icons/notice.png" alt="icon" class="w-12" />
					<div class="title">공지사항</div>
				</div>
			</div>
		</div>
		<div class="main-full">
			<div class="flex items-end">
				<img src="/icons/menu.png" alt="icon" class="w-12" />
				<div class="title">식단표</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useBoardStore } from '@/store/board';

const props = defineProps({
	loginStore: Object,
});
const kidId = props.loginStore.curKid;

const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + today.getDate()).slice(-2);

const boardStore = useBoardStore();

onMounted(async () => {
	boardStore.date = `${year}-${month}-${day}`;
	await boardStore.getOneBoardByDate(kidId);
});
</script>

<style scoped>
.main-view {
	@apply view-frame my-10 bg-gray-100;
}
.main-title {
	@apply text-2xl font-bold inline-block mx-3;
}
.main-header {
	@apply m-7 mb-3;
}
.main-one-third {
	@apply inline m-2 border-2 border-gray-400 rounded w-1/3 h-96 p-6 bg-nav-yellow bg-opacity-50;
}
.main-two-third {
	@apply inline-block flex m-2 border-2 border-gray-400 rounded w-2/3 h-96 bg-nav-yellow bg-opacity-50;
}
.main-half {
	@apply inline border-dashed border-gray-400 h-[23.8rem] p-6;
}
.main-full {
	@apply border-2 border-gray-400 rounded w-auto h-96 m-2 p-6 bg-nav-yellow bg-opacity-50;
}
.title {
	@apply text-xl font-bold mx-2;
}
.board-content {
	@apply block my-6 h-64 leading-7;
}
.health-item {
	@apply flex items-center mb-3;
}
.health-title {
	@apply inline-block w-20 font-bold;
}
.health-content {
	@apply inline-block w-16 h-[2rem] pl-2 border-2 border-gray-300 rounded bg-gray-50;
}
</style>
