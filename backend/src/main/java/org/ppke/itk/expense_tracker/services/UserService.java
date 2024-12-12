package org.ppke.itk.expense_tracker.services;

import jakarta.persistence.EntityNotFoundException;
import org.ppke.itk.expense_tracker.Repositories.RoleRepository;
import org.ppke.itk.expense_tracker.domain.Role;
import org.ppke.itk.expense_tracker.domain.RoleName;
import org.ppke.itk.expense_tracker.domain.User;
import org.ppke.itk.expense_tracker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        System.out.println("PasswordEncoder injected: " + (passwordEncoder != null));
        this.roleService = roleService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        try {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("unique_username")) {
                throw new IllegalArgumentException("Username already exists");
            } else if (e.getMessage().contains("unique_email")) {
                throw new IllegalArgumentException("Email already exists");
            } else {
                System.err.println("Unexpected DataIntegrityViolation: " + e.getMessage());
                throw new RuntimeException("An unexpected error occurred during registration."); // Generic message to the client
            }
        }
    }

    public Optional<User> updateUser(Long userId, User updatedProfile) {
        return userRepository.findById(userId).map(user -> {
            if (updatedProfile.getUsername() != null) {
                user.setUsername(updatedProfile.getUsername());
            }
            if (updatedProfile.getEmail() != null) {
                user.setEmail(updatedProfile.getEmail());
            }
            if (updatedProfile.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updatedProfile.getPassword()));
            }
            return userRepository.save(user);
        });
    }





    public boolean verifyPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public boolean deleteUser(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting user with ID " + id + ": " + e.getMessage());
            return false;
        }
    }
}
