const TOKEN_KEY = "token";
const USER_ID_KEY = "userId";
const ROLE_KEY = "role";

export function saveSession({ token, userId, role }) {
  if (token) {
    localStorage.setItem(TOKEN_KEY, token);
  }

  if (userId) {
    localStorage.setItem(USER_ID_KEY, userId);
  }

  if (role) {
    localStorage.setItem(ROLE_KEY, role);
  }
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

export function getCurrentUser() {
  const token = getToken();
  const userId = getUserId();
  const role = getRole();

  if (!token || !userId || !role) {
    return null;
  }

  return {
    token,
    userId,
    role,
  };
}

export function isSessionValid() {
  return Boolean(getCurrentUser());
}

export function hasRole(allowedRoles = []) {
  const role = getRole();

  if (!role) {
    return false;
  }

  return allowedRoles.includes(role);
}

export function clearSession() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_ID_KEY);
  localStorage.removeItem(ROLE_KEY);
}





