package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final StandardRepository standardRepository;
    private final SectionRepository sectionRepository;

    public StudentService(final StudentRepository studentRepository, final SchoolRepository schoolRepository, final StandardRepository standardRepository, final SectionRepository sectionRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.standardRepository = standardRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public ResponseDTO createStudent(final StudentDTO studentDTO) {
        final School school = this.schoolRepository.findById(studentDTO.getSchool()).orElseThrow(() -> new BadRequestServiceException("School not found"));
        school.setId(studentDTO.getSchool());
        final Standard standard = this.standardRepository.findById(studentDTO.getStandard()).orElseThrow(() -> new BadRequestServiceException("Standard not found"));
        standard.setId(studentDTO.getStandard());
        final Section section = this.sectionRepository.findById(studentDTO.getSection()).orElseThrow(() -> new BadRequestServiceException("Standard not found"));
        section.setId(studentDTO.getSection());

        final Student student = new Student();
        student.setSchool(school);
        student.setStandard(standard);
        student.setSection(section);
        student.setName(studentDTO.getName());
        student.setFatherName(studentDTO.getFatherName());
        student.setMotherName(studentDTO.getMotherName());
        student.setAddress(studentDTO.getAddress());
        student.setPhone(studentDTO.getPhone());
        student.setCreatedBy(studentDTO.getCreatedBy());
        student.setUpdatedBy(studentDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.studentRepository.save(student), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveStudentById(final String id) {
        return new ResponseDTO(Constants.SUCCESS, this.studentRepository.findById(id), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveStudent() {
        return new ResponseDTO(Constants.SUCCESS, this.studentRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO deleteStudentById(final String id) {
        if (!this.studentRepository.existsById(id)) {
            throw new BadRequestServiceException("student not found");
        }
        this.studentRepository.deleteById(id);
        return new ResponseDTO("Successfully deleted", id, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateStudentById(final String id, final StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new BadRequestServiceException("student not found"));

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
        if (studentDTO.getUpdatedBy() != null) {
            existingStudent.setUpdatedBy(studentDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.SUCCESS, this.studentRepository.save(existingStudent), HttpStatus.OK.getReasonPhrase());
    }

}
