package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.EmailValidator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    public TeacherService(final TeacherRepository teacherRepository, final SchoolRepository schoolRepository) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public ResponseDTO createTeacher(final TeacherDTO teacherDTO) {
        final School school = this.schoolRepository.findById(teacherDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException("School not found"));

        if (!EmailValidator.emailValidation(teacherDTO.getEmail())) {
            return new ResponseDTO("failed", teacherDTO, "Invalid email format");
        }
        if (teacherRepository.findByEmail(teacherDTO.getEmail())) {
            return new ResponseDTO("failed", teacherDTO, "Email already exists");
        }
            final Teacher teacher = Teacher.builder()
                    .name(teacherDTO.getName())
                    .gender(teacherDTO.getGender())
                    .knownSubject(teacherDTO.getKnownSubject())
                    .address(teacherDTO.getAddress())
                    .phone(teacherDTO.getPhone())
                    .email(teacherDTO.getEmail())
                    .knownSubject(teacherDTO.getKnownSubject())
                    .school(school)
                    .createdBy(teacherDTO.getCreatedBy())
                    .updatedBy(teacherDTO.getUpdatedBy())
                    .build();
            return new ResponseDTO(Constants.CREATED, this.teacherRepository.save(teacher), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveTeacherById(final String id) {
        return new ResponseDTO(Constants.SUCCESS, this.teacherRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Teacher not found")), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveTeacher() {
        return new ResponseDTO(Constants.SUCCESS, this.teacherRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO updateTeacher(final String id, final TeacherDTO teacherDTO) {
        Teacher existingTeacher = this.teacherRepository.findById(id).orElseThrow(
                () -> new BadRequestServiceException("Teacher Not found."));
        final School school = new School();
        school.setId(teacherDTO.getSchoolId());
        if (teacherDTO.getName() != null) {
            existingTeacher.setName(teacherDTO.getName());
        }
        if (teacherDTO.getPhone() != null) {
            existingTeacher.setPhone(teacherDTO.getPhone());
        }
        if (teacherDTO.getKnownSubject() != null) {
            existingTeacher.setKnownSubject(teacherDTO.getKnownSubject());
        }
        if (teacherDTO.getEmail() != null) {
            existingTeacher.setEmail(teacherDTO.getEmail());
        }
        if (teacherDTO.getGender() != null) {
            existingTeacher.setGender(teacherDTO.getGender());
        }
        if (teacherDTO.getAddress() != null) {
            existingTeacher.setAddress(teacherDTO.getAddress());
        }
        if (teacherDTO.getSchoolId() != null) {
            existingTeacher.setSchool(school);
        }
        if (teacherDTO.getUpdatedBy() != null) {
            existingTeacher.setUpdatedBy(teacherDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.SUCCESS, this.teacherRepository.save(existingTeacher), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO deleteTeacherById(final String id) {
        if (!this.teacherRepository.existsById(id)) {
            throw new BadRequestServiceException("Teacher id not found");
        }
        this.teacherRepository.deleteById(id);
        return new ResponseDTO("Successfully deleted", id, HttpStatus.OK.getReasonPhrase());
    }

}
