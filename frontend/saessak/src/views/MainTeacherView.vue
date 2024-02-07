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
				<div class="flex items-end">
					<img src="/icons/cake.png" alt="icon" class="w-12" />
					<div class="title">생일</div>
				</div>
			</div>
		</div>
		<div class="main-full">
			<div class="flex">
				<template
					v-for="(menu, index) in menuStore.weeklyMenu"
					:key="menu.menuId"
				>
					<template
						v-if="menu.menuDate === todayDate && menu.menuType === menuType"
					>
						<div class="menu-item m-0 p-0">
							<div class="flex items-center">
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
							<img
								:src="menuStore.weeklyMenu[index].menuPath"
								alt="메뉴사진"
								class="menu-image"
							/>
						</div>
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
								<div class="font-bold text-xl">포함성분</div>
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
							</div>
						</div>
					</template>
				</template>
			</div>
		</div>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useMenuStore } from '@/store/menu';
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

const menuType = ref('점심');

onMounted(async () => {
	await menuStore.getTeacherWeeklyMenu({ year, month, week });
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
	@apply m-7 mb-3 flex flex justify-between items-end;
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
	@apply w-1/3 h-[20.8rem] bg-nav-yellow p-1 mx-2 rounded shadow-md;
}
.menu-image {
	@apply border-2 border-gray-300 rounded-md bg-gray-50 mt-3 p-1 w-[23.5rem] h-[17.3rem];
}
.menu-item-type {
	@apply inline-block w-1/2 flex flex-col items-center justify-center;
}
.menu-item-foods {
	@apply inline-block w-1/2 flex flex-col items-start justify-center;
}
</style>
