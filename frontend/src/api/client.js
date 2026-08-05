import axios from 'axios';

export const api = axios.create({ baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api' });
export const pageParams = (search, page = 0, size = 10) => ({ params: { q: search || undefined, page, size, sort: 'id,desc' } });
