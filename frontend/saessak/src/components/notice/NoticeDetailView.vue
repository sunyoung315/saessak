<template>
	<div class="view-frame">
		<div class="flex justify-end items-center">
			<button type="button" @click="goNoticeList()" class="btn mt-5 mr-7 mb-3">
				목록
			</button>
		</div>

		<div class="flex justify-center mb-2">
			<div class="flex justify-between items-end mt-8 mb-4 w-[78%]">
				<span class="content-title2">{{ notice.noticeTitle }}</span>
				<div class="text-lg">
					{{ notice.noticeTime }}
				</div>
			</div>
		</div>

		<div class="flex justify-center">
			<hr class="w-[80%] border-1 border-gray-400" />
		</div>

		<div class="block mt-2 w-full">
			<div
				class="content-box w-[78%] mb-20 pt-8 mx-1 text-lg whitespace-pre-line leading-9"
			>
				{{ notice.noticeContent }}
			</div>
		</div>

		<template v-if="notice.noticeFile != null">
			<span class="content-title">파일</span>
			<button @click="download" class="block mt-1 mb-5 w-full relative">
				<div
					class="content-box-flex w-full mb-20 p-4 text-lg flex justify-between items-center hover:bg-gray-100"
				>
					<div class="flex justify-start items-center">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="20"
							height="18"
							viewBox="0 0 20 18"
							fill="none"
							class="transform rotate-90 inline-block mr-5"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M14.5 0C17.5376 0 20 2.46243 20 5.5C20 8.46348 17.6562 10.8795 14.7212 10.9956L14.5 11H3C2.44772 11 2 10.5523 2 10C2 9.48716 2.38604 9.06449 2.88338 9.00673L3 9H14.5C16.433 9 18 7.433 18 5.5C18 3.63144 16.5357 2.10487 14.692 2.00518L14.5 2H4C2.89543 2 2 2.89543 2 4C2 5.05436 2.81588 5.91817 3.85074 5.99451L4 6H13.5C13.7761 6 14 5.77614 14 5.5C14 5.25454 13.8231 5.05039 13.5899 5.00806L13.5 5H6C5.44772 5 5 4.55228 5 4C5 3.48716 5.38604 3.06449 5.88338 3.00673L6 3H13.5C14.8807 3 16 4.11929 16 5.5C16 6.82548 14.9685 7.91004 13.6644 7.99468L13.5 8H4C1.79086 8 0 6.20914 0 4C0 1.8578 1.68397 0.108921 3.80036 0.00489531L4 0H14.5Z"
								fill="#000000"
							/>
						</svg>
						<span>{{ notice.noticeFileName }}</span>
					</div>
					<svg
						xmlns="http://www.w3.org/2000/svg"
						width="17"
						height="18"
						viewBox="0 0 17 18"
						fill="none"
						class="mr-2"
					>
						<path
							fill-rule="evenodd"
							clip-rule="evenodd"
							d="M15.7916 11.5833C16.3258 11.5833 16.7661 11.9855 16.8263 12.5035L16.8333 12.625V16.7917C16.8333 17.3259 16.4312 17.7662 15.9131 17.8263L15.7916 17.8333H1.20829C0.674089 17.8333 0.233806 17.4312 0.173634 16.9131L0.166626 16.7917V12.625C0.166626 12.0497 0.632996 11.5833 1.20829 11.5833C1.7425 11.5833 2.18278 11.9855 2.24295 12.5035L2.24996 12.625V15.75H14.75V12.625C14.75 12.0908 15.1521 11.6505 15.6701 11.5903L15.7916 11.5833ZM8.49996 0.125C9.07526 0.125 9.54163 0.59137 9.54163 1.16667L9.54267 10.1083L12.9717 6.6801C13.3472 6.30459 13.9381 6.27571 14.3467 6.59344L14.4449 6.6801C14.8204 7.0556 14.8493 7.64647 14.5315 8.0551L14.4449 8.15324L9.23653 13.3616C8.86102 13.7371 8.27016 13.766 7.86152 13.4482L7.76339 13.3616L2.55506 8.15324C2.14826 7.74644 2.14826 7.08689 2.55506 6.6801C2.93056 6.30459 3.52143 6.27571 3.93006 6.59344L4.0282 6.6801L7.45933 10.1104L7.45829 1.16667C7.45829 0.59137 7.92466 0.125 8.49996 0.125Z"
							fill="black"
						/>
					</svg>
				</div>
			</button>
		</template>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { noticeDetail } from '@/api/notice';
import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3';

const {
	VITE_AWS_BUCKET_NAME,
	VITE_AWS_REGION,
	VITE_AWS_ACCESS_KEY,
	VITE_AWS_SECRET_KEY,
} = import.meta.env;

const router = useRouter();

// 목록으로 router 이동
const goNoticeList = () => {
	router.push({ name: 'NoticeList' });
};

const route = useRoute();

// url 파리미터
const noticeId = ref(route.params.noticeId);
const notice = ref({});

onMounted(() => {
	noticeDetail(noticeId.value, ({ data }) => {
		notice.value = data.data;
	});
});

const openFileDialog = () => {
	fileInput.value.click();
};
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
		const type = ContentType.split('/')[1];
		const blob = new Blob([byteArray], { type: ContentType });

		const downloadLink = document.createElement('a');
		downloadLink.href = URL.createObjectURL(blob);
		downloadLink.download = notice.value.noticeFileName.normalize('NFC');
		downloadLink.click();
		downloadLink.remove();
		URL.revokeObjectURL(downloadLink.href);
	} catch (error) {
		//console.log(error)
	}
};
</script>

<style scoped>
.content-title2 {
	@apply text-black text-4xl font-bold;
}

.content-title {
	@apply ml-36 text-gray-900 text-xl font-bold;
}

.content-box {
	@apply block ml-32 mt-1 rounded-md;
}

.content-box-flex {
	@apply flex ml-32 mt-1 w-9/12 rounded-md border border-neutral-300 shadow;
}
</style>
