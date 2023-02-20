package com.rabbit.dayfilm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DayfilmApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayfilmApplication.class, args);
	}

}
