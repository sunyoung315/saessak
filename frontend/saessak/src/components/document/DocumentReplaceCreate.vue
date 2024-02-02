<template>
	<div>
		<div
			class="container mx-16 p-1.5 w-auto border border-gray-200 shadow rounded-lg"
		>
			<div class="flex justify-end items-center mb-10">
				<button
					type="button"
					@click.prevent="regist()"
					class="mt-8 mr-6 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
				>
					등록
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
				<p class="text-gray-700 text-4xl font-bold text-center">
					귀 가 동 의 서
				</p>
			</div>
			<div
				class="text-wrap text-left text-2xl whitespace-pre-line ml-40 mt-12 font-bold"
			>
				<p class="mb-4 ml-60">이름: {{ kidName }}</p>
				<p class="mb-4">
					날짜 :
					<input type="date" v-model="registReplacement.replacementDate" />
				</p>
				<p class="mb-4">
					시간 :
					<input type="time" v-model="registReplacement.replacementTime" />
				</p>
				<div class="flex justify-start">
					<p class="mb-4">
						보호자 :
						<input type="text" v-model="registReplacement.replacementName" />
					</p>
					<p class="mb-4 ml-48">
						보호자 관계 :
						<input
							type="text"
							v-model="registReplacement.replacementRelationship"
						/>
					</p>
				</div>
				<p class="mb-4">
					귀가 방법 :
					<input type="tel" v-model="registReplacement.replacementVehicle" />
				</p>
				<p class="mb-4">
					비상연락망 :
					<input type="tel" v-model="registReplacement.replacementNumber" />
				</p>
			</div>
			<div class="text-wrap text-center text-xl whitespace-pre-line m-12">
				<p>{{ replaceContent }}</p>
				<p class="m-8 font-bold">금일 자녀의 귀가를 선생님께 의뢰합니다.</p>
			</div>

			<div class="flex justify-end">
				<div class="flex-col text-gray-700 text-xl font-bold m-8">
					<div>
						<h2>전자 서명: {{ registReplacement.replacementSignature }}</h2>
						<document-signature @signature-saved="handleSignatureSaved" />
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';
import DocumentSignature from '@/components/document/DocumentSignature.vue';

const router = useRouter();
const replacementStore = useReplacementStore();

// 내 아이 id 조회
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
const kidId = loginStore.kidList[0].kidId;
const kidName = loginStore.kidList[0].kidName;

// 전달할 데이터
const registReplacement = ref({
	kidId: kidId,
	replacementDate: '',
	replacementTime: '',
	replacementVehicle: '',
	replacementRelationship: '',
	replacementNumber: '',
	replacementName: '',
	replacementSignature: '',
});

// Btn
async function regist() {
	try {
		await replacementStore.PostRegistReplacement(registReplacement.value);
		console.log(registReplacement);
		console.log('성공');
	} catch (error) {
		console.error('실패: ', error);
	}
}

function goBack() {
	router.go(-1);
}
// Btn

// 문구
const replaceContent = ref(
	'영유아의 귀가 시 위 보호자에게 인도하여 주세요. \n \n 위 보호자 이외의 다른 사람에게 인계할 때는 사전에 반드시 연락을 취하겠습니다. \n \n 원에서는 부모가 희망하더라도 영유아를 혼자 귀가시키지 않습니다.',
);

// 전자 서명 데이터 가져오기
const handleSignatureSaved = signature => {
	registReplacement.value.replacementSignature = signature;
};
</script>

<style scoped></style>
