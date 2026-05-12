package com.schillerindiaservices.service;

import com.schillerindiaservices.entity.*;
import com.schillerindiaservices.repository.*;
import com.schillerindiaservices.util.ScrapPendingMetrics;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Separate service for exporting Service Master data to Excel.
 * Referenced from legacy ServiceList.jsp / ServiceDao.Excel.
 */
@Service
@RequiredArgsConstructor
public class ServiceExportService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final int MAX_EXPORT = 13000;

    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelRepository modelRepository;
    private final DropdownMasterRepository dropdownRepository;
    private final BranchRepository branchRepository;
    private final DealerRepository dealerRepository;

    /**
     * Legacy ExportUnderRepairController / UnderRepairDao: same row set as under-repair grid.
     */
    public byte[] exportUnderRepairToExcel(String scRef, String search) throws IOException {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = search == null ? "" : search.trim();
        var page = serviceRepository.findUnderRepair(sc, kw,
                PageRequest.of(0, MAX_EXPORT, org.springframework.data.domain.Sort.by(
                        org.springframework.data.domain.Sort.Direction.DESC, "scRefNo")));
        return buildQueueGridExcel(page.getContent(), "Under Repair");
    }

    /** Legacy ExportPFRNController / EmpPFRNDao — same row set as pending FRN grid; engineers: division and/or SC/RA id. */
    public byte[] exportPendingFrnToExcel(String scRef, String search, String username, boolean isAdmin) throws IOException {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = search == null ? "" : search.trim();
        boolean admin = isAdmin;
        String div = "";
        Integer empId = -1;
        if (!isAdmin) {
            Employee employee = employeeRepository.findByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new RuntimeException("Employee not found for username: " + username));
            div = employee.getDivision() == null ? "" : employee.getDivision().trim();
            empId = employee.getEmpId().intValue();
        }
        var page = serviceRepository.findPendingFrn(sc, kw, admin, div, empId,
                PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "scRefNo")));
        return buildQueueGridExcel(page.getContent(), "Pending FRN");
    }

    /** Legacy OB pending export — same 15 columns as ob_pending.jsp grid. */
    public byte[] exportObPendingToExcel(String scRef, String search) throws IOException {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = search == null ? "" : search.trim();
        var page = serviceRepository.findObPending(sc, kw,
                PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "scRefNo")));
        return buildQueueGridExcel(page.getContent(), "OB Pending");
    }

    /** Legacy closedproduct.jsp grid export — 14 columns, no Final Remarks; last column days since SC received. */
    public byte[] exportScClosedProductToExcel(String scRef, String search) throws IOException {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = search == null ? "" : search.trim();
        var page = serviceRepository.findScClosedProduct(sc, kw,
                PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "scRefNo")));
        return buildScClosedGridExcel(page.getContent(), "SC Closed Product");
    }

    /** Legacy New_ClosedProduct.jsp grid — same 14 columns as SC closed grid; commercial ship date set. */
    public byte[] exportNewClosedProductToExcel(String scRef, String search) throws IOException {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = search == null ? "" : search.trim();
        var page = serviceRepository.findNewClosedProduct(sc, kw,
                PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "scRefNo")));
        return buildScClosedGridExcel(page.getContent(), "Closed Product");
    }

    /** Legacy ScarpList.jsp grid: SCRAPPED rows; same filters as GET /scrap-list. */
    public byte[] exportScrapListToExcel(
            String division,
            Integer month,
            Integer year,
            String scRef,
            String search
    ) throws IOException {
        String div = division == null ? "" : division.trim();
        String sc = scRef == null ? "" : scRef.trim();
        String kw = search == null ? "" : search.trim();
        var page = serviceRepository.findScrapList(div, month, year, sc, kw,
                PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "scRefNo")));
        return buildScrapListGridExcel(page.getContent());
    }

    public byte[] exportToExcel(LocalDate fromDate, LocalDate toDate, String search) throws IOException {
        List<ServiceMaster> records = fetchRecords(fromDate, toDate, search);
        Map<Long, String> employeeMap = buildEmployeeMap();
        Map<String, String> modelMap = buildModelMap();
        Map<String, String> ddMap = buildDropdownMap();
        Map<String, String> branchMap = buildBranchMap();
        Map<String, String> dealerMap = buildDealerMap();

        return buildExcel(records, employeeMap, modelMap, ddMap, branchMap, dealerMap);
    }

    public byte[] exportToExcelForUser(
            LocalDate fromDate,
            LocalDate toDate,
            String search,
            String username,
            boolean admin
    ) throws IOException {
        List<ServiceMaster> records = fetchRecordsForUser(fromDate, toDate, search, username, admin);
        Map<Long, String> employeeMap = buildEmployeeMap();
        Map<String, String> modelMap = buildModelMap();
        Map<String, String> ddMap = buildDropdownMap();
        Map<String, String> branchMap = buildBranchMap();
        Map<String, String> dealerMap = buildDealerMap();

        return buildExcel(records, employeeMap, modelMap, ddMap, branchMap, dealerMap);
    }

    private List<ServiceMaster> fetchRecords(LocalDate fromDate, LocalDate toDate, String search) {
        List<ServiceMaster> records;
        if (search != null && !search.isBlank()) {
            records = serviceRepository.searchServices(search.trim(), PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id"))).getContent();
        } else {
            records = serviceRepository.findAll(PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id"))).getContent();
        }
        if (fromDate != null || toDate != null) {
            records = records.stream()
                    .filter(s -> {
                        if (s.getEntryDate() == null) return false;
                        if (fromDate != null && s.getEntryDate().isBefore(fromDate)) return false;
                        if (toDate != null && s.getEntryDate().isAfter(toDate)) return false;
                        return true;
                    })
                    .toList();
        }
        return records;
    }

    private List<ServiceMaster> fetchRecordsForUser(
            LocalDate fromDate,
            LocalDate toDate,
            String search,
            String username,
            boolean admin
    ) {
        List<ServiceMaster> records;
        if (admin) {
            records = fetchRecords(fromDate, toDate, search);
            return records;
        }

        Employee employee = employeeRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Employee not found for username: " + username));
        String division = employee.getDivision();
        if (division == null || division.isBlank()) {
            return List.of();
        }

        if (search != null && !search.isBlank()) {
            records = serviceRepository.searchByLegacyEngineerScope(division.trim(), employee.getEmpId(), search.trim(),
                    PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id"))).getContent();
        } else {
            records = serviceRepository.findByLegacyEngineerScope(division.trim(), employee.getEmpId(),
                    PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id"))).getContent();
        }
        if (fromDate != null || toDate != null) {
            records = records.stream()
                    .filter(s -> {
                        if (s.getEntryDate() == null) return false;
                        if (fromDate != null && s.getEntryDate().isBefore(fromDate)) return false;
                        if (toDate != null && s.getEntryDate().isAfter(toDate)) return false;
                        return true;
                    })
                    .toList();
        }
        return records;
    }

    private Map<Long, String> buildEmployeeMap() {
        Map<Long, String> map = new HashMap<>();
        employeeRepository.findAll().forEach(e -> map.put(e.getEmpId(), e.getEmpName()));
        return map;
    }

    private Map<String, String> buildModelMap() {
        Map<String, String> map = new HashMap<>();
        modelRepository.findAll().forEach(m -> map.put(String.valueOf(m.getModelId()), m.getModelName()));
        return map;
    }

    private Map<String, String> buildDropdownMap() {
        Map<String, String> map = new HashMap<>();
        for (int g = 1; g <= 6; g++) {
            final int group = g;
            dropdownRepository.findByDdName(String.valueOf(group)).forEach(d -> map.put(group + ":" + d.getId(), d.getDdValue()));
        }
        return map;
    }

    private Map<String, String> buildBranchMap() {
        Map<String, String> map = new HashMap<>();
        branchRepository.findAll().forEach(b -> map.put(String.valueOf(b.getId()), b.getBranchName()));
        return map;
    }

    private Map<String, String> buildDealerMap() {
        Map<String, String> map = new HashMap<>();
        dealerRepository.findAll().forEach(d -> map.put(String.valueOf(d.getDealerId()), d.getDealerName()));
        return map;
    }

    private String ddLabel(Map<String, String> ddMap, int group, Object id) {
        if (id == null || (id instanceof String s && s.isBlank())) return "";
        String key = group + ":" + id;
        String val = ddMap.get(key);
        return val != null ? val : "";
    }

    /** Same 15 columns as legacy under_repair.jsp / pending_FRN.jsp grids. */
    private byte[] buildQueueGridExcel(List<ServiceMaster> records, String sheetName) throws IOException {
        Map<Long, String> employeeMap = buildEmployeeMap();
        Map<String, String> modelMap = buildModelMap();
        Map<String, String> ddMap = buildDropdownMap();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(sheetName);

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Entry Date", "SC Ref No", "SC Eng", "FRN No", "Region", "Eng",
                    "Cust Name", "Model", "Unit Status", "Def Mod / brd", "Def GIR No",
                    "Final Remarks", "Type of work", "PDays"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            LocalDate today = LocalDate.now();
            for (ServiceMaster s : records) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(s.getId() != null ? s.getId() : 0);
                row.createCell(col++).setCellValue(s.getEntryDate() != null ? s.getEntryDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(nvl(s.getScRefNo()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getScEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getFrnNo()));
                row.createCell(col++).setCellValue(nvl(s.getRegion()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getRaEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getCustName()));
                row.createCell(col++).setCellValue(resolveModel(modelMap, s.getProductModel()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 2, s.getUnitStatus()));
                row.createCell(col++).setCellValue(nvl(s.getDefModBrdName()));
                row.createCell(col++).setCellValue(nvl(s.getDefGirNo()));
                row.createCell(col++).setCellValue(nvl(s.getFinalRemarks()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 5, s.getTypeOfWork()));
                long pd = s.getSerCentreReceivedDate() == null
                        ? 0L
                        : ChronoUnit.DAYS.between(s.getSerCentreReceivedDate(), today);
                row.createCell(col).setCellValue(pd);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    /** ScarpList.jsp: Status + 4 pending-day columns (PFRN, OBP, URP, SCC). */
    private byte[] buildScrapListGridExcel(List<ServiceMaster> records) throws IOException {
        Map<Long, String> employeeMap = buildEmployeeMap();
        Map<String, String> modelMap = buildModelMap();
        Map<String, String> ddMap = buildDropdownMap();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Scrap list");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Status", "Entry Date", "SC Ref No", "SC Eng", "FRN No", "Region", "Eng",
                    "Cust Name", "Model", "Unit Status", "Def Mod / brd", "Def GIR No", "Type of work",
                    "Pend. Days (PFRN)", "Pend. Days (OBP)", "Pend. Days (URP)", "Pend. Days (SCC)"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (ServiceMaster s : records) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(s.getId() != null ? s.getId() : 0);
                row.createCell(col++).setCellValue(nvl(s.getStatus()));
                row.createCell(col++).setCellValue(s.getEntryDate() != null ? s.getEntryDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(nvl(s.getScRefNo()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getScEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getFrnNo()));
                row.createCell(col++).setCellValue(nvl(s.getRegion()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getRaEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getCustName()));
                row.createCell(col++).setCellValue(resolveModel(modelMap, s.getProductModel()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 2, s.getUnitStatus()));
                row.createCell(col++).setCellValue(nvl(s.getDefModBrdName()));
                row.createCell(col++).setCellValue(nvl(s.getDefGirNo()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 5, s.getTypeOfWork()));
                row.createCell(col++).setCellValue(ScrapPendingMetrics.pfrn(s));
                row.createCell(col++).setCellValue(ScrapPendingMetrics.obp(s));
                row.createCell(col++).setCellValue(ScrapPendingMetrics.urp(s));
                row.createCell(col).setCellValue(ScrapPendingMetrics.scc(s));
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    /** 14 columns per legacy closedproduct.jsp (no Final Remarks). */
    private byte[] buildScClosedGridExcel(List<ServiceMaster> records, String sheetName) throws IOException {
        Map<Long, String> employeeMap = buildEmployeeMap();
        Map<String, String> modelMap = buildModelMap();
        Map<String, String> ddMap = buildDropdownMap();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(sheetName);

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Entry Date", "SC Ref No", "SC Eng", "FRN No", "Region", "Eng",
                    "Cust Name", "Model", "Unit Status", "Def Mod / brd", "Def GIR No",
                    "Type of work", "Days taken to complete"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            LocalDate today = LocalDate.now();
            for (ServiceMaster s : records) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(s.getId() != null ? s.getId() : 0);
                row.createCell(col++).setCellValue(s.getEntryDate() != null ? s.getEntryDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(nvl(s.getScRefNo()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getScEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getFrnNo()));
                row.createCell(col++).setCellValue(nvl(s.getRegion()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getRaEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getCustName()));
                row.createCell(col++).setCellValue(resolveModel(modelMap, s.getProductModel()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 2, s.getUnitStatus()));
                row.createCell(col++).setCellValue(nvl(s.getDefModBrdName()));
                row.createCell(col++).setCellValue(nvl(s.getDefGirNo()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 5, s.getTypeOfWork()));
                long pd = s.getSerCentreReceivedDate() == null
                        ? 0L
                        : ChronoUnit.DAYS.between(s.getSerCentreReceivedDate(), today);
                row.createCell(col).setCellValue(pd);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    private byte[] buildExcel(List<ServiceMaster> records, Map<Long, String> employeeMap,
                             Map<String, String> modelMap, Map<String, String> ddMap,
                             Map<String, String> branchMap, Map<String, String> dealerMap) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Service List");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                "Division", "Entry Date", "SC Ref No", "SC Engineer", "RA Engineer", "FRN No", "FRN Date",
                "Ser Comm Inward", "Ser Centre Received", "Stk/Cust", "Region", "Branch", "Dealer", "Customer",
                "Supplier", "Model", "Unit SL No", "Unit Status", "Def Mod/Brd", "Def GIR No", "Type of Work",
                "DC No", "Disp Adv", "Rep Brd Adv", "Comrcl Rmrk", "Status"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (ServiceMaster s : records) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;

                row.createCell(col++).setCellValue(nvl(s.getDivision()));
                row.createCell(col++).setCellValue(s.getEntryDate() != null ? s.getEntryDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(nvl(s.getScRefNo()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getScEngineerId()));
                row.createCell(col++).setCellValue(resolveEmployee(employeeMap, s.getRaEngineerId()));
                row.createCell(col++).setCellValue(nvl(s.getFrnNo()));
                row.createCell(col++).setCellValue(s.getFrnDate() != null ? s.getFrnDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(s.getSerCommInwardDate() != null ? s.getSerCommInwardDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(s.getSerCentreReceivedDate() != null ? s.getSerCentreReceivedDate().format(DATE_FMT) : "");
                row.createCell(col++).setCellValue(ddLabel(ddMap, 1, s.getStkCust()));
                row.createCell(col++).setCellValue(nvl(s.getRegion()));
                row.createCell(col++).setCellValue(resolveBranch(branchMap, s.getBranch()));
                row.createCell(col++).setCellValue(resolveDealer(dealerMap, s.getDealerName()));
                row.createCell(col++).setCellValue(nvl(s.getCustName()));
                row.createCell(col++).setCellValue(nvl(s.getSupplierName()));
                row.createCell(col++).setCellValue(resolveModel(modelMap, s.getProductModel()));
                row.createCell(col++).setCellValue(nvl(s.getUnitSlNo()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 2, s.getUnitStatus()));
                row.createCell(col++).setCellValue(nvl(s.getDefModBrdName()));
                row.createCell(col++).setCellValue(nvl(s.getDefGirNo()));
                row.createCell(col++).setCellValue(ddLabel(ddMap, 5, s.getTypeOfWork()));
                row.createCell(col++).setCellValue(nvl(s.getDcNo()));
                row.createCell(col++).setCellValue(nvl(s.getDispAdvNo()));
                row.createCell(col++).setCellValue(nvl(s.getRepairedBrdAdvNo()));
                row.createCell(col++).setCellValue(nvl(s.getComrclDtlInvRmrk()));
                row.createCell(col++).setCellValue(nvl(s.getReportType()));
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    private String nvl(Object o) {
        if (o == null) return "";
        String s = String.valueOf(o);
        return "null".equalsIgnoreCase(s) ? "" : s;
    }

    private String resolveEmployee(Map<Long, String> map, Integer id) {
        if (id == null) return "";
        String name = map.get(id.longValue());
        return name != null ? name : "";
    }

    private String resolveModel(Map<String, String> map, String id) {
        if (id == null || id.isBlank()) return "";
        String name = map.get(id.trim());
        return name != null ? name : "";
    }

    private String resolveBranch(Map<String, String> map, String id) {
        if (id == null || id.isBlank()) return "";
        String name = map.get(id.trim());
        return name != null ? name : "";
    }

    private String resolveDealer(Map<String, String> map, String idOrName) {
        if (idOrName == null || idOrName.isBlank()) return "";
        String name = map.get(idOrName.trim());
        return name != null ? name : idOrName;
    }
}
