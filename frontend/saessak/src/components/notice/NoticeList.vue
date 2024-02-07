<script setup>
import { onMounted, ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';

import { noticeListAll } from '@/api/notice'


const router = useRouter();

// 내 아이 id 조회
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
const kidId = loginStore.kidList[0].kidId;

// 내 아이 귀가동의서 목록 가져오기
const myReplacementList = ref([]);

onMounted(async () => {
	noticeListAll(kidId, {
		pageNo: 0
	})
});

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
	<div>
		<div>
			<div class="container p-6 flex items-center">
				<table class="items-center bg-transparent border-collapse mt-4 w-full text-center">
					<thead>
						<tr class="bg-nav-orange">
							<th
								class="px-16 border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold">
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
						<tr v-for="kid in myReplacementList" :key="kid.replacementId"
							@click="moveReplacement(kid.replacementId)" class="hover:bg-nav-blue" @update="callFunction">
							<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
								귀가 동의서
							</td>

							<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
								{{ kid.kidName }}
							</td>
							<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
								{{ kid.replacementDay }}
							</td>
							<td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
								<span :class="kid.replacementCheck
									? 'bg-nav-blue text-black font-bold rounded-lg p-2'
									: 'bg-gray-500 text-white font-bold rounded-lg p-2'
									">
									{{ kid.replacementCheck ? '확인 완료' : '확인 필요' }}
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

