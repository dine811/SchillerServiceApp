"use client";

import { useCallback, useEffect, useState } from "react";
import Link from "next/link";
import { ClipboardList, Download, Loader2, Search, X } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { DeleteRowDialog } from "@/components/admin/delete-row-dialog";
import { ServiceService, type NonsaleableAdminRecord } from "@/services/service-service";
import { DivisionService, type Division } from "@/services/division-service";
import { cn } from "@/lib/utils";

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

export default function NonSalableAdminPage() {
  const [rows, setRows] = useState<NonsaleableAdminRecord[]>([]);
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [loading, setLoading] = useState(true);
  const [exportLoading, setExportLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [deleteId, setDeleteId] = useState<number | null>(null);
  const [pendingDeleteId, setPendingDeleteId] = useState<number | null>(null);

  const [searchInput, setSearchInput] = useState("");
  const [searchDebounced, setSearchDebounced] = useState("");

  const [exportFrom, setExportFrom] = useState("");
  const [exportTo, setExportTo] = useState("");
  const [exportDivision, setExportDivision] = useState("1");
  const [divisions, setDivisions] = useState<Division[]>([]);

  useEffect(() => {
    DivisionService.getDivisions()
      .then(setDivisions)
      .catch(console.error);
  }, []);

  useEffect(() => {
    const t = window.setTimeout(() => setSearchDebounced(searchInput.trim()), 400);
    return () => window.clearTimeout(t);
  }, [searchInput]);

  useEffect(() => {
    setPage(0);
  }, [searchDebounced]);

  useEffect(() => {
    setPage(0);
  }, [pageSize]);

  const loadList = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await ServiceService.getPendingNonsaleableAdmin(page, pageSize, {
        search: searchDebounced,
      });
      setRows(data.content ?? []);
      setTotalElements(data.totalElements ?? 0);
      setTotalPages(data.totalPages ?? 0);
    } catch (e) {
      const raw = e instanceof Error ? e.message : "Failed to load list";
      const network =
        raw === "Failed to fetch" || raw.startsWith("NetworkError") || raw === "Load failed";
      setError(
        network
          ? "Cannot reach the API. Run the Spring Boot backend (proxied to port 8090 from /api)."
          : raw
      );
      setRows([]);
    } finally {
      setLoading(false);
    }
  }, [page, pageSize, searchDebounced]);

  useEffect(() => {
    loadList();
  }, [loadList]);

  useEffect(() => {
    if (totalPages > 0 && page >= totalPages) {
      setPage(totalPages - 1);
    }
  }, [totalPages, page]);

  const handleExport = async () => {
    if (!exportFrom.trim() || !exportTo.trim()) {
      alert("Choose both From and To dates for export.");
      return;
    }
    setExportLoading(true);
    try {
      const blob = await ServiceService.exportNonsaleableExcel({
        status: "Pending",
        from: exportFrom.trim(),
        to: exportTo.trim(),
        division: exportDivision.trim() || "1",
      });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `Non-Salable_${new Date().toISOString().slice(0, 10)}.xlsx`;
      a.click();
      URL.revokeObjectURL(url);
    } catch (e) {
      alert(e instanceof Error ? e.message : "Export failed");
    } finally {
      setExportLoading(false);
    }
  };

  const confirmDelete = async () => {
    if (pendingDeleteId == null) return;
    const id = pendingDeleteId;
    setDeleteId(id);
    try {
      await ServiceService.deleteNonsaleable(id);
      await loadList();
    } catch (e) {
      alert(e instanceof Error ? e.message : "Delete failed");
    } finally {
      setDeleteId(null);
      setPendingDeleteId(null);
    }
  };

  const hasFilters = searchInput.trim() !== "";
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
  const exportFromMax = exportTo ? new Date(exportTo + "T00:00:00") : todayStart;
  const exportToMin = exportFrom ? new Date(exportFrom + "T00:00:00") : tenYearsAgo;

  return (
    <div className="space-y-4 max-w-[1800px]">
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-3">
        <div>
          <div className="flex flex-wrap items-center gap-x-2 gap-y-1 mb-0.5">
            <ClipboardList className="w-6 h-6 text-amber-600 shrink-0" />
            <h1 className="text-xl font-bold text-slate-900 tracking-tight">
              Non salable list
              <span className="ml-2 inline-flex items-center rounded-full border border-amber-200 bg-amber-50 px-1.5 py-0 text-[10px] font-semibold text-amber-900 align-middle">
                Pending
              </span>
            </h1>
          </div>
          <p className="text-slate-400 text-xs mt-0.5">
            {loading ? (
              <span className="inline-flex items-center gap-1">
                <Loader2 className="w-3 h-3 animate-spin" /> Loading…
              </span>
            ) : (
              <>
                {totalElements} pending record{totalElements !== 1 ? "s" : ""}
                {" · "}
                <Link href="/dashboard/salables" className="text-amber-800 font-medium underline hover:text-amber-950">
                  Salables (completed)
                </Link>
              </>
            )}
          </p>
        </div>
        <div className="flex flex-wrap items-center gap-2">
          <button
            type="button"
            onClick={handleExport}
            disabled={exportLoading}
            className="inline-flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl text-sm font-semibold text-white shadow-sm hover:opacity-90 transition disabled:opacity-60 whitespace-nowrap"
            style={{ background: "linear-gradient(135deg, #d97706, #b45309)" }}
          >
            {exportLoading ? (
              <Loader2 className="w-4 h-4 shrink-0 animate-spin" aria-hidden />
            ) : (
              <Download className="w-4 h-4 shrink-0" aria-hidden />
            )}
            Export
          </button>
        </div>
      </div>

      <section className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="border-b border-slate-100 px-4 py-3 bg-slate-50/50">
          <h2 className="text-sm font-semibold text-slate-800">Export (legacy Non-Salable)</h2>
          <p className="text-xs text-slate-500 mt-0.5">Filters by entry_date, status Pending, and optional division.</p>
        </div>
        <div className="p-3 flex flex-wrap gap-3 items-end">
          <div className="min-w-[136px]">
            <DatePicker
              value={exportFrom}
              onChange={setExportFrom}
              placeholder="Start date"
              maxDate={exportFromMax}
              label="From"
              variant="compact"
            />
          </div>
          <div className="min-w-[136px]">
            <DatePicker
              value={exportTo}
              onChange={setExportTo}
              placeholder="End date"
              minDate={exportToMin}
              maxDate={todayStart}
              label="To"
              variant="compact"
            />
          </div>
          <div className="min-w-[170px]">
            <label className="block text-xs font-semibold text-slate-600 mb-1">Division</label>
            <select
              value={exportDivision}
              onChange={(e) => setExportDivision(e.target.value)}
              className="w-full h-9 rounded-lg border border-slate-200 bg-white px-2.5 py-1.5 text-sm text-slate-800"
            >
              <option value="1">All divisions</option>
              {divisions.map((d) => (
                <option key={d.productId} value={d.divisionName}>
                  {d.divisionName}
                </option>
              ))}
            </select>
          </div>
        </div>
        {hasFilters && (
          <div className="px-4 pb-4">
            <Button
              type="button"
              variant="outline"
              size="sm"
              className="rounded-lg border-slate-200 text-slate-600 hover:bg-white"
              onClick={() => setSearchInput("")}
            >
              <X className="w-3.5 h-3.5 mr-1.5 opacity-70" />
              Clear search
            </Button>
          </div>
        )}
      </section>

      <section className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="border-b border-slate-100 px-4 py-3 flex flex-col sm:flex-row sm:items-center gap-3">
          <div className="relative flex-1 max-w-md">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400 pointer-events-none" />
            <input
              type="search"
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              placeholder="Search model S/N, status, unit details…"
              className="w-full pl-9 pr-3 py-2 rounded-xl border border-slate-200 text-sm text-slate-800 bg-slate-50/60 focus:outline-none focus:ring-2 focus:ring-amber-200 focus:border-amber-400"
            />
          </div>
        </div>
      </section>

      {error && (
        <div className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800" role="alert">
          {error}
        </div>
      )}

      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="border-b border-slate-100 bg-slate-50/50 px-4 py-3">
          <h2 className="text-sm font-semibold text-slate-800">Non salable details</h2>
          <p className="text-xs text-slate-500 mt-0.5">Legacy nonSaleAdminList.jsp — Pending only.</p>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-sm min-w-[1200px] leading-snug [&_td]:align-middle">
            <thead>
              <tr className="border-b border-slate-100 bg-slate-50/60">
                {(
                  [
                    "Entry",
                    "Unit",
                    "FQC in",
                    "Model",
                    "FQC remarks",
                    "SC in",
                    "SC obs.",
                    "Model S/N",
                    "Tentative",
                    "Ship FQC",
                    "Status",
                    "Action",
                  ] as const
                ).map((label, idx, arr) => (
                  <th
                    key={label}
                    className={cn(
                      "text-left text-[10px] font-semibold tracking-wide text-slate-400 uppercase px-2 py-2 whitespace-nowrap",
                      idx === arr.length - 1 &&
                        "sticky right-0 z-20 min-w-[6rem] border-l border-slate-200 bg-slate-50 shadow-[-8px_0_12px_-6px_rgba(15,23,42,0.12)]"
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
                  <td colSpan={12} className="text-center py-16 text-slate-400">
                    <Loader2 className="w-6 h-6 animate-spin inline mr-2 align-middle" />
                    Loading…
                  </td>
                </tr>
              ) : rows.length === 0 ? (
                <tr>
                  <td colSpan={12} className="text-center py-14 text-slate-400 text-sm">
                    No pending non-salable records.
                  </td>
                </tr>
              ) : (
                rows.map((row) => (
                  <tr
                    key={row.id}
                    className="group border-b border-slate-50 hover:bg-amber-50/20 transition-colors last:border-0"
                  >
                    <td className="px-2 py-1.5 text-slate-600 text-sm whitespace-nowrap">{nil(row.entryDate)}</td>
                    <td className="px-2 py-1.5 text-slate-700 text-sm max-w-[100px] truncate">{nil(row.unitDetails)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm whitespace-nowrap">{nil(row.fqcInDate)}</td>
                    <td className="px-2 py-1.5 text-slate-700 text-sm max-w-[120px] truncate">{nil(row.modelName ?? row.model)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm max-w-[140px] truncate">{nil(row.fqcObservation)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm whitespace-nowrap">{nil(row.scInwardDate)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm max-w-[120px] truncate">{nil(row.scObservation)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm font-mono">{nil(row.modelSn)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm whitespace-nowrap">{nil(row.tentDateShipDate)}</td>
                    <td className="px-2 py-1.5 text-slate-600 text-sm whitespace-nowrap">{nil(row.shipDateFqc)}</td>
                    <td className="px-2 py-1.5">
                      <Badge
                        variant="outline"
                        className="!h-5 min-h-0 px-1.5 py-0 text-[10px] leading-none rounded font-normal bg-amber-50 text-amber-900 border-amber-200"
                      >
                        {nil(row.finalStatus)}
                      </Badge>
                    </td>
                    <td
                      className={cn(
                        "px-2 py-1.5 whitespace-nowrap sticky right-0 z-10 min-w-[6rem] border-l border-slate-100 bg-white shadow-[-8px_0_12px_-6px_rgba(15,23,42,0.08)]",
                        "group-hover:bg-amber-50/90"
                      )}
                    >
                      {row.id != null && (
                        <button
                          type="button"
                          onClick={() => setPendingDeleteId(row.id!)}
                          disabled={deleteId === row.id}
                          className="font-medium text-red-600 hover:text-red-800 disabled:opacity-50 text-[11px]"
                        >
                          {deleteId === row.id ? <Loader2 className="w-3 h-3 animate-spin inline" /> : "Delete"}
                        </button>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {!loading && totalElements > 0 && totalPages > 0 && (
          <div className="border-t border-slate-100 p-4 flex flex-col lg:flex-row items-stretch lg:items-center justify-between gap-4 bg-slate-50/50">
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
                <span className="text-slate-400 uppercase tracking-wide font-medium">Rows</span>
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
              {getPageNumbers(page, totalPages).map((p, i) =>
                p === "..." ? (
                  <span key={`e-${i}`} className="px-2 text-slate-400">
                    …
                  </span>
                ) : (
                  <Button
                    key={p}
                    type="button"
                    variant={page === p ? "default" : "outline"}
                    size="sm"
                    className="h-8 min-w-8 px-2"
                    onClick={() => setPage(p as number)}
                  >
                    {(p as number) + 1}
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

      <DeleteRowDialog
        open={pendingDeleteId != null}
        onOpenChange={(o) => !o && setPendingDeleteId(null)}
        onConfirm={confirmDelete}
        loading={deleteId != null}
        title="Delete non-salable row?"
        description="This removes the row from nonsaleablemaster. This cannot be undone."
      />
    </div>
  );
}
