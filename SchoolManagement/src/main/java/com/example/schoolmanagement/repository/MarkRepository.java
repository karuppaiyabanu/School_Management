package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.dto.StudentReport;
import com.example.schoolmanagement.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, String> {

    @Query("SELECT m FROM Mark m WHERE m.student.id = :studentId ORDER BY m.mark DESC")
    Mark findMarkByStudent(@Param("studentId") String studentId);//named query--->followed by entity name

    @Query(value = "SELECT * FROM mark m WHERE m.student.id = :studentId ORDER BY m.mark DESC", nativeQuery = true)
    List<Mark> findHighestMarkByStudents(@Param("studentId") String studentId);//native

    @Query(value = "SELECT \n" +
            "    s.id AS student_id,\n" +
            "    s.name AS student_name,\n" +
            "    e.standard_id,                     \n" +
            "    ROUND(\n" +
            "        (COUNT(DISTINCT a.id) / \n" +
            "        (SELECT COUNT(DISTINCT id) FROM attendance WHERE student_id = s.id)) * 100, 2\n" +
            "    ) AS student_attendance_percentage,\n" +
            "    t.name AS teacher_name,\n" +
            "    e.name AS exam_name,                \n" +
            "    SUM(m.mark) AS totalmarks  \n" +
            "FROM \n" +
            "    student s\n" +
            "-- Join attendance table to calculate attendance percentage\n" +
            "JOIN \n" +
            "    attendance a ON s.id = a.student_id\n" +
            "-- Join section_teacher to get the teacher details\n" +
            "JOIN \n" +
            "    section_teacher st ON a.section_teacher_id = st.id\n" +
            "-- Join teacher table to get the teacher's name\n" +
            "JOIN \n" +
            "    teacher t ON st.teacher_id = t.id\n" +
            "-- Join mark table to get the student's marks\n" +
            "JOIN \n" +
            "    mark m ON s.id = m.student_id\n" +
            "-- Join exam table to get the exam name and type\n" +
            "JOIN \n" +
            "    exam e ON m.exam_id = e.id\n" +
            "GROUP BY \n" +
            "    s.id, e.standard_id, t.name, e.name\n" +
            "ORDER BY \n" +
            "    student_attendance_percentage DESC;\n", nativeQuery = true)
    List<Object[]> generatePdf();




}
