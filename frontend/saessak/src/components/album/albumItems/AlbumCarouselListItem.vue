<template>
	<div>
		<!-- {{ recentAlbumList }} -->
		<div v-if="isTeacher">
			<div v-for="kid in recentAlbumList" :key="kid.kidId">
				<div v-if="kid.albumResponseDto">
					<img src="@/assets/film.png" alt="필름" />
					<Carousel
						:items-to-show="5"
						:wrap-around="true"
						v-if="kid.albumResponseDto"
					>
						<Slide
							v-for="file in kid.albumResponseDto.fileResponseDtoList"
							:key="file.fileId"
							@click="goDetail(kid.kidId)"
						>
							<div class="carousel__item">
								<img
									class="album"
									:src="`${file.filePath}`"
									:for="kid.albumResponseDto.albumId"
									alt="image"
								/>
							</div>
						</Slide>
						<template #addons>
							<Navigation />
						</template>
					</Carousel>
					<img src="@/assets/film.png" alt="필름" />
					<div class="flex justify-between">
						<button
							class="bg-nav-green m-4 text-black font-bold py-2 px-4 rounded-full"
						>
							{{ kid.kidName }}
						</button>
						<span class="m-4 text-xl font-bold">→ 전체 조회</span>
					</div>
				</div>
			</div>
		</div>
		<!-- 부모님별 Carousel : 날짜별-->
		<div v-else>
			<div v-for="album in albumDateAllList" :key="album.albumId">
				<Carousel :items-to-show="5" :wrap-around="true">
					<Slide
						v-for="file in album.fileResponseDtoList"
						:key="file.fileId"
						@click="goDetail"
					>
						<div class="carousel__item">
							<img
								class="album"
								:src="`src/assets/${file.image}`"
								:for="album.albumId"
								alt="image"
							/>
						</div>
					</Slide>
					<template #addons>
						<Navigation />
					</template>
				</Carousel>
				<div class="flex justify-between">
					<button
						class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
					>
						{{ album.albumId }}
					</button>
					<!-- <span>{{ album.albumId }}</span> -->
					<span>전체 조회</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, defineComponent, defineProps, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Carousel, Navigation, Slide } from 'vue3-carousel';
import { useAlbumStore } from '@/store/album';

// user
const router = useRouter();
const isTeacher = ref(true);

const albumStore = useAlbumStore();

const myKidAlbumDateList = ref([]);
const albumDateAllList = ref([]);
const recentAlbumList = ref([]);

onMounted(async () => {
	// 내 아이 날짜별 앨범 조회 (학부모) // 번호: kidId
	// await albumStore.getkidAlbumDateList(1);
	// myKidAlbumDateList.value = albumStore.myKidAlbumDateList;
	// 반 날짜별 전체 조회 (학부모) // 번호: classroom
	// await albumStore.getAlbumDateAllList(2);
	// albumDateAllList.value = albumStore.albumDateAllList;
	// 반 아이들 최신 앨범 리스트 조회 (선생님), 번호: classRoomId
	await albumStore.getRecentAlbumList(2);
	recentAlbumList.value = albumStore.recentAlbumList;
});

const props = defineProps({
	kid: {
		type: String,
	},
});

// carousel 시작
import 'vue3-carousel/dist/carousel.css';
defineComponent({
	name: 'WrapAround',
	components: {
		Carousel,
		Slide,
		Navigation,
	},
});
// carousel 끝

function goDetail(kidId) {
	router.push({
		name: 'AlbumDetail',
		params: { id: kidId },
	});
}
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
