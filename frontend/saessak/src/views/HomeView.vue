<template>
  <MainView />
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { useRoute, useRouter } from 'vue-router'
import { loginStore } from '@/store/loginStore'
import Swal from 'sweetalert2'
import MainView from '@/views/MainView.vue'
import { userDelete } from '@/api/oauth'
const route = useRoute()
const router = useRouter()
const store = loginStore()

const { isLogin, isTeacher, kidList, userId, isAlarm } = storeToRefs(store)
const {
  setUserid,
  setlogin,
  setTeacherFlag,
  setKidlist,
  setProfile,
  setTeachername,
  setClassroomname,
  setCurkid,
  setAlarmFlag
} = store
const code = ref(null)

const msg = Swal.mixin({
  toast: true,
  position: 'center',
  input: 'text',
  inputPlaceholder: '등록 코드를 입력해주세요',
  showConfirmButton: true,
  showCancelButton: true
}) // 회원가입 promt

const msg2 = Swal.mixin({
  toast: true,
  position: 'center',
  showConfirmButton: true
}) // 에러 alert

const { VITE_KAKAO_CLIENT_ID, VITE_KAKAO_CLIENT_SECRET, VITE_KAKAO_REDIRECT_URL } = import.meta.env

onMounted(() => {
  code.value = route.query.code
  if (code.value != null) {
    axios
      .post(
        'https://kauth.kakao.com/oauth/token',
        {
          grant_type: 'authorization_code',
          client_id: VITE_KAKAO_CLIENT_ID,
          client_secret: VITE_KAKAO_CLIENT_SECRET,
          redirect_uri: VITE_KAKAO_REDIRECT_URL,
          code: code.value
        },
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
          }
        }
      )
      .then(({ data }) => {
        console.log(data.access_token)
        axios
          .get('https://i10a706.p.ssafy.io/api/oauth/kakao/callback/' + data.access_token)
          // 발급된 코드를 갖고 신규/기존 회원 여부 판별하는 axios 호출
          .then(({ data }) => {
            // console.log('카카오 로그인 전')
            // console.log(data)
            console.log(data.data)
            if (data.data.accessToken === 'null') {
              // 회원가입
              msg
                .fire({
                  icon: 'warning',
                  title: '회원가입이 필요합니다!',
                  text: '아이 등록 코드 혹은 선생님 등록 코드를 입력해주세요'
                })
                .then((result) => {
                  if (result.isConfirmed) {
					console.log(result)
                    const input = {
                      userId: data.data.userId,
                      registCode: result.value
                    }
                    Join(input, 1)
                  } else if (result.isDismissed) {
                    location.href = '/'
                  }
                  // console.log('코드입력');
                  // console.log(input);
                })
              // alert('회원가입이 필요합니다!')
              setUserid(data.data.userId)
              // router.push({ path: '/join' })
            } else {
              // 로그인
              // console.log('로그인 드갈까?')
              KLogin(data)
              location.href = '/'
            }
            // 기존에 있는 회원 -> 바로 로그인
            // 신규 회원 -> 인증코드 입력 받기 -> 로그인
            //   route.push({name : "Home"});
          })
      })
      .catch((error) => {
        console.error(error)
      })
  }
})

const Join = (input, cnt) => {
console.log(input)
  console.log('cnt : ' + cnt)
  if (cnt >= 3) {
    userDelete(userId.value, ({ data }) => {
      console.log(data)
    })
    msg2
      .fire({
        icon: 'error',
        title: '입력 횟수를 초과하였습니다!',
        showConfirmButton: true,
        text: '회원가입을 다시 진행해주세요.'
      })
      .then(function () {
        location.href = '/'
      })
    return
  } else {
    axios
      .post('https://i10a706.p.ssafy.io/api/oauth/kakao/join', input)
      // 발급된 코드를 갖고 신규/기존 회원 여부 판별하는 axios 호출
      .then(({ data }) => {
        // console.log(data)
        KLogin(data)
      })
      .catch((error) => {
        console.log('잘못된 코드 입력 ' + error)
        const { value: code } = msg
          .fire({
            icon: 'error',
            title: '알맞은 코드를 입력해주세요!',
            input: 'text',
            inputPlaceholder: '등록 코드를 입력해주세요'
          })
          .then((result) => {
            if (result.isConfirmed) {
              const data = {
                userId: input.userId,
                registCode: result.value
              }
              Join(data, cnt+1)
            } else if (result.isDismissed) {
              location.href = '/'
            }
            // console.log('코드입력');
            // console.log(input);
          })
      })
  }
}

const KLogin = (input) => {
  if (input.data.isTeacher) {
    // 선생님 로그인
    setTeacherFlag(true)
    setAlarmFlag(input.data.alarm)
    localStorage.setItem('accessToken', input.data.accessToken)
    localStorage.setItem('refreshToken', input.data.refreshToken)
    setTeachername(input.data.teacherName)
    setClassroomname(input.data.classroomName)
    setProfile(input.data.teacherProfile)
  } else {
    // 학부모 로그인
    setTeacherFlag(false)
    setAlarmFlag(input.data.alarm)
    localStorage.setItem('accessToken', input.data.accessToken)
    localStorage.setItem('refreshToken', input.data.refreshToken)
    setKidlist(input.data.kidList) // 나머지 정보는 pinia 저장
    setCurkid(input.data.kidList[0].kidId)
  }
  setlogin()
  location.href = '/'
}
</script>

<style scoped></style>
