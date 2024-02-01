<template>
  <div
    class="flex flex-col w-full h-full max-w-md p-8 mx-auto my-autoborder border-gray-200 rounded-lg shadow bg-yellow-50 sm:p-8 dark:bg-gray-800 dark:border-gray-700"
  >
    <div class="fixed w-1/3 top-0 right-0 flex justify-between px-3 py-3 bg-yellow-50 pb-0 mx-auto">
      <h3 class="mb-5 text-lg font-bold text-center left-1/2">{{ roomName }} {{ isTeacher == true ? '학부모' : '선생님' }}</h3>
      <button type="button" @click="discon()">
        <svg
          width="30"
          height="30"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M6 12H19" stroke="#383838" stroke-width="2" stroke-linecap="round" />
          <path
            d="M9 8L5 12L9 16"
            stroke="#383838"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </button>
    </div>

    <!-- message -->
    <div
      ref="chatbox"
      class="chatbox flex flex-col scrollbar-hide overflow-y-scroll w-full h-5/6 mt-0 px-5"
    >
      <div v-for="msg in recvList" :key="msg.chatId" class="flex flex-col mt-5">
        <!--발신 메시지(오른쪽)-->
        <div v-if="msg.senderId == userId" class="flex justify-end mb-4">
          <div
            class="px-4 py-3 mr-2 text-white bg-yellow-500 rounded-bl-3xl rounded-tl-3xl rounded-tr-xl"
          >
            {{ msg.chatContent }}
          </div>
          <img
            src="https://source.unsplash.com/vpOeXr5wmR4/600x600"
            class="object-cover w-8 h-8 rounded-full"
            alt=""
          />
        </div>

        <!--수신 메시지(왼쪽)-->
        <div v-else class="flex justify-start mb-4">
          <img
            src="https://source.unsplash.com/vpOeXr5wmR4/600x600"
            class="object-cover w-8 h-8 rounded-full"
            alt=""
          />
          <div
            class="px-4 py-3 ml-2 text-white bg-stone-400 rounded-br-3xl rounded-tr-3xl rounded-tl-xl"
          >
            {{ msg.chatContent }}
          </div>
        </div>
      </div>
    </div>
    <!-- end message(오른쪽) -->
    <button type="button" @click="discon">
      <div class="h-7 w-7 ml-auto mr-2.5 border rounded-full bg-neutral-500">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
          <path
            fill="#ffffff"
            fill-rule="evenodd"
            d="M14 7a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V7Zm2 9.387 4.684 1.562A1 1 0 0 0 22 17V7a1 1 0 0 0-1.316-.949L16 7.613v8.774Z"
            clip-rule="evenodd"
          />
        </svg>
      </div>
    </button>

    <!-- <div   
        class="fixed bottom-0 right-0 flex items-end justify-between mt-auto left-3"
      ></div> -->
    <div class="bottom-0 left-0 right-0 w-full max-w-md p-2 mx-auto">
      <div class="flex items-center justify-evenly">
        <div class="w-3/4 mt-4 mb-5">
          <input
            type="text"
            id="msg-input"
            v-model="msg"
            @keyup.enter="send()"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
        </div>
        <button
          type="button"
          @click="send()"
          class="text-white bg-gray-600 -mt-1 hover:bg-yellow-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          <!--채팅전송버튼-->
          <svg
            class="w-5 h-5"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 14 10"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M1 5h12m0 0L9 1m4 4L9 9"
            />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue'
import { loadChat, isVaild } from '@/api/chat'
import { loginStore } from '@/store/loginStore';
import Stomp from 'webstomp-client' // 채팅 라이브러리 import
import { storeToRefs } from 'pinia';
//채팅메시지 = {room_id, chat_content, sender_id, receiver_id}
const props = defineProps({
  roomInfo: {
    type: Object,
    default: () => ({
      roomId: 0,
      roomName: '',
      width : 0,
      height : 0,
      // senderId: 0,
      // receiverId: 0
    })
  }
})

const {isTeacher} = storeToRefs(loginStore);

