"use client";

import { useState, useEffect } from "react";
import { Sheet, SheetContent, SheetHeader, SheetTitle, SheetDescription } from "@/components/ui/sheet";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Loader2, Mail, Link as LinkIcon } from "lucide-react";
import { Automail, AutomailService } from "@/services/automail-service";
import { DivisionService, Division } from "@/services/division-service";

interface EscalationSheetProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  initialData?: Automail;
  onSuccess: () => void;
}

const REPORT_TYPES = [
  { value: "all", label: "ALL" },
  { value: "scraplist", label: "SCRAP LIST" },
  { value: "dispatchlist", label: "DISPATCH LIST" },
  { value: "stocklist", label: "STOCK LIST" },
  { value: "servicecenter", label: "SERVICE CENTER" },
  { value: "nonsalepending", label: "NON SALE PENDING" },
  { value: "joblist", label: "JOBLIST" },
  { value: "joblist_pending", label: "JOBLIST PENDING" },
  { value: "under_repair", label: "UNDER REPAIR" },
  { value: "ob_pending", label: "OB PENDING" },
  { value: "pendingfrn", label: "PENDING FRN" },
  { value: "bir_pending", label: "BIR-PENDING" },
  { value: "jobsheet_pending", label: "JOBSHEET-PENDING" },
  { value: "prf/ob_completed", label: "COMPLETED-PRF/OB" },
  { value: "prf/ob_pending", label: "PENDING-PRF/OB" },
];

