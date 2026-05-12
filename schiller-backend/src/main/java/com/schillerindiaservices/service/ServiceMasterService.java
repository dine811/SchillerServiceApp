package com.schillerindiaservices.service;

import com.schillerindiaservices.entity.ServiceMaster;
import com.schillerindiaservices.entity.Employee;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.schillerindiaservices.dto.ServiceMasterDTO;
import com.schillerindiaservices.util.ScrapPendingMetrics;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.BeanUtils;

/**
 * Business logic for ServiceMaster module.
 * Expand this class as you migrate each workflow from the old JSP app.
 */
@Service
@RequiredArgsConstructor
public class ServiceMasterService {

    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;

    public Page<ServiceMasterDTO> findAll(Pageable pageable) {
        return serviceRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<ServiceMasterDTO> search(String keyword, Pageable pageable) {
        return serviceRepository.searchServices(keyword.trim(), pageable).map(this::convertToDTO);
    }

    public Page<ServiceMasterDTO> findAllForUser(String username, boolean admin, Pageable pageable) {
        if (admin) {
            return findAll(pageable);
        }
        Employee employee = resolveEmployee(username);
        return serviceRepository.findByLegacyEngineerScope(
                employee.getDivision().trim(),
                employee.getEmpId(),
                pageable
        ).map(this::convertToDTO);
    }

    public Page<ServiceMasterDTO> searchForUser(String username, boolean admin, String keyword, Pageable pageable) {
        String kw = keyword == null ? "" : keyword.trim();
        if (admin) {
            return search(kw, pageable);
        }
        Employee employee = resolveEmployee(username);
        return serviceRepository.searchByLegacyEngineerScope(
                employee.getDivision().trim(),
                employee.getEmpId(),
                kw,
                pageable
        ).map(this::convertToDTO);
    }

    /** Under-repair queue: same filter as legacy under_repair.jsp / Under_RepairController. */
    public Page<ServiceMasterDTO> findUnderRepair(String scRef, String keyword, Pageable pageable) {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        return serviceRepository.findUnderRepair(sc, kw, pageable).map(sm -> {
            ServiceMasterDTO dto = convertToDTO(sm);
            dto.setPendingDays(computePendingDays(sm));
            return dto;
        });
    }

    /**
     * Pending FRN queue for the authenticated user. Admins: all rows. Engineers: same division (when set on the row and
     * on the employee) <strong>or</strong> SC/RA engineer match — avoids empty pages when {@code service_master.division}
     * is null or does not match {@code employee.division}.
     */
    public Page<ServiceMasterDTO> findPendingFrnForUser(String username, boolean isAdmin, String scRef, String keyword, Pageable pageable) {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        if (isAdmin) {
            return serviceRepository.findPendingFrn(sc, kw, true, "", -1, pageable).map(this::toPendingFrnDto);
        }
        Employee e = employeeRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Employee not found for username: " + username));
        String div = e.getDivision() == null ? "" : e.getDivision().trim();
        Integer empId = e.getEmpId().intValue();
        return serviceRepository.findPendingFrn(sc, kw, false, div, empId, pageable).map(this::toPendingFrnDto);
    }

    private ServiceMasterDTO toPendingFrnDto(ServiceMaster sm) {
        ServiceMasterDTO dto = convertToDTO(sm);
        dto.setPendingDays(computePendingDays(sm));
        return dto;
    }

    /** Legacy ob_pending.jsp queue: OW or LAMC only; ship from SC null. */
    public Page<ServiceMasterDTO> findObPending(String scRef, String keyword, Pageable pageable) {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        return serviceRepository.findObPending(sc, kw, pageable).map(sm -> {
            ServiceMasterDTO dto = convertToDTO(sm);
            dto.setPendingDays(computePendingDays(sm));
            return dto;
        });
    }

    /** Legacy closedproduct.jsp: shipped from SC, repair stock set, commercial ship not yet recorded. */
    public Page<ServiceMasterDTO> findScClosedProduct(String scRef, String keyword, Pageable pageable) {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        return serviceRepository.findScClosedProduct(sc, kw, pageable).map(sm -> {
            ServiceMasterDTO dto = convertToDTO(sm);
            dto.setPendingDays(computePendingDays(sm));
            return dto;
        });
    }

    /** Legacy emp_CompletedFRN.jsp: engineer division-scoped SC Completed FRN; admins see all rows. */
    public Page<ServiceMasterDTO> findScClosedProductForUser(String username, boolean isAdmin, String scRef, String keyword, Pageable pageable) {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        String div = "";
        if (!isAdmin) {
            Employee e = employeeRepository.findByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new RuntimeException("Employee not found for username: " + username));
            div = e.getDivision() == null ? "" : e.getDivision().trim();
        }
        return serviceRepository.findScClosedProductForScope(sc, kw, isAdmin, div, pageable).map(sm -> {
            ServiceMasterDTO dto = convertToDTO(sm);
            dto.setPendingDays(computePendingDays(sm));
            return dto;
        });
    }

