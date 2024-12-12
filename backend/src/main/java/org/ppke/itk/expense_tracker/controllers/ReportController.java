package org.ppke.itk.expense_tracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ppke.itk.expense_tracker.domain.Report;
import org.ppke.itk.expense_tracker.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generates a new expense report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - New report generated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Report> generateReport() {
        Report report = reportService.generateExpenseReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/all")
    @Operation(summary = "Retrieves all existing expense reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - List of reports retrieved",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Report.class)))),
            @ApiResponse(responseCode = "204", description = "No content - No reports found")
    })
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no reports
        }
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing expense report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Report updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Report with specified ID not found")
    })
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        try {
            Report report = reportService.updateReport(id, updatedReport);
            return ResponseEntity.ok(report);
        } catch (ReportService.ReportNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //Use build() for void responses
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an existing expense report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - Report deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found - Report with specified ID not found")
    })
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (ReportService.ReportNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latest")
    @Operation(summary = "Retrieves the latest expense report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Latest report retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Report.class))),
            @ApiResponse(responseCode = "204", description = "No content - No reports exist")
    })
    public ResponseEntity<Report> getLatestReport() {
        Report report = reportService.getLatestReport();
        return report != null ? ResponseEntity.ok(report) : ResponseEntity.noContent().build();
    }
}