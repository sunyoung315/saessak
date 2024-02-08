<template>
  <div
    class="fixed w-1/3 top-0 right-0 flex items-center justify-between px-3 py-5 pb-4  mx-auto border-l-2 border-l-gray-300 bg-yellow-50"
  >
    <h5
      class="flex items-center justify-center text-xl font-bold leading-none  text-gray-900 dark:text-white"
    >
      
    {{ isTeacher == true ? '학부모 목록' : '선생님 목록' }}
    </h5>
  </div>
  <div class="flow-root grow">
    <div class="w-full max-w-sm mt-5 mx-auto bg-yellow-50 dark:bg-gray-800 dark:border-gray-700">
      <div class="flex flex-wrap pt-5 mt-auto mb-3 justify-evenly">
        <div
          v-for="person in Person"
          :key="person.id"
          class="flex flex-col items-center w-5/12 mb-3 border border-gray-300 rounded-lg shadow pb-10mb-4"
        >
          <img
            v-if="isTeacher == true"
            class="w-24 h-24 my-2 mb-3 rounded-full shadow-lg"
            :src="person.kidProfile"
            alt="Bonnie image"
          />
          <img
            v-if="isTeacher == false"
            class="w-24 h-24 my-2 mb-3 rounded-full shadow-lg"
            :src="person.profile"
            alt="Bonnie image"
          />
          <h5 class="text-xl font-medium text-gray-900 dark:text-white">
            {{ isTeacher == true ? person.kidName : person.teacherName }}
            {{ isTeacher == true ? '학부모' : '선생님' }}
          </h5>
          <span class="mb-0 text-sm text-gray-500 dark:text-gray-400">{{
            isTeacher == true ? '' : person.kidName
          }}</span>
          <div class="flex mt-3 mb-2">
            <button
              @click="
                addChat({
                  id: isTeacher == true ? person.kidId : person.teacherId,
                  name: isTeacher == true ? person.kidName : person.teacherName
                })
              "
              class="inline-flex items-center px-4 py-2 text-center text-white bg-lime-800 rounded-lg font -medium mt-0text-sm hover:bg-lime-500 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              TALK
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getClassKids, getMyTeacher } from '@/api/user'
import { teacherNewChat, parentNewChat } from '@/api/chat'
import { nextTick, ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { loginStore } from '@/store/loginStore'

const store = loginStore()
const { isLogin } = storeToRefs(store)
const props = defineProps({
  size: {
    type: Object,
    default: () => ({
      width: 0,
      height: 0
    })
  }
}) // drawer 사이즈 정보 상속
onMounted(() => {
  if (isLogin.value) {
    // console.log('getPersonList 실행')
    getPersonList()
  }
  nextTick(() => {
    // console.log('사이즈 받아왔니')
    // console.log(props.size)
  })
})
const userId = ref(3)
const classroomId = ref(1)
const { isTeacher } = storeToRefs(store)

const getPersonList = () => {
  // console.log('선생님이니?')
  // console.log(isTeacher.value)
  if (isTeacher.value) {
    // console.log('선생님 - 반 아이 조회')
    getClassKids(
      ({ data }) => {
        console.log(data.data)
        Person.value = data.data
      },
      (error) => {
        // console.log(error)
      }
    )
  } else {
    console.log('학부모 - 선생님 조회')
    getMyTeacher(
      ({ data }) => {
        console.log(data.data)
        Person.value = data.data
      },
      (error) => {
        // console.log(error)
      }
    )
  }
}

const Person = ref([])
const emit = defineEmits(['chatEvent']) // CHAT 버튼 눌렀을 때 채팅방으로 이동

const addChat = (input) => {
  // console.log('addChat 실행')
  // console.log(input)
  let tId = isTeacher.value == false ? userId.value : input[0] // 선생님 화면일때 : 학부모 화면일때
  let kId = isTeacher.value == false ? input : input[1]
  // console.log(tId + '/' + kId)
  if (isTeacher.value) {
    // console.log('선생님 드가자')
    teacherNewChat(
      input.id,
      ({ data }) => {
        // console.log(data)
        const roomInfo = {
          roomId: data.data,
          roomName: input.name,
          width: props.size.width,
          height: props.size.height
        }
        emit('chatEvent', roomInfo)
      },
      (error) => {
        // console.log(error)
      }
    )
  } else {
    // console.log('학부모 드가자')
    // console.log(input)
    parentNewChat(
      input.id,
      ({ data }) => {
        // console.log(data)
        const roomInfo = {
          roomId: data.data,
          roomName: input.name,
          width: props.size.width,
          height: props.size.height
        }
        emit('chatEvent', roomInfo)
      },
      (error) => {
        // console.log(error)
      }
    )
  }
}
</script>
<style></style>
