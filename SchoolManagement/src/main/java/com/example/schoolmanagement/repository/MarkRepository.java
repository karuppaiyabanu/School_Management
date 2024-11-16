package com.example.schoolmanagement.repository;

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

}
