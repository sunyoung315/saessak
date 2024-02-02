<template>
	<div>
		<!-- 귀가 동의서 -->
		<div v-if="isReplace === true">
			<div class="container p-6 flex items-center">
				<table class="items-center bg-transparent border-collapse mt-4 w-full">
					<thead>
						<tr class="bg-nav-blue">
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
							>
								분류
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
							>
								대상 원아
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
							>
								작성일자
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
							>
								확인여부
							</th>
						</tr>
					</thead>
					<tbody>
						<tr
							v-for="kid in myReplacementList"
							:key="kid.replacementId"
							@click="moveReplacement(kid.replacementId)"
							class="hover:bg-nav-blue"
							@update="callFunction"
						>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								귀가 동의서
							</td>

							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								{{ kid.kidName }}
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								{{ kid.replacementDay }}
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								{{ kid.replacementCheck ? '확인 완료' : '미확인' }}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- Parents Version : 알레르기 동의서 -->
		<div v-else>
			<DocumentAllergyDetail />
			<router-view></router-view>
		</div>
	</div>
</template>

<script setup>
import { onMounted, ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';
import DocumentAllergyDetail from '@/components/document/DocumentAllergyDetail.vue';

const router = useRouter();
const replacementStore = useReplacementStore();

// 내 아이 id 조회
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
const kidId = loginStore.kidList[0].kidId;

// isReplace : 귀가 동의서(T), 식품 알레르기 조사서(F) 구분
const props = defineProps({
	isReplace: Boolean,
});

// 내 아이 귀가동의서 목록 가져오기
const myReplacementList = ref([]);

onMounted(async () => {
	// 부모님 - 목록 - mykidId
	await replacementStore.getmyKidReplacementList(kidId);
	myReplacementList.value = replacementStore.myKidReplacementList;
});

// 동의서 버튼 동작

function moveReplacement(replacementId) {
	router.push({
		name: 'DocumentReplaceDetail',
		params: { replacementId },
	});
}
// 버튼 끝
</script>

<style scoped></style>
