<template>
  <div>
    <v-date-picker v-model="range" is-range :disabled-dates="disabledDates" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const range = ref([
  {
    start: new Date(),
    end: new Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000), // 현재 선택한 날짜부터 7일 이내까지 선택 가능
  },
]);



console.log(range.value);
console.log(new Date());

const disabledDates = [
  {
    start: new Date(),
    end: new Date(9999, 11, 31), // 미래의 일자 비활성화
  },
  {
    start: new Date(1900, 0, 1), // 내가 select한 날짜
    end: new Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000), // select한 날짜 + 7일
  },
];
</script>

<style scoped>
</style>
