<template>
	<div class="main-view view-frame">
		<div class="main-header">
			<p class="main-title text-2xl">
				{{ props.loginStore.kidList[0].kidName }}
			</p>
			<p class="main-title text-lg">{{ todayDate }}</p>
		</div>
		<div class="flex">
			<div class="main-two-third">
				<div class="main-half w-2/3">
					<div class="flex items-end">
						<img src="/icons/board.png" alt="icon" class="w-12" />
						<div class="title">오늘의 알림장</div>
					</div>
					<template v-if="boardStore.oneBoard">
						<div class="board-content whitespace-pre-line overflow-auto">
							{{ boardStore.oneBoard.boardContent }}
						</div>
					</template>
					<template v-else>
						<div class="px-6 pt-8">등록된 알림장이 없습니다.</div>
					</template>
				</div>
				<template v-if="boardStore.oneBoard">
					<div class="main-half w-1/3 border-l-2">
						<div class="flex items-end">
							<img src="/icons/board2.png" alt="icon" class="w-12" />
							<div class="title">건강 기록</div>
						</div>
						<div class="board-content">
							<template v-if="boardStore.oneBoard.boardTemperature">
								<div class="health-item">
									<span class="health-title">체온 : </span>
									<span class="health-content">{{
										boardStore.oneBoard.boardTemperature
									}}</span>
									<span class="pl-1">°C</span>
								</div>
							</template>
							<template v-if="boardStore.oneBoard.boardSleepTime">
								<div class="health-item">
									<span class="health-title">수면시간 : </span>
									<span class="health-content">{{
										boardStore.oneBoard.boardSleepTime
									}}</span>
									<span class="pl-1">시간</span>
								</div>
							</template>
							<template v-if="boardStore.oneBoard.boardPoopStatus">
								<div class="health-item">
									<span class="health-title">배변상태 : </span>
									<span class="health-content">{{
										boardStore.oneBoard.boardPoopStatus
									}}</span>
								</div>
							</template>
							<template v-if="boardStore.oneBoard.boardTall">
								<div class="health-item">
									<span class="health-title">키 : </span>
									<span class="health-content">{{
										boardStore.oneBoard.boardTall
									}}</span>
									<span class="pl-1">cm</span>
								</div>
							</template>
							<template v-if="boardStore.oneBoard.boardWeight">
								<div class="health-item">
									<span class="health-title">몸무게 : </span>
									<span class="health-content">{{
										boardStore.oneBoard.boardWeight
									}}</span>
									<span class="pl-1">kg</span>
								</div>
							</template>
							<template v-if="boardStore.oneBoard.boardId">
								<RouterLink
									:to="{
										name: 'BoardDetailParent',
										params: { id: boardStore.oneBoard.boardId },
									}"
									class="block font-bold text-right"
									>→ 자세히 보기</RouterLink
								>
							</template>
						</div>
					</div>
				</template>
			</div>
			<div class="main-one-third">
				<div class="flex justify-between items-center">
					<div class="flex items-end">
						<img src="/icons/notice.png" alt="icon" class="w-12" />
						<div class="title">공지사항</div>
					</div>
					<div v-if="noticeList.length">
						<button @click="minus" class="m-3" :disabled="isMin">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width="7"
								height="12"
								viewBox="0 0 7 12"
								fill="none"
							>
								<path
									fill-rule="evenodd"
									clip-rule="evenodd"
									d="M6.70711 0.292893C6.34662 -0.0675907 5.77939 -0.0953203 5.3871 0.209705L5.29289 0.292893L0.292893 5.29289C-0.0675907 5.65338 -0.0953203 6.22061 0.209705 6.6129L0.292893 6.70711L5.29289 11.7071C5.68342 12.0976 6.31658 12.0976 6.70711 11.7071C7.06759 11.3466 7.09532 10.7794 6.7903 10.3871L6.70711 10.2929L2.415 6L6.70711 1.70711C7.06759 1.34662 7.09532 0.779392 6.7903 0.387101L6.70711 0.292893Z"
									:fill="isMin ? '#cccccc' : '#000000'"
								/>
							</svg>
						</button>
						<button @click="plus" class="m-3" :disabled="isMax">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width="7"
								height="12"
								viewBox="0 0 7 12"
								fill="none"
							>
								<path
									d="M0.292893 0.292893C0.653377 -0.0675907 1.22061 -0.0953203 1.6129 0.209705L1.70711 0.292893L6.70711 5.29289C7.06759 5.65338 7.09532 6.22061 6.7903 6.6129L6.70711 6.70711L1.70711 11.7071C1.31658 12.0976 0.683418 12.0976 0.292893 11.7071C-0.0675907 11.3466 -0.0953203 10.7794 0.209705 10.3871L0.292893 10.2929L4.585 6L0.292893 1.70711C-0.0675907 1.34662 -0.0953203 0.779392 0.209705 0.387101L0.292893 0.292893Z"
									:fill="isMax ? '#cccccc' : '#000000'"
								/>
							</svg>
						</button>
					</div>
				</div>
				<div v-if="noticeList.length">
					<span class="inline-block mt-6 mx-4 text-lg font-bold">
						{{ noticeList[noticeNum].noticeTitle }}
					</span>
					<div
						v-if="noticeList[noticeNum].fileFlag"
						class="inline-block rotate-90"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="20"
							height="11"
							viewBox="0 0 20 11"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M14.5 0C17.5376 0 20 2.46243 20 5.5C20 8.46348 17.6562 10.8795 14.7212 10.9956L14.5 11H3C2.44772 11 2 10.5523 2 10C2 9.48716 2.38604 9.06449 2.88338 9.00673L3 9H14.5C16.433 9 18 7.433 18 5.5C18 3.63144 16.5357 2.10487 14.692 2.00518L14.5 2H4C2.89543 2 2 2.89543 2 4C2 5.05436 2.81588 5.91817 3.85074 5.99451L4 6H13.5C13.7761 6 14 5.77614 14 5.5C14 5.25454 13.8231 5.05039 13.5899 5.00806L13.5 5H6C5.44772 5 5 4.55228 5 4C5 3.48716 5.38604 3.06449 5.88338 3.00673L6 3H13.5C14.8807 3 16 4.11929 16 5.5C16 6.82548 14.9685 7.91004 13.6644 7.99468L13.5 8H4C1.79086 8 0 6.20914 0 4C0 1.8578 1.68397 0.108921 3.80036 0.00489531L4 0H14.5Z"
								fill="#000000"
							/>
						</svg>
					</div>
					<div
						class="mt-4 mx-4 h-[10.6rem] text-base leading-5 overflow-auto whitespace-pre-line"
					>
						{{ noticeList[noticeNum].noticeContent }}
					</div>
					<div class="mt-5 mr-2">
						<RouterLink
							class="block font-bold text-right"
							:to="{
								name: 'NoticeDetail',
								params: { noticeId: noticeList[noticeNum].noticeId },
							}"
						>
							→ 자세히 보기
						</RouterLink>
					</div>
				</div>
				<template v-else>
					<div class="m-6">즐겨찾기한 공지사항이 없습니다.</div>
				</template>
			</div>
		</div>
		<div class="main-full">
			<div class="flex">
				<div class="menu-item m-0 p-0">
					<div class="flex items-center mt-1">
						<span class="ml-3 pr-3 text-base font-bold">구분 : </span>
						<select
							id="name"
							class="selection-input w-20 h-10 text-sm"
							v-model="menuType"
							required
						>
							<option value="점심">점심</option>
							<option value="간식">간식</option>
						</select>
					</div>
					<template v-if="menuType === '점심' && !menuStore.isTodayLunch">
						<div class="m-6">등록된 식단표가 없습니다.</div>
					</template>
					<template v-if="menuType === '간식' && !menuStore.isTodaySnack">
						<div class="m-6">등록된 식단표가 없습니다.</div>
					</template>
					<template
						v-for="(menu, index) in menuStore.weeklyMenu"
						:key="menu.menuId"
					>
						<template
							v-if="menu.menuDate === todayDate && menu.menuType === menuType"
						>
							<template v-if="menuStore.weeklyMenu[index].menuPath">
								<img
									:src="menuStore.weeklyMenu[index].menuPath"
									alt="메뉴사진"
									class="menu-image"
								/>
							</template>
							<template v-else>
								<img src="/icons/tray.png" alt="null" class="menu-image" />
							</template>
						</template>
					</template>
				</div>
				<template
					v-for="(menu, index) in menuStore.weeklyMenu"
					:key="menu.menuId"
				>
					<template
						v-if="menu.menuDate === todayDate && menu.menuType === menuType"
					>
						<div class="menu-item-color flex">
							<div class="menu-item-type">
								<img src="/icons/menu.png" alt="icon" class="block w-12 mb-3" />
								<div class="font-bold text-xl">
									오늘의 {{ menuStore.weeklyMenu[index].menuType }}
								</div>
							</div>
							<div class="menu-item-foods">
								<template
									v-for="food in menuStore.weeklyMenu[index].foodList"
									:key="food.foodId"
								>
									<div class="leading-[3rem] font-bold text-lg">
										{{ food.foodName }}
									</div>
								</template>
							</div>
						</div>
						<div class="menu-item-color flex">
							<div class="menu-item-type">
								<img
									src="/icons/allergy.png"
									alt="icon"
									class="block w-12 mb-3"
								/>
								<div class="font-bold text-xl">알레르기</div>
								<div class="font-bold text-xl">유발식품</div>
							</div>
							<div class="menu-item-foods">
								<template v-if="menuType === '점심'">
									<template
										v-for="allergy in menuStore.todayLunchAllergy"
										:key="allergy"
									>
										<template
											v-for="i in menuStore.allergyList"
											:key="i.allergyId"
										>
											<template v-if="i.allergyId == allergy">
												<div class="leading-10 font-bold text-lg">
													{{ allergy }}. {{ i.allergyName }}
												</div>
											</template>
										</template>
									</template>
								</template>
								<template v-if="menuType === '간식'">
									<template
										v-for="allergy in menuStore.todaySnackAllergy"
										:key="allergy"
									>
										<template
											v-for="i in menuStore.allergyList"
											:key="i.allergyId"
										>
											<template v-if="i.allergyId == allergy">
												<div class="leading-[3rem] font-bold text-lg">
													{{ allergy }}. {{ i.allergyName }}
												</div>
											</template>
										</template>
									</template>
								</template>
							</div>
						</div>
					</template>
				</template>
			</div>
		</div>
	</div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { useBoardStore } from '@/store/board';
