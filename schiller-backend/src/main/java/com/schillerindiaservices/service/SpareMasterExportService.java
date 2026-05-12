package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.SpareMasterDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SpareMasterExportService {

    private final SpareMasterService spareMasterService;

    public byte[] exportPendingToExcel(
            String username,
            boolean isPrivileged,
            String division,
            String search,
            String from,
            String to
    ) throws IOException {
        var rows = spareMasterService.findPendingForExport(
                username,
                isPrivileged,
                division,
                search,
                parseDate(from),
                parseDate(to)
        ).getContent();
        return buildWorkbook(rows, "Spares pending");
    }

    public byte[] exportCompletedToExcel(
            String username,
            boolean isPrivileged,
            String division,
            String search,
            String from,
            String to
    ) throws IOException {
        var rows = spareMasterService.findCompletedForExport(
                username,
                isPrivileged,
                division,
                search,
                parseDate(from),
                parseDate(to)
        ).getContent();
        return buildWorkbook(rows, "Spares completed");
    }

    private byte[] buildWorkbook(java.util.List<SpareMasterDTO> rows, String sheetName) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(sheetName);
            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {
                    "Id", "Entry Date", "Division", "Supplier", "Model", "Part Number",
                    "Def Mod/Brd Name", "Reason", "Reference", "GIR No",
                    "SC Engineer", "Issued By", "Returnable Status", "Qty", "Remarks", "Final Status"
            };
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (SpareMasterDTO s : rows) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                row.createCell(col++).setCellValue(s.getId() == null ? 0 : s.getId());
                row.createCell(col++).setCellValue(nvl(s.getEntryDate()));
                row.createCell(col++).setCellValue(nvl(s.getDivision()));
                row.createCell(col++).setCellValue(nvl(s.getSupplier()));
                row.createCell(col++).setCellValue(nvl(s.getModel()));
                row.createCell(col++).setCellValue(nvl(s.getPartNumber()));
                row.createCell(col++).setCellValue(nvl(s.getDefModBrdName()));
                row.createCell(col++).setCellValue(nvl(s.getReason()));
                row.createCell(col++).setCellValue(nvl(s.getReference()));
                row.createCell(col++).setCellValue(nvl(s.getGirNo()));
                row.createCell(col++).setCellValue(nvl(s.getScEnggName() != null ? s.getScEnggName() : s.getScEngg()));
                row.createCell(col++).setCellValue(nvl(s.getIssuedBy()));
                row.createCell(col++).setCellValue(nvl(s.getReturnableStatus()));
                row.createCell(col++).setCellValue(nvl(s.getQty()));
                row.createCell(col++).setCellValue(nvl(s.getRemarks()));
                row.createCell(col).setCellValue(nvl(s.getFinalStatus()));
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return LocalDate.parse(value.trim());
        } catch (Exception ex) {
            return null;
        }
    }

    private static String nvl(Object value) {
        if (value == null) return "";
        String s = String.valueOf(value);
        return "null".equalsIgnoreCase(s) ? "" : s;
    }
}
