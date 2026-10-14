"use client";

import Link from "next/link";
import { useEffect, useState, type FormEvent } from "react";
import { useParams, useRouter } from "next/navigation";
import { ArrowLeft, Loader2, Save, Wrench } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { RepairService, type RepairRecord, type RepairStatus } from "@/services/repair-service";

function display(value: unknown): string {
  return value == null || value === "" ? "—" : String(value);
}

export default function RepairEditPage() {
  const params = useParams<{ id: string }>();
  const router = useRouter();
  const id = Number(params.id);
  const [repair, setRepair] = useState<RepairRecord | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [repairedBy, setRepairedBy] = useState("");
  const [componentsUsed, setComponentsUsed] = useState("");
  const [technicalRemarks, setTechnicalRemarks] = useState("");
  const [finalRemarks, setFinalRemarks] = useState("");
  const [status, setStatus] = useState<RepairStatus>("PENDING");

  useEffect(() => {
    if (!Number.isInteger(id) || id <= 0) {
      setError("Invalid repair ID.");
      setLoading(false);
      return;
    }
    RepairService.get(id)
      .then((record) => {
        setRepair(record);
        setRepairedBy(record.repairedBy ?? "");
        setComponentsUsed(record.componentsUsed ?? "");
        setTechnicalRemarks(record.technicalRemarks ?? "");
        setFinalRemarks(record.finalRemarks ?? "");
        setStatus(record.status);
      })
      .catch((caught) => setError(caught instanceof Error ? caught.message : "Failed to load repair"))
      .finally(() => setLoading(false));
  }, [id]);

  const submit = async (event: FormEvent) => {
    event.preventDefault();
    if (!repairedBy.trim()) {
      setError("Repaired by is required.");
      return;
    }
    setSaving(true);
    setError(null);
    try {
      await RepairService.update(id, {
        repairedBy: repairedBy.trim(),
        componentsUsed: componentsUsed.trim(),
        technicalRemarks: technicalRemarks.trim(),
        finalRemarks: finalRemarks.trim(),
        status,
      });
      router.push(status === "COMPLETED" ? "/dashboard/repair-closed" : repair?.category === "OB" ? "/dashboard/repair-ob" : repair?.category === "UR" ? "/dashboard/repair-ur" : "/dashboard/repair");
      router.refresh();
    } catch (caught) {
      setError(caught instanceof Error ? caught.message : "Failed to update repair");
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="flex min-h-64 items-center justify-center text-slate-500"><Loader2 className="mr-2 h-5 w-5 animate-spin" />Loading repair…</div>;
  if (!repair) return <div className="space-y-4"><div role="alert" className="rounded-xl border border-red-200 bg-red-50 p-4 text-red-800">{error || "Repair not found."}</div><Link href="/dashboard/repair" className="text-violet-700 underline">Back to repairs</Link></div>;

  const completed = repair.status === "COMPLETED";
  return (
    <div className="mx-auto max-w-5xl space-y-5">
      <header>
        <Link href={repair.category === "OB" ? "/dashboard/repair-ob" : repair.category === "UR" ? "/dashboard/repair-ur" : "/dashboard/repair"} className="mb-3 inline-flex items-center text-sm font-medium text-violet-700 hover:underline"><ArrowLeft className="mr-1 h-4 w-4" />Back to repair list</Link>
        <div className="flex items-center gap-2"><Wrench className="h-8 w-8 text-violet-600" /><h1 className="text-2xl font-bold text-slate-900">Update Repair</h1></div>
        <p className="mt-1 text-sm text-slate-500">Repair #{repair.id} · Service #{repair.serviceId}</p>
      </header>

      <section className="grid grid-cols-2 gap-4 rounded-2xl border border-slate-100 bg-slate-50 p-5 text-sm md:grid-cols-4">
        {[['Entry date', repair.entryDate], ['Division', repair.division], ['SC reference', repair.scRefNo], ['Defective GIR', repair.defGirNo], ['Category', repair.category], ['Model', repair.model], ['Def board/module', repair.defBoardModuleName], ['Pending days', repair.pendingDays ?? repair.days]].map(([label, value]) => <div key={String(label)}><p className="text-xs font-medium uppercase tracking-wide text-slate-400">{label}</p><p className="mt-1 font-semibold text-slate-800">{display(value)}</p></div>)}
      </section>

      {completed && <div className="rounded-xl border border-emerald-200 bg-emerald-50 p-4 text-sm text-emerald-800">This repair is completed and cannot be changed.</div>}
      {error && <div role="alert" className="rounded-xl border border-red-200 bg-red-50 p-4 text-sm text-red-800">{error}</div>}

      <form onSubmit={submit} className="space-y-5 rounded-2xl border border-slate-100 bg-white p-5 shadow-sm">
        <div className="grid gap-5 md:grid-cols-2">
          <div className="space-y-2"><Label htmlFor="repairedBy">Repaired by <span className="text-red-600">*</span></Label><Input id="repairedBy" value={repairedBy} onChange={(event) => setRepairedBy(event.target.value)} required disabled={completed || saving} maxLength={255} /></div>
          <div className="space-y-2"><Label htmlFor="status">Status <span className="text-red-600">*</span></Label><select id="status" value={status} onChange={(event) => setStatus(event.target.value as RepairStatus)} disabled={completed || saving} className="block h-10 w-full rounded-md border border-slate-200 bg-white px-3 text-sm"><option value="PENDING">Pending</option><option value="COMPLETED">Completed</option></select></div>
        </div>
        <div className="space-y-2"><Label htmlFor="componentsUsed">Components used to repair</Label><Textarea id="componentsUsed" value={componentsUsed} onChange={(event) => setComponentsUsed(event.target.value)} disabled={completed || saving} rows={3} maxLength={1000} /></div>
        <div className="space-y-2"><Label htmlFor="technicalRemarks">Technical remarks</Label><Textarea id="technicalRemarks" value={technicalRemarks} onChange={(event) => setTechnicalRemarks(event.target.value)} disabled={completed || saving} rows={4} maxLength={300} /></div>
        <div className="space-y-2"><Label htmlFor="finalRemarks">Final remarks</Label><Textarea id="finalRemarks" value={finalRemarks} onChange={(event) => setFinalRemarks(event.target.value)} disabled={completed || saving} rows={4} maxLength={100} /></div>
        {!completed && <div className="flex justify-end"><Button type="submit" disabled={saving || !repairedBy.trim()} className="rounded-xl bg-violet-600 text-white hover:bg-violet-700">{saving ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Save className="mr-2 h-4 w-4" />}Save repair</Button></div>}
      </form>
    </div>
  );
}
