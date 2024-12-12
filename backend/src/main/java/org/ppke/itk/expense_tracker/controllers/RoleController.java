package org.ppke.itk.expense_tracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.ppke.itk.expense_tracker.domain.Role;
import org.ppke.itk.expense_tracker.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:38263")
@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles") // Add a tag for grouping endpoints
public class RoleController {

    private final RoleService roleService;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(summary = "Get All Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    })
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get Role by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        Role role = roleService.getRoleByName(name);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{create}")
    @Operation(summary = "Create Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    })
    public ResponseEntity<Role> createRole(@RequestBody Role role, @PathVariable String create) {
        Role createdRole = roleService.createOrUpdateRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @DeleteMapping("/{role_id}")
    @Operation(summary = "Delete Role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    public ResponseEntity<Void> deleteRole(@PathVariable Long role_id) {
        roleService.deleteRole(role_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/assignRole/{userId}/roles/{roleName}")
    @Operation(summary = "Assign Role to User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Role assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Error assigning role")
    })
    @Parameter(in = ParameterIn.PATH, name = "userId", description = "User ID", required = true)
    @Parameter(in = ParameterIn.PATH, name = "roleName", description = "Role Name", required = true)
    public ResponseEntity<String> assignRoleToUser(
            @PathVariable Long userId,
            @PathVariable String roleName) {
        try {
            roleService.assignRoleToUser((userId), roleName);
            return ResponseEntity.ok("Role assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error assigning role: " + e.getMessage());
        }
    }
}