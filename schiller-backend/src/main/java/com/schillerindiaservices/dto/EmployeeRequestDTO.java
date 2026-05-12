package com.schillerindiaservices.dto;

import lombok.Data;

/**
 * Request DTO for Create / Update employee operations.
 */
@Data
public class EmployeeRequestDTO {

    private String empName;
    private String username;
    private String password;
    private String role;
    private String division;
    private String email;
    private String mobile;
    private String address;
    private String department;
    private Long branchId;
    private Boolean active;
}
