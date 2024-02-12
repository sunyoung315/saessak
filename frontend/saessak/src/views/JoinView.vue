<template>
  <div class="flex flex-col items-center justify-center h-screen">
    <div class="flex items-center justify-center mb-3">전달받은 코드를 입력해주세요 !</div>
    <form class="input-code">
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="text"
          v-model="joinCode"
          name="joinCode"
          id="joinCode"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
          placeholder=" "
          required
        />
        <label
          for="joinCode"
          class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >가입코드</label
        >
      </div>
      <button
        @click="Join()"
        type="button"
        class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      >
        회원가입
      </button>
    </form>
    <div class="flex items-center justify-center mb-3">안심하세요 !</div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import { loginStore } from '@/store/loginStore'

const route = useRoute()
const store = loginStore()
const code = ref(null)

const { isLogin, isTeacher, kidList, userId, isAlarm } = storeToRefs(store)
const { setUserid, setlogin, setTeacherFlag, setKidlist, setTeachername, setAlarmFlag} = store
const joinCode = ref('') // 가입 인증 코드
onMounted(() => {
  if (userId != 0) {
    // console.log('전송받은 userId : ');
    // console.log(userId.value)
  }
})

const KLogin = (input) => {
  if (input.data.isTeacher) {
    // 선생님 로그인
    setTeacherFlag(true)
    setAlarmFlag(input.data.alarm)
    localStorage.setItem('refreshToken', input.data.refreshToken)
    localStorage.setItem('accessToken', input.data.accessToken)
    setTeachername(input.data.teacherName)
  } else {
    // 학부모 로그인
    setTeacherFlag(false)
    setAlarmFlag(input.data.alarm)
    // sessionStorage.setItem('isTeacher', input.data.isTeacher)
    localStorage.setItem('accessToken', input.data.accessToken)
    localStorage.setItem('refreshToken', input.data.refreshToken) // 토큰만 세션에 저장
    // sessionStorage.setItem('kidList', JSON.stringify(input.data.kidList))
    setKidlist(input.data.kidList) // 나머지 정보는 pinia 저장
  }
  // console.log('KLogin 실행')
  setlogin()
  location.href = '/'
}

const Join = () => {
  const data = {
    userId: userId.value,
    registCode: joinCode.value
  }
  // console.log('전송 data ')
  // console.log(data)
  axios
    .post('https://i10a706.p.ssafy.io/api/oauth/kakao/join', data)
    // 발급된 코드를 갖고 신규/기존 회원 여부 판별하는 axios 호출
    .then(({ data }) => {
      // console.log(data)
      KLogin(data)
    })
    .catch((error) => {
      // console.log(error)
    })
}
</script>

<style></style>
