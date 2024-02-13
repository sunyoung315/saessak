<template>
	<div class="view-frame p-4">
		<div v-if="props.loginStore.isTeacher">
			<DocumentListTeacher :isReplace="props.isReplace" />
		</div>
		<div v-else>
			<DocumentListParent :isReplace="props.isReplace" />
		</div>
	</div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import DocumentListTeacher from '@/components/document/DocumentListTeacher.vue';
import DocumentListParent from '@/components/document/DocumentListParent.vue';

const router = useRouter();

const props = defineProps({
	loginStore: Object,
	isReplace: Boolean,
});

// 버튼 끝
</script>

<style scoped></style>
