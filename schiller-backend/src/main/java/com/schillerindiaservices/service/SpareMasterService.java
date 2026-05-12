package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.SpareMasterDTO;
import com.schillerindiaservices.dto.SpareMasterRequest;
import com.schillerindiaservices.entity.Employee;
import com.schillerindiaservices.entity.Productmaster;
import com.schillerindiaservices.entity.SpareMaster;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.ProductmasterRepository;
import com.schillerindiaservices.repository.SpareMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SpareMasterService {

    private final SpareMasterRepository spareMasterRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductmasterRepository productmasterRepository;

    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Page<SpareMasterDTO> findPendingForUser(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isPrivileged, requestedDivision);
        return spareMasterRepository.findPendingForScope(div, kw, pageable).map(this::toDto);
    }

    public Page<SpareMasterDTO> findCompletedForUser(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isPrivileged, requestedDivision);
        return spareMasterRepository.findCompletedForScope(div, kw, pageable).map(this::toDto);
    }

    public Page<SpareMasterDTO> findPendingForExport(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            LocalDate from,
            LocalDate to
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isPrivileged, requestedDivision);
        String fromStr = from == null ? "" : from.toString();
        String toStr = to == null ? "" : to.toString();
        Pageable pageable = PageRequest.of(0, 100000, Sort.by(Sort.Direction.DESC, "id"));
        return spareMasterRepository.findPendingForExport(div, kw, fromStr, toStr, pageable).map(this::toDto);
    }

    public Page<SpareMasterDTO> findCompletedForExport(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            LocalDate from,
            LocalDate to
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isPrivileged, requestedDivision);
        String fromStr = from == null ? "" : from.toString();
        String toStr = to == null ? "" : to.toString();
        Pageable pageable = PageRequest.of(0, 100000, Sort.by(Sort.Direction.DESC, "id"));
        return spareMasterRepository.findCompletedForExport(div, kw, fromStr, toStr, pageable).map(this::toDto);
    }

    public SpareMasterDTO findById(Long id) {
        return toDto(spareMasterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Spare record not found: " + id)));
    }

    @Transactional
    public SpareMasterDTO create(String username, SpareMasterRequest request) {
        SpareMaster entity = new SpareMaster();
        copyToEntity(entity, request, username, true);
        return toDto(spareMasterRepository.save(entity));
    }

    @Transactional
    public SpareMasterDTO update(Long id, SpareMasterRequest request) {
        SpareMaster entity = spareMasterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Spare record not found: " + id));
        copyToEntity(entity, request, null, false);
        return toDto(spareMasterRepository.save(entity));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!spareMasterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spare record not found: " + id);
        }
        spareMasterRepository.deleteById(id);
    }

    private void copyToEntity(SpareMaster entity, SpareMasterRequest request, String username, boolean creating) {
        entity.setDivision(normalizeDivisionScope(request.getDivision()));
        entity.setScEngg(blankToNull(request.getScEngg()));
        entity.setEntryDate(normalizeDate(request.getEntryDate()));
        entity.setSupplier(blankToNull(request.getSupplier()));
        entity.setModel(blankToNull(request.getModel()));
        entity.setPartNumber(blankToNull(request.getPartNumber()));
        entity.setDefModBrdName(blankToNull(request.getDefModBrdName()));
        entity.setReason(blankToNull(request.getReason()));
        entity.setReference(blankToNull(request.getReference()));
        entity.setGirNo(blankToNull(request.getGirNo()));
        entity.setIssuedBy(blankToNull(request.getIssuedBy()));
        entity.setReturnableStatus(blankToNull(request.getReturnableStatus()));
        entity.setRemarks(blankToNull(request.getRemarks()));
        entity.setReturnedDate(normalizeDate(request.getReturnedDate()));
        entity.setFinalStatus(blankToNull(request.getFinalStatus()));
        entity.setQty(blankToNull(request.getQty()));

        if (creating && (entity.getEntryDate() == null || entity.getEntryDate().isBlank())) {
            entity.setEntryDate(LocalDate.now().format(DISPLAY_DATE));
        }

        if (creating && (entity.getScEngg() == null || entity.getScEngg().isBlank()) && username != null) {
            employeeRepository.findByUsername(username).map(Employee::getEmpName).ifPresent(entity::setScEngg);
        }
    }

    private SpareMasterDTO toDto(SpareMaster entity) {
        SpareMasterDTO dto = new SpareMasterDTO();
        dto.setId(entity.getId());
        dto.setEntryDate(entity.getEntryDate());
        dto.setDivision(entity.getDivision());
        dto.setSupplier(entity.getSupplier());
        dto.setModel(entity.getModel());
        dto.setPartNumber(entity.getPartNumber());
        dto.setDefModBrdName(entity.getDefModBrdName());
        dto.setReason(entity.getReason());
        dto.setReference(entity.getReference());
        dto.setGirNo(entity.getGirNo());
        dto.setScEngg(entity.getScEngg());
        dto.setScEnggName(resolveEngineerName(entity.getScEngg()));
        dto.setIssuedBy(entity.getIssuedBy());
        dto.setReturnableStatus(entity.getReturnableStatus());
        dto.setRemarks(entity.getRemarks());
        dto.setReturnedDate(entity.getReturnedDate());
        dto.setFinalStatus(entity.getFinalStatus());
        dto.setQty(entity.getQty());
        return dto;
    }

    private String resolveEngineerName(String scEnggRaw) {
        String value = blankToNull(scEnggRaw);
        if (value == null) return null;
        return employeeRepository.findByUsername(value)
                .map(Employee::getEmpName)
                .orElse(value);
    }

    private String resolveDivisionScope(String username, boolean isPrivileged, String requestedDivision) {
        if (isPrivileged) return normalizeDivisionScope(requestedDivision);
        String division = employeeRepository.findByUsername(username).map(Employee::getDivision).orElse("");
        return normalizeDivisionScope(division);
    }

    private String normalizeDivisionScope(String input) {
        String raw = blankToNull(input);
        if (raw == null) return "";
        if (!raw.matches("\\d+")) return raw;
        try {
            int productId = Integer.parseInt(raw);
            return productmasterRepository.findById(productId)
                    .map(this::resolveProductDivisionName)
                    .orElse(raw);
        } catch (NumberFormatException ex) {
            return raw;
        }
    }

    private String resolveProductDivisionName(Productmaster product) {
        String div = blankToNull(product.getDivision());
        if (div != null) return div;
        String name = blankToNull(product.getDivisionName());
        return name == null ? "" : name;
    }

    private String normalizeDate(String input) {
        String value = blankToNull(input);
        if (value == null) return null;
        if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return value;
        }
        if (value.matches("\\d{2}-\\d{2}-\\d{4}")) {
            try {
                LocalDate parsed = LocalDate.parse(value, DISPLAY_DATE);
                return parsed.toString();
            } catch (DateTimeParseException ignored) {
                return value;
            }
        }
        try {
            LocalDate parsed = LocalDate.parse(value, DateTimeFormatter.ofPattern("d-M-yyyy", Locale.ENGLISH));
            return parsed.toString();
        } catch (DateTimeParseException ignored) {
            return value;
        }
    }

    private String blankToNull(String input) {
        if (input == null) return null;
        String value = input.trim();
        return value.isEmpty() ? null : value;
    }
}
