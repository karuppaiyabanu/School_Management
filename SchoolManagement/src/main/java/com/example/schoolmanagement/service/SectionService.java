package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StandardRepository standardRepository;

    public SectionService(final SectionRepository sectionRepository, final StandardRepository standardRepository) {
        this.sectionRepository = sectionRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO create(final SectionDTO sectionDTO) {
        final Standard standard = this.standardRepository.findById(sectionDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException(Constants.NOT_FOUND));
        standard.setId(sectionDTO.getStandardId());
        final Section section = Section.builder().standard(standard).name(sectionDTO.getName()).createdBy(sectionDTO.getCreatedBy()).updatedBy(sectionDTO.getUpdatedBy()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.sectionRepository.save(section)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionRepository.findById(id)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.sectionRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.sectionRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SectionDTO sectionDTO) {
        Section existingSection = this.sectionRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NOT_FOUND));
        final Standard standard = new Standard();
        standard.setId(sectionDTO.getStandardId());
        if (sectionDTO.getName() != null) {
            existingSection.setName(sectionDTO.getName());
        }
        if (sectionDTO.getStandardId() != null) {
            existingSection.setStandard(standard);
        }
        if (sectionDTO.getUpdatedBy() != null) {
            existingSection.setUpdatedBy(sectionDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.sectionRepository.save(existingSection)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
