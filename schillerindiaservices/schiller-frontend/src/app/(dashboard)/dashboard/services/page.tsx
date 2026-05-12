"use client";

import { useState, useEffect, type ReactNode } from "react";
import Link from "next/link";
import { Plus, Download, Edit, Trash2, FileText, Search, ClipboardList } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { apiFetch, getApiBaseUrl } from "@/lib/api";
import { cn } from "@/lib/utils";
import { ServiceService, type ServiceRecord, type DropdownOption, type Model } from "@/services/service-service";
import { Loader2 } from "lucide-react";
import { DatePicker } from "@/components/ui/date-picker";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const STATUS_COLORS: Record<string, string> = {
  OW: "bg-amber-100 text-amber-700 border-amber-200",
  LAMC: "bg-sky-100 text-sky-700 border-sky-200",
  NW: "bg-slate-100 text-slate-600 border-slate-200",
  RD: "bg-violet-100 text-violet-700 border-violet-200",
  BM: "bg-rose-100 text-rose-700 border-rose-200",
  IW: "bg-emerald-100 text-emerald-700 border-emerald-200",
  EW: "bg-teal-100 text-teal-700 border-teal-200",
  DEMO: "bg-pink-100 text-pink-700 border-pink-200",
  STOCK: "bg-slate-100 text-slate-600 border-slate-200",
};

// Distinct palette from STATUS_COLORS (amber/sky/slate/violet/rose/emerald/teal/pink) — Type of Work uses indigo/cyan/lime/fuchsia/blue/red/purple etc.
const WORK_TYPE_COLORS: Record<string, string> = {
  REPAIRED: "bg-indigo-100 text-indigo-700 border-indigo-200",
  "UNDER REPAIR": "bg-cyan-100 text-cyan-700 border-cyan-200",
  COMPLETED: "bg-lime-100 text-lime-700 border-lime-200",
  "UNIT RETURNED": "bg-fuchsia-100 text-fuchsia-700 border-fuchsia-200",
  SCRAPPED: "bg-rose-100 text-rose-900 border-rose-300",
  UPGRADE: "bg-blue-100 text-blue-700 border-blue-200",
  "NO FAULT": "bg-stone-100 text-stone-600 border-stone-200",
  "RETURNED AS IT IS": "bg-orange-100 text-orange-700 border-orange-200",
  "EXTERNAL REPAIR": "bg-red-100 text-red-700 border-red-200",
  "OB PENDING": "bg-yellow-100 text-yellow-700 border-yellow-200",
  "GIVEN TO PSP": "bg-purple-100 text-purple-700 border-purple-200",
  "RE-EXPORT": "bg-neutral-100 text-neutral-700 border-neutral-200",
};

const PAGE_SIZE_OPTIONS = [10, 25, 50] as const;

type ColumnDef = { key: string; label: string };

/** Admin Service Master: full operational columns. */
const ADMIN_COLUMNS: ColumnDef[] = [
  { key: "scRefNo", label: "SC Ref No" },
  { key: "entryDate", label: "Entry Date" },
  { key: "scEngineerId", label: "SC Eng" },
  { key: "frnNo", label: "FRN No" },
  { key: "region", label: "Region" },
  { key: "raEngineerId", label: "RA Eng" },
  { key: "custName", label: "Customer" },
  { key: "productModel", label: "Model" },
  { key: "unitStatus", label: "Unit Status" },
  { key: "defModBrdName", label: "Def Mod/Brd" },
  { key: "defGirNo", label: "Def GIR No" },
  { key: "typeOfWork", label: "Type of Work" },
  { key: "dcNo", label: "DC No" },
  { key: "dispAdvNo", label: "Disp Adv" },
  { key: "repairedBrdAdvNo", label: "Rep Brd Adv" },
  { key: "comrclDtlInvRmrk", label: "Comrcl Rmrk" },
];

