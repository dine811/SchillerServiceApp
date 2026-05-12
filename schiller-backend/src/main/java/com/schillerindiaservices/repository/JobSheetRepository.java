package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.JobSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSheetRepository extends JpaRepository<JobSheet, Integer> {

    List<JobSheet> findBySerIdOrderByIdDesc(Long serId);
}
