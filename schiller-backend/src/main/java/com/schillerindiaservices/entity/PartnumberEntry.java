package com.schillerindiaservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Legacy {@code partnumber_entry} — admin Spares Entry (partNumberForm.jsp / PartEntry servlet).
 */
@Entity
@Table(name = "partnumber_entry")
@Data
@NoArgsConstructor
public class PartnumberEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "part_number", nullable = false, length = 1000)
    private String partNumber;

    @Column(name = "brd_name", nullable = false, length = 300)
    private String brdName;

    @Column(name = "compatible_models", nullable = false, length = 1000)
    private String compatibleModels;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
