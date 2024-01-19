import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import LoginView from '@/views/LoginView.vue';
import UserView from '@/components/user/UserView.vue';
import BoardView from '@/components/board/BoardView.vue';
import AlbumView from '@/components/album/AlbumView.vue';
import NoticeView from '@/components/notice/NoticeView.vue';
import DocumentView from '@/components/document/DocumentView.vue';
import MenuView from '@/components/menu/MenuView.vue';
import AttendanceView from '@/components/attendance/AttendanceView.vue';
import ChatView from '@/components/chat/ChatView.vue';
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
