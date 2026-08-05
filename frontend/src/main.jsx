import React, { useMemo, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { BookOpen, CalendarCheck, GraduationCap, ShieldCheck, Users } from 'lucide-react';
import './styles.css';

const students = [
  { id: 1, roll: 'CSE-101', name: 'Aarav Sharma', department: 'Computer Science', attendance: 92 },
  { id: 2, roll: 'CSE-102', name: 'Diya Patel', department: 'Computer Science', attendance: 86 },
  { id: 3, roll: 'ECE-201', name: 'Kabir Khan', department: 'Electronics', attendance: 78 },
  { id: 4, roll: 'IT-305', name: 'Meera Rao', department: 'Information Technology', attendance: 95 },
];

const sessions = [
  { course: 'CS501', topic: 'Spring Boot REST APIs', date: '2026-08-05', present: 48, total: 52 },
  { course: 'CS502', topic: 'React Components', date: '2026-08-04', present: 44, total: 50 },
  { course: 'CS503', topic: 'Database Design', date: '2026-08-03', present: 47, total: 49 },
];

function App() {
  const [role, setRole] = useState('Faculty');
  const average = useMemo(() => Math.round(students.reduce((sum, student) => sum + student.attendance, 0) / students.length), []);

  return <main>
    <section className="hero">
      <div>
        <p className="eyebrow"><ShieldCheck size={18} /> Secure smart attendance</p>
        <h1>Smart Attendance System</h1>
        <p className="hero-copy">Manage student attendance, faculty workflows, course sessions, and reports from one responsive dashboard.</p>
        <div className="login-card">
          <h2>Role Login</h2>
          <div className="role-toggle">
            {['Faculty', 'Student'].map(item => <button className={role === item ? 'active' : ''} onClick={() => setRole(item)} key={item}>{item}</button>)}
          </div>
          <input placeholder={`${role} email`} />
          <input placeholder="Password" type="password" />
          <button className="primary">Continue as {role}</button>
        </div>
      </div>
      <div className="stats-grid">
        <Stat icon={<Users />} label="Students" value="1,248" />
        <Stat icon={<BookOpen />} label="Courses" value="32" />
        <Stat icon={<CalendarCheck />} label="Avg. Attendance" value={`${average}%`} />
        <Stat icon={<GraduationCap />} label="Faculty" value="48" />
      </div>
    </section>

    <section className="dashboard">
      <div className="panel wide">
        <h2>Attendance Reports</h2>
        <div className="sessions">
          {sessions.map(session => <article key={session.course}>
            <strong>{session.course}</strong><span>{session.topic}</span><em>{session.date}</em>
            <div><progress value={session.present} max={session.total}></progress><b>{session.present}/{session.total}</b></div>
          </article>)}
        </div>
      </div>
      <div className="panel">
        <h2>Student Details</h2>
        <table><thead><tr><th>Roll</th><th>Name</th><th>%</th></tr></thead><tbody>
          {students.map(student => <tr key={student.id}><td>{student.roll}</td><td>{student.name}<small>{student.department}</small></td><td>{student.attendance}</td></tr>)}
        </tbody></table>
      </div>
    </section>
  </main>;
}

function Stat({ icon, label, value }) {
  return <article className="stat">{icon}<span>{label}</span><strong>{value}</strong></article>;
}

createRoot(document.getElementById('root')).render(<App />);
