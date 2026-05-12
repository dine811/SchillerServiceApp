package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.DealerDTO;
import com.schillerindiaservices.entity.Dealer;
import com.schillerindiaservices.repository.DealerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    public List<DealerDTO> getAllDealers() {
        return dealerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<DealerDTO> findByRegionId(Long regionId) {
        return dealerRepository.findByRegionId(regionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DealerDTO getDealerById(Long id) {
        Dealer dealer = dealerRepository.findById(id).orElseThrow(() -> new RuntimeException("Dealer not found"));
        return convertToDTO(dealer);
    }

    @Transactional
    public DealerDTO createDealer(DealerDTO dealerDTO) {
        Dealer dealer = new Dealer();
        BeanUtils.copyProperties(dealerDTO, dealer);
        // ID should be null for create
        dealer.setDealerId(null);
        dealer = dealerRepository.save(dealer);
        return convertToDTO(dealer);
    }

    @Transactional
    public DealerDTO updateDealer(Long id, DealerDTO dealerDTO) {
        Dealer dealer = dealerRepository.findById(id).orElseThrow(() -> new RuntimeException("Dealer not found"));
        
        dealer.setDealerName(dealerDTO.getDealerName());
        dealer.setDealerAddress(dealerDTO.getDealerAddress());
        dealer.setDealerMail(dealerDTO.getDealerMail());
        dealer.setDealerPhone(dealerDTO.getDealerPhone());
        dealer.setRegionId(dealerDTO.getRegionId());
        
        dealer = dealerRepository.save(dealer);
        return convertToDTO(dealer);
    }

    @Transactional
    public void deleteDealer(Long id) {
        if (!dealerRepository.existsById(id)) {
            throw new RuntimeException("Dealer not found");
        }
        dealerRepository.deleteById(id);
    }

    private DealerDTO convertToDTO(Dealer dealer) {
        DealerDTO dto = new DealerDTO();
        BeanUtils.copyProperties(dealer, dto);
        return dto;
    }
}
