<template>
	<aside
		id="default-sidebar"
		class="top-0 left-0 z-30 w-64 min-h-screen transition-transform -translate-x-full sm:translate-x-0"
		aria-label="Sidebar"
	>
		<!-- overflow-y-auto <-- sticky 할려면 얘 없어야함 -->
		<div
			class="relative h-full min-h-full px-3 py-4 bg-gray-50 dark:bg-gray-800"
			:class="navColor"
		>
			<ul class="sticky top-28 space-y-3 space-x-3 font-bold text-base">
				<li></li>
				<li @click="changeNavColor({ color: 'bg-nav-red', name: 'home' })">
					<RouterLink
						:to="{ name: 'Home' }"
						id="Home"
						:style="{
							backgroundColor: selected === 'home' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Outline"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="M23.121,9.069,15.536,1.483a5.008,5.008,0,0,0-7.072,0L.879,9.069A2.978,2.978,0,0,0,0,11.19v9.817a3,3,0,0,0,3,3H21a3,3,0,0,0,3-3V11.19A2.978,2.978,0,0,0,23.121,9.069ZM15,22.007H9V18.073a3,3,0,0,1,6,0Zm7-1a1,1,0,0,1-1,1H17V18.073a5,5,0,0,0-10,0v3.934H3a1,1,0,0,1-1-1V11.19a1.008,1.008,0,0,1,.293-.707L9.878,2.9a3.008,3.008,0,0,1,4.244,0l7.585,7.586A1.008,1.008,0,0,1,22,11.19Z"
							/>
						</svg>
						<span class="ms-3">시작화면</span>
					</RouterLink>
				</li>

				<li
					v-if="flag == true"
					@click="changeNavColor({ color: 'bg-nav-orange', name: 'notice' })"
				>
					<RouterLink
						:to="{ name: 'NoticeList' }"
						id="Notice"
						:style="{
							backgroundColor: selected === 'notice' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							height="512"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="m17 0a1 1 0 0 0 -1 1c0 2.949-2.583 4-5 4h-7a4 4 0 0 0 -4 4v2a3.979 3.979 0 0 0 1.514 3.109l3.572 7.972a3.233 3.233 0 0 0 2.953 1.919 2.982 2.982 0 0 0 2.72-4.2l-2.2-4.8h2.441c2.417 0 5 1.051 5 4a1 1 0 0 0 2 0v-18a1 1 0 0 0 -1-1zm-8.063 20.619a.983.983 0 0 1 -.898 1.381 1.232 1.232 0 0 1 -1.126-.734l-2.808-6.266h2.254zm7.063-6.019a7.723 7.723 0 0 0 -5-1.6h-7a2 2 0 0 1 -2-2v-2a2 2 0 0 1 2-2h7a7.723 7.723 0 0 0 5-1.595zm7.9.852a1 1 0 0 1 -1.342.448l-2-1a1 1 0 0 1 .894-1.79l2 1a1 1 0 0 1 .448 1.337zm-3.79-9a1 1 0 0 1 .448-1.342l2-1a1 1 0 1 1 .894 1.79l-2 1a1 1 0 0 1 -1.342-.448zm-.11 3.548a1 1 0 0 1 1-1h2a1 1 0 0 1 0 2h-2a1 1 0 0 1 -1-1z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">공지사항</span>
					</RouterLink>
				</li>
				<li
					v-if="flag == true"
					@click="changeNavColor({ color: 'bg-nav-yellow', name: 'board' })"
				>
					<RouterLink
						:to="{ name: 'BoardList' }"
						id="Board"
						:style="{
							backgroundColor: selected === 'board' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							data-name="Layer 1"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="m17,0H7C4.243,0,2,2.243,2,5v15c0,2.206,1.794,4,4,4h11c2.757,0,5-2.243,5-5V5c0-2.757-2.243-5-5-5Zm3,11h-3c-.552,0-1-.448-1-1s.448-1,1-1h3v2Zm0-6v2h-3c-1.654,0-3,1.346-3,3s1.346,3,3,3h3v3h-12V2h9c1.654,0,3,1.346,3,3ZM6,2.172v13.828c-.728,0-1.411.196-2,.537V5c0-1.304.836-2.415,2-2.828Zm11,19.828H6c-1.103,0-2-.897-2-2s.897-2,2-2h14v1c0,1.654-1.346,3-3,3Z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">알림장</span>
					</RouterLink>
				</li>
				<li
					v-if="flag == true"
					@click="changeNavColor({ color: 'bg-nav-green', name: 'album' })"
				>
					<RouterLink
						:to="{ name: 'AlbumList' }"
						id="Album"
						:style="{
							backgroundColor: selected === 'album' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							data-name="Layer 1"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="M17,16c-.552,0-1,.448-1,1v1h-2v-1c0-.552-.448-1-1-1s-1,.448-1,1v5H6V11c0-.552-.448-1-1-1h-.5c-2.481,0-4.5,2.019-4.5,4.5v5c0,2.481,2.019,4.5,4.5,4.5H13.5c2.481,0,4.5-2.019,4.5-4.5v-2.5c0-.552-.448-1-1-1Zm-13,0v2H2v-2h2Zm0-3.95v1.95h-1.945c.2-.977,.968-1.75,1.945-1.95Zm-1.945,7.95h1.945v1.95c-.978-.199-1.745-.972-1.945-1.95Zm11.945,1.949v-1.949h1.949c-.199,.978-.971,1.75-1.949,1.949ZM19,0h-6c-2.757,0-5,2.243-5,5v4c0,2.757,2.243,5,5,5h6c2.757,0,5-2.243,5-5V5c0-2.757-2.243-5-5-5ZM10,5c0-1.654,1.346-3,3-3h6c1.147,0,2.134,.654,2.638,1.602l-3.457,3.464-.475-.48c-.736-.737-1.896-.789-2.705-.113l-4.621,3.961c-.235-.428-.381-.911-.381-1.433V5Zm9,7h-6c-.395,0-.771-.081-1.117-.221l4.406-3.784,.475,.48c.779,.78,2.049,.781,2.829,0l2.408-2.408v2.932c0,1.654-1.346,3-3,3Zm-8-7.5c0-.828,.672-1.5,1.5-1.5s1.5,.672,1.5,1.5-.672,1.5-1.5,1.5-1.5-.672-1.5-1.5Z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">앨범</span>
					</RouterLink>
				</li>
				<li
					v-if="flag == true"
					@click="changeNavColor({ color: 'bg-nav-blue', name: 'document' })"
				>
					<RouterLink
						:to="{ name: 'DocumentList' }"
						id="Document"
						:style="{
							backgroundColor: selected === 'document' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							data-name="Layer 1"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="M23.12,11.88c-1.13-1.13-3.11-1.13-4.24,0l-7,7c-.57,.57-.88,1.32-.88,2.12v2c0,.55,.45,1,1,1h2c.8,0,1.55-.31,2.12-.88l7-7c.57-.57,.88-1.32,.88-2.12s-.31-1.55-.88-2.12Zm-1.41,2.83l-7,7c-.19,.19-.44,.29-.71,.29h-1v-1c0-.26,.11-.52,.29-.71l7-7c.38-.38,1.04-.38,1.41,0,.19,.19,.29,.44,.29,.71s-.1,.52-.29,.71Zm-7.71-1.21c0-.83-.67-1.5-1.5-1.5s-1.5,.67-1.5,1.5c0,.32,.1,.67,.29,.99-.32,.28-.74,.51-1.29,.51-.44,0-.88-.13-1.3-.34,.81-1.16,1.3-2.55,1.3-3.66,0-1.65-1.35-3-3-3s-3,1.35-3,3c0,1.22,.68,2.65,1.71,3.8-.24,.13-.48,.2-.71,.2-.55,0-1,.45-1,1s.45,1,1,1c.83,0,1.61-.33,2.3-.85,.84,.53,1.76,.85,2.7,.85,2.6,0,4-2.48,4-3.5Zm-6.87-.1c-.68-.79-1.13-1.72-1.13-2.4,0-.55,.45-1,1-1s1,.45,1,1c0,.72-.35,1.63-.87,2.4Zm1.87,7.6c0,.55-.45,1-1,1h-3c-2.76,0-5-2.24-5-5V5C0,2.24,2.24,0,5,0h5.76c1.07,0,2.07,.42,2.83,1.17l3.24,3.24c.76,.76,1.17,1.76,1.17,2.83v1.76c0,.55-.45,1-1,1s-1-.45-1-1v-1.76c0-.08,0-.16-.02-.24h-2.98c-1.1,0-2-.9-2-2V2.02c-.08,0-.16-.02-.24-.02H5c-1.65,0-3,1.35-3,3v12c0,1.65,1.35,3,3,3h3c.55,0,1,.45,1,1Z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">동의서</span>
					</RouterLink>
				</li>
				<li
					v-if="flag == true"
					@click="changeNavColor({ color: 'bg-nav-navy', name: 'menu' })"
				>
					<RouterLink
						:to="{ name: 'MenuList' }"
						id="Menu"
						:style="{
							backgroundColor: selected === 'menu' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							data-name="Layer 1"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="M12,1V7a5.009,5.009,0,0,1-4,4.9V23a1,1,0,0,1-2,0V11.9A5.009,5.009,0,0,1,2,7V1A1,1,0,0,1,4,1V7A3,3,0,0,0,6,9.816V1A1,1,0,0,1,8,1V9.816A3,3,0,0,0,10,7V1A1,1,0,0,1,12,1Zm10,9a12.64,12.64,0,0,1-5,9.775V23a1,1,0,0,1-2,0V2A1.9,1.9,0,0,1,16.131.217a2.194,2.194,0,0,1,2.356.459A13.474,13.474,0,0,1,22,10Zm-2,0a11.7,11.7,0,0,0-3-7.937V17.07A10.3,10.3,0,0,0,20,10Z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">식단표</span>
					</RouterLink>
				</li>
				<li
					v-if="flag == true"
					@click="
						changeNavColor({ color: 'bg-nav-purple', name: 'attendance' })
					"
				>
					<RouterLink
						:to="{ name: 'Attendance' }"
						id="Attendance"
						:style="{
							backgroundColor: selected === 'attendance' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							data-name="Layer 1"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="m11.5,6.5c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5-1.119,2.5-2.5,2.5-2.5-1.119-2.5-2.5Zm-1.596,7.355c.472.287,1.088.136,1.374-.336.559-.922,1.627-1.519,2.722-1.519s2.163.596,2.722,1.519c.188.31.519.481.856.481.177,0,.355-.047.518-.145.472-.286.623-.901.337-1.374-.929-1.531-2.627-2.481-4.433-2.481s-3.504.951-4.433,2.481c-.286.473-.135,1.087.337,1.374Zm12.096-8.855v14c0,2.757-2.243,5-5,5H6c-2.206,0-4-1.794-4-4V5C2,2.243,4.243,0,7,0h10C19.757,0,22,2.243,22,5Zm-5-3h-9v14h12V5c0-1.654-1.346-3-3-3Zm-13,3v11.556c.591-.344,1.268-.556,2-.556V2.184c-1.161.414-2,1.514-2,2.816Zm16,14v-1H6c-1.103,0-2,.897-2,2s.897,2,2,2h11c1.654,0,3-1.346,3-3Z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">출석부</span>
					</RouterLink>
				</li>
				<li
					v-if="flag == true && isTeacher"
					@click="changeNavColor({ color: 'bg-nav-gray', name: 'setting' })"
				>
					<RouterLink
						:to="{ name: 'Setting' }"
						id="Setting"
						:style="{
							backgroundColor: selected === 'setting' ? 'white' : 'inherit',
						}"
						class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 focus:bg-gray-100 dark:hover:bg-gray-700 group"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							id="Layer_1"
							data-name="Layer 1"
							viewBox="0 0 24 24"
							class="w-6 h-6"
						>
							<path
								d="M11.5 0L14 3L18 3.5L20 6.5L18 10L20 13.5L18 16.5L14 17L11.5 20H8.5L6 17L2 16.5L0 13.5L2 10L0 6.5L2 3.5L6 3L8.5 0H11.5ZM10.562 1.999H9.437L7.0312 4.88666L3.156 5.37L2.35 6.581L4.3035 10L2.35 13.418L3.156 14.629L7.0312 15.1133L9.436 17.999H10.563L12.9688 15.1133L16.843 14.629L17.649 13.418L15.6965 10L17.649 6.581L16.843 5.37L12.9688 4.88666L10.562 1.999ZM10 6C12.2091 6 14 7.79086 14 10C14 12.2091 12.2091 14 10 14C7.79086 14 6 12.2091 6 10C6 7.79086 7.79086 6 10 6ZM10 8C8.89543 8 8 8.89543 8 10C8 11.1046 8.89543 12 10 12C11.1046 12 12 11.1046 12 10C12 8.89543 11.1046 8 10 8Z"
							/>
						</svg>
						<span class="flex-1 ms-3 whitespace-nowrap">우리반 관리</span>
					</RouterLink>
				</li>
			</ul>
		</div>
	</aside>
