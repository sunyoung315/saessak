<!-- eslint-disable prettier/prettier -->
<template>
  <div id="header-layout">
    <div class="flex justify-between items-center">
      <RouterLink to="/">Logo</RouterLink>
      <div>
        <label class="relative inline-flex items-center cursor-pointer">
          <input type="checkbox" v-model="alarm" class="sr-only peer" />
          <div
            v-if="isLogin == true"
            class="mr-8 w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600"
          ></div>
        </label>

        <RouterLink v-if="isLogin == true" to="/setting" class="inline-block">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox="0 0 20 20"
            fill="none"
          >
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M11.5 0L14 3L18 3.5L20 6.5L18 10L20 13.5L18 16.5L14 17L11.5 20H8.5L6 17L2 16.5L0 13.5L2 10L0 6.5L2 3.5L6 3L8.5 0H11.5ZM10.562 1.999H9.437L7.0312 4.88666L3.156 5.37L2.35 6.581L4.3035 10L2.35 13.418L3.156 14.629L7.0312 15.1133L9.436 17.999H10.563L12.9688 15.1133L16.843 14.629L17.649 13.418L15.6965 10L17.649 6.581L16.843 5.37L12.9688 4.88666L10.562 1.999ZM10 6C12.2091 6 14 7.79086 14 10C14 12.2091 12.2091 14 10 14C7.79086 14 6 12.2091 6 10C6 7.79086 7.79086 6 10 6ZM10 8C8.89543 8 8 8.89543 8 10C8 11.1046 8.89543 12 10 12C11.1046 12 12 11.1046 12 10C12 8.89543 11.1046 8 10 8Z"
              fill="black"
            />
          </svg>
        </RouterLink>
        <button
          v-if="isLogin == true"
          type="button"
          data-drawer-target="drawer-right-example"
          data-drawer-show="drawer-right-example"
          data-drawer-placement="right"
          aria-controls="drawer-right-example"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
          >
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M19 3C20.1046 3 21 3.89543 21 5V17C21 18.1046 20.1046 19 19 19H9L3 22L3 5C3 3.89543 3.89543 3 5 3H19ZM19 5H5V18.468L8.44603 17H19V5ZM11 12V14H7V12H11ZM17 8V10H7V8H17Z"
              fill="black"
            />
          </svg>
        </button>
        <!-- <RouterLink v-if="isLogin == true" to="/alram">알람</RouterLink> -->
        <!-- 알람 드롭다운 시작해따-->
        <button
          id="dropdownNotificationButton"
          data-dropdown-toggle="dropdownNotification"
          class="relative inline-flex items-center text-sm font-medium text-center text-gray-500 hover:text-gray-900 focus:outline-none dark:hover:text-white dark:text-gray-400"
          type="button"
          @click="getAlarmList()"
          v-if="isLogin == true"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="18" viewBox="0 0 14 18" fill="none">
            <path fill-rule="evenodd" clip-rule="evenodd" d="M8 0C10.2816 0.463729 12 2.58146 12 5V9C13.1046 9 14 9.89543 14 11V16H9C9 17.1046 8.10457 18 7 18C5.89543 18 5 17.1046 5 16H0V11C0 9.89543 0.89543 9 2 9V5C2 2.58111 3.71785 0.463343 6 0H8ZM7 2C5.40232 2 4.09634 3.24892 4.00509 4.82373L4 5V9C4 10.1046 3.10457 11 2 11V14H12V11C10.8954 11 10 10.1046 10 9V5C10 3.34315 8.65685 2 7 2Z" fill="black"/>
          </svg>
        </button>

        <!-- Dropdown menu -->
        <div
          id="dropdownNotification"
          class="z-20 hidden w-full max-w-sm bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-800 dark:divide-gray-700"
          aria-labelledby="dropdownNotificationButton"
        >
          <div
            @click="removeAllAlarm()"
            class="block px-4 py-2 font-medium text-center text-gray-700 rounded-t-lg bg-gray-50 dark:bg-gray-800 dark:text-white"
          >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512" width="10" height="10">
              <path
                d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"
              />
            </svg>
          </div>
          <div v-if="isTeacher == false">
            <div
              v-for="alarm in alarmList"
              :key="alarm.alarmId"
              @click="removeAlarm(alarm.alarmId)"
              class="divide-y divide-gray-100 dark:divide-gray-700"
            >
              <div class="w-full ps-3">
                <span class="font-semibold text-gray-900 dark:text-white">{{
                  alarm.alarmType
                }}</span>
                <div class="text-gray-500 text-sm mb-1.5 dark:text-gray-400">
                  {{ alarm.alarmDate }}일 {{ alarm.alarmContent.substring(0, 5) }}
                  {{ alarm.alarmType.substring(0, 2) }}하였습니다!
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 384 512"
                    width="10"
                    height="10"
                  >
                    <path
                      d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"
                    />
                  </svg>
                </div>
              </div>
            </div>
          </div>
          <div v-if="isTeacher == true">
            <div
              v-for="alarm in alarmList"
              :key="alarm.alarmId"
              @click="removeAlarm(alarm.alarmId)"
              class="divide-y divide-gray-100 dark:divide-gray-700"
            >
              <div class="w-full ps-3">
                <span class="font-semibold text-gray-900 dark:text-white">{{
                  alarm.alarmType
                }}</span>
                <div class="text-gray-500 text-sm mb-1.5 dark:text-gray-400">
                  {{ alarm.kidName }} 원아의
                  {{ alarm.alarmType.substring(0, alarm.alarmType.length - 3) }} 확인이 필요합니다!
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 384 512"
                    width="10"
                    height="10"
                  >
                    <path
                      d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"
                    />
                  </svg>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 알람 드롭다운 끝나따-->
        <button
          v-if="isLogin == false"
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
          type="button"
          @click="login()"
        >
          로그인
        </button>
        <button
          v-if="isLogin == true && isTeacher == true"
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
          type="button"
          @click="logout()"
        >
          로그아웃
        </button>

        <!-- Dropdown button -->
        <button
          v-if="isLogin == true && isTeacher == false"
          id="dropdownDividerButton"
          data-dropdown-toggle="dropdownDivider"
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          type="button"
        >
          {{ kidList[0].kidName }} 학부모
          <svg
            class="w-2.5 h-2.5 ms-3"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 10 6"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="m1 1 4 4 4-4"
            />
          </svg>
          <!-- Dropdown menu (아이들 목록 보여주기)-->
          <div
            id="dropdownDivider"
            class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600"
          >
            <ul
              v-for="(kid, idx) in kidList"
              :key="idx"
              class="py-2 text-sm text-gray-700 dark:text-gray-200"
              aria-labelledby="dropdownDividerButton"
            >
              <li v-if="idx > 0">
                <a
                  @click="kidChange(idx)"
                  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                  >{{ kidList[idx].kidName }}</a
                >
              </li>
            </ul>
            <div class="py-2">
              <a
                @click="newKids()"
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
                >아이 등록</a
              >
              <a
                @click="logout()"
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
                >로그아웃</a
              >
            </div>
          </div>
          <!-- Dropdown menu -->
        </button>
      </div>
    </div>
  </div>

  <!-- (채팅) drawer component -->
  <div
    id="drawer-right-example"
    class="scrollbar-hide fixed top-0 right-0 z-40 h-screen border-l-2 border-l-gray-300 p-4 overflow-y-auto transition-transform translate-x-full bg-yellow-50 w-1/3 dark:bg-gray-800"
    tabindex="-1"
    ref="drawer"
    aria-labelledby="drawer-right-label"
  >
    <!-- <div
      class="flex flex-col justify-between h-screen p-8 mx-auto my-auto overflow-y-scroll bg-white border border-gray-200 rounded-lg shadow scrollbar-hide sm:p-8 dark:bg-gray-800 dark:border-gray-700"
    > -->
      <component
        :is="chatSwitch"
        @chatEvent="chatEvent"
        @exitChat="exitChat"
        :size="size"
        :roomInfo="roomInfo"
      ></component>
      <div v-if="flag == false" class="fixed w-1/3 bottom-0 right-0 p-3 border-l-2 border-l-gray-300 bg-yellow-50">
        <div class="flex items-center justify-evenly">
          <button :flag="flag" @click="showChat(ChatPersonView)">학부모목록</button>
          <button :flag="flag" @click="showChat(ChatListView)">채팅목록</button>
        </div>
      </div>
    <!-- </div> -->
  </div>