/** Legacy ServiceListEngg.jsp: status, pending-day columns; no DC / dispatch / commercial extras. */
const ENGINEER_COLUMNS: ColumnDef[] = [
  { key: "status", label: "Status" },
  { key: "entryDate", label: "Entry Date" },
  { key: "scRefNo", label: "SC R No" },
  { key: "scEngineerId", label: "SC Eng" },
  { key: "frnNo", label: "FRN No" },
  { key: "region", label: "Region" },
  { key: "raEngineerId", label: "Engineer" },
  { key: "custName", label: "Cust Name" },
  { key: "productModel", label: "Model" },
  { key: "unitStatus", label: "Unit Status" },
  { key: "defModBrdName", label: "Def Mod/Brd" },
  { key: "defGirNo", label: "Def GIR No" },
  { key: "typeOfWork", label: "Type of Work" },
  { key: "pendDaysPfrn", label: "Pend. Days (PFRN)" },
  { key: "pendDaysObp", label: "Pend. Days (OBP)" },
  { key: "pendDaysUrp", label: "Pend. Days (URP)" },
  { key: "pendDaysScc", label: "Pend. Days (SCC)" },
];

function normalizeRole(role: string | undefined): string {
  return (role ?? "").trim().replace(/^ROLE_/i, "").toUpperCase();
}

function renderServiceCell(
  row: ServiceRecord,
  def: ColumnDef,
  dd2Options: DropdownOption[],
  dd5Options: DropdownOption[],
  employeeMap: Record<number, string>,
  modelMap: Record<string, string>,
  compact?: boolean
): ReactNode {
  const badgeCn = compact
    ? "text-[10px] rounded-full px-1.5 py-0 leading-tight font-medium"
    : "text-xs rounded-full px-2.5";
  switch (def.key) {
    case "scEngineerId":
      return nil(row.scEngineerName ?? (row.scEngineerId != null ? employeeMap[row.scEngineerId] : null) ?? row.scEngineerId);
    case "raEngineerId":
      return nil(row.raEngineerName ?? (row.raEngineerId != null ? employeeMap[row.raEngineerId] : null) ?? row.raEngineerId);
    case "unitStatus": {
      const unitStatusLabel = idToLabel(dd2Options, row.unitStatus);
      return (
        <Badge variant="outline" className={`${badgeCn} ${unitStatusLabel !== "—" ? STATUS_COLORS[unitStatusLabel] || "bg-slate-100 text-slate-600" : "bg-slate-100 text-slate-600"}`}>
          {unitStatusLabel}
        </Badge>
      );
    }
    case "typeOfWork": {
      const typeOfWorkLabel = idToLabel(dd5Options, row.typeOfWork);
      return typeOfWorkLabel !== "—" ? (
        <Badge variant="outline" className={`${badgeCn} ${WORK_TYPE_COLORS[typeOfWorkLabel] || "bg-slate-100 text-slate-600"}`}>
          {typeOfWorkLabel}
        </Badge>
      ) : (
        <span className="text-slate-400">—</span>
      );
    }
    case "productModel":
      return nil(modelMap[row.productModel ?? ""] ?? row.productModel);
    case "pendDaysPfrn":
    case "pendDaysObp":
    case "pendDaysUrp":
    case "pendDaysScc":
      return (
        <span className={cn("text-slate-600 tabular-nums whitespace-nowrap", compact ? "text-[10px]" : "text-xs")}>
          {nil(row[def.key as keyof ServiceRecord] as unknown)}
        </span>
      );
    default:
      return nil(row[def.key as keyof ServiceRecord] as unknown);
  }
}

function idToLabel(opts: DropdownOption[], id: string | undefined): string {
  if (!id) return "—";
  const o = opts.find(d => String(d.id) === String(id));
  return o?.ddValue ?? id;
}

function nil(val: unknown): string {
  return val == null || val === "null" || val === "" ? "—" : String(val);
}

interface Emp { empId: number; empName: string }
interface AuthProfile { role?: string }

async function safeJson<T>(response: Response): Promise<T | null> {
  const text = await response.text();
  if (!text.trim()) return null;
  try {
    return JSON.parse(text) as T;
  } catch {
    return null;
  }
}

