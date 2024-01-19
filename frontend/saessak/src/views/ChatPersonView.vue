<template>
    
  <div
    class="fixed top-0 left-0 right-0 z-10 flex items-center justify-between w-full max-w-md px-3 py-5 pb-4 mx-auto bg-yellow-50"
  >
    <h5 class="flex items-center justify-center text-xl font-bold leading-none text-gray-900 dark:text-white">
      학부모 목록
    </h5>
  </div>
  <div class="flow-root grow">
    <div
      class="w-full max-w-sm mt-3 bg-white dark:bg-gray-800 dark:border-gray-700"
    >
      <div class="flex flex-wrap pt-5 mt-auto mb-3 justify-evenly">
        <div
          v-for="person in Person"
          :key="person.id"
          class="flex flex-col items-center w-5/12 mb-3 border border-gray-200 rounded-lg shadow pb-10mb-4"
        >
          <img
            class="w-24 h-24 my-2 mb-3 rounded-full shadow-lg"
            src="https://flowbite.com/docs/images/people/profile-picture-3.jpg"
            alt="Bonnie image"
          />
          <h5 class="text-xl font-medium text-gray-900 dark:text-white">
            {{ person.name }}
          </h5>
          <span class="mb-0 text-sm text-gray-500 dark:text-gray-400"
            >Visual Designer</span
          >
          <div class="flex mt-3 mb-2">
            <a
              href="#"
              class="inline-flex items-center px-4 py-2 text-center text-white bg-blue-700 rounded-lg font -medium mt-0text-sm hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
              >MESSAGE</a
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const Person = [
  { id: 1, name: "김하은" },
  { id: 2, name: "윤선아" },
  { id: 3, name: "이민주" },
  { id: 4, name: "조효은" },
  { id: 5, name: "김쪼꼬" },
  { id: 6, name: "루피" },
  { id: 7, name: "마라샹궈" },
  { id: 8, name: "꿔바로우" },
  { id: 7, name: "햅쌀와플" },
  { id: 8, name: "떡튀순" },
];
</script>
<style></style>
