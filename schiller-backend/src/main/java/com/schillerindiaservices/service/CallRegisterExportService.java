package com.schillerindiaservices.service;

import com.schillerindiaservices.entity.CallRegisterDemo;
import com.schillerindiaservices.repository.CallRegisterDemoRepository;
import com.schillerindiaservices.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Legacy ExportCall_adminController / Export_CallRegDao — pending call register export.
 * Legacy ExportClosedCalls_Controller / Export_CallRegDao.ClosedCalls — completed rows.
 */
@Service
@RequiredArgsConstructor
public class CallRegisterExportService {

    private static final int MAX_EXPORT = 13000;

    private final CallRegisterDemoRepository callRegisterDemoRepository;
    private final EmployeeRepository employeeRepository;

    public byte[] exportCompletedToExcel(
            String division,
            String keyword,
            LocalDate fromDate,
            LocalDate toDate
    ) throws IOException {
        String div = division == null ? "" : division.trim();
        String kw = keyword == null ? "" : keyword.trim();
        String fromStr = fromDate == null ? "" : fromDate.toString();
        String toStr = toDate == null ? "" : toDate.toString();
        List<CallRegisterDemo> rows = callRegisterDemoRepository
                .findCompletedAdmin(div, kw, fromStr, toStr,
                        PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id")))
                .getContent();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Closed calls");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Division", "Entry Date", "Call Date", "SC Eng", "Engineer", "Model",
                    "Type of Call", "Remarks", "Status"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (CallRegisterDemo c : rows) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(c.getId() != null ? c.getId() : 0);
                row.createCell(col++).setCellValue(nvl(c.getDivision()));
                row.createCell(col++).setCellValue(nvl(c.getEntryDate()));
                row.createCell(col++).setCellValue(nvl(c.getCallDate()));
                row.createCell(col++).setCellValue(resolveScEngName(c.getScEngg()));
                row.createCell(col++).setCellValue(nvl(c.getEngineer()));
                row.createCell(col++).setCellValue(nvl(c.getModel()));
                row.createCell(col++).setCellValue(nvl(c.getTypeOfCall()));
                row.createCell(col++).setCellValue(nvl(c.getRemarks()));
                row.createCell(col).setCellValue(nvl(c.getStatusType()));
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    public byte[] exportPendingToExcel(
            String division,
            String keyword,
            LocalDate fromDate,
            LocalDate toDate
    ) throws IOException {
        String div = division == null ? "" : division.trim();
        String kw = keyword == null ? "" : keyword.trim();
        List<CallRegisterDemo> rows = callRegisterDemoRepository
                .findPendingAdmin(div, kw, PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id")))
                .getContent();

        if (fromDate != null || toDate != null) {
            LocalDate from = fromDate != null ? fromDate : LocalDate.of(1970, 1, 1);
            LocalDate to = toDate != null ? toDate : LocalDate.of(2099, 12, 31);
            rows = rows.stream()
                    .filter(c -> {
                        LocalDate cd = parseCallDate(c.getCallDate());
                        if (cd == null) return false;
                        return !cd.isBefore(from) && !cd.isAfter(to);
                    })
                    .collect(Collectors.toList());
        }

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Call register");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Division", "Entry Date", "Call Date", "SC Eng", "Engineer", "Model",
                    "Type of Call", "Remarks", "Status"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (CallRegisterDemo c : rows) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(c.getId() != null ? c.getId() : 0);
                row.createCell(col++).setCellValue(nvl(c.getDivision()));
                row.createCell(col++).setCellValue(nvl(c.getEntryDate()));
                row.createCell(col++).setCellValue(nvl(c.getCallDate()));
                row.createCell(col++).setCellValue(resolveScEngName(c.getScEngg()));
                row.createCell(col++).setCellValue(nvl(c.getEngineer()));
                row.createCell(col++).setCellValue(nvl(c.getModel()));
                row.createCell(col++).setCellValue(nvl(c.getTypeOfCall()));
                row.createCell(col++).setCellValue(nvl(c.getRemarks()));
                row.createCell(col).setCellValue(nvl(c.getStatusType()));
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    private String resolveScEngName(String scEngg) {
        if (scEngg == null || scEngg.isBlank()) return "";
        try {
            long eid = Long.parseLong(scEngg.trim());
            return employeeRepository.findById(eid).map(e -> e.getEmpName()).orElse(scEngg);
        } catch (NumberFormatException e) {
            return scEngg;
        }
    }

    private static String nvl(Object o) {
        if (o == null) return "";
        String s = String.valueOf(o);
        return "null".equalsIgnoreCase(s) ? "" : s;
    }

    private static LocalDate parseCallDate(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String s = raw.trim();
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ignored) {
        }
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
        } catch (Exception ignored) {
        }
        for (String p : new String[] { "dd-MM-yyyy", "dd/MM/yyyy", "d-M-yyyy" }) {
            try {
                String sub = s.length() >= 10 ? s.substring(0, 10) : s;
                return LocalDate.parse(sub, DateTimeFormatter.ofPattern(p));
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
