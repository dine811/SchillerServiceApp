package com.schillerindiaservices.dto;

import lombok.Data;

/**
 * Create/update payload for pending activity register.
 */
@Data
public class PendingActivityRequest {
    private String division;
    private String scEngg;
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
