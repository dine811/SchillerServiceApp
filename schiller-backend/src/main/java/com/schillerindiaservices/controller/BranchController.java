package com.schillerindiaservices.controller;

import com.schillerindiaservices.entity.Branch;
import com.schillerindiaservices.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchRepository branchRepository;

    @GetMapping
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @GetMapping("/region/{regionId}")
    public List<Branch> getByRegion(@PathVariable Long regionId) {
        return branchRepository.findByRegionId(regionId);
    }
}
