package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        System.out.println("Starting application...");
        try {
            SpringApplication.run(DemoApplication.class, args);
            System.out.println("Application started successfully!");
        } catch (Exception e) {
            System.err.println("Failed to start application:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}