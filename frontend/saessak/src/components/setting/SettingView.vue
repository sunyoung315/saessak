<template>
	<div class="view-header">
		<div class="view-title-teacher">
			<span class="nav-title">우리반 관리</span>
			<button v-if="!newKid" @click="addOneRow" class="btn">아이 등록</button>
		</div>
	</div>
	<div class="view-frame">
		<div class="table-box overflow-auto">
			<table class="table">
				<thead class="table-head">
					<tr>
						<th scope="col" class="col">이름</th>
						<th scope="col" class="col-birthday">생년월일</th>
						<th scope="col" class="col-gender">성별</th>
						<th scope="col" class="col-photo">증명사진</th>
						<th scope="col" class="col-code">등록코드</th>
					</tr>
				</thead>
				<tbody>
					<tr v-if="newKid" class="one-row !border-b-2">
						<td class="col !px-1">
							<input
								type="text"
								class="input w-18"
								v-model="newKid.kidName"
								@input="emptyKidName = false"
								:class="{
									'!border-2 !border-red-500': emptyKidName,
									shake: shakeKidName,
								}"
								required
							/>
						</td>
						<td scop="col" class="col-birthday !px-1">
							<VDatePicker
								:select-attribute="selectAttribute"
								v-model="newKid.kidBirth"
								@click="emptyKidBirth = false"
							>
								<template #default="{ inputValue, inputEvents }">
									<div class="relative max-w-sm">
										<div
											class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
										>
											<svg
												class="w-4 h-4 text-gray-900 dark:text-gray-400"
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
											class="datepicker-input"
											:class="{
												'border-2 border-red-500': emptyKidBirth,
												shake: shakeKidBirth,
											}"
										/>
									</div>
								</template>
							</VDatePicker>
						</td>
						<td class="col-gender !px-1">
							<select
								id="gender-type"
								class="selection-input w-[13rem]"
								v-model="newKid.kidGender"
								@change="
									newKid.kidGender = $event.target.value;
									emptyKidGender = false;
								"
								:class="{
									'border-2 border-red-500': emptyKidGender,
									shake: shakeKidGender,
								}"
								required
							>
								<option value="M">남</option>
								<option value="F">여</option>
							</select>
						</td>
						<!-- 파일업로드  -->
						<td scop="col" class="col-photo !pl-1">
							<div class="flex justify-between">
								<label
									class="flex flex-col justify-center h-14 w-[14rem] border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
									:class="{
										'border-2 border-red-500': emptyKidProfile,
										shake: shakeKidProfile,
									}"
								>
									<div
										class="flex flex-col items-center justify-center"
										@dragover.prevent
										@drop="onDrop"
									>
										<input
											ref="imagefile"
											id="input"
											type="file"
											name="image"
											accept="image/*"
											class="hidden"
											@change="
												uploadImage($event);
												newKid.kidProfile = $event.target.value;
												emptyKidProfile = false;
											"
										/>
										<template v-if="!newKid.kidProfile">
											<p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
												<span class="font-semibold">Click to upload</span>
											</p>
											<p class="text-xs text-gray-500 dark:text-gray-400">
												SVG, PNG, JPG or GIF (MAX. 800x400px)
											</p>
										</template>
										<template v-else>
											<div class="font-bold">
												{{ newKid.kidProfile }}
											</div>
										</template>
									</div>
								</label>
							</div>
						</td>
						<td class="col-code !px-1">
							<div>
								<button class="btn my-2 ml-1 mr-2" @click="registKid($event)">
									등록
								</button>
								<button class="btn my-2 mx-2" @click="cancleRegsit">
									취소
								</button>
							</div>
						</td>
					</tr>
					<tr v-for="(kid, index) in kidList" :key="kid.kidId" class="one-row">
						<td scope="col" class="col">{{ kid.kidName }}</td>
						<td scope="col" class="col-birthday">{{ kid.kidBirthday }}</td>
						<td scope="col" class="col">
							{{ kid.kidGender == 'M' ? '남' : '여' }}
						</td>
						<td scope="col" class="col-photo">
							<img
								:src="kid.kidProfile"
								class="h-16 w-16 border border-gray-400 rounded"
								alt=""
							/>
						</td>
						<td scope="col" class="col-code">
							<div class="flex justify-between">
								<div class="flex items-center">
									<p>{{ decodeShow[index] ? kid.encoded : '**********' }}</p>
								</div>
								<button @click="toggleCode($event, index)" class="px-3">
									<template v-if="!decodeShow[index]">
										<svg
											xmlns="http://www.w3.org/2000/svg"
											width="20"
											height="12"
											viewBox="0 0 20 12"
											fill="none"
										>
											<path
												fill-rule="evenodd"
												clip-rule="evenodd"
												d="M10 0C12.5773 0 15.9107 2 20 6C15.9107 10 12.5773 12 10 12C7.42267 12 4.08934 10 0 6C4.08934 2 7.42267 0 10 0ZM10 2C8.41651 2 6.12903 3.20868 3.26355 5.70283L2.926 6L2.95433 6.02502C5.86439 8.61416 8.19959 9.91203 9.83348 9.99568L10 10C11.5835 10 13.871 8.79132 16.7364 6.29717L17.073 6L17.0457 5.97498C14.1356 3.38584 11.8004 2.08797 10.1665 2.00432L10 2ZM10 4C11.1046 4 12 4.89543 12 6C12 7.10457 11.1046 8 10 8C8.89543 8 8 7.10457 8 6C8 4.89543 8.89543 4 10 4Z"
												fill="#000000"
											/>
										</svg>
									</template>
									<template v-else>
										<svg
											xmlns="http://www.w3.org/2000/svg"
											width="20"
											height="16"
											viewBox="0 0 20 16"
											fill="none"
										>
											<path
												fill-rule="evenodd"
												clip-rule="evenodd"
												d="M17.7071 0.292893C17.3166 -0.0976311 16.6834 -0.0976311 16.2929 0.292893L13.5684 3.01736C12.2508 2.33912 11.0613 2 10 2C7.42267 2 4.08934 4 0 8C1.68265 9.6459 3.23731 10.9532 4.66397 11.9218L2.29289 14.2929L2.2097 14.3871C1.90468 14.7794 1.93241 15.3466 2.29289 15.7071C2.68342 16.0976 3.31658 16.0976 3.70711 15.7071L6.43158 12.9826C7.74924 13.6609 8.93871 14 10 14C12.5773 14 15.9107 12 20 8C18.3173 6.35411 16.7627 5.04683 15.336 4.07818L17.7071 1.70711L17.7903 1.6129C18.0953 1.22061 18.0676 0.653377 17.7071 0.292893ZM12.0718 4.51398C11.3589 4.20164 10.7222 4.03277 10.1665 4.00432L10 4C8.41651 4 6.12903 5.20868 3.26355 7.70283L2.926 8L2.95433 8.02502C4.09571 9.04053 5.14865 9.85739 6.10553 10.4803L8.06774 8.51804C8.02356 8.35282 8 8.17916 8 8C8 6.89543 8.89543 6 10 6C10.1792 6 10.3528 6.02356 10.518 6.06774L12.0718 4.51398ZM11.9323 7.48196L13.8945 5.51974C14.8514 6.14261 15.9043 6.95947 17.0457 7.97498L17.073 8L16.7364 8.29717C13.871 10.7913 11.5835 12 10 12L9.83348 11.9957C9.27783 11.9672 8.64108 11.7984 7.92819 11.486L9.48196 9.93226C9.64718 9.97644 9.82084 10 10 10C11.1046 10 12 9.10457 12 8C12 7.82084 11.9764 7.64718 11.9323 7.48196Z"
												fill="#000000"
											/>
										</svg>
									</template>
								</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { getClassKids, registKidInClass } from '@/api/user';

