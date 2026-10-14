package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.RepairDTO;
import com.schillerindiaservices.dto.RepairUpdateRequest;
import com.schillerindiaservices.entity.*;
import com.schillerindiaservices.mapper.RepairMapper;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.DropdownMasterRepository;
import com.schillerindiaservices.repository.ModelRepository;
import com.schillerindiaservices.repository.RepairRepository;
import com.schillerindiaservices.repository.ServiceRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairService {
    private final RepairRepository repairRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelRepository modelRepository;
    private final DropdownMasterRepository dropdownMasterRepository;
    private final RepairMapper repairMapper;

    @Transactional(readOnly = true)
    public Page<RepairDTO> findAll(String category, String status, LocalDate from, LocalDate to,
                                   String division, String scRefNo, String search, Pageable pageable) {
        return repairRepository.findAll(spec(category, status, from, to, division, scRefNo, search), pageable)
                .map(repairMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<RepairDTO> findForExport(String category, String status, LocalDate from, LocalDate to,
                                         String division, String scRefNo, String search) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return repairRepository.findAll(spec(category, status, from, to, division, scRefNo, search), sort)
                .stream().map(repairMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RepairDTO findById(Integer id) {
        return repairMapper.toDto(findRepair(id));
    }

    @Transactional
    public RepairDTO createFromService(Long serviceId, String category, String username, boolean admin) {
        RepairCategory repairCategory = parseCategory(category);
        ServiceMaster source = serviceRepository.findByIdForUpdate(serviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service record not found"));
        if (!admin && !withinEngineerScope(source, username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Service record is outside engineer scope");
        }
        if (!"NR".equalsIgnoreCase(clean(source.getRepairStatus()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only NR service records can start repair");
        }
        if (!eligibleForCategory(source, repairCategory)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Service record is not eligible for the requested repair category");
        }
        Integer serviceKey;
        try {
            serviceKey = Math.toIntExact(source.getId());
        } catch (ArithmeticException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Service id cannot be represented by repair_master");
        }
        if (repairRepository.existsByServiceIdAndStatus(serviceKey, RepairStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An active repair already exists for this service");
        }

        RepairMaster repair = new RepairMaster();
        repair.setEntryDate(LocalDate.now());
        repair.setDivision(source.getDivision());
        repair.setScRefNo(source.getScRefNo());
        repair.setDefGirNo(source.getDefGirNo());
        repair.setCategory(repairCategory);
        repair.setModel(resolveModel(source.getProductModel()));
        repair.setDefBrdModName(source.getDefModBrdName());
        repair.setUnitStatus(source.getUnitStatus());
        repair.setStatus(RepairStatus.PENDING);
        repair.setNoOfDays(0);
        repair.setServiceId(serviceKey);
        source.setRepairStatus("RP");

        try {
            RepairMaster saved = repairRepository.saveAndFlush(repair);
            serviceRepository.save(source);
            return repairMapper.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "An active repair already exists for this service", ex);
        }
    }

    @Transactional
    public RepairDTO update(Integer id, RepairUpdateRequest request) {
        RepairMaster repair = repairRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Repair record not found"));
        if (repair.getStatus() == RepairStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Completed repairs are immutable");
        }
        RepairStatus requestedStatus = parseStatus(request.getStatus());
        ServiceMaster service = serviceRepository.findByIdForUpdate(repair.getServiceId().longValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,
                        "Repair is linked to a missing service record"));

        repair.setRepairedBy(request.getRepairedBy().trim());
        repair.setComponentsUsedToRepair(trimToNull(request.getComponentsUsed()));
        repair.setTechRemarks(trimToNull(request.getTechnicalRemarks()));
        repair.setFinalRemarks(trimToNull(request.getFinalRemarks()));
        repair.setStatus(requestedStatus);
        repair.setNoOfDays(elapsedDays(repair.getEntryDate()));

        if (requestedStatus == RepairStatus.COMPLETED) {
            repair.setFinishedDate(LocalDate.now());
            service.setRepairStatus("RC");
            service.setComponentsUsedForRepair(repair.getComponentsUsedToRepair());
            service.setReportType("repairlist");
        } else {
            repair.setFinishedDate(null);
            service.setRepairStatus("RP");
        }
        serviceRepository.save(service);
        return repairMapper.toDto(repairRepository.save(repair));
    }

    private boolean withinEngineerScope(ServiceMaster service, String username) {
        Employee employee = employeeRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Employee profile not found"));
        boolean divisionMatch = !clean(employee.getDivision()).isEmpty()
                && clean(employee.getDivision()).equalsIgnoreCase(clean(service.getDivision()));
        int employeeId = Math.toIntExact(employee.getEmpId());
        return divisionMatch || Integer.valueOf(employeeId).equals(service.getScEngineerId())
                || Integer.valueOf(employeeId).equals(service.getRaEngineerId());
    }

    private String resolveModel(String raw) {
        if (raw == null || raw.isBlank()) return raw;
        try {
            return modelRepository.findById(Long.parseLong(raw.trim())).map(Model::getModelName).orElse(raw);
        } catch (NumberFormatException ignored) {
            return raw;
        }
    }

    private boolean eligibleForCategory(ServiceMaster source, RepairCategory category) {
        if (category == RepairCategory.UR) {
            return source.getShipDtFrmSerCntr() != null && source.getRepairedBrdStkDate() == null;
        }
        if (source.getShipDtFrmSerCntr() != null) return false;

        String unitStatus = clean(source.getUnitStatus());
        String resolvedStatus = unitStatus;
        try {
            resolvedStatus = dropdownMasterRepository.findById(Long.parseLong(unitStatus))
                    .map(DropdownMaster::getDdValue)
                    .orElse(unitStatus);
        } catch (NumberFormatException ignored) {
            // Imported records may already contain the display value rather than a dropdown id.
        }
        boolean outOfBox = "OW".equalsIgnoreCase(clean(resolvedStatus))
                || "LAMC".equalsIgnoreCase(clean(resolvedStatus));
        return category == RepairCategory.OB ? outOfBox : !outOfBox;
    }

    private RepairMaster findRepair(Integer id) {
        return repairRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Repair record not found"));
    }

    private static Specification<RepairMaster> spec(String category, String status, LocalDate from, LocalDate to,
                                                     String division, String scRefNo, String search) {
        RepairCategory cat = category == null || category.isBlank() ? null : parseCategory(category);
        RepairStatus st = status == null || status.isBlank() ? null : parseStatus(status);
        if (from != null && to != null && from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "from date must not be after to date");
        }
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (cat != null) predicates.add(cb.equal(root.get("category"), cat));
            if (st != null) predicates.add(cb.equal(root.get("status"), st));
            if (from != null) predicates.add(cb.greaterThanOrEqualTo(root.get("entryDate"), from));
            if (to != null) predicates.add(cb.lessThanOrEqualTo(root.get("entryDate"), to));
            String div = clean(division).toLowerCase();
            if (!div.isEmpty()) predicates.add(cb.like(cb.lower(root.get("division")), div + "%"));
            String sc = clean(scRefNo).toLowerCase();
            if (!sc.isEmpty()) predicates.add(cb.like(cb.lower(root.get("scRefNo")), "%" + sc + "%"));
            String keyword = clean(search).toLowerCase();
            if (!keyword.isEmpty()) {
                String pattern = "%" + keyword + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("division")), pattern),
                        cb.like(cb.lower(root.get("scRefNo")), pattern),
                        cb.like(cb.lower(root.get("defGirNo")), pattern)
                ));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static RepairCategory parseCategory(String category) {
        try {
            return RepairCategory.from(category);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    private static RepairStatus parseStatus(String status) {
        try {
            return RepairStatus.from(status);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    private static int elapsedDays(LocalDate entryDate) {
        if (entryDate == null) return 0;
        return Math.max(0, Math.toIntExact(ChronoUnit.DAYS.between(entryDate, LocalDate.now())));
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
