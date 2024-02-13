<template>
	<TheHeader />
	<div class="flex">
		<TheNav />
		<TheView />
	</div>
	<TheFooter />
</template>

<script setup>
import TheHeader from '@/components/common/TheHeader.vue';
import TheNav from '@/components/common/TheNav.vue';
import TheView from '@/components/common/TheView.vue';
import TheFooter from '@/components/common/TheFooter.vue';
</script>

<style scoped></style>
