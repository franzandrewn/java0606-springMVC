package com.andrewn.springMVC.service;

import com.andrewn.springMVC.model.Role;
import com.andrewn.springMVC.model.User;
import com.andrewn.springMVC.repository.RoleRepository;
import com.andrewn.springMVC.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  @PersistenceContext private EntityManager em;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

  public UserService(
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }

  public User findUserById(Long userId) {
    Optional<User> userFromDb = userRepository.findById(userId);
    return userFromDb.orElse(new User());
  }

  public List<User> allUsers() {
    return userRepository.findAll();
  }

  public boolean saveUser(User newUser) {
    User userFromDb = userRepository.findByUsername(newUser.getUsername());

    if (userFromDb != null) {
      return false;
    }

    Set<Role> defaultRoles = new HashSet<>();
    defaultRoles.add(new Role(1L, "ROLE_USER"));
    newUser.setRoles(defaultRoles);
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    LOGGER.info("Saving new user " + newUser);
    userRepository.save(newUser);
    return true;
  }

  public boolean saveUser(User newUser, Set<Role> roles) {
    User userFromDb = userRepository.findByUsername(newUser.getUsername());

    if (userFromDb != null) {
      return false;
    }

    newUser.setRoles(roles);
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    LOGGER.info("Saving new user " + newUser);
    userRepository.save(newUser);
    return true;
  }

  public boolean deleteUser(Long userId) {
    if (userRepository.findById(userId).isPresent()) {
      userRepository.deleteById(userId);
      return true;
    }
    return false;
  }

  public List<User> usergtList(Long idMin) {
    return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
        .setParameter("paramId", idMin)
        .getResultList();
  }
}
