package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dealer")
@Data
@NoArgsConstructor
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dealer_id")
    private Long dealerId;

    @Column(name = "dealer_name", nullable = false, length = 200)
    private String dealerName;

    @Column(name = "dealer_address", length = 255)
    private String dealerAddress;

    @Column(name = "dealer_mail", length = 255)
    private String dealerMail;

    @Column(name = "dealer_phone", length = 50)
    private String dealerPhone;

    @Column(name = "region_id")
    private Long regionId;
}
