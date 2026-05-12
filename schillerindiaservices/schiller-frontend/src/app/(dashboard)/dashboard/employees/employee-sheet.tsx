"use client";

import { useState, useEffect } from "react";
import { Employee, EmployeeService, Branch, Division } from "@/services/employee-service";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetFooter,
} from "@/components/ui/sheet";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Loader2, User, Mail, Phone, Lock, Briefcase, MapPin, Activity, Eye, EyeOff, Building2, Map } from "lucide-react";

interface EmployeeSheetProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  employee: Employee | null;
  onSuccess: () => void;
}

export function EmployeeSheet({ open, onOpenChange, employee, onSuccess }: EmployeeSheetProps) {
  const isEditing = !!employee;
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [branches, setBranches] = useState<Branch[]>([]);
  const [divisions, setDivisions] = useState<Division[]>([]);

  const [formData, setFormData] = useState({
    empName: "",
    username: "",
    password: "",
    role: "Engineer",
    division: "",
    email: "",
    mobile: "",
    address: "",
    department: "",
    branchId: "",
    active: true,
  });

  useEffect(() => {
    EmployeeService.getBranches().then(setBranches).catch(console.error);
    EmployeeService.getDivisions().then(setDivisions).catch(console.error);
  }, []);

  useEffect(() => {
    if (open) {
      if (employee) {
        setFormData({
          empName: employee.empName || "",
          username: employee.username || "",
          password: "", // Left blank intentionally when editing
          role: employee.role || "Engineer",
          division: employee.division || "",
          email: employee.email || "",
          mobile: employee.mobile || "",
          address: employee.address || "",
          department: employee.department || "",
          branchId: employee.branchId ? employee.branchId.toString() : "",
          active: employee.active,
        });
      } else {
        setFormData({
          empName: "",
          username: "",
          password: "",
          role: "Engineer",
          division: "",
          email: "",
          mobile: "",
          address: "",
          department: "",
          branchId: "",
          active: true,
        });
      }
      setError("");
    }
  }, [open, employee]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSelectChange = (name: string, value: string | boolean) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const payload: any = { ...formData };
      
      // Don't send empty password on update
      if (isEditing && !payload.password) {
        delete payload.password;
      }

      // Convert branchId to number or null
      payload.branchId = payload.branchId ? parseInt(payload.branchId) : null;
      
      if (isEditing && employee) {
        await EmployeeService.updateEmployee(employee.empId, payload);
      } else {
        await EmployeeService.createEmployee(payload);
      }
      onSuccess();
      onOpenChange(false);
    } catch (err: any) {
      setError(err.message || "Something went wrong.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Sheet open={open} onOpenChange={onOpenChange}>
      <SheetContent className="overflow-y-auto sm:max-w-md w-full border shadow-2xl p-0 sm:mr-6 sm:my-4 sm:rounded-2xl sm:max-h-[calc(100vh-32px)] flex flex-col bg-white">
        <div className="bg-gradient-to-b from-indigo-50 to-white px-6 py-8 border-b border-slate-100">
          <SheetHeader>
            <SheetTitle className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-slate-900 to-slate-700">
              {isEditing ? "Update Employee Profile" : "Create New Employee"}
            </SheetTitle>
            <p className="text-sm text-slate-500 font-medium mt-1">
              {isEditing
                ? "Modify the staff details below and save your changes."
                : "Enter the required information to invite a new team member."}
            </p>
          </SheetHeader>
        </div>

        <form onSubmit={handleSubmit} className="px-6 py-6 flex flex-col min-h-[calc(100vh-140px)]">
          {error && (
            <div className="bg-rose-50 border border-rose-100 text-rose-600 text-sm p-4 rounded-xl mb-6 font-medium shadow-sm flex items-start gap-3">
              <span className="font-bold shrink-0 mt-0.5">!</span>
              <div>{error}</div>
            </div>
          )}

          <div className="space-y-5 flex-1 pb-10">
            {/* Identity Group */}
            <div className="space-y-4">
              <h3 className="text-xs font-bold text-slate-400 uppercase tracking-widest border-b pb-2 mb-4">Identity</h3>
              
              <div className="space-y-1.5">
                <Label htmlFor="empName" className="text-slate-700 font-semibold flex items-center gap-2">
                  <User className="w-3.5 h-3.5 text-indigo-500" /> Full Name *
                </Label>
                <Input
                  id="empName"
                  name="empName"
                  required
                  value={formData.empName}
                  onChange={handleChange}
                  placeholder="E.g. Ramesh Kumar"
                  className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white focus:ring-indigo-500 rounded-xl shadow-sm transition-all"
                />
              </div>

              <div className="space-y-1.5">
                <Label htmlFor="username" className="text-slate-700 font-semibold flex items-center gap-2">
                  <span className="text-indigo-500 font-bold text-base leading-none">@</span> Username *
                </Label>
                <Input
                  id="username"
                  name="username"
                  required
                  value={formData.username}
                  onChange={handleChange}
                  placeholder="rameshk"
                  className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white focus:ring-indigo-500 rounded-xl shadow-sm transition-all"
                />
              </div>
            </div>

            {/* Contact Group */}
            <div className="space-y-4 pt-4 mt-2">
              <h3 className="text-xs font-bold text-slate-400 uppercase tracking-widest border-b pb-2 mb-4">Contact</h3>
              
              <div className="space-y-1.5">
                <Label htmlFor="email" className="text-slate-700 font-semibold flex items-center gap-2">
                  <Mail className="w-3.5 h-3.5 text-sky-500" /> Email Address
                </Label>
                <Input
                  id="email"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={handleChange}
                  placeholder="ramesh@schiller.com"
                  className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white focus:ring-indigo-500 rounded-xl shadow-sm transition-all"
                />
              </div>

              <div className="space-y-1.5">
                <Label htmlFor="mobile" className="text-slate-700 font-semibold flex items-center gap-2">
                  <Phone className="w-3.5 h-3.5 text-emerald-500" /> Mobile Number
                </Label>
                <Input
                  id="mobile"
                  name="mobile"
                  value={formData.mobile}
                  onChange={handleChange}
                  placeholder="+91 98400 11111"
                  className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white focus:ring-indigo-500 rounded-xl shadow-sm transition-all"
                />
              </div>

              <div className="space-y-1.5">
                <Label htmlFor="address" className="text-slate-700 font-semibold flex items-center gap-2">
                  <MapPin className="w-3.5 h-3.5 text-amber-600" /> Residential Address
                </Label>
                <Textarea
                  id="address"
                  name="address"
                  value={formData.address}
                  onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setFormData({ ...formData, address: e.target.value })}
                  placeholder="Full street address..."
                  className="min-h-[80px] bg-slate-50/50 border-slate-200 focus:bg-white focus:ring-indigo-500 rounded-xl shadow-sm transition-all resize-none"
                />
              </div>
            </div>

            {/* Access Group */}
            <div className="space-y-4 pt-4 mt-2">
              <h3 className="text-xs font-bold text-slate-400 uppercase tracking-widest border-b pb-2 mb-4">Role & Access</h3>

              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-1.5">
                  <Label htmlFor="role" className="text-slate-700 font-semibold flex items-center gap-2">
                    <Briefcase className="w-3.5 h-3.5 text-amber-500" /> Role
                  </Label>
                  <Select
                    value={formData.role}
                    onValueChange={(val) => handleSelectChange("role", val)}
                  >
                    <SelectTrigger className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white rounded-xl shadow-sm">
                      <SelectValue placeholder="Select role" />
                    </SelectTrigger>
                    <SelectContent className="rounded-xl shadow-lg border-slate-100">
                      <SelectItem value="Engineer">Engineer</SelectItem>
                      <SelectItem value="FQC">FQC</SelectItem>
                      <SelectItem value="Admin">Admin</SelectItem>
                      <SelectItem value="Manager">Manager</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-1.5">
                  <Label htmlFor="division" className="text-slate-700 font-semibold flex items-center gap-2">
                    <Building2 className="w-3.5 h-3.5 text-rose-500" /> Division
                  </Label>
                  <Select
                    value={formData.division}
                    onValueChange={(val) => handleSelectChange("division", val)}
                  >
                    <SelectTrigger className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white rounded-xl shadow-sm">
                      <SelectValue placeholder="Select division" />
                    </SelectTrigger>
                    <SelectContent className="rounded-xl shadow-lg border-slate-100 max-h-[250px] overflow-y-auto">
                      {divisions.map((d) => (
                        <SelectItem key={d.productId} value={d.divisionName}>{d.divisionName}</SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-1.5">
                  <Label htmlFor="department" className="text-slate-700 font-semibold flex items-center gap-2">
                    <Map className="w-3.5 h-3.5 text-indigo-400" /> Department (Location)
                  </Label>
                  <Select
                    value={formData.department}
                    onValueChange={(val) => handleSelectChange("department", val)}
                  >
                    <SelectTrigger className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white rounded-xl shadow-sm">
                      <SelectValue placeholder="Select dept" />
                    </SelectTrigger>
                    <SelectContent className="rounded-xl shadow-lg border-slate-100">
                      <SelectItem value="InHouse">InHouse</SelectItem>
                      <SelectItem value="Field">Field</SelectItem>
                      <SelectItem value="HeadOffice">HeadOffice</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-1.5">
                  <Label htmlFor="branch" className="text-slate-700 font-semibold flex items-center gap-2">
                    <MapPin className="w-3.5 h-3.5 text-teal-500" /> Branch
                  </Label>
                  <Select
                    value={formData.branchId}
                    onValueChange={(val) => handleSelectChange("branchId", val)}
                  >
                    <SelectTrigger className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white rounded-xl shadow-sm">
                      <SelectValue placeholder="Select branch" />
                    </SelectTrigger>
                    <SelectContent className="rounded-xl shadow-lg border-slate-100 max-h-[250px] overflow-y-auto">
                      {branches.map((b) => (
                        <SelectItem key={b.id} value={b.id.toString()}>{b.branchName}</SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="space-y-1.5">
                <Label htmlFor="password" className="text-slate-700 font-semibold flex items-center gap-2">
                  <Lock className="w-3.5 h-3.5 text-slate-500" />
                  {isEditing ? "Reset Password (Optional)" : "Initial Password *"}
                </Label>
                <div className="relative">
                  <Input
                    id="password"
                    name="password"
                    type={showPassword ? "text" : "password"}
                    required={!isEditing}
                    value={formData.password}
                    onChange={handleChange}
                    placeholder={isEditing ? "Leave blank to keep unchanged" : "Create a secure password"}
                    className="h-11 pr-11 bg-slate-50/50 border-slate-200 focus:bg-white focus:ring-indigo-500 rounded-xl shadow-sm transition-all"
                  />
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    className="absolute right-1 top-1/2 -translate-y-1/2 h-9 w-9 text-slate-400 hover:text-indigo-600 hover:bg-slate-100 rounded-lg"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                  </Button>
                </div>
              </div>
              
              <div className="space-y-1.5">
                 <Label className="text-slate-700 font-semibold flex items-center gap-2">
                   <Activity className="w-3.5 h-3.5 text-indigo-500" /> Account Status
                 </Label>
                 <Select
                   value={formData.active ? "true" : "false"}
                   onValueChange={(val) => handleSelectChange("active", val === "true")}
                 >
                   <SelectTrigger className="h-11 bg-slate-50/50 border-slate-200 focus:bg-white rounded-xl shadow-sm">
                     <SelectValue placeholder="Status" />
                   </SelectTrigger>
                   <SelectContent className="rounded-xl shadow-lg border-slate-100">
                     <SelectItem value="true">
                       <span className="flex items-center gap-2"><div className="w-2 h-2 rounded-full bg-emerald-500"></div> Active</span>
                     </SelectItem>
                     <SelectItem value="false">
                       <span className="flex items-center gap-2"><div className="w-2 h-2 rounded-full bg-slate-400"></div> Inactive</span>
                     </SelectItem>
                   </SelectContent>
                 </Select>
              </div>
            </div>
          </div>

          <div className="sticky bottom-0 bg-white pt-4 pb-2 border-t border-slate-100 mt-auto flex gap-3">
            <Button
              type="button"
              variant="outline"
              onClick={() => onOpenChange(false)}
              disabled={loading}
              className="flex-1 h-12 rounded-xl border-slate-200 text-slate-600 font-semibold hover:bg-slate-50"
            >
              Cancel
            </Button>
            <Button 
              type="submit" 
              disabled={loading} 
              className="flex-1 h-12 rounded-xl bg-gradient-to-r from-indigo-600 to-violet-600 hover:from-indigo-700 hover:to-violet-700 text-white font-semibold shadow-md shadow-indigo-500/25 transition-all"
            >
              {loading && <Loader2 className="mr-2 h-5 w-5 animate-spin" />}
              {isEditing ? "Save Changes" : "Create Account"}
            </Button>
          </div>
        </form>
      </SheetContent>
    </Sheet>
  );
}
