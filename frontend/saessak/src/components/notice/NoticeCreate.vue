<template>
	<div class="view-frame p-1.5">
		<div class="flex justify-end items-center mb-10">
			<button type="button" @click="registNotice()" class="btn mt-7 mr-4 mb-3">
				등록
			</button>
			<button type="button" @click="goBack()" class="btn mt-7 mr-7 mb-3">
				목록
			</button>
		</div>

		<div class="block mt-16 mb-5">
			<span class="content-title">제목</span>
			<input
				id="title"
				class="content-box mb-10 p-4 text-lg"
				rows="6"
				v-model="notice.noticeTitle"
				:class="{
					'!border-2 !border-red-500': emptyNoticeTitle,
					shake: shakeNoticeTitle,
				}"
			/>
		</div>

		<div class="block mt-2 mb-5 w-full">
			<span class="content-title">내용</span>
			<textarea
				id="content"
				class="content-box mb-10 p-4 text-lg"
				rows="6"
				v-model="notice.noticeContent"
				:class="{
					'!border-2 !border-red-500': emptyNoticeContent,
					shake: shakeNoticeContent,
				}"
			></textarea>
		</div>

		<div class="block mt-2 mb-5 w-full relative">
			<span class="content-title">파일</span>
			<div
				@click="openFileDialog"
				class="content-box mb-10 p-4 text-lg cursor-pointer"
			>
				<div class="flex items-center ml-2">
					<svg
						width="17"
						height="18"
						viewBox="0 0 17 18"
						fill="none"
						xmlns="http://www.w3.org/2000/svg"
					>
						<path
							fill-rule="evenodd"
							clip-rule="evenodd"
							d="M15.7917 11.5833C16.326 11.5833 16.7662 11.9855 16.8264 12.5035L16.8334 12.625V16.7917C16.8334 17.3259 16.4313 17.7662 15.9132 17.8263L15.7917 17.8333H1.20841C0.674211 17.8333 0.233928 17.4312 0.173756 16.9131L0.166748 16.7917V12.625C0.166748 12.0497 0.633118 11.5833 1.20841 11.5833C1.74262 11.5833 2.1829 11.9855 2.24307 12.5035L2.25008 12.625V15.75H14.7501V12.625C14.7501 12.0908 15.1522 11.6505 15.6703 11.5903L15.7917 11.5833ZM8.50008 13.6667C9.07538 13.6667 9.54175 13.2003 9.54175 12.625L9.54279 3.68333L12.9718 7.11157C13.3473 7.48707 13.9382 7.51596 14.3469 7.19822L14.445 7.11157C14.8205 6.73606 14.8494 6.1452 14.5316 5.73656L14.445 5.63843L9.23665 0.430097C8.86115 0.054593 8.27028 0.025708 7.86164 0.343442L7.76351 0.430097L2.55518 5.63843C2.14838 6.04523 2.14838 6.70477 2.55518 7.11157C2.93068 7.48707 3.52155 7.51596 3.93019 7.19822L4.02832 7.11157L7.45946 3.68125L7.45841 12.625C7.45841 13.2003 7.92478 13.6667 8.50008 13.6667Z"
							fill="black"
						/>
					</svg>

					<span :class="{ 'text-gray-500': !fileName, 'ml-4': true }">{{
						fileName || '첨부파일 없음'
					}}</span>
					<input
						type="file"
						@change="handleFileUpload"
						class="hidden"
						ref="fileInput"
					/>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
const fileName = ref('');
const fileInput = ref(null);

onMounted(() => {});

const notice = ref({
	noticeTitle: '',
	noticeContent: '',
	noticeFile: null,
});

// 목록으로 이동하는 함수
const goBack = () => {
	router.push({ name: 'NoticeList' });
};

// 파일 업로드 처리 함수
const handleFileUpload = event => {
	if (event.target.files.length > 0) {
		fileName.value = event.target.files[0].name;
		notice.value.noticeFile = event.target.files[0];
	}
};

// 공지사항 제출 함수
const registNotice = async () => {
	if (checkEmptyFields()) {
		return;
	}
	const formData = new FormData();
	formData.append('title', notice.value.noticeTitle);
	formData.append('content', notice.value.noticeContent);
	if (notice.value.noticeFile) {
		formData.append('noticeFile', notice.value.noticeFile);
	}

	try {
		await axios.post('https://i10a706.p.ssafy.io/api/notice/', formData, {
			headers: {
				'Content-Type': 'multipart/form-data',
				Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
			},
		});
		router.push({ name: 'NoticeList' });
	} catch (error) {
		//console.error(error)
	}
};

const openFileDialog = () => {
	fileInput.value.click();
};

// 널값처리
const emptyNoticeTitle = ref(false);
const shakeNoticeTitle = ref(false);

const emptyNoticeContent = ref(false);
const shakeNoticeContent = ref(false);

const checkEmptyFields = () => {
	let hasEmptyFields = false;
	if (!notice.value.noticeTitle.trim()) {
		emptyNoticeTitle.value = true;
		shakeNoticeTitle.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeNoticeTitle.value = false;
		}, 1000);
	}
	if (!notice.value.noticeContent.trim()) {
		emptyNoticeContent.value = true;
		shakeNoticeContent.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeNoticeContent.value = false;
		}, 1000);
	}
	return hasEmptyFields;
};
</script>

<style scoped>
.content-title {
	@apply w-[70%] mx-[15%] px-2 text-gray-900 text-xl font-bold;
}

.content-box {
	@apply block  w-[70%] mx-[15%] px-2 mt-1 rounded-md border border-neutral-300 shadow;
}

.content-box-flex {
	@apply flex ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
}

@keyframes shake {
	0% {
		transform: translateX(0px);
	}
	25% {
		transform: translateX(-2px);
	}
	50% {
		transform: translateX(0px);
	}
	75% {
		transform: translateX(2px);
	}
	100% {
		transform: translateX(0px);
	}
}
.shake {
	animation: shake 0.2s;
	animation-iteration-count: 3;
}
</style>
