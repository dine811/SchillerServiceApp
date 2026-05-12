import { apiFetch, getApiBaseUrl } from "@/lib/api";

async function throwIfNotOk(response: Response, label: string): Promise<void> {
  if (response.ok) return;
  let body = "";
  try {
    body = await response.text();
  } catch {
    /* ignore */
  }
  const trimmed = body.trim();
  const snippet =
    trimmed.length > 400 ? `${trimmed.slice(0, 400)}…` : trimmed;
  const detail = snippet || response.statusText || "Unknown error";
  throw new Error(`${label} (${response.status}): ${detail}`);
}

export interface ServiceRecord {
  id?: number;
  scRefNo: string;
  entryDate?: string;
  division?: string;
  frnNo?: string;
  frnDate?: string;
  serCommInwardDate?: string;
  serCentreReceivedDate?: string;
  stkCust?: string;
  region?: string;
  branch?: string;
  dealerName?: string;
  custName?: string;
  supplierName?: string;
  productModel?: string;
  modelConfig?: string;
  unitSlNo?: string;
  unitStatus?: string;
  dod?: string;
  modBrdName?: string;
  defModBrdName?: string;
  defType?: string;
  typeOfAcc?: string;
  defPartSn?: string;
  defGirNo?: string;
  repType?: string;
  repGirNo?: string;
  defUnitGirNo?: string;
  fieldRemarks?: string;
  technicalRemarks?: string;
  componentsUsedForRepair?: string;
  finalRemarks?: string;
  comrclDtlInvRmrk?: string;
  typeOfWork?: string;
  shipDtFrmSerCntr?: string;
  repairedBrdStkDate?: string;
  shipDateFromCommercial?: string;
  dcNo?: string;
  dispAdvNo?: string;
  repairedBrdAdvNo?: string;
  partNumber?: string;
  compatibleModels?: string;
  destination?: string;
  cost?: number;
  scEngineerId?: number;
  raEngineerId?: number;
  reportType?: string;
  /** Legacy workflow status (Pending / Completed), from job sheet workflow */
  status?: string | null;

  /** Legacy repair_status (NR / RP / RC) — engineer Pending FRN repair column */
  repairStatus?: string | null;

  /** Under-repair list only: days since ser centre received date */
  pendingDays?: number;

  /** Scrap list (ScarpList.jsp): four legacy pending-day columns */
  pendDaysPfrn?: string | null;
  pendDaysObp?: string | null;
  pendDaysUrp?: string | null;
  pendDaysScc?: string | null;

  // Virtual
  scEngineerName?: string;
  raEngineerName?: string;
}

export interface JobSheetPayload {
  id?: number;
  serId: number;
  repairDate?: string | null;
  enginnerName?: string | null;
  observation?: string | null;
  repairActivity?: string | null;
  timeSpent?: string | null;
  remark?: string | null;
  repairDate1?: string | null;
  enginnerName1?: string | null;
  observation1?: string | null;
  repairActivity1?: string | null;
  timeSpent1?: string | null;
  remark1?: string | null;
  repairDate2?: string | null;
  enginnerName2?: string | null;
  observation2?: string | null;
  repairActivity2?: string | null;
  timeSpent2?: string | null;
  remark2?: string | null;
  repairDate3?: string | null;
  enginnerName3?: string | null;
  observation3?: string | null;
  repairActivity3?: string | null;
  timeSpent3?: string | null;
  remark3?: string | null;
  repairDate4?: string | null;
  enginnerName4?: string | null;
  observation4?: string | null;
  repairActivity4?: string | null;
  timeSpent4?: string | null;
  remark4?: string | null;
  serviceStatus?: string | null;
}

