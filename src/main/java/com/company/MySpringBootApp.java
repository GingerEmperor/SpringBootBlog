package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringBootApp {
    public static void main(String[] args) {
        System.out.println("SRART");
        SpringApplication.run(MySpringBootApp.class,args);
    }
}
