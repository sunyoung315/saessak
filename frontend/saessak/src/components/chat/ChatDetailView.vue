<template>
  <div
    class="flex flex-col w-full h-screen max-w-md p-8 mx-auto my-autoborder border-gray-200 rounded-lg shadow bg-yellow-50 sm:p-8 dark:bg-gray-800 dark:border-gray-700"
  >
    <div
      class="fixed left-0 right-0 top-0 z-10 flex justify-between w-full max-w-md px-3 py-5 pb-4 mx-auto"
    >
      <h3 class="mb-5 text-lg font-bold text-center left-1/2">
        {{ $route.params.id }}번 채팅방
      </h3>
    </div>

    <!-- message -->
    <div
      class="flex flex-col justify-between scrollbar-hide overflow-y-scroll w-full h-5/6 mt-10 px-5"
    >
      <div v-for="msg in chat" :key="msg.chatId" class="flex flex-col mt-5">
        <!--발신 메시지-->
        <div v-if="msg.senderId === 'teacher'" class="flex justify-end mb-4">
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

        <!--수신 메시지-->
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
    <!-- end message -->
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

    <!-- <div   
        class="fixed bottom-0 right-0 flex items-end justify-between mt-auto left-3"
      ></div> -->
    <div class="fixed bottom-0 left-0 right-0 w-full max-w-md p-2 mx-auto">
      <div class="flex items-center justify-evenly">
        <div class="w-3/4 mt-4 mb-5">
          <input
            type="text"
            id="base-input"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          />
        </div>
        <button
          type="button"
          class="text-white bg-gray-600 -mt-1 hover:bg-yellow-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center me-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
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
const chat = [
  { chatId: 1, chatContent: "안녕하세요~", senderId: "parent" },
  { chatId: 2, chatContent: "안녕하세요 ㅎㅎ", senderId: "teacher" },
  { chatId: 3, chatContent: "알림장 확인 부탁드립니다", senderId: "teacher" },
  { chatId: 4, chatContent: "네 감사합니다~", senderId: "parent" },
  { chatId: 5, chatContent: "안녕하세요~", senderId: "parent" },
  { chatId: 6, chatContent: "안녕하세요 ㅎㅎ", senderId: "teacher" },
  { chatId: 7, chatContent: "알림장 확인 부탁드립니다", senderId: "teacher" },
  { chatId: 8, chatContent: "네 감사합니다~", senderId: "parent" },
  { chatId: 9, chatContent: "안녕하세요~", senderId: "parent" },
  { chatId: 10, chatContent: "안녕하세요 ㅎㅎ", senderId: "teacher" },
  { chatId: 11, chatContent: "알림장 확인 부탁드립니다", senderId: "teacher" },
  { chatId: 12, chatContent: "네 감사합니다~", senderId: "parent" },
  { chatId: 13, chatContent: "안녕하세요~", senderId: "parent" },
  { chatId: 14, chatContent: "안녕하세요 ㅎㅎ", senderId: "teacher" },
  { chatId: 15, chatContent: "알림장 확인 부탁드립니다", senderId: "teacher" },
  { chatId: 16, chatContent: "네 감사합니다~", senderId: "parent" },
];
</script>

<style></style>
