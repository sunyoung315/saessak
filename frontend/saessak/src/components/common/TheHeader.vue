<!-- eslint-disable prettier/prettier -->
<template>
  <div class="header-frame flex justify-between items-center">
    <RouterLink to="/">
      <div class="flex items-end mx-2">
        <img src="/saessak-logo.png" alt="logo" class="h-16  px-3">
        <span class="text-4xl font-extrabold">새싹일기</span>
      </div>
    </RouterLink>

    <div class="flex justify-between items-center">
      <!-- 알림 활성화 토글 -->
      <div class="text-lg font-bold mx-3" v-if="isLogin">
        <span>알림 </span>
        <template v-if="alarm">
          <span>켜짐</span>
        </template>
        <template v-else>
          <span>꺼짐</span>
        </template>
      </div>
      <label class="relative inline-flex items-center cursor-pointer">
        <input type="checkbox" v-model="alarm" class="sr-only peer" />
        <div
          v-if="isLogin == true"
          class="mr-8 w-11 h-6 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-dark-navy"
        ></div>
      </label>
      
      <!-- 채팅 -->
      <button
        v-if="isLogin == true"
        type="button"
        data-drawer-target="drawer-right-example"
        data-drawer-show="drawer-right-example"
        data-drawer-placement="right"
        aria-controls="drawer-right-example"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none" class="w-6 h-6 mr-3">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M16 0C17.1046 0 18 0.89543 18 2V14C18 15.1046 17.1046 16 16 16H6L0 19V2C0 0.89543 0.89543 0 2 0H16ZM16 2H2V15.468L5.44603 14H16V2ZM8 9V11H4V9H8ZM14 5V7H4V5H14Z" fill="#000000"/>
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
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 15 20" fill="none" class="w-6 h-6">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M8 0C10.2816 0.463729 12 2.58146 12 5V9C13.1046 9 14 9.89543 14 11V16H9C9 17.1046 8.10457 18 7 18C5.89543 18 5 17.1046 5 16H0V11C0 9.89543 0.89543 9 2 9V5C2 2.58111 3.71785 0.463343 6 0H8ZM7 2C5.40232 2 4.09634 3.24892 4.00509 4.82373L4 5V9C4 10.1046 3.10457 11 2 11V14H12V11C10.8954 11 10 10.1046 10 9V5C10 3.34315 8.65685 2 7 2Z" fill="#000000"/>
        </svg>
      </button>

      <!-- Dropdown menu -->
      <div
        id="dropdownNotification"
        class="z-20 hidden w-72 max-w-sm bg-white divide-y divide-gray-100 rounded-lg shadow-md border border-gray-300 !-left-28"
        aria-labelledby="dropdownNotificationButton"
      >
        <div
          @click="removeAllAlarm()"
          class="block px-4 py-1 text-gray-900 text-sm rounded-t-lg bg-gray-100 text-right font-extrabold border-b border-gray-300"
        >
          전체 삭제
        </div>
        <div v-if="isTeacher == false" class="h-60 overflow-auto">
          <div
            v-for="alarm in alarmList"
            :key="alarm.alarmId"
            @click="removeAlarm(alarm.alarmId)"
            class="divide-y divide-gray-100 border-b border-gray-200 w-[96%] mx-[2%] py-1"
          >
            <div class="w-full ps-3 flex justify-between items-center">
              <div>
                <span class="font-semibold text-gray-900">{{
                  alarm.alarmType
                }}</span>
                <div class="text-gray-500 text-sm">
                  {{ alarm.alarmDate }}일 {{ alarm.alarmContent.substring(0, 5) }}
                  {{ alarm.alarmType.substring(0, 2) }}하였습니다!
                </div>
              </div>
              <div class="mx-3">
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
        <div v-if="isTeacher == true" class="h-60 overflow-auto">
          <div
            v-for="alarm in alarmList"
            :key="alarm.alarmId"
            @click="removeAlarm(alarm.alarmId)"
            class="divide-y divide-gray-100 border-b border-gray-200 w-[96%] mx-[2%] py-1"
          >
            <div class="w-full ps-3 flex justify-between items-center">
              <div>
                <span class="font-semibold text-gray-900 ">{{
                  alarm.alarmType
                }}</span>
                <div class="text-gray-500 text-sm mb-1.5 ">
                  {{ alarm.kidName }} 원아의
                  {{ alarm.alarmType.substring(0, alarm.alarmType.length - 3) }} 확인이 필요합니다!
                </div>
              </div>
              <div class="mx-3">
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
        type="button"
        @click="login()"
      >
        <img src="/icons/kakao_login.png" alt="login" class="mx-5">
      </button>
      <button
        v-if="isLogin == true && isTeacher == true"
        class="btn m-0 mx-5"
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
        class="text-black text-lg font-semibold px-5 py-2.5 text-center inline-flex items-center"
        type="button"
      >
        <img :src="kidList[0].kidProfile" alt="profile" class="rounded-full w-12 h-12 border-2 border-gray-400 shadow-md mx-3">
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
          class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow-md !left-8 !-top-5 !w-36 border"
        >
          <ul
            v-for="(kid, idx) in kidList"
            :key="idx"
            class="text-sm text-gray-700"
            aria-labelledby="dropdownDividerButton"
          >
            <li v-if="idx > 0">
              <a
                @click="kidChange(idx)"
                class="block px-4 py-2 rounded-lg hover:bg-gray-100"
                >{{ kidList[idx].kidName }}</a
              >
            </li>
          </ul>
          <div class="py-1 bg-gray-200 rounded-b-lg">
            <a
              @click="newKids()"
              class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-300"
              >아이 등록</a
            >
            <a
              @click="logout()"
              class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-300"
              >로그아웃</a
            >
          </div>
        </div>
        <!-- Dropdown menu -->
      </button>
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
import { onMounted, nextTick, shallowRef, ref, watch } from 'vue';
import { initFlowbite } from 'flowbite';
import { kidRegister, getkidList } from '@/api/user';
import { userLogout } from '@/api/oauth';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { loginStore } from '@/store/loginStore';
import { chatStore } from '@/store/chatStore';
import ChatListView from '@/components/chat/ChatListView.vue';
import ChatPersonView from '@/components/chat/ChatPersonView.vue';
import ChatDetailView from '../chat/ChatDetailView.vue';
import { saveToken, deleteToken } from '@/api/fcm';
import {
	alarmListOfParent,
	alarmListOfTeacher,
	deleteAlarm,
	deleteAllofParent,
	deleteAllofTeacher,
} from '@/api/alarm';
import Swal from 'sweetalert2';

