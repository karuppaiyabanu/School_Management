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

    public AttendanceService(final AttendanceRepository attendanceRepository, final StudentRepository studentRepository, final SectionTeacherRepository sectionTeacherRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.sectionTeacherRepository = sectionTeacherRepository;
    }

    @Transactional
    public ResponseDTO create(final AttendanceDTO attendanceDTO) {
        final Student student = this.studentRepository.findById(attendanceDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        student.setId(attendanceDTO.getStudentId());
        final SectionTeacher sectionTeacherObj = this.sectionTeacherRepository.findById(attendanceDTO.getSectionTeacherId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        sectionTeacherObj.setId(attendanceDTO.getSectionTeacherId());
        final Attendance attendance = Attendance.builder().student(student).sectionTeacher(sectionTeacherObj).status(attendanceDTO.getStatus()).build();

        return ResponseDTO.builder().message(Constants.CREATED).data(this.attendanceRepository.save(attendance)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.attendanceRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
