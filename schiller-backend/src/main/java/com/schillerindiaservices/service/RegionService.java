package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.RegionDTO;
import com.schillerindiaservices.entity.Region;
import com.schillerindiaservices.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RegionDTO convertToDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setRegionId(region.getId());
        dto.setRegionName(region.getRegionName());
        return dto;
    }
}
