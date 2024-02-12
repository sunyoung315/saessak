<template>
	<div>
		<!-- Teacher Version : 귀가 동의서 -->
		<div v-if="isReplace === true">
			<div class="container p-6 flex items-center">
				<table
					class="items-center bg-transparent border-collapse mt-4 w-full text-center"
				>
					<thead>
						<tr class="bg-nav-blue">
							<th
								class="w-72 px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								분류
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								대상 원아
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								작성일자
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								확인여부
							</th>
						</tr>
					</thead>
					<tbody>
						<tr
							v-for="kid in paginatedReplacementList"
							:key="kid.replacementId"
							@click="moveReplacement(kid.replacementId, kid.kidName)"
							class="hover:bg-nav-blue hover:bg-opacity-20"
						>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								귀가 동의서
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								{{ kid.kidName }}
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								{{ kid.replacementDay }}
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								<span
									:class="
										kid.replacementCheck
											? 'bg-nav-blue text-black font-bold rounded-lg p-2'
											: ' text-black font-bold rounded-lg p-2'
									"
								>
									{{ kid.replacementCheck ? '확인완료' : '미확인' }}
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="pagination flex justify-center text-2xl font-bold">
				<button
					@click="prevPage"
					:disabled="currentPage === 1"
					:class="{ 'text-gray-200': currentPage === 1 }"
				>
					←
				</button>
				<button
					v-for="page in totalPage"
					:key="page"
					@click="goToPage(page)"
					class="m-2 rounded-lg p-2"
					:class="{ 'bg-nav-blue': currentPage === page }"
				>
					{{ page }}
				</button>
				<button
					@click="nextPage"
					:class="{ 'text-gray-200': currentPage === totalPage }"
				>
					→
				</button>
			</div>
		</div>
		<!-- Teacher Version : 알레르기 동의서 -->
		<div v-else>
			<div class="container p-6 flex items-center">
				<table
					class="text-center items-center bg-transparent border-collapse mt-4 w-full"
				>
					<thead>
						<tr class="bg-nav-blue">
							<th
								class="w-72 px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								분류
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								대상 원아
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
							>
								작성일자
							</th>
							<th
								class="px-6 align-middle border border-solid border-blueGray-100 py-3 text uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold"
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
							class="hover:bg-nav-blue hover:bg-opacity-20"
						>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								식품 알레르기 조사서
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								<router-link :to="`/document/allgery/${kid.kidId}`">{{
									kid.kidName
								}}</router-link>
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								{{ kid.kidAllergyDate }}
							</td>
							<td
								class="border-t-0 px-6 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								<span
									:class="
										kid.kidAllergyCheck
											? 'bg-nav-blue text-black font-bold rounded-lg p-2'
											: ' text-black font-bold rounded-lg p-2'
									"
								>
									{{ kid.kidAllergyCheck ? '확인완료' : '미확인' }}
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
import { onMounted, ref, defineProps, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';
import { useAllergyStore } from '@/store/allergy';

const router = useRouter();
const replacementStore = useReplacementStore();
const allergyStore = useAllergyStore();

// boolean 설정, isTeacher: 선생님/학부모, isReplace: 귀가동의서/알러지

// 데이터 목록 가져오기
const replacementList = ref([]);
const getReplacementList = async () => {
	await replacementStore.getReplacementList();
	replacementList.value = replacementStore.replacementList;
};
const allergyList = ref([]);
const getAllergyList = async () => {
	await allergyStore.getAllergyList();
	allergyList.value = allergyStore.allergyList;
};

const props = defineProps({
	isReplace: Boolean,
});

onMounted(async () => {
	// 선생님
	await getReplacementList();
	await getAllergyList();
});

function moveReplacement(replacementId, kidName) {
	router.push({
		name: 'DocumentReplaceDetail',
		params: { replacementId },
		state: { kidName: kidName },
	});
}
function moveAllergy(kidId) {
	router.push({
		name: 'DocumentAllergyDetail',
		params: { kidId },
	});
}

////////////// 페이지네이션
const itemsPerPage = 10;
const currentPage = ref(1);

const totalPage = computed(() =>
	Math.ceil(replacementList.value.length / itemsPerPage),
);

const paginatedReplacementList = computed(() => {
	const start = (currentPage.value - 1) * itemsPerPage;
	const end = start + itemsPerPage;
	return replacementList.value.slice(start, end);
});

const nextPage = () => {
	if (currentPage.value < totalPage.value) {
		currentPage.value++;
	}
};

const goToPage = page => {
	currentPage.value = page;
};

const prevPage = () => {
	if (currentPage.value > 1) {
		currentPage.value--;
	}
};
///////////////////
</script>

<style scoped></style>
