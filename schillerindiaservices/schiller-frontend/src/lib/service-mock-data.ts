// ────────────────────────────────────────────────────
// SERVICE MASTER — Mock Data (mirrors JSP data sources)
// Replace with real API calls when backend is ready
// ────────────────────────────────────────────────────

export interface ServiceRecord {
  id: number;
  scRefNo: string;
  entryDate: string;
  scEngineer: string;
  frnNo: string;
  region: string;
  engineer: string;         // field / RA engineer
  custName: string;
  model: string;
  unitStatus: string;
  defModBrdName: string;
  defGirNo: string;
  typeOfWork: string;
  pdPfrn: string;
  pdObp: string;
  pdUrp: string;
  pdScc: string;
  branch: string;
  division: string;
  supplier: string;
  unitSlno: string;
  dod: string;
  repGirNo: string;
  repType: string;
  typeOfAcc: string;
  defType: string;
  defPartSn: string;
  defUnitGirNo: string;
  fieldRemarks: string;
  technicalRemarks: string;
  componentsUsedForRepair: string;
  repairedBrdStkDate: string;
  finalRemarks: string;
  shipDtFrmSerCntr: string;
  dispAdvNo: string;
  shipDateFromCommercial: string;
  dcNo: string;
  reportType: string;
  destination: string;
  cost: number;
  stkCust: string;
  dealer: string;
  modBrdName: string;
  partNumber: string;
  compatibleModels: string;
  modelConfig: string;
  repairedBrdAdvNo: string;
  docketNo: string;
  comrclDtlInvRmrk: string;
  reptype: string;
}

