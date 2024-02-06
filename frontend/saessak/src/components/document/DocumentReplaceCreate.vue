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
				class="flex flex-col text-left whitespace-pre-line ml-48 mt-12 font-bold"
			>
				<div class="md:flex md:items-center mb-6">
					<div>
						<label
							class="block w-24 text-gray-500 font-bold mb-1 md:mb-0 pr-4"
							for="inline-full-name"
						>
							이름:
						</label>
					</div>
					<div>
						<input
							class="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-nav-navy"
							id="inline-full-name"
							type="text"
							v-model="kidName"
							readonly
						/>
					</div>
				</div>
				<div class="flex justify-start">
					<!-- DatePicker 시작-->
					<div class="flex items-center mb-6">
						<div>
							<label
								class="block w-24 text-gray-500 font-bold mb-1 md:mb-0 pr-4"
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
											class="datepicker-input text"
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
								class="block w-12 text-gray-500 ml-40 font-bold mb-1 md:mb-0 pr-4"
								for="inline-full-name"
							>
								시간:
							</label>
						</div>
						<div>
							<input
								class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-black leading-tight focus:outline-none focus:bg-white focus:border-nav-navy"
								id="inline-full-name"
								type="time"
								v-model="registReplacement.replacementTime"
							/>
						</div>
					</div>
				</div>
				<div class="flex items-center">
					<div class="flex items-center mb-6">
						<div>
							<label
								class="block w-24 text-gray-500 font-bold mb-1 md:mb-0 pr-4"
								for="inline-full-name"
							>
								보호자 성함:
							</label>
						</div>
						<div>
							<input
								class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-black leading-tight focus:outline-none focus:bg-white focus:border-nav-navy"
								id="inline-full-name"
								type="text"
								placeholder="보호자 성함을 입력해주세요."
								v-model="registReplacement.replacementName"
							/>
						</div>
					</div>
					<div class="md:flex md:items-center mb-6">
						<div>
							<label
								class="block w-24 text-gray-500 ml-32 font-bold mb-1 md:mb-0 pr-4"
								for="inline-full-name"
							>
								보호자 관계:
							</label>
						</div>
						<div>
							<input
								class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-black leading-tight focus:outline-none focus:bg-white focus:border-nav-navy"
								id="inline-full-name"
								type="text"
								placeholder="원아와의 관계를 입력해주세요."
								v-model="registReplacement.replacementRelationship"
							/>
						</div>
					</div>
				</div>
				<div class="flex items-center">
					<div class="flex items-center mb-6">
						<div>
							<label
								class="block w-24 text-gray-500 font-bold mb-1 md:mb-0 pr-4"
								for="inline-full-name"
							>
								귀가 방법:
							</label>
						</div>
						<div>
							<input
								class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-black leading-tight focus:outline-none focus:bg-white focus:border-nav-navy"
								id="inline-full-name"
								type="text"
								placeholder="귀가 방법을 입력해주세요."
								v-model="registReplacement.replacementVehicle"
							/>
						</div>
					</div>
					<div class="md:flex md:items-center mb-6">
						<div>
							<label
								class="block w-24 text-gray-500 ml-32 font-bold mb-1 md:mb-0 pr-4"
								for="inline-full-name"
							>
								비상 연락망:
							</label>
						</div>
						<div>
							<input
								class="bg-white appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-black leading-tight focus:outline-none focus:bg-white focus:border-nav-navy"
								id="inline-full-name"
								type="text"
								placeholder="비상 연락망을 입력해주세요."
								v-model="registReplacement.replacementNumber"
							/>
						</div>
					</div>
				</div>
			</div>
			<div class="text-wrap text-center text-xl whitespace-pre-line m-12">
				<p>{{ replaceContent }}</p>
				<p class="m-8 font-bold">금일 자녀의 귀가를 선생님께 의뢰합니다.</p>
			</div>

			<div class="flex justify-end">
				<div class="flex-col text-gray-700 text-xl font-bold m-8">
					<div>
						<h2>전자 서명:</h2>
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
import DocumentSignature from '@/components/document/DocumentSignature.vue';

const router = useRouter();
const replacementStore = useReplacementStore();

// 내 아이 id 조회
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
const kidId = loginStore.kidList[0].kidId;
const kidName = loginStore.kidList[0].kidName;

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
// console.log(registReplacement);

///////// date
watchEffect(() => {
	registReplacement.value.replacementDate = new Date(
		registReplacement.value.replacementDate,
	)
		.toISOString()
		.split('T')[0];
});

const selectAttribute = ref({ highlight: 'navy' });
///////////

const replacementSignature = ref();

// Btn
async function regist() {
	// 유효성 검사
	for (const field of fields) {
		if (!validateField(field.key, field.message)) {
			return;
		}
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
		// console.error('실패: ', error);
	}
}

function goBack() {
	router.go(-1);
}
// Btn

// 전자 서명 데이터 가져오기
const handleSignatureSaved = signature => {
	replacementSignature.value = signature;
	console.log('전자서명 가져옴?');
	console.log(replacementSignature.value);
};

///////////////////  유효성 검사
const fields = [
	{
		key: 'replacementDate',
		message: '대리인 귀가 날짜를 입력해주시기 바랍니다.',
	},
	{
		key: 'replacementTime',
		message: '대리인 귀가 시간을 입력해주시기 바랍니다.',
	},
	{ key: 'replacementName', message: '보호자 성함을 입력해주시기 바랍니다.' },
	{
		key: 'replacementRelationship',
		message: '원아와의 관계에 대해 입력해주시기 바랍니다.',
	},
	{
		key: 'replacementVehicle',
		message: '대리인 귀가 방법을 입력해주시기 바랍니다.',
	},
	{
		key: 'replacementNumber',
		message: '대리인 비상 연락망을 입력해주시기 바랍니다.',
	},
];

const validateField = (field, message) => {
	if (
		!registReplacement.value[field] ||
		!registReplacement.value[field].trim()
	) {
		alert(message);
		return false;
	}
	return true;
};

const validateSignature = () => {
	if (!(replacementSignature.value instanceof Blob)) {
		alert(
			'전자 서명을 완료해주시기 바랍니다. 서명 작성 후 저장 버튼을 누르셔야 등록이 완료됩니다.',
		);
		return false;
	}
	return true;
};
//////////////////

// 문구
const replaceContent = ref(
	'영유아의 귀가 시 위 보호자에게 인도하여 주세요. \n \n 위 보호자 이외의 다른 사람에게 인계할 때는 사전에 반드시 연락을 취하겠습니다. \n \n 원에서는 부모가 희망하더라도 영유아를 혼자 귀가시키지 않습니다.',
);
</script>

<style scoped></style>
