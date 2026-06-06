"use client";

import { useCallback, useEffect, useState } from "react";
import Link from "next/link";
import { Download, Loader2, Phone, Search, X } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { cn } from "@/lib/utils";
import { apiFetch, getApiBaseUrl } from "@/lib/api";
import {
  ServiceService,
  type CallRegisterRecord,
} from "@/services/service-service";
import { DivisionService, type Division } from "@/services/division-service";
import { isVpOperationalPrivileged } from "@/lib/app-role";

const PAGE_SIZE_OPTIONS = [10, 25, 50] as const;

function getPageNumbers(current: number, total: number): (number | "...")[] {
  if (total <= 0) return [];
  if (total <= 7) return Array.from({ length: total }, (_, i) => i);
  if (current <= 3) return [0, 1, 2, 3, 4, "...", total - 1];
  if (current >= total - 4) return [0, "...", total - 5, total - 4, total - 3, total - 2, total - 1];
  return [0, "...", current - 1, current, current + 1, "...", total - 1];
}

function nil(val: unknown): string {
  return val == null || val === "null" || val === "" ? "—" : String(val);
}

export default function CallListAdminPage() {
  const [rows, setRows] = useState<CallRegisterRecord[]>([]);
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [loading, setLoading] = useState(true);
  const [exportLoading, setExportLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [role, setRole] = useState("");

  const [searchInput, setSearchInput] = useState("");
  const [searchDebounced, setSearchDebounced] = useState("");
  const [divisionFilter, setDivisionFilter] = useState("");

  const [exportFrom, setExportFrom] = useState("");
  const [exportTo, setExportTo] = useState("");

  const [divisions, setDivisions] = useState<Division[]>([]);
  const [modelMap, setModelMap] = useState<Record<string, string>>({});

  useEffect(() => {
    apiFetch(`${getApiBaseUrl()}/auth/me`)
      .then(async (r) => {
        if (!r.ok) return null;
        const raw = (await r.text().catch(() => "")).trim();
        if (!raw) return null;
        return JSON.parse(raw) as { role?: string } | null;
      })
      .then((me) => setRole((me?.role || "").toUpperCase()))
      .catch(() => setRole(""));
  }, []);

  useEffect(() => {
    DivisionService.getDivisions()
      .then(setDivisions)
      .catch(console.error);
  }, []);

  useEffect(() => {
    ServiceService.getModels()
      .then((models) => {
        const map: Record<string, string> = {};
        models.forEach((m) => {
          map[String(m.modelId)] = m.modelName;
        });
        setModelMap(map);
      })
      .catch(console.error);
  }, []);

  useEffect(() => {
    const t = window.setTimeout(() => setSearchDebounced(searchInput.trim()), 400);
    return () => window.clearTimeout(t);
  }, [searchInput]);

  useEffect(() => {
    setPage(0);
  }, [searchDebounced, divisionFilter]);

  useEffect(() => {
    setPage(0);
  }, [pageSize]);

  const loadList = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await ServiceService.getPendingCallRegister(page, pageSize, {
        division: divisionFilter || undefined,
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
          ? "Cannot reach the API. Run the Spring Boot backend (proxied to port 8090 from /api), or set NEXT_PUBLIC_API_URL / API_PROXY_TARGET."
          : raw
      );
      setRows([]);
    } finally {
      setLoading(false);
    }
  }, [page, pageSize, searchDebounced, divisionFilter]);

  useEffect(() => {
    loadList();
  }, [loadList]);

  useEffect(() => {
    if (totalPages > 0 && page >= totalPages) {
      setPage(totalPages - 1);
    }
  }, [totalPages, page]);

  const handleExport = async () => {
    setExportLoading(true);
    try {
      const blob = await ServiceService.exportCallRegisterExcel({
        division: divisionFilter || undefined,
        search: searchDebounced || undefined,
        from: exportFrom || undefined,
        to: exportTo || undefined,
      });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `CallRegister_${new Date().toISOString().slice(0, 10)}.xlsx`;
      a.click();
      URL.revokeObjectURL(url);
    } catch (e) {
      alert(e instanceof Error ? e.message : "Export failed");
    } finally {
      setExportLoading(false);
    }
  };

  const allDivisions = isVpOperationalPrivileged(role);
  const hasFilters = searchInput.trim() !== "" || (!!allDivisions && divisionFilter !== "");

  const todayStart = (() => {
    const d = new Date();
    d.setHours(0, 0, 0, 0);
    return d;
  })();
  const tenYearsAgo = (() => {
    const d = new Date();
    d.setFullYear(d.getFullYear() - 10);
    d.setHours(0, 0, 0, 0);
    return d;
  })();
  const exportFromMax = exportTo
    ? new Date(exportTo + "T00:00:00")
    : todayStart;
  const exportToMin = exportFrom
    ? new Date(exportFrom + "T00:00:00")
    : tenYearsAgo;

  return (
    <div className="space-y-5 max-w-[1600px]">
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <Phone className="w-8 h-8 text-blue-600" />
            <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Call register</h1>
          </div>
          <p className="text-slate-400 text-xs mt-1">
            {loading ? (
              <span className="inline-flex items-center gap-1">
                <Loader2 className="w-3 h-3 animate-spin" /> Loading…
              </span>
            ) : (
              <>{totalElements} pending record{totalElements !== 1 ? "s" : ""}</>
            )}
          </p>
        </div>
        <div className="flex flex-wrap items-center gap-2">
          <button
            type="button"
            onClick={handleExport}
            disabled={exportLoading}
            className="inline-flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl text-sm font-semibold text-white shadow-sm hover:opacity-90 transition disabled:opacity-60 whitespace-nowrap"
            style={{ background: "linear-gradient(135deg, #2563eb, #4f46e5)" }}
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
        className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-visible"
        aria-label="Filters and export date range"
      >
        <div className="border-b border-slate-100 bg-slate-50/50 px-4 py-3">
          <h2 className="text-sm font-semibold text-slate-800">Filters &amp; export range</h2>
          <p className="text-xs text-slate-500 mt-1">
            Grid uses division + search. Export optionally narrows by <strong>call date</strong> (from / to) when
            dates parse from stored values.
          </p>
        </div>
        <div className="p-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <div className="space-y-1.5">
            {allDivisions ? (
              <>
                <label htmlFor="call-list-division" className="block text-sm font-medium text-slate-700">
                  Division
                </label>
                <select
                  id="call-list-division"
                  value={divisionFilter}
                  onChange={(e) => setDivisionFilter(e.target.value)}
                  className="w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-300"
                >
                  <option value="">All divisions</option>
                  {divisions.map((d) => (
                    <option key={d.productId} value={(d.division || d.divisionName || "").trim()}>
                      {d.divisionName || d.division}
                    </option>
                  ))}
                </select>
              </>
            ) : (
              <div className="h-full rounded-xl border border-slate-200 bg-slate-50 px-3 py-2 text-sm text-slate-500 flex items-center">
                Division is scoped from your login (legacy engineer behavior).
              </div>
            )}
          </div>
          <div className="space-y-1.5">
            <label htmlFor="call-list-search" className="block text-sm font-medium text-slate-700">
              Search
            </label>
            <div className="flex items-center gap-2 rounded-xl border border-slate-200 bg-white px-3 py-2 shadow-sm focus-within:ring-2 focus-within:ring-blue-500/20 focus-within:border-blue-300">
              <Search className="w-4 h-4 text-slate-400 shrink-0" aria-hidden />
              <input
                id="call-list-search"
                type="search"
                autoComplete="off"
                value={searchInput}
                onChange={(e) => setSearchInput(e.target.value)}
                className="bg-transparent outline-none w-full min-w-0 text-sm text-slate-800 placeholder:text-slate-400"
                placeholder="Engineer, type of call, status…"
              />
            </div>
          </div>
          <DatePicker
            label="Export — from (call date)"
            value={exportFrom}
            onChange={setExportFrom}
            placeholder="Start date"
            minDate={tenYearsAgo}
            maxDate={exportFromMax}
          />
          <DatePicker
            label="Export — to (call date)"
            value={exportTo}
            onChange={setExportTo}
            placeholder="End date"
            minDate={exportToMin}
            maxDate={todayStart}
            alignPopover="end"
          />
        </div>
        {hasFilters && (
          <div className="px-4 pb-4">
            <Button
              type="button"
              variant="outline"
              size="sm"
              className="rounded-lg border-slate-200 text-slate-600 hover:bg-white"
              onClick={() => {
                setSearchInput("");
                setDivisionFilter("");
              }}
            >
              <X className="w-3.5 h-3.5 mr-1.5 opacity-70" />
              Clear list filters
            </Button>
          </div>
        )}
      </section>

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-xs min-w-[1100px] leading-snug [&_td]:align-middle">
            <thead>
              <tr className="border-b border-slate-100 bg-slate-50/60">
                {(
                  [
                    "Division",
                    "Entry Date",
                    "Call Date",
                    "Sc Engg",
                    "Engineer",
                    "Model",
                    "Type of Call",
                    "Remarks",
                    "Status",
                    "Action",
                  ] as const
                ).map((label) => (
                  <th
                    key={label}
                    className="text-left text-[10px] font-semibold tracking-wide text-slate-400 uppercase px-2 py-2 whitespace-nowrap"
                  >
                    {label}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {loading && rows.length === 0 ? (
                <tr>
                  <td colSpan={10} className="text-center py-16 text-slate-400">
                    <Loader2 className="w-6 h-6 animate-spin inline mr-2 align-middle" />
                    Loading…
                  </td>
                </tr>
              ) : rows.length === 0 ? (
                <tr>
                  <td colSpan={10} className="text-center py-14 text-slate-400 text-sm">
                    No pending call records.
                  </td>
                </tr>
              ) : (
                rows.map((row) => (
                  <tr
                    key={row.id}
                    className="border-b border-slate-50 hover:bg-blue-50/20 transition-colors last:border-0"
                  >
                    <td className="px-2 py-1.5 text-slate-700 text-xs">{nil(row.division)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs whitespace-nowrap">{nil(row.entryDate)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs whitespace-nowrap">{nil(row.callDate)}</td>
                    <td className="px-2 py-1.5 text-slate-700 text-xs whitespace-nowrap">
                      {nil(row.scEnggName ?? row.scEngg)}
                    </td>
                    <td className="px-2 py-1.5 text-slate-700 text-xs">{nil(row.engineer)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs max-w-[120px] truncate">
                      {nil(modelMap[row.model ?? ""] ?? row.model)}
                    </td>
                    <td className="px-2 py-1.5 text-slate-600 text-xs max-w-[100px] truncate">
                      {nil(row.typeOfCall)}
                    </td>
                    <td className="px-2 py-1.5 text-slate-500 text-xs max-w-[180px] truncate">{nil(row.remarks)}</td>
                    <td className="px-2 py-1.5">
                      <Badge
                        variant="outline"
                        className="!h-5 min-h-0 px-1.5 py-0 text-[10px] leading-none rounded font-normal bg-amber-50 text-amber-800 border-amber-200"
                      >
                        {nil(row.statusType)}
                      </Badge>
                    </td>
                    <td className="px-2 py-1.5 whitespace-nowrap">
                      {row.id != null && (
                        <Link
                          href={`/dashboard/closed-calls/${row.id}/edit?source=call-list`}
                          className="inline-flex items-center gap-1 text-[11px] font-medium text-violet-600 hover:text-violet-800"
                        >
                          Update
                        </Link>
                      )}
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
