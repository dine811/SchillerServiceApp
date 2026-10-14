"use client";

import { useState } from "react";
import { Loader2 } from "lucide-react";
import { RepairService, type RepairCategory } from "@/services/repair-service";

interface RepairInitiateButtonProps {
  serviceId?: number;
  category: RepairCategory;
  repairStatus?: string | null;
}

export function RepairInitiateButton({ serviceId, category, repairStatus }: RepairInitiateButtonProps) {
  const [loading, setLoading] = useState(false);
  const normalized = (repairStatus ?? "").trim().toUpperCase();

  if (normalized === "RP" || normalized === "RC") {
    return <span className="text-slate-500 text-xs font-medium">{normalized}</span>;
  }
  if (normalized !== "NR") {
    return <span className="text-slate-400 text-xs">{normalized || "—"}</span>;
  }
  if (serviceId == null) return <span className="text-slate-400">—</span>;

  const initiate = async () => {
    setLoading(true);
    try {
      await RepairService.initiate(serviceId, category);
      window.location.reload();
    } catch (error) {
      alert(error instanceof Error ? error.message : "Failed to initiate repair");
      setLoading(false);
    }
  };

  return (
    <button
      type="button"
      onClick={initiate}
      disabled={loading}
      className="inline-flex items-center gap-1 text-violet-600 font-semibold text-xs hover:underline disabled:opacity-60 whitespace-nowrap"
      aria-label={`Initiate ${category} repair`}
    >
      {loading && <Loader2 className="h-3 w-3 animate-spin" aria-hidden />}
      Start
    </button>
  );
}
