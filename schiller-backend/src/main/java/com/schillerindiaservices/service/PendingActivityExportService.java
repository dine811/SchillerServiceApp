package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.PendingActivityDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PendingActivityExportService {
    private static final int MAX_EXPORT = 13000;

    private final PendingActivityService pendingActivityService;

    public byte[] exportPendingToExcel(
            String username,
            boolean isAdmin,
            String division,
            String search,
            String from,
            String to
    ) throws IOException {
        var rows = pendingActivityService.findPendingForExport(
                        username,
                        isAdmin,
                        division,
                        search,
                        from,
                        to,
                        PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id")))
                .getContent();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Pending activity");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Division", "Entry Date", "SC Engineer", "Initiate Date",
                    "Activity", "Description", "Responsible", "Pending Form",
                    "Target Date", "Closed Date", "Remarks", "SC Incharge Remarks", "Status"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (PendingActivityDTO p : rows) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(p.getId() == null ? 0 : p.getId());
                row.createCell(col++).setCellValue(nvl(p.getDivision()));
                row.createCell(col++).setCellValue(nvl(p.getEntryDate()));
                row.createCell(col++).setCellValue(nvl(p.getScEnggName() != null ? p.getScEnggName() : p.getScEngg()));
                row.createCell(col++).setCellValue(nvl(p.getInitiateDate()));
                row.createCell(col++).setCellValue(nvl(p.getPendingActivity()));
                row.createCell(col++).setCellValue(nvl(p.getModel()));
                row.createCell(col++).setCellValue(nvl(p.getResponsible()));
                row.createCell(col++).setCellValue(nvl(p.getPendingForm()));
                row.createCell(col++).setCellValue(nvl(p.getTarClosedDate()));
                row.createCell(col++).setCellValue(nvl(p.getClosedDate()));
                row.createCell(col++).setCellValue(nvl(p.getRemarks()));
                row.createCell(col++).setCellValue(nvl(p.getScInchargeRemark()));
                row.createCell(col).setCellValue(nvl(p.getStatusType()));
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    public byte[] exportCompletedToExcel(
            String username,
            boolean isPrivileged,
            String division,
            String search,
            String from,
            String to
    ) throws IOException {
        var rows = pendingActivityService.findCompletedForExport(
                        username,
                        isPrivileged,
                        division,
                        search,
                        from,
                        to,
                        PageRequest.of(0, MAX_EXPORT, Sort.by(Sort.Direction.DESC, "id")))
                .getContent();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Closed activity");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Division", "Entry Date", "SC Engineer", "Initiate Date",
                    "Activity", "Description", "Responsible", "Pending Form",
                    "Target Date", "Closed Date", "Remarks", "SC Incharge Remarks", "Status"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (PendingActivityDTO p : rows) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(p.getId() == null ? 0 : p.getId());
                row.createCell(col++).setCellValue(nvl(p.getDivision()));
                row.createCell(col++).setCellValue(nvl(p.getEntryDate()));
                row.createCell(col++).setCellValue(nvl(p.getScEnggName() != null ? p.getScEnggName() : p.getScEngg()));
                row.createCell(col++).setCellValue(nvl(p.getInitiateDate()));
                row.createCell(col++).setCellValue(nvl(p.getPendingActivity()));
                row.createCell(col++).setCellValue(nvl(p.getModel()));
                row.createCell(col++).setCellValue(nvl(p.getResponsible()));
                row.createCell(col++).setCellValue(nvl(p.getPendingForm()));
                row.createCell(col++).setCellValue(nvl(p.getTarClosedDate()));
                row.createCell(col++).setCellValue(nvl(p.getClosedDate()));
                row.createCell(col++).setCellValue(nvl(p.getRemarks()));
                row.createCell(col++).setCellValue(nvl(p.getScInchargeRemark()));
                row.createCell(col).setCellValue(nvl(p.getStatusType()));
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    private static String nvl(Object o) {
        if (o == null) return "";
        String s = String.valueOf(o);
        return "null".equalsIgnoreCase(s) ? "" : s;
    }
}
