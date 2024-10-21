package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionTeacherDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.SectionTeacher;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.SectionTeacherRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionTeacherService {
    private final SectionTeacherRepository sectionTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final SectionRepository sectionRepository;

    public SectionTeacherService(SectionTeacherRepository sectionTeacherRepository,
                                 TeacherRepository teacherRepository,
                                 SectionRepository sectionRepository) {
        this.sectionTeacherRepository = sectionTeacherRepository;
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public ResponseDTO create(final SectionTeacherDTO sectionTeacherDTO) {
        final Section section = sectionRepository.findById(sectionTeacherDTO.getSection()).orElseThrow(() -> new BadRequestServiceException("section not found"));
        final Teacher teacher = teacherRepository.findById(sectionTeacherDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceException("Teacher not found"));

        section.setId(sectionTeacherDTO.getSection());
        teacher.setId(sectionTeacherDTO.getTeacherId());

        SectionTeacher sectionTeacher = new SectionTeacher();
        sectionTeacher.setTeacher(teacher);
        sectionTeacher.setSection(section);
        sectionTeacher.setCreatedBy(sectionTeacherDTO.getCreatedBy());
        sectionTeacher.setUpdatedBy(sectionTeacherDTO.getUpdatedBy());

        return new ResponseDTO(Constants.CREATED, this.sectionTeacherRepository.save(sectionTeacher), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveSectionTeacher() {
        return new ResponseDTO(Constants.SUCCESS, this.sectionTeacherRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateSectionTeacher(final String id, final SectionTeacherDTO sectionTeacherDTO) {
        final SectionTeacher sectionTeacher = this.sectionTeacherRepository.findById(id).
                orElseThrow(() -> new BadRequestServiceException("SectionTeacher id not found"));
         Teacher teacher = Teacher.builder().id(sectionTeacherDTO.getTeacherId()).build();
        teacher.setId(sectionTeacherDTO.getTeacherId());
        final Section section = new Section();
        section.setId(sectionTeacherDTO.getSection());
        sectionTeacher.setUpdatedBy(sectionTeacherDTO.getUpdatedBy());
        sectionTeacher.setTeacher(teacher);
        sectionTeacher.setSection(section);

        return new ResponseDTO(Constants.SUCCESS, this.sectionTeacherRepository.save(sectionTeacher), HttpStatus.OK.getReasonPhrase());
    }

}
