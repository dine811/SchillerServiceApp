package com.schillerindiaservices.dto;

import lombok.Data;

@Data
public class NonsaleableAdminRowDTO {
    private Integer id;
    private String entryDate;
    private String unitDetails;
    private String fqcInDate;
    private String model;
    private String modelName;
    private String fqcObservation;
    private String scInwardDate;
    private String scObservation;
    private String modelSn;
    private String tentDateShipDate;
    private String shipDateFqc;
    private String finalStatus;
}
