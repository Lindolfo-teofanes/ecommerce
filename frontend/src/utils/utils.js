import api from "@/services/api";

export const buscaCep = (cep) => api.get(`/api/cep/${cep}`)