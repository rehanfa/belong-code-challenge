package com.code.challange.solution;

import com.code.challange.solution.init.DataBaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerDetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerDetailApplication.class, args);
    }

    @Bean
    public CommandLineRunner DBInitializer() {
        return new DataBaseInitializer();
    }

}
