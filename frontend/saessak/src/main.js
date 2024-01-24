import { createApp } from 'vue';
import App from './App.vue';
import router from './router/index.js';
import 'tailwindcss/tailwind.css';
import VCalendar from 'v-calendar';
import 'v-calendar/style.css';

// Use plugin with optional defaults

createApp(App).use(router).use(VCalendar).mount('#app');
