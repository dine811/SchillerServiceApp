package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.ModelDTO;
import com.schillerindiaservices.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @GetMapping
    public ResponseEntity<List<ModelDTO>> getAll() {
        return ResponseEntity.ok(modelService.findAll());
    }

    @GetMapping("/supplier/{supName}")
    public ResponseEntity<List<ModelDTO>> getBySupplier(@PathVariable String supName) {
        return ResponseEntity.ok(modelService.findBySupName(supName));
    }
}
