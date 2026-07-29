import api from "../api/axiosConfig";
import {clearSession,getCurrentUser,getRole,getToken,getUserId, hasRole,
         isSessionValid,saveSession,} from "../utils/session";

export async function login(email, password) {
  const response = await api.post("/auth/login", {
    email,
    password,
  });

  const { token, userId, role } = response.data;

  saveSession({ token,userId,role,});

  return response.data;
}

export async function register(username, email, password, role = "PATIENT") {
  const response = await api.post("/auth/register", {
    username,email,password,role,
  });

  const { token, userId, role: userRole } = response.data;

  saveSession({token,userId,role: userRole,});

  return response.data;
}

export function logout() {
  clearSession();
}

export function isAuthenticated() {
  return isSessionValid();
}
export { getCurrentUser, getRole, getToken, getUserId, hasRole };




