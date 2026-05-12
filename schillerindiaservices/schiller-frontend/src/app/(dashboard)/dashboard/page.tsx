"use client";

import Link from "next/link";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
  Package, Users, Activity, FileText, ArrowUpRight, ArrowDownRight, TrendingUp, Clock, ChevronDown
} from "lucide-react";
import { dashboardNavSections } from "@/config/navigation";
import { cn } from "@/lib/utils";
import { useNavSections } from "@/contexts/nav-sections-context";
import {
  ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip, AreaChart, Area, CartesianGrid
} from "recharts";

const monthlyData = [
  { name: "Jan", services: 4720, spares: 2100 },
  { name: "Feb", services: 3250, spares: 1800 },
  { name: "Mar", services: 4680, spares: 2400 },
  { name: "Apr", services: 5800, spares: 3100 },
  { name: "May", services: 1350, spares: 980 },
  { name: "Jun", services: 3250, spares: 2200 },
  { name: "Jul", services: 1300, spares: 1100 },
];

const recentActivity = [
  { id: "SC-1248", customer: "Apollo Hospitals", engineer: "Ramesh K.", product: "Defibrillator X3", status: "Closed", region: "Chennai", date: "Today, 10:32" },
  { id: "SC-1247", customer: "Fortis Healthcare", engineer: "Vinod S.", product: "ECG Machine Pro", status: "Pending", region: "Bangalore", date: "Today, 09:14" },
  { id: "SC-1246", customer: "Max Hospital", engineer: "Arjun L.", product: "Patient Monitor", status: "In Progress", region: "Delhi", date: "Yesterday" },
  { id: "SC-1245", customer: "AIMS Mumbai", engineer: "Priya T.", product: "Ultrasound Unit", status: "Closed", region: "Mumbai", date: "Yesterday" },
  { id: "SC-1244", customer: "Narayan Health", engineer: "Kiran M.", product: "Ventilator V2", status: "Escalated", region: "Kolkata", date: "Mar 11" },
];

const statusConfig: Record<string, { label: string; className: string }> = {
  Closed: { label: "Closed", className: "bg-emerald-100 text-emerald-700 border-emerald-200" },
  Pending: { label: "Pending", className: "bg-amber-100 text-amber-700 border-amber-200" },
  "In Progress": { label: "In Progress", className: "bg-sky-100 text-sky-700 border-sky-200" },
  Escalated: { label: "Escalated", className: "bg-red-100 text-red-700 border-red-200" },
};

const statCards = [
  {
    title: "Total Services",
    value: "1,248",
    change: "+20.1%",
    positive: true,
    subtext: "vs last month",
    icon: Activity,
    gradient: "from-violet-500 to-indigo-600",
    bgLight: "bg-violet-50",
    textColor: "text-violet-600",
  },
  {
    title: "Pending BIRs",
    value: "42",
    change: "+5",
    positive: false,
    subtext: "this week",
    icon: FileText,
    gradient: "from-sky-500 to-cyan-600",
    bgLight: "bg-sky-50",
    textColor: "text-sky-600",
  },
  {
    title: "Spares Requests",
    value: "128",
    change: "8 pending",
    positive: null,
    subtext: "approval needed",
    icon: Package,
    gradient: "from-amber-500 to-orange-600",
    bgLight: "bg-amber-50",
    textColor: "text-amber-600",
  },
  {
    title: "Active Engineers",
    value: "46",
    change: "+3",
    positive: true,
    subtext: "across 12 regions",
    icon: Users,
    gradient: "from-emerald-500 to-teal-600",
    bgLight: "bg-emerald-50",
    textColor: "text-emerald-600",
  },
];

