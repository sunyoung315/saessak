<template>
  <div
    class="fixed w-1/3 top-0 right-0 flex items-center justify-between px-3 py-5 pb-4 mx-auto bg-yellow-50"
  >
    <h5
      class="flex items-center justify-center text-xl font-bold leading-none text-gray-900 dark:text-white"
    >
      학부모 목록
    </h5>
  </div>
  <div class="flow-root grow">
    <div class="w-full max-w-sm mt-3 mx-auto bg-white dark:bg-gray-800 dark:border-gray-700">
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
            {{ userFlag == false ? person.kidName : person.teacherName}} {{ userFlag == false ? "학부모" : "선생님" }}
          </h5>
          <span class="mb-0 text-sm text-gray-500 dark:text-gray-400">{{ userFlag == false ? "" : person.kidName }}</span>
          <div class="flex mt-3 mb-2">
            <button
             @click="addChat(userFlag == false ? person.kidId : [person.teacherId, person.kidId])"
              class="inline-flex items-center px-4 py-2 text-center text-white bg-lime-500 rounded-lg font -medium mt-0text-sm hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
              >TALK</button
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getClassKids, getMyTeacher } from '@/api/user'
import { addNewChat } from '@/api/chat'
import { ref, onMounted } from 'vue'

onMounted(() => {
  getPersonList()
})

const userId = ref(3);
const classroomId = ref(1);
const userFlag = ref(false)

const getPersonList = () => {
  if (!userFlag.value) {
    console.log('선생님 - 반 아이 조회')
    getClassKids(
      classroomId.value,
      ({ data }) => {
        console.log(data.data)
        Person.value = data.data
      },
      (error) => {
        console.log(error)
      }
    )
  } else {
    console.log('학부모 - 선생님 조회');
    getMyTeacher(
      userId.value,
      ({data}) => {
        console.log(data.data);
        Person.value = data.data;
      },
      (error) => {
        console.log(error);
      }
    )
  }
}

const Person = ref([]);
const emit = defineEmits(["chatEvent"]); // CHAT 버튼 눌렀을 때 채팅방으로 이동


const addChat = (input) => {
  console.log("addChat 실행");
  console.log(input);
  let tId = userFlag.value == false ? userId.value : input[0] ; // 선생님 화면일때 : 학부모 화면일때
  let kId = userFlag.value == false ? input : input[1]; 
  console.log(tId + "/" + kId);
  addNewChat(
    tId, kId, ({data}) => {
      console.log(data.data);
      const roomInfo = {roomId : data.data, senderId : userFlag.value == false ? tId : kId, receiverId : userFlag.value == false ? kId : tId};
      emit("chatEvent", roomInfo);
    },
    (error) => {
      console.log(error);
    }
  )
}
</script>
<style></style>
