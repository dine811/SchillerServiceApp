import { apiFetch, getApiBaseUrl } from "@/lib/api";

export type RepairCategory = "PFRN" | "OB" | "UR";
export type RepairStatus = "PENDING" | "COMPLETED";

export interface RepairRecord {
  id: number;
  serviceId: number;
  entryDate?: string | null;
  division?: string | null;
  scRefNo?: string | null;
  defGirNo?: string | null;
  category: RepairCategory;
  model?: string | null;
  defBoardModuleName?: string | null;
  technicalRemarks?: string | null;
  componentsUsed?: string | null;
  finishDate?: string | null;
  repairedBy?: string | null;
  finalRemarks?: string | null;
  status: RepairStatus;
  days: number;
  pendingDays?: number;
}

export interface RepairPage {
  content: RepairRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export interface RepairFilters {
  category?: RepairCategory;
  status?: RepairStatus;
  from?: string;
  to?: string;
  division?: string;
  scRefNo?: string;
  search?: string;
  page?: number;
  size?: number;
  sort?: string;
}

export interface RepairUpdatePayload {
  repairedBy: string;
  componentsUsed?: string;
  technicalRemarks?: string;
  finalRemarks?: string;
  status: RepairStatus;
}

async function ensureOk(response: Response, fallback: string): Promise<void> {
  if (response.ok) return;
  const text = (await response.text().catch(() => "")).trim();
  let message = text;
  if (text) {
    try {
      const parsed = JSON.parse(text) as { message?: string; detail?: string; error?: string };
      message = parsed.message || parsed.detail || parsed.error || text;
    } catch {
      // Plain-text API response.
    }
  }
  throw new Error(message || `${fallback} (${response.status})`);
}

function query(filters: RepairFilters): URLSearchParams {
  const params = new URLSearchParams();
  Object.entries(filters).forEach(([key, value]) => {
    if (value !== undefined && value !== null && String(value).trim() !== "") {
      params.set(key, String(value));
    }
  });
  return params;
}

export const RepairService = {
  async list(filters: RepairFilters): Promise<RepairPage> {
    const response = await apiFetch(`${getApiBaseUrl()}/repairs?${query(filters)}`);
    await ensureOk(response, "Failed to load repairs");
    return response.json();
  },

  async get(id: number): Promise<RepairRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/repairs/${id}`);
    await ensureOk(response, "Failed to load repair");
    return response.json();
  },

  async initiate(serviceId: number, category: RepairCategory): Promise<RepairRecord> {
    const params = new URLSearchParams({ category });
    const response = await apiFetch(
      `${getApiBaseUrl()}/repairs/from-service/${serviceId}?${params}`,
      { method: "POST" }
    );
    await ensureOk(response, "Failed to initiate repair");
    return response.json();
  },

  async update(id: number, payload: RepairUpdatePayload): Promise<RepairRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/repairs/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    await ensureOk(response, "Failed to update repair");
    return response.json();
  },

  async export(filters: Omit<RepairFilters, "page" | "size" | "sort">): Promise<Blob> {
    const response = await apiFetch(`${getApiBaseUrl()}/repairs/export?${query(filters)}`);
    await ensureOk(response, "Failed to export repairs");
    return response.blob();
  },
};
