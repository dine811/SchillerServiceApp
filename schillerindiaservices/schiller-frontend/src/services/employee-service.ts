import { apiFetch, getApiBaseUrl } from "@/lib/api";

export interface Employee {
  empId: number;
  empName: string;
  username: string;
  role: string;
  division: string | null;
  email: string | null;
  mobile: string | null;
  address?: string | null;
  department?: string | null;
  branchId?: number | null;
  active: boolean;
}

export interface Branch {
  id: number;
  branchName: string;
  regionId: number;
}

export interface Division {
  productId: number;
  division: string;
  divisionName: string;
}

export interface EmployeePageResponse {
  content: Employee[];
  pageable: any;
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  sort: any;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

const defaultHeaders = {
  "Content-Type": "application/json",
};

export const EmployeeService = {
  async getEmployees(page: number = 0, size: number = 10, search: string = ""): Promise<EmployeePageResponse> {
    const url = search 
      ? `${getApiBaseUrl()}/employees?page=${page}&size=${size}&search=${encodeURIComponent(search)}`
      : `${getApiBaseUrl()}/employees?page=${page}&size=${size}`;
    const res = await apiFetch(url, {
      headers: defaultHeaders,
      cache: "no-store",
    });
    if (!res.ok) throw new Error("Failed to fetch employees");
    return res.json();
  },

  async getActiveEmployees(): Promise<Employee[]> {
    const res = await apiFetch(`${getApiBaseUrl()}/employees/active`, {
      headers: defaultHeaders,
      cache: "no-store",
    });
    if (!res.ok) throw new Error("Failed to fetch active employees");
    return res.json();
  },

  async createEmployee(data: Partial<Employee> & { password?: string }): Promise<Employee> {
    const res = await apiFetch(`${getApiBaseUrl()}/employees`, {
      method: "POST",
      headers: defaultHeaders,
      body: JSON.stringify(data),
    });
    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || "Failed to create employee");
    }
    return res.json();
  },

  async updateEmployee(id: number, data: Partial<Employee> & { password?: string }): Promise<Employee> {
    const res = await apiFetch(`${getApiBaseUrl()}/employees/${id}`, {
      method: "PUT",
      headers: defaultHeaders,
      body: JSON.stringify(data),
    });
    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || "Failed to update employee");
    }
    return res.json();
  },

  async toggleActive(id: number): Promise<Employee> {
    const res = await apiFetch(`${getApiBaseUrl()}/employees/${id}/toggle-active`, {
      method: "PATCH",
      headers: defaultHeaders,
    });
    if (!res.ok) throw new Error("Failed to toggle employee status");
    return res.json();
  },

  async deleteEmployee(id: number): Promise<void> {
    const res = await apiFetch(`${getApiBaseUrl()}/employees/${id}`, {
      method: "DELETE",
      headers: defaultHeaders,
    });
    if (!res.ok) throw new Error("Failed to delete employee");
  },

  async getBranches(): Promise<Branch[]> {
    const res = await apiFetch(`${getApiBaseUrl()}/branches`, {
      headers: defaultHeaders,
      cache: "no-store",
    });
    if (!res.ok) throw new Error("Failed to fetch branches");
    return res.json();
  },

  async getDivisions(): Promise<Division[]> {
    const res = await apiFetch(`${getApiBaseUrl()}/products`, {
      headers: defaultHeaders,
      cache: "no-store",
    });
    if (!res.ok) throw new Error("Failed to fetch divisions");
    return res.json();
  },
};
