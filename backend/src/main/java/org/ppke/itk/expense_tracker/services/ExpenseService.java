package org.ppke.itk.expense_tracker.services;

import jakarta.persistence.criteria.Predicate;
import org.ppke.itk.expense_tracker.domain.Expense;
import org.ppke.itk.expense_tracker.Repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    @Transactional
    public Optional<Expense> updateExpense(Long id, Expense expenseDetails) {
        return expenseRepository.findById(id)
                .map(existingExpense -> {
                    if (expenseDetails.getDescription() != null) {
                        existingExpense.setDescription(expenseDetails.getDescription());
                    }
                    if (expenseDetails.getAmount() != null) {
                        existingExpense.setAmount(expenseDetails.getAmount());
                    }
                    if (expenseDetails.getCategory() != null) {
                        existingExpense.setCategory(expenseDetails.getCategory());
                    }
                    if (expenseDetails.getUser() != null) {
                        existingExpense.setUser(expenseDetails.getUser());
                    }
                    return expenseRepository.save(existingExpense);
                });
    }


    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }


    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }


    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getExpensesByCategoryId(Long categoryId) {
        return expenseRepository.findByCategoryId(categoryId);
    }

    public double getTotalExpenses() {
        return expenseRepository.findAll()
                .stream()
                .mapToDouble(Expense::getTotalPrice)
                .sum();
    }

    public Double getTotalPrice(Long expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        return expense.map(Expense::getTotalPrice).orElse(0.0);
    }


    public List<Object[]> getExpenseCategoryBreakdown( LocalDate startDate, LocalDate endDate) {
        return expenseRepository.getExpenseCategoryBreakdown(startDate, endDate);
    }




    public List<Expense> searchExpenses(String description, Integer amount, Double price, LocalDate startDate, LocalDate endDate) {
        if (description != null && amount != null && price != null && startDate != null && endDate != null) {
            return expenseRepository.findByDescriptionContainingIgnoreCaseAndAmountAndPriceAndUpdatedAtBetween(
                    description, amount, price, startDate.atStartOfDay(), endDate.atStartOfDay());
        }

        if (description != null && amount != null) {
            return expenseRepository.findByDescriptionContainingIgnoreCaseAndAmountAndPrice(
                    description, amount, price);
        }

        if (description != null && price != null) {
            return expenseRepository.findByDescriptionContainingIgnoreCaseAndPrice(description, price);
        }

        if (amount != null && price != null) {
            return expenseRepository.findByAmountAndPrice(amount, price);
        }

        if (description != null) {
            return expenseRepository.findByDescriptionContainingIgnoreCase(description);
        }

        if (amount != null) {
            return expenseRepository.findByAmount(amount);
        }

        if (price != null) {
            return expenseRepository.findByPrice(price);
        }

        if (startDate != null && endDate != null) {
            return expenseRepository.findByUpdatedAtBetween(startDate, endDate);
        }

        return expenseRepository.findAll();
    }


}
