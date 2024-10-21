package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section,String> {
}
