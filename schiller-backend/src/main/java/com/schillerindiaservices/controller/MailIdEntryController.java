package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.MailIdEntryDTO;
import com.schillerindiaservices.service.MailIdEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/escalations")
public class MailIdEntryController {

    @Autowired
    private MailIdEntryService service;

    @GetMapping
    public ResponseEntity<Page<MailIdEntryDTO>> getEscalations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("mailIdEntryId").descending());
        return ResponseEntity.ok(service.getMailIdEntries(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MailIdEntryDTO> getEscalationById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getMailIdEntryById(id));
    }

    @PostMapping
    public ResponseEntity<MailIdEntryDTO> createEscalation(@RequestBody MailIdEntryDTO dto) {
        return ResponseEntity.ok(service.createMailIdEntry(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MailIdEntryDTO> updateEscalation(@PathVariable Integer id, @RequestBody MailIdEntryDTO dto) {
        return ResponseEntity.ok(service.updateMailIdEntry(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscalation(@PathVariable Integer id) {
        service.deleteMailIdEntry(id);
        return ResponseEntity.noContent().build();
    }
}
