"use client";

import Link from "next/link";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ClipboardCheck, ClipboardList, FileSpreadsheet, ListX, PackageCheck } from "lucide-react";

/**
 * Legacy mapping:
 * index.jsp role FQC -> include FQCdashboard.jsp.
 * Full legacy FQC menu mapping:
 * - PendingActListENGG.jsp
 * - ClosedActivity.jsp
 * - nonSaleAdminList.jsp
 * - SalablesList.jsp
 * - BIRAdminList.jsp
 * - ClosedBIRList_admin.jsp
 */
export default function FqcDashboardPage() {
  const items = [
    {
      label: "Pending Activity",
      href: "/dashboard/pending-activity",
      legacy: "PendingActListENGG.jsp",
      icon: ClipboardList,
    },
    {
      label: "Closed Activity",
      href: "/dashboard/closed-activity",
      legacy: "ClosedActivity.jsp",
      icon: ClipboardCheck,
    },
    {
      label: "Non Saleable",
      href: "/dashboard/non-salable-admin",
      legacy: "nonSaleAdminList.jsp",
      icon: ListX,
    },
    {
      label: "Saleables",
      href: "/dashboard/salables",
      legacy: "SalablesList.jsp",
      icon: PackageCheck,
    },
    {
      label: "BIR LIST",
      href: "/dashboard/bir-admin",
      legacy: "BIRAdminList.jsp",
      icon: FileSpreadsheet,
    },
    {
      label: "Closed BIR LIST",
      href: "/dashboard/bir-closed-admin",
      legacy: "ClosedBIRList_admin.jsp",
      icon: ClipboardCheck,
    },
  ] as const;

  return (
    <div className="space-y-5">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">FQC Dashboard</h1>
        <p className="text-sm text-slate-500 mt-1">Migrated from legacy <code>FQCdashboard.jsp</code>.</p>
      </div>

      <Card className="rounded-2xl border-slate-100 shadow-sm">
        <CardHeader className="pb-2">
          <CardTitle className="text-sm font-semibold text-slate-800">Activity registers</CardTitle>
        </CardHeader>
        <CardContent className="pt-2">
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
        </CardContent>
      </Card>
    </div>
  );
}

