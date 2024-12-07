package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    Optional<Teacher> findByEmail(String email);

    @Query(value = "SELECT * FROM teacher ORDER BY name ASC", nativeQuery = true)
    List<Teacher> findByTeacherName();


    @Query(value = "SELECT * FROM teacher " +
            "JOIN ( " +
            "    SELECT school_name, standard_name, name AS subject_name " +
            "    FROM subject " +
            "    JOIN ( " +
            "        SELECT school_name, id AS standard_id, name AS standard_name " +
            "        FROM standard " +
            "        JOIN ( " +
            "            SELECT id AS schl_id, name AS school_name " +
            "            FROM school " +
            "            WHERE name = :school_name " +
            "        ) AS school ON standard.school_id = school.schl_id " +
            "        WHERE (:isLesserThan = true AND standard.name <= :standard_name) " +
            "        OR (:isLesserThan = false AND standard.name >= :standard_name) " +
            "    ) AS standard ON subject.standard_id = standard.standard_id " +
            "    WHERE subject.name = :subject_name " +
            ") AS subject ON teacher.known_subject = subject.subject_name",
            nativeQuery = true)
    List<Teacher> search(@Param("school_name") String schoolName,
                         @Param("standard_name") String standardName,
                         @Param("subject_name") String subjectName,
                         @Param("isLesserThan") boolean isLesserThen);

}
