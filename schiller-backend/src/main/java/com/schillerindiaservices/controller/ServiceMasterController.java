package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.ServiceMasterDTO;
import com.schillerindiaservices.service.ServiceMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.schillerindiaservices.security.SecurityRoleUtils;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceMasterController {

    private final ServiceMasterService serviceMasterService;

    /**
     * Pending FRN list (legacy pending_FRN.jsp): ship from SC not set; unit status not OW/LAMC.
     */
    @GetMapping("/pending-frn")
    public ResponseEntity<Page<ServiceMasterDTO>> getPendingFrn(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "scRefNo,desc") String sort,
            Authentication authentication
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "scRefNo" : sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        return ResponseEntity.ok(serviceMasterService.findPendingFrnForUser(
                authentication.getName(), allDivisions, scRef, search, pageable));
    }

    /**
     * Under-repair list (legacy under_repair.jsp): shipped from SC, repair date not recorded yet.
     */
    @GetMapping("/under-repair")
    public ResponseEntity<Page<ServiceMasterDTO>> getUnderRepair(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "scRefNo,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "scRefNo" : sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return ResponseEntity.ok(serviceMasterService.findUnderRepair(scRef, search, pageable));
    }

    /**
     * OB Pending list (legacy ob_pending.jsp): ship from SC not set; unit status OW or LAMC.
     */
    @GetMapping("/ob-pending")
    public ResponseEntity<Page<ServiceMasterDTO>> getObPending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "scRefNo,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "scRefNo" : sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return ResponseEntity.ok(serviceMasterService.findObPending(scRef, search, pageable));
    }

    /**
     * SC Closed Product (legacy closedproduct.jsp): repair complete at SC, awaiting commercial ship date.
     */
    @GetMapping("/sc-closed")
    public ResponseEntity<Page<ServiceMasterDTO>> getScClosed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "scRefNo,desc") String sort,
            Authentication authentication
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "scRefNo" : sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        return ResponseEntity.ok(serviceMasterService.findScClosedProductForUser(
                authentication.getName(), allDivisions, scRef, search, pageable));
    }

    /**
     * Closed Product (legacy New_ClosedProduct.jsp): commercial ship date recorded (fully closed path).
     */
    @GetMapping("/new-closed")
    public ResponseEntity<Page<ServiceMasterDTO>> getNewClosed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "scRefNo,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "scRefNo" : sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return ResponseEntity.ok(serviceMasterService.findNewClosedProduct(scRef, search, pageable));
    }

    /**
     * Scrap list (legacy ScarpList.jsp): SCRAPPED work type; optional division, entry month/year, SC ref / FRN–Def GIR search.
     */
    @GetMapping("/scrap-list")
    public ResponseEntity<Page<ServiceMasterDTO>> getScrapList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "scRefNo,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "scRefNo" : sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return ResponseEntity.ok(serviceMasterService.findScrapList(division, month, year, scRef, search, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceMasterDTO>> getAllServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort,
            Authentication authentication
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        String username = authentication.getName();

        Page<ServiceMasterDTO> services = (search != null && !search.isBlank())
                ? serviceMasterService.searchForUser(username, allDivisions, search, pageable)
                : serviceMasterService.findAllForUser(username, allDivisions, pageable);
        return ResponseEntity.ok(services);
    }

    /** Numeric id only — avoids queue paths like /pending-frn, /scrap-list being captured as path variables. */
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ServiceMasterDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceMasterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceMasterDTO> createService(@RequestBody ServiceMasterDTO serviceDTO) {
        return ResponseEntity.ok(serviceMasterService.save(serviceDTO));
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<ServiceMasterDTO> updateService(@PathVariable Long id, @RequestBody ServiceMasterDTO serviceDTO) {
        return ResponseEntity.ok(serviceMasterService.update(id, serviceDTO));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceMasterService.delete(id);
        return ResponseEntity.ok().build();
    }
}
