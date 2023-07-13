package com.andrewn.springMVC.config;

import com.andrewn.springMVC.model.Todo;
import com.andrewn.springMVC.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class LoadDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository) {
        return args -> {
            LOGGER.info("Loading " + todoRepository.save(new Todo("Купи продукты", false, new Date())));
            LOGGER.info("Loading " + todoRepository.save(new Todo("Сделай уроки", false, new Date())));
        };
    }
}