export function EscalationSheet({ open, onOpenChange, initialData, onSuccess }: EscalationSheetProps) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [formData, setFormData] = useState<Partial<Automail>>({});
  const [divisions, setDivisions] = useState<Division[]>([]);

  useEffect(() => {
    if (open) {
      if (initialData) {
        setFormData(initialData);
      } else {
        setFormData({
          mailId: "",
          reportType: "all",
          division: "all"
        });
      }
      setError("");
      
      const fetchDiv = async () => {
        try {
          const div = await DivisionService.getDivisions();
          setDivisions(div);
        } catch (e) {
          console.error("Failed loading divisions", e);
        }
      };
      if (divisions.length === 0) fetchDiv();
    }
  }, [open, initialData, divisions.length]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      if (initialData?.mailIdEntryId) {
        await AutomailService.updateEscalation(initialData.mailIdEntryId, formData as Automail);
      } else {
        await AutomailService.createEscalation(formData as Automail);
      }
      onSuccess();
      onOpenChange(false);
    } catch (err: any) {
      setError(err.message || "Failed to save escalation mail entry");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Sheet open={open} onOpenChange={onOpenChange}>
      <SheetContent 
        className="overflow-y-auto sm:max-w-xl w-[90vw] sm:w-full border-l-0 sm:border-l sm:rounded-l-2xl shadow-2xl p-0 right-0 sm:my-2 sm:h-[calc(100vh-16px)] z-[200]"
      >
        <div className="flex flex-col h-full bg-slate-50">
          <div className="px-6 py-6 border-b border-slate-100 bg-white sticky top-0 z-10">
            <SheetHeader>
              <SheetTitle className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-700 to-indigo-700">
                {initialData ? "Edit Escalation Mail" : "Add Escalation Mail"}
              </SheetTitle>
              <SheetDescription className="text-slate-500 font-medium pt-1">
                Configure automatic escalation reporting mapped to an email address and division.
              </SheetDescription>
            </SheetHeader>
          </div>

          <div className="p-6 flex-1">
            <form id="escalation-form" onSubmit={handleSubmit} className="space-y-6">
              
              {error && (
                <div className="bg-red-50 border border-red-100 text-red-600 p-4 rounded-xl flex items-center gap-3 animate-in slide-in-from-top-2">
                  <div className="h-8 w-8 rounded-full bg-red-100 flex items-center justify-center shrink-0">
                    <span className="font-bold">!</span>
                  </div>
                  <p className="font-medium text-sm">{error}</p>
                </div>
              )}

              {/* Identity Section */}
              <div className="bg-white p-5 rounded-2xl border border-slate-100 shadow-sm space-y-4">
                <div className="flex items-center gap-2 pb-2 border-b border-slate-50">
                  <div className="h-8 w-8 rounded-lg bg-blue-50 flex items-center justify-center text-blue-600">
                    <Mail className="h-4 w-4" />
                  </div>
                  <h3 className="font-semibold text-slate-800">Mail Recipient</h3>
                </div>
                
                <div className="space-y-4 pt-1">
                  <div className="space-y-2">
                    <Label htmlFor="mailId" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Email Address <span className="text-red-500">*</span></Label>
                    <Input
                      id="mailId"
                      type="email"
                      placeholder="e.g. alert@schillerindia.com"
                      value={formData.mailId || ""}
                      onChange={(e) => setFormData({ ...formData, mailId: e.target.value })}
                      required
                      className="h-10 border-slate-200 focus-visible:ring-blue-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>
                </div>
              </div>

              {/* Mapping Section */}
              <div className="bg-white p-5 rounded-2xl border border-slate-100 shadow-sm space-y-4">
                <div className="flex items-center gap-2 pb-2 border-b border-slate-50">
                  <div className="h-8 w-8 rounded-lg bg-indigo-50 flex items-center justify-center text-indigo-600">
                    <LinkIcon className="h-4 w-4" />
                  </div>
                  <h3 className="font-semibold text-slate-800">Rule Triggers</h3>
                </div>
                
                <div className="grid grid-cols-1 md:grid-cols-2 gap-5 pt-1">
                  <div className="space-y-2 md:col-span-2">
                    <Label htmlFor="reportType" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Type of Report <span className="text-red-500">*</span></Label>
                    <Select 
                      value={formData.reportType || "all"} 
                      onValueChange={(val) => setFormData({ ...formData, reportType: val })}
                      required
                    >
                      <SelectTrigger className="h-10 border-slate-200 bg-slate-50 focus:ring-blue-500">
                        <SelectValue placeholder="Select Report Type" />
                      </SelectTrigger>
                      <SelectContent className="z-[250]">
                        {REPORT_TYPES.map(type => (
                          <SelectItem key={type.value} value={type.value}>{type.label}</SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>

                  <div className="space-y-2 md:col-span-2">
                    <Label htmlFor="division" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Associated Division <span className="text-red-500">*</span></Label>
                    <Select 
                      value={formData.division || "all"} 
                      onValueChange={(val) => setFormData({ ...formData, division: val })}
                      required
                    >
                      <SelectTrigger className="h-10 border-slate-200 bg-slate-50 focus:ring-blue-500">
                        <SelectValue placeholder="Select Division" />
                      </SelectTrigger>
                      <SelectContent className="z-[250] max-h-60">
                        <SelectItem value="all">ALL</SelectItem>
                        {divisions.map(div => (
                          <SelectItem key={div.productId} value={div.divisionName || div.division}>
                            {div.divisionName || div.division}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
              </div>

            </form>
          </div>

          <div className="px-6 py-5 border-t border-slate-100 bg-white sticky bottom-0 z-10 rounded-bl-2xl">
            <div className="flex justify-end gap-3">
              <Button 
                type="button" 
                variant="outline" 
                onClick={() => onOpenChange(false)}
                disabled={loading}
                className="h-10 px-5 rounded-xl border-slate-200 text-slate-600 hover:bg-slate-50 font-medium"
              >
                Cancel
              </Button>
              <Button 
                type="submit" 
                form="escalation-form" 
                disabled={loading}
                className="h-10 px-6 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white shadow-md shadow-blue-500/20 font-medium transition-all"
              >
                {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                {initialData ? "Save Changes" : "Save Entry"}
              </Button>
            </div>
          </div>
        </div>
      </SheetContent>
    </Sheet>
  );
}
