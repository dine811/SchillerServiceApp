package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.BirAdminRowDTO;
import com.schillerindiaservices.entity.Birmaster;
import com.schillerindiaservices.repository.BirmasterRepository;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.repository.ModelRepository;
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
 * BIR admin pending / closed — legacy BIRAdminList.jsp, ClosedBIRList_admin.jsp, ExportBIR_AdminController.
 */
@Service
@RequiredArgsConstructor
public class BirService {

    private static final int MAX_EXPORT = 13_000;
    private static final DateTimeFormatter UI_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final BirmasterRepository birmasterRepository;
    private final ModelRepository modelRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Page<BirAdminRowDTO> findPendingAdmin(String keyword, Pageable pageable) {
        return birmasterRepository.findAll(pendingSpec(keyword), pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Page<BirAdminRowDTO> findCompletedAdmin(String division, String keyword, Pageable pageable) {
        return birmasterRepository.findAll(completedSpec(division, keyword), pageable).map(this::toDto);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null || !birmasterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        birmasterRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public byte[] exportAdminExcel(String status, LocalDate from, LocalDate to, String division) throws IOException {
        String st = status == null || status.isBlank() ? "Pending" : status.trim();
        Specification<Birmaster> spec = exportSpec(st, from, to, division);
        List<Birmaster> rows = birmasterRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "id"));
        if (rows.size() > MAX_EXPORT) {
            rows = rows.subList(0, MAX_EXPORT);
        }

        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("BIR");
            Row head = sheet.createRow(0);
            String[] headers = {
                    "DIVISION", "INWARD DATE", "ENTRY DATE", "BIR REF NO.", "SUPPLIER", "MODEL", "CONFIGURATION",
                    "RECEIVED QTY", "UNIT SERIAL NO.", "INVOICE NO.", "INVOICE DATE", "PREVIOUS SOFTWARE VERSION",
                    "PRESENT SOFTWARE VERSION", "ACCESSORY CHANGES", "ACCESSORY DETAILS", "USER MANUAL UPDATE",
                    "FQC REMARKS", "SC ENGINEER", "ACCESSORY CHANGES REMARKS", "HARDWARE CHANGES", "SERVICE MANUAL UPDATE",
                    "HARDWARE CHANGES REMARKS", "TS TEAM VERIFICATION DATE", "PS ENGINEER", "SOFTWARE CHANGES REMARKS",
                    "CNR/TECHNEWS CIRCULATION", "CNR/TECHNEWS REF NO.", "CNR/TECHNEWS RELESE DATE",
                    "PS TEAM VERIFICATION DATE", "APPROVED DATE", "STATUS", "CLOSED DATE", "TIMESTAMP"
            };
            for (int i = 0; i < headers.length; i++) {
                head.createCell(i).setCellValue(headers[i]);
            }
            int r = 1;
            for (Birmaster b : rows) {
                Row row = sheet.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(nvl(b.getDivision()));
                row.createCell(c++).setCellValue(fmtUi(b.getFqcInDate()));
                row.createCell(c++).setCellValue(fmtUi(b.getEntryDate()));
                row.createCell(c++).setCellValue(nvl(b.getBirRefNo()));
                row.createCell(c++).setCellValue(nvl(b.getSupplier()));
                row.createCell(c++).setCellValue(resolveModelName(b.getModel()));
                row.createCell(c++).setCellValue(nvl(b.getConfiguration()));
                row.createCell(c++).setCellValue(nvl(b.getReceivedQty()));
                row.createCell(c++).setCellValue(nvl(b.getUnitSerialNo()));
                row.createCell(c++).setCellValue(nvl(b.getInvoiceNo()));
                row.createCell(c++).setCellValue(fmtUi(b.getInvoiceDate()));
                row.createCell(c++).setCellValue(nvl(b.getSoftwrChanges()));
                row.createCell(c++).setCellValue(nvl(b.getSoftwrVersion()));
                row.createCell(c++).setCellValue(nvl(b.getAccesoryChanges()));
                row.createCell(c++).setCellValue(nvl(b.getAccesoryDetails()));
                row.createCell(c++).setCellValue(nvl(b.getUserManualUpdate()));
                row.createCell(c++).setCellValue(nvl(b.getFqcRemarks()));
                row.createCell(c++).setCellValue(resolveEmpName(b.getScEngg()));
                row.createCell(c++).setCellValue(nvl(b.getAccesChngRemark()));
                row.createCell(c++).setCellValue(nvl(b.getHardwrChanges()));
                row.createCell(c++).setCellValue(nvl(b.getServiceManualUpdate()));
                row.createCell(c++).setCellValue(nvl(b.getHrdwrChangRemark()));
                row.createCell(c++).setCellValue(fmtUi(b.getTsTeamVerDate()));
                row.createCell(c++).setCellValue(resolveEmpName(b.getPsEngg()));
                row.createCell(c++).setCellValue(nvl(b.getSftwrChngRemark()));
                row.createCell(c++).setCellValue(nvl(b.getCnrRefNo()));
                row.createCell(c++).setCellValue(nvl(b.getCnrTecRefNum()));
                row.createCell(c++).setCellValue(fmtUi(b.getCnrReleseDate()));
                row.createCell(c++).setCellValue(fmtUi(b.getPsTeamVerDate()));
                row.createCell(c++).setCellValue(fmtUi(b.getApprovedDate()));
                row.createCell(c++).setCellValue(nvl(b.getFinalStatus()));
                row.createCell(c++).setCellValue(fmtUi(b.getClosedDate()));
                row.createCell(c).setCellValue(b.getCurrentDate() == null ? "" : b.getCurrentDate().toString());
            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(bos);
            return bos.toByteArray();
        }
    }

    private static Specification<Birmaster> pendingSpec(String keyword) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            preds.add(cb.equal(cb.lower(cb.trim(root.get("finalStatus"))), "pending"));
            String kw = keyword == null ? "" : keyword.trim();
            if (!kw.isEmpty()) {
                String pattern = "%" + kw.toLowerCase() + "%";
                preds.add(cb.or(
                        cb.and(cb.isNotNull(root.get("division")), cb.like(cb.lower(root.get("division")), pattern)),
                        cb.and(cb.isNotNull(root.get("finalStatus")), cb.like(cb.lower(root.get("finalStatus")), pattern)),
                        cb.and(cb.isNotNull(root.get("birRefNo")), cb.like(cb.lower(root.get("birRefNo")), pattern))
                ));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private static Specification<Birmaster> completedSpec(String division, String keyword) {
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
                        cb.and(cb.isNotNull(root.get("division")), cb.like(cb.lower(root.get("division")), pattern)),
                        cb.and(cb.isNotNull(root.get("finalStatus")), cb.like(cb.lower(root.get("finalStatus")), pattern)),
                        cb.and(cb.isNotNull(root.get("birRefNo")), cb.like(cb.lower(root.get("birRefNo")), pattern))
                ));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private static Specification<Birmaster> exportSpec(String status, LocalDate from, LocalDate to, String division) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();
            String st = status == null || status.isBlank() ? "Pending" : status.trim();
            preds.add(cb.equal(cb.lower(cb.trim(root.get("finalStatus"))), st.toLowerCase()));
            String div = division == null ? "" : division.trim();
            if (!div.isEmpty() && !"1".equals(div)) {
                preds.add(cb.equal(root.get("division"), div));
            }
            if (from != null && to != null) {
                LocalDate lo = from.isAfter(to) ? to : from;
                LocalDate hi = from.isAfter(to) ? from : to;
                preds.add(cb.between(root.get("fqcInDate"), lo, hi));
            }
            return cb.and(preds.toArray(Predicate[]::new));
        };
    }

    private BirAdminRowDTO toDto(Birmaster b) {
        BirAdminRowDTO d = new BirAdminRowDTO();
        d.setId(b.getId());
        d.setFqcInDate(fmtIso(b.getFqcInDate()));
        d.setDivision(b.getDivision());
        d.setModel(b.getModel());
        d.setModelName(resolveModelName(b.getModel()));
        d.setConfiguration(b.getConfiguration());
        d.setReceivedQty(b.getReceivedQty());
        d.setSoftwrChanges(b.getSoftwrChanges());
        d.setHardwrChanges(b.getHardwrChanges());
        d.setAccesoryDetails(b.getAccesoryDetails());
        d.setCnrRefNo(b.getCnrRefNo());
        d.setBirRefNo(b.getBirRefNo());
        d.setFinalStatus(b.getFinalStatus());
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
