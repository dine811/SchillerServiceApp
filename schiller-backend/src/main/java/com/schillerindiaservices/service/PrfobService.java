package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.PrfobAdminRowDTO;
import com.schillerindiaservices.entity.Prfobmaster;
import com.schillerindiaservices.repository.BranchRepository;
import com.schillerindiaservices.repository.DealerRepository;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.PrfobmasterRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * PRF/OB admin lists — legacy PRFOB_AdminList.jsp (pending), PRFOB_AdminList_closed.jsp (completed), export.
 */
@Service
@RequiredArgsConstructor
public class PrfobService {

    private static final String STATUS_PENDING = "Pending";
    private static final String STATUS_COMPLETED = "Completed";
    private static final int MAX_EXPORT = 13_000;
    private static final DateTimeFormatter UI_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final PrfobmasterRepository prfobmasterRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final DealerRepository dealerRepository;

    @Transactional(readOnly = true)
    public Page<PrfobAdminRowDTO> findPendingAdmin(String division, String keyword, Pageable pageable) {
        Specification<Prfobmaster> spec = pendingSpec(division, keyword);
        return prfobmasterRepository.findAll(spec, pageable).map(this::toDto);
    }

    /** Legacy PRFOB_AdminList_closed.jsp — {@code status_type = 'Completed'}. */
    @Transactional(readOnly = true)
    public Page<PrfobAdminRowDTO> findCompletedAdmin(String division, String keyword, Pageable pageable) {
        Specification<Prfobmaster> spec = completedSpec(division, keyword);
        return prfobmasterRepository.findAll(spec, pageable).map(this::toDto);
    }

    /** Legacy PRFOBController?action=delete — remove row from {@code prfobmaster}. */
    @Transactional
    public void deleteById(Integer id) {
        if (id == null || !prfobmasterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        prfobmasterRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public byte[] exportAdminExcel(String status, LocalDate from, LocalDate to, String division) throws IOException {
        String st = status == null || status.isBlank() ? STATUS_PENDING : status.trim();
        Specification<Prfobmaster> spec = exportSpec(st, from, to, division);
        List<Prfobmaster> rows = prfobmasterRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "id"));
        if (rows.size() > MAX_EXPORT) {
            rows = rows.subList(0, MAX_EXPORT);
        }

        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("PRF-OB");
            Row head = sheet.createRow(0);
            String[] headers = {
                    "ENTRY DATE", "SC_ENGINEER", "TYPE", "RAISED DATE", "RECEIVED DATE FROM SC", "REGION",
                    "BRANCH", "ENGINEER", "DEALER", "MODEL", "SUPPLIER", "WARRENTY STATUS",
                    "PRF/OB REF NO.", "CRM REF NO.", "PART TYPE", "PART DESCRIPTION", "REMARKS",
                    "STATUS", "EXECUTED DATE", "SPARES RECEIVED DATE AT SVC"
            };
            for (int i = 0; i < headers.length; i++) {
                head.createCell(i).setCellValue(headers[i]);
            }
            int r = 1;
            for (Prfobmaster p : rows) {
                Row row = sheet.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(fmtUi(p.getEntryDate()));
                row.createCell(c++).setCellValue(resolveEmpName(p.getScEngg()));
                row.createCell(c++).setCellValue(nvl(p.getWorkType()));
                row.createCell(c++).setCellValue(fmtUi(p.getRaisedDate()));
                row.createCell(c++).setCellValue(fmtUi(p.getReceivedDate()));
                row.createCell(c++).setCellValue(nvl(p.getRegion()));
                row.createCell(c++).setCellValue(resolveBranchName(p.getBranch()));
                row.createCell(c++).setCellValue(resolveEmpNameById(p.getEngineer()));
                row.createCell(c++).setCellValue(resolveDealerName(p.getDealer()));
                row.createCell(c++).setCellValue(nvl(p.getModel()));
                row.createCell(c++).setCellValue(nvl(p.getSupplier()));
                row.createCell(c++).setCellValue(nvl(p.getWarrentyStatus()));
                row.createCell(c++).setCellValue(nvl(p.getPrfobRefNo()));
                row.createCell(c++).setCellValue(nvl(p.getCrmRefNo()));
                row.createCell(c++).setCellValue(nvl(p.getPartType()));
                row.createCell(c++).setCellValue(nvl(p.getPartDescription()));
                row.createCell(c++).setCellValue(nvl(p.getRemarks()));
                row.createCell(c++).setCellValue(nvl(p.getStatusType()));
                row.createCell(c++).setCellValue(fmtUi(p.getExecutedDate()));
                row.createCell(c).setCellValue(nvl(p.getReceiveDateFromSc()));
            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(bos);
            return bos.toByteArray();
        }
    }

