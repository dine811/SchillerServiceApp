package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.ModelDTO;
import com.schillerindiaservices.entity.Model;
import com.schillerindiaservices.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelService {

    private final ModelRepository modelRepository;

    public List<ModelDTO> findAll() {
        return modelRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ModelDTO> findBySupName(String supName) {
        return modelRepository.findBySupName(supName).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ModelDTO toDTO(Model model) {
        ModelDTO dto = new ModelDTO();
        dto.setModelId(model.getModelId());
        dto.setModelName(model.getModelName());
        dto.setModelDesc(model.getModelDesc());
        dto.setSupName(model.getSupName());
        return dto;
    }
}
