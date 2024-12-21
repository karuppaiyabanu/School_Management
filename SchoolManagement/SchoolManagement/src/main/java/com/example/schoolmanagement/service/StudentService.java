package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private AuthenticationService authentication;


    public static Specification<Student> search(final String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
            }
            return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"), criteriaBuilder.like(criteriaBuilder.lower(root.get("fatherName")), "%" + searchTerm.toLowerCase() + "%"), criteriaBuilder.like(criteriaBuilder.lower(root.get("motherName")), "%" + searchTerm.toLowerCase() + "%"), criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + searchTerm.toLowerCase() + "%"), criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + searchTerm.toLowerCase() + "%"));
        };
    }

    @Transactional
    public ResponseDTO create(final StudentDTO studentDTO) {
        final School school = this.schoolRepository.findById(studentDTO.getSchool()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Standard standard = this.standardRepository.findById(studentDTO.getStandard()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Section section = this.sectionRepository.findById(studentDTO.getSection()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Student student = Student.builder().school(school).standard(standard).section(section).name(studentDTO.getName()).fatherName(studentDTO.getFatherName()).motherName(studentDTO.getMotherName()).address(studentDTO.getAddress()).phone(studentDTO.getPhone()).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.studentRepository.save(student)).statusValue(HttpStatus.CREATED.name()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND))).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        this.studentRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final StudentDTO studentDTO) {
        if (studentDTO == null) {
            throw new ResourceNotFoundException(Constants.DATA_REQUIRED);
        }
        final Student existingStudent = this.studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        if (studentDTO.getName() != null) {
            existingStudent.setName(studentDTO.getName());
        }
        if (studentDTO.getFatherName() != null) {
            existingStudent.setFatherName(studentDTO.getFatherName());
        }
        if (studentDTO.getMotherName() != null) {
            existingStudent.setMotherName(studentDTO.getMotherName());
        }
        if (studentDTO.getAddress() != null) {
            existingStudent.setAddress(studentDTO.getAddress());
        }
        if (studentDTO.getPhone() != null) {
            existingStudent.setPhone(studentDTO.getPhone());
        }
        existingStudent.setUpdatedBy(authentication.getUserName());
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.studentRepository.save(existingStudent)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO searchStudents(final String searchTerm) {
        try {
            final Specification<Student> spec = search(searchTerm);
            final List<Student> students = this.studentRepository.findAll(spec);
            return ResponseDTO.builder().message(Constants.SUCCESS).data(students).statusValue(HttpStatus.OK.getReasonPhrase()).build();
        } catch (Exception e) {
            return ResponseDTO.builder().message(Constants.DATA_NOT_FOUND).data(e.getMessage()).statusValue(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.getReasonPhrase()).build();
        }
    }

}
