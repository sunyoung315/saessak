<template>
	<div>
		<!-- 로그인 전 -->
		<div v-if="!isLogin" class="font-bold">
			<div class="text-center mb-20">
				<h1 class="text-4xl mt-20">{{ title }}</h1>
				<p class="m-8 whitespace-pre-line">{{ titleContent }}</p>
			</div>
			<div
				class="flex items-center m-4"
				v-for="(item, index) in contents"
				:key="index"
				:class="{ 'bg-rose-200': index % 2 !== 0 }"
			>
				<img
					v-if="index % 2 === 0"
					class="img basis-1/4 m-8"
					:src="item.image"
					alt="img"
				/>
				<div class="flex flex-col basis-3/4 text-center items-center">
					<h1 class="text-2xl font-bold">{{ item.title }}</h1>
					<span class="whitespace-pre-line mt-8">{{ item.content }}</span>
				</div>
				<img
					v-if="index % 2 !== 0"
					class="img basis-1/4 m-8"
					:src="item.image"
					alt="img"
				/>
			</div>
		</div>
		<!-- 로그인 후 -->
		<div v-else>
			<div v-if="isTeacher">
				<MainTeacherVue />
			</div>
			<div v-else>
				<MainParentVue />
			</div>
		</div>
	</div>
</template>

<script setup>
import MainTeacherVue from '@/views/MainTeacherView.vue';
import MainParentVue from '@/views/MainParentView.vue';

const loginStore = JSON.parse(localStorage.getItem('loginStore'));
const isLogin = loginStore.isLogin;
let isTeacher;
if (isLogin) {
	isTeacher = loginStore.isTeacher;
}

const title = '우리 아이가 잘 생활하고 있을까요...';
const titleContent =
	'안녕하세요. [새싹일기]입니다. \n 우리 아이의 어린이집 생활을 더 가까이에서 공유하고 싶은 학부모 여러분을 위한 특별한 서비스입니다. \n [새싹일기]는 우리 아이의 소중한 순간들을 담아 학부모와 공유하는 플랫폼입니다.';
const contents = [
	{
		title: '알림장',
		content:
			'[새싹일기]에서는 어린이집의 알림장을 통해 아이의 일상을 쉽게 열람할 수 있습니다. \n 건강일지를 통한 시각화된 성장 기록과, 특정 기간 동안의 알림장 요약 리포트 기능을 통해 \n 학부모 여러분의 편의를 최우선으로 생각합니다. \n 함께 성장하는 기쁨을 [새싹일기]에서 느껴보세요.',
	},
	{
		title: '사진첩',
		content:
			'우리 아이들의 소중한 순간을 놓치지 않고 더욱 풍부하게 담을 수 있는 새로운 기능이 등장했습니다! \n [새싹일기]에서는 학부모 여러분이 더 편리하게 어린이집 생활을 체험하실 수 있도록 \n 사진 필터링 및 특별 기능을 소개합니다. \n 사진 필터링을 통해 해당 태그된 아이의 사진첩을 자동으로 만들어줍니다. \n 간편한 사용법으로 원하는 순간을 찾아가세요. 아이의 성장을 함께 감상해보세요.',
	},
	{
		title: '문서 열람',
		content:
			'[새싹일기]는 어린이의 귀가의뢰와 알러지에 대한 관리를 간편하게 해결합니다. \n 부모님은 손쉽게 아이의 건강에 관련된 의뢰서를 작성할 수 있습니다. \n 선생님들은 점심시간에 식단과 연동된 알러지 주의 알림을 받아 특이사항을 놓치지 않습니다. \n 더 안전한 어린이집 생활을 위한 [새싹일기]를 만나보세요!',
	},
	{
		title: '화상상담 및 채팅',
		content:
			'[새싹일기]는 전화 부담을 줄이고 업무 효율성을 높이기 위해 채팅 기능과 화상통화 기능을 제공합니다. \n 선생님과 학부모 간의 실시간 소통을 간편하게 즐기고, \n 긴급한 상황에서도 화면을 통한 효과적인 대화가 가능합니다. \n [새싹일기]로 더 원활한 소통을 경험하세요!',
	},
];
</script>

<style scoped>
.img {
	width: 300px;
	height: 250px;
}
</style>
