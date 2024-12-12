package org.ppke.itk.expense_tracker.services;

import org.ppke.itk.expense_tracker.Repositories.UserRepository;
import org.ppke.itk.expense_tracker.domain.Role;
import org.ppke.itk.expense_tracker.Repositories.RoleRepository;
import org.ppke.itk.expense_tracker.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;



    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository, UserRepository userRepository1) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository1;
    }

    public Role createOrUpdateRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + name));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        user.getRoles().add(role);
        userRepository.save(user); // Persist the changes
    }

    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}



