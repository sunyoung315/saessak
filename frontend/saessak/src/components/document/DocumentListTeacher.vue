<template>
	<div>
		<!-- Teacher Version : 귀가 동의서 -->
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
							v-for="kid in replacementList"
							:key="kid.replacementId"
							@click="moveReplacement(kid.replacementId)"
							class="hover:bg-nav-blue"
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
								<span
									:class="
										kid.replacementCheck
											? 'bg-nav-blue text-black font-bold rounded-lg p-2'
											: 'bg-gray-500 text-white font-bold rounded-lg p-2'
									"
								>
									{{ kid.replacementCheck ? '확인 완료' : '확인 필요' }}
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- Teacher Version : 알레르기 동의서 -->
		<div v-else>
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
							v-for="kid in allergyList"
							:key="kid.kidId"
							@click="moveAllergy(kid.kidId)"
							class="hover:bg-nav-blue"
						>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								식품 알레르기 조사서
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								<router-link :to="`/document/allgery/${kid.kidId}`">{{
									kid.kidName
								}}</router-link>
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								{{ kid.kidAllergyDate }}
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
							>
								<span
									:class="
										kid.replacementCheck
											? 'bg-nav-blue text-black font-bold rounded-lg p-2'
											: 'bg-gray-500 text-white font-bold rounded-lg p-2'
									"
								>
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

<script setup>
import { onMounted, ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';
import { useAllergyStore } from '@/store/allergy';

const router = useRouter();
const replacementStore = useReplacementStore();
const allergyStore = useAllergyStore();

// boolean 설정, isTeacher: 선생님/학부모, isReplace: 귀가동의서/알러지

// 데이터 목록 가져오기
const replacementList = ref([]);
const allergyList = ref([]);

const props = defineProps({
	isReplace: Boolean,
});

onMounted(async () => {
	// 선생님
	await replacementStore.getReplacementList();
	replacementList.value = replacementStore.replacementList;
	await allergyStore.getAllergyList();
	allergyList.value = allergyStore.allergyList;
});

function moveReplacement(replacementId) {
	router.push({
		name: 'DocumentReplaceDetail',
		params: { replacementId },
	});
}
function moveAllergy(kidId) {
	router.push({
		name: 'DocumentAllergyDetail',
		params: { kidId },
	});
}

// 버튼 끝
</script>

<style scoped></style>
