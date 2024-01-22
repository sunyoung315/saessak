<template>
  <div
    class="fixed top-0 left-0 right-0 z-10 flex items-center justify-between w-full max-w-md px-3 py-5 pb-5 mx-auto bg-yellow-50"
  >
    <h5
      class="flex items-center justify-center text-xl font-bold leading-none text-gray-900 dark:text-white"
    >
      채팅 목록
    </h5>
  </div>
  <div class="flow-root mt-10 grow">
    <ul role="list" class="" v-for="chatItem in chat" :key="chatItem.chatId">
      <li
        v-if="chatItem.unread === false"
        class="py-3 mb-2 border rounded-lg sm:py-4"
      >
        <div
          @click="chatDetail(chatItem.chatId)"
          class="flex items-center px-3"
        >
          <div class="flex-shrink-0">
            <img
              class="w-8 h-8 rounded-full"
              src="https://flowbite.com/docs/images/people/profile-picture-4.jpg"
              alt="Neil image"
            />
          </div>
          <div class="flex-1 min-w-0 ms-4">
            <p
              class="text-sm font-medium text-gray-900 truncate dark:text-white"
            >
              {{ chatItem.name }}
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
              {{ chatItem.name }}
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
const chat = [
  { chatId: 1, name: "김하은", lastChat: "네 확인했습니다 ^^", unread: true },
  {
    chatId: 2,
    name: "윤선아",
    lastChat: "안녕하세요. 가능합니다. ",
    unread: false,
  },
  { chatId: 3, name: "이민주", lastChat: "확인부탁드립니다 !", unread: false },
];

const chatDetail = (chatId) => {
  console.log(chatId);
};
</script>
<style></style>
