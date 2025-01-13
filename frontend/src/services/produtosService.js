import api from "./api";

export const listarProdutos = () => api.get("/produtos");
