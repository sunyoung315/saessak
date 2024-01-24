<template>
	<div>
		<div v-if="user === 'teacher'">
			<AlbumTeacherView />
		</div>
		<div v-else>
			<AlbumParentsView />
		</div>
		<RouterView />
	</div>
</template>

<script setup>
import AlbumParentsView from '@/components/album/AlbumParentsView.vue';
import AlbumTeacherView from '@/components/album/AlbumTeacherView.vue';

const user = 'teacher';
</script>

<style scoped></style>