export interface PaginatedServiceResponse {
  content: ServiceRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** CallListAdmin.jsp / callregister — pending rows */
export interface CallRegisterRecord {
  id?: number;
  division?: string;
  entryDate?: string;
  callDate?: string;
  scEngg?: string;
  scEnggName?: string;
  engineer?: string;
  model?: string;
  callType?: string;
  typeOfCall?: string;
  typeOfCommunication?: string;
  duration?: string;
  remarks?: string;
  statusType?: string;
}

/** PUT /api/call-register/{id} — partial update (non-null fields only on server) */
export interface CallRegisterUpdatePayload {
  remarks?: string;
  duration?: string;
  statusType?: string;
  callDate?: string;
  callType?: string;
  typeOfCall?: string;
  typeOfCommunication?: string;
}

export interface PaginatedCallRegisterResponse {
  content: CallRegisterRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** PendingActListENGG.jsp — pending rows from pendingact_master. */
export interface PendingActivityRecord {
  id?: number;
  division?: string;
  scEngg?: string;
  scEnggName?: string;
  entryDate?: string;
  initiateDate?: string;
  model?: string;
  pendingActivity?: string;
  responsible?: string;
  pendingForm?: string;
  tarClosedDate?: string;
  closedDate?: string;
  remarks?: string;
  statusType?: string;
  scInchargeRemark?: string;
}

export interface PendingActivityPayload {
  division?: string;
  scEngg?: string;
  entryDate?: string;
  initiateDate?: string;
  model?: string;
  pendingActivity?: string;
  responsible?: string;
  pendingForm?: string;
  tarClosedDate?: string;
  closedDate?: string;
  remarks?: string;
  statusType?: string;
  scInchargeRemark?: string;
}

export interface PaginatedPendingActivityResponse {
  content: PendingActivityRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** spareslist_engg.jsp / spareslist_engg2_Completed.jsp — sparemaster */
export interface SpareMasterRecord {
  id?: number;
  entryDate?: string;
  division?: string;
  supplier?: string;
  model?: string;
  partNumber?: string;
  defModBrdName?: string;
  reason?: string;
  reference?: string;
  girNo?: string;
  scEngg?: string;
  scEnggName?: string;
  issuedBy?: string;
  returnableStatus?: string;
  remarks?: string;
  returnedDate?: string;
  finalStatus?: string;
  qty?: string;
}

export interface SpareMasterPayload {
  entryDate?: string;
  division?: string;
  supplier?: string;
  model?: string;
  partNumber?: string;
  defModBrdName?: string;
  reason?: string;
  reference?: string;
  girNo?: string;
  scEngg?: string;
  issuedBy?: string;
  returnableStatus?: string;
  remarks?: string;
  returnedDate?: string;
  finalStatus?: string;
  qty?: string;
}

export interface PaginatedSpareMasterResponse {
  content: SpareMasterRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** PRFOB_AdminList.jsp — pending PRF/OB rows with resolved names */
export interface PrfobAdminRecord {
  id?: number;
  division?: string;
  entryDate?: string;
  scEngg?: string;
  scEnggName?: string;
  workType?: string;
  raisedDate?: string;
  receivedDate?: string;
  region?: string;
  branch?: string;
  branchName?: string;
  engineer?: string;
  engineerName?: string;
  dealer?: string;
  model?: string;
  supplier?: string;
  warrentyStatus?: string;
  prfobRefNo?: string;
  crmRefNo?: string;
  remarks?: string;
  statusType?: string;
  executedDate?: string;
  partType?: string;
  partDescription?: string;
  receiveDateFromSc?: string;
}

export interface PaginatedPrfobAdminResponse {
  content: PrfobAdminRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** nonSaleAdminList.jsp / SalablesList.jsp — nonsaleablemaster */
export interface NonsaleableAdminRecord {
  id?: number;
  entryDate?: string;
  unitDetails?: string;
  fqcInDate?: string;
  model?: string;
  modelName?: string;
  fqcObservation?: string;
  scInwardDate?: string;
  scObservation?: string;
  modelSn?: string;
  tentDateShipDate?: string;
  shipDateFqc?: string;
  finalStatus?: string;
}

export interface PaginatedNonsaleableResponse {
  content: NonsaleableAdminRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** BIRAdminList.jsp / ClosedBIRList_admin.jsp — birmaster */
export interface BirAdminRecord {
  id?: number;
  fqcInDate?: string;
  division?: string;
  model?: string;
  modelName?: string;
  configuration?: string;
  receivedQty?: string;
  softwrChanges?: string;
  hardwrChanges?: string;
  accesoryDetails?: string;
  cnrRefNo?: string;
  birRefNo?: string;
  finalStatus?: string;
}

export interface PaginatedBirResponse {
  content: BirAdminRecord[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/** GET /api/call-register/summary — row counts for diagnostics */
export interface CallRegisterSummary {
  total: number;
  pending: number;
  completed: number;
}

export interface Region {
  regionId: number;
  regionName: string;
}

export interface Branch {
  id: number;
  branchName: string;
  regionId: number;
}

export interface Dealer {
  dealerId: number;
  dealerName: string;
  regionId: number;
}

export interface Employee {
  empId: number;
  empName: string;
  branchId?: number;
  role?: string;
}

export interface Supplier {
  id: number;
  supplierName: string;
}

export interface Model {
  modelId: number;
  modelName: string;
  modelDesc?: string;
  supName?: string;
}

export interface DropdownOption {
  id: number;
  ddGroup: number;
  ddValue: string;
}

export interface SparePart {
  spareId: number;
  partNumber: string;
  compModels: string;
  defModBrdName: string;
  defType: string;
  division: string;
}

export const ServiceService = {
  // ─── Dropdown Data ──────────────────────────────────────────────────────────
  async getDropdownOptions(group: number): Promise<DropdownOption[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/dropdowns/group/${group}`);
    await throwIfNotOk(response, `Dropdown group ${group}`);
    return response.json();
  },

  getRegions: async (): Promise<Region[]> => {
    const response = await apiFetch(`${getApiBaseUrl()}/regions`);
    if (!response.ok) throw new Error('Failed to fetch regions');
    return response.json();
  },

  async getBranchesByRegion(regionId: number): Promise<Branch[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/branches/region/${regionId}`);
    if (!response.ok) throw new Error('Failed to fetch branches');
    return response.json();
  },

  async getDealersByRegion(regionId: number): Promise<Dealer[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/dealers/region/${regionId}`);
    if (!response.ok) throw new Error('Failed to fetch dealers');
    return response.json();
  },

  async getEmployeesByRegion(regionId: number): Promise<Employee[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/employees/region/${regionId}`);
    if (!response.ok) throw new Error('Failed to fetch employees');
    return response.json();
  },

