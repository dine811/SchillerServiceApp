import { apiFetch, getApiBaseUrl } from "@/lib/api";

export interface Model {
  modelId?: number;
  modelName: string;
  modelDesc: string;
  supName: string;
}

export interface Division {
  productId: number;
  division: string;
  divisionName: string;
  models: Model[];
}

export interface DivisionRequest {
  division: string;
  divisionName: string;
  models: Model[];
}

export const DivisionService = {
  async getDivisions(): Promise<Division[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/products`);
    if (!response.ok) {
      throw new Error(`Failed to fetch divisions: ${response.statusText}`);
    }
    return response.json();
  },

  async getDivisionById(id: number): Promise<Division> {
    const response = await apiFetch(`${getApiBaseUrl()}/products/${id}`);
    if (!response.ok) {
        throw new Error(`Failed to get division ${id}`);
    }
    return response.json();
  },

  async createDivision(data: DivisionRequest): Promise<Division> {
    const response = await apiFetch(`${getApiBaseUrl()}/products`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw new Error(`Failed to create division: ${response.statusText}`);
    }
    return response.json();
  },

  async updateDivision(id: number, data: DivisionRequest): Promise<Division> {
    const response = await apiFetch(`${getApiBaseUrl()}/products/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw new Error(`Failed to update division: ${response.statusText}`);
    }
    return response.json();
  },

  async deleteDivision(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/products/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) {
      throw new Error(`Failed to delete division: ${response.statusText}`);
    }
  }
};
