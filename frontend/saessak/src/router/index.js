import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import LoginView from '@/views/LoginView.vue';
import UserView from '@/components/user/UserView.vue';
import BoardView from '@/components/board/BoardView.vue';
import AlbumView from '@/components/album/AlbumView.vue';
import AlbumCreate from '@/components/album/albumItems/AlbumCreate.vue';
import NoticeView from '@/components/notice/NoticeView.vue';
import DocumentView from '@/components/document/DocumentView.vue';
import DocumentList from '@/components/document/DocumentList.vue';
import DocumentReplaceDetail from '@/components/document/DocumentReplaceDetail.vue';
import DocumentAllergyDetail from '@/components/document/DocumentAllergyDetail.vue';
import DocumentReplaceCreate from '@/components/document/DocumentReplaceCreate.vue';
import DocumentAllergyCreate from '@/components/document/DocumentAllergyCreate.vue';
import MenuView from '@/components/menu/MenuView.vue';
import AttendanceView from '@/components/attendance/AttendanceView.vue';
import ChatView from '@/components/chat/ChatView.vue';
import ChatDetail from '@/components/chat/ChatDetailView.vue';
import SettingView from '@/components/setting/SettingView.vue';
import BoardSummary from '@/components/board/BoardSummary.vue';

const routes = [
	{ path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFoundView },
	{
		path: '/',
		name: 'Home',
		component: HomeView,
	},
	{
		path: '/login',
		name: 'Login',
		component: LoginView,
	},
	{
		path: '/user',
		name: 'User',
		component: UserView,
	},
	{
		path: '/board',
		name: 'Board',
		component: BoardView,
		children: [
			{
				path: '/summary',
				name: 'Summary',
				component: BoardSummary,
			},
		],
	},
	{
		path: '/album',
		name: 'Album',
		component: AlbumView,
		children: [
			{
				path: 'create',
				name: 'AlbumCreate',
				component: AlbumCreate,
			},
		],
	},
	{
		path: '/notice',
		name: 'Notice',
		component: NoticeView,
	},
	{
		path: '/document',
		name: 'Document',
		component: DocumentView,
		children: [
			{
				path: '',
				name: 'DocumentList',
				component: DocumentList,
			},
			{
				path: 'replacement/:replacementId',
				name: 'DocumentReplaceDetail',
				component: DocumentReplaceDetail,
			},
			{
				path: 'allgery/:kidId',
				name: 'DocumentAllergyDetail',
				component: DocumentAllergyDetail,
			},
			{
				path: 'replace/create',
				name: 'DocumentReplaceCreate',
				component: DocumentReplaceCreate,
			},
			{
				path: 'allery/create',
				name: 'DocumentAllergyCreate',
				component: DocumentAllergyCreate,
			},
		],
	},
	{
		path: '/menu',
		name: 'Menu',
		component: MenuView,
	},
	{
		path: '/attendance',
		name: 'Attendance',
		component: AttendanceView,
	},
	{
		path: '/chat',
		name: 'Chat',
		component: ChatView,
	},
	{
		path: '/chat/:id',
		component: ChatDetail,
	}, // /chat/:id에 대한 동적 라우트
	{
		path: '/setting',
		name: 'Setting',
		component: SettingView,
	},
];

const router = createRouter({
	history: createWebHistory('/'),
	routes,
});

export default router;
