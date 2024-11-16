package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    Optional<Teacher> findByEmail(String email);

    @Query(value = "SELECT * FROM teacher ORDER BY name ASC", nativeQuery = true)
    List<Teacher> findByTeacherName();

}
