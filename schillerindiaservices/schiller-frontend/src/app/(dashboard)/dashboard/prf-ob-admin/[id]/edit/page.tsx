"use client";

import Link from "next/link";
import { useParams } from "next/navigation";
import { ClipboardList } from "lucide-react";

/**
 * Placeholder for legacy PRFOBController?action=edit — full form migration can replace this route.
 */
export default function PrfObEditPlaceholderPage() {
  const params = useParams();
  const id = params?.id;

  return (
    <div className="max-w-lg space-y-6">
      <div className="flex items-center gap-2">
        <ClipboardList className="w-8 h-8 text-violet-600" />
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Edit PRF/OB</h1>
      </div>
      <p className="text-slate-600 text-sm">
        Record id: <span className="font-mono text-slate-800">{String(id)}</span>. The legacy editor (
        <span className="font-mono text-xs">PrfOb_Update.jsp</span> /{" "}
        <span className="font-mono text-xs">PRFOBController</span>) is not migrated yet.
      </p>
      <Link
        href="/dashboard/prf-ob-admin"
        className="inline-flex text-sm font-medium text-violet-700 hover:text-violet-900 underline"
      >
        ← Back to PRF/OB list
      </Link>
    </div>
  );
}
