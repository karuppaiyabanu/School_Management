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
    public ResponseDTO createSection(final SectionDTO sectionDTO) {
        final Standard standard = this.standardRepository.findById(sectionDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException("Standard not found"));
        final Section section = new Section();
        standard.setId(sectionDTO.getStandardId());
        section.setName(sectionDTO.getName());
        section.setStandard(standard);
        section.setCreatedBy(sectionDTO.getCreatedBy());
        section.setUpdatedBy(sectionDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.sectionRepository.save(section), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveSection() {
        return new ResponseDTO(Constants.SUCCESS, this.sectionRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSectionById(final String id) {
        return new ResponseDTO(Constants.SUCCESS, this.sectionRepository.findById(id), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO deleteSectionById(final String id) {
        if (!this.sectionRepository.existsById(id)) {
            throw new BadRequestServiceException("Section id not found");
        }
        this.sectionRepository.deleteById(id);
        return new ResponseDTO("Successfully deleted", id, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateSection(final String id, final SectionDTO sectionDTO) {
        Section existingSection = this.sectionRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Section not found"));
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
        this.sectionRepository.save(existingSection);
        return new ResponseDTO(Constants.SUCCESS, existingSection, HttpStatus.OK.getReasonPhrase());
    }

}
