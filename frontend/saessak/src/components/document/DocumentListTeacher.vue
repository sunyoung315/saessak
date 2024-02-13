<template>
	<div>
		<!-- Teacher Version : 귀가 동의서 -->
		<div v-if="isReplace === true">
			<div class="table-box">
				<table class="table">
					<thead class="table-head">
						<tr>
							<th class="table-th w-[30%]">분류</th>
							<th class="table-th w-[20%]">대상 원아</th>
							<th class="table-th w-[20%]">작성일자</th>
							<th class="table-th w-[20%]">확인여부</th>
						</tr>
					</thead>
					<tbody>
						<tr
							v-for="kid in paginatedReplacementList"
							:key="kid.replacementId"
							@click="moveReplacement(kid.replacementId, kid.kidName)"
							class="hover:bg-nav-blue hover:bg-opacity-20 cursor-pointer border-b"
						>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								귀가 동의서
							</td>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								{{ kid.kidName }}
							</td>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								{{ kid.replacementDay }}
							</td>
							<td
								class="border-t-0 px-3 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								<span
									:class="
										kid.replacementCheck
											? 'bg-dark-navy w-12 text-white rounded-md p-3'
											: 'bg-gray-300 w-12 text-black font-semibold rounded-md py-3 px-[1.1rem]'
									"
								>
									{{ kid.replacementCheck ? '확인완료' : '미확인' }}
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div
				class="pagination flex justify-center text-2xl font-bold"
				v-if="replacementList.length"
			>
				<button @click="prevPage" :disabled="currentPage === 1">
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
							:fill="currentPage === 1 ? '#cccccc' : '#000000'"
						/>
					</svg>
				</button>
				<button
					v-for="page in totalPage"
					:key="page"
					@click="goToPage(page)"
					class="rounded p-2 text-base px-3 mx-2"
					:class="{ 'bg-nav-blue bg-opacity-60': currentPage === page }"
				>
					{{ page }}
				</button>
				<button @click="nextPage">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						width="7"
						height="12"
						viewBox="0 0 7 12"
						fill="none"
					>
						<path
							d="M0.292893 0.292893C0.653377 -0.0675907 1.22061 -0.0953203 1.6129 0.209705L1.70711 0.292893L6.70711 5.29289C7.06759 5.65338 7.09532 6.22061 6.7903 6.6129L6.70711 6.70711L1.70711 11.7071C1.31658 12.0976 0.683418 12.0976 0.292893 11.7071C-0.0675907 11.3466 -0.0953203 10.7794 0.209705 10.3871L0.292893 10.2929L4.585 6L0.292893 1.70711C-0.0675907 1.34662 -0.0953203 0.779392 0.209705 0.387101L0.292893 0.292893Z"
							:fill="currentPage === totalPage ? '#cccccc' : '#000000'"
						/>
					</svg>
				</button>
			</div>
			<div v-if="!replacementList.length" class="m-6">
				<p>등록된 귀가동의서가 없습니다.</p>
			</div>
		</div>
		<!-- Teacher Version : 알레르기 동의서 -->
		<div v-else>
			<div class="table-box">
				<table class="table">
					<thead class="table-head">
						<tr>
							<th class="table-th w-[30%]">분류</th>
							<th class="table-th w-[20%]">대상 원아</th>
							<th class="table-th w-[20%]">작성일자</th>
							<th class="table-th w-[20%]">확인여부</th>
						</tr>
					</thead>
					<tbody>
						<tr
							v-for="kid in allergyList"
							:key="kid.kidId"
							@click="moveAllergy(kid.kidId)"
							class="hover:bg-nav-blue hover:bg-opacity-20 cursor-pointer border-b"
						>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								식품 알레르기 조사서
							</td>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								<router-link :to="`/document/allgery/${kid.kidId}`">{{
									kid.kidName
								}}</router-link>
							</td>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								{{ kid.kidAllergyDate }}
							</td>
							<td
								class="border-t-0 align-middle border-l-0 border-r-0 text whitespace-nowrap p-4"
							>
								<span
									:class="
										kid.kidAllergyCheck
											? 'bg-dark-navy w-12 text-white rounded-md p-3'
											: 'bg-gray-300 w-12 text-black font-semibold rounded-md py-3 px-[1.1rem]'
									"
								>
									{{ kid.kidAllergyCheck ? '확인완료' : '미확인' }}
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div v-if="!allergyList.length" class="m-6">
				<p>등록된 식품 알레르기 동의서가 없습니다.</p>
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

<style scoped>
.table-box {
	@apply relative overflow-x-auto min-h-screen m-3;
}
.table {
	@apply w-full text-base text-left rtl:text-right text-black;
}
.table-head {
	@apply text-black bg-nav-blue bg-opacity-70;
}
.table-th {
	@apply p-4 py-3 uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left text-lg;
}
</style>
