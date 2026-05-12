"use client";

import { useEffect, useState } from "react";
import { EmployeeService, Employee } from "@/services/employee-service";
import { DivisionService, Division } from "@/services/division-service";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Plus, Loader2, Edit, Trash, Search, Mail, Phone, Briefcase, MapPin, MoreVertical } from "lucide-react";
import { EmployeeSheet } from "./employee-sheet";
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

// Generate a consistent color based on a string
const stringToColor = (str: string) => {
  let hash = 0;
  for (let i = 0; i < str.length; i++) {
    hash = str.charCodeAt(i) + ((hash << 5) - hash);
  }
  const colors = [
    "from-violet-500 to-indigo-500",
    "from-pink-500 to-rose-500",
    "from-sky-500 to-cyan-500",
    "from-amber-500 to-orange-500",
    "from-emerald-500 to-teal-500",
    "from-blue-500 to-indigo-600"
  ];
  return colors[Math.abs(hash) % colors.length];
};

export default function EmployeesPage() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [divisions, setDivisions] = useState<Division[]>([]);

  useEffect(() => {
    const fetchDivisions = async () => {
      try {
        const data = await DivisionService.getDivisions();
        setDivisions(data);
      } catch (err) {
        console.error("Failed to load divisions for mapping", err);
      }
    };
    fetchDivisions();
  }, []);
  
  // Pagination state
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [searchQuery, setSearchQuery] = useState("");

  // Sheet state
  const [sheetOpen, setSheetOpen] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null);

  // Delete Dialog state
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [employeeToDelete, setEmployeeToDelete] = useState<number | null>(null);
  const [deleteLoading, setDeleteLoading] = useState(false);

  const loadData = async (pageNum: number, search: string = searchQuery) => {
    setLoading(true);
    try {
      const data = await EmployeeService.getEmployees(pageNum, 10, search);
      setEmployees(data.content);
      setTotalPages(data.totalPages);
      setPage(data.number);
      setError("");
    } catch (err: any) {
      setError(err.message || "Failed to load employees");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const handler = setTimeout(() => {
      loadData(0, searchQuery);
    }, 350);
    return () => clearTimeout(handler);
  }, [searchQuery]);

  const handleAddClick = () => {
    setSelectedEmployee(null);
    setSheetOpen(true);
  };

  const handleEditClick = (employee: Employee) => {
    setSelectedEmployee(employee);
    setSheetOpen(true);
  };

  const handleConfirmDelete = async () => {
    if (employeeToDelete === null) return;
    setDeleteLoading(true);
    try {
      await EmployeeService.deleteEmployee(employeeToDelete);
      loadData(0);
      setDeleteDialogOpen(false);
      setEmployeeToDelete(null);
    } catch (err: any) {
      alert(err.message || "Failed to delete employee. They may have active service records attached.");
    } finally {
      setDeleteLoading(false);
    }
  };

  const onSheetSuccess = () => {
    loadData(page);
  };

  // Client-side filtering removed in favor of Server-side search

  return (
    <div className="space-y-4 animate-in fade-in duration-500 p-2 sm:p-4">
      
      {/* Compact Premium Header & Toolbar */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 bg-white px-5 py-3.5 rounded-xl shadow-sm border border-slate-100">
        <div>
          <h1 className="text-xl font-extrabold tracking-tight bg-clip-text text-transparent bg-gradient-to-r from-slate-900 to-slate-600">
            Employees Directory
          </h1>
          <p className="text-slate-500 mt-0.5 text-xs font-medium">
            Manage your organization's staff and credentials.
          </p>
        </div>
        
        <div className="flex flex-col sm:flex-row items-center gap-3 w-full sm:w-auto">
          <div className="relative w-full sm:w-64">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-slate-400" />
            <Input 
              placeholder="Search employees..." 
              className="pl-9 h-9 text-sm bg-slate-50 border-slate-200 rounded-lg focus-visible:ring-indigo-500 transition-all w-full"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
          <Button 
            onClick={handleAddClick} 
            className="w-full sm:w-auto gap-2 bg-gradient-to-r from-indigo-600 to-violet-600 hover:from-indigo-700 hover:to-violet-700 text-white shadow-sm shadow-indigo-500/20 h-9 px-4 rounded-lg font-medium text-sm transition-all hover:scale-[1.02]"
          >
            <Plus className="h-4 w-4" /> Add Employee
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
                  <th className="px-6 py-5 rounded-tl-xl">Employee</th>
                  <th className="px-6 py-5">Role & Division</th>
                  <th className="px-6 py-5">Contact Details</th>
                  <th className="px-6 py-5 text-center">Status</th>
                  <th className="px-6 py-5 text-center rounded-tr-xl">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {loading ? (
                  <tr>
                    <td colSpan={5} className="h-48 text-center">
                      <div className="flex flex-col items-center justify-center gap-3">
                        <Loader2 className="h-8 w-8 animate-spin text-indigo-500" />
                        <span className="text-slate-500 font-medium">Loading employee data...</span>
                      </div>
                    </td>
                  </tr>
                ) : employees.length === 0 ? (
                  <tr>
                    <td colSpan={5} className="h-48 text-center">
                      <div className="flex flex-col items-center justify-center gap-2">
                        <div className="h-16 w-16 rounded-full bg-slate-50 flex items-center justify-center mb-2">
                          <Search className="h-6 w-6 text-slate-400" />
                        </div>
                        <p className="text-slate-600 font-semibold text-lg">No employees found.</p>
                        <p className="text-slate-400">Try adjusting your search criteria.</p>
                      </div>
                    </td>
                  </tr>
                ) : (
                  employees.map((emp) => {
                    const avatarGradient = stringToColor(emp.empName);
                    const initial = emp.empName.charAt(0).toUpperCase();
                    
                    return (
                      <tr key={emp.empId} className="hover:bg-slate-50/80 transition-colors group">
                        
                        {/* Avatar & Name */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center gap-4">
                            <div className={cn("h-10 w-10 rounded-xl flex items-center justify-center text-white font-bold text-base shadow-sm shrink-0 bg-gradient-to-br", avatarGradient)}>
                              {initial}
                            </div>
                            <div>
                              <div className="font-bold text-slate-900 text-[13px]">{emp.empName}</div>
                              <div className="text-slate-500 font-medium mt-0.5 text-xs">@{emp.username} • #{emp.empId}</div>
                            </div>
                          </div>
                        </td>

                        {/* Role & Division */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex flex-col items-start gap-1.5">
                            <Badge className="bg-slate-100 text-slate-700 hover:bg-slate-200 border-none px-2 py-0.5 shadow-none font-semibold text-[11px]">
                              <Briefcase className="w-3.5 h-3.5 mr-1.5 inline-block text-slate-400" />
                              {emp.role}
                            </Badge>
                            {emp.division && (
                              <div className="flex items-center text-slate-500 text-[11px] font-medium ml-1">
                                <MapPin className="w-3 h-3 mr-1" />
                                Division: {divisions.find(d => String(d.productId) === String(emp.division))?.divisionName || emp.division}
                              </div>
                            )}
                          </div>
                        </td>

                        {/* Contact */}
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex flex-col gap-1.5">
                            <div className="flex items-center text-slate-600 text-xs font-medium">
                              <Mail className="w-3.5 h-3.5 mr-2 text-slate-400" />
                              {emp.email || <span className="text-slate-400 italic">No email</span>}
                            </div>
                            <div className="flex items-center text-slate-600 text-xs font-medium">
                              <Phone className="w-3.5 h-3.5 mr-2 text-slate-400" />
                              {emp.mobile || <span className="text-slate-400 italic">No phone</span>}
                            </div>
                          </div>
                        </td>

                        {/* Status */}
                        <td className="px-6 py-4 whitespace-nowrap text-center">
                          {emp.active ? (
                            <div className="inline-flex items-center gap-1.5 px-2 py-0.5 rounded-full bg-emerald-50 text-emerald-700 font-semibold text-[11px] border border-emerald-100 shadow-sm">
                              <div className="w-1.5 h-1.5 rounded-full bg-emerald-500 animate-pulse"></div>
                              Active
                            </div>
                          ) : (
                            <div className="inline-flex items-center gap-1.5 px-2 py-0.5 rounded-full bg-slate-100 text-slate-600 font-semibold text-[11px] border border-slate-200 shadow-sm">
                              <div className="w-1.5 h-1.5 rounded-full bg-slate-400"></div>
                              Inactive
                            </div>
                          )}
                        </td>

                        {/* Actions */}
                        <td className="px-6 py-4 whitespace-nowrap text-center">
                          <div className="flex items-center justify-center gap-2 text-slate-500">
                            <Button 
                              variant="ghost" 
                              size="icon" 
                              onClick={() => handleEditClick(emp)}
                              className="h-8 w-8 text-indigo-600 hover:text-indigo-700 hover:bg-indigo-50 rounded-lg"
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <Button 
                              variant="ghost" 
                              size="icon" 
                              onClick={() => {
                                setEmployeeToDelete(emp.empId);
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
                   onClick={() => loadData(page - 1)}
                 >
                   Previous
                 </Button>

                 <div className="hidden sm:flex items-center gap-1 mx-2">
                   {(() => {
                     const getPageNumbers = (current: number, total: number) => {
                       if (total <= 7) return Array.from({ length: total }, (_, i) => i);
                       if (current <= 3) return [0, 1, 2, 3, 4, '...', total - 1];
                       if (current >= total - 4) return [0, '...', total - 5, total - 4, total - 3, total - 2, total - 1];
                       return [0, '...', current - 1, current, current + 1, '...', total - 1];
                     };
                     
                     return getPageNumbers(page, totalPages).map((p, idx) => {
                       if (p === '...') {
                         return <span key={`ellipsis-${idx}`} className="px-2 text-slate-400">...</span>;
                       }
                       const pageNum = p as number;
                       return (
                         <Button
                           key={pageNum}
                           variant={pageNum === page ? "default" : "outline"}
                           className={cn(
                             "h-9 w-9 p-0 rounded-lg shadow-sm font-medium transition-colors",
                             pageNum === page 
                               ? "bg-indigo-600 text-white hover:bg-indigo-700 hover:text-white border-transparent"
                               : "bg-white border-slate-200 text-slate-600 hover:bg-slate-50 hover:text-indigo-600"
                           )}
                           onClick={() => loadData(pageNum)}
                         >
                           {pageNum + 1}
                         </Button>
                       );
                     });
                   })()}
                 </div>

                 <Button
                   variant="outline"
                   className="h-9 px-4 rounded-lg bg-white shadow-sm border-slate-200 font-medium hover:bg-slate-50 hover:text-indigo-600 transition-colors disabled:opacity-50"
                   disabled={page >= totalPages - 1}
                   onClick={() => loadData(page + 1)}
                 >
                   Next
                 </Button>
               </div>
             </div>
          )}
        </div>
      )}

      {/* Slide-out Edit/Create Form */}
      <EmployeeSheet
        open={sheetOpen}
        onOpenChange={setSheetOpen}
        employee={selectedEmployee}
        onSuccess={onSheetSuccess}
      />

      {/* Delete Confirmation Alert */}
      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent className="rounded-2xl sm:max-w-md">
          <AlertDialogHeader>
            <AlertDialogTitle className="text-xl text-slate-800">Are you absolutely sure?</AlertDialogTitle>
            <AlertDialogDescription className="text-slate-500 font-medium pt-2">
              This action cannot be undone. This will permanently delete the employee's account and remove their data from our servers.
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
              Delete Employee
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}
