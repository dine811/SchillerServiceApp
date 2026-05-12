"use client";

import SpareForm from "@/components/spares/spare-form";

export default function AddSparePage() {
  return (
    <SpareForm
      mode="create"
      heading="Spares Request - New Entry"
      backHref="/dashboard/spares"
      backLabel="Back to Spares List"
      defaultStatus="Pending"
    />
  );
}
