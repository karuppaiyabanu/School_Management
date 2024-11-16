package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Subject;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StandardRepository standardRepository;

    public SubjectService(final SubjectRepository subjectRepository, final StandardRepository standardRepository) {
        this.subjectRepository = subjectRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO create(final SubjectDTO subjectDTO) {
        final Standard standard = this.standardRepository.findById(subjectDTO.getStandard()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        standard.setId(subjectDTO.getStandard());
        final Subject subject = Subject.builder().standard(standard).name(subjectDTO.getName()).createdBy(subjectDTO.getCreatedBy()).updatedBy(subjectDTO.getUpdatedBy()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.subjectRepository.save(subject)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.subjectRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SubjectDTO subjectDTO) {
        Subject existingSubject = this.subjectRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        if (subjectDTO.getName() != null) {
            existingSubject.setName(subjectDTO.getName());
        }
        if (subjectDTO.getUpdatedBy() != null) {
            existingSubject.setUpdatedBy(subjectDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.subjectRepository.save(existingSubject)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO remove(final String id) {
        if (!this.subjectRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NO_DATA_FOUND);
        }
        this.subjectRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
