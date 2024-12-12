package org.ppke.itk.expense_tracker.services;

import jakarta.persistence.EntityNotFoundException;
import org.ppke.itk.expense_tracker.domain.Role;
import org.ppke.itk.expense_tracker.domain.User;
import org.ppke.itk.expense_tracker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                // Log the full exception for debugging
                System.err.println("Unexpected DataIntegrityViolation: " + e.getMessage());
                throw new RuntimeException("An unexpected error occurred during registration."); // Generic message to the client
            }
        }
    }

    public User assignRoleToUser(Long id, String roleName) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        User user = userOptional.get();
        Role role = roleService.getRoleByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User already has this role: " + roleName);
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


    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true; // Deletion success
        }
        return false; // User not found
    }
}
