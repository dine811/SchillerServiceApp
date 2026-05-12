"use client";

import { useEffect, useState } from "react";
import { AutomailService, Automail } from "@/services/automail-service";
import { EscalationSheet } from "./escalation-sheet";
import { Button } from "@/components/ui/button";
import { Plus, Loader2, Edit, Trash, Search, Mail, Tag, MapPin, Inbox } from "lucide-react";
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

export default function EscalationsPage() {
  const [escalations, setEscalations] = useState<Automail[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  
  // Pagination and search
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [searchTerm, setSearchTerm] = useState("");
  const [searchInput, setSearchInput] = useState("");
  
  const [isSheetOpen, setIsSheetOpen] = useState(false);
  const [selectedEscalation, setSelectedEscalation] = useState<Automail | undefined>();
  
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [escalationToDelete, setEscalationToDelete] = useState<number | null>(null);
  const [deleteLoading, setDeleteLoading] = useState(false);

  const loadEscalations = async () => {
    setLoading(true);
    try {
      const data = await AutomailService.getEscalations(page, 15, searchTerm);
      setEscalations(data.content || []);
      setTotalPages(data.totalPages || 0);
      setError("");
    } catch (err) {
      console.error(err);
      setError("Failed to load escalation entries.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadEscalations();
  }, [page, searchTerm]);

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      setSearchTerm(searchInput);
      setPage(0);
    }, 400);

    return () => clearTimeout(delayDebounceFn);
  }, [searchInput]);

  const handleAdd = () => {
    setSelectedEscalation(undefined);
    setIsSheetOpen(true);
  };

  const handleEdit = (escalation: Automail) => {
    setSelectedEscalation(escalation);
    setIsSheetOpen(true);
  };

  const confirmDelete = async () => {
    if (!escalationToDelete) return;
    setDeleteLoading(true);
    try {
      await AutomailService.deleteEscalation(escalationToDelete);
      setDeleteDialogOpen(false);
      setEscalationToDelete(null);
      loadEscalations();
    } catch (error) {
      console.error("Delete failed", error);
      alert("Failed to delete entry. Make sure no dependencies exist.");
    } finally {
      setDeleteLoading(false);
    }
  };

  return (
    <div className="container mx-auto p-4 space-y-6 max-w-7xl animate-in fade-in zoom-in-95 duration-500">
      
      {/* Header section */}
      <div className="flex flex-col md:flex-row md:justify-between md:items-center gap-4 bg-white p-6 rounded-2xl shadow-sm border border-slate-100">
        <div>
          <h1 className="text-3xl font-bold tracking-tight text-slate-900">Escalation Mails</h1>
          <p className="text-slate-500 mt-1">Manage automatic alert and report distribution rules.</p>
        </div>
        <Button onClick={handleAdd} size="lg" className="bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white shadow-sm shadow-blue-500/20 h-9 px-4 rounded-lg font-medium text-sm transition-all hover:scale-[1.02]">
          <Plus className="h-4 w-4 mr-2" />
          Add Entry
        </Button>
      </div>

      {/* Main Content Area */}
      <div className="bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden flex flex-col">
        {/* Toolbar */}
        <div className="p-4 border-b border-slate-100 bg-slate-50/50 flex flex-col sm:flex-row justify-between items-center gap-4">
          <div className="relative w-full sm:max-w-sm">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 h-4 w-4" />
            <input 
              type="text" 
              placeholder="Search emails, scopes, reports..." 
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              className="w-full pl-10 pr-4 py-2 bg-white border border-slate-200 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all"
            />
          </div>
          
          <div className="text-sm text-slate-500 font-medium">
            Page {page + 1} of {Math.max(1, totalPages)}
          </div>
        </div>

        {/* Directory Listing */}
        <div className="p-6">
          {error && (
            <div className="text-red-500 bg-red-50 p-4 rounded-xl border border-red-100 flex items-center mb-6">
              <span className="font-medium">{error}</span>
            </div>
          )}

          {loading ? (
            <div className="flex flex-col justify-center items-center py-20 text-slate-500">
              <Loader2 className="h-10 w-10 animate-spin text-blue-500 mb-4" />
              <p className="font-medium animate-pulse">Loading escalation rules...</p>
            </div>
          ) : escalations.length === 0 ? (
            <div className="flex flex-col items-center justify-center py-24 text-center rounded-2xl bg-slate-50 border border-slate-100 border-dashed">
              <div className="h-16 w-16 bg-white rounded-full flex items-center justify-center shadow-sm border border-slate-100 mb-4">
                <Inbox className="h-8 w-8 text-slate-300" />
              </div>
              <h3 className="text-lg font-bold text-slate-900">No entries found</h3>
              <p className="text-slate-500 mt-1 max-w-sm">There are no automatic escalation rules defined. Click the Add Entry button to create one.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {escalations.map((esc) => (
                <div 
                  key={esc.mailIdEntryId} 
                  className="group relative bg-white border border-slate-200 rounded-2xl p-6 hover:shadow-xl hover:border-blue-500/30 transition-all duration-300 overflow-hidden"
                >
                  <div className="absolute top-0 left-0 w-1 h-full bg-slate-100 transition-colors group-hover:bg-blue-500"></div>
                  
                  <div className="flex justify-between items-start mb-4">
                    <div className="flex items-center space-x-3">
                      <div className="h-10 w-10 bg-slate-50 border border-slate-100 rounded-full flex items-center justify-center text-blue-600 shrink-0 group-hover:bg-blue-50 group-hover:border-blue-100 transition-colors">
                        <Mail className="h-5 w-5" />
                      </div>
                      <div className="max-w-[180px]">
                        <h3 className="font-bold text-slate-900 truncate" title={esc.mailId}>{esc.mailId.split('@')[0]}</h3>
                        <p className="text-xs text-slate-500 truncate" title={esc.mailId}>@{esc.mailId.split('@')[1]}</p>
                      </div>
                    </div>
                  </div>

                  <div className="space-y-3 mb-6">
                    <div className="flex items-center text-sm text-slate-600 bg-slate-50 p-2 rounded-lg">
                      <Tag className="w-4 h-4 mr-2 text-slate-400" />
                      <span className="font-medium uppercase tracking-tight text-xs">{esc.reportType}</span>
                    </div>
                    {esc.division && (
                      <div className="flex items-center text-sm text-slate-600 bg-slate-50 p-2 rounded-lg">
                        <MapPin className="w-4 h-4 mr-2 text-slate-400" />
                        <span className="font-medium truncate" title={esc.division}>{esc.division}</span>
                      </div>
                    )}
                  </div>

                  <div className="pt-4 border-t border-slate-100 flex gap-3">
                    <Button 
                      variant="outline" 
                      onClick={() => handleEdit(esc)}
                      className="flex-1 bg-blue-50 hover:bg-blue-100 border-blue-100 text-blue-700 font-medium rounded-xl transition-colors shadow-sm"
                    >
                      <Edit className="h-4 w-4 mr-2" /> Edit
                    </Button>
                    <Button 
                      variant="outline" 
                      onClick={() => {
                        setEscalationToDelete(esc.mailIdEntryId || null);
                        setDeleteDialogOpen(true);
                      }}
                      className="px-4 bg-rose-50 hover:bg-rose-100 border-rose-100 text-rose-600 hover:text-rose-700 font-medium rounded-xl transition-colors shadow-sm"
                      title="Delete Entry"
                    >
                      <Trash className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Pagination Footer */}
        {!loading && totalPages > 1 && (
             <div className="border-t border-slate-100 p-5 flex items-center justify-between bg-slate-50/50">
               <p className="text-sm font-medium text-slate-500">
                 Showing page <span className="text-slate-900 font-bold">{page + 1}</span> of <span className="text-slate-900 font-bold">{totalPages}</span>
               </p>
               <div className="flex gap-2">
                 <Button
                   variant="outline"
                   className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-blue-600 transition-colors disabled:opacity-50"
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
                       className={`h-9 w-9 p-0 rounded-lg shadow-sm font-medium transition-colors ${
                         pageNum === page 
                           ? "bg-blue-600 text-white hover:bg-blue-700 hover:text-white border-transparent"
                           : "bg-white border-slate-200 text-slate-600 hover:bg-slate-50 hover:text-blue-600"
                       }`}
                       onClick={() => setPage(pageNum)}
                     >
                       {pageNum + 1}
                     </Button>
                   ))}
                 </div>

                 <Button
                   variant="outline"
                   className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-blue-600 transition-colors disabled:opacity-50"
                   disabled={page >= totalPages - 1}
                   onClick={() => setPage(page + 1)}
                 >
                   Next
                 </Button>
               </div>
             </div>
        )}
      </div>

      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent className="rounded-2xl sm:max-w-md">
          <AlertDialogHeader>
            <AlertDialogTitle className="text-xl text-slate-800">Delete Escalation Entry?</AlertDialogTitle>
            <AlertDialogDescription className="text-slate-500 font-medium pt-2">
              This action cannot be undone. Are you sure you want to permanently remove this automatic escalation reporting rule?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter className="mt-4 gap-2 sm:gap-0">
            <AlertDialogCancel disabled={deleteLoading} className="rounded-xl border-slate-200">Cancel</AlertDialogCancel>
            <AlertDialogAction 
              onClick={(e) => {
                e.preventDefault();
                confirmDelete();
              }}
              disabled={deleteLoading}
              className="rounded-xl bg-rose-600 hover:bg-rose-700 text-white shadow-sm"
            >
              {deleteLoading ? (
                <>
                  <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  Deleting...
                </>
              ) : 'Delete Rule'}
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>

      <EscalationSheet 
        open={isSheetOpen}
        onOpenChange={setIsSheetOpen}
        initialData={selectedEscalation}
        onSuccess={loadEscalations}
      />
    </div>
  );
}
