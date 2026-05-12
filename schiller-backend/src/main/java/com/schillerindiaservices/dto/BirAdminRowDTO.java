package com.schillerindiaservices.dto;

import lombok.Data;

@Data
public class BirAdminRowDTO {
    private Integer id;
    private String fqcInDate;
    private String division;
    private String model;
    private String modelName;
    private String configuration;
    private String receivedQty;
    private String softwrChanges;
    private String hardwrChanges;
    private String accesoryDetails;
    private String cnrRefNo;
    private String birRefNo;
    private String finalStatus;
}
