import { createRouter, createWebHistory } from "vue-router";
import PedidosList from "../views/PedidosList.vue";
import PedidosForm from "../views/PedidosForm.vue";

const routes = [
    { path: "/", component: PedidosList },
    { path: "/form", component: PedidosForm },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
