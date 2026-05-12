"use client";

import { apiFetch, getApiBaseUrl } from "@/lib/api";
import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { ArrowLeft, Save, Loader2 } from "lucide-react";
import { DatePicker } from "@/components/ui/date-picker";
import { Combobox } from "@/components/ui/combobox";
import {
  DDV1_STK_CUST, DDV2_UNIT_STATUS, DDV3_DEF_TYPE, DDV4_TYPE_OF_ACC,
  DDV5_TYPE_OF_WORK, DDV6_REP_TYPE, SUPPLIERS, MODELS, REPORT_TYPES,
} from "@/lib/service-mock-data";
import { ServiceService, Region, Branch, Dealer, Employee as RAEmployee, Model, Supplier, DropdownOption } from "@/services/service-service";

const MOCK_USER = { name: "Admin", role: "admin", id: 0, division: "Cardiac" };

// Division option type from /api/products
interface DivisionOption { value: string; label: string; }

// ─── Stable field components ──────────────────────────────────────────────────
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

// ─── Main Page ────────────────────────────────────────────────────────────────
export default function AddServicePage() {
  const router = useRouter();
  const [saved, setSaved] = useState(false);
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const isAdmin = MOCK_USER.role === "admin";

  const [divisionOptions, setDivisionOptions] = useState<DivisionOption[]>([]);
  const [divisionsLoading, setDivisionsLoading] = useState(true);
  const [engineerOpts, setEngineerOpts] = useState<{ value: string; label: string }[]>([]);
  const [engineersLoading, setEngineersLoading] = useState(true);

  const [regions, setRegions] = useState<Region[]>([]);
  const [branches, setBranches] = useState<Branch[]>([]);
  const [dealers, setDealers] = useState<Dealer[]>([]);
  const [raEngineers, setRaEngineers] = useState<RAEmployee[]>([]);
  const [models, setModels] = useState<Model[]>([]);
  const [suppliers, setSuppliers] = useState<Supplier[]>([]);

  const [regionsLoading, setRegionsLoading] = useState(true);
  const [cascadingLoading, setCascadingLoading] = useState(false);
  const [modelsLoading, setModelsLoading] = useState(true);
  const [suppliersLoading, setSuppliersLoading] = useState(true);

  // Dropdown Master State
  const [dd1Options, setDd1Options] = useState<DropdownOption[]>([]);
  const [dd2Options, setDd2Options] = useState<DropdownOption[]>([]);
  const [dd3Options, setDd3Options] = useState<DropdownOption[]>([]);
  const [dd4Options, setDd4Options] = useState<DropdownOption[]>([]);
  const [dd5Options, setDd5Options] = useState<DropdownOption[]>([]);
  const [dd6Options, setDd6Options] = useState<DropdownOption[]>([]);
  const [ddLoading, setDdLoading] = useState(true);

  useEffect(() => {
    apiFetch(`${getApiBaseUrl()}/products`)
      .then(r => r.json())
      .then((data: { productId: number; divisionName: string }[]) => {
        setDivisionOptions(data.map(d => ({ value: d.divisionName, label: d.divisionName })));
      })
      .catch(console.error).finally(() => setDivisionsLoading(false));

    apiFetch(`${getApiBaseUrl()}/employees?page=0&size=200`)
      .then(r => r.json())
      .then((data: { content: { empId: number; empName: string }[] }) => {
        setEngineerOpts(data.content.map(e => ({ value: String(e.empId), label: e.empName })));
      })
      .catch(console.error).finally(() => setEngineersLoading(false));

    ServiceService.getRegions().then(setRegions).catch(console.error).finally(() => setRegionsLoading(false));
    ServiceService.getModels().then(setModels).catch(console.error).finally(() => setModelsLoading(false));
    ServiceService.getSuppliers().then(setSuppliers).catch(console.error).finally(() => setSuppliersLoading(false));

    Promise.all([
      ServiceService.getDropdownOptions(1).then(setDd1Options),
      ServiceService.getDropdownOptions(2).then(setDd2Options),
      ServiceService.getDropdownOptions(3).then(setDd3Options),
      ServiceService.getDropdownOptions(4).then(setDd4Options),
      ServiceService.getDropdownOptions(5).then(setDd5Options),
      ServiceService.getDropdownOptions(6).then(setDd6Options),
    ]).catch(console.error).finally(() => setDdLoading(false));
  }, []);

  const [f, setF] = useState({
    division: isAdmin ? "" : MOCK_USER.division,
    scRefNo: "", scEngineer: isAdmin ? "" : String(MOCK_USER.id),
    frnNo: "", frnDate: "", serCommInwardDate: "", serCentreReceivedDate: "",
    stkCust: "", region: "", branch: "", engineer: "", dealer: "",
    custName: "", supplier: "", model: "", modelConfig: "", unitSlno: "",
    unitStatus: "", dod: "", modBrdName: "", partNumber: "", compatibleModels: "",
    defModBrdName: "", cost: "", defType: "", typeOfAcc: "", defPartSn: "",
    defGirNo: "", repType: "", repGirNo: "", defUnitGirNo: "", fieldRemarks: "",
    technicalRemarks: "", componentsUsedForRepair: "", repairedBrdStkDate: "",
    repairedBrdAdvNo: "", finalRemarks: "", typeOfWork: "",
    shipDtFrmSerCntr: "", dispAdvNo: "", shipDateFromCommercial: "",
    dcNo: "", reportType: "servicecenter", destination: "",
    comrclDtlInvRmrk: "",
  });

  const set = (k: keyof typeof f, v: string) => {
    setF(prev => {
      const next = { ...prev, [k]: v };
      // changefun4 & changefun6 logic: reset report_type on rep_type or type_work change
      if (k === "repType" || k === "typeOfWork") {
        next.reportType = "servicecenter";
      }

      // changefn logic: only controls readOnly state, does NOT auto-populate destination text.
      
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
        ServiceService.getEmployeesByRegion(rid)
      ]);
      setBranches(b); setDealers(d); setRaEngineers(e);
    } catch (err) { console.error(err); } finally { setCascadingLoading(false); }
  };

  const handleSupplier = async (v: string) => {
    setF(prev => ({ ...prev, supplier: v, model: "" }));
    if (!v) { ServiceService.getModels().then(setModels); return; }
    try {
      const filtered = await ServiceService.getModelsBySupplier(v);
      setModels(filtered);
    } catch (err) { console.error(err); }
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
    } catch (error) {
      console.error("Failed to fetch spare part details:", error);
    }
  };

  const toDdOpts = (opts: DropdownOption[]) => opts.map(o => ({ value: String(o.id), label: o.ddValue }));

  // Date limits per ServiceForm.jsp — datepicker-3 had minDate:-1, maxDate:0. Others get maxDate:today, minDate:10y ago by default.
  const today = new Date();
  const yesterday = (() => { const d = new Date(today); d.setDate(d.getDate() - 1); return d; })();
  const tenYearsAgo = (() => { const d = new Date(today); d.setFullYear(d.getFullYear() - 10); return d; })();

  // --- Derived Conditionals (Legacy Logic) ---
  const gir = (f.repGirNo || "").toUpperCase();
  const defGir = (f.defGirNo || "").toUpperCase();
  const girMatch = !!(gir && defGir && gir === defGir);

  // REP TYPE IDs: 56 (NA), 60 (PRF/ADV OB)
  const isNa = f.repType === "56";
  const isPrfAdvOb = f.repType === "60";

  // 1. Unit Status -> DOD Logic (from Select() in JSP)
  const isDodRequired = f.unitStatus === "44" || f.unitStatus === "43";

  // 2. Field States (from changefun1, changefun3, changefun4)
  // --- REP GIR SNO ---
  const isRepGirRequired = isPrfAdvOb;

  // --- Date Pickers & DC NO ---
  // Repaired Brd Stk Date (datepicker-15)
  // changefun1: Case 56 & matched: readOnly=false, req=true.
  // changefun_scrapp: Case 60 & wt=23 (SCRAPPED): readOnly=false, req=true.
  const isRepairStkDateReadOnly = !( (isNa && girMatch) || (isPrfAdvOb && f.typeOfWork === "23") );
  const isRepairStkDateRequired = (isNa && girMatch) || (isPrfAdvOb && f.typeOfWork === "23");

  // Ship Date from SC (datepicker-13)
  const isShipDateSCReadOnly = !( (isNa && gir !== "") || (isPrfAdvOb && gir !== "") );
  const isShipDateSCRequired = isNa || isPrfAdvOb;

  // Ship Date from Commercial (datepicker-16)
  const isShipDateCommReadOnly = !(isPrfAdvOb && gir !== "");
  const isShipDateCommRequired = isPrfAdvOb;

  // DC No (dcno)
  // changefun1: sets readOnly/required based on repType/gir
  // commercialDate: overrides readOnly=true if datepicker-16 is empty
  const isDcNoReadOnly = !(isPrfAdvOb && gir !== "") || !f.shipDateFromCommercial;
  const isDcNoRequired = isPrfAdvOb && !!f.shipDateFromCommercial;

  // Repaired Brd Adv No (STOCK ADV NO in legacy)
  // Required/Editable only if Report Type is stocklist AND datepicker-15 has value
  const isRepairedBrdAdvNoReadOnly = f.reportType !== "stocklist" || !f.repairedBrdStkDate;
  const isRepairedBrdAdvNoRequired = f.reportType === "stocklist" && !!f.repairedBrdStkDate;

  // Destination (destination)
  // changefn: readOnly=true for servicecenter, scraplist, stocklist.
  // changefun1: Case 56 & matched: readOnly=false.
  const isDestinationReadOnly = (f.reportType === "servicecenter" || f.reportType === "scraplist" || f.reportType === "stocklist") && !isNa;

  // 3. Save Button Visibility (changefun_save in JSP)
  const saveVisible = (() => {
    if (!f.repType) return false;
    const wt = f.typeOfWork; // Numeric ID from dropdown_master (Group 5)

    if (isPrfAdvOb) {
      return wt === "25" || wt === "23"; // UNDER REPAIR (25) or SCRAPPED (23)
    }
    if (isNa) {
      if (!gir) return wt === "25" || wt === "61"; // UNDER REPAIR (25) or OB PENDING (61)
      if (girMatch) return ["20", "21", "26", "29", "30"].includes(wt); // REPAIRED, UNIT RETURNED, etc.
      return wt === "25"; // UNDER REPAIR
    }
    return false;
  })();

  const reportTypeOptions = (() => {
    let extra: string[] = [];
    const wt = f.typeOfWork;

    if (isNa) {
      if (!gir) {
        extra = []; // servicecenter only
      } else {
        extra = ["dispatchlist"]; // servicecenter + dispatchlist
      }
    } else if (isPrfAdvOb) {
      if (!gir) {
        extra = [];
      } else if (wt === "23") {
        extra = ["scraplist"]; // servicecenter + scraplist
      } else {
        extra = [];
      }
    }

    // Always include servicecenter; add extras based on repType/repGir/typeOfWork
    const available = ["servicecenter", ...extra];
    const filtered = REPORT_TYPES.filter(r => available.includes(r.id));
    return filtered;
  })();

  const destinationEnabled = f.reportType === "dispatchlist";

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);
    try {
      const regionObj = regions.find(r => String(r.regionId) === f.region);
      const branchObj = branches.find(b => String(b.id) === f.branch);
      const dealerObj = dealers.find(d => String(d.dealerId) === f.dealer);
      const stkCustLabel = dd1Options.find(d => String(d.id) === f.stkCust)?.ddValue ?? f.stkCust;

      const todayStr = new Date().toISOString().split("T")[0];
      const payload = {
        scRefNo: f.scRefNo,
        entryDate: todayStr,
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
      await ServiceService.createService(payload);
      setSaved(true);
      setTimeout(() => router.push("/dashboard/services"), 1500);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to save service record");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-5">
      <div className="flex items-center gap-4">
        <Link href="/dashboard/services" className="p-2 rounded-xl hover:bg-slate-100 text-slate-500 transition">
          <ArrowLeft className="w-5 h-5" />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Add Service Record</h1>
          <p className="text-slate-500 text-sm mt-0.5">Fill and save service details</p>
        </div>
      </div>

      {error && (
        <div className="px-4 py-3 rounded-xl bg-red-50 border border-red-200 text-red-700 text-sm font-medium">
          {error}
        </div>
      )}
      {saved && (
        <div className="px-4 py-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-700 text-sm font-medium">
          ✅ Service record saved! Redirecting…
        </div>
      )}

      <form id="service-form" onSubmit={handleSubmit}>
        <div className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 items-start">
            {/* Section 1 */}
            <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
              <h3 className="text-xs font-bold text-violet-600 uppercase mb-2">Section 1: Header Details</h3>
              {isAdmin ? <Combobox label="Division" required options={divisionOptions} value={f.division} onChange={v => set("division", v)} placeholder="Select division..." /> : <Txt label="Division" value={f.division} onChange={() => {}} disabled />}
              <Txt label="SC Ref No" value={f.scRefNo} onChange={v => set("scRefNo", v)} required placeholder="e.g. SC-1249" />
              {isAdmin ? <Combobox label="SC Engineer" required options={engineerOpts} value={f.scEngineer} onChange={v => set("scEngineer", v)} placeholder="Select SC engineer..." />: <Txt label="SC Engineer" value={f.scEngineer} onChange={() => {}} disabled />}
              <Txt label="FRN No" value={f.frnNo} onChange={v => set("frnNo", v)} required placeholder="e.g. FRN-8830" />
              <DatePicker label="FRN Date" required value={f.frnDate} onChange={v => set("frnDate", v)}
                minDate={tenYearsAgo} maxDate={today} />
              <DatePicker label="Ser Comm Inward Date" required value={f.serCommInwardDate} onChange={v => set("serCommInwardDate", v)}
                minDate={tenYearsAgo} maxDate={today} />
              <DatePicker label="Received Date Ser Centre" required value={f.serCentreReceivedDate} onChange={v => set("serCentreReceivedDate", v)}
                minDate={yesterday} maxDate={today} />
              <Combobox label="STK/CUST" required options={toDdOpts(dd1Options)} value={f.stkCust} onChange={v => set("stkCust", v)} placeholder="Select..." />
              <Combobox label="Region" required options={regions.map(r => ({ value: String(r.regionId), label: r.regionName }))} value={f.region} onChange={handleRegion} placeholder="Select region..." />
              <Combobox label="Branch" required options={branches.map(b => ({ value: String(b.id), label: b.branchName }))} value={f.branch} onChange={v => set("branch", v)} disabled={!f.region} placeholder="Select branch..." />
              <Combobox label="Engineer" required options={raEngineers.map(e => ({ value: String(e.empId), label: e.empName }))} value={f.engineer} onChange={v => set("engineer", v)} disabled={!f.region} placeholder="Select engineer..." />
              <Combobox label="Dealer Name" required options={dealers.map(d => ({ value: String(d.dealerId), label: d.dealerName }))} value={f.dealer} onChange={v => set("dealer", v)} disabled={!f.region} placeholder="Select dealer..." />
              <Txt label="Customer Name" value={f.custName} onChange={v => set("custName", v)} required placeholder="e.g. Apollo Hospitals" />
              <Combobox label="Supplier" required options={suppliers.map(s => ({ value: s.supplierName, label: s.supplierName }))} value={f.supplier} onChange={handleSupplier} placeholder="Select supplier..." />
            </div>

            {/* Section 2 */}
            <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
              <h3 className="text-xs font-bold text-blue-600 uppercase mb-2">Section 2: Product & Defect</h3>
              <Combobox label="Model" required options={models.map(m => ({ value: String(m.modelId), label: m.modelName }))} value={f.model} onChange={v => set("model", v)} placeholder="Select model..." />
              <Txt label="Model Configuration" value={f.modelConfig} onChange={v => set("modelConfig", v)} placeholder="Standard / ICU Grade" />
              <Txt label="Unit SL No" value={f.unitSlno} onChange={v => set("unitSlno", v)} required placeholder="e.g. SN-4421" />
              <Combobox label="Unit Status" required options={toDdOpts(dd2Options)} value={f.unitStatus} onChange={v => set("unitStatus", v)} placeholder="Select status..." />
              <DatePicker label="DOD" required={isDodRequired} value={f.dod} onChange={v => set("dod", v)}
                minDate={tenYearsAgo} maxDate={today} />
              <Txt label="MOD/BRD Name" value={f.modBrdName} onChange={v => set("modBrdName", v)} required placeholder="e.g. PCB V1" />
              <Txt label="Part Number" value={f.partNumber} onChange={v => set("partNumber", v)} onBlur={handlePartNumberBlur} placeholder="e.g. PN-100" />
              <Txt label="Compatible Models" value={f.compatibleModels} onChange={v => { set("compatibleModels", v); set("defModBrdName", ""); }} placeholder="e.g. X1, X2" />
              <Txt label="DEF MOD/BRD Name" value={f.defModBrdName} onChange={v => set("defModBrdName", v)} required />
              {isAdmin && <Txt label="Cost (₹)" value={f.cost} onChange={v => set("cost", v)} placeholder="0.00" />}
              <Combobox label="DEF Type" required options={toDdOpts(dd3Options)} value={f.defType} onChange={v => set("defType", v)} />
              <Combobox label="Type of ACC" required options={toDdOpts(dd4Options)} value={f.typeOfAcc} onChange={v => set("typeOfAcc", v)} />
              <Txt label="DEF Part SNO" value={f.defPartSn} onChange={v => set("defPartSn", v)} required />
              <Txt label="DEF GIR No" value={f.defGirNo} onChange={v => set("defGirNo", v)} required uppercase />
              <Combobox label="REP Type" required options={toDdOpts(dd6Options)} value={f.repType} onChange={v => set("repType", v)} />
            </div>

            {/* Section 3 */}
            <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4 space-y-3">
              <h3 className="text-xs font-bold text-emerald-600 uppercase mb-2">Section 3: Repair & Shipping</h3>
              <Txt label="REP GIR SNO" required={isRepGirRequired} value={f.repGirNo} onChange={v => set("repGirNo", v)} uppercase />
              <Txt label="DEF UNIT GIR NO" value={f.defUnitGirNo} onChange={v => set("defUnitGirNo", v)} />
              <TxtA label="Field Remarks" value={f.fieldRemarks} onChange={v => set("fieldRemarks", v)} required />
              <TxtA label="Technical Remarks" value={f.technicalRemarks} onChange={v => set("technicalRemarks", v)} />
              <TxtA label="Components Used" value={f.componentsUsedForRepair} onChange={v => set("componentsUsedForRepair", v)} />
              <DatePicker label="Repaired Brd Stk Date" required={isRepairStkDateRequired} disabled={isRepairStkDateReadOnly} value={f.repairedBrdStkDate} onChange={v => set("repairedBrdStkDate", v)}
                minDate={tenYearsAgo} maxDate={today} />
              <Txt label="Repaired Brd Adv No" value={f.repairedBrdAdvNo} onChange={v => set("repairedBrdAdvNo", v)} 
                disabled={isRepairedBrdAdvNoReadOnly} required={isRepairedBrdAdvNoRequired} />
              <TxtA label="Final Remarks" value={f.finalRemarks} onChange={v => set("finalRemarks", v)} />
              <Combobox label="Type of Work" required options={toDdOpts(dd5Options)} value={f.typeOfWork} onChange={v => set("typeOfWork", v)} />
              <DatePicker label="Ship Date from SC"
                required={isShipDateSCRequired} disabled={isShipDateSCReadOnly} value={f.shipDtFrmSerCntr} onChange={v => set("shipDtFrmSerCntr", v)}
                minDate={tenYearsAgo} maxDate={today} />
              <Txt label="Advice No" value={f.dispAdvNo} onChange={v => set("dispAdvNo", v)} disabled={!f.shipDtFrmSerCntr} />
              <DatePicker label="Ship Date from Commercial"
                required={isShipDateCommRequired} disabled={isShipDateCommReadOnly} value={f.shipDateFromCommercial} onChange={v => set("shipDateFromCommercial", v)}
                minDate={tenYearsAgo} maxDate={today} />
              <Txt label="DC No" value={f.dcNo} onChange={v => set("dcNo", v)} required={isDcNoRequired} disabled={isDcNoReadOnly} />
              <Txt label="Commercial Details / Invoice Remarks" value={f.comrclDtlInvRmrk} onChange={v => set("comrclDtlInvRmrk", v)} />
              <Combobox label="Report Type" required options={reportTypeOptions.map(r => ({ value: r.id, label: r.value }))} value={f.reportType} onChange={v => set("reportType", v)} />
              <Txt label="Destination" value={f.destination} onChange={v => set("destination", v)} readOnly={isDestinationReadOnly} placeholder="City / Branch Name" />

              {/* Save button at bottom of form — mirrors JSP layout */}
              <div className="pt-3 border-t border-slate-100">
                {!saveVisible ? (
                  <p className="text-xs text-slate-400 text-center py-1">
                    Select REP TYPE + TYPE OF WORK to enable Save
                  </p>
                ) : (
                  <button form="service-form" type="submit" disabled={submitting}
                    className="w-full flex items-center justify-center gap-2 px-5 py-3 rounded-xl text-sm font-semibold text-white shadow-md hover:opacity-90 active:scale-95 transition-all disabled:opacity-70 disabled:cursor-not-allowed"
                    style={{ background: "linear-gradient(135deg, #10b981, #059669)" }}>
                    {submitting ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
                    {submitting ? "Saving…" : "Save Record"}
                  </button>
                )}
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  );
}
