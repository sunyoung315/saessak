<template>
	<div>
		<div class="flex justify-between">
			<div class="inline-flex rounded-md shadow-sm ml-6 mt-5">
				<button
					@click="replaceBtnClicked()"
					class="px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-s-lg hover:bg-dark-navy hover:text-white focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy"
				>
					귀가동의서
				</button>
				<button
					@click="foodBtnClicked()"
					class="px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-e-lg hover:bg-dark-navy hover:text-white focus:z-10 focus:ring-2 focus:ring-dark-navy focus:text-dark-navy"
				>
					식품 알레르기 조사서
				</button>
			</div>
			<div v-if="!isTeacher">
				<button
					type="button"
					@click="createDocument()"
					class="mt-9 mr-12 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
				>
					작성
				</button>
			</div>
		</div>
		<!-- Teacher Version : 귀가 동의서 -->
		<div v-if="isTeacher === true">
			<div v-if="isReplace === true">
				<div class="container p-6 flex items-center">
					<table
						class="items-center bg-transparent border-collapse mt-4 w-full"
					>
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
									{{ kid.replacementCheck ? '확인 완료' : '미확인' }}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- Teacher Version : 알레르기 동의서 -->
			<div v-else>
				<div class="container p-6 flex items-center">
					<table
						class="items-center bg-transparent border-collapse mt-4 w-full"
					>
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
									{{ kid.kidAlleryCheck ? '확인 완료' : '미확인' }}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- Parents Version : 귀가 동의서 -->
		<div v-else>
			<div v-if="isReplace === true">
				<div class="container p-6 flex items-center">
					<table
						class="items-center bg-transparent border-collapse mt-4 w-full"
					>
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
									v-if="isReplace.value"
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
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';
import { useAllergyStore } from '@/store/allergy';
import DocumentAllergyDetail from '@/components/document/DocumentAllergyDetail.vue';

const router = useRouter();
const replacementStore = useReplacementStore();
const allergyStore = useAllergyStore();

// boolean 설정, isTeacher: 선생님/학부모, isReplace: 귀가동의서/알러지
const isTeacher = ref(true);
const isReplace = ref(true);

// 데이터 목록 가져오기
const replacementList = ref([]);
const myReplacementList = ref([]);
const allergyList = ref([]);

onMounted(async () => {
	// 선생님 - 목록 - (classroomId: 자신의 담당 반 -> 1)
	await replacementStore.getReplacementList(1);
	replacementList.value = replacementStore.replacementList;
	await allergyStore.getAllergyList(1);
	allergyList.value = allergyStore.allergyList;

	// 부모님 - 목록 - mykidId (자기 자신의 아이 찾아서 매핑 -> 현재 1)
	await replacementStore.getmyKidReplacementList(1);
	myReplacementList.value = replacementStore.myKidReplacementList;
});

// 동의서 버튼 동작
function replaceBtnClicked() {
	isReplace.value = true;
}
function foodBtnClicked() {
	isReplace.value = false;
}
function createDocument() {
	if (isReplace.value) {
		router.push({
			name: 'DocumentReplaceCreate',
		});
	} else {
		router.push({
			name: 'DocumentAllergyCreate',
		});
	}
}
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

const callFunction = () => {
	console.log('이벤트 수신');
};

// 버튼 끝
</script>

<style scoped></style>
