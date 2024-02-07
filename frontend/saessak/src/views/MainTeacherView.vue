<template>
	<div class="main-view view-frame">
		<div class="main-header">
			<p class="main-title text-2xl">
				{{ props.loginStore.teacherName }}
			</p>
			<p class="main-title text-lg">{{ todayDate }}</p>
		</div>
		<div class="flex">
			<div class="main-half">
				<div class="flex items-end">
					<img src="/icons/todo.png" alt="icon" class="w-12" />
					<div class="title">오늘의 할 일</div>
				</div>
				<TodoView />
			</div>
			<div class="main-half">
				<div class="flex">
					<div class="w-1/2">
						<div class="flex items-end">
							<img src="/icons/cake.png" alt="icon" class="w-12" />
							<div class="title">생일</div>
						</div>
						<template v-if="todayBirthday.length">
							<template v-for="kid in todayBirthday" :key="kid.kidId">
								<div class="pt-8 flex justify-center">
									<div class="relative inline-block">
										<img
											:src="kid.kidProfile"
											alt="profile"
											class="w-48 h-48 rounded-full border border-gray-400 shadow-md"
										/>
										<img
											src="/icons/party-hat.png"
											alt="hbd"
											class="w-24 absolute -top-7 -right-7"
										/>
									</div>
								</div>
								<div class="text-xl font-bold text-center pt-5">
									{{ kid.kidName }} 어린이
								</div>
							</template>
						</template>
						<template v-else>
							<div class="m-6">오늘 생일인 아이가 없습니다.</div>
						</template>
					</div>
					<div class="w-1/2">
						<VCalendar
							:attributes="attributes"
							:masks="{ title: 'YYYY년 MMM' }"
							class="w-full h-[21rem]"
						/>
					</div>
				</div>
			</div>
		</div>
		<div class="main-full">
			<div class="flex">
				<div class="menu-item m-0 p-0">
					<div class="flex items-center">
						<img src="/icons/menubook.png" alt="icon" class="w-12" />
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
							<img
								:src="menuStore.weeklyMenu[index].menuPath"
								alt="메뉴사진"
								class="menu-image"
							/> </template
					></template>
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
												<div class="leading-[3rem] font-bold text-lg">
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
import { useMenuStore } from '@/store/menu';
import { useUserStore } from '@/store/user';
import { getWeekOfMonth } from 'date-fns';
import TodoView from '@/components/todo/TodoView.vue';

const props = defineProps({
	loginStore: Object,
});

const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const week = getWeekOfMonth(today, { weekStartsOn: 1 });
const day = ('0' + today.getDate()).slice(-2);

const todayDate = `${year}-${month}-${day}`;

const menuStore = useMenuStore();
const userStore = useUserStore();

const menuType = ref('점심');

const kidsList = ref([]);
const todayBirthday = ref([]);

// 캘린더 설정
const attributes = computed(() => {
	let events = [];
	kidsList.value.forEach(kid => {
		const startDate = new Date(kid.kidBirthday);
		for (let i = 0; i < 100; i++) {
			const eventDate = new Date(startDate);
			eventDate.setFullYear(startDate.getFullYear() + i);
			events.push({
				// Date 객체를 'yyyy-MM-dd' 형식의 문자열로
				// ISO 8601 형식은 'yyyy-MM-ddTHH:mm:ss.sssZ' 형태
				dates: eventDate.toISOString().split('T')[0],
				highlight: {
					color: 'yellow',
					fillMode: 'solid',
				},
				popover: {
					label: `${kid.kidName}`,
					visibility: 'hover',
					hideIndicator: true,
				},
			});
		}
	});
	return events;
});

onMounted(async () => {
	await menuStore.getTeacherWeeklyMenu({ year, month, week });
	await userStore.getKidsList();
	kidsList.value = userStore.kidsList;
	todayBirthday.value = kidsList.value.filter(kid => {
		const kidBirthday = new Date(kid.kidBirthday);
		return (
			kidBirthday.getMonth() === today.getMonth() &&
			kidBirthday.getDate() === today.getDate()
		);
	});
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
.main-half {
	@apply inline m-2 border-2 border-gray-400 rounded w-1/2 h-96 p-6 bg-nav-yellow bg-opacity-50;
}
.main-full {
	@apply border-2 border-gray-400 rounded w-auto h-96 m-2 p-6 bg-nav-yellow bg-opacity-50;
}
.title {
	@apply text-xl font-bold mx-2;
}
.menu-item {
	@apply w-1/3 h-[20.8rem] pl-3;
}
.menu-item-color {
	@apply w-1/3 h-[20.8rem] bg-nav-yellow p-1 mx-2 rounded shadow-md;
}
.menu-image {
	@apply border border-gray-300 rounded-md bg-gray-50 mt-3 p-2 w-[93%] h-[17.5rem] shadow-md;
}
.menu-item-type {
	@apply w-1/2 flex flex-col items-center justify-center;
}
.menu-item-foods {
	@apply w-1/2 flex flex-col items-start justify-center;
}
:deep(.vc-header) {
	@apply mt-6 h-12;
}
:deep(.vc-weekdays) {
	@apply h-6;
}
:deep(.vc-week) {
	@apply h-9;
}
</style>
