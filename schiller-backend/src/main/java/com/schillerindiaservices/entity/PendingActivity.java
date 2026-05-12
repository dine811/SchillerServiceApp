package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Legacy table {@code pendingact_master} — PendingActListENGG.jsp / ClosedActivity.jsp.
 * Dates are often stored as strings in this dataset, so map to String for compatibility.
 */
@Entity
@Table(name = "pendingact_master")
@Data
@NoArgsConstructor
public class PendingActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "sc_engg", length = 100)
    private String scEngg;

    @Column(name = "entry_date", length = 100)
    private String entryDate;

    @Column(name = "initiate_date", length = 100)
    private String initiateDate;

    @Column(name = "model", length = 200)
    private String model;

    @Column(name = "pending_activity", length = 300)
    private String pendingActivity;

    @Column(name = "responsible", length = 300)
    private String responsible;

    @Column(name = "pending_form", length = 200)
    private String pendingForm;

    @Column(name = "tar_closed_date", length = 100)
    private String tarClosedDate;

    @Column(name = "closed_date", length = 100)
    private String closedDate;

    @Column(name = "remarks", length = 2000)
    private String remarks;

    @Column(name = "status_type", length = 100)
    private String statusType;

    @Column(name = "sc_incharge_remark", length = 2000)
    private String scInchargeRemark;
}
