package com.marhashoft.requerimentoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class RequerimentoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequerimentoApiApplication.class, args);
	}
}
