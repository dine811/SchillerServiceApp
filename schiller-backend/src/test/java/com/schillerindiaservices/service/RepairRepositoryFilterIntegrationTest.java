package com.schillerindiaservices.service;

import com.schillerindiaservices.entity.RepairCategory;
import com.schillerindiaservices.entity.RepairMaster;
import com.schillerindiaservices.entity.RepairStatus;
import com.schillerindiaservices.repository.RepairRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:repair;MODE=PostgreSQL;NON_KEYWORDS=CURRENT_DATE;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({RepairService.class, com.schillerindiaservices.mapper.RepairMapper.class})
class RepairRepositoryFilterIntegrationTest {
    @Autowired RepairRepository repairs;
    @Autowired RepairService service;

    @BeforeEach
    void seedEveryQueue() {
        repairs.deleteAll();
        int offset = 0;
        for (RepairCategory category : RepairCategory.values()) {
            save(category, RepairStatus.PENDING, LocalDate.of(2026, 1, 1).plusDays(offset++),
                    "WEST-" + category, "SC-P-" + category, "GIR-P-" + category);
            save(category, RepairStatus.COMPLETED, LocalDate.of(2026, 1, 1).plusDays(offset++),
                    "EAST-" + category, "SC-C-" + category, "GIR-C-" + category);
        }
    }

    @Test
    void filtersEveryCategoryStatusCombination() {
        for (RepairCategory category : RepairCategory.values()) {
            for (RepairStatus status : RepairStatus.values()) {
                assertThat(service.findAll(category.name(), status.databaseValue(), null, null,
                        null, null, null, PageRequest.of(0, 10)).getContent())
                        .singleElement()
                        .satisfies(row -> {
                            assertThat(row.getCategory()).isEqualTo(category);
                            assertThat(row.getStatus()).isEqualTo(status);
                        });
            }
        }
    }

    @Test
    void dateBoundsSearchDivisionAndScReferenceAreInclusive() {
        assertThat(service.findAll(null, null, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 2),
                null, null, null, PageRequest.of(0, 10)).getTotalElements()).isEqualTo(2);
        assertThat(service.findAll(null, null, null, null, "west", null, null,
                PageRequest.of(0, 10)).getTotalElements()).isEqualTo(3);
        assertThat(service.findAll(null, null, null, null, null, "c-ob", null,
                PageRequest.of(0, 10)).getContent()).singleElement()
                .satisfies(row -> assertThat(row.getScRefNo()).isEqualTo("SC-C-OB"));
        assertThat(service.findAll(null, null, null, null, null, null, "gir-p-ur",
                PageRequest.of(0, 10)).getContent()).singleElement()
                .satisfies(row -> assertThat(row.getDefGirNo()).isEqualTo("GIR-P-UR"));
    }

    @Test
    void paginationAndSecondaryIdSortAreDeterministic() {
        LocalDate sameDate = LocalDate.of(2026, 2, 1);
        RepairMaster first = save(RepairCategory.PFRN, RepairStatus.PENDING, sameDate,
                "SORT", "SC-SORT-1", "GIR-SORT-1");
        RepairMaster second = save(RepairCategory.PFRN, RepairStatus.PENDING, sameDate,
                "SORT", "SC-SORT-2", "GIR-SORT-2");

        var result = service.findAll("PFRN", "pending", sameDate, sameDate, null, null, null,
                PageRequest.of(0, 1, Sort.by(Sort.Order.asc("entryDate"), Sort.Order.asc("id"))));
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).singleElement()
                .satisfies(row -> assertThat(row.getId()).isEqualTo(first.getId()));
        assertThat(second.getId()).isGreaterThan(first.getId());
    }

    private RepairMaster save(RepairCategory category, RepairStatus status, LocalDate entryDate,
                              String division, String scRefNo, String gir) {
        RepairMaster repair = new RepairMaster();
        repair.setCategory(category);
        repair.setStatus(status);
        repair.setEntryDate(entryDate);
        repair.setDivision(division);
        repair.setScRefNo(scRefNo);
        repair.setDefGirNo(gir);
        repair.setNoOfDays(status == RepairStatus.COMPLETED ? 4 : 0);
        return repairs.saveAndFlush(repair);
    }
}
