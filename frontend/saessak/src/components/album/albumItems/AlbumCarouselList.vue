<template>
	<div>
		<!-- 선생님 Carousel : 아이별-->
		<div v-if="user === 'teacher'">
			<Carousel :items-to-show="5" :wrap-around="true">
				<Slide v-for="album in albumList" :key="album.id">
					<div class="carousel__item">
						<img
							class="album"
							:src="`src/assets/${album.image}`"
							:for="album.id"
							alt="image"
						/>
					</div>
				</Slide>
				<template #addons>
					<Navigation />
				</template>
			</Carousel>
		</div>
		<!-- 부모님별 Carousel : 날짜별-->
		<div v-else>
			<Carousel :items-to-show="5" :wrap-around="true">
				<Slide v-for="album in albumList" :key="album.id">
					<div class="carousel__item">
						<img
							class="album"
							:src="`src/assets/${album.image}`"
							:for="album.id"
							alt="image"
						/>
					</div>
				</Slide>
				<template #addons>
					<Navigation />
				</template>
			</Carousel>
		</div>
	</div>
</template>

<script setup>
import { defineComponent } from 'vue';
import { Carousel, Navigation, Slide } from 'vue3-carousel';

// user
const user = 'teacher';

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

// 더미 데이터
const albumList = [
	{
		id: '사진1',
		name: '박나은',
		image: 'image1.png',
		date: '2022-01-02',
		checked: false,
	},
	{
		id: '사진2',
		name: '박나은',
		image: 'image2.png',
		date: '2022-02-07',
		checked: false,
	},
	{
		id: '사진3',
		name: '박나은',
		image: 'image3.png',
		date: '2022-03-02',
		checked: false,
	},
	{
		id: '사진4',
		name: '박건후',
		image: 'image4.png',
		date: '2022-03-11',
		checked: false,
	},
	{
		id: '사진5',
		name: '박나은',
		image: 'image5.png',
		date: '2022-07-02',
		checked: false,
	},
	{
		id: '사진6',
		name: '박건후',
		image: 'image6.png',
		date: '2022-08-04',
		checked: false,
	},
	{
		id: '사진7',
		name: '박건후',
		image: 'image7.png',
		date: '2022-09-16',
		checked: false,
	},
	{
		id: '사진8',
		name: '박건후',
		image: 'image8.png',
		date: '2022-11-25',
		checked: false,
	},
	{
		id: '사진9',
		name: '박건후',
		image: 'image9.png',
		date: '2022-12-08',
		checked: false,
	},
]; // 더미 데이터 끝
</script>

<style scoped>
.album {
	width: 300px;
	height: 250px;
}
</style>
