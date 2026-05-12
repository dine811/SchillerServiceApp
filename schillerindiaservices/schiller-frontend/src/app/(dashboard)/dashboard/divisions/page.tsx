"use client";

import { useEffect, useState, useMemo } from "react";
import { DivisionService, Division } from "@/services/division-service";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Plus, Loader2, Edit, Trash, Search, Layers, Box } from "lucide-react";
import { DivisionSheet } from "./division-sheet";
import { cn } from "@/lib/utils";
import { Input } from "@/components/ui/input";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";



export default function DivisionsPage() {
  const [divisions, setDivisions] = useState<Division[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  
  const [searchQuery, setSearchQuery] = useState("");
  const [page, setPage] = useState(0);
  const itemsPerPage = 10;

  // Sheet state
  const [sheetOpen, setSheetOpen] = useState(false);
  const [selectedDivision, setSelectedDivision] = useState<Division | null>(null);

  // Delete Dialog state
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [divisionToDelete, setDivisionToDelete] = useState<number | null>(null);
  const [deleteLoading, setDeleteLoading] = useState(false);

  const loadData = async () => {
    setLoading(true);
    try {
      const data = await DivisionService.getDivisions();
      setDivisions(data);
      setError("");
    } catch (err: any) {
      setError(err.message || "Failed to load divisions");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const filteredDivisions = useMemo(() => {
    if (!searchQuery) return divisions;
    const lower = searchQuery.toLowerCase();
    return divisions.filter(d => 
      d.division?.toLowerCase().includes(lower) || 
      d.divisionName?.toLowerCase().includes(lower)
    );
  }, [divisions, searchQuery]);

  useEffect(() => {
    setPage(0);
  }, [searchQuery]);

  const totalPages = Math.ceil(filteredDivisions.length / itemsPerPage);
  const paginatedDivisions = filteredDivisions.slice(page * itemsPerPage, (page + 1) * itemsPerPage);

  const handleAddClick = () => {
    setSelectedDivision(null);
    setSheetOpen(true);
  };

  const handleEditClick = (division: Division) => {
    setSelectedDivision(division);
    setSheetOpen(true);
  };

  const handleConfirmDelete = async () => {
    if (divisionToDelete === null) return;
    setDeleteLoading(true);
    try {
      await DivisionService.deleteDivision(divisionToDelete);
      loadData();
      setDeleteDialogOpen(false);
      setDivisionToDelete(null);
    } catch (err: any) {
      alert(err.message || "Failed to delete division. It may have associated records.");
    } finally {
      setDeleteLoading(false);
    }
  };

  const onSheetSuccess = () => {
    loadData();
  };

  return (
    <div className="space-y-4 animate-in fade-in duration-500 p-2 sm:p-4">
      
      {/* Compact Premium Header & Toolbar */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 bg-white px-5 py-3.5 rounded-xl shadow-sm border border-slate-100">
        <div>
          <h1 className="text-xl font-extrabold tracking-tight bg-clip-text text-transparent bg-gradient-to-r from-slate-900 to-slate-600">
            Divisions & Models
          </h1>
          <p className="text-slate-500 mt-0.5 text-xs font-medium">
            Manage product divisions and their corresponding nested models.
          </p>
        </div>
        
        <div className="flex flex-col sm:flex-row items-center gap-3 w-full sm:w-auto">
          <div className="relative w-full sm:w-64">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-slate-400" />
            <Input 
              placeholder="Search divisions..." 
              className="pl-9 h-9 text-sm bg-slate-50 border-slate-200 rounded-lg focus-visible:ring-indigo-500 transition-all w-full"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
          <Button 
            onClick={handleAddClick} 
            className="w-full sm:w-auto gap-2 bg-gradient-to-r from-indigo-600 to-violet-600 hover:from-indigo-700 hover:to-violet-700 text-white shadow-sm shadow-indigo-500/20 h-9 px-4 rounded-lg font-medium text-sm transition-all hover:scale-[1.02]"
          >
            <Plus className="h-4 w-4" /> Add Division
          </Button>
        </div>
      </div>

      {error ? (
        <div className="bg-rose-50 text-rose-600 p-5 rounded-xl border border-rose-100 flex items-center gap-3">
          <div className="h-10 w-10 rounded-full bg-rose-100 flex items-center justify-center shrink-0">
            <span className="font-bold">!</span>
          </div>
          <p className="font-medium">{error}</p>
        </div>
      ) : (
        <div className="bg-white border border-slate-100 rounded-2xl shadow-sm overflow-hidden">
          {/* Table Header Wrapper */}
          <div className="overflow-x-auto">
            <table className="w-full text-[13px] text-left">
              <thead className="text-xs text-slate-500 bg-slate-50/80 uppercase font-bold tracking-wider border-b border-slate-100">
                <tr>
                  <th className="px-6 py-5 rounded-tl-xl">Division</th>
                  <th className="px-6 py-5">Summary</th>
                  <th className="px-6 py-5">Nested Models</th>
                  <th className="px-6 py-5 text-center rounded-tr-xl">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {loading ? (
                  <tr>
                    <td colSpan={4} className="h-48 text-center">
                      <div className="flex flex-col items-center justify-center gap-3">
                        <Loader2 className="h-8 w-8 animate-spin text-indigo-500" />
                        <span className="text-slate-500 font-medium">Loading divisions data...</span>
                      </div>
                    </td>
                  </tr>
                ) : filteredDivisions.length === 0 ? (
                  <tr>
                    <td colSpan={4} className="h-48 text-center">
                      <div className="flex flex-col items-center justify-center gap-2">
                        <div className="h-16 w-16 rounded-full bg-slate-50 flex items-center justify-center mb-2">
                          <Search className="h-6 w-6 text-slate-400" />
                        </div>
                        <p className="text-slate-600 font-semibold text-lg">No divisions found.</p>
                        <p className="text-slate-400">Try adjusting your search criteria.</p>
                      </div>
                    </td>
                  </tr>
                ) : (
                  paginatedDivisions.map((div) => {
                    return (
                      <tr key={div.productId} className="hover:bg-slate-50/80 transition-colors group">
                        
                        {/* Name */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div>
                            <div className="font-bold text-slate-900 text-[13px]">{div.division}</div>
                          </div>
                        </td>

                        {/* Description */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex flex-col items-start gap-1.5">
                             <div className="flex items-center text-slate-600 text-xs font-medium">
                              <Layers className="w-3.5 h-3.5 mr-2 text-slate-400" />
                              {div.divisionName || <span className="text-slate-400 italic">No description</span>}
                            </div>
                          </div>
                        </td>

                        {/* Models Count */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <Badge className="bg-indigo-50 text-indigo-700 hover:bg-indigo-100 border-indigo-100 px-3 py-1 shadow-sm font-semibold text-xs rounded-full">
                              <Box className="w-3.5 h-3.5 mr-1.5 inline-block" />
                              {div.models?.length || 0} Models Configured
                          </Badge>
                        </td>

                        {/* Actions */}
                        <td className="px-6 py-4 whitespace-nowrap text-center">
                          <div className="flex items-center justify-center gap-2 text-slate-500">
                            <Button 
                              variant="ghost" 
                              size="icon" 
                              onClick={() => handleEditClick(div)}
                              className="h-8 w-8 text-indigo-600 hover:text-indigo-700 hover:bg-indigo-50 rounded-lg"
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <Button 
                              variant="ghost" 
                              size="icon" 
                              onClick={() => {
                                setDivisionToDelete(div.productId);
                                setDeleteDialogOpen(true);
                              }}
                              className="h-8 w-8 text-rose-500 hover:text-rose-600 hover:bg-rose-50 rounded-lg"
                            >
                              <Trash className="h-4 w-4" />
                            </Button>
                          </div>
                        </td>
                      </tr>
                    );
                  })
                )}
              </tbody>
            </table>
          </div>
          
          {/* Pagination Controls */}
          {!loading && totalPages > 1 && (
             <div className="border-t border-slate-100 p-5 flex items-center justify-between bg-slate-50/50">
               <p className="text-sm font-medium text-slate-500">
                 Showing page <span className="text-slate-900 font-bold">{page + 1}</span> of <span className="text-slate-900 font-bold">{totalPages}</span>
               </p>
               <div className="flex gap-2">
                 <Button
                   variant="outline"
                   className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-indigo-600 transition-colors disabled:opacity-50"
                   disabled={page === 0}
                   onClick={() => setPage(page - 1)}
                 >
                   Previous
                 </Button>

                 <div className="hidden sm:flex items-center gap-1 mx-2">
                   {Array.from({ length: totalPages }, (_, i) => i).map((pageNum) => (
                     <Button
                       key={pageNum}
                       variant={pageNum === page ? "default" : "outline"}
                       className={cn(
                         "h-9 w-9 p-0 rounded-lg shadow-sm font-medium transition-colors",
                         pageNum === page 
                           ? "bg-indigo-600 text-white hover:bg-indigo-700 hover:text-white border-transparent"
                           : "bg-white border-slate-200 text-slate-600 hover:bg-slate-50 hover:text-indigo-600"
                       )}
                       onClick={() => setPage(pageNum)}
                     >
                       {pageNum + 1}
                     </Button>
                   ))}
                 </div>

                 <Button
                   variant="outline"
                   className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-indigo-600 transition-colors disabled:opacity-50"
                   disabled={page >= totalPages - 1}
                   onClick={() => setPage(page + 1)}
                 >
                   Next
                 </Button>
               </div>
             </div>
          )}
        </div>
      )}

      {/* Slide-out Edit/Create Form */}
      <DivisionSheet
        open={sheetOpen}
        onOpenChange={setSheetOpen}
        division={selectedDivision}
        onSuccess={onSheetSuccess}
      />

      {/* Delete Confirmation Alert */}
      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent className="rounded-2xl sm:max-w-md">
          <AlertDialogHeader>
            <AlertDialogTitle className="text-xl text-slate-800">Delete Division?</AlertDialogTitle>
            <AlertDialogDescription className="text-slate-500 font-medium pt-2">
              This action cannot be undone. This will permanently delete the division and its related nested models.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter className="mt-4 gap-2 sm:gap-0">
            <AlertDialogCancel disabled={deleteLoading} className="rounded-xl border-slate-200">Cancel</AlertDialogCancel>
            <AlertDialogAction 
              onClick={(e) => {
                e.preventDefault();
                handleConfirmDelete();
              }}
              disabled={deleteLoading}
              className="bg-rose-600 hover:bg-rose-700 text-white rounded-xl shadow-sm shadow-rose-200"
            >
              {deleteLoading ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : <Trash className="h-4 w-4 mr-2" />}
              Delete
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}
