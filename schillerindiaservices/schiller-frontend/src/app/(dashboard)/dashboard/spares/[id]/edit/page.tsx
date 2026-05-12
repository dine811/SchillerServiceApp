"use client";

import { useParams, useSearchParams } from "next/navigation";
import SpareForm from "@/components/spares/spare-form";

export default function EditSparePage() {
  const params = useParams<{ id: string }>();
  const searchParams = useSearchParams();
  const id = Number(params?.id);
  const source = (searchParams.get("source") ?? "pending").toLowerCase();

  const fromCompleted = source === "completed";
  const backHref = fromCompleted ? "/dashboard/spares-completed" : "/dashboard/spares";
  const backLabel = fromCompleted ? "Back to Spares List Completed" : "Back to Spares List";
  const heading = fromCompleted ? "Update Completed Spares Entry" : "Update Spares Entry";

  if (!Number.isFinite(id) || id <= 0) {
    return <div className="p-4 text-sm text-red-600">Invalid spares record id.</div>;
  }

  return <SpareForm mode="edit" id={id} heading={heading} backHref={backHref} backLabel={backLabel} />;
}
