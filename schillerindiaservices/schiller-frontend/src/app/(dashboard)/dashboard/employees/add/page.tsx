"use client";

import { useState } from "react";
import Link from "next/link";
import { ArrowLeft, Save } from "lucide-react";

const departments = ["Engineering", "FQC", "Service Coordinator", "Admin", "Repair"];
const regions = ["Chennai", "Bangalore", "Delhi", "Mumbai", "Kolkata", "Hyderabad", "Pune"];
const roles = ["Service Engineer", "Senior Engineer", "FQC Engineer", "Team Lead", "Admin"];

export default function AddEmployeePage() {
  const [saved, setSaved] = useState(false);
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSaved(true);
    setTimeout(() => setSaved(false), 3000);
  };

  return (
    <div className="space-y-6 max-w-3xl">
      <div className="flex items-center gap-4">
        <Link href="/dashboard/employees" className="p-2 rounded-xl hover:bg-slate-100 text-slate-500 transition">
          <ArrowLeft className="w-5 h-5" />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Add Employee</h1>
          <p className="text-slate-500 text-sm mt-0.5">Register a new service engineer or staff member</p>
        </div>
      </div>

      {saved && (
        <div className="px-4 py-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-700 text-sm font-medium">
          ✅ Employee registered successfully! (Demo)
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-5">
        <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-4">
          <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-2 border-b border-slate-100">Personal Details</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <F label="First Name *" name="firstName" placeholder="e.g. Ramesh" required />
            <F label="Last Name *" name="lastName" placeholder="e.g. Kumar" required />
            <F label="Email *" name="email" type="email" placeholder="ramesh@schiller.com" required />
            <F label="Phone *" name="phone" placeholder="+91 98400 XXXXX" required />
            <F label="Date of Joining" name="doj" type="date" defaultValue="2026-03-14" />
            <F label="Employee ID" name="empId" placeholder="Auto-generated" disabled />
          </div>
        </div>

        <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-4">
          <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-2 border-b border-slate-100">Work Details</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <S label="Role *" name="role" options={roles} required />
            <S label="Department *" name="dept" options={departments} required />
            <S label="Assigned Region *" name="region" options={regions} required />
            <F label="Username" name="username" placeholder="ramesh.k" />
            <F label="Password" name="password" type="password" placeholder="Set initial password" />
            <F label="Confirm Password" name="confirmPassword" type="password" placeholder="Re-enter password" />
          </div>
        </div>

        <div className="flex gap-3 justify-end">
          <Link href="/dashboard/employees" className="px-5 py-2.5 rounded-xl border border-slate-200 text-slate-600 text-sm font-medium hover:bg-slate-50 transition">
            Cancel
          </Link>
          <button type="submit" className="flex items-center gap-2 px-5 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 transition-all"
            style={{ background: "linear-gradient(135deg, #ec4899, #f43f5e)" }}>
            <Save className="w-4 h-4" /> Register Employee
          </button>
        </div>
      </form>
    </div>
  );
}

function F({ label, name, placeholder, type = "text", disabled = false, required = false, defaultValue }: {
  label: string; name: string; placeholder?: string; type?: string; disabled?: boolean; required?: boolean; defaultValue?: string;
}) {
  return (
    <div>
      <label className="block text-xs font-semibold text-slate-600 mb-1.5">{label}</label>
      <input name={name} type={type} placeholder={placeholder} disabled={disabled} required={required} defaultValue={defaultValue}
        className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 bg-slate-50/60 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-pink-200 focus:border-pink-400 disabled:opacity-50 transition" />
    </div>
  );
}

function S({ label, name, options, required = false }: { label: string; name: string; options: string[]; required?: boolean }) {
  return (
    <div>
      <label className="block text-xs font-semibold text-slate-600 mb-1.5">{label}</label>
      <select name={name} required={required} className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 bg-slate-50/60 focus:outline-none focus:ring-2 focus:ring-pink-200 focus:border-pink-400 transition appearance-none">
        <option value="">Select...</option>
        {options.map(o => <option key={o}>{o}</option>)}
      </select>
    </div>
  );
}
