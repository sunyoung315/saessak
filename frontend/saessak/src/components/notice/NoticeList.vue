<script setup>
import { onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import {
	noticeListParentAll,
	noticeListTeacherAll,
	parentFix,
	parentNotFix,
	teacherNotFix,
	teacherFix,
} from '@/api/notice';
import { storeToRefs } from 'pinia';
import { loginStore } from '@/store/loginStore';
import Swal from 'sweetalert2';

const router = useRouter();

// 부를 리스트 나누기
const store = loginStore();
const { isLogin, isTeacher, kidList, isAlarm, curKid, setCurkid } =
	storeToRefs(store);

//const kidId = loginStore.kidList[0].kidId;
// 내 아이 귀가동의서 목록 가져오기
const noticeList = ref({
	list: [],
	length: 0,
});
const paging = ref({
	pageNo: 1,
});

onMounted(async () => {
	await fetchData();
});

const fetchData = async () => {
	if (isLogin) {
		if (isTeacher.value) {
			await noticeListTeacherAll(paging.value.pageNo - 1, ({ data }) => {
				noticeList.value = data.data;
			});
		} else {
			await noticeListParentAll(
				kidList.value[0].kidId,
				paging.value.pageNo - 1,
				({ data }) => {
					noticeList.value = data.data;
				},
			);
		}
	}
};

// 동의서 버튼 동작

function moveNoticeDetail(noticeId) {
	router.push({
		name: 'NoticeDetail',
		params: { noticeId },
	});
}

const startFix = async notice => {
	if (isTeacher.value) {
		if (!notice.noticeFlag) {
			await teacherFix(
				notice.noticeId,
				({ response }) => {},
				({ error }) => {
					Swal.fire({
						icon: 'warning',
						title: '공지사항은 최대 다섯 개까지 고정 할 수 있습니다!',
						confirmButtonText: '확인',
					});
					// alert('공지사항은 최대 다섯 개까지 고정 할 수 있습니다.')
				},
			);
			await fetchData();
		} else {
			await teacherNotFix(notice.noticeId);
			await fetchData();
		}
	} else {
		if (!notice.noticeFlag) {
			await parentFix(
				{
					noticeId: notice.noticeId,
					kidId: kidList.value[0].kidId,
				},
				({ response }) => {},
				({ error }) => {
					Swal.fire({
						icon: 'warning',
						title: '공지사항은 최대 다섯 개까지 고정 할 수 있습니다!',
						confirmButtonText: '확인',
					});
					// alert('공지사항은 최대 다섯 개까지 고정 할 수 있습니다.')
				},
			);
			await fetchData();
		} else {
			await parentNotFix({
				noticeId: notice.noticeId,
				kidId: kidList.value[0].kidId,
			});
			await fetchData();
		}
	}
};
</script>

<style scoped>
.table-box {
	@apply relative overflow-x-auto min-h-screen m-3;
}
.table {
	@apply w-full text-base text-left rtl:text-right text-black;
}
.table-head {
	@apply text-black bg-nav-orange bg-opacity-80;
}
.table-th {
	@apply p-4 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left text-lg;
}
</style>

<template>
	<div class="view-frame">
		<div class="table-box">
			<table class="table">
				<thead class="table-head">
					<tr>
						<th class="table-th w-[47%]">제목</th>
						<th class="table-th w-[16%]">작성자</th>
						<th class="table-th w-[12%]">첨부파일</th>
						<th class="table-th w-[16%]">작성일자</th>
						<th class="table-th w-[12%]"></th>
					</tr>
				</thead>
				<tbody v-if="noticeList.list.length > 0">
					<!-- click="moveReplacement(kid.replacementId)" -->
					<tr
						v-for="notice in noticeList.list"
						:key="notice.noticeId"
						class="hover:bg-nav-orange hover:bg-opacity-20 cursor-pointer border-b"
					>
						<td
							class="border-t-0 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
							@click="moveNoticeDetail(notice.noticeId)"
						>
							{{ notice.noticeTitle }}
						</td>
						<td
							class="border-t-0 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
							@click="moveNoticeDetail(notice.noticeId)"
						>
							{{ notice.teacherName }}
						</td>
						<td
							class="border-t-0 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
							@click="moveNoticeDetail(notice.noticeId)"
						>
							<div
								v-if="notice.fileFlag"
								class="flex justify-start items-center pl-3"
							>
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
							</div>
						</td>
						<td
							class="border-t-0 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
							@click="moveNoticeDetail(notice.noticeId)"
						>
							{{ notice.noticeTime }}
						</td>
						<td
							class="border-t-0 align-middle border-l-0 border-r-0 whitespace-nowrap p-4"
							@click="startFix(notice)"
						>
							<div
								v-if="notice.noticeFlag"
								class="flex justify-center items-center"
							>
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
				<tbody v-else>
					<div class="m-6">등록된 공지사항이 없습니다.</div>
				</tbody>
			</table>
		</div>
		<div
			v-if="noticeList.list.length > 0"
			class="flex justify-center font-bold"
		>
			<button
				:disabled="paging.pageNo === 1"
				@click="paging.pageNo = paging.pageNo - 1"
				class="m-2"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="7"
					height="12"
					viewBox="0 0 7 12"
					fill="none"
				>
					<path
						fill-rule="evenodd"
						clip-rule="evenodd"
						d="M6.70711 0.292893C6.34662 -0.0675907 5.77939 -0.0953203 5.3871 0.209705L5.29289 0.292893L0.292893 5.29289C-0.0675907 5.65338 -0.0953203 6.22061 0.209705 6.6129L0.292893 6.70711L5.29289 11.7071C5.68342 12.0976 6.31658 12.0976 6.70711 11.7071C7.06759 11.3466 7.09532 10.7794 6.7903 10.3871L6.70711 10.2929L2.415 6L6.70711 1.70711C7.06759 1.34662 7.09532 0.779392 6.7903 0.387101L6.70711 0.292893Z"
						:fill="paging.pageNo === 1 ? '#cccccc' : '#000000'"
					/>
				</svg>
			</button>

			<div v-for="index in noticeList.length" :key="index">
				<button
					class="rounded p-2 text-base px-3 mx-2"
					:class="{ 'bg-nav-orange bg-opacity-60': paging.pageNo === index }"
					@click="paging.pageNo = index"
				>
					{{ index }}
				</button>
			</div>
			<button
				:disabled="paging.pageNo === noticeList.length"
				@click="paging.pageNo = paging.pageNo + 1"
				class="m-2"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="7"
					height="12"
					viewBox="0 0 7 12"
					fill="none"
				>
					<path
						d="M0.292893 0.292893C0.653377 -0.0675907 1.22061 -0.0953203 1.6129 0.209705L1.70711 0.292893L6.70711 5.29289C7.06759 5.65338 7.09532 6.22061 6.7903 6.6129L6.70711 6.70711L1.70711 11.7071C1.31658 12.0976 0.683418 12.0976 0.292893 11.7071C-0.0675907 11.3466 -0.0953203 10.7794 0.209705 10.3871L0.292893 10.2929L4.585 6L0.292893 1.70711C-0.0675907 1.34662 -0.0953203 0.779392 0.209705 0.387101L0.292893 0.292893Z"
						:fill="paging.pageNo === noticeList.length ? '#cccccc' : '#000000'"
					/>
				</svg>
			</button>
		</div>
	</div>
</template>
