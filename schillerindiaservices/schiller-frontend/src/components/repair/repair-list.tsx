"use client";

import Link from "next/link";
import { useCallback, useEffect, useState } from "react";
import { Download, Loader2, Search, Wrench, X } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { DivisionService, type Division } from "@/services/division-service";
import {
  RepairService,
  type RepairCategory,
  type RepairRecord,
  type RepairStatus,
} from "@/services/repair-service";

const PAGE_SIZES = [10, 25, 50, 100] as const;

interface RepairListProps {
  title: string;
  category?: RepairCategory;
  status: RepairStatus;
}

function display(value: unknown): string {
  return value == null || value === "" || value === "null" ? "—" : String(value);
}

function pageNumbers(current: number, total: number): (number | "…")[] {
  if (total <= 7) return Array.from({ length: total }, (_, index) => index);
  if (current <= 3) return [0, 1, 2, 3, 4, "…", total - 1];
  if (current >= total - 4) return [0, "…", total - 5, total - 4, total - 3, total - 2, total - 1];
  return [0, "…", current - 1, current, current + 1, "…", total - 1];
}

export function RepairList({ title, category, status }: RepairListProps) {
  const [rows, setRows] = useState<RepairRecord[]>([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [exporting, setExporting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searchInput, setSearchInput] = useState("");
  const [search, setSearch] = useState("");
  const [division, setDivision] = useState("");
  const [scRefNo, setScRefNo] = useState("");
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [sort, setSort] = useState("id,desc");
  const [divisions, setDivisions] = useState<Division[]>([]);

  useEffect(() => {
    DivisionService.getDivisions().then(setDivisions).catch(() => setDivisions([]));
  }, []);

  useEffect(() => {
    const timer = window.setTimeout(() => setSearch(searchInput.trim()), 350);
    return () => window.clearTimeout(timer);
  }, [searchInput]);

  useEffect(() => setPage(0), [search, division, scRefNo, from, to, sort, size]);

  const invalidRange = Boolean(from && to && from > to);
  const load = useCallback(async () => {
    if (invalidRange) return;
    setLoading(true);
    setError(null);
    try {
      const result = await RepairService.list({
        category,
        status,
        page,
        size,
        sort,
        search: search || undefined,
        division: division || undefined,
        scRefNo: scRefNo.trim() || undefined,
        from: from || undefined,
        to: to || undefined,
      });
      setRows(result.content ?? []);
      setTotalElements(result.totalElements ?? 0);
      setTotalPages(result.totalPages ?? 0);
    } catch (caught) {
      setRows([]);
      setTotalElements(0);
      setTotalPages(0);
      setError(caught instanceof Error ? caught.message : "Failed to load repairs");
    } finally {
      setLoading(false);
    }
  }, [category, division, from, invalidRange, page, scRefNo, search, size, sort, status, to]);

  useEffect(() => { void load(); }, [load]);

  const clearFilters = () => {
    setSearchInput("");
    setSearch("");
    setDivision("");
    setScRefNo("");
    setFrom("");
    setTo("");
    setSort("id,desc");
  };

  const exportRows = async () => {
    if (invalidRange) return;
    setExporting(true);
    try {
      const blob = await RepairService.export({
        category,
        status,
        search: search || undefined,
        division: division || undefined,
        scRefNo: scRefNo.trim() || undefined,
        from: from || undefined,
        to: to || undefined,
      });
      const url = URL.createObjectURL(blob);
      const anchor = document.createElement("a");
      anchor.href = url;
      anchor.download = `repairs-${category?.toLowerCase() ?? "completed"}-${new Date().toISOString().slice(0, 10)}.xlsx`;
      anchor.click();
      URL.revokeObjectURL(url);
    } catch (caught) {
      setError(caught instanceof Error ? caught.message : "Failed to export repairs");
    } finally {
      setExporting(false);
    }
  };

  const hasFilters = Boolean(searchInput || division || scRefNo || from || to || sort !== "id,desc");

  return (
    <div className="space-y-5 max-w-[1800px]">
      <header className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
        <div>
          <div className="flex items-center gap-2">
            <Wrench className="h-8 w-8 text-violet-600" aria-hidden />
            <h1 className="text-2xl font-bold tracking-tight text-slate-900">{title}</h1>
            <Badge variant="outline" className={status === "COMPLETED" ? "border-emerald-200 bg-emerald-50 text-emerald-700" : "border-amber-200 bg-amber-50 text-amber-700"}>
              {status.toLowerCase()}
            </Badge>
          </div>
          <p className="mt-1 text-xs text-slate-500">{loading ? "Loading…" : `${totalElements} repair record${totalElements === 1 ? "" : "s"}`}</p>
        </div>
        <Button onClick={() => void exportRows()} disabled={exporting || invalidRange} className="rounded-xl bg-violet-600 text-white hover:bg-violet-700">
          {exporting ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : <Download className="mr-2 h-4 w-4" />}
          Export Excel
        </Button>
      </header>

      <section className="rounded-2xl border border-slate-100 bg-white p-4 shadow-sm" aria-label="Repair filters">
        <div className="grid grid-cols-1 gap-3 md:grid-cols-2 xl:grid-cols-7">
          <label className="space-y-1 text-xs font-medium text-slate-600 xl:col-span-2">
            Search division, SC reference, or GIR
            <span className="flex items-center gap-2 rounded-xl border border-slate-200 px-3 py-2 focus-within:border-violet-300 focus-within:ring-2 focus-within:ring-violet-100">
              <Search className="h-4 w-4 text-slate-400" />
              <input value={searchInput} onChange={(event) => setSearchInput(event.target.value)} className="w-full bg-transparent text-sm font-normal outline-none" placeholder="Search…" />
            </span>
          </label>
          <label className="space-y-1 text-xs font-medium text-slate-600">Division
            <select value={division} onChange={(event) => setDivision(event.target.value)} className="block w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm font-normal">
              <option value="">All divisions</option>
              {divisions.map((item) => {
                const value = (item.division || item.divisionName || "").trim();
                return <option key={item.productId} value={value}>{item.divisionName || item.division}</option>;
              })}
            </select>
          </label>
          <label className="space-y-1 text-xs font-medium text-slate-600">SC reference
            <input value={scRefNo} onChange={(event) => setScRefNo(event.target.value)} className="block w-full rounded-xl border border-slate-200 px-3 py-2 text-sm font-normal" />
          </label>
          <label className="space-y-1 text-xs font-medium text-slate-600">Entry date from
            <input type="date" value={from} max={to || undefined} onChange={(event) => setFrom(event.target.value)} className="block w-full rounded-xl border border-slate-200 px-3 py-2 text-sm font-normal" />
          </label>
          <label className="space-y-1 text-xs font-medium text-slate-600">Entry date to
            <input type="date" value={to} min={from || undefined} onChange={(event) => setTo(event.target.value)} className="block w-full rounded-xl border border-slate-200 px-3 py-2 text-sm font-normal" />
          </label>
          <label className="space-y-1 text-xs font-medium text-slate-600">Sort
            <select value={sort} onChange={(event) => setSort(event.target.value)} className="block w-full rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm font-normal">
              <option value="id,desc">Newest first</option>
              <option value="id,asc">Oldest first</option>
              <option value="entryDate,desc">Entry date ↓</option>
              <option value="entryDate,asc">Entry date ↑</option>
              <option value="scRefNo,asc">SC reference ↑</option>
              <option value="division,asc">Division ↑</option>
              <option value="noOfDays,desc">Days ↓</option>
            </select>
          </label>
        </div>
        <div className="mt-3 flex min-h-8 items-center justify-between">
          {invalidRange ? <p className="text-sm text-red-700">The start date must be on or before the end date.</p> : <span />}
          {hasFilters && <Button variant="outline" size="sm" onClick={clearFilters}><X className="mr-1 h-3.5 w-3.5" />Clear</Button>}
        </div>
      </section>

      {error && <div role="alert" className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800">{error} <button onClick={() => void load()} className="ml-2 font-semibold underline">Retry</button></div>}

      <section className="overflow-hidden rounded-2xl border border-slate-100 bg-white shadow-sm">
        <div className="overflow-x-auto">
          <table className="w-full min-w-[1150px] text-xs">
            <thead className="border-b border-slate-100 bg-slate-50/70 text-left uppercase tracking-wide text-slate-500">
              <tr>{["Entry date", "Division", "SC ref no", "Def GIR no", "Category", "Model", "Def board/module", "No. days", "Status", ...(status === "PENDING" ? ["Update"] : [])].map((heading) => <th key={heading} className="px-3 py-3 font-semibold">{heading}</th>)}</tr>
            </thead>
            <tbody>
              {loading && rows.length === 0 ? <tr><td colSpan={status === "PENDING" ? 10 : 9} className="py-16 text-center text-slate-500"><Loader2 className="mr-2 inline h-5 w-5 animate-spin" />Loading repairs…</td></tr>
                : rows.length === 0 ? <tr><td colSpan={status === "PENDING" ? 10 : 9} className="py-16 text-center text-slate-500">No repairs match these filters.</td></tr>
                : rows.map((row) => (
                  <tr key={row.id} className="border-b border-slate-50 hover:bg-violet-50/20">
                    <td className="whitespace-nowrap px-3 py-2.5">{display(row.entryDate)}</td>
                    <td className="px-3 py-2.5">{display(row.division)}</td>
                    <td className="whitespace-nowrap px-3 py-2.5 font-mono font-semibold text-violet-700">{display(row.scRefNo)}</td>
                    <td className="whitespace-nowrap px-3 py-2.5 font-mono">{display(row.defGirNo)}</td>
                    <td className="px-3 py-2.5"><Badge variant="outline">{display(row.category)}</Badge></td>
                    <td className="px-3 py-2.5">{display(row.model)}</td>
                    <td className="max-w-[220px] truncate px-3 py-2.5">{display(row.defBoardModuleName)}</td>
                    <td className="px-3 py-2.5 text-right font-semibold tabular-nums">
                      {display(status === "PENDING" ? (row.pendingDays ?? row.days) : row.days)}
                    </td>
                    <td className="px-3 py-2.5"><Badge variant="outline" className={row.status === "COMPLETED" ? "border-emerald-200 bg-emerald-50 text-emerald-700" : "border-amber-200 bg-amber-50 text-amber-700"}>{row.status.toLowerCase()}</Badge></td>
                    {status === "PENDING" && <td className="px-3 py-2.5"><Link href={`/dashboard/repair/${row.id}/edit`} className="font-semibold text-violet-700 hover:underline">Update</Link></td>}
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
        {!loading && totalPages > 0 && <footer className="flex flex-col gap-3 border-t border-slate-100 bg-slate-50/50 p-4 sm:flex-row sm:items-center sm:justify-between">
          <div className="flex items-center gap-3 text-sm text-slate-600"><span>{page * size + 1}–{Math.min((page + 1) * size, totalElements)} of {totalElements}</span><select value={size} onChange={(event) => setSize(Number(event.target.value))} className="rounded-lg border border-slate-200 bg-white px-2 py-1.5">{PAGE_SIZES.map((value) => <option key={value}>{value}</option>)}</select></div>
          <div className="flex items-center gap-1"><Button variant="outline" size="sm" disabled={page === 0} onClick={() => setPage((value) => value - 1)}>Previous</Button>{pageNumbers(page, totalPages).map((value, index) => value === "…" ? <span key={`ellipsis-${index}`} className="px-2">…</span> : <Button key={value} variant={value === page ? "default" : "outline"} size="sm" onClick={() => setPage(value)}>{value + 1}</Button>)}<Button variant="outline" size="sm" disabled={page + 1 >= totalPages} onClick={() => setPage((value) => value + 1)}>Next</Button></div>
        </footer>}
      </section>
    </div>
  );
}
