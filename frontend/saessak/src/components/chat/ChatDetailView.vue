<template>
  <!-- border border-gray-200 shadow -->
  <div
    class="flex flex-col w-full h-full px-0 mx-auto my-0 rounded-lg  bg-yellow-50 sm:p-8 dark:bg-gray-800 dark:border-gray-700">
    <div
      class="fixed w-1/3 top-0 right-0 flex justify-between px-3 py-3 border-l-2 border-l-gray-300 bg-yellow-50 pb-0 mx-auto">
      <h3 class="mb-5 text-xl font-bold text-center left-1/2">
        {{ roomName }} {{ isTeacher == true ? '학부모' : '선생님' }}
      </h3>
      <button type="button" @click="discon()">
        <svg width="30" height="30" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M6 12H19" stroke="#383838" stroke-width="2" stroke-linecap="round" />
          <path d="M9 8L5 12L9 16" stroke="#383838" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        </svg>
      </button>
    </div>

    <!-- message -->
    <!--scrollbar-hide -->
    <div ref="chatbox"
      class="chatbox flex flex-col scrollbar-hide overflow-y-scroll w-full h-full mt-0 mb-0 my-0 px-5 py-0">
      <div v-for="msg in recvList" :key="msg.chatId" class="flex flex-col mt-5">
        <!--발신 메시지(오른쪽)-->
        <div class="flex w-0 h-0 text-yellow-50">{{ msg.chatTime }}</div>
        <div v-if="msg.senderId == userId" ref="printMsg" class="flex justify-end mb-4">
          <div class="px-4 py-3 mr-2 text-white bg-yellow-500 rounded-bl-3xl rounded-tl-3xl rounded-tr-xl">
            {{ msg.chatContent }}
          </div>
          <img :src="myProfile" class="object-cover w-8 h-8 rounded-full" alt="" />
        </div>

        <!--수신 메시지(왼쪽)-->
        <div v-else ref="printMsg" class="flex justify-start mb-4">
          <img :src="props.roomInfo.chatProfile" class="object-cover w-8 h-8 rounded-full" alt="" />
          <div class="px-4 py-3 ml-2 text-white bg-stone-400 rounded-br-3xl rounded-tr-3xl rounded-tl-xl">
            {{ msg.chatContent }}
          </div>
        </div>
      </div>
    </div>
    <!-- end message(오른쪽) -->



    <!-- <div   
        class="fixed bottom-0 right-0 flex items-end justify-between mt-auto left-3"
      ></div> -->
    <div class="bottom-0 left-0 right-0 w-full max-w-md p-2 mx-auto">
      <div class="flex items-center justify-evenly">
        <div class="w-3/4 mt-4 mb-5">
          <input type="text" id="msg-input" v-model="msg" @keyup.enter="send()"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
        </div>
        <button type="button" @click="send()"
          class="text-white bg-gray-600 -mt-1 hover:bg-yellow-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
          <!--채팅전송버튼-->
          <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M1 5h12m0 0L9 1m4 4L9 9" />
          </svg>
        </button>
        <!-- 화상채팅 버튼 -->
        <button v-if="isTeacher == true" @click="startFaceChat()" class="block text-white" type="button">
          <div class="h-9 w-9 mb-1 ml-auto mr-2.5 border rounded-full bg-neutral-500">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <path fill="#ffffff" fill-rule="evenodd"
                d="M14 7a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V7Zm2 9.387 4.684 1.562A1 1 0 0 0 22 17V7a1 1 0 0 0-1.316-.949L16 7.613v8.774Z"
                clip-rule="evenodd" />
            </svg>
          </div>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, watch, onMounted, onBeforeUnmount } from 'vue'
import { loadChat, isVaild, disconnect } from '@/api/chat'
import { loginStore } from '@/store/loginStore'
import { chatStore } from '@/store/chatStore'
import Stomp from 'webstomp-client' // 채팅 라이브러리 import
import { storeToRefs } from 'pinia'
import Swal from 'sweetalert2'
//채팅메시지 = {room_id, chat_content, sender_id, receiver_id}
const props = defineProps({
  roomInfo: {
    type: Object,
    default: () => ({
      roomId: 0,
      roomName: '',
      chatProfile: '',
    })
  }
})

const lstore = loginStore()
const { chatName, chatRoom, isOpen } = storeToRefs(chatStore)
const { setChatname, setChatroom, setIsopen } = chatStore()
const { isTeacher, profile, kidList } = storeToRefs(lstore)
const printMsg = ref([])
const lastChat = ref('')
let oldHeight = -1
const myProfile = ref("")

