<!-- eslint-disable prettier/prettier -->
<template>
  <div id="header-layout">
    <div class="flex justify-between items-center">
      <RouterLink to="/">Logo</RouterLink>
      <div>
        <RouterLink to="/setting">설정</RouterLink>
        <button
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
          type="button"
          data-drawer-target="drawer-right-example"
          data-drawer-show="drawer-right-example"
          data-drawer-placement="right"
          aria-controls="drawer-right-example"
        >
          채팅
        </button>
        <RouterLink to="/alram">알람</RouterLink>
        <button
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
          type="button"
          @click="login()"
        >
          로그인
        </button>
      </div>
    </div>
  </div>



  <!-- drawer component -->
  <div
    id="drawer-right-example"
    class="scrollbar-hide fixed top-0 right-0 z-40 h-screen p-4 overflow-y-auto transition-transform translate-x-full  bg-yellow-50 w-1/3 dark:bg-gray-800"
    tabindex="-1"
    aria-labelledby="drawer-right-label"
  >
   
    <div
    class="flex flex-col justify-between h-screen  p-8 mx-auto my-auto overflow-y-scroll bg-white border border-gray-200 rounded-lg shadow scrollbar-hide sm:p-8 dark:bg-gray-800 dark:border-gray-700"
  >
    <component :is="chatSwitch" @chatEvent="chatEvent" :roomInfo="roomInfo"></component>
    <!-- <div
      class="fixed bottom-0 right-0 flex items-end justify-between mt-auto left-3"
    ></div> -->
    <div class="fixed w-1/3 bottom-0 right-0 p-3 bg-yellow-50">
      <div class="flex items-center justify-evenly">
        <button @click="showChat(ChatPersonView)">
            학부모목록
          <!-- <img class="flex-none" src="@/assets/학부모목록.png" width="50px" /> -->
        </button>
        <button @click="showChat(ChatListView)">
            채팅목록
          <!-- <img class="flex-none" src="@/assets/채팅목록.png" width="50px" /> -->
        </button>
      </div>
      <!-- <div class="flex items-center justify-between">
        <a href="#" class="text-lg font-bold">홈</a>
        <a href="#" class="text-lg font-bold">서비스</a>
        <a href="#" class="text-lg font-bold">프로필</a>
      </div> -->
    </div>
  </div>
    
  </div>
</template>

<script setup>
import { onMounted, shallowRef, ref, watch } from 'vue';
import { initFlowbite } from 'flowbite';
import { kakaoLogin } from '@/api/oauth';
import ChatListView from "@/components/chat/ChatListView.vue";
import ChatPersonView from "@/components/chat/ChatPersonView.vue";
import ChatDetailView from '../chat/ChatDetailView.vue';

onMounted(() => {
    initFlowbite();
})

const roomInfo = ref([]);
// watch(chatId, (newValue) =>{
//   console.log("change");
//   console.log(newValue);
// });
const chatEvent = (data) => {
  console.log("change");
  console.log(data);
  roomInfo.value = data;
  chatSwitch.value = ChatDetailView;
}

const chatSwitch = shallowRef(ChatPersonView);

const showChat = (name) => {
  chatSwitch.value = name;
  console.log(chatSwitch.value);
};

const login = () => {
  kakaoLogin(({data}) => {
    console.log(data);
  })
}
</script>

<style scoped>
#header-layout {
  border: solid 1px black;
  height: 50px;
}
</style>
