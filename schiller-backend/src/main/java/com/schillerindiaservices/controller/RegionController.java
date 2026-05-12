package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.RegionDTO;
import com.schillerindiaservices.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<RegionDTO> getAllRegions() {
        return regionService.getAllRegions();
    }
}
