import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router/index.js';
import 'tailwindcss/tailwind.css';
import { setupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';

const app = createApp(App);

app.use(router);
// Use plugin defaults (optional)
app.use(setupCalendar, {});
// pinia
app.use(createPinia());

// Use the components
app.component('VCalendar', Calendar);
app.component('VDatePicker', DatePicker);
app.mount('#app');
