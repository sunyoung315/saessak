<template>
	<TheHeader @headerClick="headerClick" />
	<template v-if="loginStore.isLogin">
		<div class="flex">
			<TheNav :navData="headerData" @navClick="navClick" />
			<TheView />
		</div>
	</template>
	<template v-else>
		<TheView />
	</template>
	<TheFooter />
</template>

<script setup>
import TheHeader from '@/components/common/TheHeader.vue';
import TheNav from '@/components/common/TheNav.vue';
import TheView from '@/components/common/TheView.vue';
import TheFooter from '@/components/common/TheFooter.vue';
import { ref } from 'vue';

const loginStore = JSON.parse(localStorage.getItem('loginStore'));

const headerData = ref('');

const headerClick = data => {
	// console.log("emit 전달 받음")
	headerData.value = data;
	// console.log(headerData)
};

const navClick = data => {
	headerData.value = data;
};
</script>

<style scoped></style>
