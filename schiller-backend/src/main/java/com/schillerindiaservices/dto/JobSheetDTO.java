package com.schillerindiaservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@code jobsheet} — mirrors legacy {@code CreateJobSheet.jsp} rows (main + 4 extra lines).
 * {@link #serviceStatus} is optional and updates {@code service_master.status} when present (legacy SAVE).
 */
@Data
@NoArgsConstructor
public class JobSheetDTO {

    private Integer id;
    private Long serId;

    private String repairDate;
    private String enginnerName;
    private String observation;
    private String repairActivity;
    private String timeSpent;
    private String remark;

    private String repairDate1;
    private String enginnerName1;
    private String observation1;
    private String repairActivity1;
    private String timeSpent1;
    private String remark1;

    private String repairDate2;
    private String enginnerName2;
    private String observation2;
    private String repairActivity2;
    private String timeSpent2;
    private String remark2;

    private String repairDate3;
    private String enginnerName3;
    private String observation3;
    private String repairActivity3;
    private String timeSpent3;
    private String remark3;

    private String repairDate4;
    private String enginnerName4;
    private String observation4;
    private String repairActivity4;
    private String timeSpent4;
    private String remark4;

    /** When set on create/update, also updates {@code service_master.status} (Pending / Completed). */
    private String serviceStatus;
}
