package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final TeacherDTO teacherDTO) {
        final School school = this.schoolRepository.findById(teacherDTO.getSchoolId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        validateTeacherDTO(teacherDTO);
        final Teacher teacher = Teacher.builder().name(teacherDTO.getName()).gender(teacherDTO.getGender()).address(teacherDTO.getAddress()).phone(teacherDTO.getPhone()).email(teacherDTO.getEmail()).knownSubject(teacherDTO.getKnownSubject()).school(school).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.teacherRepository.save(teacher)).statusValue(HttpStatus.CREATED.name()).build();
    }

    private void validateTeacherDTO(final TeacherDTO teacherDTO) {
        if (!UtilService.emailValidation(teacherDTO.getEmail())) {
            throw new InputMismatchException(Constants.REGEX_MISS_MATCH);
        }
        Optional<Teacher> teacher = this.teacherRepository.findByEmail(teacherDTO.getEmail());
        if (teacher.isPresent()) {
            throw new ResourceNotFoundException(Constants.EXISTING_EMAIL);
        }
    }

    public ResponseDTO retrieveById(final String id) {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND))).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final TeacherDTO teacherDTO) {
        final Teacher existingTeacher = this.teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.ID_DOES_NOT_EXIST));
        final School school = School.builder().build();
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
        existingTeacher.setUpdatedBy(authentication.getUserName());
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.teacherRepository.save(existingTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.REMOVED);
        }
        this.teacherRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO search(final String schoolName,final String standardName,final String subjectName,final Boolean isLesserThen){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.teacherRepository.search(schoolName,standardName,subjectName,isLesserThen))
                .statusValue(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
