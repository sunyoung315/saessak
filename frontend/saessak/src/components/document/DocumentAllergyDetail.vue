<template>
	<div>
		<!--Teacher Version-->
		<div v-if="isTeacher">
			<div
				class="container mx-16 p-1.5 w-auto border border-gray-200 shadow rounded-lg"
			>
				<div class="flex justify-end items-center mb-10">
					<button
						type="button"
						@click="check()"
						class="mt-8 mr-8 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
					>
						{{ confirm ? '확인완료' : '미확인' }}
					</button>
					<button
						type="button"
						@click="goBack()"
						class="mt-8 mr-8 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
					>
						목록
					</button>
				</div>
				<div>
					<p class="text-gray-700 text-3xl font-bold text-center">
						식품 알레르기 조사서
					</p>
				</div>
				<div>
					<p class="text-wrap text-center text-xl whitespace-pre-line m-8">
						{{ allergyContent }}
					</p>
				</div>
				<div
					class="text-center whitespace-pre-line border border-gray-200 shadow rounded-lg m-8"
				>
					<br />
					<p class="text-xl underline font-bold">※ 알레르기 유발식품</p>
					<br />
					<p class="mb-8">{{ allergyType }}</p>
				</div>
				<div class="flex items-center flex-wrap">
					<template v-for="allergy in allergyList" :key="allergy.no">
						<div class="w-1/5 p-2">
							<input
								:id="'checked-checkbox-' + allergy.no"
								type="checkbox"
								:value="allergy.no"
								:checked="isKidAllergic(allergy.no)"
								class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
							/>
							<label
								:for="'checked-checkbox-' + allergy.no"
								class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"
							>
								{{ allergy.name }}
							</label>
						</div>
					</template>
				</div>
				<div class="flex justify-end">
					<div class="flex-col text-gray-700 text-xl font-bold m-8">
						<div>
							<p class="mb-8">반 : {{ kidAllergy[0].classroomName }}</p>
							<p class="mb-8">이름 : {{ kidAllergy[0].kidName }}</p>
						</div>
						<div>
							<h2>전자 서명:</h2>
							<document-signature />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div v-else>
			<!-- Parents -->
			<div
				class="container mx-16 p-1.5 w-auto border border-gray-200 shadow rounded-lg"
			>
				<div class="flex justify-end items-center mb-10"></div>
				<div>
					<p class="text-gray-700 text-3xl font-bold text-center">
						식품 알레르기 조사서
					</p>
				</div>
				<div>
					<p class="text-wrap text-center text-xl whitespace-pre-line m-8">
						{{ allergyContent }}
					</p>
				</div>
				<div
					class="text-center whitespace-pre-line border border-gray-200 shadow rounded-lg m-8"
				>
					<br />
					<p class="text-xl underline font-bold">※ 알레르기 유발식품</p>
					<br />
					<p class="mb-8">{{ allergyType }}</p>
				</div>
				<div class="flex items-center flex-wrap">
					<template v-for="allergy in allergyList" :key="allergy.no">
						<div class="w-1/5 p-2">
							<input
								:id="'checked-checkbox-' + allergy.no"
								type="checkbox"
								:value="allergy.no"
								:checked="isKidAllergic(allergy.no)"
								class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
							/>
							<label
								:for="'checked-checkbox-' + allergy.no"
								class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"
							>
								{{ allergy.name }}
							</label>
						</div>
					</template>
				</div>
				<div class="flex justify-end">
					<div class="flex-col text-gray-700 text-xl font-bold m-8">
						<div>
							<p class="mb-8">반 : {{ kidAllergy[0].classroomName }}</p>
							<p class="mb-8">이름 : {{ kidAllergy[0].kidName }}</p>
						</div>
						<div>
							<h2>전자 서명:</h2>
							<document-signature />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import DocumentSignature from '@/components/document/DocumentSignature.vue';

const router = useRouter();
const isTeacher = ref(true);

const kidAllergy = [
	{
		kidId: 100,
		classroomName: '개나리',
		kidName: '김샛별',
		kidAllergy: '1/2/11/12',
		kidAllergySignature: 'String',
	},
];

const allergyList = ref([
	{
		no: 1,
		name: '난류',
	},
	{
		no: 2,
		name: '우유',
	},
	{
		no: 3,
		name: '메밀',
	},
	{
		no: 4,
		name: '땅콩',
	},
	{
		no: 5,
		name: '대두',
	},
	{
		no: 6,
		name: '밀',
	},
	{
		no: 7,
		name: '고등어',
	},
	{
		no: 8,
		name: '게',
	},
	{
		no: 9,
		name: '새우',
	},
	{
		no: 10,
		name: '돼지고기',
	},
	{
		no: 11,
		name: '복숭아',
	},
	{
		no: 12,
		name: '토마토',
	},
	{
		no: 13,
		name: '아황산류',
	},
	{
		no: 14,
		name: '호두',
	},
	{
		no: 15,
		name: '닭고기',
	},
	{
		no: 16,
		name: '쇠고기',
	},
	{
		no: 17,
		name: '오징어',
	},
	{
		no: 18,
		name: '조개',
	},
	{
		no: 19,
		name: '잣',
	},
]);

// 해당 알러지 번호가 알러지 목록에 있는지 확인하는 함수
const isKidAllergic = allergyNo => {
	return kidAllergy[0].kidAllergy.split('/').includes(allergyNo.toString());
};

const confirm = ref(false);
function check() {
	confirm.value = !confirm.value;
}

function goBack() {
	router.go(-1);
}

const allergyContent = ref(
	'원아들의 식품 알레르기에 관한 실태를 파악하고 \n \n 발병 등에 대한 예방조치와 제거 및 대체식품의 필요여부를 확인하고자 하오니 \n \n 특정 식품에 대한 알레르기 반응이 있는 식품을 확인하여 체크해주시기 바랍니다.',
);
const allergyType = ref(
	'1. 난류 2. 우유 3. 메밀 4. 땅콩 5. 대두 6. 밀 7.고등어 8. 게 9. 새우 10. 돼지고기 \n 11. 복숭아 12. 토마토 13. 아황산류 14. 호두 15. 닭고기 16. 쇠고기 17. 오징어 18. 조개 19. 잣',
);
</script>

<style scoped></style>
