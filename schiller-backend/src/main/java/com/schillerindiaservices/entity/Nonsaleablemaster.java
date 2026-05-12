package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Legacy {@code nonsaleablemaster} — non-salable admin list (Pending) and Salables (Completed).
 */
@Entity
@Table(name = "nonsaleablemaster")
@Data
@NoArgsConstructor
public class Nonsaleablemaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String division;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "unit_details", length = 50)
    private String unitDetails;

    @Column(name = "fqc_in_date")
    private LocalDate fqcInDate;

    @Column(length = 50)
    private String region;

    @Column(length = 50)
    private String branch;

    @Column(length = 50)
    private String engineer;

    @Column(length = 50)
    private String dealer;

    @Column(length = 50)
    private String supplier;

    @Column(length = 50)
    private String model;

    @Column(name = "model_s_n", length = 50)
    private String modelSn;

    @Column(name = "unit_config", length = 50)
    private String unitConfig;

    @Column(name = "cust_name", length = 50)
    private String custName;

    @Column(name = "reported_problm", length = 250)
    private String reportedProblm;

    @Column(name = "replaced_unit_s_n", length = 250)
    private String replacedUnitSn;

    @Column(name = "replace_ship_date")
    private LocalDate replaceShipDate;

    @Column(name = "defect_unit_recived_date")
    private LocalDate defectUnitReceivedDate;

    @Column(name = "fqc_observation", length = 250)
    private String fqcObservation;

    @Column(name = "sc_inward_date")
    private LocalDate scInwardDate;

    @Column(name = "sc_engg", length = 50)
    private String scEngg;

    @Column(name = "sc_observation", length = 250)
    private String scObservation;

    @Column(name = "required_parts", length = 250)
    private String requiredParts;

    @Column(name = "root_cause", length = 250)
    private String rootCause;

    @Column(name = "action_plan", length = 600)
    private String actionPlan;

    @Column(name = "tent_date_ship_date")
    private LocalDate tentDateShipDate;

    @Column(name = "ship_date_fqc")
    private LocalDate shipDateFqc;

    @Column(name = "fqc_final_remark", length = 550)
    private String fqcFinalRemark;

    @Column(name = "final_status", length = 600)
    private String finalStatus;

    @Column(name = "current_date", insertable = false, updatable = false)
    private LocalDateTime currentDate;
}
