import type { LucideIcon } from "lucide-react";
import {
  LayoutDashboard,
  Wrench,
  Timer,
  CheckCircle2,
  BadgeCheck,
  Puzzle,
  Package,
  Trash2,
  Users,
  Building2,
  MapPin,
  MailWarning,
  FileText,
  Settings,
  Boxes,
  Phone,
  ClipboardList,
  ClipboardCheck,
  ListX,
  PackageCheck,
  FileSpreadsheet,
} from "lucide-react";
import { isAllowedDashboardPath } from "@/lib/auth";

/** Single nav entry for sidebar and dashboard quick links */
export type NavItem = {
  label: string;
  href: string;
  icon: LucideIcon;
  /** Tailwind gradient classes for active icon background */
  gradient: string;
  /** Short line for dashboard cards */
  description?: string;
};

export type NavSection = {
  /** Stable id for keys */
  id: "master" | "services" | "activity";
  /** Section heading (matches legacy admindashboard.jsp groupings) */
  title: string;
  items: NavItem[];
};

/**
 * Legacy admin sidebar structure:
 * - Master data: Dashboard, Division, Engineers, Dealers (Company omitted until migrated).
 * - Services & filters: Service master queues (was nested under blue "Services & Filter").
 * - Activity registers: operational lists (was nested under "Activity Registers"); add more routes here as you migrate JSPs.
 */
export const dashboardNavSections: NavSection[] = [
  {
    id: "master",
    title: "Master data",
    items: [
      {
        label: "Dashboard",
        href: "/dashboard",
        icon: LayoutDashboard,
        gradient: "from-violet-500 to-indigo-500",
        description: "Overview and KPIs",
      },
      {
        label: "Divisions",
        href: "/dashboard/divisions",
        icon: Building2,
        gradient: "from-amber-500 to-orange-500",
        description: "Products and divisions",
      },
      {
        label: "Employees",
        href: "/dashboard/employees",
        icon: Users,
        gradient: "from-pink-500 to-rose-500",
        description: "Engineers and staff",
      },
      {
        label: "Dealers",
        href: "/dashboard/dealers",
        icon: MapPin,
        gradient: "from-emerald-500 to-teal-500",
        description: "Dealer directory",
      },
    ],
  },
  {
    id: "services",
    title: "Services & filters",
    items: [
      {
        label: "Services",
        href: "/dashboard/services",
        icon: Wrench,
        gradient: "from-sky-500 to-cyan-500",
        description: "All service master records",
      },
      {
        label: "Under repair",
        href: "/dashboard/under-repair",
        icon: Timer,
        gradient: "from-amber-500 to-orange-500",
        description: "Shipped from SC, repair pending",
      },
      {
        label: "SC Closed",
        href: "/dashboard/sc-closed",
        icon: CheckCircle2,
        gradient: "from-emerald-500 to-teal-500",
        description: "Awaiting commercial ship",
      },
      {
        label: "Closed Product",
        href: "/dashboard/new-closed",
        icon: BadgeCheck,
        gradient: "from-violet-500 to-purple-500",
        description: "Commercial closure complete",
      },
      {
        label: "Pending FRN",
        href: "/dashboard/pending-frn",
        icon: Puzzle,
        gradient: "from-rose-500 to-pink-500",
        description: "FRN queue",
      },
      {
        label: "OB Pending",
        href: "/dashboard/ob-pending",
        icon: Package,
        gradient: "from-amber-500 to-yellow-500",
        description: "OW / LAMC pending",
      },
      {
        label: "Scrap list",
        href: "/dashboard/scrap-list",
        icon: Trash2,
        gradient: "from-stone-500 to-neutral-600",
        description: "SCRAPPED work type",
      },
    ],
  },
  {
    id: "activity",
    title: "Activity registers",
    items: [
      {
        label: "Call register",
        href: "/dashboard/call-list",
        icon: Phone,
        gradient: "from-blue-500 to-indigo-600",
        description: "Pending calls (admin)",
      },
      {
        label: "Closed calls",
        href: "/dashboard/closed-calls",
        icon: CheckCircle2,
        gradient: "from-emerald-500 to-teal-600",
        description: "Completed call register",
      },
      {
        label: "Pending activity",
        href: "/dashboard/pending-activity",
        icon: ClipboardList,
        gradient: "from-indigo-500 to-violet-600",
        description: "Pending activity register",
      },
      {
        label: "Closed activity",
        href: "/dashboard/closed-activity",
        icon: ClipboardCheck,
        gradient: "from-emerald-500 to-teal-600",
        description: "Completed activity register",
      },
      {
        label: "PRF/OB list",
        href: "/dashboard/prf-ob-admin",
        icon: ClipboardList,
        gradient: "from-violet-500 to-purple-600",
        description: "Pending PRF/OB (admin)",
      },
      {
        label: "Closed PRF/OB",
        href: "/dashboard/prf-ob-closed",
        icon: ClipboardCheck,
        gradient: "from-emerald-500 to-teal-600",
        description: "Completed PRF/OB (admin)",
      },
      {
        label: "Non salable list",
        href: "/dashboard/non-salable-admin",
        icon: ListX,
        gradient: "from-amber-500 to-orange-600",
        description: "Pending non-salable (admin)",
      },
      {
        label: "Salables",
        href: "/dashboard/salables",
        icon: PackageCheck,
        gradient: "from-teal-500 to-cyan-600",
        description: "Completed non-salable",
      },
      {
        label: "BIR list",
        href: "/dashboard/bir-admin",
        icon: FileSpreadsheet,
        gradient: "from-indigo-500 to-violet-600",
        description: "Batch Inspection Report pending",
      },
      {
        label: "Closed BIR",
        href: "/dashboard/bir-closed-admin",
        icon: ClipboardCheck,
        gradient: "from-sky-500 to-teal-600",
        description: "BIR completed",
      },
      {
        label: "Escalations",
        href: "/dashboard/escalations",
        icon: MailWarning,
        gradient: "from-red-500 to-rose-600",
        description: "Escalation tracking",
      },
      {
        label: "Reports",
        href: "/dashboard/reports",
        icon: FileText,
        gradient: "from-emerald-500 to-teal-500",
        description: "Reports and exports",
      },
      {
        label: "Spares entry",
        href: "/dashboard/spares-entry",
        icon: Boxes,
        gradient: "from-cyan-500 to-sky-600",
        description: "Admin part number entry (legacy partNumberForm)",
      },
      {
        label: "Settings",
        href: "/dashboard/settings",
        icon: Settings,
        gradient: "from-slate-400 to-slate-600",
        description: "App preferences",
      },
    ],
  },
];

