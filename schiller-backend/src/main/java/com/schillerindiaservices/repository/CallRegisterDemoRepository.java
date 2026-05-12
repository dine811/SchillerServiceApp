package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.CallRegisterDemo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Legacy {@code ClosedCalls.jsp} reads completed rows from {@code callregister_demo} (not {@code callregister}).
 */
@Repository
public interface CallRegisterDemoRepository extends JpaRepository<CallRegisterDemo, Long> {

    @Query("SELECT c FROM CallRegisterDemo c WHERE COALESCE(LOWER(TRIM(c.statusType)), '') = 'pending' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(c.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(c.engineer, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(c.typeOfCall, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(c.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<CallRegisterDemo> findPendingAdmin(
            @Param("division") String division,
            @Param("kw") String kw,
            Pageable pageable
    );

    @Query("SELECT c FROM CallRegisterDemo c WHERE COALESCE(LOWER(TRIM(c.statusType)), '') IN ('completed', 'complete') " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(c.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(c.engineer, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(c.typeOfCall, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(c.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%'))) " +
            "AND (:fromStr = '' OR LENGTH(TRIM(COALESCE(c.callDate, ''))) >= 10 AND " +
            "SUBSTRING(TRIM(COALESCE(c.callDate, '')), 1, 10) >= :fromStr) " +
            "AND (:toStr = '' OR LENGTH(TRIM(COALESCE(c.callDate, ''))) >= 10 AND " +
            "SUBSTRING(TRIM(COALESCE(c.callDate, '')), 1, 10) <= :toStr)")
    Page<CallRegisterDemo> findCompletedAdmin(
            @Param("division") String division,
            @Param("kw") String kw,
            @Param("fromStr") String fromStr,
            @Param("toStr") String toStr,
            Pageable pageable
    );

    @Query("SELECT COUNT(c) FROM CallRegisterDemo c WHERE COALESCE(LOWER(TRIM(c.statusType)), '') IN ('completed', 'complete')")
    long countCompletedRows();

    @Query("SELECT COUNT(c) FROM CallRegisterDemo c WHERE COALESCE(LOWER(TRIM(c.statusType)), '') = 'pending'")
    long countPendingRows();
}
