package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "model")
@Data
@NoArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "model_name", length = 200, nullable = false)
    private String modelName;

    @Column(name = "sup_name", length = 200)
    private String supName;
    
    @Column(name = "model_desc", length = 200)
    private String modelDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    @ToString.Exclude
    private Productmaster productmaster;
}
