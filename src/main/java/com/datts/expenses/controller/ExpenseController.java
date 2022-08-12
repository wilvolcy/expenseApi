package com.datts.expenses.controller;

import com.datts.expenses.entity.Expense;
import com.datts.expenses.service.ExpenseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }


    /**
     * mw ka retire meta done konsernan Pageable la leum rele method toList la, poum fel retounen on list olye de Page.
     *  public Page<Expense> allExpense(Pageable pageable)
     *  poum relel nan post man map use
     * /expenses?size=2&page=1 ==> size se kantite enfo par page, page se numewo page la, li komanse nan zero.
     * poum sort li map just utilize sort epi non field mw vlel sort la
     * /expenses?sort=amount,desc
     * /expenses?size=2&page=0&sort=amount,desc
     * **/

    @GetMapping("/expenses")
    public List<Expense> allExpense(Pageable pageable){
        return expenseService.getAllExpense(pageable).toList();
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
    public Expense addExpense(@Valid @RequestBody Expense expense){
        return expenseService.addExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable Long id){
        return new ResponseEntity<>(expenseService.updateExpense(id, expense),HttpStatus.OK);
    }
    @GetMapping("/expenses/categories")
    public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable pageable){
        return expenseService.getByCategory(category, pageable);
    }
    @GetMapping("/expenses/names")
    public List<Expense> readByName(@RequestParam String key, Pageable pageable){
        return expenseService.getByNameContains(key, pageable);
    }

    @GetMapping("/expenses/dates")
    public List<Expense> readByDate(@RequestParam(required = false) Date startDate,
                                    @RequestParam(required = false) Date endDate, Pageable pageable){
        return expenseService.getByDateBetween(startDate, endDate, pageable);
    }

}
