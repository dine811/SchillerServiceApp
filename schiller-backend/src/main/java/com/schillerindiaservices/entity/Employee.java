package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "emp_name", nullable = false, length = 100)
    private String empName;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "active")
    private Boolean active = true;
}
