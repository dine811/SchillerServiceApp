"use client";

import Link from "next/link";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";
import { Wrench, Clock3, Package, CheckCircle2, AlertCircle, PhoneCall, ClipboardList, FileSpreadsheet, Boxes } from "lucide-react";

type EngineerNavItem = {
  label: string;
  href?: string;
  icon: React.ComponentType<{ className?: string }>;
  status?: "ready" | "coming-soon";
  note?: string;
};

const serviceItems: EngineerNavItem[] = [
  { label: "Services", href: "/dashboard/services", icon: Wrench, status: "ready" },
  { label: "Pending FRN", href: "/dashboard/pending-frn", icon: AlertCircle, status: "ready" },
  { label: "OB Pending", href: "/dashboard/ob-pending", icon: Package, status: "ready" },
  { label: "Under Repair Pending", href: "/dashboard/under-repair", icon: Clock3, status: "ready" },
  { label: "SC Completed FRN", href: "/dashboard/sc-closed", icon: CheckCircle2, status: "ready" },
  { label: "Completed FRN", href: "/dashboard/new-closed", icon: CheckCircle2, status: "ready" },
  { label: "Scrap List", href: "/dashboard/scrap-list", icon: Boxes, status: "ready" },
];

const activityItems: EngineerNavItem[] = [
  { label: "Call Register", href: "/dashboard/call-list", icon: PhoneCall, status: "ready" },
  { label: "Closed Calls", href: "/dashboard/closed-calls", icon: CheckCircle2, status: "ready" },
  { label: "Pending Activity", href: "/dashboard/pending-activity", icon: ClipboardList, status: "ready" },
  { label: "Closed Activity", href: "/dashboard/closed-activity", icon: ClipboardList, status: "ready" },
  { label: "PRF/OB Register", href: "/dashboard/prf-ob-admin", icon: ClipboardList, status: "ready" },
  { label: "Closed PRF/OB Register", href: "/dashboard/prf-ob-closed", icon: CheckCircle2, status: "ready" },
  { label: "Non Saleable", href: "/dashboard/non-salable-admin", icon: AlertCircle, status: "ready" },
  { label: "Saleables", href: "/dashboard/salables", icon: CheckCircle2, status: "ready" },
  { label: "BIR List", href: "/dashboard/bir-admin", icon: FileSpreadsheet, status: "ready" },
  { label: "Closed BIR", href: "/dashboard/bir-closed-admin", icon: CheckCircle2, status: "ready" },
  { label: "Spares List", href: "/dashboard/spares", icon: Boxes, status: "ready" },
  { label: "Spares List Completed", href: "/dashboard/spares-completed", icon: Boxes, status: "ready" },
];

function Section({ title, items }: { title: string; items: EngineerNavItem[] }) {
  return (
    <Card className="rounded-2xl border-slate-100 shadow-sm">
      <CardHeader className="pb-2">
        <CardTitle className="text-sm font-semibold text-slate-800">{title}</CardTitle>
      </CardHeader>
      <CardContent className="pt-2">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
          {items.map((item) => {
            const content = (
              <div
                className={cn(
                  "rounded-xl border p-3.5 transition-colors",
                  item.status === "ready"
                    ? "border-slate-200 bg-white hover:bg-slate-50"
                    : "border-slate-100 bg-slate-50"
                )}
              >
                <div className="flex items-start justify-between gap-2">
                  <div className="flex items-center gap-2.5">
                    <div className="h-8 w-8 rounded-lg bg-slate-100 flex items-center justify-center">
                      <item.icon className="h-4 w-4 text-slate-700" />
                    </div>
                    <div>
                      <p className="text-sm font-medium text-slate-900">{item.label}</p>
                      {item.note ? <p className="text-[11px] text-slate-500 mt-0.5">{item.note}</p> : null}
                    </div>
                  </div>
                  {item.status === "coming-soon" ? (
                    <Badge variant="outline" className="text-[10px] bg-slate-100 border-slate-200 text-slate-600">
                      Coming soon
                    </Badge>
                  ) : (
                    <Badge variant="outline" className="text-[10px] bg-emerald-50 border-emerald-200 text-emerald-700">
                      Ready
                    </Badge>
                  )}
                </div>
              </div>
            );

            if (item.status === "ready" && item.href) {
              return (
                <Link key={item.label} href={item.href}>
                  {content}
                </Link>
              );
            }
            return <div key={item.label}>{content}</div>;
          })}
        </div>
      </CardContent>
    </Card>
  );
}

export default function EngineerDashboardPage() {
  return (
    <div className="space-y-5">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Engineer Dashboard</h1>
        <p className="text-sm text-slate-500 mt-1">Migrated from legacy <code>engineerdashboard.jsp</code>.</p>
      </div>

      <Section title="Services & Filters" items={serviceItems} />
      <Section title="Activity Registers" items={activityItems} />
    </div>
  );
}
