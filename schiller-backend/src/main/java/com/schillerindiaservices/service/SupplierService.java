package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.SupplierDTO;
import com.schillerindiaservices.entity.Supplier;
import com.schillerindiaservices.repository.ModelRepository;
import com.schillerindiaservices.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierService {

    private final ModelRepository modelRepository;

    public List<SupplierDTO> findAll() {
        return modelRepository.findDistinctSupNames().stream()
                .map(name -> {
                    SupplierDTO dto = new SupplierDTO();
                    dto.setSupplierName(name);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
