import api from "./api";

export const listarPedidos = () => api.get("/pedidos");
export const atualizarStatus = (id, status) =>
    api.patch(`/pedidos/${id}?status=${status}`);
export const criarPedido = (pedido) => api.post("/pedidos", pedido);
