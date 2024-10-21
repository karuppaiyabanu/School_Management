package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.AttendanceDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.Attendance;
import com.example.schoolmanagement.model.SectionTeacher;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.AttendanceRepository;
import com.example.schoolmanagement.repository.SectionTeacherRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SectionTeacherRepository sectionTeacherRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository,
                             SectionTeacherRepository sectionTeacherRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.sectionTeacherRepository = sectionTeacherRepository;

    }

    @Transactional
    public ResponseDTO createAttendance(final AttendanceDTO attendanceDTO) {

        final Student student = studentRepository.findById(attendanceDTO.getStudentId())
                .orElseThrow(() -> new BadRequestServiceException("student not found"));
        student.setId(attendanceDTO.getStudentId());

        final SectionTeacher sectionTeacherObj = sectionTeacherRepository.findById(attendanceDTO.getSectionTeacherId()).orElseThrow(() -> new BadRequestServiceException("not found"));
        sectionTeacherObj.setId(attendanceDTO.getSectionTeacherId());
        final Attendance attendanceObj = new Attendance();
        attendanceObj.setStatus(attendanceDTO.getStatus());
        attendanceObj.setStudent(student);
        attendanceObj.setSectionTeacher(sectionTeacherObj);
        return new ResponseDTO(Constants.SUCCESS, this.attendanceRepository.save(attendanceObj), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO getAllStudentsAttendance() {
        return new ResponseDTO(Constants.SUCCESS, this.attendanceRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

}
