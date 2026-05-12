package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productmaster")
@Data
@NoArgsConstructor
public class Productmaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "division_name", length = 200)
    private String divisionName;

    @OneToMany(mappedBy = "productmaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Model> models;
}
