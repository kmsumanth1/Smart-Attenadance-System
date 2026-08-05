export default function DataTable({ columns, rows }) {
  return <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white"><table className="min-w-full divide-y divide-slate-200"><thead className="bg-slate-50"><tr>{columns.map(c => <th key={c.key} className="px-4 py-3 text-left text-sm font-semibold text-slate-600">{c.label}</th>)}</tr></thead><tbody className="divide-y divide-slate-100">{rows.map(row => <tr key={row.id}>{columns.map(c => <td key={c.key} className="px-4 py-3 text-sm">{c.render ? c.render(row) : row[c.key]}</td>)}</tr>)}</tbody></table></div>;
}
