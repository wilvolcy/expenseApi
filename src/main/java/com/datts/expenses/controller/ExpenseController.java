package com.datts.expenses.controller;

import com.datts.expenses.entity.Expense;
import com.datts.expenses.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }
    @GetMapping("/expenses")
    public List<Expense> allExpense(){
        return expenseService.getAllExpense();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id){
        return expenseService.getById(id);
    }
    @ResponseStatus (value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("id") Long id){
        expenseService.deleteById(id);
    }
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody Expense expense){
        return expenseService.addExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable Long id){
//        return expenseService.updateExpense(id, expense);
        return new ResponseEntity<>(expenseService.updateExpense(id, expense),HttpStatus.OK);
    }


}
