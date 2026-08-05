import { useEffect, useState } from 'react';
import { api, pageParams } from '../api/client.js';
import DataTable from './DataTable.jsx';

export default function ResourcePage({ title, endpoint, columns, initialForm, renderForm, normalize = v => v }) {
  const [rows, setRows] = useState([]); const [form, setForm] = useState(initialForm); const [search, setSearch] = useState(''); const [editing, setEditing] = useState(null);
  const load = async () => { const { data } = await api.get(endpoint, pageParams(search)); setRows(data.content || []); };
  useEffect(() => { load(); }, []);
  const submit = async e => { e.preventDefault(); const payload = normalize(form); editing ? await api.put(`${endpoint}/${editing}`, payload) : await api.post(endpoint, payload); setForm(initialForm); setEditing(null); await load(); };
  const edit = row => { setEditing(row.id); setForm({ ...initialForm, ...row }); };
  const remove = async id => { await api.delete(`${endpoint}/${id}`); await load(); };
  const actionColumns = [...columns, { key: 'actions', label: 'Actions', render: row => <div className="flex gap-2"><button className="text-brand" onClick={() => edit(row)}>Edit</button><button className="text-red-600" onClick={() => remove(row.id)}>Delete</button></div> }];
  return <section className="space-y-6"><div className="flex flex-col justify-between gap-4 md:flex-row md:items-center"><h1 className="text-3xl font-bold">{title}</h1><div className="flex gap-2"><input className="input" placeholder="Search" value={search} onChange={e => setSearch(e.target.value)} /><button className="btn" onClick={load}>Search</button></div></div><form onSubmit={submit} className="card grid gap-4 md:grid-cols-3">{renderForm(form, setForm)}<button className="btn md:col-span-3">{editing ? 'Update' : 'Create'} {title}</button></form><DataTable columns={actionColumns} rows={rows} /></section>;
}
