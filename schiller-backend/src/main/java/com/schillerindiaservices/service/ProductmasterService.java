package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.ModelDTO;
import com.schillerindiaservices.dto.ProductmasterDTO;
import com.schillerindiaservices.dto.ProductmasterRequestDTO;
import com.schillerindiaservices.entity.Model;
import com.schillerindiaservices.entity.Productmaster;
import com.schillerindiaservices.repository.ProductmasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductmasterService {

    @Autowired
    private ProductmasterRepository productmasterRepository;

    public List<ProductmasterDTO> getAllProducts() {
        return productmasterRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductmasterDTO getProductById(Integer id) {
        return productmasterRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Transactional
    public ProductmasterDTO createProduct(ProductmasterRequestDTO requestDTO) {
        Productmaster productmaster = new Productmaster();
        productmaster.setDivision(requestDTO.getDivision());
        productmaster.setDivisionName(requestDTO.getDivisionName());

        List<Model> models = new ArrayList<>();
        if (requestDTO.getModels() != null) {
            for (ModelDTO mDto : requestDTO.getModels()) {
                Model model = new Model();
                model.setModelName(mDto.getModelName());
                model.setModelDesc(mDto.getModelDesc());
                model.setSupName(mDto.getSupName());
                model.setProductmaster(productmaster);
                models.add(model);
            }
        }
        productmaster.setModels(models);

        Productmaster saved = productmasterRepository.save(productmaster);
        return toDTO(saved);
    }

    @Transactional
    public ProductmasterDTO updateProduct(Integer id, ProductmasterRequestDTO requestDTO) {
        Productmaster existing = productmasterRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setDivision(requestDTO.getDivision());
        existing.setDivisionName(requestDTO.getDivisionName());

        // Clear and rebuild models to rely on orphanRemoval
        existing.getModels().clear();

        if (requestDTO.getModels() != null) {
            for (ModelDTO mDto : requestDTO.getModels()) {
                Model model = new Model();
                // If modelId is provided, we could fetch it to update, but
                // since we clear and rebuild, we insert as new. Wait, if we keep modelId, 
                // we should reuse the existing entity to avoid ID churn.
                model.setModelId(mDto.getModelId()); 
                model.setModelName(mDto.getModelName());
                model.setModelDesc(mDto.getModelDesc());
                model.setSupName(mDto.getSupName());
                model.setProductmaster(existing);
                existing.getModels().add(model);
            }
        }

        Productmaster updated = productmasterRepository.save(existing);
        return toDTO(updated);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        productmasterRepository.deleteById(id);
    }

    private ProductmasterDTO toDTO(Productmaster entity) {
        ProductmasterDTO dto = new ProductmasterDTO();
        dto.setProductId(entity.getProductId());
        dto.setDivision(entity.getDivision());
        dto.setDivisionName(entity.getDivisionName());

        if (entity.getModels() != null) {
            List<ModelDTO> modelDTOs = entity.getModels().stream().map(m -> {
                ModelDTO md = new ModelDTO();
                md.setModelId(m.getModelId());
                md.setModelName(m.getModelName());
                md.setModelDesc(m.getModelDesc());
                md.setSupName(m.getSupName());
                return md;
            }).collect(Collectors.toList());
            dto.setModels(modelDTOs);
        } else {
            dto.setModels(new ArrayList<>());
        }
        return dto;
    }
}
