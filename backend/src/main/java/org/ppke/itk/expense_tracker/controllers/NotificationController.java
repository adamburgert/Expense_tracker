package org.ppke.itk.expense_tracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ppke.itk.expense_tracker.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:42705")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/getLatestNotifications")
    @Operation(summary = "Retrieves the latest notifications for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - List of notifications retrieved", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<String>> getNotifications(@Parameter(description = "User ID") @RequestParam("userId") Integer id) {
        List<String> notifications = notificationService.getNotificationsByUser(id);
        return ResponseEntity.ok(notifications);
    }
}