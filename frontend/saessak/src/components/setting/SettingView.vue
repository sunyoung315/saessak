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
						<th scope="col" class="col-code">등록코드</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(kid,index) in  kidList" :key="kid.kidId">
						<td scope="col" class="col">{{ kid.kidName }}</td>
						<td scope="col" class="col-birthday">{{ kid.kidBirthday }}</td>
						<td scope="col" class="col">{{ kid.kidGender == 'M' ? '남' : '여' }}</td>
						<td scope="col" class="col-kidprofile"><img :src="kid.kidProfile" class="h-16 w-16" alt=""></td>
						<td scope="col" class="col-kidcode"><div class="flex justify-between"><p >{{ decodeShow[index]? kid.encoded : "" }}</p> <button class="btn my-2 mx-2" @click="toggleCode($event, index)">{{decodeShow[index] ? "숨기기" : "확 인"}}</button></div></td>
					</tr>
					<tr v-if="newKid">
						<td class="col">
							<input type="text" class="input w-18" v-model.lazy="newKid.kidName" required />
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
								<option value="" disabled selected>성별</option>
								<option value="M">남</option>
								<option value="F">여</option>
							</select>
						</td>
						<!-- 파일업로드  -->
						<td colspan="2">
							<div class="flex justify-between">

								<label
									class="flex flex-col justify-center h-14 w-3/5 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
									<div class="flex flex-col items-center justify-center" @dragover.prevent @drop="onDrop">
										<input ref="imagefile" id="input" type="file" name="image" accept="image/*"
											class="hidden" @change="uploadImage($event)" />

										<p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
											<span class="font-semibold">Click to upload</span> or drag and drop
										</p>
										<p class="text-xs text-gray-500 dark:text-gray-400">
											SVG, PNG, JPG or GIF (MAX. 800x400px)
										</p>
									</div>
								</label>
								<div>{{ newKid.kidProfile }}</div>
								<div><button class="btn my-2 mx-2" @click="regsistKidinClass($event)">등록</button>
									<button class="btn my-2 mx-2" @click="cancleRegsit">취소</button>
								</div>
							</div>
						</td>
					</tr>

				</tbody>
			</table>
			<div class="one-row h-2 flex justify-end">
				<button v-if="!newKid" @click="addOneRow" class="text-center text-dark-navy text-lg font-bold m-7">
					아이등록
				</button>
			</div>
		</div>

	</div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { getClassKids } from '@/api/user'
import axios from 'axios';
onMounted(() => {
	getClassKids(({ data }) => {
		existKidList.value = data.data
		decodeShow.value = Array(data.data.length).fill(false)

	}, (error) => {
		console.log(error)
	}
	)
})

const cancleRegsit = () => {
	newKid.value = ""
}


const toggleCode = (event, index) => {
	// console.log(decodeShow.value)
	decodeShow.value[index] = !decodeShow.value[index]
}

function dataValidate() {
	if (!image.value) return false;
	if (!transformed.value.kidName) return false
	if (!transformed.value.kidGender) return false
	if (!transformed.value.kidBirth) return false
	return true
}

const regsistKidinClass = async (event) => {

	// validation check
	if( !dataValidate() ) return
	
	const formData = new FormData()
	formData.append('MultipartFile', image.value);
	formData.append('gender', transformed.value.kidGender)
	formData.append('kidName', transformed.value.kidName)
	formData.append('kidBirthday', transformed.value.kidBirth)

	axios.post("https://i10a706.p.ssafy.io/api/user/kid/regist", formData, {
		headers: {
			'Content-Type': 'multipart/form-data',
			Authorization: 'Bearer ' + localStorage.getItem("accessToken"),
		}
	}).then((response) => {
		getClassKids(({ data }) => {
			// console.log(data)
			existKidList.value = data.data
		}, (error) => {
			console.log(error)
		})
		image.value = ""
		newKid.value = ""
	}
		// console.log(response)

	).catch((error) => {
		console.log(error)
	})
	// console.log(formData)
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

const decodeShow = ref([])

const kidList = computed( ()=> {
	return existKidList.value.map(item => {
		return {
			...item, 
			encoded: btoa(item.kidId + "kid"),
			isOpen : false
		}
	})
})

const transformed = computed(() => {
	return {
		...newKid.value,
		kidBirth: formatDate(newKid.value.kidBirth)
	}

})

const existKidList = ref([])
const newKid = ref("")

function formatDate(date) {
	if (!date) return ""
	const year = date.getFullYear();
	const month = ('0' + (date.getMonth() + 1)).slice(-2);
	const day = ('0' + date.getDate()).slice(-2);
	return `${year}-${month}-${day}`;
}

const uploadImage = (event) => {

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
	@apply p-3 w-[20%];
}
.col-code {
	@apply p-3 w-[30%]
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