// 1. 커서에 메시지 정보 중 전송 시간 저장
const cursor = ref({
  roomId: null,
  chatContent: null,
  senderId: null,
  chatTime: null
})
// 2. 불러온 이전 채팅 내역 중 가장 첫번째 채팅 시간을 커서에 저장
// 3. 스크롤이 맨 위로 올라갔을 때 -> 이전 채팅 불러오기 -> 새로 불러온 채팅으로 커서 재설정

const convertDate = (oldDate) => {
  var NewDate =
    oldDate.getFullYear() +
    '-' +
    addZero(oldDate.getMonth() + 1) +
    '-' +
    addZero(oldDate.getDate()) +
    ' ' +
    addZero(oldDate.getHours()) +
    ':' +
    addZero(oldDate.getMinutes()) +
    ':' +
    addZero(oldDate.getSeconds()) +
    '.' +
    oldDate.getMilliseconds()

  return NewDate
}

const handleBeforeUnload = (event) => {
  // 브라우저 종료 시 채팅 퇴장 시점 저장
  const confirmationMessage = '채팅을 떠나긔?'

  // 이 부분에서 사용자에게 경고를 표시할 수 있습니다.
  event.returnValue = confirmationMessage
  if (event.returnValue) {
    discon()
  }
  return confirmationMessage
}

onMounted(() => {

  myProfile.value = isTeacher.value == true ? profile.value : kidList.value[0].kidProfile
  window.addEventListener('beforeunload', handleBeforeUnload) // 브라우저 종료 이벤트 등록
  if (chatbox.value) {
    chatbox.value.addEventListener('scroll', handleScroll)
  } // 스크롤 감지 이벤트 등록
  // 접속하면 1. userId 받아오기
  isVaild(({ data }) => {
    userId.value = data.data
  })

  // 2. 이전 채팅 load
  let currentDate = new Date()
  cursor.value = {
    cursor: convertDate(currentDate)
  }
  loadChat(props.roomInfo.roomId, cursor.value, ({ data }) => {
    if (data.data.length == 0) {
      cursor.value = {
        chatTime: null
      }

    } else {
      cursor.value = {
        // 최초 불러온 채팅을 갖고 커서 설정
        roomId: data.data[0].roomId,
        chatContent: data.data[0].chatContent,
        senderId: data.data[0].senderId,
        chatTime: data.data[0].chatTime
      }
      recvList.value = data.data
    }

    // console.log(recvList.value)
    oldHeight = chatbox.value.scrollHeight
    downScroll()
  })

})

onBeforeUnmount(() => {
  window.removeEventListener('beforeunload', handleBeforeUnload)
  if (chatbox.value) {
    chatbox.value.removeEventListener('scroll', handleScroll)
  }
})

const userId = ref(-1) // 채팅 유저 구분을 위한 현재 로그인한 userId
const chatbox = ref(null)
const emit = defineEmits(['exitChat']) // 채팅방 퇴장 처리

const roomId = props.roomInfo.roomId // 채팅방 번호
const roomName = props.roomInfo.roomName 
setChatroom(roomId)
setChatname(roomName + (isTeacher.value == true ? " 학부모와" : " 선생님과"))

// 화상채팅 전달, 하단 메뉴 클릭 flag를 위한 스토어 저장
const connected = ref(false) // 소켓 연결 여부
const socket = new WebSocket('wss://i10a706.p.ssafy.io/chat')
let options = { debug: false, protocols: Stomp.VERSIONS.supportedProtocols() }
const recvList = ref([])
const msg = ref('')
const stomp = Stomp.over(socket, options)

const handleScroll = () => {
  // 스크롤 맨 위로 올렸을 때 감지하기
  // 현재 스크롤 위치를 가져옵니다.
  const scrollTop = chatbox.value.scrollTop || document.documentElement.scrollTop
  let currentDate = new Date()
  if (scrollTop === 0) {
    // 1. 현재 커서를 기준으로 다시 메세지 불러오기(revList 맨 앞에 추가하기)

    if (cursor.value.chatTime == null) {
      // 모든 채팅 불러온 경우
      return
    }
    loadChat(props.roomInfo.roomId, cursor.value, ({ data }) => {
      if (data.data.length == 0) {
        cursor.value.chatTime = null
      } else {
        const newArr = [...data.data, ...recvList.value] // 새로 불러온 채팅 + 기존 채팅
        lastChat.value = data.data[data.data.length - 1].chatTime
        cursor.value = {
          roomId: data.data[0].roomId,
          chatContent: data.data[0].chatContent,
          senderId: data.data[0].senderId,
          chatTime: data.data[0].chatTime
        }
        recvList.value = newArr
        chatbox.value.scrollTo({ left: 0, top: chatbox.value.clientHeight }) // 올리고 나서 스크롤 위치 어따 둘건지???
        oldHeight = chatbox.value.scrollHeight
      }
    })
    // 2. 지금 불러온 메세지 중 첫번째 메세지의 시간을 커서로 변경
    // 3. 더 이상 불러올 매세지 없으면 api 호출X
  }
}

