package com.schillerindiaservices.dto;

import lombok.Data;

/**
 * Partial update — only non-null fields are applied (legacy CallRegisterDao.update on
 * {@code callregister_demo}: remarks, duration, status_type, call_date, call_type, type_of_call,
 * type_of_communication).
 */
@Data
public class CallRegisterUpdateRequest {
    private String remarks;
    private String duration;
    private String statusType;
    private String callDate;
    private String callType;
    private String typeOfCall;
    private String typeOfCommunication;
}
