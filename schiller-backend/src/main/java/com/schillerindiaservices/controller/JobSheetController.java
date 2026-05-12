package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.JobSheetDTO;
import com.schillerindiaservices.service.JobSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobsheets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JobSheetController {

    private final JobSheetService jobSheetService;

    @GetMapping("/by-service/{serviceId}")
    public ResponseEntity<List<JobSheetDTO>> listByService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(jobSheetService.findByServiceId(serviceId));
    }

    @GetMapping("/by-service/{serviceId}/latest")
    public ResponseEntity<JobSheetDTO> latestByService(@PathVariable Long serviceId) {
        JobSheetDTO dto = jobSheetService.findLatestByServiceId(serviceId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<JobSheetDTO> create(@RequestBody JobSheetDTO dto) {
        return ResponseEntity.ok(jobSheetService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSheetDTO> update(@PathVariable Integer id, @RequestBody JobSheetDTO dto) {
        return ResponseEntity.ok(jobSheetService.update(id, dto));
    }
}