const downScroll = () => {
  // 스크롤 맨 아래로 내리기
  nextTick(() => {
    if (chatbox.value) {
      chatbox.value.scrollTo({ left: 0, top: chatbox.value.scrollHeight, behavior: 'smooth' })
    }
  })
}

let headers = {
  Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
  roomId: roomId,
  userId: userId
}

// chat 메세지 정보에서 리시버id 안받을거임 / 백에서는 내가 보낸 토큰으로 senderid 지정함
// 그럼 내가 받을땐?
const newWindow = ref()
stomp.connect(
  headers,
  (frame) => {
    // 소켓 연결 성공
    connected.value = true
    setIsopen(true)
    // 서버의 메시지 전송 endpoint를 구독합니다.
    // 이런형태를 pub sub 구조라고 합니다.
    stomp.subscribe(
      '/sub/room/' + roomId,
      (res) => {
        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        if (res.body == 'videoChat allow request') {
          if (isTeacher.value == false) {
            Swal.fire({
              icon: 'question',
              title: '화상 채팅에 참여하시겠습니까?',
              confirmButtonText: '승인',
              showCancelButton : true,
              cancelButtonText : '거절',
            }).then(result => {
              if(result.isConfirmed){
                window.open('/facechat', '_blank', 'width=720, height=720')
              }else if(result.isDismissed){
                stomp.send('/pub/response', roomId, headers)
              }
            })
          }
        } else if (res.body == 'videoChat deny response') {
          if (isTeacher.value) {
            Swal.fire({
              icon: 'info',
              title: '화상 채팅 요청이 거절되었습니다.',
              confirmButtonText: '확인'
            })
          }
        } else {
          recvList.value.push(JSON.parse(res.body))
        }

        downScroll()
      },
      headers
    )
  },
  (error) => {
    // 소켓 연결 실패
    // console.log('소켓 연결 실패', error)
    connected.value = false
    setIsopen(false)
  }
)
function addZero(value) {
  return value < 10 ? '0' + value : value
}

const send = () => {
  // 메세지 전송
  // console.log('Send message:' + msg.value)
  if (msg.value.length == 0) {
    Swal.fire({
      icon: 'warning',
      title: '메시지를 입력해주세요!',
      confirmButtonText: '확인'
    })
  } else {
    let currentDate = new Date()
    if (stomp && stomp.connected) {
      const sendMsg = {
        roomId: props.roomInfo.roomId,
        senderId: userId.value,
        chatContent: msg.value,
        chatTime: convertDate(currentDate)
      }
      stomp.send('/pub/message', JSON.stringify(sendMsg), headers)
      msg.value = ''
    }
  }
}

const discon = () => {
  // console.log('discon 실행')
  const params = {
    roomId: props.roomInfo.roomId,
    userId: userId.value
  }
  disconnect(
    params,
    ({ response }) => {
    },
    ({ error }) => {
    }
  )
  //Theheader로 ChatListView로 바꾸겠다고 전송해야됨
  stomp.unsubscribe((res) => { }, headers)
  // console.log(stomp)
  emit('exitChat', true)
}

// 화상채팅 버튼 눌렀을 때 -> userName, roomId 갖고 FaceChatView로 이동
const startFaceChat = () => {
  let popupWidth = 720
  let popupHeight = 720
  let popupX = window.screen.width / 2 - popupWidth / 2
  // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음

  let popupY = window.screen.height / 2 - popupHeight / 2
  // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음

  stomp.send('/pub/request', roomId, headers)
  newWindow.value = window.open(
    '/facechat',
    '',
    'status=no, height=' +
    popupHeight +
    ', width=' +
    popupWidth +
    ', left=' +
    popupX +
    ', top=' +
    popupY
  )
}
</script>

<style></style>