/** Legacy engineer sidebar (engineerdashboard.jsp) mapped to migrated routes. */
export const engineerNavSections: NavSection[] = [
  {
    id: "services",
    title: "Services & filters",
    items: [
      { label: "Services", href: "/dashboard/services", icon: Wrench, gradient: "from-sky-500 to-cyan-500" },
      { label: "Pending FRN", href: "/dashboard/pending-frn", icon: Puzzle, gradient: "from-rose-500 to-pink-500" },
      { label: "OB Pending", href: "/dashboard/ob-pending", icon: Package, gradient: "from-amber-500 to-yellow-500" },
      { label: "Under Repair Pending", href: "/dashboard/under-repair", icon: Timer, gradient: "from-amber-500 to-orange-500" },
      { label: "SC Completed FRN", href: "/dashboard/sc-closed", icon: CheckCircle2, gradient: "from-emerald-500 to-teal-500" },
      { label: "Completed FRN", href: "/dashboard/new-closed", icon: BadgeCheck, gradient: "from-violet-500 to-purple-500" },
      { label: "Scrap List", href: "/dashboard/scrap-list", icon: Trash2, gradient: "from-stone-500 to-neutral-600" },
    ],
  },
  {
    id: "activity",
    title: "Activity registers",
    items: [
      { label: "Call Register", href: "/dashboard/call-list", icon: Phone, gradient: "from-blue-500 to-indigo-600" },
      { label: "Closed Calls", href: "/dashboard/closed-calls", icon: CheckCircle2, gradient: "from-emerald-500 to-teal-600" },
      { label: "Pending Activity", href: "/dashboard/pending-activity", icon: ClipboardList, gradient: "from-indigo-500 to-violet-600" },
      { label: "Closed Activity", href: "/dashboard/closed-activity", icon: ClipboardCheck, gradient: "from-emerald-500 to-teal-600" },
      { label: "PRF/OB Register", href: "/dashboard/prf-ob-admin", icon: ClipboardList, gradient: "from-violet-500 to-purple-600" },
      { label: "Closed PRF/OB Register", href: "/dashboard/prf-ob-closed", icon: ClipboardCheck, gradient: "from-emerald-500 to-teal-600" },
      { label: "Non Saleable", href: "/dashboard/non-salable-admin", icon: ListX, gradient: "from-amber-500 to-orange-600" },
      { label: "Saleables", href: "/dashboard/salables", icon: PackageCheck, gradient: "from-teal-500 to-cyan-600" },
      { label: "BIR LIST", href: "/dashboard/bir-admin", icon: FileSpreadsheet, gradient: "from-indigo-500 to-violet-600" },
      { label: "Closed BIR", href: "/dashboard/bir-closed-admin", icon: ClipboardCheck, gradient: "from-sky-500 to-teal-600" },
      { label: "Spares_List", href: "/dashboard/spares", icon: Boxes, gradient: "from-cyan-500 to-sky-600" },
      { label: "Spares_List_Completed", href: "/dashboard/spares-completed", icon: Boxes, gradient: "from-emerald-500 to-teal-600" },
    ],
  },
];

