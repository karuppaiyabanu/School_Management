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

    public SectionTeacherService(final SectionTeacherRepository sectionTeacherRepository, final TeacherRepository teacherRepository, final SectionRepository sectionRepository) {
        this.sectionTeacherRepository = sectionTeacherRepository;
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public ResponseDTO create(final SectionTeacherDTO sectionTeacherDTO) {
        final Section section = this.sectionRepository.findById(sectionTeacherDTO.getSection()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        section.setId(sectionTeacherDTO.getSection());
        final Teacher teacher = this.teacherRepository.findById(sectionTeacherDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        teacher.setId(sectionTeacherDTO.getTeacherId());
        final SectionTeacher sectionTeacher = SectionTeacher.builder().teacher(teacher).section(section).createdBy(sectionTeacherDTO.getCreatedBy()).updatedBy(sectionTeacherDTO.getUpdatedBy()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.sectionTeacherRepository.save(sectionTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionTeacherRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SectionTeacherDTO sectionTeacherDTO) {
        final SectionTeacher sectionTeacher = this.sectionTeacherRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        final Teacher teacher = Teacher.builder().id(sectionTeacherDTO.getTeacherId()).build();
        teacher.setId(sectionTeacherDTO.getTeacherId());
        final Section section = new Section();
        section.setId(sectionTeacherDTO.getSection());
        sectionTeacher.setUpdatedBy(sectionTeacherDTO.getUpdatedBy());
        sectionTeacher.setTeacher(teacher);
        sectionTeacher.setSection(section);
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionTeacherRepository.save(sectionTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
