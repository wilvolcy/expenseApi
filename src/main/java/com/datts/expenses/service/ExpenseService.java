package com.datts.expenses.service;

import com.datts.expenses.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public interface ExpenseService {
    Page<Expense> getAllExpense(Pageable pageable);
    Expense getById(Long id);
    void deleteById(Long id);
    Expense addExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);
    List<Expense> getByCategory(String category, Pageable pageable);
    List<Expense> getByNameContains(String key, Pageable pageable);
    List<Expense> getByDateBetween(Date startDate, Date endDate, Pageable pageable);
}
