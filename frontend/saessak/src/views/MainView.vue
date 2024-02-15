<template>
	<!-- 로그인 전 -->
	<div v-if="!isLogin">
		<!-- 서비스 소개 -->
		<div class="mb-20 bg-nav-yellow h-[85vh]">
			<!-- <div class="mb-20 bg-[url('/main-page.jpg')] bg-cover h-screen"> -->
			<img
				src="/main/logo-bubble.png"
				alt=""
				class="absolute right-[14rem] top-[23rem] transform translate-y-[-52%] w-[40rem] -rotate-12 opacity-65"
			/>
			<div class="animate-bounce h-10">
				<div
					class="absolute right-[21.5rem] top-[19rem] transform translate-y-[-70%] text-[8rem] font-bold"
				>
					새싹일기
				</div>
				<div
					class="absolute right-[23.7rem] top-[19rem] transform translate-y-[35%] text-[2rem]"
				>
					어린이집 커뮤니케이션 플랫폼
				</div>
			</div>
			<div class="image-container">
				<img
					class="w-[46rem] top-[6rem] left-36"
					:class="{ 'image-visible': isHandUpRef }"
					src="/main/hands-up.png"
				/>
				<img
					class="w-[46rem] top-[6rem] left-36"
					:class="{ 'image-visible': !isHandUpRef }"
					src="/main/hands-down.png"
				/>
			</div>
		</div>
		<!-- 알림장 -->
		<div
			data-aos="fade-up"
			data-aos-duration="1500"
			class="flex justify-center items-center h-[65vh] mb-12"
		>
			<div class="w-1/2 flex items-center justify-center">
				<img class="h-[55vh]" src="/main/diary.png" alt="img" />
			</div>
			<div
				class="w-1/2 flex flex-col basis-1/2 text-center justify-center items-center"
			>
				<div class="text-3xl font-bold">{{ contents[0].title }}</div>
				<span class="text-xl whitespace-pre-line mt-8">{{
					contents[0].content
				}}</span>
			</div>
		</div>
		<!-- 앨범 -->
		<div
			data-aos="fade-up"
			data-aos-duration="1500"
			class="flex justify-center items-center h-[65vh] bg-nav-yellow bg-opacity-70"
		>
			<div class="flex flex-col basis-1/2 text-center items-center">
				<div class="text-3xl font-bold">{{ contents[1].title }}</div>
				<span class="text-xl whitespace-pre-line mt-8">{{
					contents[1].content
				}}</span>
			</div>
			<div class="w-1/2 flex items-center justify-center">
				<img class="px-10" src="/main/album.png" alt="img" />
			</div>
		</div>
		<!-- 화상 채팅 -->
		<div
			data-aos="fade-up"
			data-aos-duration="1500"
			class="flex justify-center items-center h-[65vh]"
		>
			<div class="w-1/2 flex items-center justify-center">
				<img class="h-[50vh]" src="/main/face-chat.png" alt="img" />
			</div>
			<div
				class="w-1/2 flex flex-col basis-1/2 text-center justify-center items-center"
			>
				<div class="text-3xl font-bold">{{ contents[3].title }}</div>
				<span class="text-xl whitespace-pre-line mt-8">{{
					contents[3].content
				}}</span>
			</div>
		</div>
		<!-- 문서 열람 -->
		<div
			data-aos="fade-up"
			data-aos-duration="1500"
			class="flex items-center h-[65vh] bg-nav-yellow bg-opacity-70"
		>
			<div
				class="w-1/2 flex flex-col basis-1/2 text-center justify-center items-center"
			>
				<div class="text-3xl font-bold">
					{{ contents[2].title }}
				</div>
				<span class="text-xl whitespace-pre-line mt-8">{{
					contents[2].content
				}}</span>
			</div>
			<div class="w-1/2 flex items-center justify-center">
				<img class="h-[50vh]" src="/main/document.png" alt="img" />
			</div>
		</div>
	</div>
	<!-- 로그인 후 -->
	<div v-else>
		<div v-if="isTeacher">
			<MainTeacherVue :loginStore="loginStore" />
		</div>
		<div v-else>
			<MainParentVue :loginStore="loginStore" />
		</div>
	</div>
</template>

<script setup>
import MainTeacherVue from '@/views/MainTeacherView.vue';
import MainParentVue from '@/views/MainParentView.vue';

import { ref, onMounted, onUnmounted } from 'vue';

const isHandUpRef = ref(true);

let intervalId = null;

const toggleHand = () => {
	isHandUpRef.value = !isHandUpRef.value;
};

onMounted(() => {
	intervalId = setInterval(toggleHand, 500);
});

onUnmounted(() => {
	clearInterval(intervalId);
});

const loginStore = JSON.parse(localStorage.getItem('loginStore'));
const isLogin = loginStore.isLogin;
let isTeacher;
if (isLogin) {
	isTeacher = loginStore.isTeacher;
}

// const title = '우리 아이가 잘 생활하고 있을까요...';
// const titleContent =
// 	'안녕하세요. [ 새싹일기 ] 입니다. \n 우리 아이의 어린이집 생활을 더 가까이에서 공유하고 싶은 학부모 여러분을 위한 특별한 서비스입니다. \n [ 새싹일기 ] 는 우리 아이의 소중한 순간들을 담아 학부모와 공유하는 플랫폼입니다.';
const contents = [
	{
		title: '알림장',
		content:
			'어린이집의 알림장을 통해 아이의 일상을 쉽게 열람할 수 있습니다. \n 건강일지를 통한 시각화된 성장 기록과, 특정 기간 동안의 알림장 요약 리포트 기능을 통해 \n 학부모 여러분의 편의를 최우선으로 생각합니다. \n 함께 성장하는 기쁨을 [ 새싹일기 ] 에서 느껴보세요.',
	},
	{
		title: '앨범',
		content:
			'우리 아이들의 소중한 순간을 놓치지 않고 더욱 풍부하게 담을 수 있는 새로운 기능이 등장했습니다! \n 학부모 여러분이 더 편리하게 어린이집 생활을 체험하실 수 있도록 \n 사진 필터링 및 특별 기능을 소개합니다. \n 사진 필터링을 통해 해당 태그된 아이의 사진첩을 자동으로 만들어줍니다. \n 간편한 사용법으로 원하는 순간을 찾아가세요. 아이의 성장을 함께 감상해보세요.',
	},
	{
		title: '문서 열람',
		content:
			'어린이의 귀가의뢰와 알러지에 대한 관리를 간편하게 해결합니다. \n 부모님은 손쉽게 아이의 건강에 관련된 의뢰서를 작성할 수 있습니다. \n 선생님들은 점심시간에 식단과 연동된 알러지 주의 알림을 받아 특이사항을 놓치지 않습니다. \n 더 안전한 어린이집 생활을 위한 [ 새싹일기 ] 를 만나보세요!',
	},
	{
		title: '화상상담 및 채팅',
		content:
			'전화 부담을 줄이고 업무 효율성을 높이기 위해 채팅과 화상통화 기능을 제공합니다. \n 선생님과 학부모 간의 실시간 소통을 간편하게 즐기고, \n 긴급한 상황에서도 화면을 통한 효과적인 대화가 가능합니다. \n [ 새싹일기 ] 로 더 원활한 소통을 경험하세요!',
	},
];
</script>

<style scoped>
.image-container {
	position: relative;
}

.image-container img {
	position: absolute;
	opacity: 0;
}

.image-container img.image-visible {
	opacity: 1;
}
</style>
