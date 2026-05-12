package com.schillerindiaservices.dto;

import lombok.Data;

/**
 * Pending activity row from {@code pendingact_master}.
 */
@Data
public class PendingActivityDTO {
    private Long id;
    private String division;
    private String scEngg;
    private String scEnggName;
    private String entryDate;
    private String initiateDate;
    private String model;
    private String pendingActivity;
    private String responsible;
    private String pendingForm;
    private String tarClosedDate;
    private String closedDate;
    private String remarks;
    private String statusType;
    private String scInchargeRemark;
}
