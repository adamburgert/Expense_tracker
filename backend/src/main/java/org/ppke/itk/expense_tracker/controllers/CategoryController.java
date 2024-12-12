package org.ppke.itk.expense_tracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ppke.itk.expense_tracker.domain.Category;
import org.ppke.itk.expense_tracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:42705")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get a paginated list of categories", description = "Fetch categories with pagination and sorting options")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameter")
    })
    @GetMapping
    public ResponseEntity<Page<Category>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String searchTerm) {

        Page<Category> categories = categoryService.getCategories(page, size, sortBy, sortDir, searchTerm);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get all categories", description = "Fetch all categories without pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get category by ID", description = "Fetch a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create a new category", description = "Add a new category to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category details")
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Operation(summary = "Update a category", description = "Update an existing category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Optional<Category> updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return updatedCategory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get categories by user ID", description = "Fetch categories related to a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories by user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Category>> getCategoriesByUserId(@PathVariable Long userId) {
        List<Category> categories = categoryService.getCategoriesByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get categories by category ID", description = "Fetch categories by their category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategoriesByCategoryId(@PathVariable Long categoryId) {
        Optional<Category> categories = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Delete a category", description = "Delete a category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search categories", description = "Search for categories by description, start date, and end date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        List<Category> categories;

        if (description != null || startDate != null || endDate != null) {
            categories = categoryService.searchCategories(description, startDate, endDate);
        } else {
            categories = categoryService.getAllCategories();
        }

        return ResponseEntity.ok(categories);
    }
}
