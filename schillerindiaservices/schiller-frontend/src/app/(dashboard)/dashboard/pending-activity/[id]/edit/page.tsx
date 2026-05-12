"use client";

import { useParams, useSearchParams } from "next/navigation";
import { PendingActivityForm } from "@/components/pending-activity/pending-activity-form";

export default function EditPendingActivityPage() {
  const params = useParams<{ id: string }>();
  const searchParams = useSearchParams();
  const id = Number(params?.id);
  const source = (searchParams.get("source") || "").toLowerCase();
  const fromClosed = source === "closed-activity";
  const backHref = fromClosed ? "/dashboard/closed-activity" : "/dashboard/pending-activity";
  const backLabel = fromClosed ? "Back to closed activity" : "Back to pending activity";
  const heading = fromClosed ? "Update closed activity" : "Update pending activity";

  if (!Number.isFinite(id) || id <= 0) {
    return <div className="text-sm text-red-600">Invalid pending activity id.</div>;
  }

  return <PendingActivityForm mode="edit" recordId={id} heading={heading} backHref={backHref} backLabel={backLabel} />;
}
