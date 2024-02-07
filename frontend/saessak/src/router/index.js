import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import LoginView from '@/views/LoginView.vue';
import UserView from '@/components/user/UserView.vue';
import JoinView from '@/views/JoinView.vue';
import AlbumView from '@/components/album/AlbumView.vue';
import AlbumList from '@/components/album/AlbumList.vue';
import AlbumDetailParent from '@/components/album/AlbumDetailParent.vue';
import AlbumDetailTeacher from '@/components/album/AlbumDetailTeacher.vue';
import AlbumCreate from '@/components/album/AlbumCreate.vue';
import NoticeView from '@/components/notice/NoticeView.vue';
import NoticeList from '@/components/notice/NoticeList.vue';
import NoticeCreate from '@/components/notice/NoticeCreate.vue';
import DocumentView from '@/components/document/DocumentView.vue';
import DocumentList from '@/components/document/DocumentList.vue';
import DocumentReplaceDetail from '@/components/document/DocumentReplaceDetail.vue';
import DocumentAllergyDetail from '@/components/document/DocumentAllergyDetail.vue';
import DocumentReplaceCreate from '@/components/document/DocumentReplaceCreate.vue';
import DocumentAllergyCreate from '@/components/document/DocumentAllergyCreate.vue';
import AttendanceView from '@/components/attendance/AttendanceView.vue';
import ChatView from '@/components/chat/ChatView.vue';
import ChatDetail from '@/components/chat/ChatDetailView.vue';
import SettingView from '@/components/setting/SettingView.vue';
import BoardView from '@/components/board/BoardView.vue';
import BoardCreate from '@/components/board/BoardCreate.vue';
import BoardList from '@/components/board/BoardList.vue';
import BoardDetailTeacher from '@/components/board/BoardDetailTeacher.vue';
import BoardDetailParent from '@/components/board/BoardDetailParent.vue';
import MenuView from '@/components/menu/MenuView.vue';
import MenuList from '@/components/menu/MenuList.vue';
import MenuCreate from '@/components/menu/MenuCreate.vue';
import AppView from '@/AppView.vue';
import faceChatModal from '@/components/chat/FaceChatModal.vue';

const routes = [
	{
		path: '/',
		name: 'App',
		component: AppView,
		children: [
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
				path: '/join',
				name: 'Join',
				component: JoinView,
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
						path: '',
						name: 'BoardList',
						component: BoardList,
					},
					{
						path: 'create',
						name: 'BoardCreate',
						component: BoardCreate,
					},
					{
						path: ':id',
						name: 'BoardDetailParent',
						component: BoardDetailParent,
					},
					{
						path: 'kid/:id',
						name: 'BoardDetailTeacher',
						component: BoardDetailTeacher,
					},
				],
			},
			{
				path: '/album',
				name: 'Album',
				component: AlbumView,
				children: [
					{
						path: '',
						name: 'AlbumList',
						component: AlbumList,
					},
					{
						path: 'create',
						name: 'AlbumCreate',
						component: AlbumCreate,
					},
					{
						path: ':id',
						name: 'AlbumDetailParent',
						component: AlbumDetailParent,
					},
					{
						path: ':id',
						name: 'AlbumDetailTeacher',
						component: AlbumDetailTeacher,
					},
				],
			},
			{
				path: '/notice',
				name: 'Notice',
				component: NoticeView,
				children: [
					{
						path: '',
						name: 'NoticeList',
						component: NoticeList,
					},
					{
						path: 'create',
						name: 'NoticeCreate',
						component: NoticeCreate,
					},
				],
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
						path: 'replacement/create',
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
				children: [
					{
						path: '',
						name: 'MenuList',
						component: MenuList,
					},
					{
						path: 'create',
						name: 'MenuCreate',
						component: MenuCreate,
					},
				],
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
		],
	},
	{
		path: '/facechat',
		name: 'Facechat',
		component: faceChatModal,
	},
];

const router = createRouter({
	history: createWebHistory('/'),
	routes,
});

export default router;
