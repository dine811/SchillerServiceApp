package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.PartnumberEntryCreateRequest;
import com.schillerindiaservices.entity.PartnumberEntry;
import com.schillerindiaservices.repository.PartnumberEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnumberEntryService {

    private final PartnumberEntryRepository partnumberEntryRepository;

    @Transactional
    public PartnumberEntry create(PartnumberEntryCreateRequest req) {
        PartnumberEntry e = new PartnumberEntry();
        e.setPartNumber(req.getPartNumber().trim());
        e.setBrdName(req.getBrdName().trim());
        e.setCompatibleModels(req.getCompatibleModels().trim());
        e.setCost(req.getCost());
        return partnumberEntryRepository.save(e);
    }
}
