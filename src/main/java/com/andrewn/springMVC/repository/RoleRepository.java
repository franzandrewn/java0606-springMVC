package com.andrewn.springMVC.repository;

import com.andrewn.springMVC.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}
