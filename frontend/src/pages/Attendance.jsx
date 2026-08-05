import { useEffect, useState } from 'react';
import { api, pageParams } from '../api/client.js';
import DataTable from '../components/DataTable.jsx';

const emptySession = { courseId: '', subjectId: '', facultyId: '', sessionDate: '', topic: '' };
const emptyRecord = { sessionId: '', studentId: '', status: 'PRESENT', remarks: '' };

export default function Attendance() {
  const [courses, setCourses] = useState([]); const [subjects, setSubjects] = useState([]); const [students, setStudents] = useState([]); const [sessions, setSessions] = useState([]); const [records, setRecords] = useState([]);
  const [sessionForm, setSessionForm] = useState(emptySession); const [recordForm, setRecordForm] = useState(emptyRecord); const [editingRecord, setEditingRecord] = useState(null);
  const [filters, setFilters] = useState({ courseId: '', subjectId: '', studentId: '' }); const [summary, setSummary] = useState(null);

  const load = async () => {
    const [courseRes, subjectRes, studentRes, sessionRes] = await Promise.all([
      api.get('/courses', pageParams('', 0, 100)), api.get('/subjects', pageParams('', 0, 100)), api.get('/students', pageParams('', 0, 100)), api.get('/attendance/sessions', pageParams('', 0, 100))
    ]);
    setCourses(courseRes.data.content || []); setSubjects(subjectRes.data.content || []); setStudents(studentRes.data.content || []); setSessions(sessionRes.data.content || []); await loadRecords();
  };
  const loadRecords = async () => { const { data } = await api.get('/attendance/records', { params: { ...filters, page: 0, size: 100, sort: 'id,desc' } }); setRecords(data.content || []); };
  useEffect(() => { load(); }, []);

  const createSession = async event => { event.preventDefault(); await api.post('/attendance/sessions', { subjectId: Number(sessionForm.subjectId), facultyId: Number(sessionForm.facultyId), sessionDate: sessionForm.sessionDate, topic: sessionForm.topic }); setSessionForm(emptySession); await load(); };
  const saveRecord = async event => { event.preventDefault(); const payload = { ...recordForm, sessionId: Number(recordForm.sessionId), studentId: Number(recordForm.studentId) }; editingRecord ? await api.put(`/attendance/records/${editingRecord}`, payload) : await api.post('/attendance/records', payload); setRecordForm(emptyRecord); setEditingRecord(null); await loadRecords(); };
  const editRecord = record => { setEditingRecord(record.id); setRecordForm({ sessionId: record.sessionId, studentId: record.studentId, status: record.status, remarks: record.remarks || '' }); };
  const deleteRecord = async id => { await api.delete(`/attendance/records/${id}`); await loadRecords(); };
  const loadSummary = async () => { if (!filters.studentId) return; const { data } = await api.get(`/attendance/summary/students/${filters.studentId}`, { params: { courseId: filters.courseId || undefined, subjectId: filters.subjectId || undefined } }); setSummary(data); };
  const filteredSubjects = sessionForm.courseId ? subjects.filter(subject => String(subject.courseId) === String(sessionForm.courseId)) : subjects;

  return <section className="space-y-8">
    <div><h1 className="text-3xl font-black">Attendance Management</h1><p className="text-slate-500">Create sessions, mark or edit attendance, and filter student attendance by course and subject.</p></div>

    <form onSubmit={createSession} className="card grid gap-4 md:grid-cols-5"><h2 className="text-2xl font-bold md:col-span-5">Faculty · Create Attendance Session</h2><select className="input" value={sessionForm.courseId} onChange={e => setSessionForm({ ...sessionForm, courseId: e.target.value, subjectId: '' })}><option value="">Select course</option>{courses.map(c => <option key={c.id} value={c.id}>{c.code} · {c.title}</option>)}</select><select className="input" value={sessionForm.subjectId} onChange={e => setSessionForm({ ...sessionForm, subjectId: e.target.value })}><option value="">Select subject</option>{filteredSubjects.map(s => <option key={s.id} value={s.id}>{s.code} · {s.name}</option>)}</select><input className="input" placeholder="Faculty ID" value={sessionForm.facultyId} onChange={e => setSessionForm({ ...sessionForm, facultyId: e.target.value })}/><input className="input" type="date" value={sessionForm.sessionDate} onChange={e => setSessionForm({ ...sessionForm, sessionDate: e.target.value })}/><input className="input" placeholder="Topic" value={sessionForm.topic} onChange={e => setSessionForm({ ...sessionForm, topic: e.target.value })}/><button className="btn md:col-span-5">Create Session</button></form>

    <form onSubmit={saveRecord} className="card grid gap-4 md:grid-cols-5"><h2 className="text-2xl font-bold md:col-span-5">Faculty · Mark Present / Absent</h2><select className="input" value={recordForm.sessionId} onChange={e => setRecordForm({ ...recordForm, sessionId: e.target.value })}><option value="">Session</option>{sessions.map(s => <option key={s.id} value={s.id}>{s.sessionDate} · {s.subjectName}</option>)}</select><select className="input" value={recordForm.studentId} onChange={e => setRecordForm({ ...recordForm, studentId: e.target.value })}><option value="">Student</option>{students.map(s => <option key={s.id} value={s.id}>{s.rollNumber} · {s.name}</option>)}</select><select className="input" value={recordForm.status} onChange={e => setRecordForm({ ...recordForm, status: e.target.value })}>{['PRESENT','ABSENT','LATE','EXCUSED'].map(s => <option key={s}>{s}</option>)}</select><input className="input" placeholder="Remarks" value={recordForm.remarks} onChange={e => setRecordForm({ ...recordForm, remarks: e.target.value })}/><button className="btn">{editingRecord ? 'Update' : 'Save'}</button></form>

    <div className="card space-y-4"><h2 className="text-2xl font-bold">Student · View Attendance</h2><div className="grid gap-4 md:grid-cols-4"><select className="input" value={filters.courseId} onChange={e => setFilters({ ...filters, courseId: e.target.value })}><option value="">All courses</option>{courses.map(c => <option key={c.id} value={c.id}>{c.title}</option>)}</select><select className="input" value={filters.subjectId} onChange={e => setFilters({ ...filters, subjectId: e.target.value })}><option value="">All subjects</option>{subjects.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}</select><select className="input" value={filters.studentId} onChange={e => setFilters({ ...filters, studentId: e.target.value })}><option value="">All students</option>{students.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}</select><div className="flex gap-2"><button className="btn" onClick={loadRecords} type="button">Filter</button><button className="btn" onClick={loadSummary} type="button">Percentage</button></div></div>{summary && <p className="rounded-2xl bg-blue-50 p-4 text-xl font-bold text-brand">{summary.studentName}: {summary.percentage}% ({summary.attendedSessions}/{summary.totalSessions})</p>}</div>

    <DataTable rows={records} columns={[{key:'studentName',label:'Student'},{key:'courseTitle',label:'Course'},{key:'subjectName',label:'Subject'},{key:'sessionDate',label:'Date'},{key:'status',label:'Status'},{key:'actions',label:'Actions',render:r=><div className="flex gap-3"><button className="text-brand" onClick={() => editRecord(r)}>Edit</button><button className="text-red-600" onClick={() => deleteRecord(r.id)}>Delete</button></div>}]} />
  </section>;
}
