package com.example.schoolmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SchoolManagementApplication.class)
public class SchoolManagementApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void runMainApplication() {
        SchoolManagementApplication.main(new String[]{});
    }

}
