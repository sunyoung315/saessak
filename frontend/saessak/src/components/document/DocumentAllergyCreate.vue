<template>
	<div>
		<!--Only Parents Version-->
		<div class="view-frame p-1.5">
			<div class="flex justify-end items-center mb-10">
				<button type="button" @click="regist()" class="btn mt-7 mr-4 mb-3">
					등록
				</button>
				<button type="button" @click="goBack()" class="btn mt-7 mr-7 mb-3">
					목록
				</button>
			</div>
			<div>
				<p class="text-black text-4xl font-bold text-center">
					식품 알레르기 조사서
				</p>
			</div>
			<div>
				<p
					class="text-wrap text-center text-lg whitespace-pre-line m-8 leading-10"
				>
					{{ allergyContent }}
				</p>
			</div>
			<div
				class="text-center whitespace-pre-line border border-gray-200 shadow rounded mx-32"
			>
				<br />
				<p class="text-lg underline font-bold">※ 알레르기 유발식품</p>
				<br />
				<div class="flex flex-wrap justify-center mx-16 mb-8">
					<template v-for="allergy in allergyList" :key="allergy.no">
						<span class="inline-block text-base mr-8 py-2"
							>{{ allergy.no }}. {{ allergy.name }}</span
						>
					</template>
				</div>
			</div>
			<div class="flex items-center flex-wrap mx-32 mt-8">
				<template v-for="allergy in allergyList" :key="allergy.no">
					<div class="w-1/5 p-1 flex items-center">
						<input
							:id="'checked-checkbox' + allergy.no"
							type="checkbox"
							:value="allergy.no"
							@change="updateAllergy($event, allergy.no)"
							class="w-4 h-4 border-gray-100 rounded cursor-pointer"
						/>
						<label
							:for="'checked-checkbox' + allergy.no"
							class="px-3 text-lg font-medium text-black cursor-pointer"
						>
							{{ allergy.no }}. {{ allergy.name }}
						</label>
					</div>
				</template>
			</div>
			<div class="flex justify-end">
				<div class="flex-col text-black font-bold m-8">
					<p class="my-3 mx-1 text-black text-xl font-bold">
						이름 : {{ registAllergy.kidName }}
					</p>
					<div>
						<h2 class="mx-1 mb-1 text-xl">
							전자 서명: {{ registAllergy.kidAllergySignature }}
						</h2>
						<document-signature @signature-saved="handleSignatureSaved" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<RouterView />
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAllergyStore } from '@/store/allergy';
import DocumentSignature from '@/components/document/DocumentSignature.vue';
import Swal from 'sweetalert2';

const router = useRouter();
const allergyStore = useAllergyStore();

// 내 아이 id 조회
// let loginStore = JSON.parse(localStorage.getItem('loginStore'));
const props = defineProps({
	loginStore: Object,
});

const kidId = props.loginStore.kidList[0].kidId;
const kidName = props.loginStore.kidList[0].kidName;

// 전달할 데이터
const registAllergy = ref({
	kidId: kidId,
	kidName: kidName,
	kidAllergy: '',
});

// 알러지 목록
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

const kidAllergySignature = ref();
// 버튼
async function regist() {
	if (!validateSignature()) {
		return;
	}
	// console.log(registAllergy);
	try {
		await allergyStore.PostRegistAllergy(registAllergy.value);
		// 전자서명 입력
		const formData = new FormData();
		formData.append('kidId', kidId);
		formData.append(
			'signFile',
			new File(
				[kidAllergySignature.value],
				'sign' + allergyStore.registAllergyId + '.png',
				{ type: 'image/png' },
			),
		);
		await allergyStore.PostRegistFileAllergy(formData);
		router.push({
			name: 'DocumentList',
		});
	} catch (error) {
		console.error('실패: ', error);
	}
}

// 전자 서명 데이터 가져오기
const handleSignatureSaved = signature => {
	kidAllergySignature.value = signature;
	console.log(kidAllergySignature.value);
};

function goBack() {
	router.go(-1);
}
// 버튼 끝

// 문구
const allergyContent = ref(
	'원아들의 식품 알레르기에 관한 실태를 파악하고 발병 등에 대한 예방조치와 제거 및 대체식품의 필요여부를  \n 확인하고자 하오니 특정 식품에 대한 알레르기 반응이 있는 식품을 확인하여 체크해주시기 바랍니다.',
);

// checkbox 선택된 allergy 번호 배열을 전달할 string 형식으로 변환
const selectedAllergies = ref([]);
function updateAllergy(e, allergyNo) {
	if (e.target.checked) {
		selectedAllergies.value.push(allergyNo);
	} else {
		const index = selectedAllergies.value.indexOf(allergyNo);
		if (index > -1) {
			selectedAllergies.value.splice(index, 1);
		}
	}
	registAllergy.value.kidAllergy = selectedAllergies.value.join('/');
}

// 유효성 검사
const validateSignature = () => {
	if (!(kidAllergySignature.value instanceof Blob)) {
		Swal.fire({
			icon: 'warning',
			title: '전자 서명을 완료해주시기 바랍니다.',
			text: '서명 작성 후 저장 버튼을 누르셔야 등록이 완료됩니다.',
			confirmButtonText: '확인',
		});
		return false;
	}
	return true;
};
</script>

<style scoped></style>
