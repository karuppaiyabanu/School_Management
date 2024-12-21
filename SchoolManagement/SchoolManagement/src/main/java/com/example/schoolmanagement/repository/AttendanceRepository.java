package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
}
