package com.bibliomania.BiblioMania;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaRepositories(basePackages = "com.bibliomania.BiblioMania.repository")
@SpringBootApplication
@EnableAsync
@ComponentScan("com.bibliomania")
public class BiblioManiaApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(BiblioManiaApplication.class, args);
	}

}