</template>

<script setup>
import { onMounted, nextTick, shallowRef, ref, watch } from 'vue'
import { initFlowbite } from 'flowbite'
import { kidRegister, getkidList } from '@/api/user'
import { kakaoLogin } from '@/api/oauth'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { loginStore } from '@/store/loginStore'
import { chatStore } from '@/store/chatStore'
import ChatListView from '@/components/chat/ChatListView.vue'
import ChatPersonView from '@/components/chat/ChatPersonView.vue'
import ChatDetailView from '../chat/ChatDetailView.vue'
import { saveToken, deleteToken } from '@/api/fcm'
import {
  alarmListOfParent,
  alarmListOfTeacher,
  deleteAlarm,
  deleteAllofParent,
  deleteAllofTeacher
} from '@/api/alarm'
import Swal from 'sweetalert2'

const {
  VITE_FIREBASE_APIKEY,
  VITE_FIREBASE_AUTHDOMAIN,
  VITE_FIREBASE_PROJECTID,
  VITE_FIREBASE_STORAGEBUCKET,
  VITE_FIREBASE_MESSAGINGSENDERID,
  VITE_FIREBASE_APPID,
  VITE_FIREBASE_MEASUREMENTID,
  VITE_KAKAO_CLIENT_ID,
  VITE_KAKAO_REDIRECT_URL
} = import.meta.env

