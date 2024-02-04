<template>
	<div>
		<div
			class="container mx-16 p-1.5 w-auto border border-gray-200 shadow rounded-lg"
		>
			<div class="flex justify-end items-center mb-10">
				<button
					type="button"
					@click="check()"
					class="mt-8 mr-6 border border-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
					:class="
						replaceDetailList.replacementCheck
							? 'text-white bg-dark-navy hover:text-dark-navy'
							: 'text-black bg-white hover:bg-dark-navy'
					"
				>
					{{ replaceDetailList.replacementCheck ? '확인완료' : '미확인' }}
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
				<div class="md:flex md:items-center mb-6">
					<div>
						<label
							class="block text-black font-bold md:text-right mb-1 md:mb-0 pr-4"
							for="inline-full-name"
						>
							이름: {{ kidName }}
						</label>
					</div>
				</div>
				<div class="flex justify-start">
					<p class="mb-4">날짜 : {{ replaceDetailList.replacementDate }}</p>
					<p class="mb-4 ml-40">
						시간 : {{ replaceDetailList.replacementTime }}
					</p>
				</div>
				<div class="flex justify-start">
					<p class="mb-4">보호자 : {{ replaceDetailList.replacementName }}</p>
					<p class="mb-4 ml-52">
						보호자 관계 : {{ replaceDetailList.replacementRelationship }}
					</p>
				</div>
				<p class="mb-4">
					귀가 방법 : {{ replaceDetailList.replacementVehicle }}
				</p>
				<p class="mb-4">
					비상연락망 : {{ replaceDetailList.replacementNumber }}
				</p>
			</div>
			<div class="text-wrap text-center text-xl whitespace-pre-line m-12">
				<p>{{ replaceContent }}</p>
				<p class="m-8 font-bold">금일 자녀의 귀가를 선생님께 의뢰합니다.</p>
			</div>

			<div class="flex justify-end">
				<div class="flex-col text-gray-700 text-xl font-bold m-8">
					<div>
						<h2>전자 서명: 전자서명된 이미지 파일</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';

const router = useRouter();
const route = useRoute();
const emit = defineEmits(['update']);

// const isTeacher = ref(true);
const replacementStore = useReplacementStore();
const replacementId = route.params.replacementId;
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
// 내 아이 이름 조회 (학부모인 경우만)
const kidName = loginStore.isTeacher
	? undefined
	: loginStore.kidList[0].kidName;

// 데이터 목록 가져오기
const replaceDetailList = ref([]);
onMounted(async () => {
	await replacementStore.getReplacementDetailList(replacementId);
	replaceDetailList.value = replacementStore.replacemenDetailtList;
});

const replacementCheckList = ref([]);

// Btn
// 확인버튼
function check() {
	replacementStore.getreplacemenChecktList(replacementId);
	replacementCheckList.value = replacementStore.replacementCheckList;
}

function goBack() {
	router.go(-1);
}
// Btn

// 문구
const replaceContent = ref(
	'영유아의 귀가 시 위 보호자에게 인도하여 주세요. \n \n 위 보호자 이외의 다른 사람에게 인계할 때는 사전에 반드시 연락을 취하겠습니다. \n \n 원에서는 부모가 희망하더라도 영유아를 혼자 귀가시키지 않습니다.',
);
</script>

<style scoped></style>
