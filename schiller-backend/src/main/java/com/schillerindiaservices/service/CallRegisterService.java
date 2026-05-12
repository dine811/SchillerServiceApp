package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.CallRegisterDTO;
import com.schillerindiaservices.entity.CallRegister;
import com.schillerindiaservices.entity.CallRegisterDemo;
import com.schillerindiaservices.entity.Productmaster;
import com.schillerindiaservices.repository.CallRegisterDemoRepository;
import com.schillerindiaservices.repository.CallRegisterRepository;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.ProductmasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.schillerindiaservices.dto.CallRegisterUpdateRequest;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CallRegisterService {

    private final CallRegisterRepository callRegisterRepository;
    private final CallRegisterDemoRepository callRegisterDemoRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductmasterRepository productmasterRepository;

    public Page<CallRegisterDTO> findPendingAdmin(String division, String keyword, Pageable pageable) {
        String div = normalizeDivisionScope(division);
        String kw = keyword == null ? "" : keyword.trim();
        return callRegisterDemoRepository.findPendingAdmin(div, kw, pageable).map(this::toDto);
    }

    /**
     * Legacy CallListEngg.jsp behavior: non-admins are implicitly scoped to their own division.
     * Admins can view all (or filter by requested division).
     */
    public Page<CallRegisterDTO> findPendingForUser(
            String username,
            boolean isAdmin,
            String requestedDivision,
            String keyword,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div;
        if (isAdmin) {
            div = normalizeDivisionScope(requestedDivision);
        } else {
            String rawDiv = employeeRepository.findByUsernameIgnoreCase(username)
                    .map(e -> e.getDivision() == null ? "" : e.getDivision().trim())
                    .orElse("");
            div = normalizeDivisionScope(rawDiv);
        }
        return callRegisterDemoRepository.findPendingAdmin(div, kw, pageable).map(this::toDto);
    }

    /** Legacy ClosedCalls.jsp — completed rows live in {@code callregister_demo}, not {@code callregister}. */
    public Page<CallRegisterDTO> findCompletedAdmin(
            String division, String keyword, LocalDate from, LocalDate to, Pageable pageable) {
        String div = normalizeDivisionScope(division);
        String kw = keyword == null ? "" : keyword.trim();
        String fromStr = from == null ? "" : from.toString();
        String toStr = to == null ? "" : to.toString();
        return callRegisterDemoRepository.findCompletedAdmin(div, kw, fromStr, toStr, pageable).map(this::toDto);
    }

    /**
     * Legacy ClosedCalls.jsp behavior:
     * - Admin / ViceChancellor: all divisions (or requested division filter)
     * - Others: fixed to logged-in user's division
     */
    public Page<CallRegisterDTO> findCompletedForUser(
            String username,
            boolean isPrivileged,
            String requestedDivision,
            String keyword,
            LocalDate from,
            LocalDate to,
            Pageable pageable
    ) {
        String kw = keyword == null ? "" : keyword.trim();
        String div;
        if (isPrivileged) {
            div = normalizeDivisionScope(requestedDivision);
        } else {
            String rawDiv = employeeRepository.findByUsernameIgnoreCase(username)
                    .map(e -> e.getDivision() == null ? "" : e.getDivision().trim())
                    .orElse("");
            div = normalizeDivisionScope(rawDiv);
        }
        String fromStr = from == null ? "" : from.toString();
        String toStr = to == null ? "" : to.toString();
        return callRegisterDemoRepository.findCompletedAdmin(div, kw, fromStr, toStr, pageable).map(this::toDto);
    }

    /** Single row — tries {@code callregister_demo} first (ClosedCalls edit), then {@code callregister}. */
    public CallRegisterDTO findById(Long id) {
        return callRegisterDemoRepository.findById(id)
                .map(this::toDto)
                .or(() -> callRegisterRepository.findById(id).map(this::toDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Applies legacy-style partial update: only non-null fields in the request are written.
     * Updates {@code callregister_demo} or {@code callregister} depending on where the id exists.
     */
    @Transactional
    public CallRegisterDTO update(Long id, CallRegisterUpdateRequest req) {
        if (callRegisterDemoRepository.existsById(id)) {
            CallRegisterDemo e = callRegisterDemoRepository.findById(id).orElseThrow();
            applyUpdate(req, e);
            return toDto(callRegisterDemoRepository.save(e));
        }
        if (callRegisterRepository.existsById(id)) {
            CallRegister e = callRegisterRepository.findById(id).orElseThrow();
            applyUpdate(req, e);
            return toDto(callRegisterRepository.save(e));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private static void applyUpdate(CallRegisterUpdateRequest req, CallRegisterDemo e) {
        if (req.getRemarks() != null) {
            e.setRemarks(req.getRemarks());
        }
        if (req.getDuration() != null) {
            e.setDuration(req.getDuration());
        }
        if (req.getStatusType() != null) {
            e.setStatusType(req.getStatusType());
        }
        if (req.getCallDate() != null) {
            e.setCallDate(req.getCallDate());
        }
        if (req.getCallType() != null) {
            e.setCallType(req.getCallType());
        }
        if (req.getTypeOfCall() != null) {
            e.setTypeOfCall(req.getTypeOfCall());
        }
        if (req.getTypeOfCommunication() != null) {
            e.setTypeOfCommunication(req.getTypeOfCommunication());
        }
    }

    private static void applyUpdate(CallRegisterUpdateRequest req, CallRegister e) {
        if (req.getRemarks() != null) {
            e.setRemarks(req.getRemarks());
        }
        if (req.getDuration() != null) {
            e.setDuration(req.getDuration());
        }
        if (req.getStatusType() != null) {
            e.setStatusType(req.getStatusType());
        }
        if (req.getCallDate() != null) {
            e.setCallDate(req.getCallDate());
        }
        if (req.getCallType() != null) {
            e.setCallType(req.getCallType());
        }
        if (req.getTypeOfCall() != null) {
            e.setTypeOfCall(req.getTypeOfCall());
        }
        if (req.getTypeOfCommunication() != null) {
            e.setTypeOfCommunication(req.getTypeOfCommunication());
        }
    }

    /** Row counts for empty-state diagnostics on call-register pages. */
    public Map<String, Long> summary() {
        long cr = callRegisterRepository.count();
        long demo = callRegisterDemoRepository.count();
        return Map.of(
                "total", cr + demo,
                "pending", callRegisterDemoRepository.countPendingRows(),
                "completed", callRegisterDemoRepository.countCompletedRows()
        );
    }

    @Transactional
    public void deleteById(Long id) {
        if (callRegisterRepository.existsById(id)) {
            callRegisterRepository.deleteById(id);
        }
        if (callRegisterDemoRepository.existsById(id)) {
            callRegisterDemoRepository.deleteById(id);
        }
    }

    private CallRegisterDTO toDto(CallRegister c) {
        CallRegisterDTO d = baseDto(c.getId(), c.getDivision(), c.getEntryDate(), c.getCallDate(),
                c.getScEngg(), c.getEngineer(), c.getModel(), c.getCallType(), c.getTypeOfCall(),
                c.getTypeOfCommunication(), c.getDuration(), c.getRemarks(), c.getStatusType());
        resolveScEnggName(d, c.getScEngg());
        return d;
    }

    private CallRegisterDTO toDto(CallRegisterDemo c) {
        CallRegisterDTO d = baseDto(c.getId(), c.getDivision(), c.getEntryDate(), c.getCallDate(),
                c.getScEngg(), c.getEngineer(), c.getModel(), c.getCallType(), c.getTypeOfCall(),
                c.getTypeOfCommunication(), c.getDuration(), c.getRemarks(), c.getStatusType());
        resolveScEnggName(d, c.getScEngg());
        return d;
    }

    private static CallRegisterDTO baseDto(Long id, String division, String entryDate, String callDate,
            String scEngg, String engineer, String model, String callType, String typeOfCall,
            String typeOfCommunication, String duration, String remarks, String statusType) {
        CallRegisterDTO d = new CallRegisterDTO();
        d.setId(id);
        d.setDivision(division);
        d.setEntryDate(entryDate);
        d.setCallDate(callDate);
        d.setScEngg(scEngg);
        d.setEngineer(engineer);
        d.setModel(model);
        d.setCallType(callType);
        d.setTypeOfCall(typeOfCall);
        d.setTypeOfCommunication(typeOfCommunication);
        d.setDuration(duration);
        d.setRemarks(remarks);
        d.setStatusType(statusType);
        return d;
    }

    private void resolveScEnggName(CallRegisterDTO d, String scEngg) {
        if (scEngg == null || scEngg.isBlank()) {
            return;
        }
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

    private String resolveProductDivisionName(Productmaster p) {
        String division = p.getDivision() == null ? "" : p.getDivision().trim();
        if (!division.isEmpty()) return division;
        String divisionName = p.getDivisionName() == null ? "" : p.getDivisionName().trim();
        return divisionName;
    }
}
