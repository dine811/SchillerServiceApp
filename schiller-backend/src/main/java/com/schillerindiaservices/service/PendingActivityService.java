package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.PendingActivityDTO;
import com.schillerindiaservices.dto.PendingActivityRequest;
import com.schillerindiaservices.entity.PendingActivity;
import com.schillerindiaservices.entity.Productmaster;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.PendingActivityRepository;
import com.schillerindiaservices.repository.ProductmasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PendingActivityService {

    private final PendingActivityRepository pendingActivityRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductmasterRepository productmasterRepository;

    public Page<PendingActivityDTO> findPendingForUser(
            String username,
            boolean isAdmin,
            String requestedDivision,
            String keyword,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isAdmin, requestedDivision);
        return pendingActivityRepository.findPendingForScope(div, kw, pageable).map(this::toDto);
    }

    public Page<PendingActivityDTO> findPendingForExport(
            String username,
            boolean isAdmin,
            String requestedDivision,
            String keyword,
            String from,
            String to,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isAdmin, requestedDivision);
        String fromStr = from == null ? "" : from.trim();
        String toStr = to == null ? "" : to.trim();
        return pendingActivityRepository.findPendingForExport(div, kw, fromStr, toStr, pageable).map(this::toDto);
    }

    public Page<PendingActivityDTO> findCompletedForUser(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isPrivileged, requestedDivision);
        return pendingActivityRepository.findCompletedForScope(div, kw, pageable).map(this::toDto);
    }

    public Page<PendingActivityDTO> findCompletedForExport(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            String from,
            String to,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div = resolveDivisionScope(username, isPrivileged, requestedDivision);
        String fromStr = from == null ? "" : from.trim();
        String toStr = to == null ? "" : to.trim();
        return pendingActivityRepository.findCompletedForExport(div, kw, fromStr, toStr, pageable).map(this::toDto);
    }

    public PendingActivityDTO findById(Long id) {
        return pendingActivityRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public PendingActivityDTO create(PendingActivityRequest req, String username, boolean isAdmin) {
        validateMandatoryRequest(req, isAdmin, true);
        PendingActivity e = new PendingActivity();
        applyCreateOrUpdate(req, e, username, isAdmin);
        validateMandatoryEntity(e);
        return toDto(pendingActivityRepository.save(e));
    }

    @Transactional
    public PendingActivityDTO update(Long id, PendingActivityRequest req) {
        validateMandatoryRequest(req, false, false);
        PendingActivity e = pendingActivityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        applyUpdate(req, e);
        validateMandatoryEntity(e);
        return toDto(pendingActivityRepository.save(e));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!pendingActivityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        pendingActivityRepository.deleteById(id);
    }

    private void applyCreateOrUpdate(PendingActivityRequest req, PendingActivity e, String username, boolean isAdmin) {
        String requested = req.getDivision() == null ? "" : req.getDivision().trim();
        String division;
        if (isAdmin) {
            division = normalizeDivisionScope(requested);
        } else {
            String rawDiv = employeeRepository.findByUsernameIgnoreCase(username)
                    .map(emp -> emp.getDivision() == null ? "" : emp.getDivision().trim())
                    .orElse("");
            division = normalizeDivisionScope(rawDiv);
        }
        e.setDivision(division);
        e.setScEngg(clean(req.getScEngg()));
        e.setEntryDate(clean(req.getEntryDate()));
        e.setInitiateDate(clean(req.getInitiateDate()));
        e.setModel(clean(req.getModel()));
        e.setPendingActivity(clean(req.getPendingActivity()));
        e.setResponsible(clean(req.getResponsible()));
        e.setPendingForm(clean(req.getPendingForm()));
        e.setTarClosedDate(clean(req.getTarClosedDate()));
        e.setClosedDate(clean(req.getClosedDate()));
        e.setRemarks(clean(req.getRemarks()));
        e.setStatusType(clean(req.getStatusType()));
        e.setScInchargeRemark(clean(req.getScInchargeRemark()));
    }

    private void applyUpdate(PendingActivityRequest req, PendingActivity e) {
        if (req.getInitiateDate() != null) e.setInitiateDate(clean(req.getInitiateDate()));
        if (req.getModel() != null) e.setModel(clean(req.getModel()));
        if (req.getPendingActivity() != null) e.setPendingActivity(clean(req.getPendingActivity()));
        if (req.getResponsible() != null) e.setResponsible(clean(req.getResponsible()));
        if (req.getPendingForm() != null) e.setPendingForm(clean(req.getPendingForm()));
        if (req.getTarClosedDate() != null) e.setTarClosedDate(clean(req.getTarClosedDate()));
        if (req.getClosedDate() != null) e.setClosedDate(clean(req.getClosedDate()));
        if (req.getRemarks() != null) e.setRemarks(clean(req.getRemarks()));
        if (req.getStatusType() != null) e.setStatusType(clean(req.getStatusType()));
        if (req.getScInchargeRemark() != null) e.setScInchargeRemark(clean(req.getScInchargeRemark()));
    }

    private PendingActivityDTO toDto(PendingActivity p) {
        PendingActivityDTO d = new PendingActivityDTO();
        d.setId(p.getId());
        d.setDivision(p.getDivision());
        d.setScEngg(p.getScEngg());
        d.setEntryDate(p.getEntryDate());
        d.setInitiateDate(p.getInitiateDate());
        d.setModel(p.getModel());
        d.setPendingActivity(p.getPendingActivity());
        d.setResponsible(p.getResponsible());
        d.setPendingForm(p.getPendingForm());
        d.setTarClosedDate(p.getTarClosedDate());
        d.setClosedDate(p.getClosedDate());
        d.setRemarks(p.getRemarks());
        d.setStatusType(p.getStatusType());
        d.setScInchargeRemark(p.getScInchargeRemark());
        resolveScEnggName(d, p.getScEngg());
        return d;
    }

    private void resolveScEnggName(PendingActivityDTO d, String scEngg) {
        if (scEngg == null || scEngg.isBlank()) return;
        try {
            long eid = Long.parseLong(scEngg.trim());
            employeeRepository.findById(eid).ifPresent(emp -> d.setScEnggName(emp.getEmpName()));
        } catch (NumberFormatException ignored) {
            d.setScEnggName(scEngg);
        }
    }

    private String normalizeDivisionScope(String input) {
        if (input == null) return "";
        String raw = input.trim();
        if (raw.isEmpty()) return "";
        if (!raw.matches("\\d+")) return raw;
        try {
            int productId = Integer.parseInt(raw);
            return productmasterRepository.findById(productId)
                    .map(this::resolveProductDivisionName)
                    .orElse(raw);
        } catch (NumberFormatException ignored) {
            return raw;
        }
    }

    private String resolveDivisionScope(String username, boolean isAdmin, String requestedDivision) {
        if (isAdmin) return normalizeDivisionScope(requestedDivision);
        String rawDiv = employeeRepository.findByUsernameIgnoreCase(username)
                .map(e -> e.getDivision() == null ? "" : e.getDivision().trim())
                .orElse("");
        return normalizeDivisionScope(rawDiv);
    }

    private String resolveProductDivisionName(Productmaster p) {
        String division = p.getDivision() == null ? "" : p.getDivision().trim();
        if (!division.isEmpty()) return division;
        String divisionName = p.getDivisionName() == null ? "" : p.getDivisionName().trim();
        return divisionName;
    }

    private static String clean(String v) {
        if (v == null) return null;
        String t = v.trim();
        return t.isEmpty() ? null : t;
    }

    private static boolean blank(String v) {
        return v == null || v.trim().isEmpty();
    }

    /**
     * Prevent empty rows (legacy forms always captured these fields).
     * For create as admin, division must be supplied on the request.
     */
    private void validateMandatoryRequest(PendingActivityRequest req, boolean isAdmin, boolean isCreate) {
        if (isCreate && isAdmin && blank(req.getDivision())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Division is required");
        }
        if (blank(req.getScEngg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SC Engineer is required");
        }
        if (blank(req.getEntryDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entry date is required");
        }
        if (blank(req.getInitiateDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initiate date is required");
        }
        if (blank(req.getPendingActivity())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Activity is required");
        }
        if (blank(req.getModel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description / model is required");
        }
        String st = req.getStatusType() == null ? "" : req.getStatusType().trim();
        if (st.equalsIgnoreCase("completed") && blank(req.getClosedDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Closed date is required when status is Completed");
        }
    }

    private void validateMandatoryEntity(PendingActivity e) {
        if (blank(e.getDivision())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Division is required (configure employee division or select division as admin)");
        }
        if (blank(e.getScEngg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SC Engineer is required");
        }
        if (blank(e.getEntryDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entry date is required");
        }
        if (blank(e.getInitiateDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initiate date is required");
        }
        if (blank(e.getPendingActivity())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Activity is required");
        }
        if (blank(e.getModel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description / model is required");
        }
        String st = e.getStatusType() == null ? "" : e.getStatusType().trim();
        if (st.equalsIgnoreCase("completed") && blank(e.getClosedDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Closed date is required when status is Completed");
        }
    }
}
