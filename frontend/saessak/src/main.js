import { createApp } from 'vue';
import App from './App.vue';
import router from './router/index.js';
import 'tailwindcss/tailwind.css';
import VCalendar from 'v-calendar';
import 'v-calendar/style.css';

const app = createApp(App);
app.use(router);
app.use(VCalendar, {});
app.mount('#app');
