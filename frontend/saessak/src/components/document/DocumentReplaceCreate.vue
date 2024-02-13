<template>
	<div>
		<div class="view-frame p-1.5">
			<div class="flex justify-end items-center mb-10">
				<button
					type="button"
					@click.prevent="regist()"
					class="btn mt-7 mr-4 mb-3"
				>
					등록
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
					class="flex flex-col text-left mt-12 font-bold justify-center w-[65%]"
				>
					<div class="md:flex md:items-center mb-6">
						<div>
							<label
								class="block w-24 text-black font-bold mb-1 md:mb-0 pr-4 text-lg"
								for="inline-full-name"
							>
								이름:
							</label>
						</div>
						<div>
							<input
								class="bg-gray-200 input w-56"
								id="inline-full-name"
								type="text"
								v-model="kidName"
								readonly
							/>
						</div>
					</div>
					<div class="flex items-center justify-between">
						<!-- DatePicker 시작-->
						<div class="flex items-center mb-6">
							<div>
								<label
									class="block w-24 text-black font-bold md:mb-0 text-black text-lg"
									for="inline-full-name"
								>
									날짜:
								</label>
							</div>
							<div>
								<VDatePicker
									v-model="registReplacement.replacementDate"
									:select-attribute="selectAttribute"
								>
									<template #default="{ inputValue, inputEvents }">
										<div class="relative max-w-sm">
											<div
												class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
											>
												<svg
													class="w-4 h-4 text-gray-900"
													aria-hidden="true"
													xmlns="http://www.w3.org/2000/svg"
													fill="currentColor"
													viewBox="0 0 20 20"
												>
													<path
														d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"
													/>
												</svg>
											</div>
											<input
												:value="inputValue"
												v-on="inputEvents"
												class="datepicker-input w-56"
											/>
										</div>
									</template>
								</VDatePicker>
							</div>
						</div>
						<!-- DatePicker 끝-->
						<div class="flex items-center mb-6">
							<div>
								<label
									class="block w-24 font-bold mb-1 md:mb-0 pr-4 text-black text-lg"
									for="inline-full-name"
								>
									시간:
								</label>
							</div>
							<div>
								<input
									class="input w-56"
									id="inline-full-name"
									type="time"
									v-model="registReplacement.replacementTime"
									:class="{
										'border-2 border-red-500': emptyReplacementTime,
										shake: shakeReplacementTime,
									}"
								/>
							</div>
						</div>
					</div>
					<div class="flex items-center justify-between">
						<div class="flex items-center mb-6">
							<div>
								<label
									class="block w-24 text-black text-lg font-bold mb-1 md:mb-0 pr-4"
									for="inline-full-name"
								>
									보호자 성함:
								</label>
							</div>
							<div>
								<input
									class="input w-56"
									id="inline-full-name"
									type="text"
									placeholder="보호자 성함을 입력해주세요."
									v-model="registReplacement.replacementName"
									:class="{
										'border-2 border-red-500': emptyReplacementName,
										shake: shakeReplacementName,
									}"
								/>
							</div>
						</div>
						<div class="md:flex md:items-center mb-6">
							<div>
								<label
									class="block w-24 text-black text-lg font-bold mb-1 md:mb-0 pr-4"
									for="inline-full-name"
								>
									보호자 관계:
								</label>
							</div>
							<div>
								<input
									class="input w-56"
									id="inline-full-name"
									type="text"
									placeholder="원아와의 관계를 입력해주세요."
									v-model="registReplacement.replacementRelationship"
									:class="{
										'border-2 border-red-500': emptyReplacementRelationship,
										shake: shakeReplacementRelationship,
									}"
								/>
							</div>
						</div>
					</div>
					<div class="flex items-center justify-between">
						<div class="flex items-center mb-6">
							<div>
								<label
									class="block w-24 text-black text-lg font-bold mb-1 md:mb-0 pr-4"
									for="inline-full-name"
								>
									귀가 방법:
								</label>
							</div>
							<div>
								<input
									class="input w-56"
									id="inline-full-name"
									type="text"
									placeholder="귀가 방법을 입력해주세요."
									v-model="registReplacement.replacementVehicle"
									:class="{
										'border-2 border-red-500': emptyReplacementVehicle,
										shake: shakeReplacementVehicle,
									}"
								/>
							</div>
						</div>
						<div class="md:flex md:items-center mb-6">
							<div>
								<label
									class="block w-24 text-black text-lg font-bold mb-1 md:mb-0 pr-4"
									for="inline-full-name"
								>
									비상 연락망:
								</label>
							</div>
							<div>
								<input
									class="input w-56"
									id="inline-full-name"
									type="text"
									placeholder="비상 연락망을 입력해주세요."
									v-model="registReplacement.replacementNumber"
									:class="{
										'border-2 border-red-500': emptyReplacementNumber,
										shake: shakeReplacementNumber,
									}"
								/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="text-wrap text-center text-lg whitespace-pre-line m-12">
				<p>{{ replaceContent }}</p>
				<p class="m-8 font-bold">금일 자녀의 귀가를 선생님께 의뢰합니다.</p>
			</div>

			<div class="flex justify-end">
				<div class="flex-col text-black text-lg font-bold m-8">
					<div>
						<h2 class="ml-2 mb-1 text-lg">전자 서명:</h2>
						<document-signature @signature-saved="handleSignatureSaved" />
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import { useReplacementStore } from '@/store/replacement';
import Swal from 'sweetalert2';
import DocumentSignature from '@/components/document/DocumentSignature.vue';