const selectAttribute = ref({
	highlight: 'gray',
});

onMounted(() => {
	getClassKids(
		({ data }) => {
			existKidList.value = data.data;
			decodeShow.value = Array(data.data.length).fill(false);
		},
		error => {
			console.log(error);
		},
	);
});

const cancleRegsit = () => {
	newKid.value = '';
	emptyKidName.value = false;
	emptyKidGender.value = false;
	emptyKidBirth.value = false;
	emptyKidProfile.value = false;

	shakeKidName.value = false;
	shakeKidGender.value = false;
	shakeKidBirth.value = false;
	shakeKidProfile.value = false;
};

const toggleCode = (event, index) => {
	// console.log(decodeShow.value)
	decodeShow.value[index] = !decodeShow.value[index];
};

const emptyKidName = ref(false);
const emptyKidGender = ref(false);
const emptyKidBirth = ref(false);
const emptyKidProfile = ref(false);

const shakeKidName = ref(false);
const shakeKidGender = ref(false);
const shakeKidBirth = ref(false);
const shakeKidProfile = ref(false);

// 흔들흔들 적용 함수
// date picker 적용안됨...
function dataValidate() {
	let isValidate = true;
	if (!image.value) {
		isValidate = false;
		shakeKidProfile.value = true;
		emptyKidProfile.value = true;
		setTimeout(() => {
			shakeKidProfile.value = false;
		}, 1000);
	}
	if (!transformed.value.kidName) {
		isValidate = false;
		shakeKidName.value = true;
		emptyKidName.value = true;
		setTimeout(() => {
			shakeKidName.value = false;
		}, 1000);
	}
	if (!transformed.value.kidGender) {
		isValidate = false;
		shakeKidGender.value = true;
		emptyKidGender.value = true;
		setTimeout(() => {
			shakeKidGender.value = false;
		}, 1000);
	}

	if (!transformed.value.kidBirth) {
		isValidate = false;
		shakeKidBirth.value = true;
		emptyKidBirth.value = true;
		setTimeout(() => {
			shakeKidBirth.value = false;
		}, 1000);
	} else {
		emptyKidBirth.value = false;
	}
	return isValidate;
}

