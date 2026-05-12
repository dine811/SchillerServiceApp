"use client";

import { useState, useEffect } from "react";
import { DealerService, Dealer } from "@/services/dealer-service";
import { Region } from "@/services/region-service";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Loader2, Users } from "lucide-react";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetDescription,
} from "@/components/ui/sheet";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

interface DealerSheetProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  dealer: Dealer | null;
  regions: Region[];
  onSuccess: () => void;
}

export function DealerSheet({ open, onOpenChange, dealer, regions, onSuccess }: DealerSheetProps) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  
  const [formData, setFormData] = useState({
    dealerName: "",
    dealerAddress: "",
    dealerMail: "",
    dealerPhone: "",
    regionId: 0
  });

  useEffect(() => {
    if (open) {
      if (dealer) {
        setFormData({
          dealerName: dealer.dealerName || "",
          dealerAddress: dealer.dealerAddress || "",
          dealerMail: dealer.dealerMail || "",
          dealerPhone: dealer.dealerPhone || "",
          regionId: dealer.regionId || 0
        });
      } else {
        setFormData({
          dealerName: "",
          dealerAddress: "",
          dealerMail: "",
          dealerPhone: "",
          regionId: 0
        });
      }
      setError("");
    }
  }, [open, dealer]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      if (dealer && dealer.dealerId) {
        await DealerService.updateDealer(dealer.dealerId, formData);
      } else {
        await DealerService.createDealer(formData);
      }
      onSuccess();
      onOpenChange(false);
    } catch (err: any) {
      setError(err.message || "Failed to save dealer");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Sheet open={open} onOpenChange={onOpenChange}>
      <SheetContent 
        className="overflow-y-auto sm:max-w-xl w-[90vw] sm:w-full border-l-0 sm:border-l sm:rounded-l-2xl shadow-2xl p-0 right-0 sm:right-2 sm:my-2 sm:h-[calc(100vh-16px)]"
      >
        <div className="flex flex-col h-full bg-slate-50">
          <div className="px-6 py-6 border-b border-slate-100 bg-white sticky top-0 z-10">
            <SheetHeader>
              <SheetTitle className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-slate-900 to-slate-600">
                {dealer ? "Edit Dealer" : "Add Dealer"}
              </SheetTitle>
              <SheetDescription className="text-slate-500 font-medium pt-1">
                {dealer ? "Update the contact information for this partner." : "Register a new dealer partner and assign their territory."}
              </SheetDescription>
            </SheetHeader>
          </div>

          <div className="p-6 flex-1">
            <form id="dealer-form" onSubmit={handleSubmit} className="space-y-6">
              {error && (
                <div className="bg-rose-50 border border-rose-100 text-rose-600 p-4 rounded-xl flex items-center gap-3 animate-in slide-in-from-top-2">
                  <div className="h-8 w-8 rounded-full bg-rose-100 flex items-center justify-center shrink-0">
                    <span className="font-bold">!</span>
                  </div>
                  <p className="font-medium text-sm">{error}</p>
                </div>
              )}

              {/* Identity Section */}
              <div className="bg-white p-5 rounded-2xl border border-slate-100 shadow-sm space-y-4">
                <div className="flex items-center gap-2 pb-2 border-b border-slate-50">
                  <div className="h-8 w-8 rounded-lg bg-indigo-50 flex items-center justify-center text-indigo-600">
                    <Users className="h-4 w-4" />
                  </div>
                  <h3 className="font-semibold text-slate-800">Partner Details</h3>
                </div>
                
                <div className="space-y-4 pt-1">
                  <div className="space-y-2">
                    <Label htmlFor="dealerName" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Company / Dealer Name</Label>
                    <Input
                      id="dealerName"
                      name="dealerName"
                      value={formData.dealerName}
                      onChange={handleChange}
                      placeholder="e.g. Acme Medical Supplies"
                      required
                      className="h-10 border-slate-200 focus-visible:ring-indigo-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="dealerAddress" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Physical Address</Label>
                    <Textarea
                      id="dealerAddress"
                      name="dealerAddress"
                      value={formData.dealerAddress}
                      onChange={handleChange}
                      placeholder="e.g. 123 Health Ave, Mumbai"
                      required
                      rows={2}
                      className="resize-none border-slate-200 focus-visible:ring-indigo-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>
                </div>
              </div>

              {/* Contact Section */}
              <div className="bg-white p-5 rounded-2xl border border-slate-100 shadow-sm space-y-4">
                <h3 className="font-semibold text-slate-800 border-b border-slate-50 pb-2">Contact & Operations</h3>
                
                <div className="grid grid-cols-1 md:grid-cols-2 gap-5 pt-1">
                  <div className="space-y-2">
                    <Label htmlFor="dealerMail" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Email Address</Label>
                    <Input
                      id="dealerMail"
                      name="dealerMail"
                      type="email"
                      value={formData.dealerMail}
                      onChange={handleChange}
                      placeholder="e.g. info@acmemedical.com"
                      required
                      className="h-10 border-slate-200 focus-visible:ring-indigo-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="dealerPhone" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Contact Number</Label>
                    <Input
                      id="dealerPhone"
                      name="dealerPhone"
                      value={formData.dealerPhone}
                      onChange={handleChange}
                      placeholder="e.g. +91 98765 43210"
                      required
                      className="h-10 border-slate-200 focus-visible:ring-indigo-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>
                  
                  <div className="space-y-2 md:col-span-2">
                    <Label htmlFor="regionId" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Operational Territory (Region)</Label>
                    <Select 
                      value={formData.regionId ? formData.regionId.toString() : ""} 
                      onValueChange={(val) => setFormData({...formData, regionId: parseInt(val)})}
                      required
                    >
                      <SelectTrigger className="h-10 border-slate-200 bg-slate-50 focus:ring-indigo-500">
                        <SelectValue placeholder="Select primary region" />
                      </SelectTrigger>
                      <SelectContent>
                        {regions.map((region) => (
                          <SelectItem key={region.regionId} value={region.regionId?.toString() || ""}>
                            {region.regionName}
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
                className="h-10 px-5 rounded-xl border-slate-200 text-slate-600 hover:bg-slate-50 font-medium"
              >
                Cancel
              </Button>
              <Button 
                type="submit" 
                form="dealer-form" 
                disabled={loading}
                className="h-10 px-6 rounded-xl bg-gradient-to-r from-indigo-600 to-violet-600 hover:from-indigo-700 hover:to-violet-700 text-white shadow-md shadow-indigo-500/20 font-medium transition-all"
              >
                {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                {dealer ? "Save Changes" : "Create Dealer"}
              </Button>
            </div>
          </div>
        </div>
      </SheetContent>
    </Sheet>
  );
}
