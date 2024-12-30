package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

    Optional<School> findByEmail(String email);

    @Query(value = "select email from school", nativeQuery = true)
    String[] getEmails();
}
