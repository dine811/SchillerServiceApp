package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.PartnumberEntryCreateRequest;
import com.schillerindiaservices.entity.PartnumberEntry;
import com.schillerindiaservices.service.PartnumberEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partnumber-entry")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class PartnumberEntryController {

    private final PartnumberEntryService partnumberEntryService;

    @PostMapping
    public ResponseEntity<PartnumberEntry> create(@Valid @RequestBody PartnumberEntryCreateRequest body) {
        PartnumberEntry saved = partnumberEntryService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
