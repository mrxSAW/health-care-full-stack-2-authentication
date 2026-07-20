import api from "../api/axiosConfig";

export async function getAll(page = 0, size = 10) {
  const response = await api.get(`/dossiers?page=${page}&size=${size}`);
  return response.data;
}

export async function getById(id) {
  const response = await api.get(`/dossiers/${id}`);
  return response.data;
}

export async function create(dossier) {
  const response = await api.post("/dossiers", dossier);
  return response.data;
}

export async function update(id, dossier) {
  const response = await api.put(`/dossiers/${id}`, dossier);
  return response.data;
}

export async function remove(id) {
  const response = await api.delete(`/dossiers/${id}`);
  return response.data;
}




export async function downloadPdf(id) {
  const response = await api.get(`/api/download/dossier-medical/${id}`, {
    responseType: "blob",
  });

  const url = window.URL.createObjectURL(response.data);
  const link = document.createElement("a");

  link.href = url;
  link.download = `dossier-medical-${id}.pdf`;
  link.click();

  window.URL.revokeObjectURL(url);
}