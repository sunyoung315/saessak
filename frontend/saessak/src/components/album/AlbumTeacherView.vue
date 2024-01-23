<template>
	<div class="flex justify-between">
		<div class="flex justify-start">
			<span class="text-4xl m-5 font-extrabold inline-block"
				><RouterLink to="/album">앨범 - 선생님</RouterLink></span
			>
			<label class="relative inline-flex items-center me-5 cursor-pointer">
				<input
					type="checkbox"
					class="sr-only peer"
					checked
					v-model="showStatus"
				/>
				<div
					class="w-11 h-6 bg-gray-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-dark-navy peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-8 after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-dark-navy"
				></div>
				<span
					class="text-xl m-5 font-extrabold inline-block text-gray-900 dark:text-gray-300"
				>
					{{ showStatus ? '전체 보기' : '아이별 보기' }}</span
				>
			</label>
			<!-- DatePicker 시작-->
			<VDatePicker v-model="date">
				<template #default="{ inputValue, togglePopover }">
					<input class="px-3 py-2 mt-6 mr-6 border" :value="inputValue" />
					<button class="px-3 py-2" @click="togglePopover">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							viewBox="0 0 24 24"
							fill="none"
						>
							<path
								fill-rule="evenodd"
								clip-rule="evenodd"
								d="M16 2C16.5523 2 17 2.44772 17 3V4H20C21.1046 4 22 4.89543 22 6V20C22 21.1046 21.1046 22 20 22H4C2.89543 22 2 21.1046 2 20V6C2 4.89543 2.89543 4 4 4H7V3C7 2.44772 7.44772 2 8 2C8.55228 2 9 2.44772 9 3V4H15V3C15 2.44772 15.4477 2 16 2ZM20 11H4V20H20V11ZM7 6H4V9H20V6H17V7C17 7.55228 16.5523 8 16 8C15.4477 8 15 7.55228 15 7V6H9V7C9 7.55228 8.55228 8 8 8C7.44772 8 7 7.55228 7 7V6Z"
								fill="#000000"
							/>
						</svg>
					</button>
				</template>
			</VDatePicker>
			<!-- DatePicker 끝-->
		</div>
		<div class="flex justify-end">
			<button
				type="button"
				@click="registAlbum()"
				class="mt-6 mr-6 text-white hover:text-dark-navy border border-dark-navy bg-dark-navy hover:bg-white focus:outline-none font-medium rounded-lg text-sm px-3 py-1.5 text-center me-2 mb-2"
			>
				추가
			</button>
		</div>
	</div>
	<div v-if="showStatus === true">
		<AlbumCardList />
	</div>
	<div v-else>
		<!-- 반복문 써서 아이별 Carousel 구현 -->
		<!-- <AlbumCarouselList /> -->
		<div v-for="kid in kidList" :key="kid.kidName">
			<AlbumCarouselList :kid="kid.kidName" />
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import AlbumCardList from '@/components/album/albumItems/AlbumCardList.vue';
import AlbumCarouselList from '@/components/album/albumItems/AlbumCarouselList.vue';

// showStatus: true = all, false = 아이별
const showStatus = ref(false);

// datePicker
const date = ref(new Date());

// 아이 리스트 더미데이터
const kidList = [
	{
		kidId: 1,
		kidName: '박나은',
		kidBirth: '2014-01-12',
		kidAllergy: '1/2/3/4/5',
		kidProfile: 'https://s3url', // s3 url
		kidAllergySignature: 'String',
		kidInviteLink: 'String',
		kidAllergyCheck: true,
		kidAllergyDate: '2024-01-14',
	},
	{
		kidId: 2,
		kidName: '박건후',
		kidBirth: '2014-01-12',
		kidAllergy: '1/2/3/4/5',
		kidProfile: 'https://s3url', // s3 url
		kidAllergySignature: 'String',
		kidInviteLink: 'String',
		kidAllergyCheck: true,
		kidAllergyDate: '2024-01-14',
	},
]; // 더미 데이터 끝
</script>

<style scoped></style>
