package org.ppke.itk.expense_tracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ppke.itk.expense_tracker.domain.Expense;
import org.ppke.itk.expense_tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:42705")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Fetch all expenses", description = "Retrieve all expenses stored in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched all expenses",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Expense.class)))
    })
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Fetch an expense by ID", description = "Retrieve a specific expense by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Expense.class))),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Operation(summary = "Create a new expense", description = "Add a new expense to the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Expense created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Expense.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @Operation(summary = "Update an expense", description = "Modify an existing expense.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Expense.class))),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails) {
        Optional<Expense> updatedExpense = expenseService.updateExpense(id, expenseDetails);
        return updatedExpense.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Operation(summary = "Fetch expenses by user ID", description = "Retrieve all expenses associated with a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched user expenses")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable Long userId) {
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Fetch expenses by category ID", description = "Retrieve all expenses associated with a specific category.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched category expenses")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Expense>> getExpensesByCategoryId(@PathVariable Long categoryId) {
        List<Expense> expenses = expenseService.getExpensesByCategoryId(categoryId);
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Get total expenses", description = "Calculate the total of all expenses.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total calculated successfully")
    })
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalExpenses() {
        double total = expenseService.getTotalExpenses();
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Delete an expense", description = "Remove an expense by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @Operation(summary = "Get expense category breakdown", description = "Retrieve a breakdown of expenses by category for a given date range.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Breakdown fetched successfully")
    })
    @GetMapping("/categories")
    public List<Object[]> getExpenseCategoryBreakdown(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return expenseService.getExpenseCategoryBreakdown(startDate, endDate);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Expense>> searchExpenses(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        List<Expense> expenses = expenseService.searchExpenses(description, amount, price, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }
}
