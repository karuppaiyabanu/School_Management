package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.AttendanceDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.Attendance;
import com.example.schoolmanagement.model.SectionTeacher;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.AttendanceRepository;
import com.example.schoolmanagement.repository.SectionTeacherRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SectionTeacherRepository sectionTeacherRepository;

    @Transactional
    public ResponseDTO create(final AttendanceDTO attendanceDTO) {
        final Student student = this.studentRepository.findById(attendanceDTO.getStudentId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final SectionTeacher sectionTeacherObj = this.sectionTeacherRepository.findById(attendanceDTO.getSectionTeacherId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Attendance attendance = Attendance.builder().student(student).sectionTeacher(sectionTeacherObj).status(attendanceDTO.getStatus()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.attendanceRepository.save(attendance)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.attendanceRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
