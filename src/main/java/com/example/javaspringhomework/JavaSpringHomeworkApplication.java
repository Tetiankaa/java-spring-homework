package com.example.javaspringhomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JavaSpringHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringHomeworkApplication.class, args);
    }

}
