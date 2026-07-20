import api from "../api/axiosConfig";

export async function getAll(page = 0, size = 10) {
  const response = await api.get(`/patients?page=${page}&size=${size}`);
  return response.data;
}

export async function getById(id) {
  const response = await api.get(`/patients/${id}`);
  return response.data;
}

export async function create(patient) {
  const response = await api.post("/patients", patient);
  return response.data;
}

export async function update(id, patient) {
  const response = await api.put(`/patients/${id}`, patient);
  return response.data;
}

export async function remove(id) {
  const response = await api.delete(`/patients/${id}`);
  return response.data;
}