const store = loginStore()
const chStore = chatStore()

const router = useRouter()
// const isLogin = ref(false)
// const isTeacher = ref(false)
// const kidList = ref([])

const drawer = ref(null)
const size = ref([])
const flag = ref(false)

const getSizeOfDrawer = () => {
  const width = drawer.value.offsetWidth
  const height = drawer.value.offsetHeight
  // console.log(`Drawer size: ${width}px x ${height}px`)
  // console.log(drawer.value)
  size.value = { width: width, height: height }
}

const msg = Swal.mixin({
  toast: true,
  position: 'center',
  input: 'text',
  inputPlaceholder: '등록 코드를 입력해주세요',
  showConfirmButton: true,
  showCancelButton: true,
  confirmButtonText: '등록',
  cancelButtonText: '취소',
  backdrop: true
}) // 아이등록 promt

const { chatName, chatReoom, isOpen } = storeToRefs(chStore)
const { isLogin, isTeacher, kidList, isAlarm, curKid } = storeToRefs(store)
const { setlogin, setCurkid, setlogout, setKidlist, setTeacherFlag, setTeachername, setAlarmFlag } =
  store
onMounted(() => {
  initFlowbite()
  // 로그인 여부 판단하기
  const token = localStorage.getItem('accessToken')
  // isLogin = token == null ? false : true
  // isTeacher = store.isTeacher;//sTeacher
  // console.log('나는 선생님인가? ' + isTeacher)
  if (isLogin) {
    if (!isTeacher) {
      kidList.value = JSON.parse(localStorage.getItem('kidList'))
    }
    alarm.value = isAlarm.value
    // console.log(isAlarm.value)
  }
  getSizeOfDrawer()
  // console.log(isLogin)
})

const roomInfo = ref([])
const chatEvent = (data) => {
  // console.log('change')
  // console.log(data)
  roomInfo.value = data
  chatSwitch.value = ChatDetailView
  flag.value = true
}

const exitChat = (input) => {
  if (input) {
    chatSwitch.value = ChatListView
    flag.value = false
  }
}

const chatSwitch = shallowRef(ChatPersonView)

const showChat = (name) => {
  // if(isOpen){ // 채팅방에서 하단 메뉴 클릭할 때
  //   console.log("채팅방에서 클릭")
  // }
  // flag.value = true
  chatSwitch.value = name
  // console.log(chatSwitch.value)
}

