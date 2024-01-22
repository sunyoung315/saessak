import { createApp } from 'vue';
import App from './App.vue';
import router from './router/index.js';
import 'tailwindcss/tailwind.css';
import VCalendar from 'v-calendar';
import 'v-calendar/style.css';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

const app = createApp(App);

app.use(router);
app.use(VCalendar, {});
app.component('VueDatePicker', VueDatePicker);
app.mount('#app');
