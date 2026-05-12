"use client";

import { useState, useEffect } from "react";
import { DivisionService, Division, Model } from "@/services/division-service";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Loader2, Plus, Trash2, Box } from "lucide-react";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetDescription,
} from "@/components/ui/sheet";

interface DivisionSheetProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  division: Division | null;
  onSuccess: () => void;
}

export function DivisionSheet({ open, onOpenChange, division, onSuccess }: DivisionSheetProps) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  
  const [formData, setFormData] = useState({
    division: "",
    divisionName: "",
    models: [] as Model[]
  });

  const [selectedModels, setSelectedModels] = useState<Set<number>>(new Set());

  useEffect(() => {
    if (open) {
      if (division) {
        setFormData({
          division: division.division || "",
          divisionName: division.divisionName || "",
          models: Array.isArray(division.models) 
            ? division.models.map(m => ({ ...m })) 
            : []
        });
      } else {
        setFormData({
          division: "",
          divisionName: "",
          models: []
        });
      }
      setSelectedModels(new Set());
      setError("");
    }
  }, [open, division]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleModelChange = (index: number, field: keyof Model, value: string) => {
    const updatedModels = [...formData.models];
    updatedModels[index] = { ...updatedModels[index], [field]: value };
    setFormData({ ...formData, models: updatedModels });
  };

  const addModelRow = () => {
    // Top insertion
    setFormData({
      ...formData,
      models: [{ modelName: "", modelDesc: "", supName: "" }, ...formData.models]
    });
    const nextSelected = new Set<number>();
    selectedModels.forEach(i => nextSelected.add(i + 1));
    setSelectedModels(nextSelected);
  };

  const removeModelRow = (index: number) => {
    const updatedModels = [...formData.models];
    updatedModels.splice(index, 1);
    
    const nextSelected = new Set<number>();
    selectedModels.forEach(i => {
      if (i < index) nextSelected.add(i);
      else if (i > index) nextSelected.add(i - 1);
    });
    
    setFormData({ ...formData, models: updatedModels });
    setSelectedModels(nextSelected);
  };

  const removeSelectedModels = () => {
    const remainingModels = formData.models.filter((_, idx) => !selectedModels.has(idx));
    setFormData({ ...formData, models: remainingModels });
    setSelectedModels(new Set());
  };

  const toggleModelSelection = (index: number) => {
    const nextSelected = new Set(selectedModels);
    if (nextSelected.has(index)) {
      nextSelected.delete(index);
    } else {
      nextSelected.add(index);
    }
    setSelectedModels(nextSelected);
  };

  const toggleAllSelection = () => {
    if (selectedModels.size === formData.models.length && formData.models.length > 0) {
      setSelectedModels(new Set());
    } else {
      setSelectedModels(new Set(formData.models.map((_, i) => i)));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      if (division) {
        await DivisionService.updateDivision(division.productId, formData);
      } else {
        await DivisionService.createDivision(formData);
      }
      onSuccess();
      onOpenChange(false);
    } catch (err: any) {
      setError(err.message || "Failed to save division");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Sheet open={open} onOpenChange={onOpenChange}>
      <SheetContent 
        className="overflow-y-auto !max-w-[1000px] w-[90vw] sm:w-[1000px] border-l-0 sm:border-l sm:rounded-l-2xl shadow-2xl p-0 right-0 sm:right-2 sm:my-2 sm:h-[calc(100vh-16px)]"
      >
        <div className="flex flex-col h-full bg-slate-50">
          <div className="px-6 py-6 border-b border-slate-100 bg-white sticky top-0 z-10">
            <SheetHeader>
              <SheetTitle className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-slate-900 to-slate-600">
                {division ? "Edit Division" : "New Division"}
              </SheetTitle>
              <SheetDescription className="text-slate-500 font-medium pt-1">
                {division ? "Update the details and configured models for this division." : "Create a new division and configure its supported models."}
              </SheetDescription>
            </SheetHeader>
          </div>

          <div className="p-6 flex-1">
            <form id="division-form" onSubmit={handleSubmit} className="space-y-8">
              {error && (
                <div className="bg-rose-50 border border-rose-100 text-rose-600 p-4 rounded-xl flex items-center gap-3 animate-in slide-in-from-top-2">
                  <div className="h-8 w-8 rounded-full bg-rose-100 flex items-center justify-center shrink-0">
                    <span className="font-bold">!</span>
                  </div>
                  <p className="font-medium text-sm">{error}</p>
                </div>
              )}

              {/* Division Section */}
              <div className="bg-white p-5 rounded-2xl border border-slate-100 shadow-sm space-y-4">
                <div className="flex items-center gap-2 pb-2 border-b border-slate-50">
                  <div className="h-8 w-8 rounded-lg bg-indigo-50 flex items-center justify-center text-indigo-600">
                    <Box className="h-4 w-4" />
                  </div>
                  <h3 className="font-semibold text-slate-800">Division Details</h3>
                </div>
                
                <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
                  <div className="space-y-2">
                    <Label htmlFor="division" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Division Identifier</Label>
                    <Input
                      id="division"
                      name="division"
                      value={formData.division}
                      onChange={handleChange}
                      placeholder="e.g. PATIENT MONITORS"
                      required
                      className="h-10 border-slate-200 focus-visible:ring-indigo-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="divisionName" className="text-slate-700 font-semibold text-xs uppercase tracking-wider">Description</Label>
                    <Input
                      id="divisionName"
                      name="divisionName"
                      value={formData.divisionName}
                      onChange={handleChange}
                      placeholder="e.g. MONITORS"
                      className="h-10 border-slate-200 focus-visible:ring-indigo-500 bg-slate-50 focus:bg-white transition-colors"
                    />
                  </div>
                </div>
              </div>

              {/* Models Section */}
              <div className="bg-white p-5 rounded-2xl border border-slate-100 shadow-sm space-y-4">
                 <div className="flex items-center justify-between pb-2 border-b border-slate-50">
                  <div className="flex items-center gap-2">
                    <div className="h-8 w-8 rounded-lg bg-violet-50 flex items-center justify-center text-violet-600">
                      <Box className="h-4 w-4" />
                    </div>
                    <div>
                      <h3 className="font-semibold text-slate-800">Coupled Models</h3>
                      <p className="text-[11px] text-slate-500 font-medium">Configure distinct models belonging to this Division.</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-2">
                    {selectedModels.size > 0 && (
                      <Button 
                        type="button" 
                        onClick={removeSelectedModels}
                        variant="destructive" 
                        size="sm"
                        className="h-8 gap-1.5"
                      >
                        <Trash2 className="h-3.5 w-3.5" />
                        Delete ({selectedModels.size}) Selected
                      </Button>
                    )}
                    <Button 
                      type="button" 
                      onClick={addModelRow}
                      variant="outline" 
                      size="sm"
                      className="h-8 gap-1.5 border-indigo-200 text-indigo-700 bg-indigo-50 hover:bg-indigo-100 hover:text-indigo-800"
                    >
                      <Plus className="h-3.5 w-3.5" />
                      Add Model
                    </Button>
                  </div>
                </div>

                <div className="space-y-2 pt-2">
                  {formData.models.length === 0 ? (
                    <div className="text-center py-8 rounded-xl border border-dashed border-slate-200 bg-slate-50">
                      <p className="text-slate-500 font-medium text-sm">No models registered.</p>
                    </div>
                  ) : (
                    <>
                      {/* Grid Header */}
                      <div className="grid grid-cols-12 gap-3 px-2 pb-1 text-[10px] text-slate-500 font-bold uppercase tracking-wider items-center">
                        <div className="col-span-1 flex justify-center">
                          <input 
                            type="checkbox" 
                            className="w-3.5 h-3.5 rounded border-slate-300 text-indigo-600 focus:ring-indigo-600 cursor-pointer"
                            checked={formData.models.length > 0 && selectedModels.size === formData.models.length}
                            onChange={toggleAllSelection}
                            title="Select All"
                          />
                        </div>
                        <div className="col-span-3">Model Name</div>
                        <div className="col-span-3">Supplier Name</div>
                        <div className="col-span-4">Description</div>
                        <div className="col-span-1 text-center">Act</div>
                      </div>
                      
                      {/* Grid Rows */}
                      {formData.models.map((model, index) => {
                        const isSelected = selectedModels.has(index);
                        return (
                          <div 
                            key={index} 
                            className={`grid grid-cols-12 gap-3 items-center px-2 py-1.5 rounded-lg transition-colors border ${
                              isSelected ? 'bg-indigo-50/50 border-indigo-200' : 'bg-transparent border-transparent hover:bg-slate-50 hover:border-slate-100'
                            }`}
                          >
                            <div className="col-span-1 flex justify-center">
                              <input 
                                type="checkbox" 
                                className="w-4 h-4 rounded border-slate-300 text-indigo-600 focus:ring-indigo-600 cursor-pointer transition-all"
                                checked={isSelected}
                                onChange={() => toggleModelSelection(index)}
                              />
                            </div>
                            <div className="col-span-3">
                              <Input
                                value={model.modelName}
                                onChange={(e) => handleModelChange(index, "modelName", e.target.value)}
                                placeholder="Model Name"
                                required
                                className={`h-9 text-sm ${isSelected ? 'bg-white' : 'bg-white'}`}
                              />
                            </div>
                            <div className="col-span-3">
                              <Input
                                value={model.supName}
                                onChange={(e) => handleModelChange(index, "supName", e.target.value)}
                                placeholder="Supplier Name"
                                required
                                className={`h-9 text-sm ${isSelected ? 'bg-white' : 'bg-white'}`}
                              />
                            </div>
                            <div className="col-span-4">
                              <Input
                                value={model.modelDesc}
                                onChange={(e) => handleModelChange(index, "modelDesc", e.target.value)}
                                placeholder="Description"
                                className={`h-9 text-sm ${isSelected ? 'bg-white' : 'bg-white'}`}
                              />
                            </div>
                            <div className="col-span-1 flex justify-center">
                              <Button
                                type="button"
                                variant="ghost"
                                size="icon"
                                className="h-8 w-8 text-slate-400 hover:text-rose-600 hover:bg-rose-50"
                                onClick={() => removeModelRow(index)}
                                title="Remove Model"
                              >
                                <Trash2 className="h-4 w-4" />
                              </Button>
                            </div>
                          </div>
                        );
                      })}
                    </>
                  )}
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
                form="division-form" 
                disabled={loading}
                className="h-10 px-6 rounded-xl bg-gradient-to-r from-indigo-600 to-violet-600 hover:from-indigo-700 hover:to-violet-700 text-white shadow-md shadow-indigo-500/20 font-medium transition-all"
              >
                {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                {division ? "Save Changes" : "Create Division"}
              </Button>
            </div>
          </div>
        </div>
      </SheetContent>
    </Sheet>
  );
}
