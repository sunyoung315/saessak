<script setup>
import { onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import {
  noticeListParentAll,
  noticeListTeacherAll,
  parentFix,
  parentNotFix,
  teacherNotFix,
  teacherFix
} from '@/api/notice'
import { storeToRefs } from 'pinia'
import { loginStore } from '@/store/loginStore'

const router = useRouter()

// 부를 리스트 나누기
const store = loginStore()
const { isLogin, isTeacher, kidList, isAlarm, curKid, setCurkid } = storeToRefs(store)

//const kidId = loginStore.kidList[0].kidId;
// 내 아이 귀가동의서 목록 가져오기
const noticeList = ref([])
const paging = ref({
  pageNo: 1
})

onMounted(() => {
  fetchData()
})

function fetchData() {
  if (isLogin) {
    if (isTeacher.value) {
      noticeListTeacherAll(paging.value.pageNo - 1, ({ data }) => {
        noticeList.value = data.data
      })
    } else {
      noticeListParentAll(kidList.value[0].kidId, paging.value.pageNo - 1, ({ data }) => {
        noticeList.value = data.data
      })
    }
  }
}

// 동의서 버튼 동작

function moveNoticeDetail(noticeId) {
  router.push({
    name: 'NoticeDetail',
    params: { noticeId }
  })
}

watch(noticeList, async () => {
  await fetchData()
})

watch(paging, async () => {
  await fetchData()
})

function startFix(notice) {
  if (!isTeacher.value) {
    if (!notice.noticeFlag) {
      parentFix({
        noticeId: notice.noticeId,
        kidId: kidList.value[0].kidId
      })
    } else {
      parentNotFix({
        noticeId: notice.noticeId,
        kidId: kidList.value[0].kidId
      })
    }
  } else {
    if (!notice.noticeFlag) {
      teacherFix(notice.noticeId)
    } else {
      teacherNotFix(notice.noticeId)
    }
  }
}
</script>

<style scoped></style>

<template>
  <div class="view-frame p-4">
    <div class="container p-6 flex items-center">
      <table class="items-center bg-transparent border-collapse mt-4 w-full text-center">
        <thead>
          <tr class="bg-nav-orange">
            <th
              class="px-48 border border-solid border-blueGray-100 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
            >
              제목
            </th>
            <th
              class="px-2 border border-solid border-blueGray-100 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
            >
              작성자
            </th>
            <th
              class="px-1 border border-solid border-blueGray-100 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
            >
              첨부파일
            </th>
            <th
              class="px-1 border border-solid border-blueGray-100 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
            >
              작성일자
            </th>
            <th
              class="px-1 border border-solid border-blueGray-100 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
            ></th>
          </tr>
        </thead>
        <tbody>
          <!-- click="moveReplacement(kid.replacementId)" -->
          <tr
            v-for="notice in noticeList.list"
            :key="notice.noticeId"
            class="hover:bg-nav-orange hover:bg-opacity-20"
          >
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
              @click="moveNoticeDetail(notice.noticeId)"
            >
              {{ notice.noticeTitle }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
              @click="moveNoticeDetail(notice.noticeId)"
            >
              {{ notice.teacherName }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
              @click="moveNoticeDetail(notice.noticeId)"
            >
              <div v-if="notice.fileFlag" class="flex justify-center items-center">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  fill="none"
                >
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M15 2H15.306L20 6.69398V7V20C20 21.1046 19.1046 22 18 22H6C4.89543 22 4 21.1046 4 20V4C4 2.89543 4.89543 2 6 2H15ZM13 4H6V20H18V9H13V4ZM17.693 7L15 4.306V7H17.693Z"
                    fill="black"
                  />
                </svg>
              </div>
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
              @click="moveNoticeDetail(notice.noticeId)"
            >
              {{ notice.noticeTime }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
              @click="startFix(notice)"
            >
              <div v-if="notice.noticeFlag" class="flex justify-center items-center">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="18"
                  height="17"
                  viewBox="0 0 18 17"
                  fill="none"
                >
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M9.39301 0.0802627C9.62625 0.179445 9.81219 0.363042 9.91263 0.593353L11.9554 5.27719L17.098 5.74815C17.6445 5.79819 18.0464 6.27618 17.9957 6.81576C17.9722 7.06545 17.8529 7.29679 17.6621 7.46229L13.782 10.828L14.9175 15.8029C15.0382 16.3316 14.702 16.8567 14.1666 16.9758C13.9189 17.031 13.6591 16.9904 13.4408 16.8623L8.99997 14.2586L4.55917 16.8623C4.08728 17.139 3.4776 16.9856 3.19741 16.5196C3.06775 16.304 3.02662 16.0476 3.08245 15.8029L4.21798 10.828L0.337881 7.46229C-0.0744204 7.10465 -0.115035 6.48469 0.247167 6.07757C0.414773 5.88918 0.649067 5.77131 0.901936 5.74815L6.04454 5.27719L8.08731 0.593353C8.30438 0.0956463 8.88896 -0.134072 9.39301 0.0802627Z"
                    fill="#FFD700"
                  />
                </svg>
              </div>
              <div v-else class="flex justify-center items-center">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="18"
                  height="17"
                  viewBox="0 0 18 17"
                  fill="none"
                >
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M9.39301 0.0802627C9.62625 0.179445 9.81219 0.363042 9.91263 0.593353L11.9554 5.27719L17.098 5.74815C17.6445 5.79819 18.0464 6.27618 17.9957 6.81576C17.9722 7.06545 17.8529 7.29679 17.6621 7.46229L13.782 10.828L14.9175 15.8029C15.0382 16.3316 14.702 16.8567 14.1666 16.9758C13.9189 17.031 13.6591 16.9904 13.4408 16.8623L8.99997 14.2586L4.55917 16.8623C4.08728 17.139 3.4776 16.9856 3.19741 16.5196C3.06775 16.304 3.02662 16.0476 3.08245 15.8029L4.21798 10.828L0.337881 7.46229C-0.0744204 7.10465 -0.115035 6.48469 0.247167 6.07757C0.414773 5.88918 0.649067 5.77131 0.901936 5.74815L6.04454 5.27719L8.08731 0.593353C8.30438 0.0956463 8.88896 -0.134072 9.39301 0.0802627Z"
                    fill="#A1A7C4"
                  />
                </svg>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="flex justify-center text-2xl font-bold">
      <button
        :disabled="paging.pageNo === 1"
        :class="{ 'text-gray-200': paging.pageNo === 1 }"
        @click="paging.pageNo = paging.pageNo - 1"
      >
        ←
      </button>

      <div v-for="index in noticeList.length">
        <button
          :key="index"
          class="m-2 rounded-lg p-2"
          :class="{ 'bg-nav-orange': paging.pageNo === index }"
          @click="paging.pageNo = index"
        >
          {{ index }}
        </button>
      </div>
      <button
        :disabled="paging.pageNo === noticeList.length"
        :class="{ 'text-gray-200': paging.pageNo === noticeList.length }"
        @click="paging.pageNo = paging.pageNo + 1"
      >
        →
      </button>
    </div>
  </div>
</template>