  async getSuppliers(): Promise<Supplier[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/suppliers`);
    if (!response.ok) throw new Error('Failed to fetch suppliers');
    return response.json();
  },

  async getModels(): Promise<Model[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/models`);
    await throwIfNotOk(response, "Models list");
    return response.json();
  },

  async getModelsBySupplier(supName: string): Promise<Model[]> {
    const response = await apiFetch(`${getApiBaseUrl()}/models/supplier/${encodeURIComponent(supName)}`);
    await throwIfNotOk(response, "Models by supplier");
    return response.json();
  },

  async getSparePartDetails(partNumber: string, division: string): Promise<SparePart> {
    const response = await apiFetch(`${getApiBaseUrl()}/spare-parts/${encodeURIComponent(partNumber)}?division=${division}`);
    if (!response.ok) throw new Error('Failed to fetch spare part details');
    return response.json();
  },

  // ─── Service Records ────────────────────────────────────────────────────────
  async getServices(
    page: number = 0,
    size: number = 10,
    search: string = ''
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
      ...(search && { search })
    });
    
    const response = await apiFetch(`${getApiBaseUrl()}/services?${params}`);
    await throwIfNotOk(response, "Services list");
    return response.json();
  },

  /** Under-repair queue: ship from SC recorded, repaired board stock date not set. */
  async getUnderRepair(
    page: number = 0,
    size: number = 10,
    scRef: string = '',
    search: string = '',
    sort: string = 'scRefNo,desc'
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort,
    });
    if (scRef.trim()) params.set('scRef', scRef.trim());
    if (search.trim()) params.set('search', search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/services/under-repair?${params}`);
    if (!response.ok) throw new Error('Failed to fetch under-repair list');
    return response.json();
  },

  /**
   * Pending FRN queue: ship from SC null; unit status not OW/LAMC (dropdown join).
   * Backend scopes by engineer division when JWT is not admin (legacy EmpPFRNController).
   */
  async getPendingFrn(
    page: number = 0,
    size: number = 10,
    scRef: string = '',
    search: string = '',
    sort: string = 'scRefNo,desc'
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort,
    });
    if (scRef.trim()) params.set('scRef', scRef.trim());
    if (search.trim()) params.set('search', search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/services/pending-frn?${params}`);
    if (!response.ok) throw new Error('Failed to fetch pending FRN list');
    return response.json();
  },

