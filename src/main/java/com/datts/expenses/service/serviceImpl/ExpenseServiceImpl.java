package com.datts.expenses.service.serviceImpl;

import com.datts.expenses.entity.Expense;
import com.datts.expenses.exceptionHandeler.ResourceNotFoundException;
import com.datts.expenses.repository.ExpenseRepository;
import com.datts.expenses.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Page<Expense> getAllExpense(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @Override
    public Expense getById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            return expense.get();
        }
        throw new ResourceNotFoundException("no Expense found for id :"+id);
    }

    @Override
    public void deleteById(Long id) {
        Expense expense = getById(id);
        expenseRepository.delete(expense);
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getById(id);
        existingExpense.setAmount((expense.getAmount()!=null) ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setCategory(expense.getCategory()!=null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        return expenseRepository.save(existingExpense);
    }

    @Override
    public List<Expense> getByCategory(String category, Pageable pageable) {
        return expenseRepository.findByCategory(category, pageable).toList();
    }

    @Override
    public List<Expense> getByNameContains(String key, Pageable pageable) {
        return expenseRepository.findByNameContaining(key, pageable).toList();
    }

    @Override
    public List<Expense> getByDateBetween(Date startDate, Date endDate, Pageable pageable) {
        if (startDate == null){
            startDate = new Date(0);
        }
        if(endDate == null){
            endDate = new Date(System.currentTimeMillis());
        }
        return expenseRepository.findByDateBetween(startDate, endDate,pageable).toList();
    }
}
