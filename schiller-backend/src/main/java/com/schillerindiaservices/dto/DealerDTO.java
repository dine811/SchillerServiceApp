package com.schillerindiaservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealerDTO {
    private Long dealerId;
    private String dealerName;
    private String dealerAddress;
    private String dealerMail;
    private String dealerPhone;
    private Long regionId;
}
