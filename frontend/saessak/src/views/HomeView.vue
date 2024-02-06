<template>
  <div>
    <TodoView></TodoView>

  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { useRoute, useRouter } from 'vue-router'
import { loginStore } from '@/store/loginStore'
import TodoView from '@/components/todo/TodoView.vue'
const route = useRoute()
const router = useRouter()
const store = loginStore()

const { isLogin, isTeacher, kidList, userId, isAlarm } = storeToRefs(store)
const { setUserid, setlogin, setTeacherFlag, setKidlist, setTeachername, setCurkid, setAlarmFlag} = store
const code = ref(null)

onMounted(() => {
  code.value = route.query.code
  if (code.value != null) {
    // console.log(code.value)
    axios
      .get('https://i10a706.p.ssafy.io/api/oauth/kakao/callback/' + code.value)
      // 발급된 코드를 갖고 신규/기존 회원 여부 판별하는 axios 호출
      .then(({ data }) => {
        // console.log('카카오 로그인 전')
        // console.log(data)
        // console.log(data.data)
        if (data.data.accessToken === 'null') {
          // 회원가입
          //   console.log('회원가입 필요')
          alert('회원가입이 필요합니다!')
          setUserid(data.data.userId)
          router.push({ path: '/join' })
        } else {
          // 로그인
          //   console.log('로그인 드갈까?')
          KLogin(data)
        }
        // 기존에 있는 회원 -> 바로 로그인
        // 신규 회원 -> 인증코드 입력 받기 -> 로그인
        //   location.href="/";
        //   route.push({name : "Home"});
      })
  }
})

const KLogin = (input) => {
  //   console.log('로그인 드가자')
  //   console.log(input)
  if (input.data.isTeacher) {
    // 선생님 로그인
    setTeacherFlag(true)
    setAlarmFlag(input.data.isAlarm)
    // console.log('나는 선생님이다')
    // console.log(store.isTeacher)
    // sessionStorage.setItem('isTeacher', input.data.isTeacher)
    sessionStorage.setItem('accessToken', input.data.accessToken)
    sessionStorage.setItem('refreshToken', input.data.refreshToken)
    setTeachername(input.data.teacherName)
    // sessionStorage.setItem('teacherName', input.data.teacherName)
    // sessionStorage.setItem('teacherId', input.data.teacherId)
    // sessionStorage.setItem('classroomId', input.data.classroomId)
  } else {
    // 학부모 로그인
    setTeacherFlag(false);
    setAlarmFlag(input.data.isAlarm)
    // sessionStorage.setItem('isTeacher', input.data.isTeacher)
    sessionStorage.setItem('accessToken', input.data.accessToken)
    sessionStorage.setItem('refreshToken', input.data.refreshToken) // 토큰만 세션에 저장
    // sessionStorage.setItem('kidList', JSON.stringify(input.data.kidList))
    setKidlist(input.data.kidList) // 나머지 정보는 pinia 저장
    setCurkid(input.data.kidList[0].kidId)
  }
  setlogin()
  //   console.log('KLogin 실행')
  location.href = '/'
}
</script>

<style scoped></style>
