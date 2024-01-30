import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router/index.js';
import 'tailwindcss/tailwind.css';
import piniaPersist from "pinia-plugin-persistedstate";
import { setupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';

const app = createApp(App);

app.use(router);
// Use plugin defaults (optional)
app.use(setupCalendar, {});
// pinia
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(pinia);

// Use the components
app.component('VCalendar', Calendar);
app.component('VDatePicker', DatePicker);
app.mount('#app');
