package com.schillerindiaservices.dto;

import lombok.Data;

@Data
public class MailIdEntryDTO {
    private Integer mailIdEntryId;
    private String mailId;
    private String reportType;
    private String division; // Mapped to temp1 in Database
    private String temp2; // Unused
}
