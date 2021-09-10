package com.openclassrooms.ocproject10.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan({"com.openclassrooms.ocproject10.notes", "com.openclassrooms.ocproject10.domain"})
@EnableJpaRepositories(basePackages = {"com.openclassrooms.ocproject10.patients.repository", "com.openclassrooms.ocproject10.notes.repository"})
@EntityScan("com.openclassrooms.ocproject10.domain")
public class NotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
