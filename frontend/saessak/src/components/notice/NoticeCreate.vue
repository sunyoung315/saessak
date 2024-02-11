<template>
  <div class="view-frame p-4">
    <div class="flex justify-end items-center">
      <button type="button" @click="registNotice()" class="btn">등록</button>
      <button type="button" @click="goBack()" class="btn">목록</button>
    </div>

    <div class="block mt-16 mb-5 w-full">
      <span class="content-title">제목</span>
      <input
        id="title"
        class="content-box mb-10 p-4 text-lg"
        rows="6"
        v-model="notice.noticeTitle"
      />
    </div>

    <div class="block mt-2 mb-5 w-full">
      <span class="content-title">내용</span>
      <textarea
        id="content"
        class="content-box mb-10 p-4 text-lg"
        rows="6"
        v-model="notice.noticeContent"
      ></textarea>
    </div>

    <div class="block mt-2 mb-5 w-full relative">
      <span class="content-title">파일</span>
      <div class="content-box-flex w-full mb-10 p-4 text-lg flex justify-between items-center">
        <div class="flex items-center ml-2">
          <!-- SVG 아이콘 클릭 시 openFileDialog 함수 호출 -->
          <svg
            @click="openFileDialog"
            xmlns="http://www.w3.org/2000/svg"
            width="17"
            height="18"
            viewBox="0 0 17 18"
            fill="none"
          >
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M15.7916 11.5833C16.3258 11.5833 16.7661 11.9855 16.8263 12.5035L16.8333 12.625V16.7917C16.8333 17.3259 16.4312 17.7662 15.9131 17.8263L15.7916 17.8333H1.20829C0.674089 17.8333 0.233806 17.4312 0.173634 16.9131L0.166626 16.7917V12.625C0.166626 12.0497 0.632996 11.5833 1.20829 11.5833C1.7425 11.5833 2.18278 11.9855 2.24295 12.5035L2.24996 12.625V15.75H14.75V12.625C14.75 12.0908 15.1521 11.6505 15.6701 11.5903L15.7916 11.5833ZM8.49996 0.125C9.07526 0.125 9.54163 0.59137 9.54163 1.16667L9.54267 10.1083L12.9717 6.6801C13.3472 6.30459 13.9381 6.27571 14.3467 6.59344L14.4449 6.6801C14.8204 7.0556 14.8493 7.64647 14.5315 8.0551L14.4449 8.15324L9.23653 13.3616C8.86102 13.7371 8.27016 13.766 7.86152 13.4482L7.76339 13.3616L2.55506 8.15324C2.14826 7.74644 2.14826 7.08689 2.55506 6.6801C2.93056 6.30459 3.52143 6.27571 3.93006 6.59344L4.0282 6.6801L7.45933 10.1104L7.45829 1.16667C7.45829 0.59137 7.92466 0.125 8.49996 0.125Z"
              fill="black"
            />
          </svg>

          <span :class="{ 'text-gray-500': !fileName, 'ml-4': true }">{{
            fileName || '첨부파일 없음'
          }}</span>
          <input type="file" @change="handleFileUpload" class="hidden" ref="fileInput" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { noticeDetail } from '@/api/notice'

const router = useRouter()
const route = useRoute()
const noticeId = ref(route.params.noticeId)
const notice = ref({})
const fileName = ref('')
const fileInput = ref(null)

onMounted(() => {})

// 목록으로 이동하는 함수
const goBack = () => {
  router.push({ name: 'NoticeList' })
}

// 파일 업로드 처리 함수
const handleFileUpload = (event) => {
  if (event.target.files.length > 0) {
    fileName.value = event.target.files[0].name
    notice.value.noticeFile = event.target.files[0]
  }
}

// 공지사항 제출 함수
const registNotice = async () => {
  console.log(notice.value.noticeTitle)
  console.log(notice.value.noticeContent)
  console.log(notice.value.noticeFile)

  const formData = new FormData()
  formData.append('title', notice.value.noticeTitle)
  formData.append('content', notice.value.noticeContent)
  if (notice.value.noticeFile) {
    formData.append('noticeFile', notice.value.noticeFile)
  }

  try {
    await axios.post('https://i10a706.p.ssafy.io/api/notice/', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: 'Bearer ' + localStorage.getItem('accessToken')
      }
    })
    router.push({ name: 'NoticeList' })
  } catch (error) {
    console.error(error)
  }
}

const openFileDialog = () => {
  fileInput.value.click()
}
</script>

<style scoped>
.content-title {
  @apply ml-36 text-gray-900 text-xl font-bold;
}

.content-box {
  @apply block ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
}

.content-box-flex {
  @apply flex ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
}

.record-title {
  @apply inline-block m-5 text-gray-700 text-base font-extrabold;
}

.record-flex {
  @apply flex items-center;
}

.record-content {
  @apply block w-20 h-11 py-2.5 bg-gray-100 rounded-md border border-neutral-300 text-center text-gray-900 text-base;
}

.unit {
  @apply pl-3 pr-6;
}

.no-content {
  @apply mx-36 mt-8 text-lg;
}

.group-button {
  @apply inline-flex h-11 rounded-md shadow-sm;
}

.group-button-left-item {
  @apply h-11 px-6 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-s-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}

.group-button-left-item-focus {
  @apply h-11 px-6 py-2 text-base border border-gray-200 rounded-s-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}

.group-button-center-item {
  @apply h-11 px-6 py-2 text-base font-medium text-gray-900 bg-white border-t border-b border-gray-200 focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}

.group-button-center-item-focus {
  @apply h-11 px-6 py-2 text-base border-t border-b border-gray-200 z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}

.group-button-right-item {
  @apply h-11 px-6 py-2 text-base font-medium text-gray-900 bg-white border border-gray-200 rounded-e-lg focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy focus:font-bold focus:bg-gray-100;
}

.group-button-right-item-focus {
  @apply h-11 px-6 py-2 text-base border border-gray-200 rounded-e-lg z-10 ring-2 ring-dark-navy text-dark-navy font-bold bg-gray-100;
}
</style>
