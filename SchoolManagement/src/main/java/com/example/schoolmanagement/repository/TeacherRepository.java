package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,String> {

    @Query(value = "SELECT email FROM teacher WHERE email=:search",nativeQuery = true)
    Boolean findByEmail (@Param("search") String email);
}
