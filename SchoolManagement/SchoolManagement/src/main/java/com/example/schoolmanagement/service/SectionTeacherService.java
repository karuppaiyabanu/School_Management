package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionTeacherDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.SectionTeacher;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.SectionTeacherRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionTeacherService {

    @Autowired
    private SectionTeacherRepository sectionTeacherRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final SectionTeacherDTO sectionTeacherDTO) {
        final Section section = this.sectionRepository.findById(sectionTeacherDTO.getSection()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Teacher teacher = this.teacherRepository.findById(sectionTeacherDTO.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final SectionTeacher sectionTeacher = SectionTeacher.builder().teacher(teacher).section(section).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.sectionTeacherRepository.save(sectionTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.sectionTeacherRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SectionTeacherDTO sectionTeacherDTO) {
        if (sectionTeacherDTO == null) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        final SectionTeacher sectionTeacher = this.sectionTeacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Teacher teacher = Teacher.builder().id(sectionTeacherDTO.getTeacherId()).build();
        teacher.setId(sectionTeacherDTO.getTeacherId());
        final Section section = new Section();
        section.setId(sectionTeacherDTO.getSection());
        sectionTeacher.setUpdatedBy(authentication.getUserName());
        sectionTeacher.setTeacher(teacher);
        sectionTeacher.setSection(section);
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionTeacherRepository.save(sectionTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
