package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Legacy table {@code callregister_demo} — ClosedCalls.jsp / ClosedCalls_Controller.
 * Same column set as legacy {@code callregister} (no separate {@code call} column).
 */
@Entity
@Table(name = "callregister_demo")
@Data
@NoArgsConstructor
public class CallRegisterDemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "sc_engg", length = 100)
    private String scEngg;

    @Column(name = "call_date", length = 100)
    private String callDate;

    @Column(name = "call_type", length = 100)
    private String callType;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "branch", length = 100)
    private String branch;

    @Column(name = "dealer", length = 100)
    private String dealer;

    @Column(name = "engineer", length = 100)
    private String engineer;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "type_of_call", length = 100)
    private String typeOfCall;

    @Column(name = "type_of_communication", length = 100)
    private String typeOfCommunication;

    @Column(name = "remarks", length = 2000)
    private String remarks;

    @Column(name = "duration", length = 100)
    private String duration;

    @Column(name = "status_type", length = 100)
    private String statusType;

    @Column(name = "entry_date", length = 100)
    private String entryDate;
}
