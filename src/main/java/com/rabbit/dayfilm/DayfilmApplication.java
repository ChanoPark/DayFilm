package com.rabbit.dayfilm;

import com.rabbit.dayfilm.item.repository.ItemElasticsearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = com.rabbit.dayfilm.item.repository.ItemElasticsearchRepository.class))
public class DayfilmApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayfilmApplication.class, args);
	}

}
