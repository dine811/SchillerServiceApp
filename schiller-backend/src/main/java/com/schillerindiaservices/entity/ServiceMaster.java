package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "service_master")
@Data
@NoArgsConstructor
public class ServiceMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sc_ref_no", nullable = false, length = 50)
    private String scRefNo;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "frn_no", length = 50)
    private String frnNo;

    @Column(name = "frn_date")
    private LocalDate frnDate;

    @Column(name = "ser_comm_inward_date")
    private LocalDate serCommInwardDate;

    @Column(name = "ser_centre_received_date")
    private LocalDate serCentreReceivedDate;

    @Column(name = "stk_cust", length = 50)
    private String stkCust;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "branch", length = 100)
    private String branch;

    @Column(name = "dealer_name", length = 200)
    private String dealerName;

    @Column(name = "cust_name", length = 200)
    private String custName;

    @Column(name = "supplier_name", length = 200)
    private String supplierName;

    @Column(name = "product_model", length = 100)
    private String productModel;

    @Column(name = "model_config", length = 1000)
    private String modelConfig;

    @Column(name = "unit_sl_no", length = 100)
    private String unitSlNo;

    @Column(name = "unit_status", length = 50)
    private String unitStatus;

    @Column(name = "dod")
    private LocalDate dod;

    @Column(name = "mod_brd_name", length = 200)
    private String modBrdName;

    @Column(name = "def_mod_brd_name", length = 200)
    private String defModBrdName;

    @Column(name = "def_type", length = 50)
    private String defType;

    @Column(name = "type_of_acc", length = 50)
    private String typeOfAcc;

    @Column(name = "def_part_sn", length = 100)
    private String defPartSn;

    @Column(name = "def_gir_no", length = 100)
    private String defGirNo;

    @Column(name = "rep_type", length = 50)
    private String repType;

    @Column(name = "rep_gir_no", length = 100)
    private String repGirNo;

    @Column(name = "def_unit_gir_no", length = 100)
    private String defUnitGirNo;

    @Column(name = "field_remarks", length = 500)
    private String fieldRemarks;

    @Column(name = "technical_remarks", length = 500)
    private String technicalRemarks;

    @Column(name = "components_used_for_repair", length = 500)
    private String componentsUsedForRepair;

    @Column(name = "final_remarks", length = 500)
    private String finalRemarks;

    @Column(name = "comrcl_dtl_inv_rmrk", length = 500)
    private String comrclDtlInvRmrk;

    @Column(name = "type_of_work", length = 50)
    private String typeOfWork;

    @Column(name = "ship_dt_frm_ser_cntr")
    private LocalDate shipDtFrmSerCntr;

    @Column(name = "repaired_brd_stk_date")
    private LocalDate repairedBrdStkDate;

    @Column(name = "ship_date_from_commercial")
    private LocalDate shipDateFromCommercial;

    @Column(name = "dc_no", length = 100)
    private String dcNo;

    @Column(name = "disp_adv_no", length = 100)
    private String dispAdvNo;

    @Column(name = "repaired_brd_adv_no", length = 100)
    private String repairedBrdAdvNo;

    @Column(name = "part_number", length = 100)
    private String partNumber;

    @Column(name = "compatible_models", length = 500)
    private String compatibleModels;

    @Column(name = "destination", length = 200)
    private String destination;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "sc_engineer_id")
    private Integer scEngineerId;

    @Column(name = "ra_engineer_id")
    private Integer raEngineerId;

    @Column(name = "report_type", length = 50)
    private String reportType;

    /** Legacy workflow status (e.g. Pending / Completed) updated from job sheet save. */
    @Column(name = "status", length = 1000)
    private String status;

    /** Legacy repair workflow flag (e.g. NR, RP, RC) — engineer queue SR/RP/RC column. */
    @Column(name = "repair_status", length = 100)
    private String repairStatus;
}
