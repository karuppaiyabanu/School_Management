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
    List<Mark> findHighestMarkByStudent(@Param("studentId") String studentId);


}
