<template>
	<div class="view-frame">
		<div class="flex justify-end items-center">
			<button type="button" @click="goNoticeList()" class="btn mt-5 mr-7 mb-3">
				목록
			</button>
		</div>

		<div class="block mt-2 mb-5 w-full">
			<span class="content-title">제목</span>
			<input id="contents" class="content-box mb-10 p-4 text-lg" rows="6" :value="notice.noticeTitle" readonly />
		</div>

		<div class="block mt-2 mb-5 w-full">
			<span class="content-title">내용</span>
			<textarea id="contents" class="content-box mb-10 p-4 text-lg" rows="6" :value="notice.noticeContent"
				readonly></textarea>
		</div>

		<div class="block mt-2 mb-5 w-full relative ">
			<span class="content-title ">파일</span>
			<div class="content-box-flex w-full mb-10 p-4 text-lg flex justify-between items-center">
				<div class="flex">
					<svg xmlns="http://www.w3.org/2000/svg" width="20" height="18" viewBox="0 0 20 18" fill="none"
						class="transform rotate-90 inline-block mr-5">
						<path fill-rule="evenodd" clip-rule="evenodd"
							d="M14.5 0C17.5376 0 20 2.46243 20 5.5C20 8.46348 17.6562 10.8795 14.7212 10.9956L14.5 11H3C2.44772 11 2 10.5523 2 10C2 9.48716 2.38604 9.06449 2.88338 9.00673L3 9H14.5C16.433 9 18 7.433 18 5.5C18 3.63144 16.5357 2.10487 14.692 2.00518L14.5 2H4C2.89543 2 2 2.89543 2 4C2 5.05436 2.81588 5.91817 3.85074 5.99451L4 6H13.5C13.7761 6 14 5.77614 14 5.5C14 5.25454 13.8231 5.05039 13.5899 5.00806L13.5 5H6C5.44772 5 5 4.55228 5 4C5 3.48716 5.38604 3.06449 5.88338 3.00673L6 3H13.5C14.8807 3 16 4.11929 16 5.5C16 6.82548 14.9685 7.91004 13.6644 7.99468L13.5 8H4C1.79086 8 0 6.20914 0 4C0 1.8578 1.68397 0.108921 3.80036 0.00489531L4 0H14.5Z"
							fill="#000000" />
					</svg>
					<span>{{ notice.noticeFileName }}</span>
				</div>

				<button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded ml-auto"
					@click="download()">
					버튼

				</button>
			</div>
		</div>


	</div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { noticeDetail } from '@/api/notice'
import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3';

const {
	VITE_AWS_BUCKET_NAME,
	VITE_AWS_REGION,
	VITE_AWS_ACCESS_KEY,
	VITE_AWS_SECRET_KEY,
} = import.meta.env

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
		console.log(data.data)
		notice.value = data.data
	});

});

const bucketName = VITE_AWS_BUCKET_NAME;
const removeUrl = `https://${bucketName}.s3.${VITE_AWS_REGION}.amazonaws.com/`;

const s3Client = new S3Client({
	region: VITE_AWS_REGION, // AWS 리전 (예: us-east-1)
	credentials: {
		accessKeyId: VITE_AWS_ACCESS_KEY,
		secretAccessKey: VITE_AWS_SECRET_KEY,
	},
});

// File Download 시작
const download = async () => {
	const fileKey = notice.value.noticeFile.replace(removeUrl, '');
	try {
		// S3 객체 다운로드 명령 생성
		const getObjectCommand = new GetObjectCommand({
			Bucket: bucketName,
			Key: fileKey,
		});

		// S3 객체 다운로드
		const { Body, ContentType } = await s3Client.send(getObjectCommand);
		const byteArray = await Body.transformToByteArray();
		console.log(ContentType);
		const type = ContentType.split('/')[1];
		const blob = new Blob([byteArray], { type: ContentType });

		const downloadLink = document.createElement('a');
		downloadLink.href = URL.createObjectURL(blob);
		downloadLink.download = notice.value.noticeFileName;
		downloadLink.click();
		downloadLink.remove();
		URL.revokeObjectURL(downloadLink.href);
	} catch (error) {
		console.log(error)
	}
};



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
