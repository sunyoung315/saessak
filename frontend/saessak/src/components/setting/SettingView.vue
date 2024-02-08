<template>
	<div class="view-frame">
		<div class="table-box">
			<div class="flex justify-between">
				<div class="flex items-center m-2">
					<div class="mr-4 text-lg text-black font-bold">설정</div>

				</div>

			</div>
			<table class="table">
				<thead class="table-head">
					<tr>
						<th scope="col" class="col">이름</th>
						<th scope="col" class="col-birthday">생년월일</th>
						<th scope="col" class="col-gender">성별</th>
						<th scope="col" class="col-photo">증명사진</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="kid in  existKidList" :key="kid.kidId">
						<td scope="col" class="col">{{ kid.kidName }}</td>
						<td scope="col" class="col-birthday">{{ kid.kidBirthday }}</td>
						<td scope="col" class="col">{{ kid.kidGender == 'M' ? '남' : '여' }}</td>
						<td scope="col" class="col-kidprofile"><img :src="kid.kidProfile" class="h-16 w-16" alt=""></td>
					</tr>
					<tr v-if="Object.keys(newKid).length !== 0">
						<td class="col">
							<input type="text" class="input w-18" v-model="newKid.kidName" required />
						</td>
						<td scop="col" class="col-birthday">
							<VDatePicker :select-attribute="selectAttribute" v-model="newKid.kidBirth">
								<template #default="{ inputValue, inputEvents }">
									<div class="relative max-w-sm">
										<div
											class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
											<svg class="w-4 h-4 text-gray-900 dark:text-gray-400" aria-hidden="true"
												xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
												<path
													d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
											</svg>
										</div>
										<input :value="inputValue" v-on="inputEvents" class="datepicker-input" />
									</div>
								</template>
							</VDatePicker>
						</td>
						<td>
							<select id="menu-type" class="selection-input w-20" v-model="newKid.kidGender" required>
								<option value="M">남</option>
								<option value="F">여</option>
							</select>
						</td>
						<!-- 파일업로드  -->
						<td>
							<div class="flex justify-between">

								<label
									class="flex flex-col justify-center h-14 w-3/5 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
									<div class="flex flex-col items-center justify-center" @dragover.prevent @drop="onDrop">
										<input ref="image" id="input" type="file" name="image" accept="image/*"
											class="hidden" @change="uploadImage($event, newKid.kidIndex)" />

										<p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
											<span class="font-semibold">Click to upload</span> or drag and drop
										</p>
										<p class="text-xs text-gray-500 dark:text-gray-400">
											SVG, PNG, JPG or GIF (MAX. 800x400px)
										</p>
									</div>
								</label>
								<div>{{ newKid.kidProfile }}</div>
								<div><button class="btn my-2 mx-2" @click="regsitKid($event, newKid.Kidindex)">등록</button>
								</div>
							</div>
						</td>
					</tr>

					<tr class="one-row h-2">
						<button v-if="Object.keys(newKid).length == 0" @click="addOneRow"
							class="text-center text-dark-navy text-lg font-bold m-7">
							+ 행 추가
						</button>
					</tr>
				</tbody>
			</table>
		</div>
		<div>데이터 테스트</div>
		<div>{{ transformedList }}</div>
	</div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { getClassKids } from '@/api/user'
import axios from 'axios';
onMounted(() => {
	getClassKids(({ data }) => {
		existKidList.value = data.data
	}, (error) => {
		console.log(error)
	}
	)
})

const regsitKid = (event) => {
	const formData = new FormData()

	console.log(obj)
	formData.append('file', image.value);
	formData.append('gender', obj.kidGender);
	formData.append('kidName', obj.kidName);
	formData.append('kidBirthday', obj.kidBirth);
	console.log(formData)
}

const image = ref(null)

let count = 0;
const addOneRow = () => {
	newKid.value = {
		kidIndex: count,
		kidName: "",
		kidGender: "",
		kidBirth: "",
		profileName: "",
	}

}

const transformedList = computed(() => {
	return {
		...newKid.value,
		kidBirth: formatDate(newKid.value.kidBirth)
	}

})

const existKidList = ref([])
const newKid = ref({})

function formatDate(date) {
	if (!date) return ""
	const year = date.getFullYear();
	const month = ('0' + (date.getMonth() + 1)).slice(-2);
	const day = ('0' + date.getDate()).slice(-2);
	return `${year}-${month}-${day}`;
}

const uploadImage = (event, index) => {

	const file = event.target.files[0]
	if (!file) return
	newKid.value.kidProfile = file.name
	image.value = file
}

</script>

<style scoped>
.table-box {
	@apply relative overflow-x-auto min-h-screen m-3;
}

.table {
	@apply w-full mt-3 text-base text-left rtl:text-right text-gray-500 dark:text-gray-400;
}

.table-head {
	@apply text-gray-700 bg-nav-navy bg-opacity-30 dark:bg-gray-700 dark:text-gray-400;
}

.col {
	@apply pl-3 w-[14%];
}

.col-gender {
	@apply p-3 w-[10%];
}

.col-birthday {
	@apply p-3 w-[20%];
}

.col-photo {
	@apply p-3 w-[60%];
}

.col-btn {
	@apply w-[5%] text-dark-navy font-bold;
}

.one-row {
	@apply bg-white border-b h-20;
}

.checkbox-frame {
	@apply pr-3 py-1 inline-block text-black;
}

.checkbox {
	@apply px-1;
}

.checkbox-label {
	@apply px-1 text-base;
}
</style>
