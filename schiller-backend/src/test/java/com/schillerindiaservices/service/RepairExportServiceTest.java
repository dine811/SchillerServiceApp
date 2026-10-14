package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.RepairDTO;
import com.schillerindiaservices.entity.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RepairExportServiceTest {
    @Test void writesExactFourteenColumnContract() throws Exception {
        RepairService service = mock(RepairService.class);
        RepairDTO repair = new RepairDTO(); repair.setCategory(RepairCategory.OB);
        repair.setStatus(RepairStatus.COMPLETED); repair.setDays(2);
        when(service.findForExport("OB", "completed", null, null, null, null, null)).thenReturn(List.of(repair));
        byte[] bytes = new RepairExportService(service).export("OB", "completed", null, null, null, null, null);
        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            var sheet = workbook.getSheetAt(0);
            assertThat(sheet.getRow(0).getLastCellNum()).isEqualTo((short)14);
            assertThat(sheet.getRow(0).getCell(0).getStringCellValue()).isEqualTo("ENTRY DATE");
            assertThat(sheet.getRow(0).getCell(13).getStringCellValue()).isEqualTo("NO OF DAYS");
            assertThat(sheet.getRow(1).getCell(4).getStringCellValue()).isEqualTo("OB");
            assertThat(sheet.getRow(1).getCell(12).getStringCellValue()).isEqualTo("completed");
        }
    }
}
