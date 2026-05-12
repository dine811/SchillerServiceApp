package com.schillerindiaservices.dto;

import lombok.Data;

@Data
public class SpareMasterRequest {
    private String division;
    private String scEngg;
    private String entryDate;
    private String supplier;
    private String model;
    private String partNumber;
    private String defModBrdName;
    private String reason;
    private String reference;
    private String girNo;
    private String issuedBy;
    private String returnableStatus;
    private String remarks;
    private String returnedDate;
    private String finalStatus;
    private String qty;
}
