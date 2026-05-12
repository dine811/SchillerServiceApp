package com.schillerindiaservices.dto;

import lombok.Data;

/**
 * One row for PRFOB_AdminList.jsp — resolved SC engineer, branch, and engineer names.
 */
@Data
public class PrfobAdminRowDTO {
    private Integer id;
    private String division;
    /** ISO yyyy-MM-dd or null */
    private String entryDate;
    private String scEngg;
    private String scEnggName;
    private String workType;
    private String raisedDate;
    private String receivedDate;
    private String region;
    private String branch;
    private String branchName;
    private String engineer;
    private String engineerName;
    private String dealer;
    private String model;
    private String supplier;
    private String warrentyStatus;
    private String prfobRefNo;
    private String crmRefNo;
    private String remarks;
    private String statusType;
    private String executedDate;
    private String partType;
    private String partDescription;
    private String receiveDateFromSc;
}
