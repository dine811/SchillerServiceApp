package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Legacy table {@code dropdownmaster}: {@code dd_id}, {@code ddname} (group as "1".."6"), {@code ddvalue}.
 * Flyway V1 used {@code dropdown_master} with {@code dd_group}; V20 copies into this shape for one JPA mapping.
 */
@Data
@Entity
@Table(name = "dropdownmaster")
public class DropdownMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dd_id")
    private Long id;

    @Column(name = "ddname")
    private String ddName;

    @Column(name = "ddvalue")
    private String ddValue;
}
