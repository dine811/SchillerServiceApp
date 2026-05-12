"use client";

import { Loader2, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogMedia,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";

type Props = {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onConfirm: () => void | Promise<void>;
  loading?: boolean;
};

export function DeletePrfobDialog({ open, onOpenChange, onConfirm, loading = false }: Props) {
  return (
    <AlertDialog open={open} onOpenChange={onOpenChange}>
      <AlertDialogContent className="sm:max-w-md border-slate-200 shadow-lg">
        <AlertDialogHeader className="sm:text-left">
          <AlertDialogMedia className="bg-red-50 text-red-600 ring-1 ring-red-100">
            <Trash2 className="size-5" aria-hidden />
          </AlertDialogMedia>
          <AlertDialogTitle className="text-lg font-semibold text-slate-900">
            Confirm deletion
          </AlertDialogTitle>
          <AlertDialogDescription className="text-slate-600 text-sm leading-relaxed">
            Are you sure you want to delete this PRF/OB record? This cannot be undone.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter className="gap-2 sm:gap-2">
          <AlertDialogCancel disabled={loading} className="rounded-xl border-slate-200">
            Cancel
          </AlertDialogCancel>
          <Button
            type="button"
            variant="destructive"
            className="rounded-xl"
            disabled={loading}
            onClick={() => {
              void onConfirm();
            }}
          >
            {loading ? (
              <>
                <Loader2 className="size-4 animate-spin mr-2" aria-hidden />
                Deleting…
              </>
            ) : (
              "Delete"
            )}
          </Button>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
