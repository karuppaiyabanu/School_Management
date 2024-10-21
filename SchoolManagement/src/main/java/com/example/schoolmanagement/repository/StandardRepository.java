package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StandardRepository extends JpaRepository<Standard,String> {

   @Query(value = "SELECT MAX(FEES) FROM STANDARD",nativeQuery = true)
    Double findMaxFees();

}
