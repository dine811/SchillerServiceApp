"use client";

import { useEffect, useState, useMemo } from "react";
import { DealerService, Dealer } from "@/services/dealer-service";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Plus, Loader2, Edit, Trash, Search, MapPin, Mail, Phone, Users } from "lucide-react";
import { DealerSheet } from "./dealer-sheet";
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
import { RegionService, Region } from "@/services/region-service";

export default function DealersPage() {
  const [dealers, setDealers] = useState<Dealer[]>([]);
  const [regions, setRegions] = useState<Region[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  
  const [searchQuery, setSearchQuery] = useState("");
  const [page, setPage] = useState(0);
  const itemsPerPage = 10;

  // Sheet state
  const [sheetOpen, setSheetOpen] = useState(false);
  const [selectedDealer, setSelectedDealer] = useState<Dealer | null>(null);

  // Delete Dialog state
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [dealerToDelete, setDealerToDelete] = useState<number | null>(null);
  const [deleteLoading, setDeleteLoading] = useState(false);

  const loadData = async () => {
    setLoading(true);
    try {
      const [dealersData, regionsData] = await Promise.all([
        DealerService.getDealers(),
        RegionService.getRegions()
      ]);
      setDealers(dealersData);
      setRegions(regionsData);
      setError("");
    } catch (err: any) {
      setError(err.message || "Failed to load dealers");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const getRegionName = (regionId?: number) => {
    if (!regionId) return "Unknown Region";
    const region = regions.find(r => r.regionId === regionId);
    return region ? region.regionName : "Unknown Region";
  };

  const filteredDealers = useMemo(() => {
    if (!searchQuery) return dealers;
    const lower = searchQuery.toLowerCase();
    return dealers.filter(d => 
      d.dealerName?.toLowerCase().includes(lower) || 
      d.dealerMail?.toLowerCase().includes(lower) ||
      d.dealerPhone?.toLowerCase().includes(lower) ||
      d.dealerAddress?.toLowerCase().includes(lower)
    );
  }, [dealers, searchQuery]);

  useEffect(() => {
    setPage(0);
  }, [searchQuery]);

  const totalPages = Math.ceil(filteredDealers.length / itemsPerPage);
  const paginatedDealers = filteredDealers.slice(page * itemsPerPage, (page + 1) * itemsPerPage);

  const handleAddClick = () => {
    setSelectedDealer(null);
    setSheetOpen(true);
  };

  const handleEditClick = (dealer: Dealer) => {
    setSelectedDealer(dealer);
    setSheetOpen(true);
  };

  const handleConfirmDelete = async () => {
    if (dealerToDelete === null) return;
    setDeleteLoading(true);
    try {
      await DealerService.deleteDealer(dealerToDelete);
      loadData();
      setDeleteDialogOpen(false);
      setDealerToDelete(null);
    } catch (err: any) {
      alert(err.message || "Failed to delete dealer. It may have associated records.");
    } finally {
      setDeleteLoading(false);
    }
  };

  const onSheetSuccess = () => {
    loadData();
  };

  return (
    <div className="space-y-4 animate-in fade-in duration-500 p-2 sm:p-4">
      
      {/* Header & Toolbar */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 bg-white px-5 py-3.5 rounded-xl shadow-sm border border-slate-100">
        <div>
          <h1 className="text-xl font-extrabold tracking-tight bg-clip-text text-transparent bg-gradient-to-r from-slate-900 to-slate-600">
            Dealers Directory
          </h1>
          <p className="text-slate-500 mt-0.5 text-xs font-medium">
            Manage partners, their contact information, and assigned regions.
          </p>
        </div>
        
        <div className="flex flex-col sm:flex-row items-center gap-3 w-full sm:w-auto">
          <div className="relative w-full sm:w-64">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-slate-400" />
            <Input 
              placeholder="Search dealers..." 
              className="pl-9 h-9 text-sm bg-slate-50 border-slate-200 rounded-lg focus-visible:ring-indigo-500 transition-all w-full"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
          <Button 
            onClick={handleAddClick} 
            className="w-full sm:w-auto gap-2 bg-gradient-to-r from-indigo-600 to-violet-600 hover:from-indigo-700 hover:to-violet-700 text-white shadow-sm shadow-indigo-500/20 h-9 px-4 rounded-lg font-medium text-sm transition-all hover:scale-[1.02]"
          >
            <Plus className="h-4 w-4" /> Add Dealer
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
          <div className="overflow-x-auto">
            <table className="w-full text-[13px] text-left">
              <thead className="text-xs text-slate-500 bg-slate-50/80 uppercase font-bold tracking-wider border-b border-slate-100">
                <tr>
                  <th className="px-6 py-5 rounded-tl-xl">Partner</th>
                  <th className="px-6 py-5">Contact Details</th>
                  <th className="px-6 py-5">Territory</th>
                  <th className="px-6 py-5 text-center rounded-tr-xl">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {loading ? (
                  <tr>
                    <td colSpan={4} className="h-48 text-center">
                      <div className="flex flex-col items-center justify-center gap-3">
                        <Loader2 className="h-8 w-8 animate-spin text-indigo-500" />
                        <span className="text-slate-500 font-medium">Loading dealers data...</span>
                      </div>
                    </td>
                  </tr>
                ) : filteredDealers.length === 0 ? (
                  <tr>
                    <td colSpan={4} className="h-48 text-center">
                      <div className="flex flex-col items-center justify-center gap-2">
                        <div className="h-16 w-16 rounded-full bg-slate-50 flex items-center justify-center mb-2">
                          <Search className="h-6 w-6 text-slate-400" />
                        </div>
                        <p className="text-slate-600 font-semibold text-lg">No dealers found.</p>
                        <p className="text-slate-400">Try adjusting your search criteria.</p>
                      </div>
                    </td>
                  </tr>
                ) : (
                  paginatedDealers.map((dealer) => {
                    return (
                      <tr key={dealer.dealerId} className="hover:bg-slate-50/80 transition-colors group">
                        
                        {/* Name */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center gap-3">
                            <div className="h-10 w-10 rounded-xl bg-indigo-50 flex items-center justify-center shrink-0">
                              <Users className="h-5 w-5 text-indigo-600" />
                            </div>
                            <div>
                              <div className="font-bold text-slate-900 text-[13px]">{dealer.dealerName}</div>
                              <div className="text-slate-500 font-medium mt-0.5 text-xs">ID #{dealer.dealerId}</div>
                            </div>
                          </div>
                        </td>

                        {/* Contact Details */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex flex-col gap-1.5 justify-center">
                            {dealer.dealerMail ? (
                              <div className="flex items-center text-slate-600 text-[11px] font-medium bg-slate-50 border border-slate-100 rounded-md px-2 py-0.5 w-fit">
                                <Mail className="w-3 h-3 mr-1.5 text-slate-400" />
                                {dealer.dealerMail}
                              </div>
                            ) : null}
                            
                            {dealer.dealerPhone ? (
                              <div className="flex items-center text-slate-600 text-[11px] font-medium bg-slate-50 border border-slate-100 rounded-md px-2 py-0.5 w-fit">
                                <Phone className="w-3 h-3 mr-1.5 text-slate-400" />
                                {dealer.dealerPhone}
                              </div>
                            ) : null}

                            {dealer.dealerAddress ? (
                              <div className="flex items-center text-slate-500 text-[11px] mt-0.5 w-64 truncate">
                                <MapPin className="w-3 h-3 mr-1.5 text-slate-300 shrink-0" />
                                <span className="truncate">{dealer.dealerAddress}</span>
                              </div>
                            ) : null}
                          </div>
                        </td>

                        {/* Region */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <Badge className="bg-emerald-50 text-emerald-700 hover:bg-emerald-100 border-emerald-100 px-3 py-1 shadow-sm font-semibold text-xs rounded-full">
                              {getRegionName(dealer.regionId)}
                          </Badge>
                        </td>

                        {/* Actions */}
                        <td className="px-6 py-4 whitespace-nowrap text-center">
                          <div className="flex items-center justify-center gap-2 text-slate-500">
                            <Button 
                              variant="ghost" 
                              size="icon" 
                              onClick={() => handleEditClick(dealer)}
                              className="h-8 w-8 text-indigo-600 hover:text-indigo-700 hover:bg-indigo-50 rounded-lg"
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <Button 
                              variant="ghost" 
                              size="icon" 
                              onClick={() => {
                                setDealerToDelete(dealer.dealerId || null);
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
      <DealerSheet
        open={sheetOpen}
        onOpenChange={setSheetOpen}
        dealer={selectedDealer}
        regions={regions}
        onSuccess={onSheetSuccess}
      />

      {/* Delete Confirmation Alert */}
      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent className="rounded-2xl sm:max-w-md">
          <AlertDialogHeader>
            <AlertDialogTitle className="text-xl text-slate-800">Delete Dealer?</AlertDialogTitle>
            <AlertDialogDescription className="text-slate-500 font-medium pt-2">
              This action cannot be undone. This will permanently delete the dealer information.
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
