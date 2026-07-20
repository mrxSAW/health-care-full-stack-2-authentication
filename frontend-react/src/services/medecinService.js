import api from "../api/axiosConfig";

export async function getAll(page = 0, size = 10) {
  const response = await api.get(`/medcins?page=${page}&size=${size}`);
  return response.data;
}

export async function getById(id) {
  const response = await api.get(`/medcins/${id}`);
  return response.data;
}

export async function create(medecin) {
  const response = await api.post("/medcins", medecin);
  return response.data;
}

export async function update(id, medecin) {
  const response = await api.put(`/medcins/${id}`, medecin);
  return response.data;
}

export async function remove(id) {
  const response = await api.delete(`/medcins/${id}`);
  return response.data;
}