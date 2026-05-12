package com.schillerindiaservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/** Call register row for CallListAdmin.jsp grid / export. */
@Data
@NoArgsConstructor
public class CallRegisterDTO {
    private Long id;
    private String division;
    private String entryDate;
    private String callDate;
    /** Raw sc_engg id from DB */
    private String scEngg;
    /** Resolved from employee master when possible */
    private String scEnggName;
    private String engineer;
    private String model;
    /** Legacy call_type */
    private String callType;
    private String typeOfCall;
    private String typeOfCommunication;
    private String duration;
    private String remarks;
    private String statusType;
}