onMounted(() => {
  // 접속하면 1. userId 받아오기
  isVaild(({ data }) => {
    // console.log('userId : ')
    // console.log(data)
    userId.value = data.data
  })

  // 2. 이전 채팅 load
  loadChat(props.roomInfo.roomId, ({ data }) => {
    // console.log('채팅 내역 조회')
    // console.log(data)
    recvList.value = data.data
    downScroll()
  })

  // console.log("진짜 마지막으로 사이즈 받아왔니")
  // console.log(props.roomInfo.width + " " + props.roomInfo.height)
})

// 1. 커서에 메시지 정보 중 전송 시간 저장
const cursor = ref(-1);
// 2. 불러온 이전 채팅 내역 중 가장 첫번째 채팅 시간을 커서에 저장
// 3. 스크롤이 맨 위로 올라갔을 때 -> 이전 채팅 불러오기 -> 새로 불러온 채팅으로 커서 재설정

const userId = ref(-1) // 채팅 유저 구분을 위한 현재 로그인한 userId
const chatbox = ref(null)
const emit = defineEmits(["exitChat"]); // 채팅방 퇴장 처리

// console.log(props.roomInfo)
const roomId = props.roomInfo.roomId // 채팅방 번호
const roomName = props.roomInfo.roomName
const connected = ref(false) // 소켓 연결 여부
const socket = new WebSocket('wss://i10a706.p.ssafy.io/chat')
let options = { debug: false, protocols: Stomp.VERSIONS.supportedProtocols() }
const recvList = ref([])
const msg = ref('')
const stomp = Stomp.over(socket, options)

const downScroll = () => {
  nextTick(() => {
    if (chatbox.value) {
      chatbox.value.scrollTo(0, chatbox.value.scrollHeight)
    }
  })
}


let headers = { Authorization: 'Bearer ' + sessionStorage.getItem('accessToken'),
        roomId : roomId, userId : userId }

// console.log('소켓 연결 시작')
// console.log('roomId : ' + roomId)
// chat 메세지 정보에서 리시버id 안받을거임 / 백에서는 내가 보낸 토큰으로 senderid 지정함
// 그럼 내가 받을땐?
// console.log(roomId);
stomp.connect(
  headers,
  (frame) => {
    // 소켓 연결 성공
    // console.log('after frame')
    connected.value = true
    // console.log('소켓 연결 성공', frame)
    // console.log(stomp);
    // 서버의 메시지 전송 endpoint를 구독합니다.
    // 이런형태를 pub sub 구조라고 합니다.
    stomp.subscribe(
      '/sub/room/' + roomId,
      (res) => {
        // console.log('구독으로 받은 메시지 입니다.', res.body)

        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        recvList.value.push(JSON.parse(res.body))

        downScroll()
        // nextTick(() => {
        //   if (chatbox.value) {
        //     chatbox.value.scrollTo(0, chatbox.value.scrollHeight)
        //   }
        // })
      },
      headers
    )
  },
  (error) => {
    // 소켓 연결 실패
    // console.log('소켓 연결 실패', error)
    connected.value = false
  }
)
function addZero(value) {
  return value < 10 ? '0' + value : value
}

const send = () => {
  // 메세지 전송
  // console.log('Send message:' + msg.value)

  let currentDate = new Date()

  var formattedDate =
    currentDate.getFullYear() +
    '-' +
    addZero(currentDate.getMonth() + 1) +
    '-' +
    addZero(currentDate.getDate()) +
    ' ' +
    addZero(currentDate.getHours()) +
    ':' +
    addZero(currentDate.getMinutes()) +
    ':' +
    addZero(currentDate.getSeconds()) +
    '.' +
    currentDate.getMilliseconds()
  if (stomp && stomp.connected) {
    const sendMsg = {
      roomId: props.roomInfo.roomId,
      senderId: userId.value,
      chatContent: msg.value,
      chatTime: formattedDate
    }
    stomp.send('/pub/message', JSON.stringify(sendMsg), headers)
    msg.value = ''
  }
}

const discon = () => {
  // console.log('discon 실행')
  //Theheader로 ChatListView로 바꾸겠다고 전송해야됨
  stomp.unsubscribe((res) => {}, headers)
  // console.log(stomp)
  emit("exitChat", true);
}
</script>

<style></style>
