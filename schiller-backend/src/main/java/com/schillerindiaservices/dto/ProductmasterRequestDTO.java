package com.schillerindiaservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductmasterRequestDTO {
    private String division;
    private String divisionName;
    private List<ModelDTO> models;
}
