<template>
	<div>
		<div class="flex justify-between">
			<div class="inline-flex rounded-md shadow-sm ml-6 mt-5">
				<button
					@click="replaceBtnClicked()"
					:class="
						isReplace
							? 'px-6 py-3 text-m font-bold text-dark-navy bg-white border-2 border-dark-navy rounded-s-lg hover:bg-dark-navy hover:text-white  '
							: 'px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-s-lg hover:bg-dark-navy hover:text-white  '
					"
				>
					귀가동의서
				</button>
				<button
					@click="foodBtnClicked()"
					:class="
						!isReplace
							? 'px-6 py-3 text-m font-bold text-dark-navy bg-white border-2 border-dark-navy rounded-e-lg hover:bg-dark-navy hover:text-white'
							: 'px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-e-lg hover:bg-dark-navy hover:text-white  '
					"
				>
					식품 알레르기 조사서
				</button>
			</div>
			<div v-if="!isTeacher">
				<button
					type="button"
					@click="createDocument()"
					class="mt-9 mr-12 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
				>
					작성
				</button>
			</div>
		</div>
		<div v-if="isTeacher">
			<DocumentListTeacher :isReplace="isReplace" />
		</div>
		<div v-else>
			<DocumentListParent :isReplace="isReplace" />
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import DocumentListTeacher from '@/components/document/DocumentListTeacher.vue';
import DocumentListParent from '@/components/document/DocumentListParent.vue';

const router = useRouter();

let loginStore = JSON.parse(localStorage.getItem('loginStore'));
let isTeacher = loginStore.isTeacher;

// boolean 설정, isReplace: 귀가동의서(T)/식품 알레르기 동의서(F) 버튼
const isReplace = ref(true);

// 동의서 버튼 동작
function replaceBtnClicked() {
	isReplace.value = true;
}
function foodBtnClicked() {
	isReplace.value = false;
}
function createDocument() {
	if (isReplace.value) {
		router.push({
			name: 'DocumentReplaceCreate',
		});
	} else {
		router.push({
			name: 'DocumentAllergyCreate',
		});
	}
}

// 버튼 끝
</script>

<style scoped></style>
