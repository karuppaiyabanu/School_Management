package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.SectionTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionTeacherRepository extends JpaRepository<SectionTeacher,String > {
}
