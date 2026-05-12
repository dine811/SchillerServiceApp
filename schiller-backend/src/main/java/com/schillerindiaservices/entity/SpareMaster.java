package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Legacy table {@code sparemaster} — spareslist_engg.jsp / spareslist_engg2_Completed.jsp.
 */
@Entity
@Table(name = "sparemaster")
@Data
@NoArgsConstructor
public class SpareMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "sc_engg", length = 100)
    private String scEngg;

    @Column(name = "entry_date", length = 100)
    private String entryDate;

    @Column(name = "supplier", length = 100)
    private String supplier;

    @Column(name = "model", length = 150)
    private String model;

    @Column(name = "partnumber", length = 150)
    private String partNumber;

    @Column(name = "def_mod_brd_name", length = 200)
    private String defModBrdName;

    @Column(name = "reason", length = 300)
    private String reason;

    @Column(name = "reference", length = 300)
    private String reference;

    @Column(name = "gir_no", length = 100)
    private String girNo;

    @Column(name = "issued_by", length = 150)
    private String issuedBy;

    @Column(name = "returnable_status", length = 120)
    private String returnableStatus;

    @Column(name = "remarks", length = 2000)
    private String remarks;

    @Column(name = "returned_date", length = 120)
    private String returnedDate;

    @Column(name = "final_status", length = 120)
    private String finalStatus;

    @Column(name = "req_date_time")
    private LocalDateTime reqDateTime;

    @Column(name = "issued_date_time")
    private LocalDateTime issuedDateTime;

    @Column(name = "returned_date_time")
    private LocalDateTime returnedDateTime;

    @Column(name = "completed_date_time")
    private LocalDateTime completedDateTime;

    @Column(name = "qty", length = 255)
    private String qty;
}
