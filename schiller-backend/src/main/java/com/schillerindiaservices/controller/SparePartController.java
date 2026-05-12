package com.schillerindiaservices.controller;

import com.schillerindiaservices.model.SparePart;
import com.schillerindiaservices.repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spare-parts")
@CrossOrigin(origins = "*")
public class SparePartController {

    @Autowired
    private SparePartRepository sparePartRepository;

    @GetMapping("/{partNumber}")
    public ResponseEntity<SparePart> getSparePartByNumber(
            @PathVariable String partNumber,
            @RequestParam(required = false) String division) {
        
        return sparePartRepository.findByPartNumberAndDivision(partNumber, division)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
