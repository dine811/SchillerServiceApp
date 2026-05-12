package com.schillerindiaservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartnumberEntryCreateRequest {

    @NotBlank
    private String partNumber;

    @NotBlank
    private String brdName;

    @NotBlank
    private String compatibleModels;

    @NotNull
    private BigDecimal cost;
}
