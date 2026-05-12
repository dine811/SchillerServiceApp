package com.schillerindiaservices.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sparepart_master")
@Data
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spare_id")
    private Integer spareId;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "comp_models")
    private String compModels;

    @Column(name = "def_mod_brd_name")
    private String defModBrdName;

    @Column(name = "def_type")
    private String defType;

    @Column(name = "division")
    private String division;
}