const registKid = async event => {
	// validation check
	if (!dataValidate()) {
		return;
	}
	const formData = new FormData();
	formData.append('MultipartFile', image.value);
	formData.append('gender', transformed.value.kidGender);
	formData.append('kidName', transformed.value.kidName);
	formData.append('kidBirthday', transformed.value.kidBirth);

	registKidInClass(
		formData,
		response => {
			getClassKids(
				({ data }) => {
					// console.log(data)
					existKidList.value = data.data;
				},
				error => {
					console.log(error);
				},
			);
			image.value = '';
			newKid.value = '';
		},
		error => {
			console.log(error);
		},
	);
};

const image = ref(null);

let count = 0;
const addOneRow = () => {
	newKid.value = {
		kidIndex: count,
		kidName: '',
		kidGender: '',
		kidBirth: '',
		profileName: '',
	};
};

const decodeShow = ref([]);

const kidList = computed(() => {
	return existKidList.value.map(item => {
		return {
			...item,
			encoded: btoa(item.kidId + 'kid'),
			isOpen: false,
		};
	});
});

const transformed = computed(() => {
	return {
		...newKid.value,
		kidName : newKid.value.kidName.trim(),
		kidBirth: formatDate(newKid.value.kidBirth),
	};
});

const existKidList = ref([]);
const newKid = ref('');

function formatDate(date) {
	if (!date) return '';
	const year = date.getFullYear();
	const month = ('0' + (date.getMonth() + 1)).slice(-2);
	const day = ('0' + date.getDate()).slice(-2);
	return `${year}-${month}-${day}`;
}

const uploadImage = event => {
	const file = event.target.files[0];
	if (!file) return;
	newKid.value.kidProfile = file.name;
	image.value = file;
};
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
.view-title-teacher {
	@apply flex justify-between items-center;
}
.table-box {
	@apply relative overflow-x-auto min-h-screen m-3;
}
.table {
	@apply w-full text-base text-left rtl:text-right text-black;
}
.table-head {
	@apply bg-nav-gray bg-opacity-40 text-lg;
}
.col {
	@apply p-3 w-[20%];
}
.col-gender {
	@apply p-3 w-[20%];
}
.col-birthday {
	@apply p-3 w-[20%];
}
.col-photo {
	@apply p-3 w-[20%];
}
.col-code {
	@apply p-3 w-[20%];
}
.one-row {
	@apply bg-white border-b h-20;
}
</style>
