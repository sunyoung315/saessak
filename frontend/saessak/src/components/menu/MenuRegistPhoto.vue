<template>
	<div
		class="flowbit-modal fixed inset-0 flex items-center justify-center z-50"
		v-if="isOpen"
	>
		<div
			class="flowbit-modal__overlay absolute inset-0 bg-black opacity-50"
		></div>
		<div
			class="flowbit-modal__container bg-white w-full max-w-md m-8 md:m-0 rounded-lg shadow-lg z-50 overflow-y-auto"
		>
			<!-- Title & Close Btn -->
			<div class="flowbit-modal__header px-4 flex justify-between items-center">
				<div class="flex font-bold text-lg items-center">
					<div class="pl-6 pr-4 pt-6 pb-4 text-xl font-extrabold">
						식단표 사진 등록
					</div>
					<div class="p-1 pt-3 text-nav-navy">{{ menu.menuDate }}</div>
					<div class="p-1 pt-3 text-nav-navy">( {{ menu.menuType }} )</div>
				</div>
				<button
					class="pr-5 pb-3 pl-24 flowbit-modal__close"
					@click="closeModal"
				>
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
							d="M16.694 7.30602C17.102 7.71405 17.102 8.37559 16.694 8.78361L13.4783 11.9993L16.694 15.2164C17.102 15.6244 17.102 16.286 16.694 16.694C16.286 17.102 15.6244 17.102 15.2164 16.694L12.0007 13.4769L8.78361 16.694C8.37559 17.102 7.71405 17.102 7.30602 16.694C6.89799 16.286 6.89799 15.6244 7.30602 15.2164L10.5231 11.9993L7.30602 8.78361C6.89799 8.37559 6.89799 7.71405 7.30602 7.30602C7.71405 6.89799 8.37559 6.89799 8.78361 7.30602L12.0007 10.5217L15.2164 7.30602C15.6244 6.89799 16.286 6.89799 16.694 7.30602Z"
							fill="#000000"
						/>
					</svg>
				</button>
			</div>

			<div class="file-upload">
				<!-- 파일 업로드 -->
				<input
					id="inputFile"
					ref="inputFile"
					type="file"
					@change="readInputFile"
				/>
				<button class="btn m-0" @click="registPhoto(menu.menuId)">등록</button>
			</div>
			<!-- 업로드한 파일 미리보기 -->
			<div id="imagePreview" class="file-preview">
				<img :src="imageSrc" v-if="imageSrc" class="preview-image" />
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { registMenuPhoto } from '@/api/menu';

// 사진을 등록할 메뉴 정보
const menu = ref();

// 이미지 파일
const file = ref(null);

// 이미지 파일의 소스(미리보기를 위한 데이터 URL)
const imageSrc = ref('');

// input에 값이 들어왔을 때
const readInputFile = e => {
	// 파일 1개만 입력 가능
	file.value = e.target.files[0];

	// 확장자가 이미지 파일인지 확인
	if (!file.value.type.match('image/.*')) {
		alert('이미지 확장자만 업로드 가능합니다.');
		document.getElementById('inputFile').value = null;
		return;
	}

	// 파일을 읽기 위한 객체
	const reader = new FileReader();
	// 파일 읽기가 완료되었을 때 호출
	reader.onload = e => {
		// 선택한 이미지 파일의 데이터 url을 imageSrc 변수에 저장
		imageSrc.value = e.target.result;
	};
	// 선택한 파일을 데이터 url로 읽도록 요청
	reader.readAsDataURL(file.value);
};

// Modal //////////////////
const isOpen = ref(false);

const openModal = data => {
	isOpen.value = true;
	menu.value = data;
};

const closeModal = () => {
	isOpen.value = false;
	imageSrc.value = '';
};

defineExpose({ openModal });
////////////////////////////

let inputFile;

onMounted(() => {
	inputFile = ref(null);
});

const props = defineProps({
	getWeeklyMenu: Function,
});

const registPhoto = async menuId => {
	// FormData 객체 생성
	const formData = new FormData();
	// file 필드에 선택된 파일 추가

	formData.append('menuId', menuId);
	formData.append('menuFile', file.value);

	const isSuccess = await registMenuPhoto(formData);

	// 사진 등록 성공 시 모달창 닫기
	if (isSuccess) {
		closeModal();
		await props.getWeeklyMenu();
	}
};
</script>

<style scoped>
.flowbit-modal__container {
	@apply w-[40rem] h-[36rem] max-w-none;
}
.file-upload {
	@apply flex justify-between mx-10 items-end;
}
.file-preview {
	@apply w-[36rem] h-[26rem] border-2 mx-8 my-4 p-4 flex justify-center;
}
.preview-image {
	@apply max-w-[36rem] max-h-[26rem];
}
</style>
