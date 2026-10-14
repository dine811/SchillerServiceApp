package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.RepairDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairExportService {
    private final RepairService repairService;

    public byte[] export(String category, String status, LocalDate from, LocalDate to,
                         String division, String scRefNo, String search) throws IOException {
        List<RepairDTO> rows = repairService.findForExport(category, status, from, to, division, scRefNo, search);
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Repair List");
            String[] headers = {"ENTRY DATE", "DIVISION", "SC REF NO", "DEF GIR NO", "CATEGORY", "MODEL",
                    "DEF BOARD/MODULE NAME", "TECH REMARKS", "COMPONENTS USED TO REPAIR", "FINISHED DATE",
                    "REPAIRED BY", "FINAL REMARKS", "STATUS", "NO OF DAYS"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);
            int rowIndex = 1;
            for (RepairDTO repair : rows) {
                Row row = sheet.createRow(rowIndex++);
                int c = 0;
                row.createCell(c++).setCellValue(value(repair.getEntryDate()));
                row.createCell(c++).setCellValue(value(repair.getDivision()));
                row.createCell(c++).setCellValue(value(repair.getScRefNo()));
                row.createCell(c++).setCellValue(value(repair.getDefGirNo()));
                row.createCell(c++).setCellValue(value(repair.getCategory()));
                row.createCell(c++).setCellValue(value(repair.getModel()));
                row.createCell(c++).setCellValue(value(repair.getDefBoardModuleName()));
                row.createCell(c++).setCellValue(value(repair.getTechnicalRemarks()));
                row.createCell(c++).setCellValue(value(repair.getComponentsUsed()));
                row.createCell(c++).setCellValue(value(repair.getFinishDate()));
                row.createCell(c++).setCellValue(value(repair.getRepairedBy()));
                row.createCell(c++).setCellValue(value(repair.getFinalRemarks()));
                row.createCell(c++).setCellValue(repair.getStatus() == null ? "" : repair.getStatus().databaseValue());
                row.createCell(c).setCellValue(repair.getDays());
            }
            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
            workbook.write(output);
            return output.toByteArray();
        }
    }

    private static String value(Object value) {
        return value == null ? "" : value.toString();
    }
}
