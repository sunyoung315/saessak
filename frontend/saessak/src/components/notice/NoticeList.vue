<script setup>
import { onMounted, ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';

import { noticeListAll } from '@/api/notice'


const router = useRouter();

// 아이 id
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
const kidId = loginStore.kidList[0].kidId;

// 내 아이 귀가동의서 목록 가져오기
const noticeList = ref([]);
const paging = ref({
	pageNo: 0
})
onMounted(() => {
	noticeListAll(kidId, {
		params: paging.value
	}, ({ data }) => {
		noticeList.value = data.data
	});
})


// 동의서 버튼 동작

function moveNoticeDetail() {
	router.push({
		name: 'NoticeDetail',
		params: { noticeId },
	});
}
// 버튼 끝
</script>

<style scoped></style>


<template>
	<div class="view-frame p-4">
		<div class="container p-6 flex items-center">
			<table class="items-center bg-transparent border-collapse mt-4 w-full text-center">
				<thead>
					<tr class="bg-nav-orange">
						<th
							class="px-48 border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold">
							제목
						</th>
						<th
							class="px-2 border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold">
							작성자
						</th>
						<th
							class="px-1 border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold">
							첨부파일
						</th>
						<th
							class="px-1 border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold">
							작성일자
						</th>
						<th
							class="px-1 border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold">
							고정
						</th>
					</tr>
				</thead>
				<tbody>
					<!-- click="moveReplacement(kid.replacementId)" -->
					<tr v-for="notice in noticeList" :key="notice.noticeId" class="hover:bg-nav-orange">
						<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
							{{ notice.noticeTitle }}
						</td>
						<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
							선생님1
						</td>
						<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
							<div v-if="notice.fileFlag" class="flex justify-center items-center">
								<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
									fill="none">
									<path fill-rule="evenodd" clip-rule="evenodd"
										d="M15 2H15.306L20 6.69398V7V20C20 21.1046 19.1046 22 18 22H6C4.89543 22 4 21.1046 4 20V4C4 2.89543 4.89543 2 6 2H15ZM13 4H6V20H18V9H13V4ZM17.693 7L15 4.306V7H17.693Z"
										fill="black" />
								</svg>
							</div>
						</td>
						<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
							{{ notice.noticeTime }}
						</td>
						<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
							<div v-if="notice.noticeFlag" class="flex justify-center items-center">
								<svg xmlns="http://www.w3.org/2000/svg" width="18" height="17" viewBox="0 0 18 17"
									fill="none">
									<path fill-rule="evenodd" clip-rule="evenodd"
										d="M9.39301 0.0802627C9.62625 0.179445 9.81219 0.363042 9.91263 0.593353L11.9554 5.27719L17.098 5.74815C17.6445 5.79819 18.0464 6.27618 17.9957 6.81576C17.9722 7.06545 17.8529 7.29679 17.6621 7.46229L13.782 10.828L14.9175 15.8029C15.0382 16.3316 14.702 16.8567 14.1666 16.9758C13.9189 17.031 13.6591 16.9904 13.4408 16.8623L8.99997 14.2586L4.55917 16.8623C4.08728 17.139 3.4776 16.9856 3.19741 16.5196C3.06775 16.304 3.02662 16.0476 3.08245 15.8029L4.21798 10.828L0.337881 7.46229C-0.0744204 7.10465 -0.115035 6.48469 0.247167 6.07757C0.414773 5.88918 0.649067 5.77131 0.901936 5.74815L6.04454 5.27719L8.08731 0.593353C8.30438 0.0956463 8.88896 -0.134072 9.39301 0.0802627Z"
										fill="#FFD700" />
								</svg>
							</div>
							<div v-else class="flex justify-center items-center">
								<svg xmlns="http://www.w3.org/2000/svg" width="18" height="17" viewBox="0 0 18 17"
									fill="none">
									<path fill-rule="evenodd" clip-rule="evenodd"
										d="M9.39301 0.0802627C9.62625 0.179445 9.81219 0.363042 9.91263 0.593353L11.9554 5.27719L17.098 5.74815C17.6445 5.79819 18.0464 6.27618 17.9957 6.81576C17.9722 7.06545 17.8529 7.29679 17.6621 7.46229L13.782 10.828L14.9175 15.8029C15.0382 16.3316 14.702 16.8567 14.1666 16.9758C13.9189 17.031 13.6591 16.9904 13.4408 16.8623L8.99997 14.2586L4.55917 16.8623C4.08728 17.139 3.4776 16.9856 3.19741 16.5196C3.06775 16.304 3.02662 16.0476 3.08245 15.8029L4.21798 10.828L0.337881 7.46229C-0.0744204 7.10465 -0.115035 6.48469 0.247167 6.07757C0.414773 5.88918 0.649067 5.77131 0.901936 5.74815L6.04454 5.27719L8.08731 0.593353C8.30438 0.0956463 8.88896 -0.134072 9.39301 0.0802627Z"
										fill="#A1A7C4" />
								</svg>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

