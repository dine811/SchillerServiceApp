"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useParams, useRouter, useSearchParams } from "next/navigation";
import { ArrowLeft, Save, Loader2 } from "lucide-react";
import { DatePicker } from "@/components/ui/date-picker";
import { Combobox } from "@/components/ui/combobox";
import { apiFetch, getApiBaseUrl } from "@/lib/api";
import { REPORT_TYPES } from "@/lib/service-mock-data";
import {
  ServiceService,
  type ServiceRecord,
  Region,
  Branch,
  Dealer,
  Employee as RAEmployee,
  Model,
  Supplier,
  DropdownOption,
} from "@/services/service-service";

interface DivisionOption { value: string; label: string }

function Txt({ label, value, onChange, onBlur, required, placeholder, disabled, readOnly, uppercase }: {
  label: string; value: string; onChange: (v: string) => void; onBlur?: () => void;
  required?: boolean; placeholder?: string; disabled?: boolean; readOnly?: boolean; uppercase?: boolean;
}) {
  return (
    <div className="space-y-1.5">
      <label className="block text-xs font-semibold text-slate-600">
        {label}{required && <span className="text-red-500 ml-0.5">*</span>}
      </label>
      <input value={value}
        onChange={e => onChange(uppercase ? e.target.value.toUpperCase() : e.target.value)}
        placeholder={placeholder} disabled={disabled} readOnly={readOnly} required={required}
        className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 bg-white placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-violet-200 focus:border-violet-400 disabled:bg-slate-100 disabled:opacity-60 read-only:bg-slate-50 transition" />
    </div>
  );
}

function TxtA({ label, value, onChange, rows = 2, required }: {
  label: string; value: string; onChange: (v: string) => void; rows?: number; required?: boolean;
}) {
  return (
    <div className="space-y-1.5">
      <label className="block text-xs font-semibold text-slate-600">
        {label}{required && <span className="text-red-500 ml-0.5">*</span>}
      </label>
      <textarea value={value} onChange={e => onChange(e.target.value)} rows={rows} required={required}
        className="w-full px-3.5 py-2.5 rounded-xl border border-slate-200 text-sm text-slate-800 bg-white focus:outline-none focus:ring-2 focus:ring-violet-200 focus:border-violet-400 transition resize-none" />
    </div>
  );
}

const emptyForm = {
  division: "", scRefNo: "", scEngineer: "", frnNo: "", frnDate: "", serCommInwardDate: "", serCentreReceivedDate: "",
  stkCust: "", region: "", branch: "", engineer: "", dealer: "", custName: "", supplier: "", model: "", modelConfig: "",
  unitSlno: "", unitStatus: "", dod: "", modBrdName: "", partNumber: "", compatibleModels: "", defModBrdName: "",
  cost: "", defType: "", typeOfAcc: "", defPartSn: "", defGirNo: "", repType: "", repGirNo: "", defUnitGirNo: "",
  fieldRemarks: "", technicalRemarks: "", componentsUsedForRepair: "", repairedBrdStkDate: "", repairedBrdAdvNo: "",
  finalRemarks: "", typeOfWork: "", shipDtFrmSerCntr: "", dispAdvNo: "", shipDateFromCommercial: "",
  dcNo: "", reportType: "servicecenter", destination: "", comrclDtlInvRmrk: "",
};

function fmtDate(d: string | undefined): string {
  if (!d) return "";
  const m = d.match(/^(\d{4})-(\d{2})-(\d{2})/);
  return m ? m[0] : d;
}

