package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.*;
import com.schillerindiaservices.entity.*;
import com.schillerindiaservices.mapper.RepairMapper;
import com.schillerindiaservices.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepairServiceTest {
    @Mock RepairRepository repairs; @Mock ServiceRepository services;
    @Mock EmployeeRepository employees; @Mock ModelRepository models;
    @Mock DropdownMasterRepository dropdowns;
    RepairService subject;
    @BeforeEach void setup() { subject = new RepairService(repairs, services, employees, models, dropdowns, new RepairMapper()); }

    @Test void initiationCopiesServiceAndSetsRp() {
        ServiceMaster source = source("NR");
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source));
        when(repairs.saveAndFlush(any())).thenAnswer(a -> { RepairMaster r = a.getArgument(0); r.setId(7); return r; });
        RepairDTO result = subject.createFromService(42L, "pfrn", "admin", true);
        assertThat(result.getId()).isEqualTo(7); assertThat(result.getCategory()).isEqualTo(RepairCategory.PFRN);
        assertThat(result.getStatus()).isEqualTo(RepairStatus.PENDING); assertThat(result.getScRefNo()).isEqualTo("SC-42");
        assertThat(source.getRepairStatus()).isEqualTo("RP"); verify(services).save(source);
    }

    @Test void rejectsNonNrAndDuplicateRepair() {
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source("RP")));
        assertStatus(HttpStatus.CONFLICT, () -> subject.createFromService(42L, "OB", "admin", true));
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source("NR")));
        when(repairs.existsByServiceIdAndStatus(42, RepairStatus.PENDING)).thenReturn(true);
        assertStatus(HttpStatus.CONFLICT, () -> subject.createFromService(42L, "PFRN", "admin", true));
        verify(repairs, never()).saveAndFlush(any());
    }

    @Test void concurrentUniqueConstraintRaceReturnsConflict() {
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source("NR")));
        when(repairs.saveAndFlush(any())).thenThrow(new DataIntegrityViolationException("uq_repair_master_pending_service"));

        assertStatus(HttpStatus.CONFLICT,
                () -> subject.createFromService(42L, "PFRN", "admin", true));
    }

    @Test void categoryMustMatchTheServiceQueue() {
        ServiceMaster pfrn = source("NR");
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(pfrn));
        assertStatus(HttpStatus.CONFLICT, () -> subject.createFromService(42L, "OB", "admin", true));
        assertStatus(HttpStatus.CONFLICT, () -> subject.createFromService(42L, "UR", "admin", true));

        pfrn.setUnitStatus("OW");
        when(repairs.saveAndFlush(any())).thenAnswer(a -> a.getArgument(0));
        assertThat(subject.createFromService(42L, "OB", "admin", true).getCategory()).isEqualTo(RepairCategory.OB);

        ServiceMaster underRepair = source("NR");
        underRepair.setShipDtFrmSerCntr(LocalDate.now());
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(underRepair));
        assertThat(subject.createFromService(42L, "UR", "admin", true).getCategory()).isEqualTo(RepairCategory.UR);
    }

    @SuppressWarnings("unchecked")
    @Test void supportsEveryCategoryAndStatusQueueCombination() {
        when(repairs.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());
        Pageable page = PageRequest.of(0, 10);

        for (RepairCategory category : RepairCategory.values()) {
            for (RepairStatus status : RepairStatus.values()) {
                assertThat(subject.findAll(category.name(), status.databaseValue(), null, null,
                        null, null, null, page)).isEmpty();
            }
        }

        verify(repairs, times(RepairCategory.values().length * RepairStatus.values().length))
                .findAll(any(Specification.class), eq(page));
    }

    @Test void engineerScopeAllowsAssignmentOnlyWhenDivisionDiffers() {
        Employee employee = new Employee(); employee.setEmpId(9L); employee.setDivision("WEST");
        when(employees.findByUsernameIgnoreCase("eng")).thenReturn(Optional.of(employee));
        ServiceMaster source = source("NR"); source.setDivision("SOUTH"); source.setScEngineerId(8); source.setRaEngineerId(10);
        when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source));
        assertStatus(HttpStatus.FORBIDDEN, () -> subject.createFromService(42L, "PFRN", "eng", false));
        source.setRaEngineerId(9); when(repairs.saveAndFlush(any())).thenAnswer(a -> a.getArgument(0));
        assertThat(subject.createFromService(42L, "PFRN", "eng", false).getServiceId()).isEqualTo(42);
    }

    @Test void pendingUpdatePreservesCompletionOnlyServiceFields() {
        RepairMaster repair = pending(); ServiceMaster source = source("RP");
        source.setComponentsUsedForRepair("existing"); source.setReportType("old");
        when(repairs.findByIdForUpdate(3)).thenReturn(Optional.of(repair)); when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source));
        when(repairs.save(any())).thenAnswer(a -> a.getArgument(0));
        RepairDTO result = subject.update(3, update("pending"));
        assertThat(result.getFinishDate()).isNull(); assertThat(source.getRepairStatus()).isEqualTo("RP");
        assertThat(source.getComponentsUsedForRepair()).isEqualTo("existing"); assertThat(source.getReportType()).isEqualTo("old");
    }

    @Test void completionSynchronizesService() {
        RepairMaster repair = pending(); repair.setEntryDate(LocalDate.now().minusDays(4)); ServiceMaster source = source("RP");
        when(repairs.findByIdForUpdate(3)).thenReturn(Optional.of(repair)); when(services.findByIdForUpdate(42L)).thenReturn(Optional.of(source));
        when(repairs.save(any())).thenAnswer(a -> a.getArgument(0));
        RepairDTO result = subject.update(3, update("completed"));
        assertThat(result.getFinishDate()).isEqualTo(LocalDate.now()); assertThat(result.getDays()).isEqualTo(4);
        assertThat(source.getRepairStatus()).isEqualTo("RC"); assertThat(source.getComponentsUsedForRepair()).isEqualTo("C1");
        assertThat(source.getReportType()).isEqualTo("repairlist");
    }

    @Test void completedIsImmutableAndInvalidEnumsAreBadRequests() {
        RepairMaster repair = pending(); repair.setStatus(RepairStatus.COMPLETED); when(repairs.findByIdForUpdate(3)).thenReturn(Optional.of(repair));
        assertStatus(HttpStatus.CONFLICT, () -> subject.update(3, update("pending")));
        assertStatus(HttpStatus.BAD_REQUEST, () -> subject.createFromService(42L, "bad", "admin", true));
        repair.setStatus(RepairStatus.PENDING); assertStatus(HttpStatus.BAD_REQUEST, () -> subject.update(3, update("reopened")));
    }

    private static ServiceMaster source(String status) { ServiceMaster s = new ServiceMaster(); s.setId(42L); s.setDivision("WEST");
        s.setScRefNo("SC-42"); s.setDefGirNo("GIR-42"); s.setProductModel("Cardio X"); s.setDefModBrdName("Board");
        s.setUnitStatus("Received"); s.setRepairStatus(status); return s; }
    private static RepairMaster pending() { RepairMaster r = new RepairMaster(); r.setId(3); r.setServiceId(42);
        r.setEntryDate(LocalDate.now().minusDays(2)); r.setStatus(RepairStatus.PENDING); r.setCategory(RepairCategory.PFRN); return r; }
    private static RepairUpdateRequest update(String status) { RepairUpdateRequest r = new RepairUpdateRequest();
        r.setRepairedBy("Tech"); r.setComponentsUsed("C1"); r.setTechnicalRemarks("Fixed"); r.setFinalRemarks("OK"); r.setStatus(status); return r; }
    private static void assertStatus(HttpStatus status, Runnable action) { assertThatThrownBy(action::run)
        .isInstanceOf(ResponseStatusException.class).extracting(e -> ((ResponseStatusException)e).getStatusCode()).isEqualTo(status); }
}
