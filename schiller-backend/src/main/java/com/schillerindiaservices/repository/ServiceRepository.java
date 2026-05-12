package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.ServiceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for ServiceMaster entity.
 * Add custom query methods here as needed per module migration.
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceMaster, Long> {

    Page<ServiceMaster> findByRegion(String region, Pageable pageable);

    Page<ServiceMaster> findByTypeOfWork(String typeOfWork, Pageable pageable);

    Page<ServiceMaster> findByReportType(String reportType, Pageable pageable);

    Page<ServiceMaster> findByScEngineerId(Integer engineerId, Pageable pageable);

    @Query("SELECT s FROM ServiceMaster s WHERE " +
            "LOWER(COALESCE(s.custName, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.region, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defModBrdName, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.productModel, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.unitSlNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.dcNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))")
    Page<ServiceMaster> searchServices(@Param("kw") String keyword, Pageable pageable);

    Page<ServiceMaster> findByDivisionIgnoreCase(String division, Pageable pageable);

    @Query("SELECT s FROM ServiceMaster s WHERE LOWER(COALESCE(s.division, '')) = LOWER(:division) AND (" +
            "LOWER(COALESCE(s.custName, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.region, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defModBrdName, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.productModel, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.unitSlNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.dcNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<ServiceMaster> searchServicesByDivision(@Param("division") String division, @Param("kw") String keyword, Pageable pageable);

    @Query("SELECT s FROM ServiceMaster s WHERE " +
            "LOWER(COALESCE(s.division, '')) = LOWER(:division) OR " +
            "s.scEngineerId = :empId OR s.raEngineerId = :empId")
    Page<ServiceMaster> findByLegacyEngineerScope(
            @Param("division") String division,
            @Param("empId") Long empId,
            Pageable pageable
    );

    @Query("SELECT s FROM ServiceMaster s WHERE (" +
            "LOWER(COALESCE(s.division, '')) = LOWER(:division) OR " +
            "s.scEngineerId = :empId OR s.raEngineerId = :empId" +
            ") AND (" +
            "LOWER(COALESCE(s.custName, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.region, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defModBrdName, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.productModel, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.unitSlNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.dcNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))" +
            ")")
    Page<ServiceMaster> searchByLegacyEngineerScope(
            @Param("division") String division,
            @Param("empId") Long empId,
            @Param("kw") String keyword,
            Pageable pageable
    );

    List<ServiceMaster> findByEntryDateBetweenOrderByIdDesc(LocalDate from, LocalDate to);

    /**
     * Legacy under_repair.jsp: units shipped from SC but repair stock date not yet set.
     * {@code ship_dt_frm_ser_cntr IS NOT NULL AND repaired_brd_stk_date IS NULL}
     */
    @Query("SELECT s FROM ServiceMaster s WHERE s.shipDtFrmSerCntr IS NOT NULL AND s.repairedBrdStkDate IS NULL " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findUnderRepair(@Param("scRef") String scRef, @Param("kw") String kw, Pageable pageable);

    /**
     * Pending FRN queue. Admins see all rows ({@code admin = true}). Engineers: division match when both sides have a
     * non-blank division, <strong>or</strong> SC/RA engineer id match (aligns with {@link #findByLegacyEngineerScope}).
     */
    @Query("SELECT DISTINCT s FROM ServiceMaster s INNER JOIN DropdownMaster dm ON CAST(TRIM(s.unitStatus) AS long) = dm.id " +
            "WHERE s.shipDtFrmSerCntr IS NULL " +
            "AND s.unitStatus IS NOT NULL " +
            "AND TRIM(s.unitStatus) <> '' " +
            "AND LOWER(dm.ddValue) NOT IN ('ow', 'lamc') " +
            "AND (:admin = true OR ((LENGTH(TRIM(COALESCE(:div, ''))) > 0 AND LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:div))) " +
            "OR s.scEngineerId = :empId OR s.raEngineerId = :empId)) " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findPendingFrn(
            @Param("scRef") String scRef,
            @Param("kw") String kw,
            @Param("admin") boolean admin,
            @Param("div") String div,
            @Param("empId") Integer empId,
            Pageable pageable);

    /**
     * Legacy ob_pending.jsp: same ship condition as Pending FRN but unit status OW or LAMC only
     * ({@code INNER JOIN dropdownmaster ... ddvalue IN ('OW','LAMC') AND sm.ship_dt_frm_ser_cntr IS NULL}).
     */
    @Query("SELECT DISTINCT s FROM ServiceMaster s INNER JOIN DropdownMaster dm ON CAST(TRIM(s.unitStatus) AS long) = dm.id " +
            "WHERE s.shipDtFrmSerCntr IS NULL " +
            "AND s.unitStatus IS NOT NULL " +
            "AND TRIM(s.unitStatus) <> '' " +
            "AND LOWER(dm.ddValue) IN ('ow', 'lamc') " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findObPending(@Param("scRef") String scRef, @Param("kw") String kw, Pageable pageable);

    /**
     * Legacy closedproduct.jsp / Closed_Controller:
     * {@code ship_dt_frm_ser_cntr IS NOT NULL AND repaired_brd_stk_date IS NOT NULL AND ship_date_from_commercial IS NULL}.
     */
    @Query("SELECT s FROM ServiceMaster s WHERE s.shipDtFrmSerCntr IS NOT NULL AND s.repairedBrdStkDate IS NOT NULL " +
            "AND s.shipDateFromCommercial IS NULL " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findScClosedProduct(@Param("scRef") String scRef, @Param("kw") String kw, Pageable pageable);

    /**
     * Engineer closed-FRN scope used by legacy EmpCFRNController: same queue with division constraint.
     * Admins bypass division filter and see all rows.
     */
    @Query("SELECT s FROM ServiceMaster s WHERE s.shipDtFrmSerCntr IS NOT NULL AND s.repairedBrdStkDate IS NOT NULL " +
            "AND s.shipDateFromCommercial IS NULL " +
            "AND (:admin = true OR LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:div))) " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findScClosedProductForScope(
            @Param("scRef") String scRef,
            @Param("kw") String kw,
            @Param("admin") boolean admin,
            @Param("div") String div,
            Pageable pageable
    );

    /**
     * Legacy New_ClosedProduct.jsp / New_ClosedController:
     * {@code ship_dt_frm_ser_cntr IS NOT NULL AND repaired_brd_stk_date IS NOT NULL AND ship_date_from_commercial IS NOT NULL}.
     */
    @Query("SELECT s FROM ServiceMaster s WHERE s.shipDtFrmSerCntr IS NOT NULL AND s.repairedBrdStkDate IS NOT NULL " +
            "AND s.shipDateFromCommercial IS NOT NULL " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findNewClosedProduct(@Param("scRef") String scRef, @Param("kw") String kw, Pageable pageable);

    /**
     * Legacy ScarpList.jsp / ScarpListEnggController: rows with type of work SCRAPPED (dropdown ddvalue).
     * Optional filters: division (exact, case-insensitive), entry month/year on {@code entry_date}.
     */
    @Query("SELECT DISTINCT s FROM ServiceMaster s INNER JOIN DropdownMaster dm ON CAST(TRIM(s.typeOfWork) AS long) = dm.id " +
            "WHERE LOWER(TRIM(dm.ddValue)) = 'scrapped' " +
            "AND s.typeOfWork IS NOT NULL AND TRIM(s.typeOfWork) <> '' " +
            "AND (:division = '' OR LOWER(TRIM(COALESCE(s.division, ''))) = LOWER(TRIM(:division))) " +
            "AND (:month IS NULL OR month(s.entryDate) = :month) " +
            "AND (:year IS NULL OR year(s.entryDate) = :year) " +
            "AND (:scRef = '' OR LOWER(COALESCE(s.scRefNo, '')) LIKE LOWER(CONCAT('%', :scRef, '%'))) " +
            "AND (:kw = '' OR (LOWER(COALESCE(s.frnNo, '')) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "OR LOWER(COALESCE(s.defGirNo, '')) LIKE LOWER(CONCAT('%', :kw, '%'))))")
    Page<ServiceMaster> findScrapList(
            @Param("division") String division,
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("scRef") String scRef,
            @Param("kw") String kw,
            Pageable pageable);
}