export default function DashboardPage() {
  const { expanded, toggle } = useNavSections();

  return (
    <div className="space-y-8 min-h-full">
      {/* Page Header */}
      <div className="flex items-center justify-between pt-1">
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Good evening, Admin 👋</h1>
          <p className="text-slate-500 text-sm mt-1">Here&apos;s what&apos;s happening across your service network.</p>
        </div>
        <div className="hidden md:flex items-center gap-2 text-sm text-slate-500 border border-slate-200 rounded-xl px-3 py-2 bg-white shadow-sm">
          <Clock className="w-4 h-4" />
          <span>Last updated: Just now</span>
        </div>
      </div>

      {/* Legacy admindashboard.jsp structure — collapsible groups (same as sidebar) */}
      <section className="space-y-4" aria-label="Quick navigation by area">
        {dashboardNavSections.map((section) => {
          const isOpen = expanded[section.id];
          return (
            <div
              key={section.id}
              className="rounded-2xl border border-slate-100 bg-slate-50/40 overflow-hidden"
            >
              <button
                type="button"
                onClick={() => toggle(section.id)}
                aria-expanded={isOpen}
                className="flex w-full items-center gap-3 px-4 py-3.5 text-left transition-colors hover:bg-slate-100/80"
              >
                <ChevronDown
                  className={cn(
                    "h-5 w-5 shrink-0 text-slate-500 transition-transform duration-200",
                    !isOpen && "-rotate-90"
                  )}
                  aria-hidden
                />
                <h2
                  className={cn(
                    "text-xs font-semibold tracking-widest uppercase flex-1",
                    section.id === "master" && "text-slate-600",
                    section.id === "services" && "text-indigo-700",
                    section.id === "activity" && "text-sky-800"
                  )}
                >
                  {section.title}
                </h2>
                <span className="text-[11px] text-slate-400 tabular-nums">
                  {section.items.length} pages
                </span>
              </button>
              <div
                hidden={!isOpen}
                className="border-t border-slate-100 bg-white px-4 pb-4 pt-1"
              >
                <div
                  className={cn(
                    "grid gap-3 pt-3",
                    section.id === "master" && "grid-cols-2 sm:grid-cols-4",
                    section.id === "services" && "grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-7",
                    section.id === "activity" && "grid-cols-2 sm:grid-cols-4"
                  )}
                >
                  {section.items.map((item) => (
                    <Link
                      key={item.href}
                      href={item.href}
                      className="group flex flex-col rounded-xl border border-slate-100 bg-white p-4 shadow-sm transition-all hover:border-slate-200 hover:shadow-md"
                    >
                      <div
                        className={cn(
                          "mb-3 flex h-10 w-10 items-center justify-center rounded-xl bg-gradient-to-br text-white shadow-sm",
                          item.gradient
                        )}
                      >
                        <item.icon className="h-5 w-5" aria-hidden />
                      </div>
                      <span className="text-sm font-semibold text-slate-900 group-hover:text-indigo-700">
                        {item.label}
                      </span>
                      {item.description && (
                        <span className="mt-1 line-clamp-2 text-xs text-slate-500">{item.description}</span>
                      )}
                    </Link>
                  ))}
                </div>
              </div>
            </div>
          );
        })}
      </section>

      {/* Stat Cards */}
      <div className="grid gap-4 grid-cols-1 sm:grid-cols-2 xl:grid-cols-4">
        {statCards.map((card, i) => (
          <div
            key={i}
            className="relative overflow-hidden rounded-2xl bg-white border border-slate-100 shadow-sm hover:shadow-md transition-shadow group"
          >
            <div className="p-5">
              <div className="flex items-start justify-between mb-4">
                <p className="text-sm font-medium text-slate-500">{card.title}</p>
                <div className={`w-10 h-10 rounded-xl flex items-center justify-center bg-gradient-to-br ${card.gradient} shadow-md`}>
                  <card.icon className="w-5 h-5 text-white" />
                </div>
              </div>
              <div className="flex items-end justify-between">
                <div>
                  <p className="text-3xl font-bold text-slate-900 leading-none">{card.value}</p>
                  <div className="flex items-center gap-1 mt-2">
                    {card.positive === true && <ArrowUpRight className="w-3.5 h-3.5 text-emerald-500" />}
                    {card.positive === false && <ArrowDownRight className="w-3.5 h-3.5 text-red-400" />}
                    {card.positive === null && <TrendingUp className="w-3.5 h-3.5 text-amber-500" />}
                    <span className={`text-xs font-semibold ${card.positive === true ? "text-emerald-600" : card.positive === false ? "text-red-500" : "text-amber-600"}`}>
                      {card.change}
                    </span>
                    <span className="text-xs text-slate-400">{card.subtext}</span>
                  </div>
                </div>
              </div>
            </div>
            {/* Bottom gradient accent */}
            <div className={`absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r ${card.gradient} opacity-60`} />
          </div>
        ))}
      </div>

      {/* Charts Row */}
      <div className="grid gap-4 grid-cols-1 lg:grid-cols-5">
        {/* Bar Chart — wider */}
        <Card className="col-span-3 border-slate-100 shadow-sm rounded-2xl">
          <CardHeader className="pb-2 flex flex-row items-center justify-between">
            <div>
              <CardTitle className="text-[15px] font-semibold text-slate-800">Service Overview</CardTitle>
              <p className="text-xs text-slate-400 mt-0.5">Monthly service calls vs spares</p>
            </div>
            <Badge variant="outline" className="text-violet-600 border-violet-200 bg-violet-50 text-xs">This Year</Badge>
          </CardHeader>
          <CardContent className="pt-0 pl-1">
            <ResponsiveContainer width="100%" height={260}>
              <BarChart data={monthlyData} barSize={20} barGap={4}>
                <CartesianGrid strokeDasharray="3 3" stroke="#f1f5f9" vertical={false} />
                <XAxis dataKey="name" tick={{ fontSize: 11, fill: "#94a3b8" }} axisLine={false} tickLine={false} />
                <YAxis tick={{ fontSize: 11, fill: "#94a3b8" }} axisLine={false} tickLine={false} />
                <Tooltip
                  contentStyle={{ borderRadius: "12px", border: "1px solid #e2e8f0", boxShadow: "0 4px 20px rgba(0,0,0,0.08)", fontSize: 12 }}
                  cursor={{ fill: "#f8fafc" }}
                />
                <Bar dataKey="services" name="Services" fill="url(#serviceGradient)" radius={[6, 6, 0, 0]} />
                <Bar dataKey="spares" name="Spares" fill="url(#sparesGradient)" radius={[6, 6, 0, 0]} />
                <defs>
                  <linearGradient id="serviceGradient" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="0%" stopColor="#8b5cf6" />
                    <stop offset="100%" stopColor="#6366f1" />
                  </linearGradient>
                  <linearGradient id="sparesGradient" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="0%" stopColor="#06b6d4" />
                    <stop offset="100%" stopColor="#0ea5e9" />
                  </linearGradient>
                </defs>
              </BarChart>
            </ResponsiveContainer>
            <div className="flex items-center gap-5 px-2 pt-2">
              <div className="flex items-center gap-1.5 text-xs text-slate-500">
                <div className="w-3 h-3 rounded-sm bg-violet-500" />
                Service calls
              </div>
              <div className="flex items-center gap-1.5 text-xs text-slate-500">
                <div className="w-3 h-3 rounded-sm bg-cyan-500" />
                Spares requests
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Area Chart */}
        <Card className="col-span-2 border-slate-100 shadow-sm rounded-2xl">
          <CardHeader className="pb-2">
            <CardTitle className="text-[15px] font-semibold text-slate-800">Trend</CardTitle>
            <p className="text-xs text-slate-400">Service volume trend</p>
          </CardHeader>
          <CardContent className="pt-0 pl-1">
            <ResponsiveContainer width="100%" height={260}>
              <AreaChart data={monthlyData}>
                <defs>
                  <linearGradient id="areaGradient" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="0%" stopColor="#8b5cf6" stopOpacity={0.35} />
                    <stop offset="100%" stopColor="#8b5cf6" stopOpacity={0.02} />
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" stroke="#f1f5f9" vertical={false} />
                <XAxis dataKey="name" tick={{ fontSize: 11, fill: "#94a3b8" }} axisLine={false} tickLine={false} />
                <YAxis tick={{ fontSize: 11, fill: "#94a3b8" }} axisLine={false} tickLine={false} />
                <Tooltip
                  contentStyle={{ borderRadius: "12px", border: "1px solid #e2e8f0", boxShadow: "0 4px 20px rgba(0,0,0,0.08)", fontSize: 12 }}
                />
                <Area
                  type="monotone"
                  dataKey="services"
                  stroke="#8b5cf6"
                  strokeWidth={2.5}
                  fill="url(#areaGradient)"
                  dot={{ fill: "#8b5cf6", strokeWidth: 2, r: 4, stroke: "#fff" }}
                  activeDot={{ r: 6 }}
                />
              </AreaChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>

      {/* Recent Activity Table */}
      <Card className="border-slate-100 shadow-sm rounded-2xl">
        <CardHeader className="flex flex-row items-center justify-between pb-3">
          <div>
            <CardTitle className="text-[15px] font-semibold text-slate-800">Recent Service Calls</CardTitle>
            <p className="text-xs text-slate-400 mt-0.5">Latest 5 service records</p>
          </div>
          <button className="text-xs font-medium text-violet-600 hover:text-violet-800 transition-colors">View all →</button>
        </CardHeader>
        <CardContent className="p-0 pb-2">
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="border-y border-slate-100 bg-slate-50/60">
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-6 py-3">Ref No.</th>
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-4 py-3">Customer</th>
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-4 py-3">Engineer</th>
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-4 py-3">Product</th>
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-4 py-3">Region</th>
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-4 py-3">Status</th>
                  <th className="text-left text-[11px] font-semibold tracking-wide text-slate-400 uppercase px-4 py-3">Date</th>
                </tr>
              </thead>
              <tbody>
                {recentActivity.map((row, i) => (
                  <tr key={i} className="border-b border-slate-50 hover:bg-violet-50/30 transition-colors group">
                    <td className="px-6 py-3.5 font-mono text-xs font-semibold text-violet-600">{row.id}</td>
                    <td className="px-4 py-3.5 font-medium text-slate-800">{row.customer}</td>
                    <td className="px-4 py-3.5 text-slate-600">{row.engineer}</td>
                    <td className="px-4 py-3.5 text-slate-600">{row.product}</td>
                    <td className="px-4 py-3.5 text-slate-500 text-xs">{row.region}</td>
                    <td className="px-4 py-3.5">
                      <Badge
                        variant="outline"
                        className={`text-xs font-medium rounded-full px-2.5 py-0.5 ${statusConfig[row.status].className}`}
                      >
                        {statusConfig[row.status].label}
                      </Badge>
                    </td>
                    <td className="px-4 py-3.5 text-slate-400 text-xs whitespace-nowrap">{row.date}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
