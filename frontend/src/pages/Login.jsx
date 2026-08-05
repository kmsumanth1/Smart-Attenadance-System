import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api/client.js';

export default function Login() {
  const [mode, setMode] = useState('login');
  const [form, setForm] = useState({ email: '', password: '', fullName: '', role: 'STUDENT' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const submit = async event => {
    event.preventDefault();
    setError('');
    try {
      const endpoint = mode === 'login' ? '/auth/login' : '/auth/register';
      const payload = mode === 'login' ? { email: form.email, password: form.password } : form;
      const { data } = await api.post(endpoint, payload);
      localStorage.setItem('token', data.token);
      localStorage.setItem('user', JSON.stringify({ id: data.id, name: data.name, email: data.email, role: data.role }));
      navigate('/');
    } catch {
      setError(mode === 'login' ? 'Invalid email or password' : 'Registration failed. Check the details and try again.');
    }
  };

  return <main className="grid min-h-screen place-items-center bg-gradient-to-br from-blue-50 to-teal-50 p-6">
    <form onSubmit={submit} className="card w-full max-w-md space-y-4">
      <h1 className="text-3xl font-black">{mode === 'login' ? 'Login' : 'Create account'}</h1>
      <p className="text-slate-500">JWT-secured access for admins, faculty, and students.</p>
      {error && <p className="rounded-xl bg-red-50 p-3 text-red-700">{error}</p>}
      {mode === 'register' && <input className="input" placeholder="Full name" value={form.fullName} onChange={e => setForm({ ...form, fullName: e.target.value })} />}
      <input className="input" placeholder="Email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} />
      <input className="input" placeholder="Password" type="password" value={form.password} onChange={e => setForm({ ...form, password: e.target.value })} />
      {mode === 'register' && <select className="input" value={form.role} onChange={e => setForm({ ...form, role: e.target.value })}>{['STUDENT', 'FACULTY', 'ADMIN'].map(role => <option key={role}>{role}</option>)}</select>}
      <button className="btn w-full">{mode === 'login' ? 'Sign in' : 'Register'}</button>
      <button type="button" className="w-full text-sm font-semibold text-brand" onClick={() => setMode(mode === 'login' ? 'register' : 'login')}>{mode === 'login' ? 'Need an account? Register' : 'Already registered? Sign in'}</button>
    </form>
  </main>;
}
