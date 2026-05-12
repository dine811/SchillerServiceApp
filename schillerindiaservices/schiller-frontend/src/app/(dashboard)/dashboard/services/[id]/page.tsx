"use client";

import { apiFetch, getApiBaseUrl } from "@/lib/api";
import { useState, useEffect } from "react";
import { useParams } from "next/navigation";
import Link from "next/link";
import { ArrowLeft, Edit, FileText, Loader2 } from "lucide-react";
import { ServiceService, type ServiceRecord, type Model } from "@/services/service-service";
import type { DropdownOption } from "@/services/service-service";

function nil(val: unknown): string {
  return val == null || val === "null" || val === "" ? "—" : String(val);
}

export default function ViewServicePage() {
  const params = useParams();
  const id = parseInt(params.id as string, 10);
  const [record, setRecord] = useState<ServiceRecord | null>(null);
  const [dd2Options, setDd2Options] = useState<DropdownOption[]>([]);
  const [dd5Options, setDd5Options] = useState<DropdownOption[]>([]);
  const [modelMap, setModelMap] = useState<Record<string, string>>({});
  const [employeeMap, setEmployeeMap] = useState<Record<number, string>>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!id || isNaN(id)) {
      setError("Invalid service ID");
      setLoading(false);
      return;
    }
    Promise.all([
      ServiceService.getService(id),
      ServiceService.getDropdownOptions(2),
      ServiceService.getDropdownOptions(5),
      ServiceService.getModels().then((models: Model[]) => {
        const map: Record<string, string> = {};
        models.forEach((m: Model) => { map[String(m.modelId)] = m.modelName; });
        return map;
      }),
      apiFetch(`${getApiBaseUrl()}/employees?page=0&size=500`).then(r => r.json()),
    ])
      .then(([svc, dd2, dd5, modelMapRes, empRes]) => {
        setRecord(svc);
        setDd2Options(dd2);
        setDd5Options(dd5);
        setModelMap(modelMapRes);
        const list = empRes?.content ?? empRes ?? [];
        const empMap: Record<number, string> = {};
        list.forEach((e: { empId: number; empName: string }) => { empMap[e.empId] = e.empName; });
        setEmployeeMap(empMap);
      })
      .catch(err => setError(err instanceof Error ? err.message : "Failed to load service"))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[300px]">
        <Loader2 className="w-8 h-8 animate-spin text-violet-500" />
      </div>
    );
  }
  if (error || !record) {
    return (
      <div className="space-y-4">
        <Link href="/dashboard/services" className="inline-flex items-center gap-2 text-slate-600 hover:text-violet-600 text-sm font-medium">
          <ArrowLeft className="w-4 h-4" /> Back to Services
        </Link>
        <div className="bg-rose-50 text-rose-700 p-5 rounded-xl border border-rose-200">{error || "Record not found"}</div>
      </div>
    );
  }

  const unitStatusLabel = dd2Options.find(d => String(d.id) === String(record.unitStatus))?.ddValue ?? record.unitStatus;
  const typeOfWorkLabel = dd5Options.find(d => String(d.id) === String(record.typeOfWork))?.ddValue ?? record.typeOfWork;
  const scEng = record.scEngineerName ?? (record.scEngineerId != null ? employeeMap[record.scEngineerId] : null) ?? record.scEngineerId;
  const raEng = record.raEngineerName ?? (record.raEngineerId != null ? employeeMap[record.raEngineerId] : null) ?? record.raEngineerId;

  const fields = [
    { label: "SC Ref No", value: record.scRefNo },
    { label: "Entry Date", value: record.entryDate },
    { label: "SC Engineer", value: scEng },
    { label: "RA Engineer", value: raEng },
    { label: "FRN No", value: record.frnNo },
    { label: "Region", value: record.region },
    { label: "Branch", value: record.branch },
    { label: "Customer", value: record.custName },
    { label: "Model", value: modelMap[record.productModel ?? ""] ?? record.productModel },
    { label: "Unit Status", value: unitStatusLabel },
    { label: "Type of Work", value: typeOfWorkLabel },
    { label: "DEF GIR No", value: record.defGirNo },
    { label: "DEF MOD/BRD Name", value: record.defModBrdName },
  ];

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <Link href="/dashboard/services" className="inline-flex items-center gap-2 text-slate-600 hover:text-violet-600 text-sm font-medium">
          <ArrowLeft className="w-4 h-4" /> Back to Services
        </Link>
        <div className="flex gap-2">
          <Link
            href={`/dashboard/services/${id}/update`}
            className="inline-flex items-center gap-2 px-4 py-2 rounded-xl bg-violet-600 text-white text-sm font-medium hover:bg-violet-700"
          >
            <FileText className="w-4 h-4" /> Update
          </Link>
          <Link
            href={`/dashboard/services/${id}/edit`}
            className="inline-flex items-center gap-2 px-4 py-2 rounded-xl border border-slate-200 text-slate-700 text-sm font-medium hover:bg-slate-50"
          >
            <Edit className="w-4 h-4" /> Edit
          </Link>
        </div>
      </div>

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-6">
        <h1 className="text-xl font-bold text-slate-900 mb-4">Service Record — {nil(record.scRefNo)}</h1>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {fields.map(({ label, value }) => (
            <div key={label}>
              <p className="text-[10px] font-semibold text-slate-400 uppercase tracking-wider mb-0.5">{label}</p>
              <p className="text-sm font-medium text-slate-800">{nil(value)}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
