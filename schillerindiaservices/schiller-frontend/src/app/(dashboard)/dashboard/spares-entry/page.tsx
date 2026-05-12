"use client";

import { useState } from "react";
import Link from "next/link";
import { ArrowLeft, Boxes, Loader2 } from "lucide-react";
import { ServiceService } from "@/services/service-service";

export default function SparesEntryPage() {
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // Capture before any await — React clears e.currentTarget after the sync tick, so async .reset() would throw.
    const form = e.currentTarget;
    setError(null);
    setSuccess(false);
    const fd = new FormData(form);
    const partNumber = String(fd.get("part_no") ?? "").trim();
    const brdName = String(fd.get("brd_name") ?? "").trim();
    const compatibleModels = String(fd.get("compatible_models") ?? "").trim();
    const costRaw = String(fd.get("cost") ?? "").trim();
    const cost = Number(costRaw);
    if (!partNumber || !brdName || !compatibleModels || Number.isNaN(cost)) {
      setError("Fill all fields with valid numbers for cost.");
      return;
    }
    setSaving(true);
    try {
      await ServiceService.createPartnumberEntry({
        partNumber,
        brdName,
        compatibleModels,
        cost,
      });
      setSuccess(true);
      form.reset();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Save failed");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="space-y-6 max-w-lg">
      <div className="flex items-center gap-4">
        <Link href="/dashboard" className="p-2 rounded-xl hover:bg-slate-100 text-slate-500 transition">
          <ArrowLeft className="w-5 h-5" />
        </Link>
        <div>
          <div className="flex items-center gap-2">
            <Boxes className="w-5 h-5 text-cyan-600" />
            <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Spares entry</h1>
          </div>
          <p className="text-slate-500 text-sm mt-0.5">Legacy partNumberForm.jsp / PartEntry → partnumber_entry</p>
        </div>
      </div>

      {success && (
        <div className="px-4 py-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-800 text-sm font-medium">
          Saved to partnumber_entry.
        </div>
      )}
      {error && (
        <div className="px-4 py-3 rounded-xl bg-red-50 border border-red-200 text-red-800 text-sm" role="alert">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-4">
        <div>
          <label htmlFor="part_no" className="block text-sm font-medium text-slate-700 mb-1">
            Part number *
          </label>
          <input
            id="part_no"
            name="part_no"
            required
            maxLength={200}
            className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-cyan-200 focus:border-cyan-400"
          />
        </div>
        <div>
          <label htmlFor="brd_name" className="block text-sm font-medium text-slate-700 mb-1">
            Def mod / board name *
          </label>
          <input
            id="brd_name"
            name="brd_name"
            required
            maxLength={300}
            className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-cyan-200 focus:border-cyan-400"
          />
        </div>
        <div>
          <label htmlFor="compatible_models" className="block text-sm font-medium text-slate-700 mb-1">
            Compatible models *
          </label>
          <input
            id="compatible_models"
            name="compatible_models"
            required
            maxLength={1000}
            className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-cyan-200 focus:border-cyan-400"
          />
        </div>
        <div>
          <label htmlFor="cost" className="block text-sm font-medium text-slate-700 mb-1">
            Cost *
          </label>
          <input
            id="cost"
            name="cost"
            type="number"
            step="0.01"
            min="0"
            required
            className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-cyan-200 focus:border-cyan-400"
          />
        </div>
        <div className="pt-2 flex justify-center">
          <button
            type="submit"
            disabled={saving}
            className="inline-flex items-center justify-center gap-2 px-8 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 disabled:opacity-60 transition"
            style={{ background: "linear-gradient(135deg, #06b6d4, #0891b2)" }}
          >
            {saving ? <Loader2 className="w-4 h-4 animate-spin" /> : null}
            Save
          </button>
        </div>
      </form>
    </div>
  );
}
