package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final SectionDTO sectionDTO) {
        final Standard standard = this.standardRepository.findById(sectionDTO.getStandardId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        standard.setId(sectionDTO.getStandardId());
        final Section section = Section.builder().standard(standard).name(sectionDTO.getName()).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.sectionRepository.save(section)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.sectionRepository.findAll()).statusValue(HttpStatus.OK.name()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.sectionRepository.findById(id)).statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.sectionRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        this.sectionRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SectionDTO sectionDTO) {
        if (sectionDTO == null) {
            throw new ResourceNotFoundException(Constants.DATA_REQUIRED);
        }
        final Section existingSection = this.sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Standard standard = new Standard();
        standard.setId(sectionDTO.getStandardId());
        if (sectionDTO.getName() != null) {
            existingSection.setName(sectionDTO.getName());
        }
        if (sectionDTO.getStandardId() != null) {
            existingSection.setStandard(standard);
        }
        existingSection.setUpdatedBy(authentication.getUserName());
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.sectionRepository.save(existingSection)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
