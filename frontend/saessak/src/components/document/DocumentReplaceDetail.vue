<template>
	<div>
		<div class="view-frame p-1.5">
			<div class="flex justify-end items-center mb-10">
				<button
					v-if="loginStore.isTeacher"
					type="button"
					@click="check"
					class="btn mt-7 mr-4 mb-3"
					:class="
						replaceDetailList.replacementCheck
							? 'text-white bg-dark-navy cursor-default'
							: 'text-black bg-gray-300 font-semibold !border-0 hover:bg-white'
					"
				>
					{{ replaceDetailList.replacementCheck ? '확인완료' : '미확인' }}
				</button>
				<button
					v-else
					type="button"
					disabled
					class="mt-7 mr-4 mb-3"
					:class="
						replaceDetailList.replacementCheck
							? 'bg-dark-navy w-20 text-white rounded-md p-3 py-[0.6rem] cursor-default'
							: 'bg-gray-300 w-20 text-black font-semibold rounded-md py-[0.65rem] px-[1.1rem] cursor-default'
					"
				>
					{{ replaceDetailList.replacementCheck ? '확인완료' : '미확인' }}
				</button>
				<button type="button" @click="goList()" class="btn mt-7 mr-7 mb-3">
					목록
				</button>
			</div>
			<div>
				<p class="text-black text-4xl font-bold text-center">귀 가 동 의 서</p>
			</div>
			<div class="flex justify-center">
				<div
					class="flex flex-col text-left mt-16 font-bold justify-center w-[50%]"
				>
					<div class="md:flex md:items-center mb-6">
						<div>
							<label
								class="block text-black font-bold mb-1 md:mb-0 pr-4 text-lg"
								for="inline-full-name"
							>
								이름: {{ kidName }}
							</label>
						</div>
					</div>
					<div class="flex items-center justify-between mb-6">
						<div
							class="w-48 text-black font-bold md:mb-0 text-black text-lg"
							for="inline-full-name"
						>
							날짜: {{ replaceDetailList.replacementDate }}
						</div>
						<div
							class="w-48 text-black font-bold md:mb-0 text-black text-lg"
							for="inline-full-name"
						>
							시간: {{ replaceDetailList.replacementTime }}
						</div>
					</div>
					<div class="flex items-center justify-between mb-6">
						<div
							class="w-48 text-black font-bold md:mb-0 text-black text-lg"
							for="inline-full-name"
						>
							보호자: {{ replaceDetailList.replacementName }}
						</div>
						<div
							class="w-48 text-black font-bold md:mb-0 text-black text-lg"
							for="inline-full-name"
						>
							보호자 관계: {{ replaceDetailList.replacementRelationship }}
						</div>
					</div>
					<div class="flex items-center justify-between mb-6">
						<div
							class="w-48 text-black font-bold md:mb-0 text-black text-lg"
							for="inline-full-name"
						>
							귀가 방법: {{ replaceDetailList.replacementVehicle }}
						</div>
						<div
							class="w-48 text-black font-bold md:mb-0 text-black text-lg"
							for="inline-full-name"
						>
							비상연락망: {{ replaceDetailList.replacementNumber }}
						</div>
					</div>
				</div>
			</div>
			<div class="text-wrap text-center text-lg whitespace-pre-line m-12 mt-16">
				<p>{{ replaceContent }}</p>
				<p class="m-8 font-bold">금일 자녀의 귀가를 선생님께 의뢰합니다.</p>
			</div>

			<div class="flex justify-end">
				<div class="flex-col text-black m-8">
					<div>
						<h2 class="ml-2 mb-1 text-lg font-bold">전자 서명:</h2>
						<div
							v-if="replaceDetailList.replacementSignature"
							class="border relative text-center items-center font-bold text-lg h-32 w-64"
						>
							<img
								:src="replaceDetailList.replacementSignature"
								alt="image"
								class="z-0 absolute top-0 left-0 w-full h-full"
							/>
							<div
								class="z-10 relative flex items-center justify-center h-full"
							>
								<span class>(인 또는 서명)</span>
							</div>
						</div>
						<div v-else>등록된 서명이 없습니다.</div>
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
// 아이 이름 조회
const kidName = loginStore.isTeacher
	? history.state.kidName
	: loginStore.kidList[0].kidName;

// 데이터 목록 가져오기
const replaceDetailList = ref([]);
const getReplacementDetailList = async () => {
	await replacementStore.getReplacementDetailList(replacementId);
	replaceDetailList.value = replacementStore.replacemenDetailtList;
};

onMounted(async () => {
	await getReplacementDetailList(replacementId);
});

// Btn
// 확인버튼
async function check() {
	if (replaceDetailList.value.replacementCheck === false) {
		replaceDetailList.value.replacementCheck =
			!replaceDetailList.value.replacementCheck;
		await replacementStore.getReplacemenCheckList(replacementId);
	}
}

function goList() {
	router.push({
		name: 'DocumentList',
	});
}
// Btn

// 문구
const replaceContent = ref(
	'영유아의 귀가 시 위 보호자에게 인도하여 주세요. \n \n 위 보호자 이외의 다른 사람에게 인계할 때는 사전에 반드시 연락을 취하겠습니다. \n \n 원에서는 부모가 희망하더라도 영유아를 혼자 귀가시키지 않습니다.',
);
</script>

<style scoped></style>
