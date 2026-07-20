import api from "../api/axiosConfig";

export async function getAll(page = 0, size = 10) {
  const response = await api.get(`/rendezvous?page=${page}&size=${size}`);
  return response.data;
}

export async function getById(id) {
  const response = await api.get(`/rendezvous/${id}`);
  return response.data;
}

export async function create(rendezVous) {
  const response = await api.post("/rendezvous", rendezVous);
  return response.data;
}

export async function update(id, rendezVous) {
  const response = await api.put(`/rendezvous/${id}`, rendezVous);
  return response.data;
}

export async function remove(id) {
  const response = await api.delete(`/rendezvous/${id}`);
  return response.data;
}