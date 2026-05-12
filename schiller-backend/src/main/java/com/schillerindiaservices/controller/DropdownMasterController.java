package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.DropdownMasterDTO;
import com.schillerindiaservices.service.DropdownMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dropdowns")
@RequiredArgsConstructor
public class DropdownMasterController {

    private final DropdownMasterService dropdownMasterService;

    @GetMapping("/group/{group}")
    public List<DropdownMasterDTO> getByGroup(@PathVariable Integer group) {
        return dropdownMasterService.findByGroup(group);
    }
}
