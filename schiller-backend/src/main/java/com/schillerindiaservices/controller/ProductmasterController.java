package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.ProductmasterDTO;
import com.schillerindiaservices.dto.ProductmasterRequestDTO;
import com.schillerindiaservices.service.ProductmasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductmasterController {

    @Autowired
    private ProductmasterService productmasterService;

    @GetMapping
    public ResponseEntity<List<ProductmasterDTO>> getAllProducts() {
        return ResponseEntity.ok(productmasterService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductmasterDTO> getProduct(@PathVariable Integer id) {
        ProductmasterDTO dto = productmasterService.getProductById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductmasterDTO> createProduct(@RequestBody ProductmasterRequestDTO requestDTO) {
        ProductmasterDTO created = productmasterService.createProduct(requestDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductmasterDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductmasterRequestDTO requestDTO) {
        ProductmasterDTO updated = productmasterService.updateProduct(id, requestDTO);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productmasterService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
