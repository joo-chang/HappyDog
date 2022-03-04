package dev.mvc.happydog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.mvc"})
public class HappydogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappydogApplication.class, args);
	}

}
