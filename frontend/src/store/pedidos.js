import { defineStore } from "pinia";
import { listarPedidos } from "../services/pedidosService";

export const usePedidosStore = defineStore("pedidos", {
    state: () => ({
        pedidos: [],
    }),
    actions: {
        async fetchPedidos() {
            try {
                const response = await listarPedidos();
                this.pedidos = response.data;
            } catch (error) {
                console.error("Erro ao buscar pedidos:", error);
            }
        },
    },
});
