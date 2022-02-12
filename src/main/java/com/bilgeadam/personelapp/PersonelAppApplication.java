package com.bilgeadam.personelapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PersonelAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonelAppApplication.class, args);
    }

}
