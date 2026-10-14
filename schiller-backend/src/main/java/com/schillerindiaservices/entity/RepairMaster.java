package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_master")
@Data
@NoArgsConstructor
public class RepairMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "entry_date")
    private LocalDate entryDate;
    @Column(length = 100)
    private String division;
    @Column(name = "sc_ref_no", length = 100)
    private String scRefNo;
    @Column(name = "def_gir_no", length = 100)
    private String defGirNo;
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private RepairCategory category;
    @Column(length = 1000)
    private String model;
    @Column(name = "def_brd_mod_name", length = 1000)
    private String defBrdModName;
    @Column(name = "unit_status", length = 300)
    private String unitStatus;
    @Column(name = "tech_remarks", length = 300)
    private String techRemarks;
    @Column(name = "comp_used_to_repair", length = 1000)
    private String componentsUsedToRepair;
    @Column(name = "finished_date")
    private LocalDate finishedDate;
    @Column(name = "repaired_by", length = 300)
    private String repairedBy;
    @Column(name = "final_remarks", length = 100)
    private String finalRemarks;
    @Convert(converter = RepairStatusConverter.class)
    @Column(length = 100)
    private RepairStatus status;
    @Column(name = "no_of_days")
    private Integer noOfDays;
    @Column(name = "ser_id")
    private Integer serviceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ser_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ServiceMaster service;
    @Column(name = "\"current_date\"", insertable = false, updatable = false)
    private LocalDateTime currentDate;
}
