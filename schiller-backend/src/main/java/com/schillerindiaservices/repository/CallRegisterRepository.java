package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.CallRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Legacy CallListAdmin / CallReg_AdminPageController: {@code callregister}, pending rows.
 */
@Repository
public interface CallRegisterRepository extends JpaRepository<CallRegister, Long> {

    @Query("SELECT c FROM CallRegister c WHERE LOWER(TRIM(c.statusType)) = 'pending' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(c.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:kw = '' OR LOWER(COALESCE(c.engineer, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(c.typeOfCall, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(c.statusType, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<CallRegister> findPendingAdmin(
            @Param("division") String division,
            @Param("kw") String kw,
            Pageable pageable
    );

    @Query("SELECT COUNT(c) FROM CallRegister c WHERE COALESCE(LOWER(TRIM(c.statusType)), '') = 'pending'")
    long countPendingRows();
}
