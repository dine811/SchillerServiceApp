import { apiFetch, getApiBaseUrl } from "@/lib/api";

export interface Region {
  regionId: number;
  regionName: string;
}

export const RegionService = {
  getRegions: async (): Promise<Region[]> => {
    const response = await apiFetch(`${getApiBaseUrl()}/regions`);
    if (!response.ok) throw new Error("Failed to fetch regions");
    return response.json();
  }
};