const login = () => {
  // kakaoLogin(({ data }) => {
  //   // console.log('로그인 가즈아')
  //   // console.log(data)
  //   window.location.href = data
  // })
  window.location.href =
    'https://kauth.kakao.com' +
    '/auth/authorize' +
    '?client_id=' +
    VITE_KAKAO_CLIENT_ID +
    '&redirect_uri=' +
    VITE_KAKAO_REDIRECT_URL +
    '&response_type=code&prompt=login'
}

const logout = () => {
  localStorage.removeItem('accessToken')
  localStorage.removeItem('refreshToken')
  if (!store.isTeacher) {
    setKidlist('')
  }
  // console.log("로그아웃 드가자")
  localStorage.removeItem('loginStore')
  localStorage.removeItem('chatStore')
  setlogout()
  setTeacherFlag(false)
  setTeachername('')
  window.location.href = '/'
}

const newKids = () => {
  msg
    .fire({
      icon: 'info'
    })
    .then(function (result) {
      if (result.isConfirmed) {
        kidRegister({ registCode: result.value }, ({ data }) => {
          // console.log(data)
          getkidList(({ data }) => {
            setKidlist(data.data)
          })
        })
      }
    })
}

const alarm = ref()

const firebaseConfig = {
  apiKey: VITE_FIREBASE_APIKEY,
  authDomain: VITE_FIREBASE_AUTHDOMAIN,
  projectId: VITE_FIREBASE_PROJECTID,
  storageBucket: VITE_FIREBASE_STORAGEBUCKET,
  messagingSenderId: VITE_FIREBASE_MESSAGINGSENDERID,
  appId: VITE_FIREBASE_APPID,
  measurementId: VITE_FIREBASE_MEASUREMENTID
}

firebase.initializeApp(firebaseConfig)
const messaging = firebase.messaging()

const tokenBox = ref({
  token: ''
})

const saveFcmToken = () => {
  messaging.getToken().then((tokenValue) => {
    tokenBox.value.token = tokenValue
    saveToken(
      tokenBox.value,
      (response) => {
        // console.log(response)
      },
      (error) => {
        // console.log(error)
      }
    )
  })
}

const deleteFcmToken = () => {
  deleteToken(
    (response) => {
      // console.log(response)
    },
    (error) => {
      // console.log(error)
    }
  )
}

watch(alarm, (newValue, oldValue) => {
  if(oldValue !== undefined) {
    if (newValue) {
      saveFcmToken()
      setAlarmFlag(true)
    } else {
      deleteFcmToken()
      setAlarmFlag(false)
    }
  }
})

const alarmList = ref([])

const getAlarmList = () => {
  if (isTeacher.value) {
    alarmListOfTeacher(
      ({ data }) => {
        alarmList.value = data.data
      },
      (error) => {
        console.log(error)
      }
    )
  }
  if (!isTeacher.value) {
    alarmListOfParent(
      curKid.value,
      ({ data }) => {
        alarmList.value = data.data
      },
      (error) => {
        console.log(error)
      }
    )
  }
}

const removeAllAlarm = () => {
  if (isTeacher.value) {
    // console.log("알람 선생님 전체 삭제");
    deleteAllofTeacher(
      (response) => {
        getAlarmList()
      },
      (error) => {
        console.log(error)
      }
    )
  }
  if (!isTeacher.value) {
    // console.log("알람 학부모 전체 삭제");
    deleteAllofParent(
      curKid.value,
      (response) => {
        getAlarmList()
      },
      (error) => {
        console.log(error)
      }
    )
  }
}

const removeAlarm = (alarmId) => {
  // console.log("알람 삭제", alarmId);
  deleteAlarm(
    alarmId,
    (response) => {
      getAlarmList()
    },
    (error) => {
      console.log(error)
    }
  )
}

const kidChange = (idx) => {
  // console.log(kidList.value[idx].kidId)
  setCurkid(kidList.value[idx].kidId)
  // 변경한 kidId 삭제 후 0번째 kid로 다시 넣기
  let tmp = kidList.value.splice(idx, 1)[0]
  kidList.value.unshift(tmp)
  location.reload()
}
</script>

<style scoped>
#header-layout {
  border: solid 1px black;
  height: 50px;
}
</style>
