package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.DropdownMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropdownMasterRepository extends JpaRepository<DropdownMaster, Long> {
    /** Legacy {@code ddname} stores group as {@code "1"} … {@code "6"}. */
    List<DropdownMaster> findByDdName(String ddName);
}