const {
	VITE_FIREBASE_APIKEY,
	VITE_FIREBASE_AUTHDOMAIN,
	VITE_FIREBASE_PROJECTID,
	VITE_FIREBASE_STORAGEBUCKET,
	VITE_FIREBASE_MESSAGINGSENDERID,
	VITE_FIREBASE_APPID,
	VITE_FIREBASE_MEASUREMENTID,
	VITE_KAKAO_CLIENT_ID,
	VITE_KAKAO_REDIRECT_URL,
} = import.meta.env;

const store = loginStore();
const chStore = chatStore();

const router = useRouter();
// const isLogin = ref(false)
// const isTeacher = ref(false)
// const kidList = ref([])

const drawer = ref(null);
const size = ref([]);
const flag = ref(false);

const getSizeOfDrawer = () => {
	const width = drawer.value.offsetWidth;
	const height = drawer.value.offsetHeight;
	// console.log(`Drawer size: ${width}px x ${height}px`)
	// console.log(drawer.value)
	size.value = { width: width, height: height };
};

const msg = Swal.mixin({
	toast: true,
	position: 'center',
	input: 'text',
	inputPlaceholder: '등록 코드를 입력해주세요',
	showConfirmButton: true,
	showCancelButton: true,
	confirmButtonText: '등록',
	cancelButtonText: '취소',
	backdrop: true,
}); // 아이등록 promt

const { chatName, chatReoom, isOpen } = storeToRefs(chStore);
const { isLogin, isTeacher, kidList, isAlarm, curKid } = storeToRefs(store);
const {
	setlogin,
	setCurkid,
	setlogout,
	setKidlist,
	setTeacherFlag,
	setTeachername,
	setAlarmFlag,
} = store;
onMounted(() => {
	initFlowbite();
	// 로그인 여부 판단하기
	const token = localStorage.getItem('accessToken');
	// isLogin = token == null ? false : true
	// isTeacher = store.isTeacher;//sTeacher
	// console.log('나는 선생님인가? ' + isTeacher)
  console.log(isLogin)
	if (isLogin) {
		if (!isTeacher) {
			kidList.value = JSON.parse(localStorage.getItem('kidList'));
		}
		alarm.value = isAlarm.value;
		// console.log(isAlarm.value)
	}
	getSizeOfDrawer();
	// console.log(isLogin)
});

const roomInfo = ref([]);
const chatEvent = data => {
	// console.log('change')
	// console.log(data)
	roomInfo.value = data;
	chatSwitch.value = ChatDetailView;
	flag.value = true;
};

