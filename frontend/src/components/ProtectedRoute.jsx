import { Navigate, Outlet } from 'react-router-dom';
import { isTokenExpired, logout } from '../api/client.js';

export default function ProtectedRoute() {
  const token = localStorage.getItem('token');
  if (!token) return <Navigate to="/login" replace />;
  if (isTokenExpired(token)) {
    logout();
    return null;
  }
  return <Outlet />;
}
