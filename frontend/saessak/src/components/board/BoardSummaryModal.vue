<template>
  <div class="flowbit-modal fixed inset-0 flex items-center justify-center z-50" v-if="isOpen">
    <div class="flowbit-modal__overlay absolute inset-0 bg-black opacity-50"></div>
    <div
      class="flowbit-modal__container bg-white max-w-md m-8 md:m-0 rounded-lg shadow-lg z-50 overflow-y-auto"
    >
      <!-- Title & Close Btn -->
      <div class="flowbit-modal__header px-4 py-2 flex justify-between items-center">
        <div>
          <div class="mx-20 mt-10 inline-block text-xl font-extrabold">요약레포트</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useBoardStore } from '@/store/board'
import OpenAI from 'openai'

// Modal
const isOpen = ref(false)

const openModal = () => {
  isOpen.value = true
}
const closeModal = () => {
  isOpen.value = false
}

defineExpose({ openModal })

// VDatePicker
const startDate = ref('')
const endDate = ref('')

// datepicker 선택 날짜 색상 지정
const selectAttribute = ref({ highlight: 'yellow' })

// 미래날짜 비활성화
const today = new Date()
const tomorrow = new Date(today)
tomorrow.setDate(tomorrow.getDate() + 1)
const disabledStartDates = ref([
  {
    start: tomorrow,
    end: null
  }
])

// 시작 날짜 추적, 시작일자로부터 7일만 활성화
watch(startDate, () => {
  const start = startDate.value
  const pastDates = []
  const futureDates = []

  const beforeStart = new Date(start)
  beforeStart.setDate(start.getDate() - 1)
  pastDates.push({ start: null, end: beforeStart })

  const end = new Date(start)
  if (end.getDate() + 7 > today.getDate()) {
    end.setDate(today.getDate() + 1)
  } else {
    end.setDate(end.getDate() + 7)
  }

  futureDates.push({ start: end, end: null })

  disabledEndDates.value = [...pastDates, ...futureDates]
})

// 선택한 시작날짜로부터 7일이후 날짜 비활성화
const startSevenDays = new Date(startDate.value)
startSevenDays.setDate(startSevenDays.getDate() + 7)
const disabledEndDates = ref([
  {
    start: startSevenDays.value,
    end: null
  }
])

const store = useBoardStore()

// 선택 기간의 알림장 1줄 string으로 변환된 정보
let content = ref('')
// OpenAI로 요약한 정보
const summary = ref('')

// OpenAI 요약
const API_KEY = import.meta.env.VITE_OPENAI_API_KEY

const getGPTResponse = async () => {
  try {
    const openai = new OpenAI({
      apiKey: API_KEY,
      dangerouslyAllowBrowser: true
    })

    const prompt = `${store.boardList.length}일치의 알림장인 ${content.value} 를 전부 합쳐서 날짜 ':'으로 구분하지 말고 자연스럽게 이어서 150자 정도로 유치원 선생님 말투로 요약해줘.`

    const response = await openai.chat.completions.create({
      messages: [
        {
          role: 'user',
          content: prompt
        }
      ],
      model: 'gpt-3.5-turbo'
    })
    console.log('요약 결과 : ', response.choices[0].message.content)
    summary.value = response.choices[0].message.content
  } catch (error) {
    console.log('에러 발생 : ', error)
  }
}

const getSummaryBoard = async (kidId, { startDate, endDate }) => {
  await store.getSummaryBoard(kidId, { startDate, endDate })

  // boardContent 하나의 String으로 연결
  for (let i = 0; i < store.boardList.length; i++) {
    content.value +=
      store.boardList[i].boardDate + ' 의 알림장 :  \n' + store.boardList[i].boardContent + '\n'
  }

  getGPTResponse()
}
</script>

<style scoped>
.flowbit-modal__container {
  width: 60rem;
  height: 40rem;
  max-width: none;
}
.loader {
  position: relative;
  width: 108px;
  display: flex;
  justify-content: space-between;
}
.loader::after,
.loader::before {
  content: '';
  display: inline-block;
  width: 48px;
  height: 48px;
  background-color: #fff;
  background-image: radial-gradient(circle 14px, #0d161b 100%, transparent 0);
  background-repeat: no-repeat;
  border: black solid 3px;
  border-radius: 50%;
  animation:
    eyeMove 10s infinite,
    blink 10s infinite;
}
@keyframes eyeMove {
  0%,
  10% {
    background-position: 0px 0px;
  }
  13%,
  40% {
    background-position: -15px 0px;
  }
  43%,
  70% {
    background-position: 15px 0px;
  }
  73%,
  90% {
    background-position: 0px 15px;
  }
  93%,
  100% {
    background-position: 0px 0px;
  }
}
@keyframes blink {
  0%,
  10%,
  12%,
  20%,
  22%,
  40%,
  42%,
  60%,
  62%,
  70%,
  72%,
  90%,
  92%,
  98%,
  100% {
    height: 48px;
  }
  11%,
  21%,
  41%,
  61%,
  71%,
  91%,
  99% {
    height: 18px;
  }
}
</style>
