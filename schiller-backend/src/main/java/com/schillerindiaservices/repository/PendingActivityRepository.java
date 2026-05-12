package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.PendingActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingActivityRepository extends JpaRepository<PendingActivity, Long> {

    @Query("SELECT p FROM PendingActivity p WHERE COALESCE(LOWER(TRIM(p.statusType)), '') = 'pending' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(p.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(p.division, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.pendingActivity, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.responsible, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<PendingActivity> findPendingForScope(
            @Param("division") String division,
            @Param("kw") String keyword,
            Pageable pageable
    );

    @Query("SELECT p FROM PendingActivity p WHERE COALESCE(LOWER(TRIM(p.statusType)), '') = 'pending' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(p.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(p.division, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.pendingActivity, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.responsible, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%'))) " +
            "AND (:fromStr = '' OR LENGTH(TRIM(COALESCE(p.entryDate, ''))) >= 10 AND " +
            "SUBSTRING(TRIM(COALESCE(p.entryDate, '')), 1, 10) >= :fromStr) " +
            "AND (:toStr = '' OR LENGTH(TRIM(COALESCE(p.entryDate, ''))) >= 10 AND " +
            "SUBSTRING(TRIM(COALESCE(p.entryDate, '')), 1, 10) <= :toStr)")
    Page<PendingActivity> findPendingForExport(
            @Param("division") String division,
            @Param("kw") String keyword,
            @Param("fromStr") String fromStr,
            @Param("toStr") String toStr,
            Pageable pageable
    );

    @Query("SELECT p FROM PendingActivity p WHERE COALESCE(LOWER(TRIM(p.statusType)), '') IN ('completed', 'complete') " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(p.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(p.division, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.pendingActivity, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.responsible, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<PendingActivity> findCompletedForScope(
            @Param("division") String division,
            @Param("kw") String keyword,
            Pageable pageable
    );

    @Query("SELECT p FROM PendingActivity p WHERE COALESCE(LOWER(TRIM(p.statusType)), '') IN ('completed', 'complete') " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(p.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(p.division, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.pendingActivity, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.responsible, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(p.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%'))) " +
            "AND (:fromStr = '' OR LENGTH(TRIM(COALESCE(p.entryDate, ''))) >= 10 AND " +
            "SUBSTRING(TRIM(COALESCE(p.entryDate, '')), 1, 10) >= :fromStr) " +
            "AND (:toStr = '' OR LENGTH(TRIM(COALESCE(p.entryDate, ''))) >= 10 AND " +
            "SUBSTRING(TRIM(COALESCE(p.entryDate, '')), 1, 10) <= :toStr)")
    Page<PendingActivity> findCompletedForExport(
            @Param("division") String division,
            @Param("kw") String keyword,
            @Param("fromStr") String fromStr,
            @Param("toStr") String toStr,
            Pageable pageable
    );
}