export const MOCK_SERVICES: ServiceRecord[] = [
  {
    id: 1248, scRefNo: "SC-1248", entryDate: "2026-03-14", scEngineer: "Ramesh K.",
    frnNo: "FRN-8821", region: "Chennai", engineer: "Ramesh K.", custName: "Apollo Hospitals",
    model: "Defibrillator X3", unitStatus: "OW", defModBrdName: "Power Board V2",
    defGirNo: "GIR-001A", typeOfWork: "Repair", pdPfrn: "2026-03-10", pdObp: "", pdUrp: "", pdScc: "",
    branch: "Chennai Central", division: "Cardiac", supplier: "Schiller AG",
    unitSlno: "SN-4421", dod: "2024-06-01", repGirNo: "GIR-001A", repType: "PFRN",
    typeOfAcc: "Warranty", defType: "Electronic", defPartSn: "PS-990",
    defUnitGirNo: "UGIR-221", fieldRemarks: "Unit not powering on",
    technicalRemarks: "Power board replaced", componentsUsedForRepair: "Power Board V2",
    repairedBrdStkDate: "2026-03-12", finalRemarks: "Tested OK",
    shipDtFrmSerCntr: "2026-03-13", dispAdvNo: "DADV-001",
    shipDateFromCommercial: "", dcNo: "DC-1001", reportType: "dispatchlist",
    destination: "Apollo Chennai", cost: 5500, stkCust: "Customer", dealer: "MedEquip India",
    modBrdName: "Power Board", partNumber: "PB-V2-001", compatibleModels: "Defib X2, X3",
    modelConfig: "Standard", repairedBrdAdvNo: "ADV-881", docketNo: "DOC-001",
    comrclDtlInvRmrk: "", reptype: "PFRN",
  },
  {
    id: 1247, scRefNo: "SC-1247", entryDate: "2026-03-14", scEngineer: "Admin",
    frnNo: "FRN-8820", region: "Bangalore", engineer: "Vinod S.", custName: "Fortis Healthcare",
    model: "ECG Machine Pro", unitStatus: "LAMC", defModBrdName: "Display Module",
    defGirNo: "GIR-002B", typeOfWork: "PM", pdPfrn: "2026-03-09", pdObp: "2026-03-11", pdUrp: "", pdScc: "",
    branch: "Bangalore North", division: "Diagnostic", supplier: "Nihon Kohden",
    unitSlno: "SN-3310", dod: "2023-11-15", repGirNo: "GIR-002C", repType: "OBP",
    typeOfAcc: "AMC", defType: "Mechanical", defPartSn: "DM-440",
    defUnitGirNo: "UGIR-110", fieldRemarks: "Screen flickering",
    technicalRemarks: "Display cable replaced", componentsUsedForRepair: "Display cable",
    repairedBrdStkDate: "", finalRemarks: "",
    shipDtFrmSerCntr: "2026-03-12", dispAdvNo: "DADV-002",
    shipDateFromCommercial: "2026-03-13", dcNo: "DC-1002", reportType: "servicecenter",
    destination: "Fortis Bangalore", cost: 2200, stkCust: "Stock", dealer: "HealthTech Solutions",
    modBrdName: "Display Module", partNumber: "DM-440-A", compatibleModels: "ECG Pro, ECG Elite",
    modelConfig: "Advanced", repairedBrdAdvNo: "", docketNo: "DOC-002",
    comrclDtlInvRmrk: "Invoice raised", reptype: "OBP",
  },
  {
    id: 1246, scRefNo: "SC-1246", entryDate: "2026-03-13", scEngineer: "Admin",
    frnNo: "FRN-8819", region: "Delhi", engineer: "Arjun L.", custName: "Max Hospital",
    model: "Patient Monitor", unitStatus: "NW", defModBrdName: "SPO2 Sensor",
    defGirNo: "GIR-003C", typeOfWork: "Repair", pdPfrn: "2026-03-08", pdObp: "", pdUrp: "2026-03-10", pdScc: "",
    branch: "Delhi South", division: "ICU Support", supplier: "Philips Healthcare",
    unitSlno: "SN-5523", dod: "2022-05-20", repGirNo: "GIR-003C", repType: "UR",
    typeOfAcc: "Warranty", defType: "Electronic", defPartSn: "SPO-002",
    defUnitGirNo: "UGIR-334", fieldRemarks: "SPO2 reading error",
    technicalRemarks: "Sensor calibrated", componentsUsedForRepair: "SPO2 sensor module",
    repairedBrdStkDate: "2026-03-11", finalRemarks: "Calibration done",
    shipDtFrmSerCntr: "", dispAdvNo: "",
    shipDateFromCommercial: "", dcNo: "", reportType: "stocklist",
    destination: "", cost: 0, stkCust: "Customer", dealer: "CureMax Pvt Ltd",
    modBrdName: "SPO2 Board", partNumber: "SPO2-B-01", compatibleModels: "Mon V3, Mon V4",
    modelConfig: "ICU Grade", repairedBrdAdvNo: "ADV-332", docketNo: "DOC-003",
    comrclDtlInvRmrk: "", reptype: "UR",
  },
  {
    id: 1245, scRefNo: "SC-1245", entryDate: "2026-03-12", scEngineer: "Admin",
    frnNo: "FRN-8818", region: "Mumbai", engineer: "Priya T.", custName: "AIMS Mumbai",
    model: "Ultrasound Unit", unitStatus: "OW", defModBrdName: "Transducer Probe",
    defGirNo: "GIR-004D", typeOfWork: "Installation", pdPfrn: "", pdObp: "", pdUrp: "", pdScc: "2026-03-11",
    branch: "Mumbai West", division: "Radiology", supplier: "GE Healthcare",
    unitSlno: "SN-6634", dod: "2021-08-10", repGirNo: "GIR-004D", repType: "PFRN",
    typeOfAcc: "Non-Warranty", defType: "Probe", defPartSn: "TP-007",
    defUnitGirNo: "UGIR-445", fieldRemarks: "No image output",
    technicalRemarks: "Probe replaced", componentsUsedForRepair: "Transducer probe TP-007",
    repairedBrdStkDate: "2026-03-10", finalRemarks: "Image quality verified",
    shipDtFrmSerCntr: "2026-03-11", dispAdvNo: "DADV-003",
    shipDateFromCommercial: "2026-03-12", dcNo: "DC-1003", reportType: "dispatchlist",
    destination: "AIMS Mumbai Radiology", cost: 18000, stkCust: "Customer", dealer: "MedLine India",
    modBrdName: "Probe Board", partNumber: "TP-007-A", compatibleModels: "US Pro, US Elite",
    modelConfig: "Radiology", repairedBrdAdvNo: "ADV-441", docketNo: "DOC-004",
    comrclDtlInvRmrk: "Invoice #INV-2042", reptype: "PFRN",
  },
  {
    id: 1244, scRefNo: "SC-1244", entryDate: "2026-03-11", scEngineer: "Admin",
    frnNo: "FRN-8817", region: "Kolkata", engineer: "Kiran M.", custName: "Narayan Health",
    model: "Ventilator V2", unitStatus: "RD", defModBrdName: "Flow Sensor",
    defGirNo: "GIR-005E", typeOfWork: "Repair", pdPfrn: "2026-03-07", pdObp: "", pdUrp: "", pdScc: "",
    branch: "Kolkata Central", division: "Critical Care", supplier: "Draeger",
    unitSlno: "SN-7745", dod: "2020-12-01", repGirNo: "GIR-005F", repType: "OBP",
    typeOfAcc: "AMC", defType: "Sensor", defPartSn: "FS-003",
    defUnitGirNo: "UGIR-556", fieldRemarks: "Tidal volume error",
    technicalRemarks: "Flow sensor replaced and calibrated", componentsUsedForRepair: "Flow sensor FS-003",
    repairedBrdStkDate: "", finalRemarks: "",
    shipDtFrmSerCntr: "", dispAdvNo: "",
    shipDateFromCommercial: "", dcNo: "", reportType: "",
    destination: "", cost: 0, stkCust: "Stock", dealer: "MedEquip East",
    modBrdName: "Flow Board", partNumber: "FS-003-B", compatibleModels: "Vent V2, Vent V3",
    modelConfig: "ICU Ventilator", repairedBrdAdvNo: "", docketNo: "DOC-005",
    comrclDtlInvRmrk: "", reptype: "OBP",
  },
];

