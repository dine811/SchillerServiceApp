"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useParams } from "next/navigation";
import { ArrowLeft, ClipboardList, Loader2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { ServiceService, type JobSheetPayload, type ServiceRecord } from "@/services/service-service";

/** Normalize legacy text (dd-mm-yyyy, dd/mm/yyyy) to yyyy-MM-dd for HTML date inputs. */
function parseRepairDateToIso(raw: string | null | undefined): string {
  if (!raw || !String(raw).trim()) return "";
  const s = String(raw).trim();
  if (/^\d{4}-\d{2}-\d{2}$/.test(s)) return s;
  const m = s.match(/^(\d{1,2})[-/](\d{1,2})[-/](\d{4})$/);
  if (m) {
    const d = m[1].padStart(2, "0");
    const mo = m[2].padStart(2, "0");
    return `${m[3]}-${mo}-${d}`;
  }
  return "";
}

const REPAIR_DATE_KEYS: (keyof JobSheetPayload)[] = [
  "repairDate",
  "repairDate1",
  "repairDate2",
  "repairDate3",
  "repairDate4",
];

function normalizeJobSheetDates(payload: JobSheetPayload): JobSheetPayload {
  const out = { ...payload };
  for (const key of REPAIR_DATE_KEYS) {
    const v = out[key];
    if (typeof v === "string" && v) {
      const iso = parseRepairDateToIso(v);
      (out as Record<string, unknown>)[key] = iso || "";
    }
  }
  return out;
}

function emptySheet(serId: number): JobSheetPayload {
  return {
    serId,
    repairDate: "",
    enginnerName: "",
    observation: "",
    repairActivity: "",
    timeSpent: "",
    remark: "",
    repairDate1: "",
    enginnerName1: "",
    observation1: "",
    repairActivity1: "",
    timeSpent1: "",
    remark1: "",
    repairDate2: "",
    enginnerName2: "",
    observation2: "",
    repairActivity2: "",
    timeSpent2: "",
    remark2: "",
    repairDate3: "",
    enginnerName3: "",
    observation3: "",
    repairActivity3: "",
    timeSpent3: "",
    remark3: "",
    repairDate4: "",
    enginnerName4: "",
    observation4: "",
    repairActivity4: "",
    timeSpent4: "",
    remark4: "",
  };
}

const ROW_SUFFIXES = ["", "1", "2", "3", "4"] as const;

function rowKeys(suf: (typeof ROW_SUFFIXES)[number]) {
  const s = suf;
  return {
    repairDate: (`repairDate${s === "" ? "" : s}`) as keyof JobSheetPayload,
    enginnerName: (`enginnerName${s === "" ? "" : s}`) as keyof JobSheetPayload,
    observation: (`observation${s === "" ? "" : s}`) as keyof JobSheetPayload,
    repairActivity: (`repairActivity${s === "" ? "" : s}`) as keyof JobSheetPayload,
    timeSpent: (`timeSpent${s === "" ? "" : s}`) as keyof JobSheetPayload,
    remark: (`remark${s === "" ? "" : s}`) as keyof JobSheetPayload,
  };
}

export default function JobSheetPage() {
  const params = useParams();
  const id = Number(params.id);
  const [service, setService] = useState<ServiceRecord | null>(null);
  const [form, setForm] = useState<JobSheetPayload | null>(null);
  const [jobSheetId, setJobSheetId] = useState<number | null>(null);
  const [serviceStatus, setServiceStatus] = useState<string>("Pending");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [saveMessage, setSaveMessage] = useState<string | null>(null);

  useEffect(() => {
    if (!Number.isFinite(id)) return;
    let cancelled = false;
    (async () => {
      try {
        setLoading(true);
        const [svc, js] = await Promise.all([
          ServiceService.getService(id),
          ServiceService.getJobSheetLatest(id).catch(() => null),
        ]);
        if (cancelled) return;
        setService(svc);
        const st = (svc.status || "").trim();
        setServiceStatus(st === "Completed" ? "Completed" : "Pending");
        if (js) {
          setJobSheetId(js.id ?? null);
          setForm(normalizeJobSheetDates({ ...emptySheet(id), ...js, serId: id }));
        } else {
          setForm(emptySheet(id));
        }
      } catch (e) {
        if (!cancelled) setError(e instanceof Error ? e.message : "Failed to load");
      } finally {
        if (!cancelled) setLoading(false);
      }
    })();
    return () => {
      cancelled = true;
    };
  }, [id]);

  useEffect(() => {
    if (!saveMessage) return;
    const t = window.setTimeout(() => setSaveMessage(null), 6000);
    return () => window.clearTimeout(t);
  }, [saveMessage]);

  const updateField = (key: keyof JobSheetPayload, value: string) => {
    setForm((f) => (f ? { ...f, [key]: value } : f));
  };

  const handleSave = async () => {
    if (!form) return;
    setSaving(true);
    setError(null);
    setSaveMessage(null);
    try {
      const payload: JobSheetPayload = {
        ...form,
        serId: id,
        serviceStatus,
      };
      const wasUpdate = Boolean(jobSheetId);
      if (jobSheetId) {
        const saved = await ServiceService.updateJobSheet(jobSheetId, payload);
        setForm(normalizeJobSheetDates({ ...form, ...saved }));
        setJobSheetId(saved.id ?? jobSheetId);
      } else {
        const saved = await ServiceService.createJobSheet(payload);
        setJobSheetId(saved.id ?? null);
        setForm(normalizeJobSheetDates({ ...form, ...saved }));
      }
      setSaveMessage(wasUpdate ? "Job sheet updated successfully." : "Job sheet saved successfully.");
    } catch (e) {
      setError(e instanceof Error ? e.message : "Save failed");
    } finally {
      setSaving(false);
    }
  };

  const statusEditable =
    !service?.status ||
    String(service.status).trim() === "" ||
    String(service.status).trim().toLowerCase() === "pending";

  if (loading || !form) {
    return (
      <div className="flex items-center justify-center min-h-[320px] text-slate-500 gap-2">
        <Loader2 className="w-5 h-5 animate-spin" /> Loading job sheet…
      </div>
    );
  }

  if (!service) {
    return (
      <div className="rounded-2xl border border-amber-200 bg-amber-50 text-amber-900 px-5 py-4 text-sm">
        Service record could not be loaded. Return to{" "}
        <Link href="/dashboard/services" className="font-semibold text-violet-700 underline">
          Service Master
        </Link>
        .
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-[1200px]">
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <Link
            href="/dashboard/services"
            className="inline-flex items-center gap-1.5 text-sm text-violet-600 hover:text-violet-800 mb-2"
          >
            <ArrowLeft className="w-4 h-4" /> Back to Service Master
          </Link>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight flex items-center gap-2">
            <ClipboardList className="w-7 h-7 text-violet-600" />
            Job Sheet
          </h1>
          <p className="text-slate-500 text-sm mt-1">
            Service #{id} · FRN {service.frnNo ?? "—"} · Ref {service.scRefNo ?? "—"}
          </p>
        </div>
      </div>

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-5 space-y-3 text-sm">
        <p className="font-semibold text-slate-700">Unit / service context</p>
        <div className="grid sm:grid-cols-2 gap-2 text-slate-600">
          <p>
            <span className="text-slate-400">Customer:</span> {service.custName ?? "—"}
          </p>
          <p>
            <span className="text-slate-400">Model id:</span> {service.productModel ?? "—"}
          </p>
          <p>
            <span className="text-slate-400">Defective GIR:</span> {service.defGirNo ?? "—"}
          </p>
          <p>
            <span className="text-slate-400">Def. mod/brd:</span> {service.defModBrdName ?? "—"}
          </p>
          <p className="sm:col-span-2">
            <span className="text-slate-400">Received at SC:</span>{" "}
            {service.serCentreReceivedDate ?? "—"}
          </p>
        </div>
      </div>

      {saveMessage && (
        <div className="rounded-xl border border-emerald-200 bg-emerald-50 text-emerald-900 px-4 py-3 text-sm">
          {saveMessage}
        </div>
      )}

      {error && (
        <div className="rounded-xl border border-red-200 bg-red-50 text-red-800 px-4 py-3 text-sm">{error}</div>
      )}

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-sm min-w-[900px]">
            <thead>
              <tr className="border-b border-slate-100 bg-slate-50/80">
                <th className="text-left px-3 py-2.5 text-[11px] font-semibold text-slate-500 uppercase min-w-[168px]">Repair date</th>
                <th className="text-left px-3 py-2.5 text-[11px] font-semibold text-slate-500 uppercase">Engineer</th>
                <th className="text-left px-3 py-2.5 text-[11px] font-semibold text-slate-500 uppercase">Observation</th>
                <th className="text-left px-3 py-2.5 text-[11px] font-semibold text-slate-500 uppercase">Repair activity</th>
                <th className="text-left px-3 py-2.5 text-[11px] font-semibold text-slate-500 uppercase w-24">Time spent</th>
                <th className="text-left px-3 py-2.5 text-[11px] font-semibold text-slate-500 uppercase">Remark</th>
              </tr>
            </thead>
            <tbody>
              {ROW_SUFFIXES.map((suf, idx) => {
                const k = rowKeys(suf);
                return (
                  <tr key={suf || "0"} className="border-b border-slate-50">
                    <td className="px-3 py-2 align-top min-w-[168px]">
                      <span className="sr-only">Line {idx + 1}</span>
                      <DatePicker
                        variant="compact"
                        value={parseRepairDateToIso(String(form[k.repairDate] ?? ""))}
                        onChange={(v) => updateField(k.repairDate, v)}
                        placeholder="Select date"
                      />
                    </td>
                    <td className="px-3 py-2 align-top">
                      <input
                        className="w-full rounded-lg border border-slate-200 px-2 py-1.5 text-xs"
                        value={String(form[k.enginnerName] ?? "")}
                        onChange={(e) => updateField(k.enginnerName, e.target.value)}
                      />
                    </td>
                    <td className="px-3 py-2 align-top">
                      <input
                        className="w-full rounded-lg border border-slate-200 px-2 py-1.5 text-xs"
                        value={String(form[k.observation] ?? "")}
                        onChange={(e) => updateField(k.observation, e.target.value)}
                      />
                    </td>
                    <td className="px-3 py-2 align-top">
                      <input
                        className="w-full rounded-lg border border-slate-200 px-2 py-1.5 text-xs"
                        value={String(form[k.repairActivity] ?? "")}
                        onChange={(e) => updateField(k.repairActivity, e.target.value)}
                      />
                    </td>
                    <td className="px-3 py-2 align-top">
                      <input
                        className="w-full rounded-lg border border-slate-200 px-2 py-1.5 text-xs"
                        value={String(form[k.timeSpent] ?? "")}
                        onChange={(e) => updateField(k.timeSpent, e.target.value)}
                      />
                    </td>
                    <td className="px-3 py-2 align-top">
                      <input
                        className="w-full rounded-lg border border-slate-200 px-2 py-1.5 text-xs"
                        value={String(form[k.remark] ?? "")}
                        onChange={(e) => updateField(k.remark, e.target.value)}
                      />
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>

      <div className="flex flex-wrap items-end gap-4">
        <div className="space-y-1">
          <label className="text-xs font-semibold text-slate-500 uppercase">Workflow status</label>
          <select
            className="rounded-xl border border-slate-200 px-3 py-2 text-sm bg-white min-w-[200px]"
            value={serviceStatus}
            disabled={!statusEditable}
            onChange={(e) => setServiceStatus(e.target.value)}
          >
            <option value="Pending">Pending</option>
            <option value="Completed">Completed</option>
          </select>
          {!statusEditable && (
            <p className="text-xs text-slate-400">Status is locked after completion (legacy behaviour).</p>
          )}
        </div>
        <Button
          type="button"
          onClick={handleSave}
          disabled={saving}
          className="rounded-xl bg-gradient-to-r from-violet-600 to-indigo-600 text-white"
        >
          {saving ? <Loader2 className="w-4 h-4 animate-spin mr-2" /> : null}
          Save job sheet
        </Button>
      </div>
    </div>
  );
}
