<template>
	<div class="view-frame p-6">
		<vue-cal
			class="mx-5 my-3"
			active-view="month"
			hide-view-selector
			selected-date="2024-01-27"
			:disable-views="['years', 'year', 'week', 'day']"
			show-all-day-events="true"
			events-on-month-view="true"
			:events="events"
			:locale="'ko'"
			startWeekOnSunday
		>
			<template #title="{ view }">
				<span v-if="view.id === 'month'"
					>{{ view.startDate.format('MMMM YYYY') }}년</span
				>
			</template>
			<template #cell-date="{ date, view }">
				<div v-if="view === 'month'">
					{{ date.getDate() }}
					<span class="tooltip">
						{{ date.toISOString() }}
					</span>
				</div>
			</template>
		</vue-cal>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useAttendanceStore } from '@/store/attendance';

const store = useAttendanceStore();

const oneKidList = ref([]);

const events = ref([]);

const loginStore = JSON.parse(localStorage.getItem('loginStore'));

// 추후에 학부모가 현재 보고 있는 아이의 kidId가 curKid에 들어갈 예정!!!
// const kidId = loginStore.curKid;
const kidId = loginStore.kidList[0].kidId;
////////////////////////////////////////////////////////////////////

// 내 아이의 출석부 비동기 호출
const getList = async () => {
	await store.getOneKidList(kidId);
	oneKidList.value = store.oneKidList;

	// vue-cal에 입력할 events 형식으로 변환
	for (let i = 0; i < oneKidList.value.length; i++) {
		// 결석
		if (!oneKidList.value[i].attendanceInTime) {
			const noEvent = {
				start: oneKidList.value[i].attendanceDate,
				end: oneKidList.value[i].attendanceDate,
				content: '결석',
				class: 'no-event',
				allDay: true,
			};
			events.value.push(noEvent);
		} else {
			// 하원 미입력
			if (!oneKidList.value[i].attendanceOutTime) {
				const inEvent = {
					start: oneKidList.value[i].attendanceDate,
					end: oneKidList.value[i].attendanceDate,
					content: `등원 ${oneKidList.value[i].attendanceInTime}`,
					class: 'in-event',
					allDay: true,
				};
				events.value.push(inEvent);

				const falseEvent = {
					start: oneKidList.value[i].attendanceDate,
					end: oneKidList.value[i].attendanceDate,
					content: '하원: 미입력',
					class: 'false-event',
					allDay: true,
				};
				events.value.push(falseEvent);
			}
			// 정상 등하원
			else {
				const inEvent = {
					start: oneKidList.value[i].attendanceDate,
					end: oneKidList.value[i].attendanceDate,
					content: `등원 ${oneKidList.value[i].attendanceInTime}`,
					class: 'in-event',
					allDay: true,
				};
				events.value.push(inEvent);

				const outEvent = {
					start: oneKidList.value[i].attendanceDate,
					end: oneKidList.value[i].attendanceDate,
					content: `하원 ${oneKidList.value[i].attendanceOutTime}`,
					class: 'out-event',
					allDay: true,
				};
				events.value.push(outEvent);
			}
		}
	}
};

onMounted(async () => {
	await getList();
});
</script>

<style scoped>
:deep(.vuecal__title-bar) {
	@apply h-16 bg-white font-bold text-xl;
}
:deep(.vuecal__heading) {
	@apply bg-nav-purple bg-opacity-30  font-bold text-base;
}
/* red & blue version  */
/* :deep(.vuecal__event.in-event) {
	@apply border-x border-t border-white rounded-t bg-nav-blue bg-opacity-50 font-bold text-gray-900 pt-2;
}
:deep(.vuecal__event.out-event) {
	@apply border-x border-b border-white rounded-b bg-nav-blue bg-opacity-50 font-bold text-gray-900 pb-2;
}
:deep(.vuecal__event.false-event) {
	@apply border-x border-b border-white rounded-b bg-nav-blue bg-opacity-50 font-bold text-gray-500 pb-2;
}
:deep(.vuecal__event.no-event) {
	@apply border border-white rounded bg-nav-red bg-opacity-50 font-bold text-gray-900 py-5;
} */

/* white & black version */
:deep(.vuecal__event.in-event) {
	@apply rounded-t font-bold  text-gray-900 pt-2;
}
:deep(.vuecal__event.out-event) {
	@apply rounded-b font-bold shadow-md text-gray-900 pb-2;
}
:deep(.vuecal__event.false-event) {
	@apply rounded-b font-bold shadow-md text-gray-400 text-sm pt-0.5 pb-2.5;
}
:deep(.vuecal__event.no-event) {
	@apply rounded-md font-bold shadow-md text-red-600 py-5;
}
:deep(.vuecal__cell) {
	@apply h-28;
}
:deep(.vuecal__cell--selected) {
	@apply bg-nav-purple bg-opacity-10;
}
:deep(.vuecal__cell-date) {
	@apply mb-1;
}
:deep(.vuecal__cell--today) {
	@apply bg-nav-purple bg-opacity-50;
}
</style>
