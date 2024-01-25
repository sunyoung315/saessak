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
			<p>선생님</p>
			<div v-if="isReplace === true">
				<div class="container p-6 flex items-center">
					<table
						class="items-center bg-transparent border-collapse mt-4 w-full"
					>
						<thead>
							<tr class="bg-nav-navy">
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
							<tr v-for="kid in kidReplaceList" :key="kid.replacementId">
								<td
									class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
								>
									귀가 동의서
								</td>
								<td
									class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
								>
									<router-link
										:to="`/document/replacement/${kid.replacementId}`"
										>{{ kid.kidName }}</router-link
									>
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
							<tr class="bg-nav-navy">
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
							<tr v-for="kid in kidAllgeryList" :key="kid.kidId">
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
			<p>학부모</p>
			<div v-if="isReplace === true">
				<div class="container p-6 flex items-center">
					<table
						class="items-center bg-transparent border-collapse mt-4 w-full"
					>
						<thead>
							<tr class="bg-nav-navy">
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
							<tr v-for="kid in kidReplaceList" :key="kid.replacementId">
								<td
									class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
								>
									귀가 동의서
								</td>
								<td
									class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
								>
									<router-link
										:to="`/document/replacement/${kid.replacementId}`"
										>{{ kid.kidName }}</router-link
									>
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
				<div class="container p-6 flex items-center">
					<table
						class="items-center bg-transparent border-collapse mt-4 w-full"
					>
						<thead>
							<tr class="bg-nav-navy">
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
							<tr v-for="kid in kidAllgeryList" :key="kid.kidId">
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
	</div>
	<!-- <tr v-for="kid in kidReplaceList" :key="kid.replacementId">
							<td>귀가 동의서</td>
							<td>
								<router-link
									:to="`/document/replacement/${kid.replacementId}`"
									>{{ kid.kidName }}</router-link
								>
							</td>
							<td>{{ kid.replacementDay }}</td>
							<td>{{ kid.replacementCheck ? '확인 완료' : '미확인' }}</td>
						</tr> -->
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/stores/replaceDocument';

const router = useRouter();
const replacementStore = useReplacementStore();

const replacementList = ref([]);
// console.log(replacementList);

onMounted(async () => {
	await replacementStore.getReplacementList(2);
	replacementList.value = replacementStore.replacementList;
});

// console.log(replacementStore);

// boolean 설정, isTeacher: 선생님/학부모, isReplace: 귀가동의서/알러지
const isTeacher = ref(true);
const isReplace = ref(true);

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
// 버튼 끝

// 더미데이터 sql 가져오는거

// 더미 데이터
const kidReplaceList = ref([
	{
		replacementId: 1,
		replacementDay: '2024-01-12',
		kidName: '김샛별',
		replacementCheck: true,
	},
	{
		replacementId: 2,
		replacementDay: '2024-01-12',
		kidName: '김햇님',
		replacementCheck: false,
	},
]);

const kidAllgeryList = ref([
	{
		kidId: 1,
		kidName: '김샛별',
		kidAllergyDate: '2024-01-14',
		kidAlleryCheck: false,
	},
	{
		kidId: 2,
		kidName: '김햇님',
		kidAllergyDate: '2024-01-24',
		kidAlleryCheck: false,
	},
]);
// 더미 데이터
</script>

<style scoped></style>
