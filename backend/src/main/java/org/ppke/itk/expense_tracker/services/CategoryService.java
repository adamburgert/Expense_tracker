package org.ppke.itk.expense_tracker.services;

import org.ppke.itk.expense_tracker.domain.Category;
import org.ppke.itk.expense_tracker.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Updates an existing category by its ID.
     *
     * @param id             The ID of the category to be updated.
     * @param categoryDetails The updated category details.
     * @return The updated category wrapped in Optional if found.
     */
    @Transactional
    public Optional<Category> updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    if (categoryDetails.getDescription() != null) {
                        existingCategory.setDescription(categoryDetails.getDescription());
                    }
                    if (categoryDetails.getCategory() != null) {
                        existingCategory.setCategory(categoryDetails.getCategory());
                    }
                    if (categoryDetails.getUser() != null) {
                        existingCategory.setUser(categoryDetails.getUser());
                    }
                    return categoryRepository.save(existingCategory);
                });
    }

    /**
     * Fetch all categories.
     *
     * @return List of all categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Delete a category by its ID.
     *
     * @param id The ID of the category to be deleted.
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Create a new category.
     *
     * @param category The category object to be created.
     * @return The created category.
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }


    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }


    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }


    public List<Category> searchCategories(String description, LocalDate startDate, LocalDate endDate) {
        if (description != null && startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();
            return categoryRepository.findByDescriptionContainingIgnoreCaseAndUpdatedAtBetween(
                    description, startDateTime, endDateTime);
        }

        if (description != null) {
            return categoryRepository.findByDescriptionContainingIgnoreCase(description);
        }

        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();
            return categoryRepository.findByUpdatedAtBetween(startDateTime, endDateTime);
        }

        return categoryRepository.findAll();
    }


    public Page<Category> getCategories(int page, int size, String sortBy, String sortDir, String searchTerm) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Enhanced filtering by search term
        Specification<Category> specification = (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("id").as(String.class)), "%" + searchTerm + "%")
                );
            }
            return criteriaBuilder.conjunction(); // No filtering if searchTerm is null
        };

        return categoryRepository.findAll(specification, pageable);
    }


}
