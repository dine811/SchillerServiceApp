package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.DropdownMasterDTO;
import com.schillerindiaservices.repository.DropdownMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DropdownMasterService {

    private final DropdownMasterRepository dropdownMasterRepository;

    public List<DropdownMasterDTO> findByGroup(Integer group) {
        return dropdownMasterRepository.findByDdName(String.valueOf(group)).stream()
                .map(dm -> {
                    DropdownMasterDTO dto = new DropdownMasterDTO();
                    dto.setId(dm.getId());
                    dto.setDdGroup(group);
                    dto.setDdValue(dm.getDdValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
