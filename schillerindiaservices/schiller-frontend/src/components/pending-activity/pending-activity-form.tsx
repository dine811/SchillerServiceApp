"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Loader2, Save } from "lucide-react";
import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { ServiceService, type PendingActivityPayload, type PendingActivityRecord } from "@/services/service-service";

type Props = {
  mode: "create" | "edit";
  recordId?: number;
  heading?: string;
  backHref?: string;
  backLabel?: string;
};

const EMPTY: PendingActivityPayload = {
  division: "",
  scEngg: "",
  entryDate: "",
  initiateDate: "",
  model: "",
  pendingActivity: "",
  responsible: "",
  pendingForm: "",
  tarClosedDate: "",
  closedDate: "",
  remarks: "",
  statusType: "Pending",
  scInchargeRemark: "",
};

function safe(v: unknown): string {
  return v == null || v === "null" ? "" : String(v);
}

function trimOrEmpty(v: string | undefined | null): string {
  return (v ?? "").trim();
}

/** yyyy-MM-dd */
function validatePendingActivityPayload(
  form: PendingActivityPayload,
  mode: "create" | "edit"
): string[] {
  const errs: string[] = [];
  const division = trimOrEmpty(form.division);
  const scEngg = trimOrEmpty(form.scEngg);
  const entryDate = trimOrEmpty(form.entryDate);
  const initiateDate = trimOrEmpty(form.initiateDate);
  const pendingActivity = trimOrEmpty(form.pendingActivity);
  const model = trimOrEmpty(form.model);
  const status = trimOrEmpty(form.statusType) || "Pending";
  const closedDate = trimOrEmpty(form.closedDate);

  if (mode === "create" && !division) errs.push("Division is required.");
  if (!scEngg) errs.push("SC Engineer ID is required.");
  if (!entryDate) errs.push("Entry date is required.");
  if (!initiateDate) errs.push("Initiate date is required.");
  if (!pendingActivity) errs.push("Activity is required.");
  if (!model) errs.push("Description / model is required.");
  if (status.toLowerCase() === "completed" && !closedDate) {
    errs.push("Closed date is required when status is Completed.");
  }
  return errs;
}

