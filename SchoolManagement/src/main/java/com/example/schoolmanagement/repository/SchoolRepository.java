package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

}
