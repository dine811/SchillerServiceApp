package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.NonsaleableAdminRowDTO;
import com.schillerindiaservices.entity.Nonsaleablemaster;
import com.schillerindiaservices.repository.BranchRepository;
import com.schillerindiaservices.repository.DealerRepository;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.ModelRepository;
import com.schillerindiaservices.repository.NonsaleablemasterRepository;
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
 * Non-salable admin list (Pending) and Salables (Completed) — legacy nonSaleAdminList.jsp, SalablesList.jsp, ExportNonSale_AdminController.
 */
@Service
@RequiredArgsConstructor
public class NonsaleableService {

    private static final int MAX_EXPORT = 13_000;
    private static final DateTimeFormatter UI_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final NonsaleablemasterRepository nonsaleablemasterRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final DealerRepository dealerRepository;
    private final ModelRepository modelRepository;

    @Transactional(readOnly = true)
    public Page<NonsaleableAdminRowDTO> findPendingAdmin(String keyword, Pageable pageable) {
        return nonsaleablemasterRepository.findAll(pendingSpec(keyword), pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Page<NonsaleableAdminRowDTO> findSalables(String division, String keyword, Pageable pageable) {
        return nonsaleablemasterRepository.findAll(salablesSpec(division, keyword), pageable).map(this::toDto);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null || !nonsaleablemasterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        nonsaleablemasterRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public byte[] exportAdminExcel(String status, LocalDate from, LocalDate to, String division) throws IOException {
        String st = status == null || status.isBlank() ? "Pending" : status.trim();
        Specification<Nonsaleablemaster> spec = exportSpec(st, from, to, division);
        List<Nonsaleablemaster> rows = nonsaleablemasterRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "id"));
        if (rows.size() > MAX_EXPORT) {
            rows = rows.subList(0, MAX_EXPORT);
        }

        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("Non-Salable");
            Row head = sheet.createRow(0);
            String[] headers = {
                    "DIVISION", "ENTRY DATE", "UNIT DETAILS", "FQC INWARD DATE", "REGION", "BRANCH", "ENGINEER", "DEALER",
                    "SUPPLIER", "MODEL", "MODEL S/N", "UNIT CONFIGURATION", "CUSTOMER NAME", "REPORTED PROBLEM",
                    "REPLACED UNIT S/N", "REPLACEMENT SHIP DATE", "DEFECTIVE UNIT RECEIVED DATE", "FQC OBSERVATION",
                    "SC INWARD DATE", "SC ENGINEER", "SC OBSERVATION", "REQUIRED PARTS", "ROOT CAUSE", "SC ACTION PLAN",
                    "TENTATIVE DATE", "SHIP DATE TO FQC", "FQC FINAL REMARKS", "FINAL STATUS", "TIMESTAMP"
            };
            for (int i = 0; i < headers.length; i++) {
                head.createCell(i).setCellValue(headers[i]);
            }
            int r = 1;
            for (Nonsaleablemaster n : rows) {
                Row row = sheet.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(nvl(n.getDivision()));
                row.createCell(c++).setCellValue(fmtUi(n.getEntryDate()));
                row.createCell(c++).setCellValue(nvl(n.getUnitDetails()));
                row.createCell(c++).setCellValue(fmtUi(n.getFqcInDate()));
                row.createCell(c++).setCellValue(nvl(n.getRegion()));
                row.createCell(c++).setCellValue(resolveBranchName(n.getBranch()));
                row.createCell(c++).setCellValue(resolveEmpName(n.getEngineer()));
                row.createCell(c++).setCellValue(resolveDealerName(n.getDealer()));
                row.createCell(c++).setCellValue(nvl(n.getSupplier()));
                row.createCell(c++).setCellValue(resolveModelName(n.getModel()));
                row.createCell(c++).setCellValue(nvl(n.getModelSn()));
                row.createCell(c++).setCellValue(nvl(n.getUnitConfig()));
                row.createCell(c++).setCellValue(nvl(n.getCustName()));
                row.createCell(c++).setCellValue(nvl(n.getReportedProblm()));
                row.createCell(c++).setCellValue(nvl(n.getReplacedUnitSn()));
                row.createCell(c++).setCellValue(fmtUi(n.getReplaceShipDate()));
                row.createCell(c++).setCellValue(fmtUi(n.getDefectUnitReceivedDate()));
                row.createCell(c++).setCellValue(nvl(n.getFqcObservation()));
                row.createCell(c++).setCellValue(fmtUi(n.getScInwardDate()));
                row.createCell(c++).setCellValue(resolveEmpName(n.getScEngg()));
                row.createCell(c++).setCellValue(nvl(n.getScObservation()));
                row.createCell(c++).setCellValue(nvl(n.getRequiredParts()));
                row.createCell(c++).setCellValue(nvl(n.getRootCause()));
                row.createCell(c++).setCellValue(nvl(n.getActionPlan()));
                row.createCell(c++).setCellValue(fmtUi(n.getTentDateShipDate()));
                row.createCell(c++).setCellValue(fmtUi(n.getShipDateFqc()));
                row.createCell(c++).setCellValue(nvl(n.getFqcFinalRemark()));
                row.createCell(c++).setCellValue(nvl(n.getFinalStatus()));
                row.createCell(c).setCellValue(n.getCurrentDate() == null ? "" : n.getCurrentDate().toString());
            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(bos);
            return bos.toByteArray();
        }
    }

    private static Specification<Nonsaleablemaster> pendingSpec(String keyword) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(cb.lower(cb.trim(root.get("finalStatus"))), "pending"));
            String kw = keyword == null ? "" : keyword.trim();
            if (!kw.isEmpty()) {
                String pattern = "%" + kw.toLowerCase() + "%";
                preds.add(cb.or(
                        cb.and(cb.isNotNull(root.get("modelSn")), cb.like(cb.lower(root.get("modelSn")), pattern)),
                        cb.and(cb.isNotNull(root.get("finalStatus")), cb.like(cb.lower(root.get("finalStatus")), pattern)),
                        cb.and(cb.isNotNull(root.get("unitDetails")), cb.like(cb.lower(root.get("unitDetails")), pattern))
                ));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private static Specification<Nonsaleablemaster> salablesSpec(String division, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(cb.lower(cb.trim(root.get("finalStatus"))), "completed"));
            String div = division == null ? "" : division.trim();
            if (!div.isEmpty() && !"1".equals(div)) {
                preds.add(cb.equal(root.get("division"), div));
            }
            String kw = keyword == null ? "" : keyword.trim();
            if (!kw.isEmpty()) {
                String pattern = "%" + kw.toLowerCase() + "%";
                preds.add(cb.or(
                        cb.and(cb.isNotNull(root.get("modelSn")), cb.like(cb.lower(root.get("modelSn")), pattern)),
                        cb.and(cb.isNotNull(root.get("finalStatus")), cb.like(cb.lower(root.get("finalStatus")), pattern)),
                        cb.and(cb.isNotNull(root.get("unitDetails")), cb.like(cb.lower(root.get("unitDetails")), pattern))
                ));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private static Specification<Nonsaleablemaster> exportSpec(String status, LocalDate from, LocalDate to, String division) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(cb.lower(cb.trim(root.get("finalStatus"))), status.toLowerCase()));
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

    private NonsaleableAdminRowDTO toDto(Nonsaleablemaster n) {
        NonsaleableAdminRowDTO d = new NonsaleableAdminRowDTO();
        d.setId(n.getId());
        d.setEntryDate(fmtIso(n.getEntryDate()));
        d.setUnitDetails(n.getUnitDetails());
        d.setFqcInDate(fmtIso(n.getFqcInDate()));
        d.setModel(n.getModel());
        d.setModelName(resolveModelName(n.getModel()));
        d.setFqcObservation(n.getFqcObservation());
        d.setScInwardDate(fmtIso(n.getScInwardDate()));
        d.setScObservation(n.getScObservation());
        d.setModelSn(n.getModelSn());
        d.setTentDateShipDate(fmtIso(n.getTentDateShipDate()));
        d.setShipDateFqc(fmtIso(n.getShipDateFqc()));
        d.setFinalStatus(n.getFinalStatus());
        return d;
    }

    private String resolveModelName(String modelRaw) {
        if (modelRaw == null || modelRaw.isBlank()) {
            return "";
        }
        try {
            long id = Long.parseLong(modelRaw.trim());
            return modelRepository.findById(id).map(m -> m.getModelName()).orElse(modelRaw);
        } catch (NumberFormatException e) {
            return modelRaw;
        }
    }

    private String resolveEmpName(String raw) {
        if (raw == null || raw.isBlank()) {
            return "";
        }
        try {
            long id = Long.parseLong(raw.trim());
            return employeeRepository.findById(id).map(e -> e.getEmpName()).orElse(raw);
        } catch (NumberFormatException e) {
            return raw;
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
