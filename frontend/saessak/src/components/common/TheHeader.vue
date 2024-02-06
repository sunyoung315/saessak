<!-- eslint-disable prettier/prettier -->
<template>
  <div id="header-layout">
    <div class="flex justify-between items-center">
      <RouterLink to="/">Logo</RouterLink>
      <div>
        <label class="relative inline-flex items-center cursor-pointer">
          <input type="checkbox" v-model="alarm" class="sr-only peer" >
          <div class=" mr-8 w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600"></div>
        </label>

        <RouterLink v-if="isLogin == true" to="/setting">설정</RouterLink>
        <button
          v-if="isLogin == true"
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
          type="button"
          data-drawer-target="drawer-right-example"
          data-drawer-show="drawer-right-example"
          data-drawer-placement="right"
          aria-controls="drawer-right-example"
        >
          채팅
        </button>
        <RouterLink v-if="isLogin == true" to="/alram">알람</RouterLink>

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

  <!-- drawer component -->
  <div
    id="drawer-right-example"
    class="scrollbar-hide fixed top-0 right-0 z-40 h-screen p-4 overflow-y-auto transition-transform translate-x-full bg-yellow-50 w-1/3 dark:bg-gray-800"
    tabindex="-1"
    ref="drawer"
    aria-labelledby="drawer-right-label"
  >
    <div
      class="flex flex-col justify-between h-screen p-8 mx-auto my-auto overflow-y-scroll bg-white border border-gray-200 rounded-lg shadow scrollbar-hide sm:p-8 dark:bg-gray-800 dark:border-gray-700"
    >
      <component
        :is="chatSwitch"
        @chatEvent="chatEvent"
        @exitChat="exitChat"
        :size="size"
        :roomInfo="roomInfo"
      ></component>
      <div v-if="flag == false" class="fixed w-1/3 bottom-0 right-0 p-3 bg-yellow-50">
        <div class="flex items-center justify-evenly">
          <button :flag="flag" @click="showChat(ChatPersonView)">학부모목록</button>
          <button :flag="flag" @click="showChat(ChatListView)">채팅목록</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, nextTick, shallowRef, ref, watch } from 'vue'
import { initFlowbite } from 'flowbite'
import { kakaoLogin } from '@/api/oauth'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { loginStore } from '@/store/loginStore'
import { chatStore } from '@/store/chatStore'
import ChatListView from '@/components/chat/ChatListView.vue'
import ChatPersonView from '@/components/chat/ChatPersonView.vue'
import ChatDetailView from '../chat/ChatDetailView.vue'
import { saveToken, deleteToken } from "@/api/fcm";

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

const { chatName, chatReoom, isOpen } = storeToRefs(chStore)
const { isLogin, isTeacher, kidList, isAlarm } = storeToRefs(store)
const { setlogin, setCurkid, setlogout, setKidlist, setTeacherFlag, setTeachername } = store
onMounted(() => {
  initFlowbite()
  // 로그인 여부 판단하기
  const token = sessionStorage.getItem('accessToken')
  // isLogin = token == null ? false : true
  // isTeacher = store.isTeacher;//sTeacher
  // console.log('나는 선생님인가? ' + isTeacher)
  if (isLogin) {
    if (!isTeacher) {
      kidList.value = JSON.parse(sessionStorage.getItem('kidList'))
    }
    alarm.value = isAlarm.value
    console.log(isAlarm.value);
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
  flag.value = true;
}

const exitChat = (input) => {
  if (input) {
    chatSwitch.value = ChatListView
    flag.value = false;
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
  kakaoLogin(({ data }) => {
    // console.log('로그인 가즈아')
    // console.log(data)
    window.location.href = data
  })
}

const logout = () => {
  sessionStorage.removeItem('accessToken')
  sessionStorage.removeItem('refreshToken')
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

const alarm = ref();

const tokenBox = ref({
  token: '',
});

const saveFcmToken = () => {
    messaging.getToken()
    .then((tokenValue) => {
      tokenBox.value.token = tokenValue;
      saveToken(tokenBox.value,
      (response) => {
        // console.log(response);
      },
      (error) => {
        console.log(error);
      })
    }
  );
}

const deleteFcmToken = () => {
  deleteToken(
    (response) => {
      // console.log(response);
    },
    (error) => {
      console.log(error);
    }
  );
}

watch(alarm, (newValue) => {
  if(newValue) {
    // console.log('checked');
    saveFcmToken();
  } else {
    // console.log('unchecked');
    deleteFcmToken();
  }}
)

const kidChange = (idx) => {
  console.log(kidList.value[idx].kidId)
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
