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

    public SubjectService(SubjectRepository subjectRepository, StandardRepository standardRepository) {
        this.subjectRepository = subjectRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO createSubjectToStandard(final SubjectDTO subjectDTO) {

        final Standard standard = standardRepository.findById(subjectDTO.getStandard()).orElseThrow(() -> new BadRequestServiceException("Standard not found"));
        standard.setId(subjectDTO.getStandard());
        Subject subject = new Subject();
        subject.setStandard(standard);
        subject.setName(subjectDTO.getName());
        subject.setCreatedBy(subjectDTO.getCreatedBy());
        subject.setUpdatedBy(subjectDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.subjectRepository.save(subject), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveSubject() {
        return new ResponseDTO(Constants.SUCCESS, this.subjectRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO updateSubjectToStandard(final String id, final SubjectDTO subjectDTO) {
        Subject existingSubject = this.subjectRepository.findById(id).orElseThrow(
                () -> new BadRequestServiceException("Subject Not found."));
        if (subjectDTO.getName() != null) {
            existingSubject.setName(subjectDTO.getName());
        }
        if (subjectDTO.getUpdatedBy() != null) {
            existingSubject.setUpdatedBy(subjectDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.SUCCESS, this.subjectRepository.save(existingSubject), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO deleteSubjectById(final String id) {
        if (!this.subjectRepository.existsById(id)) {
            throw new BadRequestServiceException("Teacher id not found");
        }
        this.subjectRepository.deleteById(id);
        return new ResponseDTO("Successfully deleted", id, HttpStatus.OK.getReasonPhrase());
    }
}
