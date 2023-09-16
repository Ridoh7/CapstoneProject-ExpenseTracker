package com.Ridoh.ExpenseTrackerApplication.Controller;
import com.Ridoh.ExpenseTrackerApplication.DTO.ExpenseRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Expense;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Service.ExpenseService;
import com.Ridoh.ExpenseTrackerApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response> createExpense(@RequestBody ExpenseRequest expenseRequest, Authentication authentication) throws Exception {
        Response createdExpense = expenseService.createExpense(expenseRequest);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Expense> expenses = expenseService.getExpensesByUser(user);
        return ResponseEntity.ok(expenses);
}

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<Expense>> getExpensesByUserAndCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId
    ) {
        User user = new User();
        user.setId(userId);

        Category category = new Category();
        category.setId(categoryId);

        List<Expense> expenses = expenseService.getExpensesByUserAndCategory(user, category);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/date")
    public ResponseEntity<List<Expense>> getExpensesByUserAndDateRange(
            @PathVariable User userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        List<Expense> expenses = expenseService.getExpensesByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/amount-greater-than/{amount}")
    public ResponseEntity<List<Expense>> getExpensesByUserAndAmountGreaterThan(
            @PathVariable Long userId,
            @PathVariable Double amount
    ) {
        User user = new User();
        user.setId(userId);

        List<Expense> expenses = expenseService.getExpensesByUserAndAmountGreaterThan(user, amount);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/order-by-date-desc")
    public ResponseEntity<List<Expense>> getExpensesByUserOrderByDateDesc(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Expense> expenses = expenseService.getExpensesByUserOrderByDateDesc(user);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/total-amount-spent")
    public ResponseEntity<Double> getTotalAmountSpentByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        Double totalAmountSpent = expenseService.getTotalAmountSpentByUser(user);
        return ResponseEntity.ok(totalAmountSpent);
    }
}
