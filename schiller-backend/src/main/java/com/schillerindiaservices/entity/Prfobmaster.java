package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Legacy {@code prfobmaster} — PRF/OB register (pending admin list, export).
 */
@Entity
@Table(name = "prfobmaster")
@Data
@NoArgsConstructor
public class Prfobmaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String division;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "sc_engg", length = 100)
    private String scEngg;

    @Column(name = "work_type", length = 100)
    private String workType;

    @Column(name = "raised_date")
    private LocalDate raisedDate;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String branch;

    @Column(length = 100)
    private String engineer;

    @Column(length = 100)
    private String dealer;

    @Column(length = 100)
    private String model;

    @Column(length = 100)
    private String supplier;

    @Column(name = "warrenty_status", length = 100)
    private String warrentyStatus;

    @Column(name = "prfob_ref_no", length = 100)
    private String prfobRefNo;

    @Column(name = "crm_ref_no", length = 100)
    private String crmRefNo;

    @Column(length = 600)
    private String remarks;

    @Column(name = "status_type", length = 100)
    private String statusType;

    @Column(name = "executed_date")
    private LocalDate executedDate;

    @Column(name = "part_type", length = 100, nullable = false)
    private String partType = "";

    @Column(name = "part_description", length = 100, nullable = false)
    private String partDescription = "";

    @Column(name = "currentdate_time")
    private LocalDateTime currentDateTime;

    @Column(name = "rec_dt_frm_sc", length = 255)
    private String receiveDateFromSc;
}
