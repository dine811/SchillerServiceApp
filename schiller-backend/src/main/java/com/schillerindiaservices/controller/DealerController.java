package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.DealerDTO;
import com.schillerindiaservices.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @GetMapping
    public ResponseEntity<List<DealerDTO>> getAllDealers() {
        return ResponseEntity.ok(dealerService.getAllDealers());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<DealerDTO>> getByRegion(@PathVariable Long regionId) {
        return ResponseEntity.ok(dealerService.findByRegionId(regionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerDTO> getDealerById(@PathVariable Long id) {
        return ResponseEntity.ok(dealerService.getDealerById(id));
    }

    @PostMapping
    public ResponseEntity<DealerDTO> createDealer(@RequestBody DealerDTO dealerDTO) {
        return new ResponseEntity<>(dealerService.createDealer(dealerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealerDTO> updateDealer(@PathVariable Long id, @RequestBody DealerDTO dealerDTO) {
        return ResponseEntity.ok(dealerService.updateDealer(id, dealerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id) {
        dealerService.deleteDealer(id);
        return ResponseEntity.noContent().build();
    }
}
