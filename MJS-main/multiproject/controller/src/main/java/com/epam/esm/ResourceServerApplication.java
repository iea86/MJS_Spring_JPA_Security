package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.epam"})
@EntityScan(basePackages = {"com.epam"})
@EnableJpaRepositories(basePackages = {"com.epam"})
public class ResourceServerApplication {
    public static void main(String[] args)  {

        SpringApplication.run(ResourceServerApplication.class, args);
    }
}