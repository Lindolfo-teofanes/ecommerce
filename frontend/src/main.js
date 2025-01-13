import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createVuetify } from "vuetify";
import "vuetify/styles";
import "@mdi/font/css/materialdesignicons.css";
import { createPinia } from "pinia";

const vuetify = createVuetify();
const pinia = createPinia();

createApp(App).use(router).use(vuetify).use(pinia).mount("#app");