    private static Specification<Prfobmaster> pendingSpec(String division, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(root.get("statusType"), STATUS_PENDING));
            String div = division == null ? "" : division.trim();
            if (!div.isEmpty() && !"1".equals(div)) {
                preds.add(cb.equal(root.get("division"), div));
            }
            String kw = keyword == null ? "" : keyword.trim();
            if (!kw.isEmpty()) {
                String pattern = "%" + kw.toLowerCase() + "%";
                preds.add(cb.or(
                        cb.and(cb.isNotNull(root.get("workType")), cb.like(cb.lower(root.get("workType")), pattern)),
                        cb.and(cb.isNotNull(root.get("prfobRefNo")), cb.like(cb.lower(root.get("prfobRefNo")), pattern)),
                        cb.and(cb.isNotNull(root.get("crmRefNo")), cb.like(cb.lower(root.get("crmRefNo")), pattern))
                ));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private static Specification<Prfobmaster> completedSpec(String division, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(root.get("statusType"), STATUS_COMPLETED));
            String div = division == null ? "" : division.trim();
            if (!div.isEmpty() && !"1".equals(div)) {
                preds.add(cb.equal(root.get("division"), div));
            }
            String kw = keyword == null ? "" : keyword.trim();
            if (!kw.isEmpty()) {
                String pattern = "%" + kw.toLowerCase() + "%";
                preds.add(cb.or(
                        cb.and(cb.isNotNull(root.get("workType")), cb.like(cb.lower(root.get("workType")), pattern)),
                        cb.and(cb.isNotNull(root.get("prfobRefNo")), cb.like(cb.lower(root.get("prfobRefNo")), pattern)),
                        cb.and(cb.isNotNull(root.get("crmRefNo")), cb.like(cb.lower(root.get("crmRefNo")), pattern))
                ));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private static Specification<Prfobmaster> exportSpec(String status, LocalDate from, LocalDate to, String division) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(root.get("statusType"), status));
            String div = division == null ? "" : division.trim();
            if (!div.isEmpty() && !"1".equals(div)) {
                preds.add(cb.equal(root.get("division"), div));
            }
            if (from != null && to != null) {
                preds.add(cb.between(root.get("entryDate"), from, to));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private PrfobAdminRowDTO toDto(Prfobmaster p) {
        PrfobAdminRowDTO d = new PrfobAdminRowDTO();
        d.setId(p.getId());
        d.setDivision(p.getDivision());
        d.setEntryDate(fmtIso(p.getEntryDate()));
        d.setScEngg(p.getScEngg());
        d.setScEnggName(resolveEmpName(p.getScEngg()));
        d.setWorkType(p.getWorkType());
        d.setRaisedDate(fmtIso(p.getRaisedDate()));
        d.setReceivedDate(fmtIso(p.getReceivedDate()));
        d.setRegion(p.getRegion());
        d.setBranch(p.getBranch());
        d.setBranchName(resolveBranchName(p.getBranch()));
        d.setEngineer(p.getEngineer());
        d.setEngineerName(resolveEmpNameById(p.getEngineer()));
        d.setDealer(p.getDealer());
        d.setModel(p.getModel());
        d.setSupplier(p.getSupplier());
        d.setWarrentyStatus(p.getWarrentyStatus());
        d.setPrfobRefNo(p.getPrfobRefNo());
        d.setCrmRefNo(p.getCrmRefNo());
        d.setRemarks(p.getRemarks());
        d.setStatusType(p.getStatusType());
        d.setExecutedDate(fmtIso(p.getExecutedDate()));
        d.setPartType(p.getPartType());
        d.setPartDescription(p.getPartDescription());
        d.setReceiveDateFromSc(p.getReceiveDateFromSc());
        return d;
    }

    private String resolveEmpName(String scEnggRaw) {
        if (scEnggRaw == null || scEnggRaw.isBlank()) {
            return "";
        }
        try {
            long id = Long.parseLong(scEnggRaw.trim());
            return employeeRepository.findById(id).map(e -> e.getEmpName()).orElse(scEnggRaw);
        } catch (NumberFormatException e) {
            return scEnggRaw;
        }
    }

    private String resolveEmpNameById(String engineerRaw) {
        if (engineerRaw == null || engineerRaw.isBlank()) {
            return "";
        }
        try {
            long id = Long.parseLong(engineerRaw.trim());
            return employeeRepository.findById(id).map(e -> e.getEmpName()).orElse(engineerRaw);
        } catch (NumberFormatException e) {
            return engineerRaw;
        }
    }

    private String resolveBranchName(String branchRaw) {
        if (branchRaw == null || branchRaw.isBlank()) {
            return "";
        }
        try {
            long id = Long.parseLong(branchRaw.trim());
            return branchRepository.findById(id).map(b -> b.getBranchName()).orElse(branchRaw);
        } catch (NumberFormatException e) {
            return branchRaw;
        }
    }

    private String resolveDealerName(String dealerRaw) {
        if (dealerRaw == null || dealerRaw.isBlank()) {
            return "";
        }
        try {
            long id = Long.parseLong(dealerRaw.trim());
            return dealerRepository.findById(id).map(d -> d.getDealerName()).orElse(dealerRaw);
        } catch (NumberFormatException e) {
            return dealerRaw;
        }
    }

    private static String fmtIso(LocalDate d) {
        return d == null ? null : d.toString();
    }

    private static String fmtUi(LocalDate d) {
        return d == null ? "" : UI_DATE.format(d);
    }

    private static String nvl(String s) {
        return s == null ? "" : s;
    }
}
