"use client";

import { Download, FileText, FileSpreadsheet, BarChart2 } from "lucide-react";

const reports = [
  {
    title: "Closed Service Calls",
    description: "All completed service records filtered by date range",
    icon: FileText,
    gradient: "from-violet-500 to-indigo-600",
    count: "1,142 records",
  },
  {
    title: "Pending & Open Calls",
    description: "All services pending closure or engineer assignment",
    icon: BarChart2,
    gradient: "from-amber-500 to-orange-600",
    count: "106 records",
  },
  {
    title: "BIR Summary",
    description: "Bank Instrument Report across all regions",
    icon: FileSpreadsheet,
    gradient: "from-sky-500 to-cyan-600",
    count: "348 records",
  },
  {
    title: "Non-Sale Report",
    description: "Non-sale service activity summary",
    icon: FileSpreadsheet,
    gradient: "from-emerald-500 to-teal-600",
    count: "89 records",
  },
  {
    title: "Spares Utilization",
    description: "Parts requested, dispatched, and pending per region",
    icon: FileText,
    gradient: "from-pink-500 to-rose-600",
    count: "128 records",
  },
  {
    title: "Engineer Performance",
    description: "Services completed per engineer by month",
    icon: BarChart2,
    gradient: "from-purple-500 to-fuchsia-600",
    count: "46 engineers",
  },
];

const recentExports = [
  { name: "Closed_Services_March.xlsx", date: "Today, 09:30", size: "2.4 MB" },
  { name: "BIR_Report_Feb_2026.xlsx", date: "Yesterday", size: "1.8 MB" },
  { name: "Spares_Q1_2026.xlsx", date: "Mar 10", size: "980 KB" },
];

export default function ReportsPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Reports & Exports</h1>
        <p className="text-slate-500 text-sm mt-1">Generate and download reports for your service data</p>
      </div>

      {/* Date Filter */}
      <div className="flex items-center gap-3 p-4 bg-white rounded-2xl border border-slate-100 shadow-sm flex-wrap">
        <p className="text-sm font-medium text-slate-600 mr-2">Date Range:</p>
        <input type="date" defaultValue="2026-03-01"
          className="px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-700 bg-slate-50 focus:outline-none focus:ring-2 focus:ring-emerald-200 focus:border-emerald-400 transition" />
        <span className="text-slate-400 text-sm">to</span>
        <input type="date" defaultValue="2026-03-14"
          className="px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-700 bg-slate-50 focus:outline-none focus:ring-2 focus:ring-emerald-200 focus:border-emerald-400 transition" />
        <select className="px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-700 bg-slate-50 focus:outline-none focus:ring-2 focus:ring-emerald-200 transition appearance-none">
          <option>All Regions</option>
          <option>Chennai</option>
          <option>Bangalore</option>
          <option>Delhi</option>
          <option>Mumbai</option>
        </select>
      </div>

      {/* Report Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-4">
        {reports.map((r, i) => (
          <div key={i} className="bg-white rounded-2xl border border-slate-100 shadow-sm hover:shadow-md transition-shadow p-5">
            <div className="flex items-start gap-4">
              <div className={`w-11 h-11 rounded-xl flex items-center justify-center bg-gradient-to-br ${r.gradient} shadow-md shrink-0`}>
                <r.icon className="w-5 h-5 text-white" />
              </div>
              <div className="flex-1">
                <p className="font-semibold text-slate-900 text-[14px]">{r.title}</p>
                <p className="text-xs text-slate-500 mt-0.5 leading-relaxed">{r.description}</p>
                <p className="text-xs text-slate-400 mt-1 font-mono">{r.count}</p>
              </div>
            </div>
            <div className="mt-4 flex gap-2">
              <button className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium text-white transition hover:opacity-90"
                style={{ background: "linear-gradient(135deg, #10b981, #059669)" }}>
                <Download className="w-3.5 h-3.5" /> Excel
              </button>
              <button className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium text-slate-600 bg-slate-100 hover:bg-slate-200 transition">
                <FileText className="w-3.5 h-3.5" /> PDF
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Recent Exports */}
      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-5">
        <h2 className="text-sm font-semibold text-slate-700 mb-4">Recent Exports</h2>
        <div className="space-y-3">
          {recentExports.map((f, i) => (
            <div key={i} className="flex items-center justify-between py-2.5 border-b border-slate-100 last:border-0">
              <div className="flex items-center gap-3">
                <div className="w-9 h-9 rounded-lg bg-emerald-50 flex items-center justify-center">
                  <FileSpreadsheet className="w-4 h-4 text-emerald-600" />
                </div>
                <div>
                  <p className="text-sm font-medium text-slate-800">{f.name}</p>
                  <p className="text-xs text-slate-400">{f.date} · {f.size}</p>
                </div>
              </div>
              <button className="p-2 rounded-lg hover:bg-slate-100 text-slate-400 hover:text-slate-700 transition">
                <Download className="w-4 h-4" />
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