/** Legacy service coordinator sidebar (ServiceCoorDashBoard.jsp). */
export const serviceCoordinatorNavSections: NavSection[] = [
  {
    id: "activity",
    title: "Coordinator",
    items: [
      {
        label: "Dashboard",
        href: "/dashboard/service-coordinator",
        icon: LayoutDashboard,
        gradient: "from-violet-500 to-indigo-500",
      },
      {
        label: "PRF/OB Register",
        href: "/dashboard/prf-ob-admin",
        icon: ClipboardList,
        gradient: "from-violet-500 to-purple-600",
      },
      {
        label: "Closed PRF/OB Register",
        href: "/dashboard/prf-ob-closed",
        icon: ClipboardCheck,
        gradient: "from-emerald-500 to-teal-600",
      },
      {
        label: "Spares Request Update",
        href: "/dashboard/spares",
        icon: Boxes,
        gradient: "from-cyan-500 to-sky-600",
      },
      {
        label: "Spares List Completed",
        href: "/dashboard/spares-completed",
        icon: Boxes,
        gradient: "from-emerald-500 to-teal-600",
      },
    ],
  },
];

/** Legacy FQC sidebar (FQCdashboard.jsp). */
export const fqcNavSections: NavSection[] = [
  {
    id: "activity",
    title: "Activity registers",
    items: [
      {
        label: "Pending Activity",
        href: "/dashboard/pending-activity",
        icon: ClipboardList,
        gradient: "from-indigo-500 to-violet-600",
      },
      {
        label: "Closed Activity",
        href: "/dashboard/closed-activity",
        icon: ClipboardCheck,
        gradient: "from-emerald-500 to-teal-600",
      },
      {
        label: "Non Saleable",
        href: "/dashboard/non-salable-admin",
        icon: ListX,
        gradient: "from-amber-500 to-orange-600",
      },
      {
        label: "Saleables",
        href: "/dashboard/salables",
        icon: PackageCheck,
        gradient: "from-teal-500 to-cyan-600",
      },
      {
        label: "BIR LIST",
        href: "/dashboard/bir-admin",
        icon: FileSpreadsheet,
        gradient: "from-indigo-500 to-violet-600",
      },
      {
        label: "Closed BIR LIST",
        href: "/dashboard/bir-closed-admin",
        icon: ClipboardCheck,
        gradient: "from-sky-500 to-teal-600",
      },
    ],
  },
];

