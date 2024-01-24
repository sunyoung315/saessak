<template>
	<div>
		<span class="text-4xl m-5 font-extrabold inline-block"
			><RouterLink to="/album">앨범 - 부모님</RouterLink></span
		>
		<AlbumCarouselList />
	</div>
</template>

<script setup>
import AlbumCarouselList from '@/components/album/albumItems/AlbumCarouselList.vue';
</script>

<style scoped></style>