import { useMenuStore } from '@/store/menu';
import { useNoticeStore } from '@/store/notice';
import { getWeekOfMonth } from 'date-fns';

const props = defineProps({
	loginStore: Object,
});
const kidId = props.loginStore.curKid;

const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + today.getDate()).slice(-2);

const todayDate = `${year}-${month}-${day}`;
const week = getWeekOfMonth(today, { weekStartsOn: 1 });

const boardStore = useBoardStore();
const menuStore = useMenuStore();
const noticeStore = useNoticeStore();

const oneBoard = ref(null);

const menuType = ref('점심');

const noticeList = ref([]);
const noticeNum = ref(0);

const isMin = computed(() => noticeNum.value === 0);
const isMax = computed(() => noticeNum.value === noticeList.value.length - 1);

const minus = () => {
	if (!isMin.value) {
		noticeNum.value--;
	}
};

const plus = () => {
	if (!isMax.value) {
		noticeNum.value++;
	}
};

onMounted(async () => {
	boardStore.date = `${year}-${month}-${day}`;
	await boardStore.getOneBoardByDate(kidId);

	if (boardStore.oneBoard) {
		oneBoard.value = boardStore.oneBoard;
	}

	await menuStore.getParentWeeklyMenu({ year, month, week }, kidId);

	await noticeStore.getFixedNoticeList(kidId);
	noticeList.value = noticeStore.fixedNoticeList;
});
</script>

