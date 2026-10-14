package com.schillerindiaservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RepairUpdateRequest {
    @NotBlank
    @Size(max = 300)
    private String repairedBy;
    @Size(max = 1000)
    private String componentsUsed;
    @Size(max = 300)
    private String technicalRemarks;
    @Size(max = 100)
    private String finalRemarks;
    @NotNull
    private String status;
}