</template>

<script setup>
import { onMounted, ref, watch, defineProps, defineEmits, computed } from 'vue';
import { useRoute } from 'vue-router';
import { loginStore } from '@/store/loginStore';
import { storeToRefs } from 'pinia';

const route = useRoute();
const store = loginStore();

// Local Storage에서 색상을 가져와서 설정 or 기본으로 bg-nav-red로 설정
const navColor = ref(localStorage.getItem('navColor') || 'bg-nav-red');
const flag = ref(false); // 로그인 여부 저장
const selected = ref('');
const { isTeacher } = storeToRefs(store);
const emits = defineEmits(['headerClick']); // 메뉴 클릭 이벤트
const props = defineProps({
	navData: {
		type: String,
		default: '',
	},
});
onMounted(() => {
	// 로그인 여부 판단하기
	// console.log(props.navData)
	const token = localStorage.getItem('accessToken');
	flag.value = token == null ? false : true;
	// console.log(flag);
	const now = route.path.slice(1);
	console.log('now : ' + now);
	if (now == '') {
		selected.value = 'home';
	} else {
		selected.value = now;
	}
});

// 메인페이지 알림장, 공지사항 '자세히 보기'로 이동, 아이 변경 시 Nav 컬러 추적
const path = computed(() => route.path.substr(1));
watch(path, newVal => {
	if (route.path.substr(1, 5) === 'board') {
		selected.value = 'board';
		navColor.value = 'bg-nav-yellow';
		localStorage.setItem('navColor', 'bg-nav-yellow');
	} else if (route.path.substr(1, 6) === 'notice') {
		selected.value = 'notice';
		navColor.value = 'bg-nav-orange';
		localStorage.setItem('navColor', 'bg-nav-orange');
	} else if (newVal === '') {
		selected.value = 'home';
		navColor.value = 'bg-nav-red';
		localStorage.setItem('navColor', 'bg-nav-red');
	}
});

// navColor가 변경될 때 Local Storage에 저장
watch(navColor, newColor => {
	localStorage.setItem('navColor', newColor);
	// console.log("color 변경됨")
});

watch(
	() => props.navData,
	(newValue, oldValue) => {
		if (newValue == 'click') {
			navColor.value = 'bg-nav-red';
			localStorage.setItem('navColor', 'bg-nav-red');
			selected.value = 'home';
		}
		//   console.log('props.navData changed:', newValue, oldValue);
	},
);

// 각 색상 변경 함수. 선택한 색상을 Local Storage에 저장
const changeNavColor = input => {
	emits('navClick', '');
	navColor.value = input.color;
	selected.value = input.name;
	localStorage.setItem('logoClick', false);
	// console.log("selected : " + selected.value)
};
</script>

<style scoped></style>
