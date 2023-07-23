package com.andrewn.springMVC.config;

import com.andrewn.springMVC.model.Role;
import com.andrewn.springMVC.model.Todo;
import com.andrewn.springMVC.model.User;
import com.andrewn.springMVC.repository.RoleRepository;
import com.andrewn.springMVC.repository.TodoRepository;
import com.andrewn.springMVC.service.UserService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(
      TodoRepository todoRepository, UserService userService, RoleRepository roleRepository) {
    return args -> {
      LOGGER.info("Loading " + todoRepository.save(new Todo("Купи продукты", false, new Date())));
      LOGGER.info("Loading " + todoRepository.save(new Todo("Сделай уроки", false, new Date())));
      Role roleUser = new Role(1L, "ROLE_USER");
      Role roleAdmin = new Role(2L, "ROLE_ADMIN");
      LOGGER.info("Loading " + roleRepository.save(roleUser));
      LOGGER.info("Loading " + roleRepository.save(roleAdmin));
      User admin = new User();
      Set<Role> adminRoles = new HashSet<>();
      adminRoles.add(roleUser);
      adminRoles.add(roleAdmin);
      admin.setUsername("admin");
      admin.setPassword("12345");
      admin.setConfirmPassword("12345");
      LOGGER.info("Loading " + userService.saveUser(admin, adminRoles));
    };
  }
}