export function PendingActivityForm({
  mode,
  recordId,
  heading,
  backHref = "/dashboard/pending-activity",
  backLabel = "Back to pending activity",
}: Props) {
  const router = useRouter();
  const [form, setForm] = useState<PendingActivityPayload>(EMPTY);
  const [loading, setLoading] = useState(mode === "edit");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const title = useMemo(
    () => heading || (mode === "create" ? "Add pending activity" : "Update pending activity"),
    [mode, heading]
  );

  useEffect(() => {
    if (mode !== "edit" || !recordId) return;
    ServiceService.getPendingActivityById(recordId)
      .then((r: PendingActivityRecord) => {
        setForm({
          division: safe(r.division),
          scEngg: safe(r.scEngg),
          entryDate: safe(r.entryDate),
          initiateDate: safe(r.initiateDate),
          model: safe(r.model),
          pendingActivity: safe(r.pendingActivity),
          responsible: safe(r.responsible),
          pendingForm: safe(r.pendingForm),
          tarClosedDate: safe(r.tarClosedDate),
          closedDate: safe(r.closedDate),
          remarks: safe(r.remarks),
          statusType: safe(r.statusType || "Pending"),
          scInchargeRemark: safe(r.scInchargeRemark),
        });
      })
      .catch((e) => setError(e instanceof Error ? e.message : "Failed to load record"))
      .finally(() => setLoading(false));
  }, [mode, recordId]);

  const onChange = (key: keyof PendingActivityPayload, value: string) => {
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    const payload: PendingActivityPayload = {
      ...form,
      statusType: form.statusType?.trim() || "Pending",
    };
    const validationErrors = validatePendingActivityPayload(payload, mode);
    if (validationErrors.length > 0) {
      setError(validationErrors.join(" "));
      return;
    }
    setSaving(true);
    try {
      if (mode === "create") {
        await ServiceService.createPendingActivity(payload);
      } else {
        await ServiceService.updatePendingActivity(recordId!, payload);
      }
      router.push(backHref);
      router.refresh();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Save failed");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="space-y-5 max-w-5xl">
      <div className="flex items-start justify-between gap-3">
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">{title}</h1>
          <p className="text-slate-500 text-sm mt-1">Legacy Pending Activity form migration.</p>
        </div>
        <Link href={backHref} className="text-sm text-indigo-600 hover:text-indigo-800 underline">
          {backLabel}
        </Link>
      </div>

      {error && <div className="rounded-xl border border-red-200 bg-red-50 text-red-800 px-4 py-3 text-sm">{error}</div>}

      <form onSubmit={onSubmit} className="bg-white rounded-2xl border border-slate-100 shadow-sm p-5 space-y-5">
        {loading ? (
          <div className="text-slate-500 text-sm inline-flex items-center gap-2">
            <Loader2 className="h-4 w-4 animate-spin" /> Loading record...
          </div>
        ) : (
          <>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <Field label="Division" value={form.division || ""} onChange={(v) => onChange("division", v)} disabled={mode === "edit"} />
              <Field label="SC Engineer ID" value={form.scEngg || ""} onChange={(v) => onChange("scEngg", v)} disabled={mode === "edit"} />
              <DateField label="Entry Date" value={form.entryDate || ""} onChange={(v) => onChange("entryDate", v)} disabled={mode === "edit"} />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <DateField label="Initiate Date" value={form.initiateDate || ""} onChange={(v) => onChange("initiateDate", v)} />
              <DateField label="Target Date" value={form.tarClosedDate || ""} onChange={(v) => onChange("tarClosedDate", v)} />
              <DateField label="Closed Date" value={form.closedDate || ""} onChange={(v) => onChange("closedDate", v)} />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <Field label="Activity" value={form.pendingActivity || ""} onChange={(v) => onChange("pendingActivity", v)} />
              <Field label="Description / Model" value={form.model || ""} onChange={(v) => onChange("model", v)} />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <Field label="Responsible" value={form.responsible || ""} onChange={(v) => onChange("responsible", v)} />
              <Field label="Pending Form" value={form.pendingForm || ""} onChange={(v) => onChange("pendingForm", v)} />
              <div className="space-y-1.5">
                <label className="block text-sm font-medium text-slate-700">Status</label>
                <select
                  value={form.statusType || "Pending"}
                  onChange={(e) => onChange("statusType", e.target.value)}
                  className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm"
                >
                  <option value="Pending">Pending</option>
                  <option value="Completed">Completed</option>
                </select>
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <TextArea label="Remarks" value={form.remarks || ""} onChange={(v) => onChange("remarks", v)} />
              <TextArea
                label="SC Incharge Remarks"
                value={form.scInchargeRemark || ""}
                onChange={(v) => onChange("scInchargeRemark", v)}
              />
            </div>
          </>
        )}

        <div className="flex items-center gap-2">
          <Button type="submit" disabled={saving || loading}>
            {saving ? <Loader2 className="h-4 w-4 animate-spin mr-1.5" /> : <Save className="h-4 w-4 mr-1.5" />}
            {mode === "create" ? "Create Record" : "Update Record"}
          </Button>
          <Link href={backHref}>
            <Button type="button" variant="outline">
              Cancel
            </Button>
          </Link>
        </div>
      </form>
    </div>
  );
}

function DateField({
  label,
  value,
  onChange,
  disabled,
}: {
  label: string;
  value: string;
  onChange: (value: string) => void;
  disabled?: boolean;
}) {
  return (
    <div className="space-y-1.5">
      <DatePicker
        label={label}
        value={value}
        onChange={onChange}
        placeholder="yyyy-MM-dd"
        disabled={disabled}
      />
    </div>
  );
}

function Field({
  label,
  value,
  onChange,
  placeholder,
  disabled,
}: {
  label: string;
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  disabled?: boolean;
}) {
  return (
    <div className="space-y-1.5">
      <label className="block text-sm font-medium text-slate-700">{label}</label>
      <input
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={placeholder}
        disabled={disabled}
        className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm disabled:bg-slate-50 disabled:text-slate-500"
      />
    </div>
  );
}

function TextArea({
  label,
  value,
  onChange,
}: {
  label: string;
  value: string;
  onChange: (value: string) => void;
}) {
  return (
    <div className="space-y-1.5">
      <label className="block text-sm font-medium text-slate-700">{label}</label>
      <textarea
        value={value}
        onChange={(e) => onChange(e.target.value)}
        rows={4}
        className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm"
      />
    </div>
  );
}
