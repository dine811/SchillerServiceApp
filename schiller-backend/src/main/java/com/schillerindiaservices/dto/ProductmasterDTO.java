package com.schillerindiaservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductmasterDTO {
    private Integer productId;
    private String division;
    private String divisionName;
    private List<ModelDTO> models;
}
