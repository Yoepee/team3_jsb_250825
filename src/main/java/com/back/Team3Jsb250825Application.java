package com.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team3Jsb250825Application {

    public static void main(String[] args) {
        SpringApplication.run(Team3Jsb250825Application.class, args);
    }

}
