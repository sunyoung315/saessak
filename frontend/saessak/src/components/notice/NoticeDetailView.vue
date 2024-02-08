<template>
	<div class="view-frame">
		<div class="flex justify-end items-center">
			<button type="button" @click="goNoticeList()" class="btn mt-5 mr-7 mb-3">
				목록
			</button>
		</div>

		<div class="block mt-2 mb-5 w-full">
			<span class="content-title">제목</span>
			<input id="contents" class="content-box mb-10 p-4 text-lg" rows="6" :value="notice.noticeTitle"
				readonly></input>
		</div>

		<div class="block mt-2 mb-5 w-full">
			<span class="content-title">내용</span>
			<textarea id="contents" class="content-box mb-10 p-4 text-lg" rows="6" :value="notice.noticeContent"
				readonly></textarea>
		</div>

		<div class="block mt-2 mb-5 w-full">
			<span class="content-title">파일</span>
			<input id="contents" class="content-box mb-10 p-4 text-lg" rows="6" :value="notice.noticeFileName" readonly>
		</div>
	</div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { noticeDetail } from '@/api/notice'

const router = useRouter();

// 목록으로 router 이동
const goNoticeList = () => {
	router.push({ name: 'NoticeList' });
};

const route = useRoute();

// url 파리미터
const noticeId = ref(route.params.noticeId);
const notice = ref({})

onMounted(() => {
	noticeDetail(noticeId.value, ({ data }) => {
		notice.value = data.data
	});
});
</script>

<style scoped>
.content-title {
	@apply ml-36 text-gray-900 text-xl font-bold;
}

.content-box {
	@apply block ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
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
