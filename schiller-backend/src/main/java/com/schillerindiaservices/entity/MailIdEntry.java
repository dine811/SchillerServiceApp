package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mail_id_entry")
@Data
public class MailIdEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_id_entry_id")
    private Integer mailIdEntryId;

    @Column(name = "mail_id")
    private String mailId;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "temp1")
    private String temp1;

    @Column(name = "temp2")
    private String temp2;
}
