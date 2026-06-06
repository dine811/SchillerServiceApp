"use client";

import Link from "next/link";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  BadgeCheck,
  CheckCircle2,
  ClipboardCheck,
  ClipboardList,
  FileSpreadsheet,
  ListX,
  Package,
  PackageCheck,
  Phone,
  Puzzle,
  Timer,
  Trash2,
  Wrench,
} from "lucide-react";

/**
 * Legacy mapping:
 * index.jsp role ViceChancellor → include VPDashboard.jsp
 */
export default function ViceChancellorDashboardPage() {
  const serviceItems = [
    { label: "Services", href: "/dashboard/services", legacy: "ServiceList.jsp", icon: Wrench },
    { label: "Under Repair", href: "/dashboard/under-repair", legacy: "under_repair.jsp", icon: Timer },
    { label: "Pending FRN", href: "/dashboard/pending-frn", legacy: "pending_FRN.jsp", icon: Puzzle },
    { label: "OB Pending", href: "/dashboard/ob-pending", legacy: "ob_pending.jsp", icon: Package },
    { label: "SC Closed", href: "/dashboard/sc-closed", legacy: "closedproduct.jsp", icon: CheckCircle2 },
    { label: "Closed Product", href: "/dashboard/new-closed", legacy: "New_ClosedProduct.jsp", icon: BadgeCheck },
    { label: "Scrap List", href: "/dashboard/scrap-list", legacy: "ScarpList.jsp", icon: Trash2 },
  ] as const;

  const activityItems = [
    { label: "Call Register", href: "/dashboard/call-list", legacy: "CallListAdmin.jsp", icon: Phone },
    { label: "Closed Calls", href: "/dashboard/closed-calls", legacy: "ClosedCalls.jsp", icon: Phone },
    { label: "Pending Activity", href: "/dashboard/pending-activity", legacy: "PendingActListAdmin.jsp", icon: ClipboardList },
    { label: "Closed Activity", href: "/dashboard/closed-activity", legacy: "ClosedActivity.jsp", icon: ClipboardCheck },
    { label: "PRF/OB List", href: "/dashboard/prf-ob-admin", legacy: "PRFOB_AdminList.jsp", icon: ClipboardList },
    { label: "Closed PRF/OB", href: "/dashboard/prf-ob-closed", legacy: "PRFOB_AdminList_closed.jsp", icon: ClipboardCheck },
    { label: "Non Salable", href: "/dashboard/non-salable-admin", legacy: "nonSaleAdminList.jsp", icon: ListX },
    { label: "Salables", href: "/dashboard/salables", legacy: "SalablesList.jsp", icon: PackageCheck },
    { label: "BIR List", href: "/dashboard/bir-admin", legacy: "BIRAdminList.jsp", icon: FileSpreadsheet },
    { label: "Closed BIR", href: "/dashboard/bir-closed-admin", legacy: "ClosedBIRList.jsp", icon: ClipboardCheck },
  ] as const;

  const renderGrid = (items: typeof serviceItems | typeof activityItems) => (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
      {items.map((item) => (
        <Link key={item.href} href={item.href} className="block">
          <div className="rounded-xl border border-slate-200 bg-white p-3.5 transition-colors hover:bg-slate-50">
            <div className="flex items-start justify-between gap-2">
              <div className="flex items-center gap-2.5">
                <div className="h-8 w-8 rounded-lg bg-slate-100 flex items-center justify-center">
                  <item.icon className="h-4 w-4 text-slate-700" />
                </div>
                <div>
                  <p className="text-sm font-medium text-slate-900">{item.label}</p>
                  <p className="text-[11px] text-slate-500 mt-0.5">Legacy {item.legacy}</p>
                </div>
              </div>
              <Badge variant="outline" className="text-[10px] bg-emerald-50 border-emerald-200 text-emerald-700">
                Ready
              </Badge>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );

  return (
    <div className="space-y-5">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Vice Chancellor Dashboard</h1>
        <p className="text-sm text-slate-500 mt-1">Migrated from legacy <code>VPDashboard.jsp</code>.</p>
      </div>

      <Card className="rounded-2xl border-slate-100 shadow-sm">
        <CardHeader className="pb-2">
          <CardTitle className="text-sm font-semibold text-slate-800">Services &amp; filters</CardTitle>
        </CardHeader>
        <CardContent className="pt-2">{renderGrid(serviceItems)}</CardContent>
      </Card>

      <Card className="rounded-2xl border-slate-100 shadow-sm">
        <CardHeader className="pb-2">
          <CardTitle className="text-sm font-semibold text-slate-800">Activity registers</CardTitle>
        </CardHeader>
        <CardContent className="pt-2">{renderGrid(activityItems)}</CardContent>
      </Card>
    </div>
  );
}
