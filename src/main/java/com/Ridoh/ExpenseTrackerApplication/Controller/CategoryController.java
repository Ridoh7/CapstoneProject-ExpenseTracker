package com.Ridoh.ExpenseTrackerApplication.Controller;
import com.Ridoh.ExpenseTrackerApplication.DTO.CategoryRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<Response> createdCategory(@RequestBody CategoryRequest categoryRequest) {
        Response createdCategory = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/user/{userId}/expenses")
    public ResponseEntity<List<Category>> getCategoriesByUserExpenses(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Category> categories = categoryService.getCategoriesByUserExpenses(user);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/user/{userId}/expenses/date")
    public ResponseEntity<List<Category>> getCategoriesByUserExpensesAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        User user = new User();
        user.setId(userId);

        List<Category> categories = categoryService.getCategoriesByUserExpensesAndDateRange(user, startDate, endDate);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/budgets/amount-greater-than/{amount}")
    public ResponseEntity<List<Category>> getCategoriesByBudgetsAmountGreaterThan(@PathVariable Double amount) {
        List<Category> categories = categoryService.getCategoriesByBudgetsAmountGreaterThan(amount);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/budgets/user/{userId}")
    public ResponseEntity<List<Category>> getCategoriesByBudgetsCategoryUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Category> categories = categoryService.getCategoriesByBudgetsCategoryUser(user);
        return ResponseEntity.ok(categories);
    }

    // Add more methods as needed
}

