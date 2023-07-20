package com.andrewn.springMVC.service;

import com.andrewn.springMVC.model.Role;
import com.andrewn.springMVC.model.User;
import com.andrewn.springMVC.repository.RoleRepository;
import com.andrewn.springMVC.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private final EntityManager em;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(EntityManager em, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, BCryptPasswordEncoder passwordEncoder1) {
        this.em = em;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder1;
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

    public int countUsers() {
        return em.createNativeQuery("select count(*) from a_user").getFirstResult();
    }
}