<style scoped>
.main-view {
	@apply my-10 bg-gray-100;
}
.main-title {
	@apply font-bold inline-block mx-3;
}
.main-header {
	@apply m-7 mb-3 flex justify-between items-end;
}
.main-one-third {
	@apply inline m-2 border-2 border-gray-400 rounded w-1/3 h-96 p-6 bg-nav-yellow bg-opacity-50;
}
.main-two-third {
	@apply flex m-2 border-2 border-gray-400 rounded w-2/3 h-96 bg-nav-yellow bg-opacity-50;
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
.menu-item {
	@apply w-1/3 h-[20.8rem];
}
.menu-item-color {
	@apply w-1/3 h-[20.8rem] bg-nav-yellow px-1 py-6 mx-2 rounded shadow-md;
}
.menu-image {
	@apply border border-gray-300 rounded-md bg-gray-50 mt-3 p-2 w-[93%] h-[17rem] shadow-md;
}
.menu-item-type {
	@apply w-1/2 flex flex-col items-center justify-center;
}
.menu-item-foods {
	@apply w-1/2 flex flex-col items-start justify-center ml-5 py-4 h-full overflow-auto;
	/* @apply w-1/2 flex flex-col items-start justify-center ml-5 py-4 h-[20.8rem] overflow-auto; */
}

::-webkit-scrollbar {
	width: 0.4rem;
}
/* 스크롤바의 트랙(경로)부분 */
::-webkit-scrollbar-track {
	background-color: white;
	border-radius: 1rem;
	box-shadow: inset 0px 0px 5px white;
}
/* 스크롤바의 핸들(드래그하는 부분) */
::-webkit-scrollbar-thumb {
	background-color: #777;
	border-radius: 1rem;
}
/* 스크롤바의 핸들을 호버 시 */
::-webkit-scrollbar-thumb:hover {
	background: #555;
}
</style>
