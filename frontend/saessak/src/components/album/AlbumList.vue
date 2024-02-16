<template>
	<div class="view-frame p-4">
		<div v-if="props.loginStore.isTeacher">
			<AlbumListTeacher :showToggle="props.showToggle" />
		</div>
		<div v-else>
			<AlbumListParent
				:loginStore="loginStore"
				:showToggle="props.showToggle"
			/>
		</div>
	</div>
</template>

<script setup>
import AlbumListParent from '@/components/album/AlbumListParent.vue';
import AlbumListTeacher from '@/components/album/AlbumListTeacher.vue';

const props = defineProps({
	loginStore: Object,
	showToggle: Boolean,
});
</script>

<style scoped></style>
