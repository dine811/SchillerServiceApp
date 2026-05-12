package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.JobSheetDTO;
import com.schillerindiaservices.entity.JobSheet;
import com.schillerindiaservices.entity.ServiceMaster;
import com.schillerindiaservices.repository.JobSheetRepository;
import com.schillerindiaservices.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSheetService {

    private final JobSheetRepository jobSheetRepository;
    private final ServiceRepository serviceRepository;

    public List<JobSheetDTO> findByServiceId(Long serId) {
        return jobSheetRepository.findBySerIdOrderByIdDesc(serId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Latest row for a service (legacy form used the last matching row when multiple exist).
     */
    public JobSheetDTO findLatestByServiceId(Long serId) {
        List<JobSheet> list = jobSheetRepository.findBySerIdOrderByIdDesc(serId);
        if (list.isEmpty()) {
            return null;
        }
        return toDto(list.get(0));
    }

    @Transactional
    public JobSheetDTO create(JobSheetDTO dto) {
        JobSheet entity = new JobSheet();
        copySheet(dto, entity);
        entity.setId(null);
        JobSheet saved = jobSheetRepository.save(entity);
        applyServiceStatus(dto);
        return toDto(saved);
    }

    @Transactional
    public JobSheetDTO update(Integer id, JobSheetDTO dto) {
        JobSheet existing = jobSheetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job sheet not found: " + id));
        copySheet(dto, existing);
        existing.setId(id);
        JobSheet saved = jobSheetRepository.save(existing);
        applyServiceStatus(dto);
        return toDto(saved);
    }

    private void applyServiceStatus(JobSheetDTO dto) {
        if (dto.getServiceStatus() == null || dto.getServiceStatus().isBlank() || dto.getSerId() == null) {
            return;
        }
        Long sid = dto.getSerId();
        ServiceMaster sm = serviceRepository.findById(sid)
                .orElseThrow(() -> new RuntimeException("Service not found: " + sid));
        sm.setStatus(dto.getServiceStatus().trim());
        serviceRepository.save(sm);
    }

    private void copySheet(JobSheetDTO dto, JobSheet entity) {
        BeanUtils.copyProperties(dto, entity, "id", "serviceStatus");
    }

    private JobSheetDTO toDto(JobSheet e) {
        JobSheetDTO d = new JobSheetDTO();
        BeanUtils.copyProperties(e, d);
        return d;
    }
}
