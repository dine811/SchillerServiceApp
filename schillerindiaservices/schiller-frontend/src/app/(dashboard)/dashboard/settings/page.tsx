"use client";

import { useState } from "react";
import { Save, Mail, Bell, Shield, Database, ChevronRight } from "lucide-react";

const sections = [
  { label: "SMTP & Email", icon: Mail, color: "text-violet-600", bg: "bg-violet-50" },
  { label: "Notifications", icon: Bell, color: "text-amber-600", bg: "bg-amber-50" },
  { label: "Security", icon: Shield, color: "text-sky-600", bg: "bg-sky-50" },
  { label: "Dropdown Values", icon: Database, color: "text-emerald-600", bg: "bg-emerald-50" },
];

export default function SettingsPage() {
  const [saved, setSaved] = useState(false);
  const [active, setActive] = useState("SMTP & Email");

  const handleSave = (e: React.FormEvent) => {
    e.preventDefault();
    setSaved(true);
    setTimeout(() => setSaved(false), 3000);
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Settings</h1>
        <p className="text-slate-500 text-sm mt-1">Configure system preferences and integrations</p>
      </div>

      <div className="flex gap-5">
        {/* Settings Nav */}
        <div className="w-56 shrink-0 space-y-1">
          {sections.map(s => (
            <button key={s.label} onClick={() => setActive(s.label)}
              className={`w-full flex items-center gap-3 px-3.5 py-2.5 rounded-xl text-sm font-medium transition-all text-left ${active === s.label ? "bg-slate-900 text-white shadow-sm" : "text-slate-600 hover:bg-slate-100"}`}>
              <div className={`w-7 h-7 rounded-lg flex items-center justify-center ${active === s.label ? "bg-white/10" : s.bg}`}>
                <s.icon className={`w-3.5 h-3.5 ${active === s.label ? "text-white" : s.color}`} />
              </div>
              <span className="flex-1">{s.label}</span>
              <ChevronRight className={`w-3.5 h-3.5 ${active === s.label ? "text-white/60" : "text-slate-300"}`} />
            </button>
          ))}
        </div>

        {/* Right Panel */}
        <div className="flex-1">
          {saved && (
            <div className="mb-4 px-4 py-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-700 text-sm font-medium">
              ✅ Settings saved successfully! (Demo)
            </div>
          )}

          {active === "SMTP & Email" && (
            <form onSubmit={handleSave} className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-5">
              <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-2 border-b border-slate-100">SMTP Configuration</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <F label="SMTP Host" name="host" placeholder="smtp.gmail.com" defaultValue="smtp.gmail.com" />
                <F label="SMTP Port" name="port" placeholder="587" defaultValue="587" />
                <F label="Email Username" name="user" placeholder="alerts@schiller.com" />
                <F label="Email Password" name="pass" type="password" placeholder="••••••••" />
                <F label="From Name" name="fromName" placeholder="Schiller India Services" className="md:col-span-2" />
              </div>
              <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pt-2 pb-2 border-b border-slate-100">Auto-Escalation</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <F label="Escalation After (hours)" name="escalateHours" placeholder="48" defaultValue="48" />
                <F label="Escalation Email" name="escalateEmail" placeholder="manager@schiller.com" />
              </div>
              <button type="submit" className="flex items-center gap-2 px-5 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 transition-all"
                style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}>
                <Save className="w-4 h-4" /> Save Settings
              </button>
            </form>
          )}

          {active === "Notifications" && (
            <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-4">
              <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-2 border-b border-slate-100">Notification Preferences</h2>
              {[
                ["New service call assigned", true],
                ["Escalation triggered", true],
                ["Spares request pending approval", true],
                ["Daily summary email", false],
                ["Weekly report digest", false],
              ].map(([label, checked], i) => (
                <div key={i} className="flex items-center justify-between py-2 border-b border-slate-50 last:border-0">
                  <p className="text-sm text-slate-700">{label as string}</p>
                  <label className="relative inline-flex items-center cursor-pointer">
                    <input type="checkbox" defaultChecked={checked as boolean} className="sr-only peer" />
                    <div className="w-10 h-5 bg-slate-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-0.5 after:left-0.5 after:bg-white after:rounded-full after:h-4 after:w-4 after:transition-all peer-checked:bg-violet-500"></div>
                  </label>
                </div>
              ))}
              <button onClick={handleSave} className="flex items-center gap-2 px-5 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 transition-all mt-2"
                style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}>
                <Save className="w-4 h-4" /> Save Preferences
              </button>
            </div>
          )}

          {active === "Security" && (
            <form onSubmit={handleSave} className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-5">
              <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-2 border-b border-slate-100">Security Settings</h2>
              <div className="grid grid-cols-1 gap-4">
                <F label="JWT Secret Key" name="jwtSecret" placeholder="Enter new JWT secret (min 32 chars)" />
                <F label="Token Expiry (hours)" name="tokenExpiry" placeholder="24" defaultValue="24" />
                <div className="flex items-center justify-between p-3 bg-slate-50 rounded-xl">
                  <div>
                    <p className="text-sm font-medium text-slate-700">Force 2FA for Admin</p>
                    <p className="text-xs text-slate-400 mt-0.5">Require 2-factor auth for admin accounts</p>
                  </div>
                  <label className="relative inline-flex items-center cursor-pointer">
                    <input type="checkbox" className="sr-only peer" />
                    <div className="w-10 h-5 bg-slate-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-0.5 after:left-0.5 after:bg-white after:rounded-full after:h-4 after:w-4 after:transition-all peer-checked:bg-violet-500"></div>
                  </label>
                </div>
              </div>
              <button type="submit" className="flex items-center gap-2 px-5 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 transition-all"
                style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}>
                <Save className="w-4 h-4" /> Save Security
              </button>
            </form>
          )}

          {active === "Dropdown Values" && (
            <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-4">
              <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-2 border-b border-slate-100">Manage Dropdown Options</h2>
              {[
                { label: "Unit Status", values: ["Working", "Not Working", "Under Repair", "Scrap"] },
                { label: "Repair Type", values: ["Repair", "PM", "Installation", "Demo", "Warranty"] },
                { label: "Regions", values: ["Chennai", "Bangalore", "Delhi", "Mumbai", "Kolkata"] },
              ].map(group => (
                <div key={group.label} className="space-y-2">
                  <p className="text-xs font-semibold text-slate-600">{group.label}</p>
                  <div className="flex flex-wrap gap-2">
                    {group.values.map(v => (
                      <span key={v} className="px-2.5 py-1 bg-slate-100 text-slate-700 text-xs rounded-lg font-medium">{v}</span>
                    ))}
                    <button className="px-2.5 py-1 bg-violet-100 text-violet-600 text-xs rounded-lg font-medium hover:bg-violet-200 transition">+ Add</button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

function F({ label, name, placeholder, type = "text", defaultValue, className = "" }: {
  label: string; name: string; placeholder?: string; type?: string; defaultValue?: string; className?: string;
}) {
  return (
    <div className={className}>
      <label className="block text-xs font-semibold text-slate-600 mb-1.5">{label}</label>
      <input name={name} type={type} placeholder={placeholder} defaultValue={defaultValue}
        className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 bg-slate-50/60 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-violet-300 focus:border-violet-400 transition" />
    </div>
  );
}
