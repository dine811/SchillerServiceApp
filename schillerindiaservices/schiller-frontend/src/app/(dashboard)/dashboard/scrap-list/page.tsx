"use client";

import { useCallback, useEffect, useState } from "react";
import { Download, Loader2, Search, Trash2, X } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { apiFetch, getApiBaseUrl } from "@/lib/api";
import { cn } from "@/lib/utils";
import {
  ServiceService,
  type DropdownOption,
  type ServiceRecord,
} from "@/services/service-service";
import { DivisionService, type Division } from "@/services/division-service";

const PAGE_SIZE_OPTIONS = [10, 25, 50] as const;

const MONTHS = [
  { v: 1, label: "January" },
  { v: 2, label: "February" },
  { v: 3, label: "March" },
  { v: 4, label: "April" },
  { v: 5, label: "May" },
  { v: 6, label: "June" },
  { v: 7, label: "July" },
  { v: 8, label: "August" },
  { v: 9, label: "September" },
  { v: 10, label: "October" },
  { v: 11, label: "November" },
  { v: 12, label: "December" },
] as const;

const YEAR_OPTIONS = Array.from({ length: 2030 - 2010 + 1 }, (_, i) => 2010 + i);

function getPageNumbers(current: number, total: number): (number | "...")[] {
  if (total <= 0) return [];
  if (total <= 7) return Array.from({ length: total }, (_, i) => i);
  if (current <= 3) return [0, 1, 2, 3, 4, "...", total - 1];
  if (current >= total - 4) return [0, "...", total - 5, total - 4, total - 3, total - 2, total - 1];
  return [0, "...", current - 1, current, current + 1, "...", total - 1];
}

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

function idToLabel(opts: DropdownOption[], id: string | undefined): string {
  if (!id) return "—";
  const o = opts.find((d) => String(d.id) === String(id));
  return o?.ddValue ?? id;
}

function nil(val: unknown): string {
  return val == null || val === "null" || val === "" ? "—" : String(val);
}

interface Emp {
  empId: number;
  empName: string;
}

