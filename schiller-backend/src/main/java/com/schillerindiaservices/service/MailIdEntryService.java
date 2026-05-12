package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.MailIdEntryDTO;
import com.schillerindiaservices.entity.MailIdEntry;
import com.schillerindiaservices.repository.MailIdEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailIdEntryService {

    @Autowired
    private MailIdEntryRepository repository;

    public Page<MailIdEntryDTO> getMailIdEntries(String search, Pageable pageable) {
        Page<MailIdEntry> page;
        if (search != null && !search.isEmpty()) {
            page = repository.findByMailIdContainingIgnoreCaseOrReportTypeContainingIgnoreCaseOrTemp1ContainingIgnoreCase(search, search, search, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        return page.map(this::convertToDTO);
    }

    public MailIdEntryDTO getMailIdEntryById(Integer id) {
        MailIdEntry entity = repository.findById(id).orElseThrow(() -> new RuntimeException("MailIdEntry not found"));
        return convertToDTO(entity);
    }

    @Transactional
    public MailIdEntryDTO createMailIdEntry(MailIdEntryDTO dto) {
        MailIdEntry entity = new MailIdEntry();
        entity.setMailId(dto.getMailId());
        entity.setReportType(dto.getReportType());
        entity.setTemp1(dto.getDivision());
        entity.setTemp2(dto.getTemp2());

        MailIdEntry saved = repository.save(entity);
        return convertToDTO(saved);
    }

    @Transactional
    public MailIdEntryDTO updateMailIdEntry(Integer id, MailIdEntryDTO dto) {
        MailIdEntry entity = repository.findById(id).orElseThrow(() -> new RuntimeException("MailIdEntry not found"));
        
        if(dto.getMailId() != null) entity.setMailId(dto.getMailId());
        if(dto.getReportType() != null) entity.setReportType(dto.getReportType());
        if(dto.getDivision() != null) entity.setTemp1(dto.getDivision());
        if(dto.getTemp2() != null) entity.setTemp2(dto.getTemp2());

        MailIdEntry saved = repository.save(entity);
        return convertToDTO(saved);
    }

    @Transactional
    public void deleteMailIdEntry(Integer id) {
        repository.deleteById(id);
    }

    private MailIdEntryDTO convertToDTO(MailIdEntry entity) {
        MailIdEntryDTO dto = new MailIdEntryDTO();
        dto.setMailIdEntryId(entity.getMailIdEntryId());
        dto.setMailId(entity.getMailId());
        dto.setReportType(entity.getReportType());
        dto.setDivision(entity.getTemp1());
        dto.setTemp2(entity.getTemp2());
        return dto;
    }
}