  /** OB Pending queue: ship from SC null; unit status OW or LAMC (dropdown join). */
  async getObPending(
    page: number = 0,
    size: number = 10,
    scRef: string = '',
    search: string = '',
    sort: string = 'scRefNo,desc'
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort,
    });
    if (scRef.trim()) params.set('scRef', scRef.trim());
    if (search.trim()) params.set('search', search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/services/ob-pending?${params}`);
    if (!response.ok) throw new Error('Failed to fetch OB pending list');
    return response.json();
  },

  /** SC Closed Product: ship from SC and repair stock set; commercial ship date not set. */
  async getScClosed(
    page: number = 0,
    size: number = 10,
    scRef: string = '',
    search: string = '',
    sort: string = 'scRefNo,desc'
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort,
    });
    if (scRef.trim()) params.set('scRef', scRef.trim());
    if (search.trim()) params.set('search', search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/services/sc-closed?${params}`);
    if (!response.ok) throw new Error('Failed to fetch SC closed list');
    return response.json();
  },

  /** Closed Product (New_ClosedProduct): commercial ship date recorded. */
  async getNewClosed(
    page: number = 0,
    size: number = 10,
    scRef: string = '',
    search: string = '',
    sort: string = 'scRefNo,desc'
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort,
    });
    if (scRef.trim()) params.set('scRef', scRef.trim());
    if (search.trim()) params.set('search', search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/services/new-closed?${params}`);
    if (!response.ok) throw new Error('Failed to fetch Closed Product list');
    return response.json();
  },

  /** Scrap list (ScarpList.jsp): type of work SCRAPPED; optional division, entry month/year. */
  async getScrapList(
    page: number = 0,
    size: number = 10,
    opts: {
      division?: string;
      month?: number;
      year?: number;
      scRef?: string;
      search?: string;
      sort?: string;
    } = {}
  ): Promise<PaginatedServiceResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? 'scRefNo,desc',
    });
    if (opts.division?.trim()) params.set('division', opts.division.trim());
    if (opts.month != null && opts.month >= 1 && opts.month <= 12) params.set('month', String(opts.month));
    if (opts.year != null && opts.year > 1990) params.set('year', String(opts.year));
    if (opts.scRef?.trim()) params.set('scRef', opts.scRef.trim());
    if (opts.search?.trim()) params.set('search', opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/services/scrap-list?${params}`);
    if (!response.ok) throw new Error('Failed to fetch scrap list');
    return response.json();
  },

  async getService(id: number): Promise<ServiceRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/services/${id}`);
    if (!response.ok) throw new Error('Failed to fetch service');
    return response.json();
  },

  async createService(data: ServiceRecord): Promise<ServiceRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/services`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error('Failed to create service');
    return response.json();
  },

  async updateService(id: number, data: ServiceRecord): Promise<ServiceRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/services/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error('Failed to update service');
    return response.json();
  },

  async deleteService(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/services/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Failed to delete service');
  },

  async exportToExcel(params?: { from?: string; to?: string; search?: string }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.from) searchParams.set('from', params.from);
    if (params?.to) searchParams.set('to', params.to);
    if (params?.search) searchParams.set('search', params.search);
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export');
    return response.blob();
  },

  async exportUnderRepairExcel(params?: { scRef?: string; search?: string }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.scRef?.trim()) searchParams.set('scRef', params.scRef.trim());
    if (params?.search?.trim()) searchParams.set('search', params.search.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export/under-repair${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export under-repair list');
    return response.blob();
  },

  async exportPendingFrnExcel(params?: { scRef?: string; search?: string }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.scRef?.trim()) searchParams.set('scRef', params.scRef.trim());
    if (params?.search?.trim()) searchParams.set('search', params.search.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export/pending-frn${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export pending FRN list');
    return response.blob();
  },

  async exportObPendingExcel(params?: { scRef?: string; search?: string }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.scRef?.trim()) searchParams.set('scRef', params.scRef.trim());
    if (params?.search?.trim()) searchParams.set('search', params.search.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export/ob-pending${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export OB pending list');
    return response.blob();
  },

  async exportScClosedExcel(params?: { scRef?: string; search?: string }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.scRef?.trim()) searchParams.set('scRef', params.scRef.trim());
    if (params?.search?.trim()) searchParams.set('search', params.search.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export/sc-closed${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export SC closed list');
    return response.blob();
  },

  async exportNewClosedExcel(params?: { scRef?: string; search?: string }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.scRef?.trim()) searchParams.set('scRef', params.scRef.trim());
    if (params?.search?.trim()) searchParams.set('search', params.search.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export/new-closed${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export Closed Product list');
    return response.blob();
  },

  async exportScrapListExcel(params?: {
    division?: string;
    month?: number;
    year?: number;
    scRef?: string;
    search?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set('division', params.division.trim());
    if (params?.month != null && params.month >= 1 && params.month <= 12) searchParams.set('month', String(params.month));
    if (params?.year != null && params.year > 1990) searchParams.set('year', String(params.year));
    if (params?.scRef?.trim()) searchParams.set('scRef', params.scRef.trim());
    if (params?.search?.trim()) searchParams.set('search', params.search.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/services/export/scrap-list${qs ? `?${qs}` : ''}`;
    const response = await apiFetch(url);
    if (!response.ok) throw new Error('Failed to export scrap list');
    return response.blob();
  },

  /** Call register admin — pending only (CallListAdmin.jsp). */
  async getPendingCallRegister(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedCallRegisterResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/call-register/pending?${params}`);
    await throwIfNotOk(response, "Failed to fetch call register list");
    return response.json();
  },

  async getCallRegisterSummary(): Promise<CallRegisterSummary> {
    const response = await apiFetch(`${getApiBaseUrl()}/call-register/summary`);
    await throwIfNotOk(response, "Failed to fetch call register summary");
    return response.json();
  },

  /** ClosedCalls.jsp — completed call register rows. */
  async getCompletedCallRegister(
    page = 0,
    size = 10,
    opts: {
      division?: string;
      search?: string;
      sort?: string;
      /** ISO yyyy-MM-dd — filters by call_date (stored as string, first 10 chars) */
      from?: string;
      to?: string;
    } = {}
  ): Promise<PaginatedCallRegisterResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    if (opts.from?.trim()) params.set("from", opts.from.trim());
    if (opts.to?.trim()) params.set("to", opts.to.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/call-register/completed?${params}`);
    await throwIfNotOk(response, "Failed to fetch closed calls list");
    return response.json();
  },

  async exportCallRegisterExcel(params?: {
    division?: string;
    search?: string;
    from?: string;
    to?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set("division", params.division.trim());
    if (params?.search?.trim()) searchParams.set("search", params.search.trim());
    if (params?.from?.trim()) searchParams.set("from", params.from.trim());
    if (params?.to?.trim()) searchParams.set("to", params.to.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/call-register/export/pending${qs ? `?${qs}` : ""}`;
    const response = await apiFetch(url);
    await throwIfNotOk(response, "Failed to export call register");
    return response.blob();
  },

  async exportCompletedCallRegisterExcel(params?: {
    division?: string;
    search?: string;
    from?: string;
    to?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set("division", params.division.trim());
    if (params?.search?.trim()) searchParams.set("search", params.search.trim());
    if (params?.from?.trim()) searchParams.set("from", params.from.trim());
    if (params?.to?.trim()) searchParams.set("to", params.to.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/call-register/export/completed${qs ? `?${qs}` : ""}`;
    const response = await apiFetch(url);
    await throwIfNotOk(response, "Failed to export closed calls");
    return response.blob();
  },

  async getCallRegister(id: number): Promise<CallRegisterRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/call-register/${id}`);
    await throwIfNotOk(response, "Failed to load call register");
    return response.json();
  },

  async updateCallRegister(id: number, body: CallRegisterUpdatePayload): Promise<CallRegisterRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/call-register/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
    await throwIfNotOk(response, "Failed to update call register");
    return response.json();
  },

  async deleteCallRegister(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/call-register/${id}`, { method: "DELETE" });
    await throwIfNotOk(response, "Failed to delete call register row");
  },

  async getPendingActivity(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedPendingActivityResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/pending-activity/pending?${params}`);
    await throwIfNotOk(response, "Failed to fetch pending activity list");
    return response.json();
  },

  async exportPendingActivityExcel(params?: {
    division?: string;
    search?: string;
    from?: string;
    to?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set("division", params.division.trim());
    if (params?.search?.trim()) searchParams.set("search", params.search.trim());
    if (params?.from?.trim()) searchParams.set("from", params.from.trim());
    if (params?.to?.trim()) searchParams.set("to", params.to.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/pending-activity/export/pending${qs ? `?${qs}` : ""}`;
    const response = await apiFetch(url);
    await throwIfNotOk(response, "Failed to export pending activity");
    return response.blob();
  },

  async getClosedActivity(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedPendingActivityResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/pending-activity/completed?${params}`);
    await throwIfNotOk(response, "Failed to fetch closed activity list");
    return response.json();
  },

  async exportClosedActivityExcel(params?: {
    division?: string;
    search?: string;
    from?: string;
    to?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set("division", params.division.trim());
    if (params?.search?.trim()) searchParams.set("search", params.search.trim());
    if (params?.from?.trim()) searchParams.set("from", params.from.trim());
    if (params?.to?.trim()) searchParams.set("to", params.to.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/pending-activity/export/completed${qs ? `?${qs}` : ""}`;
    const response = await apiFetch(url);
    await throwIfNotOk(response, "Failed to export closed activity");
    return response.blob();
  },

  async getPendingActivityById(id: number): Promise<PendingActivityRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/pending-activity/${id}`);
    await throwIfNotOk(response, "Failed to load pending activity");
    return response.json();
  },

  async createPendingActivity(body: PendingActivityPayload): Promise<PendingActivityRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/pending-activity`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
    await throwIfNotOk(response, "Failed to create pending activity");
    return response.json();
  },

  async updatePendingActivity(id: number, body: PendingActivityPayload): Promise<PendingActivityRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/pending-activity/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
    await throwIfNotOk(response, "Failed to update pending activity");
    return response.json();
  },

  async deletePendingActivity(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/pending-activity/${id}`, { method: "DELETE" });
    await throwIfNotOk(response, "Failed to delete pending activity");
  },

  async getPendingSpares(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedSpareMasterResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/spares/pending?${params}`);
    await throwIfNotOk(response, "Failed to fetch spares list");
    return response.json();
  },

  async getCompletedSpares(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedSpareMasterResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/spares/completed?${params}`);
    await throwIfNotOk(response, "Failed to fetch completed spares list");
    return response.json();
  },

  async exportPendingSparesExcel(params?: {
    division?: string;
    search?: string;
    from?: string;
    to?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set("division", params.division.trim());
    if (params?.search?.trim()) searchParams.set("search", params.search.trim());
    if (params?.from?.trim()) searchParams.set("from", params.from.trim());
    if (params?.to?.trim()) searchParams.set("to", params.to.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/spares/export/pending${qs ? `?${qs}` : ""}`;
    const response = await apiFetch(url);
    await throwIfNotOk(response, "Failed to export spares list");
    return response.blob();
  },

  async exportCompletedSparesExcel(params?: {
    division?: string;
    search?: string;
    from?: string;
    to?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params?.division?.trim()) searchParams.set("division", params.division.trim());
    if (params?.search?.trim()) searchParams.set("search", params.search.trim());
    if (params?.from?.trim()) searchParams.set("from", params.from.trim());
    if (params?.to?.trim()) searchParams.set("to", params.to.trim());
    const qs = searchParams.toString();
    const url = `${getApiBaseUrl()}/spares/export/completed${qs ? `?${qs}` : ""}`;
    const response = await apiFetch(url);
    await throwIfNotOk(response, "Failed to export completed spares list");
    return response.blob();
  },

  async getSpareById(id: number): Promise<SpareMasterRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/spares/${id}`);
    await throwIfNotOk(response, "Failed to load spares record");
    return response.json();
  },

  async createSpare(body: SpareMasterPayload): Promise<SpareMasterRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/spares`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
    await throwIfNotOk(response, "Failed to create spares record");
    return response.json();
  },

  async updateSpare(id: number, body: SpareMasterPayload): Promise<SpareMasterRecord> {
    const response = await apiFetch(`${getApiBaseUrl()}/spares/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
    await throwIfNotOk(response, "Failed to update spares record");
    return response.json();
  },

  async deleteSpare(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/spares/${id}`, { method: "DELETE" });
    await throwIfNotOk(response, "Failed to delete spares record");
  },

  /** PRFOB_AdminList.jsp — status Pending only on server */
  async getPendingPrfobAdmin(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedPrfobAdminResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/prf-ob/admin/pending?${params}`);
    await throwIfNotOk(response, "Failed to fetch PRF/OB list");
    return response.json();
  },

  /** PRFOB_AdminList_closed.jsp — status Completed only on server */
  async getCompletedPrfobAdmin(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedPrfobAdminResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/prf-ob/admin/completed?${params}`);
    await throwIfNotOk(response, "Failed to fetch closed PRF/OB list");
    return response.json();
  },

  /**
   * Legacy ExportPRFOB_AdminController — filters by status, entry_date range, division (use "1" or omit for all).
   */
  async exportPrfobAdminExcel(params: {
    status?: string;
    from: string;
    to: string;
    division?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params.status?.trim()) searchParams.set("status", params.status.trim());
    searchParams.set("from", params.from.trim());
    searchParams.set("to", params.to.trim());
    if (params.division?.trim()) searchParams.set("division", params.division.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/prf-ob/admin/export?${searchParams}`);
    await throwIfNotOk(response, "Failed to export PRF/OB");
    return response.blob();
  },

  async deletePrfob(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/prf-ob/${id}`, { method: "DELETE" });
    await throwIfNotOk(response, "Failed to delete PRF/OB record");
  },

  async getPendingNonsaleableAdmin(
    page = 0,
    size = 10,
    opts: { search?: string; sort?: string } = {}
  ): Promise<PaginatedNonsaleableResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/nonsaleable/admin/pending?${params}`);
    await throwIfNotOk(response, "Failed to fetch non-salable list");
    return response.json();
  },

  async getSalables(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedNonsaleableResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/nonsaleable/salables?${params}`);
    await throwIfNotOk(response, "Failed to fetch salables list");
    return response.json();
  },

  async exportNonsaleableExcel(params: {
    status?: string;
    from: string;
    to: string;
    division?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params.status?.trim()) searchParams.set("status", params.status.trim());
    searchParams.set("from", params.from.trim());
    searchParams.set("to", params.to.trim());
    if (params.division?.trim()) searchParams.set("division", params.division.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/nonsaleable/admin/export?${searchParams}`);
    await throwIfNotOk(response, "Failed to export non-salable");
    return response.blob();
  },

  async deleteNonsaleable(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/nonsaleable/${id}`, { method: "DELETE" });
    await throwIfNotOk(response, "Failed to delete non-salable record");
  },

  async getPendingBirAdmin(
    page = 0,
    size = 10,
    opts: { search?: string; sort?: string } = {}
  ): Promise<PaginatedBirResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/bir/admin/pending?${params}`);
    await throwIfNotOk(response, "Failed to fetch BIR list");
    return response.json();
  },

  async getCompletedBirAdmin(
    page = 0,
    size = 10,
    opts: { division?: string; search?: string; sort?: string } = {}
  ): Promise<PaginatedBirResponse> {
    const params = new URLSearchParams({
      page: String(page),
      size: String(size),
      sort: opts.sort ?? "id,desc",
    });
    if (opts.division?.trim()) params.set("division", opts.division.trim());
    if (opts.search?.trim()) params.set("search", opts.search.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/bir/admin/completed?${params}`);
    await throwIfNotOk(response, "Failed to fetch closed BIR list");
    return response.json();
  },

  async exportBirExcel(params: {
    status?: string;
    from: string;
    to: string;
    division?: string;
  }): Promise<Blob> {
    const searchParams = new URLSearchParams();
    if (params.status?.trim()) searchParams.set("status", params.status.trim());
    searchParams.set("from", params.from.trim());
    searchParams.set("to", params.to.trim());
    if (params.division?.trim()) searchParams.set("division", params.division.trim());
    const response = await apiFetch(`${getApiBaseUrl()}/bir/admin/export?${searchParams}`);
    await throwIfNotOk(response, "Failed to export BIR");
    return response.blob();
  },

  async deleteBir(id: number): Promise<void> {
    const response = await apiFetch(`${getApiBaseUrl()}/bir/${id}`, { method: "DELETE" });
    await throwIfNotOk(response, "Failed to delete BIR record");
  },

  async createPartnumberEntry(body: {
    partNumber: string;
    brdName: string;
    compatibleModels: string;
    cost: number;
  }): Promise<{ id: number }> {
    const response = await apiFetch(`${getApiBaseUrl()}/partnumber-entry`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        partNumber: body.partNumber,
        brdName: body.brdName,
        compatibleModels: body.compatibleModels,
        cost: body.cost,
      }),
    });
    await throwIfNotOk(response, "Failed to save spares entry");
    return response.json();
  },

  async getJobSheetLatest(serviceId: number): Promise<JobSheetPayload | null> {
    const response = await apiFetch(`${getApiBaseUrl()}/jobsheets/by-service/${serviceId}/latest`);
    if (response.status === 404) return null;
    if (!response.ok) throw new Error('Failed to fetch job sheet');
    return response.json();
  },

  async createJobSheet(payload: JobSheetPayload): Promise<JobSheetPayload> {
    const response = await apiFetch(`${getApiBaseUrl()}/jobsheets`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    if (!response.ok) {
      const detail = await response.text().catch(() => '');
      throw new Error(detail || `Failed to save job sheet (${response.status})`);
    }
    return response.json();
  },

  async updateJobSheet(jobSheetId: number, payload: JobSheetPayload): Promise<JobSheetPayload> {
    const response = await apiFetch(`${getApiBaseUrl()}/jobsheets/${jobSheetId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    if (!response.ok) {
      const detail = await response.text().catch(() => '');
      throw new Error(detail || `Failed to update job sheet (${response.status})`);
    }
    return response.json();
  },
};