export default function ServicesPage() {
  const [search, setSearch] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState<number>(10);

  const [services, setServices] = useState<ServiceRecord[]>([]);
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [isLoading, setIsLoading] = useState(true);

  const [dd2Options, setDd2Options] = useState<DropdownOption[]>([]);
  const [dd5Options, setDd5Options] = useState<DropdownOption[]>([]);
  const [employeeMap, setEmployeeMap] = useState<Record<number, string>>({});
  const [modelMap, setModelMap] = useState<Record<string, string>>({});
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [serviceToDelete, setServiceToDelete] = useState<ServiceRecord | null>(null);
  const [deleteLoading, setDeleteLoading] = useState(false);
  const [exportLoading, setExportLoading] = useState(false);
  const [profile, setProfile] = useState<AuthProfile | null>(null);
  const role = normalizeRole(profile?.role);
  const isEngineer = role === "ENGINEER";
  const columns = isEngineer ? ENGINEER_COLUMNS : ADMIN_COLUMNS;
  const thPad = isEngineer ? "px-2.5 py-2" : "px-4 py-3.5";
  const tdPad = isEngineer ? "px-2.5 py-1.5" : "px-4 py-3";

  useEffect(() => {
    Promise.all([
      ServiceService.getDropdownOptions(2).then(setDd2Options),
      ServiceService.getDropdownOptions(5).then(setDd5Options),
      apiFetch(`${getApiBaseUrl()}/employees?page=0&size=500`).then(r => (r.ok ? r.json() : { content: [] })),
      apiFetch(`${getApiBaseUrl()}/auth/me`).then(r => r.ok ? safeJson<AuthProfile>(r) : null),
      ServiceService.getModels().then((models: Model[]) => {
        const map: Record<string, string> = {};
        models.forEach((m: Model) => { map[String(m.modelId)] = m.modelName; });
        return map;
      }),
    ]).then(([, , empRes, meRes, modelMapRes]) => {
      const list: Emp[] = empRes?.content ?? empRes ?? [];
      const empMap: Record<number, string> = {};
      list.forEach((e: Emp) => { empMap[e.empId] = e.empName; });
      setEmployeeMap(empMap);
      setProfile(meRes);
      setModelMap(modelMapRes);
    }).catch(console.error);
  }, []);

  useEffect(() => {
    const timer = setTimeout(() => setSearchTerm(search), 500);
    return () => clearTimeout(timer);
  }, [search]);

  const loadServices = async () => {
    try {
      setIsLoading(true);
      const data = await ServiceService.getServices(page, pageSize, searchTerm);
      setServices(data.content);
      setTotalElements(data.totalElements);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error("Failed to load services", err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDeleteClick = (row: ServiceRecord) => {
    if (!row.id) return;
    setServiceToDelete(row);
    setDeleteDialogOpen(true);
  };

  const handleExportExcel = async () => {
    setExportLoading(true);
    try {
      const blob = await ServiceService.exportToExcel({
        from: fromDate || undefined,
        to: toDate || undefined,
        search: searchTerm || undefined,
      });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `ServiceList_${new Date().toISOString().slice(0, 10)}.xlsx`;
      a.click();
      URL.revokeObjectURL(url);
    } catch (err) {
      console.error("Export failed", err);
      alert(err instanceof Error ? err.message : "Export failed");
    } finally {
      setExportLoading(false);
    }
  };

  const handleConfirmDelete = async () => {
    if (!serviceToDelete?.id) return;
    setDeleteLoading(true);
    try {
      await ServiceService.deleteService(serviceToDelete.id);
      loadServices();
      setDeleteDialogOpen(false);
      setServiceToDelete(null);
    } catch (err) {
      console.error("Failed to delete service", err);
      alert(err instanceof Error ? err.message : "Failed to delete service.");
    } finally {
      setDeleteLoading(false);
    }
  };

  useEffect(() => {
    loadServices();
  }, [page, pageSize, searchTerm]);

  const paginated = services;
  const rangeStart = totalElements === 0 ? 0 : page * pageSize + 1;
  const rangeEnd = Math.min((page + 1) * pageSize, totalElements);

  return (
    <div className="space-y-5">
      {/* Header */}
      <div className="flex items-start justify-between">
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">{isEngineer ? "Service List" : "Service Master"}</h1>
          <p className="text-slate-500 text-sm mt-1">
            {isEngineer
              ? <>Service calls in your division or assigned to you — {totalElements} entries </>
              : <>All service call records — {totalElements} entries </>}
            {isLoading && <Loader2 className="inline w-3 h-3 animate-spin" />}
          </p>
        </div>
        <Link
          href="/dashboard/services/add"
          className="flex items-center gap-2 px-4 py-2.5 rounded-xl text-sm font-semibold text-white shadow-md hover:shadow-lg hover:opacity-90 transition-all"
          style={{
            background: isEngineer
              ? "linear-gradient(135deg, #0d9488, #0f766e)"
              : "linear-gradient(135deg, #6366f1, #8b5cf6)",
          }}
        >
          <Plus className="w-4 h-4" /> Add New Record
        </Link>
      </div>

      {/* Filter bar */}
      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm p-4">
        <div className="flex flex-col gap-3 sm:flex-row sm:flex-wrap sm:items-end sm:justify-between sm:gap-x-4 sm:gap-y-3">
          <div className="flex items-center gap-2 px-3 py-2.5 rounded-xl bg-slate-50 border border-slate-200 text-sm text-slate-400 w-full sm:max-w-[260px] sm:w-auto shrink-0">
            <Search className="w-4 h-4 shrink-0" />
            <input value={search} onChange={e => { setSearch(e.target.value); setPage(0); }}
              className="bg-transparent outline-none w-full min-w-0 text-slate-700 placeholder:text-slate-400"
              placeholder="Search customer, SC ref, model…" />
          </div>
          <div className="flex flex-wrap items-end gap-3 sm:justify-end sm:ml-auto">
            <div className="flex items-end gap-2">
              <DatePicker value={fromDate} onChange={v => { setFromDate(v); setPage(0); }} placeholder="From date" label="From Date" />
              <DatePicker value={toDate} onChange={v => { setToDate(v); setPage(0); }} placeholder="To date" label="To Date" />
            </div>
            <button
              type="button"
              onClick={handleExportExcel}
              disabled={exportLoading}
              className="flex items-center gap-2 px-4 py-2.5 rounded-xl text-sm font-semibold text-white shadow-sm hover:opacity-90 transition disabled:opacity-60 shrink-0"
              style={{ background: "linear-gradient(135deg, #10b981, #059669)" }}>
              {exportLoading ? <Loader2 className="w-4 h-4 animate-spin" /> : <Download className="w-4 h-4" />}
              Export Excel
            </button>
          </div>
        </div>
      </div>

      {/* Table */}
      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="overflow-x-auto">
          <table className={cn("w-full", isEngineer ? "min-w-[1180px] text-xs" : "min-w-[1400px] text-sm")}>
            <thead>
              <tr className="border-b border-slate-100 bg-slate-50/60">
                <th className={cn("text-left font-semibold tracking-wide text-slate-400 uppercase w-10", isEngineer ? "text-[10px]" : "text-[11px]", thPad)}>#</th>
                {columns.map(c => (
                  <th key={c.key} className={cn("text-left font-semibold tracking-wide text-slate-400 uppercase whitespace-nowrap", isEngineer ? "text-[10px]" : "text-[11px]", thPad)}>{c.label}</th>
                ))}
                <th className={cn("text-left font-semibold tracking-wide text-slate-400 uppercase", isEngineer ? "text-[10px]" : "text-[11px]", thPad)}>{isEngineer ? "Job sheet" : "Actions"}</th>
              </tr>
            </thead>
            <tbody>
              {paginated.length === 0 ? (
                <tr><td colSpan={columns.length + 2} className="text-center py-12 text-slate-400 text-sm">No records found</td></tr>
              ) : paginated.map((row: ServiceRecord, i) => (
                <tr
                  key={row.id}
                  className={cn(
                    "border-b border-slate-50 transition-colors last:border-0",
                    isEngineer ? "hover:bg-teal-50/30" : "hover:bg-violet-50/20"
                  )}
                >
                  <td className={cn(tdPad, "text-slate-400", isEngineer ? "text-[10px]" : "text-xs")}>{page * pageSize + i + 1}</td>
                  {columns.map(c => (
                    <td
                      key={c.key}
                      className={cn(
                        tdPad,
                        c.key === "scRefNo" && cn("font-mono font-semibold text-violet-600 whitespace-nowrap", isEngineer ? "text-[11px]" : "text-xs"),
                        c.key === "status" && cn("text-slate-700", isEngineer ? "text-[11px]" : "text-xs"),
                        (c.key === "entryDate" || c.key === "frnNo" || c.key === "defGirNo") && cn("text-slate-500 whitespace-nowrap", isEngineer ? "text-[11px]" : "text-xs"),
                        c.key === "custName" && cn("font-medium text-slate-800 max-w-[140px] truncate", isEngineer ? "text-[11px]" : ""),
                        c.key === "productModel" && cn("text-slate-600 whitespace-nowrap max-w-[120px] truncate", isEngineer ? "text-[11px]" : ""),
                        c.key === "defModBrdName" && cn("text-slate-500 max-w-[120px] truncate", isEngineer ? "text-[11px]" : "text-xs"),
                        (c.key === "dcNo" || c.key === "dispAdvNo" || c.key === "repairedBrdAdvNo" || c.key === "comrclDtlInvRmrk") && "text-xs text-slate-500 whitespace-nowrap",
                        (c.key === "region" || c.key === "scEngineerId" || c.key === "raEngineerId") && cn("text-slate-700 whitespace-nowrap", isEngineer ? "text-[11px]" : "")
                      )}
                    >
                      {renderServiceCell(row, c, dd2Options, dd5Options, employeeMap, modelMap, isEngineer)}
                    </td>
                  ))}
                  <td className={tdPad}>
                    <div className="flex items-center gap-1">
                      <Link
                        href={`/dashboard/services/${row.id}/jobsheet`}
                        className={cn(
                          "rounded-lg transition",
                          isEngineer
                            ? "inline-flex items-center gap-0.5 px-1.5 py-0.5 text-[10px] font-semibold leading-tight text-teal-700 bg-teal-50 hover:bg-teal-100 border border-teal-200/80"
                            : "p-1.5 hover:bg-violet-100 text-slate-400 hover:text-violet-600"
                        )}
                        title="Job sheet"
                      >
                        <ClipboardList className={cn("shrink-0", isEngineer ? "w-3 h-3" : "w-3.5 h-3.5")} />
                        {isEngineer && <span>Job sheet</span>}
                      </Link>
                      {!isEngineer && (
                        <>
                          <Link href={`/dashboard/services/${row.id}/edit`}
                            className="p-1.5 rounded-lg hover:bg-blue-100 text-slate-400 hover:text-blue-600 transition" title="Edit">
                            <Edit className="w-3.5 h-3.5" />
                          </Link>
                          <Link href={`/dashboard/services/${row.id}/update`}
                            className="p-1.5 rounded-lg hover:bg-emerald-100 text-slate-400 hover:text-emerald-600 transition" title="Update (Service Form 2)">
                            <FileText className="w-3.5 h-3.5" />
                          </Link>
                          <button type="button" onClick={(e) => { e.preventDefault(); e.stopPropagation(); handleDeleteClick(row); }}
                            className="p-1.5 rounded-lg hover:bg-red-100 text-slate-400 hover:text-red-600 transition" title="Delete">
                            <Trash2 className="w-3.5 h-3.5" />
                          </button>
                        </>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Footer: row range, page size, pagination */}
        {!isLoading && (
          <div className="border-t border-slate-100 px-4 py-3 sm:px-5 bg-slate-50/50">
            <div className="flex flex-col gap-3 lg:flex-row lg:items-center lg:justify-between">
              <div className="flex flex-wrap items-center gap-x-4 gap-y-2">
                <p className="text-sm text-slate-600">
                  {totalElements === 0 ? (
                    <span className="font-medium text-slate-500">No records</span>
                  ) : (
                    <>
                      <span className="text-slate-500">Showing </span>
                      <span className="font-semibold text-slate-800 tabular-nums">{rangeStart}</span>
                      <span className="text-slate-500">–</span>
                      <span className="font-semibold text-slate-800 tabular-nums">{rangeEnd}</span>
                      <span className="text-slate-500"> of </span>
                      <span className="font-semibold text-slate-800 tabular-nums">{totalElements}</span>
                    </>
                  )}
                </p>
                <div className="flex items-center gap-2">
                  <span className="text-xs font-medium text-slate-500 whitespace-nowrap">Rows per page</span>
                  <Select
                    value={String(pageSize)}
                    onValueChange={v => {
                      setPageSize(Number(v));
                      setPage(0);
                    }}
                  >
                    <SelectTrigger size="sm" className="h-9 w-[76px] rounded-lg border-slate-200 bg-white text-slate-700">
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      {PAGE_SIZE_OPTIONS.map(n => (
                        <SelectItem key={n} value={String(n)}>
                          {n}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>
              {totalPages > 1 && (
                <div className="flex flex-wrap items-center gap-2 lg:justify-end">
                  <p className="text-sm text-slate-500 mr-1 w-full sm:w-auto sm:mr-2">
                    Page <span className="font-semibold text-slate-800">{page + 1}</span>
                    <span className="text-slate-400"> / </span>
                    <span className="font-semibold text-slate-800">{totalPages}</span>
                  </p>
                  <Button
                    variant="outline"
                    className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-indigo-600 transition-colors disabled:opacity-50"
                    disabled={page === 0}
                    onClick={() => setPage(p => p - 1)}
                  >
                    Previous
                  </Button>
                  <div className="hidden sm:flex items-center gap-1">
                    {(() => {
                      const getPageNumbers = (current: number, total: number) => {
                        if (total <= 7) return Array.from({ length: total }, (_, i) => i);
                        if (current <= 3) return [0, 1, 2, 3, 4, "...", total - 1];
                        if (current >= total - 4) return [0, "...", total - 5, total - 4, total - 3, total - 2, total - 1];
                        return [0, "...", current - 1, current, current + 1, "...", total - 1];
                      };

                      return getPageNumbers(page, totalPages).map((p, idx) => {
                        if (p === "...") {
                          return <span key={`ellipsis-${idx}`} className="px-2 text-slate-400">...</span>;
                        }
                        const pageNum = p as number;
                        return (
                          <Button
                            key={pageNum}
                            variant={pageNum === page ? "default" : "outline"}
                            className={cn(
                              "h-9 w-9 p-0 rounded-lg shadow-sm font-medium transition-colors",
                              pageNum === page
                                ? "bg-indigo-600 text-white hover:bg-indigo-700 hover:text-white border-transparent"
                                : "bg-white border-slate-200 text-slate-600 hover:bg-slate-50 hover:text-indigo-600"
                            )}
                            onClick={() => setPage(pageNum)}
                          >
                            {pageNum + 1}
                          </Button>
                        );
                      });
                    })()}
                  </div>
                  <Button
                    variant="outline"
                    className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-indigo-600 transition-colors disabled:opacity-50"
                    disabled={page >= totalPages - 1}
                    onClick={() => setPage(p => p + 1)}
                  >
                    Next
                  </Button>
                </div>
              )}
            </div>
          </div>
        )}
      </div>

      {/* Delete Confirmation Dialog */}
      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent className="rounded-2xl sm:max-w-md">
          <AlertDialogHeader>
            <AlertDialogTitle className="text-xl text-slate-800">Delete Service Record?</AlertDialogTitle>
            <AlertDialogDescription className="text-slate-500 font-medium pt-2">
              This action cannot be undone. This will permanently delete the service record{nil(serviceToDelete?.scRefNo) !== "—" ? ` ${nil(serviceToDelete?.scRefNo)}` : ""}.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter className="mt-4 gap-2 sm:gap-0">
            <AlertDialogCancel disabled={deleteLoading} className="rounded-xl border-slate-200">Cancel</AlertDialogCancel>
            <AlertDialogAction
              onClick={(e) => {
                e.preventDefault();
                handleConfirmDelete();
              }}
              disabled={deleteLoading}
              className="bg-rose-600 hover:bg-rose-700 text-white rounded-xl shadow-sm shadow-rose-200"
            >
              {deleteLoading ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : <Trash2 className="h-4 w-4 mr-2" />}
              Delete
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}
