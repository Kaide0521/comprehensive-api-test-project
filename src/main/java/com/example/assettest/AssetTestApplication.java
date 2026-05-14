package com.example.assettest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class used by scanners to recognize a Spring Boot project.
 */
@SpringBootApplication
@MapperScan("com.example.assettest.mapper")
public class AssetTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssetTestApplication.class, args);
    }
}
