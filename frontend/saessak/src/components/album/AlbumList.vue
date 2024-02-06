<template>
	<div class="view-frame p-4">
		<div v-if="isTeacher">
			<AlbumListTeacher></AlbumListTeacher>
		</div>
		<div v-else>
			<AlbumListParent></AlbumListParent>
		</div>
	</div>
</template>

<script setup>
import AlbumListParent from '@/components/album/AlbumListParent.vue';
import AlbumListTeacher from '@/components/album/AlbumListTeacher.vue';

let loginStore = JSON.parse(localStorage.getItem('loginStore'));
loginStore = JSON.parse(localStorage.getItem('loginStore'));
let isTeacher = loginStore.isTeacher;
</script>

<style scoped></style>
