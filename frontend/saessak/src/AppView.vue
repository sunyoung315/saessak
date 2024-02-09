<template>
	<TheHeader />
	<template v-if="loginStore.isLogin">
		<div class="flex">
			<TheNav />
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

const loginStore = JSON.parse(localStorage.getItem('loginStore'));
console.log(loginStore)
</script>

<style scoped></style>
