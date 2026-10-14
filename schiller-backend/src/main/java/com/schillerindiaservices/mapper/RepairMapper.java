package com.schillerindiaservices.mapper;

import com.schillerindiaservices.dto.RepairDTO;
import com.schillerindiaservices.entity.RepairMaster;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class RepairMapper {
    private final Clock clock;

    public RepairMapper() {
        this(Clock.systemDefaultZone());
    }

    RepairMapper(Clock clock) {
        this.clock = clock;
    }

    public RepairDTO toDto(RepairMaster repair) {
        RepairDTO dto = new RepairDTO();
        dto.setId(repair.getId());
        dto.setServiceId(repair.getServiceId());
        dto.setEntryDate(repair.getEntryDate());
        dto.setDivision(repair.getDivision());
        dto.setScRefNo(repair.getScRefNo());
        dto.setDefGirNo(repair.getDefGirNo());
        dto.setCategory(repair.getCategory());
        dto.setModel(repair.getModel());
        dto.setDefBoardModuleName(repair.getDefBrdModName());
        dto.setUnitStatus(repair.getUnitStatus());
        dto.setTechnicalRemarks(repair.getTechRemarks());
        dto.setComponentsUsed(repair.getComponentsUsedToRepair());
        dto.setFinishDate(repair.getFinishedDate());
        dto.setRepairedBy(repair.getRepairedBy());
        dto.setFinalRemarks(repair.getFinalRemarks());
        dto.setStatus(repair.getStatus());
        int days = days(repair);
        dto.setDays(days);
        dto.setPendingDays(repair.getStatus() == com.schillerindiaservices.entity.RepairStatus.PENDING ? days : 0);
        return dto;
    }

    private int days(RepairMaster repair) {
        if (repair.getStatus() != null && repair.getStatus().databaseValue().equals("completed")) {
            return repair.getNoOfDays() == null ? 0 : repair.getNoOfDays();
        }
        if (repair.getEntryDate() == null) return 0;
        return Math.max(0, Math.toIntExact(ChronoUnit.DAYS.between(repair.getEntryDate(), LocalDate.now(clock))));
    }
}
