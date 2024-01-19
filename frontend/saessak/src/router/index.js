import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import ChatView from "@/views/ChatView.vue";
import ChatDetail from "@/views/ChatDetailView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", component: HomeView },

    { path: "/chat", component: ChatView }, // /chat에 대한 기본 컴포넌트
    { path: "/chat/:id", component: ChatDetail }, // /chat/:id에 대한 동적 라우트
    // {
    //   path: '/chat',
    //   component: ChatView,
    //   children: [
    //     { path: '', component: ChatView }, // /chat에 대한 기본 컴포넌트
    //     { path: '/:id', component: ChatDetail }, // /chat/:id에 대한 동적 라우트
    //   ],
    // },
  ],
});

export default router;
