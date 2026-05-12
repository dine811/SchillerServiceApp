package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByUsernameIgnoreCase(String username);

    Page<Employee> findByRole(String role, Pageable pageable);

    Page<Employee> findByDivision(String division, Pageable pageable);

    Page<Employee> findByActive(Boolean active, Pageable pageable);

    List<Employee> findByActiveTrue();

    boolean existsByUsername(String username);

    @org.springframework.data.jpa.repository.Query("SELECT e FROM Employee e WHERE LOWER(e.empName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Employee> searchEmployees(@org.springframework.data.repository.query.Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT e FROM Employee e INNER JOIN Branch b ON b.id = e.branchId WHERE b.regionId = :regionId AND e.active = true")
    List<Employee> findByRegionId(@Param("regionId") Long regionId);
}