export default function ScrapListPage() {
  const [rows, setRows] = useState<ServiceRecord[]>([]);
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [loading, setLoading] = useState(true);
  const [exportLoading, setExportLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [scRefInput, setScRefInput] = useState("");
  const [searchInput, setSearchInput] = useState("");
  const [scRefDebounced, setScRefDebounced] = useState("");
  const [searchDebounced, setSearchDebounced] = useState("");

  const [divisionFilter, setDivisionFilter] = useState("");
  const [monthFilter, setMonthFilter] = useState<number | "">("");
  const [yearFilter, setYearFilter] = useState<number | "">("");

  const [divisions, setDivisions] = useState<Division[]>([]);
  const [dd2Options, setDd2Options] = useState<DropdownOption[]>([]);
  const [dd5Options, setDd5Options] = useState<DropdownOption[]>([]);
  const [employeeMap, setEmployeeMap] = useState<Record<number, string>>({});
  const [modelMap, setModelMap] = useState<Record<string, string>>({});

  useEffect(() => {
    DivisionService.getDivisions()
      .then(setDivisions)
      .catch(console.error);
  }, []);

  useEffect(() => {
    Promise.all([
      ServiceService.getDropdownOptions(2).then(setDd2Options),
      ServiceService.getDropdownOptions(5).then(setDd5Options),
      apiFetch(`${getApiBaseUrl()}/employees?page=0&size=500`).then((r) => r.json()),
      ServiceService.getModels().then((models) => {
        const map: Record<string, string> = {};
        models.forEach((m) => {
          map[String(m.modelId)] = m.modelName;
        });
        return map;
      }),
    ])
      .then(([, , empRes, modelMapRes]) => {
        const list: Emp[] = empRes?.content ?? empRes ?? [];
        const empMap: Record<number, string> = {};
        list.forEach((e) => {
          empMap[e.empId] = e.empName;
        });
        setEmployeeMap(empMap);
        setModelMap(modelMapRes);
      })
      .catch(console.error);
  }, []);

  useEffect(() => {
    const t = window.setTimeout(() => setScRefDebounced(scRefInput.trim()), 400);
    return () => window.clearTimeout(t);
  }, [scRefInput]);

  useEffect(() => {
    const t = window.setTimeout(() => setSearchDebounced(searchInput.trim()), 400);
    return () => window.clearTimeout(t);
  }, [searchInput]);

  useEffect(() => {
    setPage(0);
  }, [scRefDebounced, searchDebounced, divisionFilter, monthFilter, yearFilter]);

  useEffect(() => {
    setPage(0);
  }, [pageSize]);

  const loadScrapList = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await ServiceService.getScrapList(page, pageSize, {
        division: divisionFilter || undefined,
        month: monthFilter === "" ? undefined : monthFilter,
        year: yearFilter === "" ? undefined : yearFilter,
        scRef: scRefDebounced,
        search: searchDebounced,
      });
      setRows(data.content ?? []);
      setTotalElements(data.totalElements ?? 0);
      setTotalPages(data.totalPages ?? 0);
    } catch (e) {
      const raw = e instanceof Error ? e.message : "Failed to load list";
      const network =
        raw === "Failed to fetch" ||
        raw.startsWith("NetworkError") ||
        raw === "Load failed";
      setError(
        network
          ? "Cannot reach the API. Start PostgreSQL and the Spring Boot backend (default proxy to port 8090). Set NEXT_PUBLIC_API_URL or API_PROXY_TARGET in .env.local if needed, then restart Next.js."
          : raw
      );
      setRows([]);
    } finally {
      setLoading(false);
    }
  }, [page, pageSize, scRefDebounced, searchDebounced, divisionFilter, monthFilter, yearFilter]);

  useEffect(() => {
    loadScrapList();
  }, [loadScrapList]);

  useEffect(() => {
    if (totalPages > 0 && page >= totalPages) {
      setPage(totalPages - 1);
    }
  }, [totalPages, page]);

  const handleExport = async () => {
    setExportLoading(true);
    try {
      const blob = await ServiceService.exportScrapListExcel({
        division: divisionFilter || undefined,
        month: monthFilter === "" ? undefined : monthFilter,
        year: yearFilter === "" ? undefined : yearFilter,
        scRef: scRefDebounced || undefined,
        search: searchDebounced || undefined,
      });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `ScrapList_${new Date().toISOString().slice(0, 10)}.xlsx`;
      a.click();
      URL.revokeObjectURL(url);
    } catch (e) {
      alert(e instanceof Error ? e.message : "Export failed");
    } finally {
      setExportLoading(false);
    }
  };

  const hasActiveFilters =
    scRefInput.trim() !== "" ||
    searchInput.trim() !== "" ||
    divisionFilter !== "" ||
    monthFilter !== "" ||
    yearFilter !== "";

  return (
    <div className="space-y-5 max-w-[1800px]">
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <Trash2 className="w-8 h-8 text-stone-600" />
            <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Scrap list</h1>
          </div>
          <p className="text-slate-400 text-xs mt-1">
            {loading ? (
              <span className="inline-flex items-center gap-1">
                <Loader2 className="w-3 h-3 animate-spin" /> Loading…
              </span>
            ) : (
              <>{totalElements} record{totalElements !== 1 ? "s" : ""} (SCRAPPED)</>
            )}
          </p>
        </div>
        <div className="flex flex-wrap items-center gap-2">
          <button
            type="button"
            onClick={handleExport}
            disabled={exportLoading}
            className="inline-flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl text-sm font-semibold text-white shadow-sm hover:opacity-90 transition disabled:opacity-60 whitespace-nowrap"
            style={{ background: "linear-gradient(135deg, #57534e, #44403c)" }}
          >
            {exportLoading ? (
              <Loader2 className="w-4 h-4 shrink-0 animate-spin" aria-hidden />
            ) : (
              <Download className="w-4 h-4 shrink-0" aria-hidden />
            )}
            <span className="leading-none">Export Excel</span>
          </button>
        </div>
      </div>

      {error && (
        <div className="rounded-xl border border-red-200 bg-red-50 text-red-800 px-4 py-3 text-sm">{error}</div>
      )}

      <section
        className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden"
        role="search"
        aria-label="Filter scrap list"
      >
        <div className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between sm:gap-4 border-b border-slate-100 bg-slate-50/50 px-4 py-3">
          <h2 className="text-sm font-semibold text-slate-800">Filters</h2>
          {hasActiveFilters && (
            <Button
              type="button"
              variant="outline"
              size="sm"
              className="shrink-0 rounded-lg border-slate-200 text-slate-600 hover:bg-white"
              onClick={() => {
                setScRefInput("");
                setSearchInput("");
                setDivisionFilter("");
                setMonthFilter("");
                setYearFilter("");
              }}
            >
              <X className="w-3.5 h-3.5 mr-1.5 opacity-70" />
              Clear filters
            </Button>
          )}
        </div>
        <div className="p-4 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-4">
          <div className="space-y-1.5">
            <label htmlFor="scrap-list-division" className="block text-sm font-medium text-slate-700">
              Division
            </label>
            <select
              id="scrap-list-division"
              value={divisionFilter}
              onChange={(e) => setDivisionFilter(e.target.value)}
              className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm focus:outline-none focus:ring-2 focus:ring-stone-500/20 focus:border-stone-300"
            >
              <option value="">All divisions</option>
              {divisions.map((d) => (
                <option key={d.productId} value={(d.division || d.divisionName || "").trim()}>
                  {d.divisionName || d.division}
                </option>
              ))}
            </select>
          </div>
          <div className="space-y-1.5">
            <label htmlFor="scrap-list-month" className="block text-sm font-medium text-slate-700">
              Entry month
            </label>
            <select
              id="scrap-list-month"
              value={monthFilter === "" ? "" : String(monthFilter)}
              onChange={(e) => {
                const v = e.target.value;
                setMonthFilter(v === "" ? "" : Number(v));
              }}
              className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm focus:outline-none focus:ring-2 focus:ring-stone-500/20 focus:border-stone-300"
            >
              <option value="">Any month</option>
              {MONTHS.map((m) => (
                <option key={m.v} value={m.v}>
                  {m.label}
                </option>
              ))}
            </select>
          </div>
          <div className="space-y-1.5">
            <label htmlFor="scrap-list-year" className="block text-sm font-medium text-slate-700">
              Entry year
            </label>
            <select
              id="scrap-list-year"
              value={yearFilter === "" ? "" : String(yearFilter)}
              onChange={(e) => {
                const v = e.target.value;
                setYearFilter(v === "" ? "" : Number(v));
              }}
              className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm focus:outline-none focus:ring-2 focus:ring-stone-500/20 focus:border-stone-300"
            >
              <option value="">Any year</option>
              {YEAR_OPTIONS.map((y) => (
                <option key={y} value={y}>
                  {y}
                </option>
              ))}
            </select>
          </div>
          <div className="space-y-1.5">
            <label htmlFor="scrap-list-sc-ref" className="block text-sm font-medium text-slate-700">
              Sc RNo
            </label>
            <div className="flex items-center gap-2 rounded-xl border border-slate-200 bg-white px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-stone-500/20 focus-within:border-stone-300">
              <Search className="w-4 h-4 text-slate-400 shrink-0" aria-hidden />
              <input
                id="scrap-list-sc-ref"
                name="scRef"
                type="search"
                autoComplete="off"
                value={scRefInput}
                onChange={(e) => setScRefInput(e.target.value)}
                className="bg-transparent outline-none w-full min-w-0 text-sm text-slate-800 placeholder:text-slate-400"
                placeholder="Match anywhere in Sc RNo…"
              />
            </div>
          </div>
          <div className="space-y-1.5">
            <label htmlFor="scrap-list-frn-gir" className="block text-sm font-medium text-slate-700">
              Frn No / Def Gir No
            </label>
            <div className="flex items-center gap-2 rounded-xl border border-slate-200 bg-white px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-stone-500/20 focus-within:border-stone-300">
              <Search className="w-4 h-4 text-slate-400 shrink-0" aria-hidden />
              <input
                id="scrap-list-frn-gir"
                name="frnDefGir"
                type="search"
                autoComplete="off"
                value={searchInput}
                onChange={(e) => setSearchInput(e.target.value)}
                className="bg-transparent outline-none w-full min-w-0 text-sm text-slate-800 placeholder:text-slate-400"
                placeholder="Contains text in FRN or Def GIR…"
              />
            </div>
          </div>
        </div>
      </section>

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-xs min-w-[1500px] leading-snug [&_td]:align-middle">
            <thead>
              <tr className="border-b border-slate-100 bg-slate-50/60">
                {(
                  [
                    "Id",
                    "Status",
                    "Entry Date",
                    "Sc RNo",
                    "Sc Eng",
                    "Frn No",
                    "Region",
                    "Eng",
                    "Cust Name",
                    "Model",
                    "Unit Status",
                    "Def Mod / brd",
                    "Def Gir No",
                    "Type of work",
                    "Pend. (PFRN)",
                    "Pend. (OBP)",
                    "Pend. (URP)",
                    "Pend. (SCC)",
                  ] as const
                ).map((label) => (
                  <th
                    key={label}
                    className={cn(
                      "text-left text-[10px] font-semibold tracking-wide text-slate-400 uppercase px-2 py-2 whitespace-nowrap",
                      label.startsWith("Pend.") && "text-right"
                    )}
                  >
                    {label}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {loading && rows.length === 0 ? (
                <tr>
                  <td colSpan={18} className="text-center py-16 text-slate-400">
                    <Loader2 className="w-6 h-6 animate-spin inline mr-2 align-middle" />
                    Loading…
                  </td>
                </tr>
              ) : rows.length === 0 ? (
                <tr>
                  <td colSpan={18} className="text-center py-14 text-slate-400 text-sm">
                    No scrapped records match the filters.
                  </td>
                </tr>
              ) : (
                rows.map((row) => (
                  <tr
                    key={row.id}
                    className="border-b border-slate-50 hover:bg-stone-50/40 transition-colors last:border-0"
                  >
                    <td className="px-2 py-1.5 font-mono text-xs text-slate-600">{nil(row.id)}</td>
                    <td className="px-2 py-1.5 text-slate-700 text-xs max-w-[100px] truncate">{nil(row.status)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs whitespace-nowrap">{nil(row.entryDate)}</td>
                    <td className="px-2 py-1.5 font-mono text-xs font-semibold text-stone-700 whitespace-nowrap">
                      {nil(row.scRefNo)}
                    </td>
                    <td className="px-2 py-1.5 text-slate-700 text-xs whitespace-nowrap">
                      {nil(
                        row.scEngineerName ??
                          (row.scEngineerId != null ? employeeMap[row.scEngineerId] : null) ??
                          row.scEngineerId
                      )}
                    </td>
                    <td className="px-2 py-1.5 font-mono text-xs text-slate-600 whitespace-nowrap">
                      {nil(row.frnNo)}
                    </td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs">{nil(row.region)}</td>
                    <td className="px-2 py-1.5 text-slate-700 text-xs whitespace-nowrap">
                      {nil(
                        row.raEngineerName ??
                          (row.raEngineerId != null ? employeeMap[row.raEngineerId] : null) ??
                          row.raEngineerId
                      )}
                    </td>
                    <td className="px-2 py-1.5 font-medium text-slate-800 max-w-[120px] truncate">
                      {nil(row.custName)}
                    </td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs max-w-[100px] truncate">
                      {nil(modelMap[row.productModel ?? ""] ?? row.productModel)}
                    </td>
                    <td className="px-2 py-1.5">
                      {(() => {
                        const unitStatusLabel = idToLabel(dd2Options, row.unitStatus);
                        return (
                          <Badge
                            variant="outline"
                            className={`!h-5 min-h-0 px-1.5 py-0 text-[10px] leading-none rounded font-normal ${
                              unitStatusLabel !== "—"
                                ? STATUS_COLORS[unitStatusLabel] || "bg-slate-100 text-slate-600"
                                : "bg-slate-100 text-slate-600"
                            }`}
                          >
                            {unitStatusLabel}
                          </Badge>
                        );
                      })()}
                    </td>
                    <td className="px-2 py-1.5 text-slate-500 text-xs max-w-[100px] truncate">
                      {nil(row.defModBrdName)}
                    </td>
                    <td className="px-2 py-1.5 font-mono text-xs text-slate-600 whitespace-nowrap">
                      {nil(row.defGirNo)}
                    </td>
                    <td className="px-2 py-1.5">
                      {(() => {
                        const typeOfWorkLabel = idToLabel(dd5Options, row.typeOfWork);
                        return typeOfWorkLabel !== "—" ? (
                          <Badge
                            variant="outline"
                            className={`!h-5 min-h-0 max-w-[140px] truncate px-1.5 py-0 text-[10px] leading-none rounded font-normal ${
                              WORK_TYPE_COLORS[typeOfWorkLabel] || "bg-slate-100 text-slate-600"
                            }`}
                          >
                            {typeOfWorkLabel}
                          </Badge>
                        ) : (
                          <span className="text-slate-400">—</span>
                        );
                      })()}
                    </td>
                    <td className="px-2 py-1.5 text-right tabular-nums text-slate-700 text-xs">
                      {nil(row.pendDaysPfrn)}
                    </td>
                    <td className="px-2 py-1.5 text-right tabular-nums text-slate-700 text-xs">
                      {nil(row.pendDaysObp)}
                    </td>
                    <td className="px-2 py-1.5 text-right tabular-nums text-slate-700 text-xs">
                      {nil(row.pendDaysUrp)}
                    </td>
                    <td className="px-2 py-1.5 text-right tabular-nums text-slate-700 text-xs">
                      {nil(row.pendDaysScc)}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {!loading && totalElements > 0 && totalPages > 0 && (
          <div className="border-t border-slate-100 p-4 sm:p-5 flex flex-col lg:flex-row items-stretch lg:items-center justify-between gap-4 bg-slate-50/50">
            <div className="flex flex-wrap items-center gap-x-4 gap-y-2 text-sm text-slate-500">
              <p>
                <span className="text-slate-400">Showing </span>
                <span className="font-semibold text-slate-800">{page * pageSize + 1}</span>
                <span className="text-slate-400"> – </span>
                <span className="font-semibold text-slate-800">
                  {Math.min((page + 1) * pageSize, totalElements)}
                </span>
                <span className="text-slate-400"> of </span>
                <span className="font-semibold text-slate-800">{totalElements}</span>
              </p>
              <label className="inline-flex items-center gap-2 text-xs">
                <span className="text-slate-400 uppercase tracking-wide font-medium">Rows per page</span>
                <select
                  value={pageSize}
                  onChange={(e) => setPageSize(Number(e.target.value))}
                  className="rounded-lg border border-slate-200 bg-white px-2 py-1 text-sm text-slate-800"
                >
                  {PAGE_SIZE_OPTIONS.map((n) => (
                    <option key={n} value={n}>
                      {n}
                    </option>
                  ))}
                </select>
              </label>
            </div>
            <div className="flex flex-wrap items-center justify-center gap-1">
              <Button
                type="button"
                variant="outline"
                size="sm"
                className="h-8 px-2"
                disabled={page <= 0}
                onClick={() => setPage((p) => Math.max(0, p - 1))}
              >
                Prev
              </Button>
              {getPageNumbers(page, totalPages).map((item, idx) =>
                item === "..." ? (
                  <span key={`e-${idx}`} className="px-2 text-slate-400">
                    …
                  </span>
                ) : (
                  <Button
                    key={item}
                    type="button"
                    variant={page === item ? "default" : "outline"}
                    size="sm"
                    className="h-8 min-w-[2rem] px-2"
                    onClick={() => setPage(item as number)}
                  >
                    {(item as number) + 1}
                  </Button>
                )
              )}
              <Button
                type="button"
                variant="outline"
                size="sm"
                className="h-8 px-2"
                disabled={page >= totalPages - 1}
                onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
              >
                Next
              </Button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
