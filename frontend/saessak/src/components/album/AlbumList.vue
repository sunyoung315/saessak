<template>
	<div class="container p-6 flex items-center">
		<div v-if="showStatus === true">
			<AlbumCardListItem />
		</div>
		<div v-else>
			<div>
				<AlbumCarouselListItem />
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import AlbumCardListItem from '@/components/album/albumItems/AlbumCardListItem.vue';
import AlbumCarouselListItem from '@/components/album/albumItems/AlbumCarouselListItem.vue';

// showStatus: true = all, false = 아이별

const props = defineProps({
	showStatus: Boolean,
	isTeacher: Boolean,
});
</script>

<style scoped></style>
