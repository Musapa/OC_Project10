package com.openclassrooms.ocproject10.patients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.openclassrooms.ocproject10.patients", "com.openclassrooms.ocproject10.domain"})
@EnableJpaRepositories(basePackages = {"com.openclassrooms.ocproject10.patients.repository"})
@EntityScan("com.openclassrooms.ocproject10.domain")
public class PatientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsApplication.class, args);
	}

}
