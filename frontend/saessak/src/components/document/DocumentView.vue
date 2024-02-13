<template>
	<div>
		<div class="view-header flex justify-between">
			<div class="flex items-center">
				<span class="nav-title">동의서</span>
				<div
					class="group-btn"
					v-if="
						route.name !== 'DocumentReplaceDetail' &&
						route.name !== 'DocumentAllergyDetail' &&
						path !== 'allery/create' &&
						path !== 'replacement/create'
					"
				>
					<button
						@click="documentClick()"
						class="group-btn-left-item"
						:class="
							isReplace
								? 'px-6 py-3 text-m font-bold text-dark-navy bg-white border-2 border-dark-navy rounded-s-lg hover:bg-dark-navy hover:text-white  '
								: 'px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-s-lg hover:bg-dark-navy hover:text-white  '
						"
					>
						귀가동의서
					</button>
					<button
						@click="documentClick()"
						class="group-btn-right-item"
						:class="
							!isReplace
								? 'px-6 py-3 text-m font-bold text-dark-navy bg-white border-2 border-dark-navy rounded-e-lg hover:bg-dark-navy hover:text-white'
								: 'px-6 py-3 text-m font-bold text-dark-navy bg-white border border-gray-200 rounded-e-lg hover:bg-dark-navy hover:text-white  '
						"
					>
						식품 알레르기 조사서
					</button>
				</div>
			</div>
			<div
				v-if="
					!isTeacher &&
					path !== 'allery/create' &&
					path !== 'replacement/create'
				"
				class="mt-10 ml-32 mr-6"
			>
				<button type="button" @click="createDocument()" class="btn m-0">
					작성
				</button>
			</div>
		</div>
		<router-view :loginStore="loginStore" :isReplace="isReplace" />
	</div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const router = useRouter();

const route = useRoute();
let loginStore = JSON.parse(localStorage.getItem('loginStore'));
let isTeacher = loginStore.isTeacher;

// boolean 설정, isReplace: 귀가동의서(T)/식품 알레르기 동의서(F) 버튼
const isReplace = ref(true);

// 동의서 버튼 동작
function documentClick() {
	isReplace.value = !isReplace.value;
}

const path = computed(() => {
	return route.path.substr(10);
});

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
</script>

<style scoped></style>
