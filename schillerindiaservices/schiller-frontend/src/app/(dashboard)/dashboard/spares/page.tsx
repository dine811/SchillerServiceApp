"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import {
  canCreateSparesForRole,
  canDeleteSparesForRole,
  isServiceCoordinator,
  isSparesAllDivisionsRole,
} from "@/lib/app-role";
import { ServiceService, type SpareMasterRecord } from "@/services/service-service";

type MeResponse = { role?: string };

export default function SparesPage() {
  const router = useRouter();
  const [rows, setRows] = useState<SpareMasterRecord[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [query, setQuery] = useState("");
  const [search, setSearch] = useState("");
  const [divisionFilter, setDivisionFilter] = useState("");
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [isPrivileged, setIsPrivileged] = useState(false);
  const [canDelete, setCanDelete] = useState(false);
  const [canCreate, setCanCreate] = useState(false);
  const [isCoordinator, setIsCoordinator] = useState(false);

  const canGoPrev = page > 0;
  const canGoNext = page + 1 < totalPages;

  useEffect(() => {
    let mounted = true;
    fetch("/api/auth/me", { credentials: "include" })
      .then((r) => (r.ok ? r.json() : null))
      .then((me: MeResponse | null) => {
        if (!mounted) return;
        const role = me?.role ?? "";
        setIsPrivileged(isSparesAllDivisionsRole(role));
        setCanDelete(canDeleteSparesForRole(role));
        setCanCreate(canCreateSparesForRole(role));
        setIsCoordinator(isServiceCoordinator(role));
      })
      .catch(() => undefined);
    return () => {
      mounted = false;
    };
  }, []);

  const loadRows = useMemo(
    () => async () => {
      setLoading(true);
      setError(null);
      try {
        const res = await ServiceService.getPendingSpares(page, size, {
          search,
          division: isPrivileged ? divisionFilter : undefined,
          sort: "id,desc",
        });
        setRows(res.content ?? []);
        setTotalPages(res.totalPages ?? 0);
      } catch (e: unknown) {
        setError(e instanceof Error ? e.message : "Failed to load spares list");
      } finally {
        setLoading(false);
      }
    },
    [page, size, search, divisionFilter, isPrivileged]
  );

  useEffect(() => {
    void loadRows();
  }, [loadRows]);

  const onSearch = () => {
    setPage(0);
    setSearch(query.trim());
  };

  const onDelete = async (id?: number) => {
    if (!id) return;
    if (!confirm(`Delete spares record ${id}?`)) return;
    try {
      await ServiceService.deleteSpare(id);
      await loadRows();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Failed to delete spares record");
    }
  };

  const onExport = async () => {
    try {
      const blob = await ServiceService.exportPendingSparesExcel({
        search,
        division: isPrivileged ? divisionFilter : undefined,
        from: fromDate || undefined,
        to: toDate || undefined,
      });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `Spares_List_${new Date().toISOString().slice(0, 10)}.xlsx`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      URL.revokeObjectURL(url);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Failed to export spares list");
    }
  };

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-slate-900">
            {isCoordinator ? "Spares Request Update" : "Spares List"}
          </h1>
          <p className="text-sm text-slate-500">
            {isCoordinator
              ? "Legacy spareslist_update.jsp — pending spares, all divisions (Update only)."
              : "Legacy spareslist_engg.jsp — pending spares."}
          </p>
        </div>
        <div className="flex gap-2">
          <Link href="/dashboard/spares-completed" className="rounded border border-slate-300 px-3 py-2 text-sm text-slate-700 hover:bg-slate-50">
            Closed List
          </Link>
          {canCreate ? (
            <Link href="/dashboard/spares/add" className="rounded bg-blue-600 px-3 py-2 text-sm font-medium text-white hover:bg-blue-700">
              Add New Entry
            </Link>
          ) : null}
        </div>
      </div>

      <div className="grid grid-cols-1 gap-3 rounded-xl border border-slate-200 bg-white p-3 md:grid-cols-6">
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search model / part / status..."
          className="rounded border border-slate-300 px-3 py-2 text-sm md:col-span-2"
        />
        <button onClick={onSearch} className="rounded bg-slate-800 px-3 py-2 text-sm font-medium text-white hover:bg-slate-900">
          Search
        </button>
        {isPrivileged ? (
          <input
            value={divisionFilter}
            onChange={(e) => {
              setDivisionFilter(e.target.value);
              setPage(0);
            }}
            placeholder="Division filter"
            className="rounded border border-slate-300 px-3 py-2 text-sm"
          />
        ) : (
          <div className="rounded border border-slate-200 bg-slate-50 px-3 py-2 text-xs text-slate-500">Division auto-scoped by login</div>
        )}
        <input type="date" value={fromDate} onChange={(e) => setFromDate(e.target.value)} className="rounded border border-slate-300 px-3 py-2 text-sm" />
        <input type="date" value={toDate} onChange={(e) => setToDate(e.target.value)} className="rounded border border-slate-300 px-3 py-2 text-sm" />
      </div>

      <div className="flex justify-end">
        <button onClick={onExport} className="rounded bg-emerald-600 px-3 py-2 text-sm font-medium text-white hover:bg-emerald-700">
          Export
        </button>
      </div>

      {error ? <div className="rounded border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700">{error}</div> : null}

      <div className="overflow-auto rounded-xl border border-slate-200 bg-white">
        <table className="min-w-full text-sm">
          <thead className="bg-slate-50 text-left">
            <tr>
              {["Id", "Entry Date", "Division", "Supplier", "Model", "Part Number", "Def_Mod_Brd_Name", "Reason", "Reference", "Gir_No", "Sc_Engg", "Final Status", "Action"].map((h) => (
                <th key={h} className="whitespace-nowrap px-3 py-2 text-xs font-semibold uppercase tracking-wide text-slate-600">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={13} className="px-3 py-6 text-center text-slate-500">Loading...</td>
              </tr>
            ) : rows.length === 0 ? (
              <tr>
                <td colSpan={13} className="px-3 py-6 text-center text-slate-500">No records found</td>
              </tr>
            ) : (
              rows.map((row) => (
                <tr key={row.id} className="border-t border-slate-100">
                  <td className="whitespace-nowrap px-3 py-2">{row.id}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.entryDate ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.division ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.supplier ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.model ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.partNumber ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.defModBrdName ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.reason ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.reference ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.girNo ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.scEnggName ?? row.scEngg ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">{row.finalStatus ?? ""}</td>
                  <td className="whitespace-nowrap px-3 py-2">
                    <div className="flex items-center gap-2">
                      <button
                        onClick={() => row.id && router.push(`/dashboard/spares/${row.id}/edit?source=pending`)}
                        className="text-blue-600 hover:underline"
                      >
                        Update
                      </button>
                      {canDelete ? (
                        <button onClick={() => void onDelete(row.id)} className="text-red-600 hover:underline">
                          Delete
                        </button>
                      ) : null}
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      <div className="flex items-center justify-end gap-2">
        <button
          className="rounded border border-slate-300 px-3 py-1.5 text-sm disabled:opacity-50"
          onClick={() => canGoPrev && setPage((p) => p - 1)}
          disabled={!canGoPrev}
        >
          Previous
        </button>
        <span className="text-sm text-slate-600">Page {totalPages === 0 ? 0 : page + 1} / {totalPages}</span>
        <button
          className="rounded border border-slate-300 px-3 py-1.5 text-sm disabled:opacity-50"
          onClick={() => canGoNext && setPage((p) => p + 1)}
          disabled={!canGoNext}
        >
          Next
        </button>
      </div>
    </div>
  );
}
