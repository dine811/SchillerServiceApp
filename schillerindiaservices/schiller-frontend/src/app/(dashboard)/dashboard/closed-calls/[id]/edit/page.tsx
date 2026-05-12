"use client";

import { useCallback, useEffect, useState } from "react";
import Link from "next/link";
import { useParams, useRouter, useSearchParams } from "next/navigation";
import { ArrowLeft, CheckCircle2, Loader2, Save } from "lucide-react";
import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { ServiceService } from "@/services/service-service";

function toIsoDate(s: string | undefined): string {
  if (!s) return "";
  const m = /^(\d{4}-\d{2}-\d{2})/.exec(String(s).trim());
  return m ? m[1] : "";
}

/** Legacy ClosedCalls.jsp → CallRegisterController?action=edit — same fields as CallRegisterDao.update (demo). */
export default function EditClosedCallPage() {
  const params = useParams();
  const router = useRouter();
  const searchParams = useSearchParams();
  const id = parseInt(params.id as string, 10);
  const source = (searchParams.get("source") || "").toLowerCase();
  const fromCallList = source === "call-list";
  const backHref = fromCallList ? "/dashboard/call-list" : "/dashboard/closed-calls";

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [division, setDivision] = useState("");
  const [entryDate, setEntryDate] = useState("");
  const [scEnggName, setScEnggName] = useState("");
  const [engineer, setEngineer] = useState("");
  const [model, setModel] = useState("");

  const [callDate, setCallDate] = useState("");
  const [callType, setCallType] = useState("");
  const [typeOfCall, setTypeOfCall] = useState("");
  const [typeOfCommunication, setTypeOfCommunication] = useState("");
  const [duration, setDuration] = useState("");
  const [remarks, setRemarks] = useState("");
  const [statusType, setStatusType] = useState("");

  const load = useCallback(async () => {
    if (Number.isNaN(id) || id < 1) {
      setError("Invalid record id");
      setLoading(false);
      return;
    }
    try {
      setLoading(true);
      setError(null);
      const r = await ServiceService.getCallRegister(id);
      setDivision(r.division ?? "");
      setEntryDate(r.entryDate ?? "");
      setScEnggName(r.scEnggName ?? r.scEngg ?? "");
      setEngineer(r.engineer ?? "");
      setModel(r.model ?? "");
      setCallDate(toIsoDate(r.callDate));
      setCallType(r.callType ?? "");
      setTypeOfCall(r.typeOfCall ?? "");
      setTypeOfCommunication(r.typeOfCommunication ?? "");
      setDuration(r.duration ?? "");
      setRemarks(r.remarks ?? "");
      setStatusType(r.statusType ?? "");
    } catch (e) {
      setError(e instanceof Error ? e.message : "Failed to load");
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    load();
  }, [load]);

  const handleSave = async () => {
    if (Number.isNaN(id) || id < 1) return;
    try {
      setSaving(true);
      setError(null);
      await ServiceService.updateCallRegister(id, {
        callDate: callDate.trim(),
        callType: callType.trim(),
        typeOfCall: typeOfCall.trim(),
        typeOfCommunication: typeOfCommunication.trim(),
        duration: duration.trim(),
        remarks: remarks.trim(),
        statusType: statusType.trim(),
      });
      router.push(backHref);
    } catch (e) {
      setError(e instanceof Error ? e.message : "Save failed");
    } finally {
      setSaving(false);
    }
  };

  const today = new Date();
  const tenYearsAgo = new Date();
  tenYearsAgo.setFullYear(tenYearsAgo.getFullYear() - 10);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[40vh] text-slate-500 gap-2">
        <Loader2 className="w-6 h-6 animate-spin" />
        Loading…
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-3xl">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div className="flex items-center gap-3">
          <CheckCircle2 className="w-8 h-8 text-emerald-600 shrink-0" />
          <div>
            <h1 className="text-2xl font-bold text-slate-900 tracking-tight">
              {fromCallList ? "Update call register" : "Update closed call"}
            </h1>
            <p className="text-slate-400 text-xs mt-0.5">Record id {id}</p>
          </div>
        </div>
        <Link
          href={backHref}
          className="inline-flex items-center gap-2 text-sm font-medium text-slate-600 hover:text-slate-900"
        >
          <ArrowLeft className="w-4 h-4" />
          {fromCallList ? "Back to call register" : "Back to closed calls"}
        </Link>
      </div>

      {error && (
        <div className="rounded-xl border border-red-200 bg-red-50 text-red-800 px-4 py-3 text-sm">{error}</div>
      )}

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-5 space-y-6">
        <div>
          <h2 className="text-sm font-semibold text-slate-800 mb-3">Read-only</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-sm">
            <div>
              <span className="text-slate-500 text-xs uppercase tracking-wide">Division</span>
              <p className="text-slate-800 font-medium">{division || "—"}</p>
            </div>
            <div>
              <span className="text-slate-500 text-xs uppercase tracking-wide">Entry date</span>
              <p className="text-slate-800 font-medium">{entryDate || "—"}</p>
            </div>
            <div>
              <span className="text-slate-500 text-xs uppercase tracking-wide">SC engineer</span>
              <p className="text-slate-800 font-medium">{scEnggName || "—"}</p>
            </div>
            <div>
              <span className="text-slate-500 text-xs uppercase tracking-wide">Engineer</span>
              <p className="text-slate-800 font-medium">{engineer || "—"}</p>
            </div>
            <div className="sm:col-span-2">
              <span className="text-slate-500 text-xs uppercase tracking-wide">Model</span>
              <p className="text-slate-800 font-medium">{model || "—"}</p>
            </div>
          </div>
        </div>

        <div className="border-t border-slate-100 pt-5">
          <h2 className="text-sm font-semibold text-slate-800 mb-3">Editable (legacy ClosedCalls update)</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <DatePicker
              label="Call date"
              value={callDate}
              onChange={setCallDate}
              placeholder="yyyy-MM-dd"
              minDate={tenYearsAgo}
              maxDate={today}
            />
            <div className="space-y-1.5">
              <label className="block text-xs font-semibold text-slate-600">Call type</label>
              <input
                value={callType}
                onChange={(e) => setCallType(e.target.value)}
                className="w-full px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-300"
                placeholder="e.g. Received"
              />
            </div>
            <div className="space-y-1.5">
              <label className="block text-xs font-semibold text-slate-600">Type of call</label>
              <input
                value={typeOfCall}
                onChange={(e) => setTypeOfCall(e.target.value)}
                className="w-full px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-300"
                placeholder="e.g. Technical"
              />
            </div>
            <div className="space-y-1.5">
              <label className="block text-xs font-semibold text-slate-600">Type of communication</label>
              <input
                value={typeOfCommunication}
                onChange={(e) => setTypeOfCommunication(e.target.value)}
                className="w-full px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-300"
                placeholder="e.g. Telephonic"
              />
            </div>
            <div className="space-y-1.5">
              <label className="block text-xs font-semibold text-slate-600">Duration</label>
              <input
                value={duration}
                onChange={(e) => setDuration(e.target.value)}
                className="w-full px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-300"
                placeholder="e.g. 02 : 10"
              />
            </div>
            <div className="space-y-1.5">
              <label className="block text-xs font-semibold text-slate-600">Status</label>
              <input
                value={statusType}
                onChange={(e) => setStatusType(e.target.value)}
                className="w-full px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-300"
                placeholder="e.g. Completed"
              />
            </div>
            <div className="sm:col-span-2 space-y-1.5">
              <label className="block text-xs font-semibold text-slate-600">Remarks</label>
              <textarea
                value={remarks}
                onChange={(e) => setRemarks(e.target.value)}
                rows={4}
                className="w-full px-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-300 resize-y min-h-[100px]"
                placeholder="Remarks"
              />
            </div>
          </div>
        </div>

        <div className="flex flex-wrap gap-2 pt-2">
          <Button
            type="button"
            onClick={handleSave}
            disabled={saving}
            className="rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white"
          >
            {saving ? <Loader2 className="w-4 h-4 animate-spin mr-2" /> : <Save className="w-4 h-4 mr-2" />}
            Save
          </Button>
          <Button type="button" variant="outline" className="rounded-xl" asChild>
            <Link href="/dashboard/closed-calls">Cancel</Link>
          </Button>
        </div>
      </div>
    </div>
  );
}
