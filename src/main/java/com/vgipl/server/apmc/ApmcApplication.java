package com.vgipl.server.apmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class ApmcApplication extends SpringBootServletInitializer {

	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApmcApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApmcApplication.class, args);
	}

}
