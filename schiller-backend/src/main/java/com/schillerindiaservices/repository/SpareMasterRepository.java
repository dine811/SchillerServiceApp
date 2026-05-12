package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.SpareMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpareMasterRepository extends JpaRepository<SpareMaster, Long> {

    @Query("SELECT s FROM SpareMaster s WHERE COALESCE(LOWER(TRIM(s.finalStatus)), '') = 'pending' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(s.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.partNumber, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.finalStatus, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.entryDate, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<SpareMaster> findPendingForScope(
            @Param("division") String division,
            @Param("kw") String keyword,
            Pageable pageable
    );

    @Query("SELECT s FROM SpareMaster s WHERE COALESCE(LOWER(TRIM(s.finalStatus)), '') IN ('completed', 'complete') " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(s.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.partNumber, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.finalStatus, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.entryDate, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<SpareMaster> findCompletedForScope(
            @Param("division") String division,
            @Param("kw") String keyword,
            Pageable pageable
    );

    @Query("SELECT s FROM SpareMaster s WHERE COALESCE(LOWER(TRIM(s.finalStatus)), '') = 'pending' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(s.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.partNumber, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.finalStatus, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.entryDate, '')) LIKE LOWER(CONCAT('%', :kw, '%'))) " +
            "AND (:fromStr = '' OR LENGTH(TRIM(COALESCE(s.entryDate, ''))) >= 10 AND SUBSTRING(TRIM(COALESCE(s.entryDate, '')), 1, 10) >= :fromStr) " +
            "AND (:toStr = '' OR LENGTH(TRIM(COALESCE(s.entryDate, ''))) >= 10 AND SUBSTRING(TRIM(COALESCE(s.entryDate, '')), 1, 10) <= :toStr)")
    Page<SpareMaster> findPendingForExport(
            @Param("division") String division,
            @Param("kw") String keyword,
            @Param("fromStr") String fromStr,
            @Param("toStr") String toStr,
            Pageable pageable
    );

    @Query("SELECT s FROM SpareMaster s WHERE COALESCE(LOWER(TRIM(s.finalStatus)), '') IN ('completed', 'complete') " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(s.model, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.partNumber, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.finalStatus, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.entryDate, '')) LIKE LOWER(CONCAT('%', :kw, '%'))) " +
            "AND (:fromStr = '' OR LENGTH(TRIM(COALESCE(s.entryDate, ''))) >= 10 AND SUBSTRING(TRIM(COALESCE(s.entryDate, '')), 1, 10) >= :fromStr) " +
            "AND (:toStr = '' OR LENGTH(TRIM(COALESCE(s.entryDate, ''))) >= 10 AND SUBSTRING(TRIM(COALESCE(s.entryDate, '')), 1, 10) <= :toStr)")
    Page<SpareMaster> findCompletedForExport(
            @Param("division") String division,
            @Param("kw") String keyword,
            @Param("fromStr") String fromStr,
            @Param("toStr") String toStr,
            Pageable pageable
    );
}
