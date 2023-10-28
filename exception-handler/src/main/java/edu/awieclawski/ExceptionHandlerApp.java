package edu.awieclawski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class ExceptionHandlerApp {
    public static void main(String[] args) {
        SpringApplication.run(ExceptionHandlerApp.class, args);
    }
}