/** Legacy VPDashboard.jsp — routes allowed for VICE_CHANCELLOR in auth.ts. */
export const viceChancellorNavSections: NavSection[] = [
  {
    id: "services",
    title: "Services & filters",
    items: [
      {
        label: "Dashboard",
        href: "/dashboard/vp",
        icon: LayoutDashboard,
        gradient: "from-violet-500 to-indigo-500",
      },
      {
        label: "Services",
        href: "/dashboard/services",
        icon: Wrench,
        gradient: "from-sky-500 to-cyan-500",
      },
      {
        label: "Under repair",
        href: "/dashboard/under-repair",
        icon: Timer,
        gradient: "from-amber-500 to-orange-500",
      },
      {
        label: "Pending FRN",
        href: "/dashboard/pending-frn",
        icon: Puzzle,
        gradient: "from-rose-500 to-pink-500",
      },
      {
        label: "OB Pending",
        href: "/dashboard/ob-pending",
        icon: Package,
        gradient: "from-amber-500 to-yellow-500",
      },
      {
        label: "SC Closed",
        href: "/dashboard/sc-closed",
        icon: CheckCircle2,
        gradient: "from-emerald-500 to-teal-500",
      },
      {
        label: "Closed Product",
        href: "/dashboard/new-closed",
        icon: BadgeCheck,
        gradient: "from-violet-500 to-purple-500",
      },
      {
        label: "Scrap list",
        href: "/dashboard/scrap-list",
        icon: Trash2,
        gradient: "from-stone-500 to-neutral-600",
      },
    ],
  },
  {
    id: "activity",
    title: "Activity registers",
    items: [
      {
        label: "Call register",
        href: "/dashboard/call-list",
        icon: Phone,
        gradient: "from-blue-500 to-indigo-600",
      },
      {
        label: "Closed calls",
        href: "/dashboard/closed-calls",
        icon: CheckCircle2,
        gradient: "from-emerald-500 to-teal-600",
      },
      {
        label: "Pending activity",
        href: "/dashboard/pending-activity",
        icon: ClipboardList,
        gradient: "from-indigo-500 to-violet-600",
      },
      {
        label: "Closed activity",
        href: "/dashboard/closed-activity",
        icon: ClipboardCheck,
        gradient: "from-emerald-500 to-teal-600",
      },
      {
        label: "PRF/OB list",
        href: "/dashboard/prf-ob-admin",
        icon: ClipboardList,
        gradient: "from-violet-500 to-purple-600",
      },
      {
        label: "Closed PRF/OB",
        href: "/dashboard/prf-ob-closed",
        icon: ClipboardCheck,
        gradient: "from-emerald-500 to-teal-600",
      },
      {
        label: "Non salable list",
        href: "/dashboard/non-salable-admin",
        icon: ListX,
        gradient: "from-amber-500 to-orange-600",
      },
      {
        label: "Salables",
        href: "/dashboard/salables",
        icon: PackageCheck,
        gradient: "from-teal-500 to-cyan-600",
      },
      {
        label: "BIR list",
        href: "/dashboard/bir-admin",
        icon: FileSpreadsheet,
        gradient: "from-indigo-500 to-violet-600",
      },
      {
        label: "Closed BIR",
        href: "/dashboard/bir-closed-admin",
        icon: ClipboardCheck,
        gradient: "from-sky-500 to-teal-600",
      },
    ],
  },
];

export function navSectionsForRole(role: string | null | undefined): NavSection[] {
  const normalized = (role ?? "").toUpperCase();
  if (normalized === "ENGINEER") return engineerNavSections;
  if (normalized === "FQC") return fqcNavSections;
  if (normalized === "SERVICE_COORDINATOR") return serviceCoordinatorNavSections;
  if (normalized === "VICE_CHANCELLOR") return viceChancellorNavSections;
  if (normalized === "PRODUCT_SUPPORT" || normalized === "REPAIR_TEAM") {
    return allowedNavSectionsForRole(normalized, dashboardNavSections);
  }
  if (normalized && normalized !== "ADMIN") return allowedNavSectionsForRole(normalized, dashboardNavSections);
  return dashboardNavSections;
}

/** Flat list of all items (e.g. active state checks) */
export function allNavItems(): NavItem[] {
  return dashboardNavSections.flatMap((s) => s.items);
}

function allowedNavSectionsForRole(role: string, sections: NavSection[]): NavSection[] {
  return sections
    .map((section) => ({
      ...section,
      items: section.items.filter((item) => isAllowedDashboardPath(role, item.href)),
    }))
    .filter((section) => section.items.length > 0);
}
