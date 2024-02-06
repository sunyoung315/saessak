<template>
	<div class="view-frame">
		<div class="table-box">
			<div class="flex justify-between">
				<div class="flex items-center m-2">
					<div class="mr-4 text-lg text-black font-bold">설정</div>

				</div>
				<button @click="createMenuList(menuList)" class="btn my-2 mx-2">
					등록
				</button>
			</div>
			<table class="table">
				<thead class="table-head">
					<tr>
						<th scope="col" class="col">이름</th>
						<th scope="col" class="col-birthday">생년월일</th>
						<th scope="col" class="col-photo">증명사진</th>
						<th scope="col" class="w-1/12"></th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="kid in  existKidList" :key="kid.kidId">
						<td scope="col" class="col-kidname">{{ kid.kidName }}</td>
						<td scope="col" class="col-birthday">{{ kid.kidName }}</td>
						<td scope="col" class="col-kidprofile">{{ kid.kidProfile }}</td>
					</tr>
					<tr v-for="newKid in newKidList" :key="newKid.kidId">
						<td scope="col" class="col-new-kid-name"></td>
					</tr>

					<tr class="one-row h-2">
						<button @click="addOneRow" class="text-center text-dark-navy text-lg font-bold m-7">
							+ 행 추가
						</button>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getClassKids } from '@/api/user'
onMounted(() => {
	getClassKids(({ data }) => {
		existKidList.value = data.data
	}, (error) => {
		console.log(error)
	}
	)
})
let count = 0;
const addOneRow = () => {
	newKidList.value.push({
		"kidId": count++,
		"kidName": "",
		"kidBirth": ""
	})

}

const existKidList = ref([])
const newKidList = ref([])



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
	@apply p-3 w-[14%];
}

.col-food {
	@apply p-3 w-[21%];
}

.col-allergy {
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
