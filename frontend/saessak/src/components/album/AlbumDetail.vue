<template>
	<div>
		<div class="flex flex-row justify-around">
			<div
				v-for="album in myKidAlbumDateList"
				:key="album.albumId"
				class="my-2"
			>
				<div v-for="file in album.fileResponseDtoList" :key="file.fileId">
					<input
						type="checkbox"
						:id="file.fileId"
						:value="`${file.fileId}`"
						class="hidden peer"
						v-model="checked"
					/>
					<label
						:for="file.fileId"
						class="inline-flex items-center justify-between w-full p-4 text-gray-500 bg-white border-4 border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 peer-checked:border-blue-600 hover:text-gray-600 dark:peer-checked:text-gray-300 peer-checked:text-gray-600 hover:bg-gray-50 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
					>
						<img
							class="album rounded-t-lg"
							:src="`${file.filePath}`"
							:for="file.fileId"
							alt="img"
						/>
					</label>
				</div>
			</div>
		</div>
		<br />
		<span>Check 이미지 : {{ checked }}</span>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useAlbumStore } from '@/store/album';
import { useRoute } from 'vue-router';

const route = useRoute();
const checked = ref([]);

const albumStore = useAlbumStore();

const myKidAlbumDateList = ref([]);

onMounted(async () => {
	// 아이별 앨범 조회 (선생님) // 번호: kidId
	// route.params.id = kidId
	await albumStore.getkidAlbumDateList(route.params.id);
	myKidAlbumDateList.value = albumStore.kidAlbumList;
});
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
