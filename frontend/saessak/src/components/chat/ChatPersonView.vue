<template>
	<div
		class="fixed w-1/3 top-0 right-0 flex items-center justify-between px-3 py-5 pb-4 mx-auto border-l-2 border-l-gray-300 bg-yellow-50"
	>
		<h5
			class="flex items-center justify-center text-xl font-bold leading-none text-gray-900 dark:text-white"
		>
			{{ isTeacher == true ? '학부모 목록' : '선생님 목록' }}
		</h5>
	</div>
	<div class="flow-root grow">
		<div
			class="w-full mt-5 mx-0 my-0 bg-yellow-50 dark:bg-gray-800 dark:border-gray-700"
		>
			<div class="flex flex-wrap py-5 w-full mt-auto mb-3 justify-evenly">
				<div
					v-for="person in Person"
					:key="person.id"
					class="flex flex-col items-center w-6/12 mb-3 border border-gray-300 rounded-lg shadow pb-10mb-4"
				>
					<img
						v-if="isTeacher == true"
						class="w-24 h-24 my-2 mb-3 rounded-full shadow-lg"
						:src="person.kidProfile"
						alt="Bonnie image"
					/>
					<img
						v-if="isTeacher == false"
						class="w-24 h-24 my-2 mb-3 rounded-full shadow-lg"
						:src="person.profile"
						alt="Bonnie image"
					/>
					<h5 class="text-xl font-medium text-gray-900 dark:text-white">
						{{ isTeacher == true ? person.kidName : person.teacherName }}
						{{ isTeacher == true ? '학부모' : '선생님' }}
					</h5>
					<span class="mb-0 text-sm text-gray-500 dark:text-gray-400">{{
						isTeacher == true ? '' : person.kidName
					}}</span>
					<div class="flex mt-3 mb-2">
						<button
							@click="
								addChat({
									id: isTeacher == true ? person.kidId : person.teacherId,
									name: isTeacher == true ? person.kidName : person.teacherName,
									profile:
										isTeacher == true ? person.kidProfile : person.profile,
								})
							"
							class="inline-flex items-center px-4 py-2 text-center text-white bg-lime-800 rounded-lg font -medium mt-0text-sm hover:bg-lime-500 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
						>
							TALK
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { getClassKids, getMyTeacher } from '@/api/user';
import { getClassList, teacherNewChat, parentNewChat } from '@/api/chat';
import { nextTick, ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { loginStore } from '@/store/loginStore';

const store = loginStore();
const { isLogin } = storeToRefs(store);
const props = defineProps({
	size: {
		type: Object,
		default: () => ({
			width: 0,
			height: 0,
		}),
	},
}); // drawer 사이즈 정보 상속
onMounted(() => {
	if (isLogin.value) {
		getPersonList();
	}
	nextTick(() => {});
});
const userId = ref(3);
const classroomId = ref(1);
const { isTeacher } = storeToRefs(store);

const getPersonList = () => {
	if (isTeacher.value) {
		getClassList(
			({ data }) => {
				Person.value = data.data;
			},
			error => {},
		);
	} else {
		getMyTeacher(
			({ data }) => {
				Person.value = data.data;
			},
			error => {},
		);
	}
};

const Person = ref([]);
const emit = defineEmits(['chatEvent']); // CHAT 버튼 눌렀을 때 채팅방으로 이동

const addChat = input => {
	let tId = isTeacher.value == false ? userId.value : input[0]; // 선생님 화면일때 : 학부모 화면일때
	let kId = isTeacher.value == false ? input : input[1];
	if (isTeacher.value) {
		teacherNewChat(
			input.id,
			({ data }) => {
				const roomInfo = {
					roomId: data.data,
					roomName: input.name,
					chatProfile: input.profile,
				};
				emit('chatEvent', roomInfo);
			},
			error => {},
		);
	} else {
		parentNewChat(
			input.id,
			({ data }) => {
				const roomInfo = {
					roomId: data.data,
					roomName: input.name,
					chatProfile: input.profile,
				};
				emit('chatEvent', roomInfo);
			},
			error => {},
		);
	}
};
</script>
<style></style>
