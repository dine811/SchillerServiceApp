"use client";

import { useState, useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import Link from "next/link";
import { ArrowLeft, Save, Info, Loader2 } from "lucide-react";
import { DatePicker } from "@/components/ui/date-picker";
import { Combobox } from "@/components/ui/combobox";
import { Badge } from "@/components/ui/badge";
import { apiFetch, getApiBaseUrl } from "@/lib/api";
import { REPORT_TYPES } from "@/lib/service-mock-data";
import { ServiceService, type ServiceRecord, type Model } from "@/services/service-service";
import type { DropdownOption } from "@/services/service-service";

const MOCK_USER = { name: "Vinod S.", role: "engineer", id: 2, qid: 1 };
const toOpts = (opts: DropdownOption[]) => opts.map(o => ({ value: String(o.id), label: o.ddValue }));

function nil(val: unknown): string {
  return val == null || val === "null" || val === "" ? "—" : String(val);
}

// ─── Stable field components — MUST be outside the page component ────────────
// If defined inside, React remounts inputs on every state change → 1-char bug
function Txt({ label, value, onChange, required, placeholder, disabled, readonly, uppercase }: {
  label: string; value: string; onChange: (v: string) => void;
  required?: boolean; placeholder?: string; disabled?: boolean; readonly?: boolean; uppercase?: boolean;
}) {
  return (
    <div className="space-y-1.5">
      <label className="block text-xs font-semibold text-slate-600">
        {label}{required && <span className="text-red-500 ml-0.5">*</span>}
      </label>
      <input
        value={value}
        onChange={e => !readonly && onChange(uppercase ? e.target.value.toUpperCase() : e.target.value)}
        placeholder={placeholder}
        readOnly={readonly}
        disabled={disabled}
        required={required}
        className={`w-full px-3.5 py-2.5 rounded-xl border text-sm text-slate-800 bg-white placeholder:text-slate-400
          focus:outline-none focus:ring-2 focus:ring-violet-200 focus:border-violet-400
          ${readonly || disabled ? "bg-slate-50 text-slate-500 cursor-default opacity-70 border-slate-200" : "border-slate-200"} transition`}
      />
    </div>
  );
}

function TxtA({ label, value, onChange }: {
  label: string; value: string; onChange: (v: string) => void;
}) {
  return (
    <div className="space-y-1.5">
      <label className="block text-xs font-semibold text-slate-600">{label}</label>
      <textarea
        value={value}
        onChange={e => onChange(e.target.value)}
        rows={2}
        className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 bg-white
          focus:outline-none focus:ring-2 focus:ring-violet-200 focus:border-violet-400 transition resize-none"
      />
    </div>
  );
}

// ─── Main Page Component ──────────────────────────────────────────────────────
export default function UpdateServicePage() {
  const params = useParams();
  const router = useRouter();
  const id = parseInt(params.id as string, 10);
  const isAdmin = MOCK_USER.role === "admin" || MOCK_USER.role === "ViceChancellor";

  const [record, setRecord] = useState<ServiceRecord | null>(null);
  const [engineerOpts, setEngineerOpts] = useState<{ value: string; label: string }[]>([]);
  const [dd5Options, setDd5Options] = useState<DropdownOption[]>([]);
  const [modelMap, setModelMap] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [saved, setSaved] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [f, setF] = useState({
    raEngineer: "",
    defUnitGirNo: "",
    technicalRemarks: "",
    componentsUsedForRepair: "",
    repairedBrdStkDate: "",
    repairedBrdAdvNo: "",
    finalRemarks: "",
    typeOfWork: "",
    shipDtFrmSerCntr: "",
    dispAdvNo: "",
    shipDateFromCommercial: "",
    dcNo: "",
    repGirNo: "",
    reportType: "servicecenter",
    destination: "",
  });

  useEffect(() => {
    if (!id || isNaN(id)) {
      setError("Invalid service ID");
      setLoading(false);
      return;
    }
    Promise.all([
      ServiceService.getService(id),
      apiFetch(`${getApiBaseUrl()}/employees?page=0&size=200`).then(r => r.json()),
      ServiceService.getDropdownOptions(5),
      ServiceService.getModels().then((models: Model[]) => {
        const map: Record<string, string> = {};
        models.forEach((m: Model) => { map[String(m.modelId)] = m.modelName; });
        return map;
      }),
    ])
      .then(([svc, empRes, dd5, modelMapRes]) => {
        setRecord(svc);
        setEngineerOpts((empRes.content || []).map((e: { empId: number; empName: string }) => ({ value: String(e.empId), label: e.empName })));
        setDd5Options(dd5);
        setModelMap(modelMapRes);
        const r = svc as ServiceRecord;
        setF({
          raEngineer: r.raEngineerId ? String(r.raEngineerId) : "",
          defUnitGirNo: r.defUnitGirNo ?? "",
          technicalRemarks: r.technicalRemarks ?? "",
          componentsUsedForRepair: r.componentsUsedForRepair ?? "",
          repairedBrdStkDate: r.repairedBrdStkDate ?? "",
          repairedBrdAdvNo: r.repairedBrdAdvNo ?? "",
          finalRemarks: r.finalRemarks ?? "",
          typeOfWork: r.typeOfWork ?? "",
          shipDtFrmSerCntr: r.shipDtFrmSerCntr ?? "",
          dispAdvNo: r.dispAdvNo ?? "",
          shipDateFromCommercial: r.shipDateFromCommercial ?? "",
          dcNo: r.dcNo ?? "",
          repGirNo: r.repGirNo ?? "",
          reportType: r.reportType ?? "servicecenter",
          destination: r.destination ?? "",
        });
      })
      .catch(err => setError(err instanceof Error ? err.message : "Failed to load service"))
      .finally(() => setLoading(false));
  }, [id]);

  const set = (k: keyof typeof f, v: string) => setF(prev => ({ ...prev, [k]: v }));

  // Business logic (record may be null while loading)
  const unitStatus = record?.unitStatus ?? "";
  const isOW_LAMC = unitStatus === "OW" || unitStatus === "LAMC";
  const shipFilled = f.shipDtFrmSerCntr.length > 0;
  const repairFilled = f.repairedBrdStkDate.length > 0;

  const workVariant = (() => {
    if (isOW_LAMC && !shipFilled) return "A";
    if (!isOW_LAMC && !shipFilled) return "B";
    if (shipFilled && !repairFilled) return "C";
    return "D";
  })();

  const reportTypeOptions = workVariant === "C"
    ? REPORT_TYPES.filter(r => ["servicecenter", "scraplist", "stocklist"].includes(r.id))
    : REPORT_TYPES.filter(r => r.id === "dispatchlist");

  const repGirReadOnly = !isAdmin && !(MOCK_USER.qid === 1 || MOCK_USER.qid === 2);
  const repGirRequired = !isAdmin && (MOCK_USER.qid === 1 || MOCK_USER.qid === 2);

  const girMatch = !!(f.repGirNo && record?.defGirNo && f.repGirNo.toUpperCase() === record.defGirNo.toUpperCase());
  const repairDateReadOnly = f.repGirNo.length === 0 || !girMatch;
  const typeOfWorkVal = parseInt(f.typeOfWork || "0");

  const saveVisible = (() => {
    if (workVariant === "A" || workVariant === "B") {
      if (girMatch && [20, 21, 26, 29, 30].includes(typeOfWorkVal)) return true;
      if (!girMatch && typeOfWorkVal === 25) return true;
      if (girMatch && typeOfWorkVal !== 25 && repairFilled) return true;
      return false;
    }
    if (workVariant === "C") return f.dcNo !== "" && f.shipDateFromCommercial !== "";
    return true;
  })();

  const destinationEnabled = f.reportType === "dispatchlist";

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!record?.id) return;
    setError("");
    setSubmitting(true);
    try {
      const payload: ServiceRecord = {
        ...record,
        raEngineerId: f.raEngineer ? parseInt(f.raEngineer, 10) : undefined,
        defUnitGirNo: f.defUnitGirNo || undefined,
        technicalRemarks: f.technicalRemarks || undefined,
        componentsUsedForRepair: f.componentsUsedForRepair || undefined,
        repairedBrdStkDate: f.repairedBrdStkDate || undefined,
        repairedBrdAdvNo: f.repairedBrdAdvNo || undefined,
        finalRemarks: f.finalRemarks || undefined,
        typeOfWork: f.typeOfWork || undefined,
        shipDtFrmSerCntr: f.shipDtFrmSerCntr || undefined,
        dispAdvNo: f.dispAdvNo || undefined,
        shipDateFromCommercial: f.shipDateFromCommercial || undefined,
        dcNo: f.dcNo || undefined,
        repGirNo: f.repGirNo || undefined,
        reportType: f.reportType || undefined,
        destination: f.destination || undefined,
      };
      await ServiceService.updateService(record.id, payload);
      setSaved(true);
      setTimeout(() => router.push("/dashboard/services"), 1500);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to update service record");
    } finally {
      setSubmitting(false);
    }
  };

  if (loading || !record) {
    return (
      <div className="flex items-center justify-center py-24">
        {error ? (
          <div className="text-center">
            <p className="text-red-600 font-medium">{error}</p>
            <Link href="/dashboard/services" className="mt-4 inline-block text-sm text-violet-600 hover:underline">Back to Services</Link>
          </div>
        ) : (
          <Loader2 className="w-8 h-8 animate-spin text-violet-500" />
        )}
      </div>
    );
  }

  return (
    <div className="space-y-5 max-w-5xl">
      {/* Header */}
      <div className="flex items-center gap-4">
        <Link href="/dashboard/services" className="p-2 rounded-xl hover:bg-slate-100 text-slate-500 transition">
          <ArrowLeft className="w-5 h-5" />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Update Service — {record.scRefNo}</h1>
          <p className="text-slate-500 text-sm mt-0.5">Update service workflow details</p>
        </div>
      </div>

      {error && (
        <div className="px-4 py-3 rounded-xl bg-red-50 border border-red-200 text-red-700 text-sm font-medium">
          {error}
        </div>
      )}
      {saved && (
        <div className="px-4 py-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-700 text-sm font-medium">
          ✅ Service record updated! Redirecting…
        </div>
      )}

      {/* Read-only header card */}
      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-5">
        <div className="flex items-center gap-2 mb-4">
          <Info className="w-4 h-4 text-slate-400" />
          <h2 className="text-sm font-semibold text-slate-500 uppercase tracking-wider">Service Record Overview (Read-Only)</h2>
        </div>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {[
            { label: "SC Ref No", value: record.scRefNo },
            { label: "SC Engineer", value: record.scEngineerName ?? record.scEngineerId },
            { label: "FRN No", value: record.frnNo },
            { label: "Customer Name", value: record.custName },
            { label: "Model", value: modelMap[record.productModel ?? ""] ?? record.productModel },
            { label: "Unit Status", value: record.unitStatus },
            { label: "DEF GIR No", value: record.defGirNo },
            { label: "DEF MOD/BRD Name", value: record.defModBrdName },
          ].map(item => (
            <div key={item.label}>
              <p className="text-[10px] font-semibold text-slate-400 uppercase tracking-wider mb-0.5">{item.label}</p>
              <p className="text-sm font-semibold text-violet-700">{nil(item.value)}</p>
            </div>
          ))}
          <div>
            <p className="text-[10px] font-semibold text-slate-400 uppercase tracking-wider mb-0.5">Type of Work</p>
            <Badge variant="outline" className="text-xs rounded-full px-2.5 bg-blue-50 text-blue-700 border-blue-200">
              {nil(dd5Options.find(d => String(d.id) === String(record.typeOfWork))?.ddValue ?? record.typeOfWork)}
            </Badge>
          </div>
        </div>
      </div>

      {/* Editable form */}
      <form onSubmit={handleSubmit}>
        <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6 space-y-5">
          <h2 className="text-sm font-semibold text-slate-700 uppercase tracking-wider pb-3 border-b border-slate-100">Update Details</h2>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <Combobox label="RA Engineer *" required
              options={engineerOpts}
              value={f.raEngineer} onChange={v => set("raEngineer", v)} placeholder="Select RA engineer..." />

            <Txt label="DEF UNIT GIR NO" value={f.defUnitGirNo} onChange={v => set("defUnitGirNo", v)} placeholder="e.g. UGIR-221" />
            <TxtA label="Technical Remarks" value={f.technicalRemarks} onChange={v => set("technicalRemarks", v)} />
            <TxtA label="Components Used for Repair" value={f.componentsUsedForRepair} onChange={v => set("componentsUsedForRepair", v)} />

            <DatePicker label={`Repaired Brd Stk Date${!repairDateReadOnly ? " *" : ""}`}
              required={!repairDateReadOnly} disabled={repairDateReadOnly}
              value={f.repairedBrdStkDate} onChange={v => set("repairedBrdStkDate", v)} />

            <Txt label="Repaired Brd Adv No" value={f.repairedBrdAdvNo} onChange={v => set("repairedBrdAdvNo", v)}
              disabled={!f.repairedBrdStkDate}
              placeholder={f.repairedBrdStkDate ? "Enter adv no" : "Unlocks after repair date"} />

            <TxtA label="Final Remarks" value={f.finalRemarks} onChange={v => set("finalRemarks", v)} />

            {/* Type of Work — 4 variants */}
            {(workVariant === "A" || workVariant === "B") && (
              <Combobox label={`Type of Work/Status (${workVariant === "A" ? "OB" : "PFRN"}) *`}
                required options={toOpts(dd5Options)} value={f.typeOfWork}
                onChange={v => set("typeOfWork", v)} placeholder="Select type..." />
            )}
            {workVariant === "C" && (
              <Combobox label="Type of Work/Status (UR)"
                options={toOpts(dd5Options)} value={f.typeOfWork}
                onChange={v => set("typeOfWork", v)} placeholder="Select type..." />
            )}
            {workVariant === "D" && (
              <div className="space-y-1.5">
                <label className="block text-xs font-semibold text-slate-600">Type of Work (Locked)</label>
                <div className="px-3.5 py-2.5 rounded-xl border border-slate-200 bg-slate-50 text-sm text-slate-600">
                  {nil(dd5Options.find(d => String(d.id) === f.typeOfWork)?.ddValue ?? record.typeOfWork)}
                </div>
              </div>
            )}

            <DatePicker label="Ship Date from Service Centre" value={f.shipDtFrmSerCntr} onChange={v => set("shipDtFrmSerCntr", v)} />

            <Txt label="Dispatch Advice No" value={f.dispAdvNo} onChange={v => set("dispAdvNo", v)}
              disabled={!f.shipDtFrmSerCntr}
              placeholder={f.shipDtFrmSerCntr ? "Enter advice no" : "Unlocks after ship date"} />

            {workVariant === "C" ? (
              <DatePicker label="Ship Date from Commercial *" required value={f.shipDateFromCommercial} onChange={v => set("shipDateFromCommercial", v)} />
            ) : (
              <DatePicker label="Ship Date from Commercial" value={f.shipDateFromCommercial} onChange={v => set("shipDateFromCommercial", v)} />
            )}

            {workVariant === "A" || workVariant === "B" ? (
              <Txt label="DC No" value={f.dcNo} onChange={v => set("dcNo", v)} readonly placeholder="Read-only until ship date" />
            ) : (
              <Txt label="DC No *" value={f.dcNo} onChange={v => set("dcNo", v)} required placeholder="e.g. DC-1001" />
            )}

            {/* REP GIR SNO variants */}
            {isAdmin ? (
              <Txt label="REP GIR SNO (Admin)" value={f.repGirNo} onChange={v => set("repGirNo", v)} uppercase placeholder="GIR number..." />
            ) : repGirRequired ? (
              <Txt label="REP GIR SNO *" value={f.repGirNo} onChange={v => set("repGirNo", v)} required uppercase placeholder="GIR number required" />
            ) : (
              <Txt label="REP GIR SNO" value={f.repGirNo} onChange={v => set("repGirNo", v)} readonly uppercase />
            )}

            {/* GIR match indicator */}
            {f.repGirNo && (
              <div className={`md:col-span-3 flex items-center gap-2 px-4 py-2.5 rounded-xl text-xs font-medium border ${
                girMatch ? "bg-emerald-50 border-emerald-200 text-emerald-700" : "bg-amber-50 border-amber-200 text-amber-700"
              }`}>
                {girMatch
                  ? "✅ REP GIR matches DEF GIR — Repaired Board Stock Date is active"
                  : "⚠️ REP GIR differs from DEF GIR — Ship Date from Service Centre path active"}
              </div>
            )}

            {workVariant === "D" ? (
              <Txt label="Report Type" value={f.reportType} onChange={() => {}} readonly />
            ) : (
              <Combobox label="Type of Report *" required
                options={reportTypeOptions.map(r => ({ value: r.id, label: r.value }))}
                value={f.reportType}
                onChange={v => { set("reportType", v); if (v !== "dispatchlist") set("destination", ""); }}
                placeholder="Select report type..." />
            )}

            <div className="md:col-span-2">
              <Txt label="Destination" value={f.destination} onChange={v => set("destination", v)}
                disabled={!destinationEnabled}
                placeholder={destinationEnabled ? "Enter dispatch destination" : "Only for Dispatch List"} />
            </div>
          </div>

          {/* Buttons */}
          <div className="flex items-center justify-between pt-3 border-t border-slate-100">
            <Link href="/dashboard/services" className="px-4 py-2.5 rounded-xl border border-slate-200 text-slate-600 text-sm font-medium hover:bg-slate-50 transition">
              Cancel
            </Link>
            <div className="flex items-center gap-3">
              {!saveVisible && (
                <p className="text-xs text-slate-400">Complete required fields to enable UPDATE</p>
              )}
              {saveVisible && (
                <button type="submit" disabled={submitting}
                  className="flex items-center gap-2 px-5 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 transition-all disabled:opacity-70 disabled:cursor-not-allowed"
                  style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}>
                  {submitting ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
                  {submitting ? "Updating…" : "UPDATE"}
                </button>
              )}
            </div>
          </div>
        </div>
      </form>
    </div>
  );
}
