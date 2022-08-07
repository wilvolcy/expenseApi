package com.datts.expenses.service;

import com.datts.expenses.entity.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {
    List<Expense> getAllExpense();
    Expense getById(Long id);
    void deleteById(Long id);
    Expense addExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);
}
