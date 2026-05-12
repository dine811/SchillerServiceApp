"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { ServiceService, type SpareMasterPayload, type SpareMasterRecord } from "@/services/service-service";

type Mode = "create" | "edit";

type Props = {
  mode: Mode;
  id?: number;
  heading: string;
  backHref: string;
  backLabel: string;
  defaultStatus?: string;
};

const INITIAL: SpareMasterPayload = {
  entryDate: "",
  division: "",
  supplier: "",
  model: "",
  partNumber: "",
  defModBrdName: "",
  reason: "",
  reference: "",
  girNo: "",
  scEngg: "",
  issuedBy: "",
  returnableStatus: "",
  remarks: "",
  returnedDate: "",
  finalStatus: "Pending",
  qty: "",
};

export default function SpareForm({
  mode,
  id,
  heading,
  backHref,
  backLabel,
  defaultStatus = "Pending",
}: Props) {
  const router = useRouter();
  const [form, setForm] = useState<SpareMasterPayload>({ ...INITIAL, finalStatus: defaultStatus });
  const [loading, setLoading] = useState(mode === "edit");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (mode !== "edit" || !id) return;
    let mounted = true;
    setLoading(true);
    ServiceService
      .getSpareById(id)
      .then((row) => {
        if (!mounted) return;
        setForm(toPayload(row));
      })
      .catch((e: unknown) => {
        if (!mounted) return;
        setError(e instanceof Error ? e.message : "Failed to load spares record");
      })
      .finally(() => {
        if (mounted) setLoading(false);
      });
    return () => {
      mounted = false;
    };
  }, [id, mode]);

  const isCompleted = useMemo(
    () => (form.finalStatus ?? "").trim().toLowerCase() === "completed",
    [form.finalStatus]
  );

  const set = (key: keyof SpareMasterPayload, value: string) =>
    setForm((p) => ({ ...p, [key]: value }));

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setSaving(true);
    setError(null);
    try {
      if (mode === "create") {
        await ServiceService.createSpare(form);
      } else if (id) {
        await ServiceService.updateSpare(id, form);
      }
      router.push(backHref);
      router.refresh();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Failed to save spares record");
    } finally {
      setSaving(false);
    }
  }

  if (loading) return <div className="p-4 text-sm text-slate-600">Loading spares record...</div>;

  return (
    <div className="max-w-5xl space-y-5">
      <div className="flex items-center justify-between gap-3">
        <h1 className="text-2xl font-bold text-slate-900">{heading}</h1>
        <Link href={backHref} className="text-sm text-blue-600 hover:underline">
          {backLabel}
        </Link>
      </div>

      {error ? <div className="rounded border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700">{error}</div> : null}

      <form onSubmit={onSubmit} className="space-y-4">
        <div className="grid grid-cols-1 gap-4 rounded-xl border border-slate-200 bg-white p-4 md:grid-cols-2">
          <Field label="Entry Date">
            <input
              type="date"
              value={toInputDate(form.entryDate)}
              onChange={(e) => set("entryDate", e.target.value)}
              className={inputCls}
            />
          </Field>
          <Field label="Division">
            <input value={form.division ?? ""} onChange={(e) => set("division", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Supplier">
            <input value={form.supplier ?? ""} onChange={(e) => set("supplier", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Model">
            <input value={form.model ?? ""} onChange={(e) => set("model", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Part Number">
            <input value={form.partNumber ?? ""} onChange={(e) => set("partNumber", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Def Mod/Brd Name">
            <input
              value={form.defModBrdName ?? ""}
              onChange={(e) => set("defModBrdName", e.target.value)}
              className={inputCls}
            />
          </Field>
          <Field label="Reason">
            <input value={form.reason ?? ""} onChange={(e) => set("reason", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Reference">
            <input value={form.reference ?? ""} onChange={(e) => set("reference", e.target.value)} className={inputCls} />
          </Field>
          <Field label="GIR No">
            <input value={form.girNo ?? ""} onChange={(e) => set("girNo", e.target.value)} className={inputCls} />
          </Field>
          <Field label="SC Engineer">
            <input value={form.scEngg ?? ""} onChange={(e) => set("scEngg", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Issued By">
            <input value={form.issuedBy ?? ""} onChange={(e) => set("issuedBy", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Qty">
            <input value={form.qty ?? ""} onChange={(e) => set("qty", e.target.value)} className={inputCls} />
          </Field>
          <Field label="Returnable Status">
            <input
              value={form.returnableStatus ?? ""}
              onChange={(e) => set("returnableStatus", e.target.value)}
              className={inputCls}
            />
          </Field>
          <Field label="Final Status">
            <select
              value={form.finalStatus ?? "Pending"}
              onChange={(e) => set("finalStatus", e.target.value)}
              className={inputCls}
            >
              <option value="Pending">Pending</option>
              <option value="Completed">Completed</option>
            </select>
          </Field>
          <Field label="Returned Date">
            <input
              type="date"
              value={toInputDate(form.returnedDate)}
              onChange={(e) => set("returnedDate", e.target.value)}
              className={inputCls}
              disabled={!isCompleted}
            />
          </Field>
          <div className="md:col-span-2">
            <Field label="Remarks">
              <textarea
                value={form.remarks ?? ""}
                onChange={(e) => set("remarks", e.target.value)}
                rows={3}
                className={inputCls}
              />
            </Field>
          </div>
        </div>

        <div className="flex items-center gap-3">
          <button
            type="submit"
            disabled={saving}
            className="rounded bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700 disabled:opacity-60"
          >
            {saving ? "Saving..." : mode === "create" ? "Create" : "Update"}
          </button>
          <Link href={backHref} className="rounded border border-slate-300 px-4 py-2 text-sm text-slate-700 hover:bg-slate-50">
            Cancel
          </Link>
        </div>
      </form>
    </div>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <label className="space-y-1.5 text-sm">
      <span className="block text-xs font-medium uppercase text-slate-500">{label}</span>
      {children}
    </label>
  );
}

const inputCls =
  "w-full rounded border border-slate-300 px-3 py-2 text-sm text-slate-900 outline-none focus:border-blue-500 focus:ring-1 focus:ring-blue-500 disabled:bg-slate-100";

function toPayload(row: SpareMasterRecord): SpareMasterPayload {
  return {
    entryDate: row.entryDate ?? "",
    division: row.division ?? "",
    supplier: row.supplier ?? "",
    model: row.model ?? "",
    partNumber: row.partNumber ?? "",
    defModBrdName: row.defModBrdName ?? "",
    reason: row.reason ?? "",
    reference: row.reference ?? "",
    girNo: row.girNo ?? "",
    scEngg: row.scEngg ?? "",
    issuedBy: row.issuedBy ?? "",
    returnableStatus: row.returnableStatus ?? "",
    remarks: row.remarks ?? "",
    returnedDate: row.returnedDate ?? "",
    finalStatus: row.finalStatus ?? "Pending",
    qty: row.qty ?? "",
  };
}

function toInputDate(value?: string): string {
  if (!value) return "";
  if (/^\d{4}-\d{2}-\d{2}$/.test(value)) return value;
  const m = value.match(/^(\d{2})-(\d{2})-(\d{4})$/);
  if (!m) return "";
  return `${m[3]}-${m[2]}-${m[1]}`;
}
