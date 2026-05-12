package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.MailIdEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailIdEntryRepository extends JpaRepository<MailIdEntry, Integer> {
    Page<MailIdEntry> findByMailIdContainingIgnoreCaseOrReportTypeContainingIgnoreCaseOrTemp1ContainingIgnoreCase(
        String search, String search2, String search3, Pageable pageable
    );
}
