<template>
	<div
		class="flowbit-modal fixed inset-0 flex items-center justify-center z-50"
		v-if="isOpen"
	>
		<div
			class="flowbit-modal__overlay absolute inset-0 bg-black opacity-50"
		></div>
		<div
			class="flowbit-modal__container bg-white w-full max-w-md m-8 md:m-0 rounded-lg shadow-lg z-50 overflow-y-auto"
		>
			<!-- Title & Close Btn -->
			<div
				class="flowbit-modal__header px-4 py-2 flex justify-between items-center"
			>
      <button type="button" @click="goDocument()"
					class="mt-9 mr-12 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
					작성
				</button>
				<button class="pr-5 pb-3 flowbit-modal__close" @click="closeModal">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						width="24"
						height="24"
						viewBox="0 0 24 24"
						fill="none"
					>
						<path
							fill-rule="evenodd"
							clip-rule="evenodd"
							d="M16.694 7.30602C17.102 7.71405 17.102 8.37559 16.694 8.78361L13.4783 11.9993L16.694 15.2164C17.102 15.6244 17.102 16.286 16.694 16.694C16.286 17.102 15.6244 17.102 15.2164 16.694L12.0007 13.4769L8.78361 16.694C8.37559 17.102 7.71405 17.102 7.30602 16.694C6.89799 16.286 6.89799 15.6244 7.30602 15.2164L10.5231 11.9993L7.30602 8.78361C6.89799 8.37559 6.89799 7.71405 7.30602 7.30602C7.71405 6.89799 8.37559 6.89799 8.78361 7.30602L12.0007 10.5217L15.2164 7.30602C15.6244 6.89799 16.286 6.89799 16.694 7.30602Z"
							fill="#7E84A3"
						/>
					</svg>
				</button>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

function goDocument() {
  router.push({ name: 'DocumentCreate' });
}

// modal
const isOpen = ref(false);

const openModal = () => {
	isOpen.value = true;
};

const closeModal = () => {
	isOpen.value = false;
};

defineExpose({ openModal });
// modal
</script>

<style scoped>
.flowbit-modal__container {
	width: 60rem;
	height: 20rem;
	max-width: none;
}
</style>
