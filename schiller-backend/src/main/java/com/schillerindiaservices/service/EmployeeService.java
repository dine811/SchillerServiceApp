package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.EmployeeDTO;
import com.schillerindiaservices.dto.EmployeeRequestDTO;
import com.schillerindiaservices.entity.Employee;
import com.schillerindiaservices.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for Employee (emploeemaster) module.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // ─── Queries ────────────────────────────────────────────────────────────────

    public Page<EmployeeDTO> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(this::toDTO);
    }

    public EmployeeDTO findById(Long id) {
        return toDTO(getOrThrow(id));
    }

    public EmployeeDTO findByUsername(String username) {
        return employeeRepository.findByUsername(username)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Employee not found with username: " + username));
    }

    public Page<EmployeeDTO> findByRole(String role, Pageable pageable) {
        return employeeRepository.findByRole(role, pageable).map(this::toDTO);
    }

    public Page<EmployeeDTO> findByDivision(String division, Pageable pageable) {
        return employeeRepository.findByDivision(division, pageable).map(this::toDTO);
    }

    public List<EmployeeDTO> findAllActive() {
        return employeeRepository.findByActiveTrue()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Page<EmployeeDTO> searchEmployees(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return findAll(pageable);
        }
        return employeeRepository.searchEmployees(keyword, pageable).map(this::toDTO);
    }

    public List<EmployeeDTO> findByRegionId(Long regionId) {
        return employeeRepository.findByRegionId(regionId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ─── Mutations ───────────────────────────────────────────────────────────────

    @Transactional
    public EmployeeDTO create(EmployeeRequestDTO request) {
        if (employeeRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }
        Employee emp = fromRequest(new Employee(), request);
        return toDTO(employeeRepository.save(emp));
    }

    @Transactional
    public EmployeeDTO update(Long id, EmployeeRequestDTO request) {
        Employee emp = getOrThrow(id);

        // If username is changing, check it's not already taken
        if (!emp.getUsername().equals(request.getUsername())
                && employeeRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        fromRequest(emp, request);
        return toDTO(employeeRepository.save(emp));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id); // ensure exists
        employeeRepository.deleteById(id);
    }

    @Transactional
    public EmployeeDTO toggleActive(Long id) {
        Employee emp = getOrThrow(id);
        emp.setActive(!Boolean.TRUE.equals(emp.getActive()));
        return toDTO(employeeRepository.save(emp));
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private Employee getOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    private Employee fromRequest(Employee emp, EmployeeRequestDTO req) {
        emp.setEmpName(req.getEmpName());
        emp.setUsername(req.getUsername());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            emp.setPassword(req.getPassword());
        }
        emp.setRole(req.getRole());
        emp.setDivision(req.getDivision());
        emp.setEmail(req.getEmail());
        emp.setMobile(req.getMobile());
        emp.setAddress(req.getAddress());
        emp.setDepartment(req.getDepartment());
        emp.setBranchId(req.getBranchId());
        if (req.getActive() != null) {
            emp.setActive(req.getActive());
        }
        return emp;
    }

    public EmployeeDTO toDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpId(emp.getEmpId());
        dto.setEmpName(emp.getEmpName());
        dto.setUsername(emp.getUsername());
        dto.setRole(emp.getRole());
        dto.setDivision(emp.getDivision());
        dto.setEmail(emp.getEmail());
        dto.setMobile(emp.getMobile());
        dto.setAddress(emp.getAddress());
        dto.setDepartment(emp.getDepartment());
        dto.setBranchId(emp.getBranchId());
        dto.setActive(emp.getActive());
        return dto;
    }
}
