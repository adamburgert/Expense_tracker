package org.ppke.itk.expense_tracker.Repositories;

import org.ppke.itk.expense_tracker.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {


    List<Expense> findByDescriptionContainingIgnoreCaseAndAmountAndPriceAndUpdatedAtBetween(
            String description, Integer amount, Double price, LocalDateTime updatedAt, LocalDateTime updatedAt2);



    List<Expense> findByDescriptionContainingIgnoreCaseAndAmountAndPrice(
            String description, Integer amount, Double price);

    List<Expense> findByDescriptionContainingIgnoreCaseAndPrice(
            String description, Double price);

    List<Expense> findByUserId(Long userId);


    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByAmountAndPrice(Integer amount, Double price);

    List<Expense> findByAmount(Integer amount);

    List<Expense> findByPrice(Double price);




    List<Expense> findByDescriptionContainingIgnoreCase(String description);




    List<Expense> findByUpdatedAtBetween(LocalDate startDate, LocalDate endDate);



    @Query("SELECT SUM(e.amount * e.price) FROM Expense e " +
            "WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate " +
            "GROUP BY e.category")
    List<Object[]> getExpenseCategoryBreakdown(LocalDate startDate, LocalDate endDate);

}

