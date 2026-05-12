package com.schillerindiaservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ServiceMasterDTO {
    private Long id;
    private String scRefNo;
    private LocalDate entryDate;
    private String division;
    private String frnNo;
    private LocalDate frnDate;
    private LocalDate serCommInwardDate;
    private LocalDate serCentreReceivedDate;
    private String stkCust;
    private String region;
    private String branch;
    private String dealerName;
    private String custName;
    private String supplierName;
    private String productModel;
    private String modelConfig;
    private String unitSlNo;
    private String unitStatus;
    private LocalDate dod;
    private String modBrdName;
    private String defModBrdName;
    private String defType;
    private String typeOfAcc;
    private String defPartSn;
    private String defGirNo;
    private String repType;
    private String repGirNo;
    private String defUnitGirNo;
    private String fieldRemarks;
    private String technicalRemarks;
    private String componentsUsedForRepair;
    private String finalRemarks;
    private String comrclDtlInvRmrk;
    private String typeOfWork;
    private LocalDate shipDtFrmSerCntr;
    private LocalDate repairedBrdStkDate;
    private LocalDate shipDateFromCommercial;
    private String dcNo;
    private String dispAdvNo;
    private String repairedBrdAdvNo;
    private String partNumber;
    private String compatibleModels;
    private String destination;
    private BigDecimal cost;
    private Integer scEngineerId;
    private Integer raEngineerId;
    private String reportType;

    /** Legacy workflow status (Pending / Completed). */
    private String status;

    /** Legacy repair_status (NR / RP / RC) for engineer queue actions. */
    private String repairStatus;
    
    // Virtual fields for joined tables if needed later
    private String scEngineerName;
    private String raEngineerName;

    /** Days since ser centre received date (under-repair, OB/Pending FRN queues, SC closed “days taken”). */
    private Long pendingDays;

    /** Scrap list (ScarpList.jsp): legacy “Pend. Days” columns — strings may include suffixes (e.g. "5 SC"). */
    private String pendDaysPfrn;
    private String pendDaysObp;
    private String pendDaysUrp;
    private String pendDaysScc;
}
