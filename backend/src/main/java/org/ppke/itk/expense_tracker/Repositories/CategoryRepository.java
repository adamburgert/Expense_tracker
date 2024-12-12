package org.ppke.itk.expense_tracker.Repositories;

import org.ppke.itk.expense_tracker.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {




    List<Category> findByUserId(Long userId);






    List<Category> findByDescriptionContainingIgnoreCase(String description);


    List<Category> findByUpdatedAtBetween(LocalDateTime updatedAt, LocalDateTime updatedAt2);

    List<Category> findByDescriptionContainingIgnoreCaseAndUpdatedAtBetween(
            String description, LocalDateTime updatedAt, LocalDateTime updatedAt2);



}

