package com.example.projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DomainTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainTestApplication.class, args);
    }
}
