package com.schillerindiaservices.controller;

import com.schillerindiaservices.config.SecurityConfig;
import com.schillerindiaservices.dto.RepairDTO;
import com.schillerindiaservices.entity.RepairCategory;
import com.schillerindiaservices.entity.RepairStatus;
import com.schillerindiaservices.security.JwtAuthenticationFilter;
import com.schillerindiaservices.security.JwtService;
import com.schillerindiaservices.service.RepairExportService;
import com.schillerindiaservices.service.RepairService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepairController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
class RepairControllerSecurityTest {
    @Autowired MockMvc mvc;
    @MockitoBean RepairService repairService;
    @MockitoBean RepairExportService repairExportService;
    @MockitoBean JwtService jwtService;

    @Test
    @WithMockUser(roles = "ENGINEER")
    void engineerCannotReadOrUpdateRepairQueues() throws Exception {
        mvc.perform(get("/api/repairs")).andExpect(status().isForbidden());
        mvc.perform(put("/api/repairs/1")
                        .contentType("application/json")
                        .content("{\"repairedBy\":\"Tech\",\"status\":\"PENDING\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "REPAIR_TEAM")
    void repairTeamCanReadAndUpdateButCannotInitiate() throws Exception {
        when(repairService.findAll(any(), any(), any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(Page.empty());
        RepairDTO updated = new RepairDTO();
        updated.setId(1);
        updated.setStatus(RepairStatus.PENDING);
        when(repairService.update(eq(1), any())).thenReturn(updated);

        mvc.perform(get("/api/repairs")).andExpect(status().isOk());
        mvc.perform(put("/api/repairs/1")
                        .contentType("application/json")
                        .content("{\"repairedBy\":\"Tech\",\"status\":\"PENDING\"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/repairs/from-service/42").param("category", "PFRN"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "engineer", roles = "ENGINEER")
    void engineerCanInitiateAndFiltersPaginationSearchAreForwarded() throws Exception {
        RepairDTO created = new RepairDTO();
        created.setId(7);
        created.setCategory(RepairCategory.PFRN);
        created.setStatus(RepairStatus.PENDING);
        when(repairService.createFromService(42L, "PFRN", "engineer", false)).thenReturn(created);

        mvc.perform(post("/api/repairs/from-service/42").param("category", "PFRN"))
                .andExpect(status().isCreated());
        verify(repairService).createFromService(42L, "PFRN", "engineer", false);
    }

    @Test
    @WithMockUser(roles = "REPAIR_TEAM")
    void listValidatesPagingSortingAndForwardsEveryFilter() throws Exception {
        when(repairService.findAll(any(), any(), any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(Page.empty());

        mvc.perform(get("/api/repairs")
                        .param("category", "OB")
                        .param("status", "completed")
                        .param("from", "2026-01-01")
                        .param("to", "2026-01-31")
                        .param("division", "WEST")
                        .param("scRefNo", "SC-1")
                        .param("search", "GIR")
                        .param("page", "2")
                        .param("size", "25")
                        .param("sort", "entryDate,asc"))
                .andExpect(status().isOk());
        verify(repairService).findAll(eq("OB"), eq("completed"),
                eq(LocalDate.of(2026, 1, 1)), eq(LocalDate.of(2026, 1, 31)),
                eq("WEST"), eq("SC-1"), eq("GIR"), argThat(pageable ->
                        pageable.getPageNumber() == 2 && pageable.getPageSize() == 25
                                && pageable.getSort().getOrderFor("entryDate") != null));

        mvc.perform(get("/api/repairs").param("size", "101"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/repairs").param("sort", "notAField,asc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "REPAIR_TEAM")
    void serviceConflictIsNotMaskedBySecurityErrorDispatch() throws Exception {
        when(repairService.update(eq(1), any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Completed repairs are immutable"));

        mvc.perform(put("/api/repairs/1")
                        .contentType("application/json")
                        .content("{\"repairedBy\":\"Tech\",\"status\":\"PENDING\"}"))
                .andExpect(status().isConflict());
    }
}