    /** Legacy New_ClosedProduct.jsp: full closure — commercial ship date recorded. */
    public Page<ServiceMasterDTO> findNewClosedProduct(String scRef, String keyword, Pageable pageable) {
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        return serviceRepository.findNewClosedProduct(sc, kw, pageable).map(sm -> {
            ServiceMasterDTO dto = convertToDTO(sm);
            dto.setPendingDays(computePendingDays(sm));
            return dto;
        });
    }

    /**
     * Legacy ScarpList.jsp: type of work SCRAPPED; optional division and entry month/year (replaces legacy month/year columns).
     */
    public Page<ServiceMasterDTO> findScrapList(
            String division,
            Integer month,
            Integer year,
            String scRef,
            String keyword,
            Pageable pageable
    ) {
        String div = division == null ? "" : division.trim();
        String sc = scRef == null ? "" : scRef.trim();
        String kw = keyword == null ? "" : keyword.trim();
        return serviceRepository.findScrapList(div, month, year, sc, kw, pageable).map(this::convertToDTO);
    }

    private long computePendingDays(ServiceMaster sm) {
        if (sm.getSerCentreReceivedDate() == null) {
            return 0L;
        }
        return ChronoUnit.DAYS.between(sm.getSerCentreReceivedDate(), LocalDate.now());
    }

    public Page<ServiceMasterDTO> findByRegion(String region, Pageable pageable) {
        return serviceRepository.findByRegion(region, pageable).map(this::convertToDTO);
    }

    public ServiceMasterDTO findById(Long id) {
        return serviceRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Service record not found with id: " + id));
    }

    public ServiceMasterDTO save(ServiceMasterDTO dto) {
        ServiceMaster entity = convertToEntity(dto);
        ServiceMaster saved = serviceRepository.save(entity);
        return convertToDTO(saved);
    }

    public ServiceMasterDTO update(Long id, ServiceMasterDTO updated) {
        ServiceMaster existing = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service record not found with id: " + id));
        
        BeanUtils.copyProperties(updated, existing, "id");
        return convertToDTO(serviceRepository.save(existing));
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

    public String resolveDivision(String username) {
        String division = resolveEmployee(username).getDivision();
        if (division == null || division.isBlank()) {
            throw new RuntimeException("Division is not configured for user: " + username);
        }
        return division.trim();
    }

    private Employee resolveEmployee(String username) {
        Employee employee = employeeRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Employee not found for username: " + username));
        if (employee.getDivision() == null || employee.getDivision().isBlank()) {
            throw new RuntimeException("Division is not configured for user: " + username);
        }
        return employee;
    }

    private ServiceMasterDTO convertToDTO(ServiceMaster entity) {
        ServiceMasterDTO dto = new ServiceMasterDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setPendDaysPfrn(ScrapPendingMetrics.pfrn(entity));
        dto.setPendDaysObp(ScrapPendingMetrics.obp(entity));
        dto.setPendDaysUrp(ScrapPendingMetrics.urp(entity));
        dto.setPendDaysScc(ScrapPendingMetrics.scc(entity));
        if (entity.getScEngineerId() != null) {
            employeeRepository.findById(entity.getScEngineerId().longValue())
                    .ifPresent(e -> dto.setScEngineerName(e.getEmpName()));
        }
        if (entity.getRaEngineerId() != null) {
            employeeRepository.findById(entity.getRaEngineerId().longValue())
                    .ifPresent(e -> dto.setRaEngineerName(e.getEmpName()));
        }
        return dto;
    }

    private ServiceMaster convertToEntity(ServiceMasterDTO dto) {
        ServiceMaster entity = new ServiceMaster();
        BeanUtils.copyProperties(dto, entity);
        if (entity.getEntryDate() == null) {
            entity.setEntryDate(LocalDate.now());
        }
        return entity;
    }
}
