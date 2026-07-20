import api from "../api/axiosConfig";

const TOKEN_KEY = "token";
const USER_ID_KEY = "userId";
const ROLE_KEY = "role";

export async function login(email, password) {
  const response = await api.post("/auth/login", {
    email, password,
  });

  const { token, userId, role } = response.data;

  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(USER_ID_KEY, userId);
  localStorage.setItem(ROLE_KEY, role);

  return response.data;
}

export async function register(username, email, password, role = "PATIENT") {
  const response = await api.post("/auth/register", {
    username, email,password, role,
  });

  const { token, userId, role: userRole } = response.data;

  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(USER_ID_KEY, userId);
  localStorage.setItem(ROLE_KEY, userRole);

  return response.data;
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_ID_KEY);
  localStorage.removeItem(ROLE_KEY);
}

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function getUserId() {
  return localStorage.getItem(USER_ID_KEY);
}

export function getRole() {
  return localStorage.getItem(ROLE_KEY);
}

export function isAuthenticated() {
  return Boolean(getToken());
}