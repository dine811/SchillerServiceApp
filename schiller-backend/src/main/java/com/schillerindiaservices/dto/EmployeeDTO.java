package com.schillerindiaservices.dto;

import lombok.Data;

/**
 * DTO for Employee — excludes the password field from API responses.
 */
@Data
public class EmployeeDTO {

    private Long empId;
    private String empName;
    private String username;
    private String role;
    private String division;
    private String email;
    private String mobile;
    private String address;
    private String department;
    private Long branchId;
    private Boolean active;
}
