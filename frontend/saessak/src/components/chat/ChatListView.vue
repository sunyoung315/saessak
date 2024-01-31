<template>
  <div class="fixed w-1/3 top-0 right-0 flex px-3 py-5 pb-5 mx-auto bg-yellow-50">
    <h5
      class="flex items-center justify-center text-xl font-bold leading-none text-gray-900 dark:text-white"
    >
      채팅 목록
    </h5>
  </div>
  <div class="flow-root mt-5 grow h-screen">
    <ul role="list" class="" v-for="chatItem in chat" :key="chatItem.chatId">
      <li v-if="chatItem.flag === false" class="py-3 mb-2 border rounded-lg sm:py-4">
        <div
          @click="chatDetail({roomId : chatItem.roomId, receiverId : userFlag == false ? chatItem.kidId : chatItem.teacherId, senderId : userFlag == false ? userId : chatItem.kidId})"
          class="flex items-center px-3"
        ><!--학부모->아이ID는 현재의 아이모드를 구분하기 위해 pinia에 저장해서 거기서 가져오기-->
        <!--선생님 화면에서 본인의 teacherId는 로그인 정보에서 가져오기-->
          <div class="flex-shrink-0">
            <img
              class="w-8 h-8 rounded-full"
              src="https://flowbite.com/docs/images/people/profile-picture-4.jpg"
              alt="Neil image"
            />
          </div>
          <div class="flex-1 min-w-0 ms-4">
            <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
              {{ userFlag == false ? chatItem.kidName : chatItem.teacherName }}
              {{ userFlag == false ? '학부모' : '선생님' }}
            </p>
            <p class="text-sm text-gray-500 truncate dark:text-gray-400">
              {{ chatItem.lastChat }}
            </p>
          </div>
        </div>
      </li>
      <li
        v-else
        class="relative py-3 mb-2 border-2 border-yellow-100 rounded-lg bg-amber-200 sm:py-4"
      >
        <div
          class="absolute left-0.5 w-2 h-2 transform -translate-x-1/2 -translate-y-1/2 bg-yellow-500 rounded-full top-1"
        ></div>

        <div @click="chatDetail(chatItem.chatId)" class="relative flex items-center px-3">
          <div class="relative flex-shrink-0">
            <div
              class="absolute top-0 left-0 h-2 transform -translate-x-1/2 -translate-y-1/2 bg-yellow-500 rounded-full"
            ></div>
            <img
              class="w-8 h-8 rounded-full"
              src="https://flowbite.com/docs/images/people/profile-picture-4.jpg"
              alt="Bonnie image"
            />
          </div>
          <div class="flex-1 min-w-0 ms-4">
            <p class="text-sm font-bold text-gray-900 truncate dark:text-white">
              {{ userFlag == false ? chatItem.kidName : chatItem.teacherName }}
              {{ userFlag == false ? '학부모' : '선생님' }}
            </p>
            <p class="text-sm font-bold text-gray-500 truncate dark:text-gray-400">
              {{ chatItem.lastChat }}
            </p>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { chatListParent, chatListTeacher } from '@/api/chat'
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { loginStore } from '@/store/loginStore'

onMounted(() => {
  getChatList()
})

const store = loginStore()
const {isTeacher} = storeToRefs(store);
const userId = ref(3)
const getChatList = () => {
  console.log('채팅 리스트 조회')
  if (isTeacher) {
    // 선생님 조회
    chatListTeacher(
      ({ data }) => {
        console.log('선생님 조회')
        console.log(data.data)
        chat.value = data.data
        console.log(chat);
      },
      (error) => {
        console.log(error)
      }
    )
  } else {
    // 학부모 조회
    chatListParent(
      ({ data }) => {
        console.log('학부모 조회')
        console.log(data.data)
        chat.value = data.data
      },
      (error) => {
        console.log(error)
      }
    )
  }
}

const emit = defineEmits(['chatEvent'])
const chat = ref([])
// [
//   { chatId: 1, name: "김하은", lastChat: "네 확인했습니다 ^^", unread: true },
//   {
//     chatId: 2,
//     name: "윤선아",
//     lastChat: "안녕하세요. 가능합니다. ",
//     unread: false,
//   },
//   { chatId: 3, name: "이민주", lastChat: "확인부탁드립니다 !", unread: false },
// ];

const chatDetail = (roomInfo) => {
  console.log("chatlistview - chatDetail");
  console.log(roomInfo)
  emit('chatEvent', roomInfo)
}
</script>
<style></style>
