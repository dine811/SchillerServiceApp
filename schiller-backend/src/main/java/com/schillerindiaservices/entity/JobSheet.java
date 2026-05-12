package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Maps legacy table {@code jobsheet} (repair log lines linked to {@code service_master} via {@code ser_id}).
 */
@Entity
@Table(name = "jobsheet")
@Data
@NoArgsConstructor
public class JobSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "repair_date", length = 255)
    private String repairDate;

    @Column(name = "enginner_name", length = 255)
    private String enginnerName;

    @Column(name = "observation", length = 255)
    private String observation;

    @Column(name = "repair_activity", length = 255)
    private String repairActivity;

    @Column(name = "time_spent", length = 255)
    private String timeSpent;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "ser_id")
    private Long serId;

    @Column(name = "repair_date1", length = 255)
    private String repairDate1;

    @Column(name = "repair_date2", length = 255)
    private String repairDate2;

    @Column(name = "repair_date3", length = 255)
    private String repairDate3;

    @Column(name = "repair_date4", length = 255)
    private String repairDate4;

    @Column(name = "enginner_name1", length = 255)
    private String enginnerName1;

    @Column(name = "enginner_name2", length = 255)
    private String enginnerName2;

    @Column(name = "enginner_name3", length = 255)
    private String enginnerName3;

    @Column(name = "enginner_name4", length = 255)
    private String enginnerName4;

    @Column(name = "observation1", length = 255)
    private String observation1;

    @Column(name = "observation2", length = 255)
    private String observation2;

    @Column(name = "observation3", length = 255)
    private String observation3;

    @Column(name = "observation4", length = 255)
    private String observation4;

    @Column(name = "repair_activity1", length = 255)
    private String repairActivity1;

    @Column(name = "repair_activity2", length = 255)
    private String repairActivity2;

    @Column(name = "repair_activity3", length = 255)
    private String repairActivity3;

    @Column(name = "repair_activity4", length = 255)
    private String repairActivity4;

    @Column(name = "time_spent1", length = 255)
    private String timeSpent1;

    @Column(name = "time_spent2", length = 255)
    private String timeSpent2;

    @Column(name = "time_spent3", length = 255)
    private String timeSpent3;

    @Column(name = "time_spent4", length = 255)
    private String timeSpent4;

    @Column(name = "remark1", length = 255)
    private String remark1;

    @Column(name = "remark2", length = 255)
    private String remark2;

    @Column(name = "remark3", length = 255)
    private String remark3;

    @Column(name = "remark4", length = 255)
    private String remark4;
}
