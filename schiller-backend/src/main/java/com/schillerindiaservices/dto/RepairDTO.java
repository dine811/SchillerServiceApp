package com.schillerindiaservices.dto;

import com.schillerindiaservices.entity.RepairCategory;
import com.schillerindiaservices.entity.RepairStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RepairDTO {
    private Integer id;
    private Integer serviceId;
    private LocalDate entryDate;
    private String division;
    private String scRefNo;
    private String defGirNo;
    private RepairCategory category;
    private String model;
    private String defBoardModuleName;
    private String unitStatus;
    private String technicalRemarks;
    private String componentsUsed;
    private LocalDate finishDate;
    private String repairedBy;
    private String finalRemarks;
    private RepairStatus status;
    private int days;
    private int pendingDays;
}
