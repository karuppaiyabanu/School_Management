package com.example.schoolmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchoolManagementApplication extends RuntimeException {

    public static void main(String[] args) {

        SpringApplication.run(SchoolManagementApplication.class, args);

    }

}
