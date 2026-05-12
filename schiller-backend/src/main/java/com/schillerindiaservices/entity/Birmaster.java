package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Legacy {@code birmaster} — Batch Inspection Report (BIR) pending and closed lists.
 */
@Entity
@Table(name = "birmaster")
@Data
@NoArgsConstructor
public class Birmaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String division;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "fqc_in_date")
    private LocalDate fqcInDate;

    @Column(length = 100)
    private String model;

    @Column(length = 100)
    private String configuration;

    @Column(name = "received_qty", length = 100)
    private String receivedQty;

    @Column(name = "unit_serial_no", length = 100)
    private String unitSerialNo;

    @Column(name = "invoice_no", length = 100)
    private String invoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "softwr_changes", length = 100)
    private String softwrChanges;

    @Column(name = "softwr_version", length = 100)
    private String softwrVersion;

    @Column(name = "hardwr_changes", length = 100)
    private String hardwrChanges;

    @Column(name = "hardware_details", length = 100)
    private String hardwareDetails;

    @Column(name = "accesory_changes", length = 100)
    private String accesoryChanges;

    @Column(name = "accesory_details", length = 100)
    private String accesoryDetails;

    @Column(name = "user_manual_update", length = 100)
    private String userManualUpdate;

    @Column(name = "service_manual_update", length = 100)
    private String serviceManualUpdate;

    @Column(name = "fqc_remarks", length = 1000)
    private String fqcRemarks;

    @Column(name = "cnr_ref_no", length = 100)
    private String cnrRefNo;

    @Column(name = "ts_team_ver_date")
    private LocalDate tsTeamVerDate;

    @Column(name = "ps_team_ver_date")
    private LocalDate psTeamVerDate;

    @Column(name = "final_status", length = 100)
    private String finalStatus;

    @Column(name = "closed_date")
    private LocalDate closedDate;

    @Column(name = "acces_chng_remark", length = 1000, nullable = false)
    private String accesChngRemark = "";

    @Column(name = "hrdwr_chang_remark", length = 1000, nullable = false)
    private String hrdwrChangRemark = "";

    @Column(name = "sftwr_chng_remark", length = 1000, nullable = false)
    private String sftwrChngRemark = "";

    @Column(name = "cnr_relese_date")
    private LocalDate cnrReleseDate;

    @Column(name = "bir_ref_no", length = 100, nullable = false)
    private String birRefNo = "";

    @Column(length = 100, nullable = false)
    private String supplier = "";

    @Column(name = "sc_engg", length = 100, nullable = false)
    private String scEngg = "";

    @Column(name = "ps_engg", length = 100, nullable = false)
    private String psEngg = "";

    @Column(name = "approved_date")
    private LocalDate approvedDate;

    @Column(name = "cnr_tec_ref_num", length = 100, nullable = false)
    private String cnrTecRefNum = "";

    @Column(name = "unit_in_date")
    private LocalDate unitInDate;

    @Column(name = "tech_remarks", length = 1000)
    private String techRemarks;

    @Column(name = "current_date", insertable = false, updatable = false)
    private LocalDateTime currentDate;
}
