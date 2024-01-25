
<template>
  <div
    class="flex flex-col justify-between w-full h-screen max-w-md p-8 mx-auto my-auto overflow-y-scroll bg-white border border-gray-200 rounded-lg shadow scrollbar-hide sm:p-8 dark:bg-gray-800 dark:border-gray-700"
  >
    <component :is="chatSwitch"></component>
    <!-- <div
      class="fixed bottom-0 right-0 flex items-end justify-between mt-auto left-3"
    ></div> -->
    <div class="fixed bottom-0 left-0 right-0 w-full max-w-md p-2 mx-auto bg-yellow-50">
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
</template>

<script setup>
import { shallowRef, ref } from "vue";
import ChatListView from "./ChatListView.vue";
import ChatPersonView from "./ChatPersonView.vue";

const chatSwitch = shallowRef(ChatPersonView);

const showChat = (name) => {
  chatSwitch.value = name;
  console.log(chatSwitch.value);
};
</script>
<style scoped>

</style>