export default function EditServicePage() {
  const params = useParams();
  const router = useRouter();
  const searchParams = useSearchParams();
  const id = parseInt(params.id as string, 10);
  const [loading, setLoading] = useState(true);
  const [saved, setSaved] = useState(false);
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [userRole, setUserRole] = useState("");
  const isAdmin = userRole === "ADMIN" || userRole === "VICE_CHANCELLOR";
  const source = (searchParams.get("source") || "").toLowerCase();
  const fromPendingFrn = source === "pending-frn";
  const fromObPending = source === "ob-pending";
  const fromScClosed = source === "sc-closed";
  const fromLegacyQueue = fromPendingFrn || fromObPending || fromScClosed;
  const fromOpenLegacyQueue = fromPendingFrn || fromObPending;
  const backHref = fromPendingFrn
    ? "/dashboard/pending-frn"
    : fromObPending
      ? "/dashboard/ob-pending"
      : fromScClosed
        ? "/dashboard/sc-closed"
      : "/dashboard/services";

  const [divisionOptions, setDivisionOptions] = useState<DivisionOption[]>([]);
  const [engineerOpts, setEngineerOpts] = useState<{ value: string; label: string }[]>([]);
  const [regions, setRegions] = useState<Region[]>([]);
  const [branches, setBranches] = useState<Branch[]>([]);
  const [dealers, setDealers] = useState<Dealer[]>([]);
  const [raEngineers, setRaEngineers] = useState<RAEmployee[]>([]);
  const [models, setModels] = useState<Model[]>([]);
  const [suppliers, setSuppliers] = useState<Supplier[]>([]);
  const [cascadingLoading, setCascadingLoading] = useState(false);
  const [dd1Options, setDd1Options] = useState<DropdownOption[]>([]);
  const [dd2Options, setDd2Options] = useState<DropdownOption[]>([]);
  const [dd3Options, setDd3Options] = useState<DropdownOption[]>([]);
  const [dd4Options, setDd4Options] = useState<DropdownOption[]>([]);
  const [dd5Options, setDd5Options] = useState<DropdownOption[]>([]);
  const [dd6Options, setDd6Options] = useState<DropdownOption[]>([]);

  const [f, setF] = useState(emptyForm);
  const [entryDate, setEntryDate] = useState("");

  const set = (k: keyof typeof f, v: string) => {
    setF(prev => {
      const next = { ...prev, [k]: v };
      if (k === "repType" || k === "typeOfWork") next.reportType = "servicecenter";
      return next;
    });
  };

  const handleRegion = async (v: string) => {
    setF(prev => ({ ...prev, region: v, branch: "", engineer: "", dealer: "" }));
    if (!v) return;
    setCascadingLoading(true);
    try {
      const rid = parseInt(v);
      const [b, d, e] = await Promise.all([
        ServiceService.getBranchesByRegion(rid),
        ServiceService.getDealersByRegion(rid),
        ServiceService.getEmployeesByRegion(rid),
      ]);
      setBranches(b);
      setDealers(d);
      setRaEngineers(e);
    } catch (err) {
      console.error(err);
    } finally {
      setCascadingLoading(false);
    }
  };

  const handleSupplier = async (v: string) => {
    setF(prev => ({ ...prev, supplier: v, model: "" }));
    if (!v) {
      ServiceService.getModels().then(setModels);
      return;
    }
    try {
      const filtered = await ServiceService.getModelsBySupplier(v);
      setModels(filtered);
    } catch (err) {
      console.error(err);
    }
  };

  const handlePartNumberBlur = async () => {
    if (!f.partNumber || !f.division) return;
    try {
      const details = await ServiceService.getSparePartDetails(f.partNumber, f.division);
      if (details) {
        set("compatibleModels", details.compModels || "");
        set("defModBrdName", details.defModBrdName || "");
        set("defType", details.defType || "");
      }
    } catch (err) {
      console.error("Failed to fetch spare part details:", err);
    }
  };

  const toDdOpts = (opts: DropdownOption[]) => opts.map(o => ({ value: String(o.id), label: o.ddValue }));

  useEffect(() => {
    if (!id || isNaN(id)) {
      setError("Invalid service ID");
      setLoading(false);
      return;
    }

    const load = async () => {
      setLoading(true);
      try {
        const [
          record,
          divRes,
          empRes,
          regionsData,
          me,
          dd1, dd2, dd3, dd4, dd5, dd6,
        ] = await Promise.all([
          ServiceService.getService(id),
          apiFetch(`${getApiBaseUrl()}/products`).then(r => r.json()),
          apiFetch(`${getApiBaseUrl()}/employees?page=0&size=200`).then(r => r.json()),
          ServiceService.getRegions(),
          apiFetch(`${getApiBaseUrl()}/auth/me`).then(async (r) => {
            if (!r.ok) return null;
            const raw = (await r.text().catch(() => "")).trim();
            if (!raw) return null;
            return JSON.parse(raw) as { role?: string } | null;
          }),
          ServiceService.getDropdownOptions(1),
          ServiceService.getDropdownOptions(2),
          ServiceService.getDropdownOptions(3),
          ServiceService.getDropdownOptions(4),
          ServiceService.getDropdownOptions(5),
          ServiceService.getDropdownOptions(6),
        ]);
        setUserRole((me?.role || "").toUpperCase());

        setDivisionOptions((divRes || []).map((d: { productId: number; divisionName: string }) => ({ value: d.divisionName, label: d.divisionName })));
        setEngineerOpts((empRes?.content || []).map((e: { empId: number; empName: string }) => ({ value: String(e.empId), label: e.empName })));
        setRegions(regionsData);
        setDd1Options(dd1);
        setDd2Options(dd2);
        setDd3Options(dd3);
        setDd4Options(dd4);
        setDd5Options(dd5);
        setDd6Options(dd6);
        ServiceService.getModels().then(setModels);
        ServiceService.getSuppliers().then(setSuppliers);

        const r = record as ServiceRecord;
        const regionMatch = regionsData.find((rg: Region) => rg.regionName === r.region);
        const regionId = regionMatch ? String(regionMatch.regionId) : "";

        let branchesData: Branch[] = [];
        let dealersData: Dealer[] = [];
        let raEngs: RAEmployee[] = [];
        if (regionId) {
          const [b, d, e] = await Promise.all([
            ServiceService.getBranchesByRegion(parseInt(regionId, 10)),
            ServiceService.getDealersByRegion(parseInt(regionId, 10)),
            ServiceService.getEmployeesByRegion(parseInt(regionId, 10)),
          ]);
          branchesData = b;
          dealersData = d;
          raEngs = e;
          setBranches(b);
          setDealers(d);
          setRaEngineers(e);
        }

        const branchMatch = branchesData.find((br: Branch) => br.branchName === r.branch);
        const branchId = branchMatch ? String(branchMatch.id) : "";
        const dealerMatch = dealersData.find((dr: Dealer) => dr.dealerName === r.dealerName);
        const dealerId = dealerMatch ? String(dealerMatch.dealerId) : "";
        const engId = r.raEngineerId != null ? String(r.raEngineerId) : "";
        const stkMatch = dd1.find((o: DropdownOption) => o.ddValue === r.stkCust || String(o.id) === r.stkCust);
        const stkCustId = stkMatch ? String(stkMatch.id) : r.stkCust || "";
        const modelId = r.productModel || "";
        const supplierName = r.supplierName || "";
        if (supplierName) {
          const bySup = await ServiceService.getModelsBySupplier(supplierName);
          setModels(bySup);
        }

        setEntryDate(fmtDate(r.entryDate));
        setF({
          division: r.division || "",
          scRefNo: r.scRefNo || "",
          scEngineer: r.scEngineerId != null ? String(r.scEngineerId) : "",
          frnNo: r.frnNo || "",
          frnDate: fmtDate(r.frnDate),
          serCommInwardDate: fmtDate(r.serCommInwardDate),
          serCentreReceivedDate: fmtDate(r.serCentreReceivedDate),
          stkCust: stkCustId,
          region: regionId,
          branch: branchId,
          engineer: engId,
          dealer: dealerId,
          custName: r.custName || "",
          supplier: supplierName,
          model: modelId,
          modelConfig: r.modelConfig || "",
          unitSlno: r.unitSlNo || "",
          unitStatus: r.unitStatus || "",
          dod: fmtDate(r.dod),
          modBrdName: r.modBrdName || "",
          partNumber: r.partNumber || "",
          compatibleModels: r.compatibleModels || "",
          defModBrdName: r.defModBrdName || "",
          cost: r.cost != null ? String(r.cost) : "",
          defType: r.defType || "",
          typeOfAcc: r.typeOfAcc || "",
          defPartSn: r.defPartSn || "",
          defGirNo: r.defGirNo || "",
          repType: r.repType || "",
          repGirNo: r.repGirNo || "",
          defUnitGirNo: r.defUnitGirNo || "",
          fieldRemarks: r.fieldRemarks || "",
          technicalRemarks: r.technicalRemarks || "",
          componentsUsedForRepair: r.componentsUsedForRepair || "",
          repairedBrdStkDate: fmtDate(r.repairedBrdStkDate),
          repairedBrdAdvNo: r.repairedBrdAdvNo || "",
          finalRemarks: r.finalRemarks || "",
          typeOfWork: r.typeOfWork || "",
          shipDtFrmSerCntr: fmtDate(r.shipDtFrmSerCntr),
          dispAdvNo: r.dispAdvNo || "",
          shipDateFromCommercial: fmtDate(r.shipDateFromCommercial),
          dcNo: r.dcNo || "",
          reportType: fromOpenLegacyQueue ? (r.reportType || "dispatchlist") : (r.reportType || "servicecenter"),
          destination: r.destination || "",
          comrclDtlInvRmrk: r.comrclDtlInvRmrk || "",
        });
      } catch (err) {
        setError(err instanceof Error ? err.message : "Failed to load service record");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [id]);

  const today = new Date();
  const yesterday = (() => { const d = new Date(today); d.setDate(d.getDate() - 1); return d; })();
  const tenYearsAgo = (() => { const d = new Date(today); d.setFullYear(d.getFullYear() - 10); return d; })();
  const gir = (f.repGirNo || "").toUpperCase();
  const defGir = (f.defGirNo || "").toUpperCase();
  const girMatch = !!(gir && defGir && gir === defGir);
  const isNa = f.repType === "56";
  const isPrfAdvOb = f.repType === "60";
  const queueTypeWork = Number.parseInt(f.typeOfWork || "", 10);
  const queueRepGirFilled = gir.length > 0;
  const queueRepGirMatch = queueRepGirFilled && defGir.length > 0 && gir === defGir;
  const queueTypeAllowsMatch = [20, 21, 26, 29].includes(queueTypeWork);
  const queueTypeAllowsMatchSave = [20, 21, 26, 29, 30].includes(queueTypeWork);
  const queueIsDiffAndReturnType = queueRepGirFilled && !queueRepGirMatch && queueTypeWork === 25;
  const queueShipSet = !!f.shipDtFrmSerCntr;
  const queueRepairSet = !!f.repairedBrdStkDate;
  const isDodRequired = f.unitStatus === "44" || f.unitStatus === "43";
  const isRepGirRequired = fromOpenLegacyQueue ? true : isPrfAdvOb;
  const isRepairStkDateReadOnly = fromOpenLegacyQueue
    ? !queueRepGirFilled || queueIsDiffAndReturnType
    : !((isNa && girMatch) || (isPrfAdvOb && f.typeOfWork === "23"));
  const isRepairStkDateRequired = fromOpenLegacyQueue
    ? queueRepGirFilled && !queueIsDiffAndReturnType
    : (isNa && girMatch) || (isPrfAdvOb && f.typeOfWork === "23");
  const isShipDateSCReadOnly = fromOpenLegacyQueue
    ? !(queueRepGirFilled && ((queueRepGirMatch && queueTypeAllowsMatch) || queueIsDiffAndReturnType))
    : !((isNa && gir !== "") || (isPrfAdvOb && gir !== ""));
  const isShipDateSCRequired = fromOpenLegacyQueue
    ? queueRepGirFilled && ((queueRepGirMatch && queueTypeAllowsMatch) || queueIsDiffAndReturnType)
    : isNa || isPrfAdvOb;
  const isShipDateCommReadOnly = fromOpenLegacyQueue ? !queueShipSet : !(isPrfAdvOb && gir !== "");
  const isShipDateCommRequired = fromOpenLegacyQueue ? queueShipSet && queueRepairSet : isPrfAdvOb;
  const isDcNoReadOnly = fromOpenLegacyQueue
    ? !queueShipSet
    : (!(isPrfAdvOb && gir !== "") || !f.shipDateFromCommercial);
  const isDcNoRequired = fromOpenLegacyQueue
    ? queueShipSet && queueRepairSet
    : (isPrfAdvOb && !!f.shipDateFromCommercial);
  const isRepairedBrdAdvNoReadOnly = f.reportType !== "stocklist" || !f.repairedBrdStkDate;
  const isRepairedBrdAdvNoRequired = f.reportType === "stocklist" && !!f.repairedBrdStkDate;
  const isDestinationReadOnly = fromOpenLegacyQueue
    ? f.reportType !== "dispatchlist"
    : (f.reportType === "servicecenter" || f.reportType === "scraplist" || f.reportType === "stocklist") && !isNa;
  const lockNonOperationalFields = fromOpenLegacyQueue;
  const saveVisible = (() => {
    if (fromOpenLegacyQueue) {
      // Legacy qid=1/2 logic: save gate is REP GIR/TYPE WORK driven, and once both ship/repair dates
      // are set it additionally expects commercial date + DC no.
      if (queueShipSet && queueRepairSet) {
        return !!(f.shipDateFromCommercial && f.dcNo);
      }
      if (!queueRepGirFilled) return false;
      if (queueRepGirMatch) return queueTypeAllowsMatchSave;
      return queueIsDiffAndReturnType;
    }
    if (!f.repType) return false;
    const wt = f.typeOfWork;
    if (isPrfAdvOb) return wt === "25" || wt === "23";
    if (isNa) {
      if (!gir) return wt === "25" || wt === "61";
      if (girMatch) return ["20", "21", "26", "29", "30"].includes(wt);
      return wt === "25";
    }
    return false;
  })();
  const reportTypeOptions = (() => {
    if (fromOpenLegacyQueue && !f.shipDtFrmSerCntr) {
      return REPORT_TYPES.filter((r) => r.id === "dispatchlist");
    }
    if (fromOpenLegacyQueue && f.shipDtFrmSerCntr && !f.repairedBrdStkDate) {
      return REPORT_TYPES.filter((r) => ["servicecenter", "scraplist", "stocklist"].includes(r.id));
    }
    let extra: string[] = [];
    if (isNa) extra = gir ? ["dispatchlist"] : [];
    else if (isPrfAdvOb && f.typeOfWork === "23") extra = ["scraplist"];
    return REPORT_TYPES.filter(r => ["servicecenter", ...extra].includes(r.id));
  })();
  const scEngineerName = engineerOpts.find((e) => e.value === f.scEngineer)?.label || f.scEngineer || "—";
  const modelLabel = models.find((m) => String(m.modelId) === String(f.model))?.modelName || f.model || "—";
  const unitStatusLabel = dd2Options.find((d) => String(d.id) === String(f.unitStatus))?.ddValue || f.unitStatus || "—";
  const typeOfWorkLabel = dd5Options.find((d) => String(d.id) === String(f.typeOfWork))?.ddValue || f.typeOfWork || "—";
  const oldStyleSummaryRows = [
    { label: "SC_REF_NO", value: f.scRefNo || "—" },
    { label: "SC ENGINEER", value: scEngineerName },
    { label: "FRN NO", value: f.frnNo || "—" },
    { label: "CUSTOMER NAME", value: f.custName || "—" },
    { label: "MODEL", value: modelLabel },
    { label: "UNIT STATUS", value: unitStatusLabel },
    { label: "DEF GIR NO", value: f.defGirNo || "—" },
    { label: "DEF MOD/BRD NAME", value: f.defModBrdName || "—" },
    { label: "TYPE OF WORK/STATUS", value: typeOfWorkLabel },
  ];

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);
    try {
      const regionObj = regions.find(r => String(r.regionId) === f.region);
      const branchObj = branches.find(b => String(b.id) === f.branch);
      const dealerObj = dealers.find(d => String(d.dealerId) === f.dealer);
      const stkCustLabel = dd1Options.find(d => String(d.id) === f.stkCust)?.ddValue ?? f.stkCust;

      const payload: ServiceRecord = {
        id,
        scRefNo: f.scRefNo,
        entryDate: entryDate || new Date().toISOString().split("T")[0],
        division: f.division,
        scEngineerId: f.scEngineer ? parseInt(f.scEngineer, 10) : undefined,
        raEngineerId: f.engineer ? parseInt(f.engineer, 10) : undefined,
        frnNo: f.frnNo,
        frnDate: f.frnDate || undefined,
        serCommInwardDate: f.serCommInwardDate || undefined,
        serCentreReceivedDate: f.serCentreReceivedDate || undefined,
        stkCust: stkCustLabel,
        region: regionObj?.regionName ?? f.region,
        branch: branchObj?.branchName ?? f.branch,
        dealerName: dealerObj?.dealerName ?? f.dealer,
        custName: f.custName,
        supplierName: f.supplier || undefined,
        productModel: f.model || undefined,
        modelConfig: f.modelConfig || undefined,
        unitSlNo: f.unitSlno,
        unitStatus: f.unitStatus || undefined,
        dod: f.dod || undefined,
        modBrdName: f.modBrdName || undefined,
        defModBrdName: f.defModBrdName,
        defType: f.defType || undefined,
        typeOfAcc: f.typeOfAcc || undefined,
        defPartSn: f.defPartSn || undefined,
        defGirNo: f.defGirNo,
        repType: f.repType || undefined,
        repGirNo: f.repGirNo || undefined,
        defUnitGirNo: f.defUnitGirNo || undefined,
        fieldRemarks: f.fieldRemarks || undefined,
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
        comrclDtlInvRmrk: f.comrclDtlInvRmrk || undefined,
        reportType: f.reportType || undefined,
        destination: f.destination || undefined,
        partNumber: f.partNumber || undefined,
        compatibleModels: f.compatibleModels || undefined,
        cost: f.cost ? parseFloat(f.cost) : undefined,
      };
      await ServiceService.updateService(id, payload);
      setSaved(true);
      setTimeout(() => router.push(backHref), 1500);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to update service record");
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[300px]">
        <Loader2 className="w-8 h-8 animate-spin text-violet-500" />
      </div>
    );
  }

  if (error && !f.scRefNo) {
    return (
      <div className="space-y-4">
        <Link href={backHref} className="inline-flex items-center gap-2 text-slate-600 hover:text-violet-600 text-sm font-medium">
          <ArrowLeft className="w-4 h-4" /> Back to Services
        </Link>
        <div className="bg-rose-50 text-rose-700 p-5 rounded-xl border border-rose-200">{error}</div>
      </div>
    );
  }

  return (
    <div className="space-y-5">
      <div className="flex items-center gap-4">
        <Link href={backHref} className="p-2 rounded-xl hover:bg-slate-100 text-slate-500 transition">
          <ArrowLeft className="w-5 h-5" />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">
            {fromPendingFrn
              ? "Pending FRN Update"
              : fromObPending
                ? "OB Pending Update"
                : fromScClosed
                  ? "SC Completed FRN Update"
                  : "Edit Service Record"}
          </h1>
          <p className="text-slate-500 text-sm mt-0.5">
            {fromLegacyQueue ? "Legacy-style update flow for queue records" : "Update all service details"}
          </p>
        </div>
      </div>

      {error && (
        <div className="px-4 py-3 rounded-xl bg-red-50 border border-red-200 text-red-700 text-sm font-medium">{error}</div>
      )}
      {saved && (
        <div className="px-4 py-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-700 text-sm font-medium">
          ✅ Service record updated! Redirecting…
        </div>
      )}
      {fromLegacyQueue && (
        <div className="rounded-2xl border border-slate-200 bg-slate-50 px-4 py-3">
          <p className="text-xs font-semibold text-slate-600 mb-2">Legacy Record Snapshot</p>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
            <div className="space-y-1.5">
              {[oldStyleSummaryRows[0], oldStyleSummaryRows[1], oldStyleSummaryRows[2]].map((row) => (
                <div key={row.label} className="grid grid-cols-[140px_10px_1fr] items-start gap-1">
                  <span className="font-semibold tracking-wide text-slate-700">{row.label}</span>
                  <span className="text-slate-500">:</span>
                  <span className="text-blue-700">{row.value}</span>
                </div>
              ))}
            </div>
            <div className="space-y-1.5">
              {[oldStyleSummaryRows[3], oldStyleSummaryRows[4], oldStyleSummaryRows[5]].map((row) => (
                <div key={row.label} className="grid grid-cols-[140px_10px_1fr] items-start gap-1">
                  <span className="font-semibold tracking-wide text-slate-700">{row.label}</span>
                  <span className="text-slate-500">:</span>
                  <span className="text-blue-700">{row.value}</span>
                </div>
              ))}
            </div>
            <div className="space-y-1.5">
              {[oldStyleSummaryRows[6], oldStyleSummaryRows[7], oldStyleSummaryRows[8]].map((row) => (
                <div key={row.label} className="grid grid-cols-[170px_10px_1fr] items-start gap-1">
                  <span className="font-semibold tracking-wide text-slate-700">{row.label}</span>
                  <span className="text-slate-500">:</span>
                  <span className="text-blue-700">{row.value}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      <form id="service-form-edit" onSubmit={handleSubmit}>
        <div className="space-y-4">
          {fromOpenLegacyQueue ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 items-start">
              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
                <h3 className="text-xs font-bold text-violet-600 uppercase mb-2">Section 1: Assignment & Core</h3>
                <Combobox label="Engineer" required options={raEngineers.map(e => ({ value: String(e.empId), label: e.empName }))} value={f.engineer} onChange={v => set("engineer", v)} disabled={!f.region} placeholder="Select engineer..." />
                <Txt label="REP GIR SNO" required={isRepGirRequired} value={f.repGirNo} onChange={v => set("repGirNo", v)} uppercase />
                <Txt label="DEF UNIT GIR NO" value={f.defUnitGirNo} onChange={v => set("defUnitGirNo", v)} />
              </div>

              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
                <h3 className="text-xs font-bold text-blue-600 uppercase mb-2">Section 2: Repair Progress</h3>
                <Combobox label="Type of Work" required options={toDdOpts(dd5Options)} value={f.typeOfWork} onChange={v => set("typeOfWork", v)} />
                <DatePicker label="Repaired Brd Stk Date" required={isRepairStkDateRequired} disabled={isRepairStkDateReadOnly} value={f.repairedBrdStkDate} onChange={v => set("repairedBrdStkDate", v)} minDate={tenYearsAgo} maxDate={today} />
                <Txt label="Repaired Brd Adv No" value={f.repairedBrdAdvNo} onChange={v => set("repairedBrdAdvNo", v)} disabled={isRepairedBrdAdvNoReadOnly} required={isRepairedBrdAdvNoRequired} />
                <TxtA label="Technical Remarks" value={f.technicalRemarks} onChange={v => set("technicalRemarks", v)} />
              </div>

              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
                <h3 className="text-xs font-bold text-emerald-600 uppercase mb-2">Section 3: Shipping & Report</h3>
                <DatePicker label="Ship Date from SC" required={isShipDateSCRequired} disabled={isShipDateSCReadOnly} value={f.shipDtFrmSerCntr} onChange={v => set("shipDtFrmSerCntr", v)} minDate={tenYearsAgo} maxDate={today} />
                <Txt label="Advice No" value={f.dispAdvNo} onChange={v => set("dispAdvNo", v)} disabled={!f.shipDtFrmSerCntr} />
                <DatePicker label="Ship Date from Commercial" required={isShipDateCommRequired} disabled={isShipDateCommReadOnly} value={f.shipDateFromCommercial} onChange={v => set("shipDateFromCommercial", v)} minDate={tenYearsAgo} maxDate={today} />
                <Txt label="DC No" value={f.dcNo} onChange={v => set("dcNo", v)} required={isDcNoRequired} disabled={isDcNoReadOnly} />
              </div>

              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3 lg:col-span-3">
                <h3 className="text-xs font-bold text-amber-600 uppercase mb-2">Section 4: Finalization</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 items-start">
                  {fromOpenLegacyQueue && queueShipSet && queueRepairSet ? (
                    <Txt label="Report Type" value={f.reportType} onChange={() => {}} readOnly />
                  ) : (
                    <Combobox label="Report Type" required options={reportTypeOptions.map(r => ({ value: r.id, label: r.value }))} value={f.reportType} onChange={v => set("reportType", v)} />
                  )}
                  <Txt label="Destination" value={f.destination} onChange={v => set("destination", v)} readOnly={isDestinationReadOnly} placeholder="City / Branch Name" />
                  <TxtA label="Components Used" value={f.componentsUsedForRepair} onChange={v => set("componentsUsedForRepair", v)} />
                  <TxtA label="Final Remarks" value={f.finalRemarks} onChange={v => set("finalRemarks", v)} />
                </div>
              </div>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 items-start">
              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
                <h3 className="text-xs font-bold text-violet-600 uppercase mb-2">Section 1: Header Details</h3>
                {isAdmin
                  ? <Combobox label="Division" required options={divisionOptions} value={f.division} onChange={v => set("division", v)} placeholder="Select division..." disabled={lockNonOperationalFields} />
                  : <Txt label="Division" value={f.division} onChange={() => {}} disabled />}
                <Txt label="SC Ref No" value={f.scRefNo} onChange={v => set("scRefNo", v)} required placeholder="e.g. SC-1249" readOnly={lockNonOperationalFields} />
                {isAdmin
                  ? <Combobox label="SC Engineer" required options={engineerOpts} value={f.scEngineer} onChange={v => set("scEngineer", v)} placeholder="Select SC engineer..." disabled={lockNonOperationalFields} />
                  : <Txt label="SC Engineer" value={f.scEngineer} onChange={() => {}} disabled />}
                <Txt label="FRN No" value={f.frnNo} onChange={v => set("frnNo", v)} required placeholder="e.g. FRN-8830" readOnly={lockNonOperationalFields} />
                <DatePicker label="FRN Date" required value={f.frnDate} onChange={v => set("frnDate", v)} minDate={tenYearsAgo} maxDate={today} disabled={lockNonOperationalFields} />
                <DatePicker label="Ser Comm Inward Date" required value={f.serCommInwardDate} onChange={v => set("serCommInwardDate", v)} minDate={tenYearsAgo} maxDate={today} disabled={lockNonOperationalFields} />
                <DatePicker label="Received Date Ser Centre" required value={f.serCentreReceivedDate} onChange={v => set("serCentreReceivedDate", v)} minDate={yesterday} maxDate={today} disabled={lockNonOperationalFields} />
                <Combobox label="STK/CUST" required options={toDdOpts(dd1Options)} value={f.stkCust} onChange={v => set("stkCust", v)} placeholder="Select..." disabled={lockNonOperationalFields} />
                <Combobox label="Region" required options={regions.map(r => ({ value: String(r.regionId), label: r.regionName }))} value={f.region} onChange={handleRegion} placeholder="Select region..." disabled={lockNonOperationalFields} />
                <Combobox label="Branch" required options={branches.map(b => ({ value: String(b.id), label: b.branchName }))} value={f.branch} onChange={v => set("branch", v)} disabled={lockNonOperationalFields || !f.region} placeholder="Select branch..." />
                <Combobox label="Engineer" required options={raEngineers.map(e => ({ value: String(e.empId), label: e.empName }))} value={f.engineer} onChange={v => set("engineer", v)} disabled={lockNonOperationalFields || !f.region} placeholder="Select engineer..." />
                <Combobox label="Dealer Name" required options={dealers.map(d => ({ value: String(d.dealerId), label: d.dealerName }))} value={f.dealer} onChange={v => set("dealer", v)} disabled={lockNonOperationalFields || !f.region} placeholder="Select dealer..." />
                <Txt label="Customer Name" value={f.custName} onChange={v => set("custName", v)} required placeholder="e.g. Apollo Hospitals" readOnly={lockNonOperationalFields} />
                <Combobox label="Supplier" required options={suppliers.map(s => ({ value: s.supplierName, label: s.supplierName }))} value={f.supplier} onChange={handleSupplier} placeholder="Select supplier..." disabled={lockNonOperationalFields} />
              </div>

              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
                <h3 className="text-xs font-bold text-blue-600 uppercase mb-2">Section 2: Product & Defect</h3>
                <Combobox label="Model" required options={models.map(m => ({ value: String(m.modelId), label: m.modelName }))} value={f.model} onChange={v => set("model", v)} placeholder="Select model..." disabled={lockNonOperationalFields} />
                <Txt label="Model Configuration" value={f.modelConfig} onChange={v => set("modelConfig", v)} placeholder="Standard / ICU Grade" readOnly={lockNonOperationalFields} />
                <Txt label="Unit SL No" value={f.unitSlno} onChange={v => set("unitSlno", v)} required placeholder="e.g. SN-4421" readOnly={lockNonOperationalFields} />
                <Combobox label="Unit Status" required options={toDdOpts(dd2Options)} value={f.unitStatus} onChange={v => set("unitStatus", v)} placeholder="Select status..." disabled={lockNonOperationalFields} />
                <DatePicker label="DOD" required={isDodRequired} value={f.dod} onChange={v => set("dod", v)} minDate={tenYearsAgo} maxDate={today} disabled={lockNonOperationalFields} />
                <Txt label="MOD/BRD Name" value={f.modBrdName} onChange={v => set("modBrdName", v)} required placeholder="e.g. PCB V1" readOnly={lockNonOperationalFields} />
                <Txt label="Part Number" value={f.partNumber} onChange={v => set("partNumber", v)} onBlur={handlePartNumberBlur} placeholder="e.g. PN-100" readOnly={lockNonOperationalFields} />
                <Txt label="Compatible Models" value={f.compatibleModels} onChange={v => { set("compatibleModels", v); set("defModBrdName", ""); }} placeholder="e.g. X1, X2" readOnly={lockNonOperationalFields} />
                <Txt label="DEF MOD/BRD Name" value={f.defModBrdName} onChange={v => set("defModBrdName", v)} required readOnly={lockNonOperationalFields} />
                {isAdmin && <Txt label="Cost (₹)" value={f.cost} onChange={v => set("cost", v)} placeholder="0.00" readOnly={lockNonOperationalFields} />}
                <Combobox label="DEF Type" required options={toDdOpts(dd3Options)} value={f.defType} onChange={v => set("defType", v)} disabled={lockNonOperationalFields} />
                <Combobox label="Type of ACC" required options={toDdOpts(dd4Options)} value={f.typeOfAcc} onChange={v => set("typeOfAcc", v)} disabled={lockNonOperationalFields} />
                <Txt label="DEF Part SNO" value={f.defPartSn} onChange={v => set("defPartSn", v)} required readOnly={lockNonOperationalFields} />
                <Txt label="DEF GIR No" value={f.defGirNo} onChange={v => set("defGirNo", v)} required uppercase readOnly={lockNonOperationalFields} />
                <Combobox label="REP Type" required options={toDdOpts(dd6Options)} value={f.repType} onChange={v => set("repType", v)} />
              </div>

              <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
                <h3 className="text-xs font-bold text-emerald-600 uppercase mb-2">Section 3: Repair & Shipping</h3>
                <Txt label="REP GIR SNO" required={isRepGirRequired} value={f.repGirNo} onChange={v => set("repGirNo", v)} uppercase />
                <Txt label="DEF UNIT GIR NO" value={f.defUnitGirNo} onChange={v => set("defUnitGirNo", v)} />
                <TxtA label="Field Remarks" value={f.fieldRemarks} onChange={v => set("fieldRemarks", v)} required={false} />
                <TxtA label="Technical Remarks" value={f.technicalRemarks} onChange={v => set("technicalRemarks", v)} />
                <TxtA label="Components Used" value={f.componentsUsedForRepair} onChange={v => set("componentsUsedForRepair", v)} />
                <DatePicker label="Repaired Brd Stk Date" required={isRepairStkDateRequired} disabled={isRepairStkDateReadOnly} value={f.repairedBrdStkDate} onChange={v => set("repairedBrdStkDate", v)} minDate={tenYearsAgo} maxDate={today} />
                <Txt label="Repaired Brd Adv No" value={f.repairedBrdAdvNo} onChange={v => set("repairedBrdAdvNo", v)} disabled={isRepairedBrdAdvNoReadOnly} required={isRepairedBrdAdvNoRequired} />
                <TxtA label="Final Remarks" value={f.finalRemarks} onChange={v => set("finalRemarks", v)} />
                <Combobox label="Type of Work" required options={toDdOpts(dd5Options)} value={f.typeOfWork} onChange={v => set("typeOfWork", v)} />
                <DatePicker label="Ship Date from SC" required={isShipDateSCRequired} disabled={isShipDateSCReadOnly} value={f.shipDtFrmSerCntr} onChange={v => set("shipDtFrmSerCntr", v)} minDate={tenYearsAgo} maxDate={today} />
                <Txt label="Advice No" value={f.dispAdvNo} onChange={v => set("dispAdvNo", v)} disabled={!f.shipDtFrmSerCntr} />
                <DatePicker label="Ship Date from Commercial" required={isShipDateCommRequired} disabled={isShipDateCommReadOnly} value={f.shipDateFromCommercial} onChange={v => set("shipDateFromCommercial", v)} minDate={tenYearsAgo} maxDate={today} />
                <Txt label="DC No" value={f.dcNo} onChange={v => set("dcNo", v)} required={isDcNoRequired} disabled={isDcNoReadOnly} />
                <Txt label="Commercial Details / Invoice Remarks" value={f.comrclDtlInvRmrk} onChange={v => set("comrclDtlInvRmrk", v)} />
                <Combobox label="Report Type" required options={reportTypeOptions.map(r => ({ value: r.id, label: r.value }))} value={f.reportType} onChange={v => set("reportType", v)} />
                <Txt label="Destination" value={f.destination} onChange={v => set("destination", v)} readOnly={isDestinationReadOnly} placeholder="City / Branch Name" />
              </div>
            </div>
          )}

          <div className="pt-3 border-t border-slate-100">
            {!saveVisible ? (
              <p className="text-xs text-slate-400 text-center py-1">Select REP TYPE + TYPE OF WORK to enable Save</p>
            ) : (
              <button form="service-form-edit" type="submit" disabled={submitting}
                className="inline-flex min-w-[220px] items-center justify-center gap-2 px-5 py-3 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 active:scale-95 transition-all disabled:opacity-70 disabled:cursor-not-allowed"
                style={{ background: "linear-gradient(135deg, #10b981, #059669)" }}>
                {submitting ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
                {submitting ? "Saving…" : "Update Record"}
              </button>
            )}
          </div>
        </div>
      </form>
    </div>
  );
}