const router = useRouter();
const replacementStore = useReplacementStore();

// 내 아이 id 조회
const props = defineProps({
	loginStore: Object,
});
const kidId = props.loginStore.kidList[0].kidId;
const kidName = props.loginStore.kidList[0].kidName;
const today = ref(`${new Date().toISOString().split('T')[0]}`);

// 전달할 데이터
const registReplacement = ref({
	kidId: kidId,
	replacementDate: today,
	replacementTime: '',
	replacementVehicle: '',
	replacementRelationship: '',
	replacementNumber: '',
	replacementName: '',
});

///////// date
const selectAttribute = ref({ highlight: 'navy' });
watchEffect(() => {
	registReplacement.value.replacementDate = new Date(
		registReplacement.value.replacementDate,
	)
		.toISOString()
		.split('T')[0];
});

// 전자서명
const replacementSignature = ref();
const isButtonClicked = ref(false);
// Btn
async function regist() {
	isButtonClicked.value = true;
	// 유효성 검사
	if (checkEmptyFields()) {
		return;
	}

	if (!validateSignature()) {
		return;
	}
	// 유효성 검사 끝
	try {
		// 전자서명 제외 입력
		await replacementStore.PostRegistReplacement(registReplacement.value);
		console.log('전자서명 입력 후 ' + replacementStore.registReplacementId);
		// 전자서명 입력
		const formData = new FormData();
		formData.append('replacementId', replacementStore.registReplacementId);
		formData.append(
			'signFile',
			new File(
				[replacementSignature.value],
				'sign' + replacementStore.registReplacementId + '.png',
				{ type: 'image/png' },
			),
		);
		await replacementStore.PostRegistFileReplacement(formData);
		router.push({
			name: 'DocumentList',
		});
	} catch (error) {
		console.error('실패: ', error);
	}
}

function goList() {
	router.push({
		name: 'DocumentList',
	});
}
// Btn

// 전자 서명 데이터 가져오기
const handleSignatureSaved = signature => {
	replacementSignature.value = signature;
};

const validateSignature = () => {
	if (!(replacementSignature.value instanceof Blob)) {
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
//////////////////

// 널값 처리를 위한 ref 추가
const emptyReplacementDate = ref(false);
const emptyReplacementTime = ref(false);
const emptyReplacementName = ref(false);
const emptyReplacementRelationship = ref(false);
const emptyReplacementVehicle = ref(false);
const emptyReplacementNumber = ref(false);

const shakeReplacementDate = ref(false);
const shakeReplacementTime = ref(false);
const shakeReplacementName = ref(false);
const shakeReplacementRelationship = ref(false);
const shakeReplacementVehicle = ref(false);
const shakeReplacementNumber = ref(false);

// 널값 처리 함수
const checkEmptyFields = () => {
	let hasEmptyFields = false;
	if (!registReplacement.value.replacementDate.trim()) {
		emptyReplacementDate.value = true;
		shakeReplacementDate.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeReplacementDate.value = false;
		}, 1000);
	}
	if (!registReplacement.value.replacementTime.trim()) {
		emptyReplacementTime.value = true;
		shakeReplacementTime.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeReplacementTime.value = false;
		}, 1000);
	}
	if (!registReplacement.value.replacementName.trim()) {
		emptyReplacementName.value = true;
		shakeReplacementName.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeReplacementName.value = false;
		}, 1000);
	}
	if (!registReplacement.value.replacementRelationship.trim()) {
		emptyReplacementRelationship.value = true;
		shakeReplacementRelationship.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeReplacementRelationship.value = false;
		}, 1000);
	}
	if (!registReplacement.value.replacementVehicle.trim()) {
		emptyReplacementVehicle.value = true;
		shakeReplacementVehicle.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeReplacementVehicle.value = false;
		}, 1000);
	}
	if (!registReplacement.value.replacementNumber.trim()) {
		emptyReplacementNumber.value = true;
		shakeReplacementNumber.value = true;
		hasEmptyFields = true;
		setTimeout(() => {
			shakeReplacementNumber.value = false;
		}, 1000);
	}
	return hasEmptyFields;
};

// 문구
const replaceContent = ref(
	'영유아의 귀가 시 위 보호자에게 인도하여 주세요. \n \n 위 보호자 이외의 다른 사람에게 인계할 때는 사전에 반드시 연락을 취하겠습니다. \n \n 원에서는 부모가 희망하더라도 영유아를 혼자 귀가시키지 않습니다.',
);
</script>

<style scoped>
@keyframes shake {
	0% {
		transform: translateX(0px);
	}

	25% {
		transform: translateX(-2px);
	}

	50% {
		transform: translateX(0px);
	}

	75% {
		transform: translateX(2px);
	}

	100% {
		transform: translateX(0px);
	}
}

.shake {
	animation: shake 0.2s;
	animation-iteration-count: 3;
}
</style>
