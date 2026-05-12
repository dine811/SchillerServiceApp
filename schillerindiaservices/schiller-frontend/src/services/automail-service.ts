import { apiFetch, getApiBaseUrl } from "@/lib/api";

export interface Automail {
  mailIdEntryId?: number;
  mailId: string;
  reportType: string;
  division: string;
}

export const AutomailService = {
  getEscalations: async (page: number = 0, size: number = 10, search?: string) => {
    let url = `${getApiBaseUrl()}/escalations?page=${page}&size=${size}`;
    if (search) {
      url += `&search=${encodeURIComponent(search)}`;
    }
    const response = await apiFetch(url);
    if (!response.ok) {
        throw new Error(`Failed to fetch escalations: ${response.statusText}`);
    }
    return response.json();
  },

  getEscalationById: async (id: number): Promise<Automail> => {
    const response = await apiFetch(`${getApiBaseUrl()}/escalations/${id}`);
    if (!response.ok) {
        throw new Error(`Failed to get escalation ${id}`);
    }
    return response.json();
  },

  createEscalation: async (data: Automail): Promise<Automail> => {
    const response = await apiFetch(`${getApiBaseUrl()}/escalations`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
        throw new Error(`Failed to create escalation: ${response.statusText}`);
    }
    return response.json();
  },

  updateEscalation: async (id: number, data: Automail): Promise<Automail> => {
    const response = await apiFetch(`${getApiBaseUrl()}/escalations/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
        throw new Error(`Failed to update escalation: ${response.statusText}`);
    }
    return response.json();
  },

  deleteEscalation: async (id: number): Promise<void> => {
    const response = await apiFetch(`${getApiBaseUrl()}/escalations/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error(`Failed to delete escalation: ${response.statusText}`);
    }
  }
};
