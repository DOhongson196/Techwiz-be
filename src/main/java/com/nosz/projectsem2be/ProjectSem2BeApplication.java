package com.nosz.projectsem2be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProjectSem2BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectSem2BeApplication.class, args);
    }

}
