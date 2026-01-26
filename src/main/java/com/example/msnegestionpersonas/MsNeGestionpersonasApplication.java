package com.example.msnegestionpersonas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories
@SpringBootApplication
public class MsNeGestionpersonasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsNeGestionpersonasApplication.class, args);
    }

}