// Dropdown master data (equivalent to DropdownDao DDV1–DDV6)
export const DDV1_STK_CUST = [
  { id: 1, value: "Customer" },
  { id: 2, value: "Stock" },
];

export const DDV2_UNIT_STATUS = [
  { id: 1, value: "OW" },
  { id: 2, value: "LAMC" },
  { id: 3, value: "NW" },
  { id: 4, value: "RD" },
  { id: 5, value: "BM" },
];

export const DDV3_DEF_TYPE = [
  { id: 1, value: "Electronic" },
  { id: 2, value: "Mechanical" },
  { id: 3, value: "Software" },
  { id: 4, value: "Probe" },
  { id: 5, value: "Sensor" },
  { id: 6, value: "Calibration" },
];

export const DDV4_TYPE_OF_ACC = [
  { id: 1, value: "Warranty" },
  { id: 2, value: "Non-Warranty" },
  { id: 3, value: "AMC" },
  { id: 4, value: "CMC" },
];

export const DDV5_TYPE_OF_WORK = [
  { id: 20, value: "Repair" },
  { id: 21, value: "PM" },
  { id: 23, value: "Installation" },
  { id: 25, value: "URP" },
  { id: 26, value: "Demo" },
  { id: 29, value: "Calibration" },
  { id: 30, value: "Training" },
];

export const DDV6_REP_TYPE = [
  { id: 1, value: "PFRN" },
  { id: 2, value: "OBP" },
  { id: 3, value: "UR" },
  { id: 4, value: "SCC" },
];

export const REPORT_TYPES = [
  { id: "dispatchlist", value: "DISPATCH LIST" },
  { id: "servicecenter", value: "SERVICE CENTER" },
  { id: "stocklist", value: "STOCK LIST" },
  { id: "scraplist", value: "SCRAP LIST" },
];

export const DIVISIONS = ["Cardiac", "Diagnostic", "ICU Support", "Radiology", "Critical Care", "General"];

export const REGIONS = ["Chennai", "Bangalore", "Delhi", "Mumbai", "Kolkata", "Hyderabad", "Pune"];

export const BRANCHES: Record<string, string[]> = {
  Chennai: ["Chennai Central", "Chennai North", "Chennai South"],
  Bangalore: ["Bangalore North", "Bangalore South", "Bangalore East"],
  Delhi: ["Delhi North", "Delhi South", "Delhi West"],
  Mumbai: ["Mumbai West", "Mumbai East", "Mumbai Central"],
  Kolkata: ["Kolkata Central", "Kolkata South"],
  Hyderabad: ["Hyderabad Central", "Hyderabad West"],
  Pune: ["Pune Central", "Pune East"],
};

export const ENGINEERS = [
  { id: 1, name: "Ramesh K.", division: "Cardiac", region: "Chennai" },
  { id: 2, name: "Vinod S.", division: "Diagnostic", region: "Bangalore" },
  { id: 3, name: "Arjun L.", division: "ICU Support", region: "Delhi" },
  { id: 4, name: "Priya T.", division: "Radiology", region: "Mumbai" },
  { id: 5, name: "Kiran M.", division: "Critical Care", region: "Kolkata" },
  { id: 6, name: "Suresh P.", division: "General", region: "Hyderabad" },
  { id: 7, name: "Deepa R.", division: "Diagnostic", region: "Bangalore" },
  { id: 8, name: "Manoj B.", division: "Cardiac", region: "Chennai" },
];

export const DEALERS = [
  { id: 1, name: "MedEquip India", region: "Chennai" },
  { id: 2, name: "HealthTech Solutions", region: "Bangalore" },
  { id: 3, name: "CureMax Pvt Ltd", region: "Delhi" },
  { id: 4, name: "MedLine India", region: "Mumbai" },
  { id: 5, name: "MedEquip East", region: "Kolkata" },
  { id: 6, name: "CareTech Systems", region: "Hyderabad" },
];

export const SUPPLIERS = ["Schiller AG", "Nihon Kohden", "Philips Healthcare", "GE Healthcare", "Draeger", "Mindray"];

export const MODELS = [
  { id: 1, name: "Defibrillator X3", supplier: "Schiller AG" },
  { id: 2, name: "ECG Machine Pro", supplier: "Nihon Kohden" },
  { id: 3, name: "Patient Monitor", supplier: "Philips Healthcare" },
  { id: 4, name: "Ultrasound Unit", supplier: "GE Healthcare" },
  { id: 5, name: "Ventilator V2", supplier: "Draeger" },
  { id: 6, name: "Infusion Pump", supplier: "Mindray" },
  { id: 7, name: "X-Ray Unit", supplier: "Schiller AG" },
  { id: 8, name: "Holter Monitor", supplier: "Schiller AG" },
];
