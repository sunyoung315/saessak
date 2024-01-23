<template>
	<div>
		<!--Teacher Version -->
		<div v-if="isTeacher" class="container p-6 flex items-center">
			<div>
				<span class="text-4xl mt-10 ml-14 mb-3 mr-5 font-extrabold inline-block">동의서 - 선생님</span>
			</div>


		</div>

		<!-- Parents Version -->
		<div v-else class="container p-6">
			<div class="flex justify-between items-center">
				<span class="text-4xl mt-10 ml-14 mb-3 font-extrabold inline-block">동의서 - 학부모</span>
				<button type="button" @click="createModal.openModal"
					class="mt-9 mr-12 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
					작성
				</button>
			</div>
		</div>
		<documentCreateModal ref="createModal" />
		<router-view></router-view>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import documentCreateModal from '@/components/document/documentCreateModal.vue';

const router = useRouter();
const isTeacher = ref(false);

const createModal = ref();

	// router.push({ name: 'DocumentCreate' });
</script>

<style scoped></style>