const exitChat = input => {
	if (input) {
		chatSwitch.value = ChatListView;
		flag.value = false;
	}
};

const chatSwitch = shallowRef(ChatPersonView);

const showChat = name => {
	// if(isOpen){ // 채팅방에서 하단 메뉴 클릭할 때
	//   console.log("채팅방에서 클릭")
	// }
	// flag.value = true
	chatSwitch.value = name;
	// console.log(chatSwitch.value)
};

const login = () => {
	window.location.href =
		'https://kauth.kakao.com' +
		'/auth/authorize' +
		'?client_id=' +
		VITE_KAKAO_CLIENT_ID +
		'&redirect_uri=' +
		VITE_KAKAO_REDIRECT_URL +
		'&response_type=code&prompt=login';
};

const logout = () => {
  userLogout();
	localStorage.removeItem('accessToken');
	localStorage.removeItem('refreshToken');
	if (!store.isTeacher) {
		setKidlist('');
	}
	// console.log("로그아웃 드가자")
	localStorage.removeItem('loginStore');
	localStorage.removeItem('chatStore');
	setlogout();
	setTeacherFlag(false);
	setTeachername('');
	window.location.href = '/';
};

const newKids = () => {
	msg
		.fire({
			icon: 'info',
		})
		.then(function (result) {
			if (result.isConfirmed) {
				kidRegister({ registCode: result.value }, ({ data }) => {
					// console.log(data)
					getkidList(({ data }) => {
						setKidlist(data.data);
					});
				});
			}
		});
};

const alarm = ref();

const firebaseConfig = {
	apiKey: VITE_FIREBASE_APIKEY,
	authDomain: VITE_FIREBASE_AUTHDOMAIN,
	projectId: VITE_FIREBASE_PROJECTID,
	storageBucket: VITE_FIREBASE_STORAGEBUCKET,
	messagingSenderId: VITE_FIREBASE_MESSAGINGSENDERID,
	appId: VITE_FIREBASE_APPID,
	measurementId: VITE_FIREBASE_MEASUREMENTID,
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();

messaging.onMessage((payload) => {
    console.log('[클라이언트] 데이터 메시지 수신: ', payload.notification);

    navigator.serviceWorker.controller.postMessage({
        type: 'foreground',
        payload: payload.notification
    });
});

const tokenBox = ref({
	token: '',
});

const saveFcmToken = () => {
	messaging.getToken().then(tokenValue => {
		tokenBox.value.token = tokenValue;
		saveToken(
			tokenBox.value,
			response => {
				// console.log(response)
			},
			error => {
				// console.log(error)
			},
		);
	});
};

const deleteFcmToken = () => {
	deleteToken(
		response => {
			// console.log(response)
		},
		error => {
			// console.log(error)
		},
	);
};

watch(alarm, (newValue, oldValue) => {
	if (oldValue !== undefined) {
		if (newValue) {
			saveFcmToken();
			setAlarmFlag(true);
		} else {
			deleteFcmToken();
			setAlarmFlag(false);
		}
	}
});

const alarmList = ref([]);

const getAlarmList = () => {
	if (isTeacher.value) {
		alarmListOfTeacher(
			({ data }) => {
				alarmList.value = data.data;
			},
			error => {
				console.log(error);
			},
		);
	}
	if (!isTeacher.value) {
		alarmListOfParent(
			curKid.value,
			({ data }) => {
				alarmList.value = data.data;
			},
			error => {
				console.log(error);
			},
		);
	}
};

const removeAllAlarm = () => {
	if (isTeacher.value) {
		// console.log("알람 선생님 전체 삭제");
		deleteAllofTeacher(
			response => {
				getAlarmList();
			},
			error => {
				console.log(error);
			},
		);
	}
	if (!isTeacher.value) {
		// console.log("알람 학부모 전체 삭제");
		deleteAllofParent(
			curKid.value,
			response => {
				getAlarmList();
			},
			error => {
				console.log(error);
			},
		);
	}
};

const removeAlarm = alarmId => {
	// console.log("알람 삭제", alarmId);
	deleteAlarm(
		alarmId,
		response => {
			getAlarmList();
		},
		error => {
			console.log(error);
		},
	);
};

const kidChange = idx => {
	// console.log(kidList.value[idx].kidId)
	setCurkid(kidList.value[idx].kidId);
	// 변경한 kidId 삭제 후 0번째 kid로 다시 넣기
	let tmp = kidList.value.splice(idx, 1)[0];
	kidList.value.unshift(tmp);
	location.reload();
};
</script>

<style scoped>
.header-frame {
	@apply h-20 bg-gray-50 shadow-md;
}
</style>
