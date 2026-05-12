import { apiFetch, getApiBaseUrl } from "@/lib/api";

export interface Dealer {
  dealerId?: number;
  dealerName: string;
  dealerAddress?: string;
  dealerMail?: string;
  dealerPhone?: string;
  regionId?: number;
}

export const DealerService = {
  getDealers: async (): Promise<Dealer[]> => {
    const response = await apiFetch(`${getApiBaseUrl()}/dealers`);
    if (!response.ok) throw new Error("Failed to fetch dealers");
    return response.json();
  },

  getDealerById: async (id: number): Promise<Dealer> => {
    const response = await apiFetch(`${getApiBaseUrl()}/dealers/${id}`);
    if (!response.ok) throw new Error("Failed to fetch dealer");
    return response.json();
  },

  createDealer: async (data: Dealer): Promise<Dealer> => {
    const response = await apiFetch(`${getApiBaseUrl()}/dealers`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error("Failed to create dealer");
    return response.json();
  },

  updateDealer: async (id: number, data: Dealer): Promise<Dealer> => {
    const response = await apiFetch(`${getApiBaseUrl()}/dealers/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error("Failed to update dealer");
    return response.json();
  },

  deleteDealer: async (id: number): Promise<void> => {
    const response = await apiFetch(`${getApiBaseUrl()}/dealers/${id}`, {
      method: "DELETE"
    });
    if (!response.ok) throw new Error("Failed to delete dealer");
  }
};
