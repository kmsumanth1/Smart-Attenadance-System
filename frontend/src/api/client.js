import axios from 'axios';

export const api = axios.create({ baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api' });
export const pageParams = (search, page = 0, size = 10) => ({ params: { q: search || undefined, page, size, sort: 'id,desc' } });

export function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  if (window.location.pathname !== '/login') window.location.assign('/login');
}

export function isTokenExpired(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp && payload.exp * 1000 <= Date.now();
  } catch {
    return true;
  }
}

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    if (isTokenExpired(token)) {
      logout();
      throw new axios.Cancel('JWT expired');
    }
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(response => response, error => {
  if (error.response?.status === 401 || error.response?.status === 403) logout();
  return Promise.reject(error);
});
