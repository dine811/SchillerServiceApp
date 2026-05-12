package com.schillerindiaservices.repository;

import com.schillerindiaservices.model.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SparePartRepository extends JpaRepository<SparePart, Integer> {
    Optional<SparePart> findByPartNumberAndDivision(String partNumber, String division);
}
