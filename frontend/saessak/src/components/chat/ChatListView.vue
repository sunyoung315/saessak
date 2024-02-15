<template>
	<div
		class="fixed w-1/3 top-0 right-0 flex px-3 py-5 pb-5 mx-auto border-l-2 border-l-gray-300 bg-yellow-100"
	>
		<h5
			class="flex items-center justify-center text-xl font-bold leading-none text-gray-900 mx-4"
		>
			채팅 목록
		</h5>
	</div>
	<div class="flow-root mt-12 grow h-screen">
		<div v-if="isEmpty == false" class="text-lg text-center mt-10">
			생성된 채팅방이 없습니다.
		</div>
		<ul
			role="list"
			class="my-2"
			v-for="chatItem in chat"
			:key="chatItem.chatId"
		>
			<li
				v-if="chatItem.flag === false"
				class="cursor-pointer py-3 mb-2 border border-gray-300 shadow rounded-lg sm:py-4"
			>
				<div
					@click="
						chatDetail({
							roomId: chatItem.roomId,
							roomName:
								isTeacher == true ? chatItem.kidName : chatItem.teacherName,
							chatProfile:
								isTeacher == true
									? chatItem.kidProfile
									: chatItem.teacherProfile,
						})
					"
					class="flex items-center px-3"
				>
					<!--학부모->아이ID는 현재의 아이모드를 구분하기 위해 pinia에 저장해서 거기서 가져오기-->
					<!--선생님 화면에서 본인의 teacherId는 로그인 정보에서 가져오기-->
					<div class="flex-shrink-0">
						<img
							v-if="isTeacher == true"
							class="w-8 h-8 rounded-full"
							:src="chatItem.kidProfile"
						/>
						<img
							v-if="isTeacher == false"
							class="w-8 h-8 rounded-full"
							:src="chatItem.teacherProfile"
						/>
					</div>
					<div class="flex-1 min-w-0 ms-4">
						<p
							class="text-sm font-medium text-gray-900 truncate dark:text-white"
						>
							{{ isTeacher == true ? chatItem.kidName : chatItem.teacherName }}
							{{ isTeacher == true ? '학부모' : '선생님' }}
						</p>
						<p class="text-sm text-gray-500 truncate dark:text-gray-400">
							{{ chatItem.lastChat }}
						</p>
					</div>
				</div>
			</li>
			<li
				v-else
				class="relative py-3 mb-2 cursor-pointer border-2 border-yellow-100 rounded-lg bg-amber-200 sm:py-4"
			>
				<div
					class="absolute left-0.5 w-2 h-2 transform -translate-x-1/2 -translate-y-1/2 bg-yellow-500 rounded-full top-1"
				></div>

				<div
					@click="
						chatDetail({
							roomId: chatItem.roomId,
							roomName:
								isTeacher == true ? chatItem.kidName : chatItem.teacherName,
							chatProfile:
								isTeacher == true
									? chatItem.kidProfile
									: chatItem.teacherProfile,
						})
					"
					class="relative flex items-center px-3"
				>
					<div class="relative flex-shrink-0">
						<div
							class="absolute top-0 left-0 h-2 transform -translate-x-1/2 -translate-y-1/2 bg-yellow-500 rounded-full"
						></div>
						<img
							v-if="isTeacher == true"
							class="w-8 h-8 rounded-full"
							:src="chatItem.kidProfile"
						/>
						<img
							v-if="isTeacher == false"
							class="w-8 h-8 rounded-full"
							:src="chatItem.teacherProfile"
						/>
					</div>
					<div class="flex-1 min-w-0 ms-4">
						<p class="text-sm font-bold text-gray-900 truncate dark:text-white">
							{{ isTeacher == true ? chatItem.kidName : chatItem.teacherName }}
							{{ isTeacher == true ? '학부모' : '선생님' }}
						</p>
						<p
							class="text-sm font-bold text-gray-500 truncate dark:text-gray-400"
						>
							{{ chatItem.lastChat }}
						</p>
					</div>
				</div>
			</li>
		</ul>
	</div>
</template>

<script setup>
import { chatListParent, chatListTeacher } from '@/api/chat';
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { loginStore } from '@/store/loginStore';

const props = defineProps({
	size: {
		type: Object,
		default: () => ({
			width: 0,
			height: 0,
		}),
	},
});

onMounted(() => {
	getChatList();
});

const store = loginStore();
const { isTeacher } = storeToRefs(store);
const userId = ref(3);
const isEmpty = ref(true);
const getChatList = () => {
	if (isTeacher.value) {
		// 선생님 조회
		chatListTeacher(
			({ data }) => {
				chat.value = data.data;
				if (chat.value.length == 0) {
					isEmpty.value = false;
				}
			},
			error => {},
		);
	} else {
		// 학부모 조회
		chatListParent(
			({ data }) => {
				chat.value = data.data;
				if (chat.value.length == 0) {
					isEmpty.value = false;
				}
			},
			error => {},
		);
	}
};

const emit = defineEmits(['chatEvent']);
const chat = ref([]);

const chatDetail = roomInfo => {
	emit('chatEvent', roomInfo);
};
</script>
<style></style>
