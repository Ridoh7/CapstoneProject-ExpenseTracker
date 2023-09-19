package com.Ridoh.ExpenseTrackerApplication.Controller;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetResponse;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetUpdateRequest;
import com.Ridoh.ExpenseTrackerApplication.Entity.Budget;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.ServiceInterface.BudgetService;
import com.Ridoh.ExpenseTrackerApplication.ServiceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    @Autowired
    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @PostMapping("/createBudget")
    public ResponseEntity<BudgetResponse> createBudget(@RequestBody BudgetRequest budgetRequest) throws ChangeSetPersister.NotFoundException {
        BudgetResponse createdBudget = budgetService.createBudget(budgetRequest);
        return ResponseEntity.ok(createdBudget);
    }

    @PutMapping("/updateBudget/category/{categoryId}/user/{userId}")
    public ResponseEntity<BudgetResponse> updateBudget(@RequestBody BudgetUpdateRequest budgetUpdateRequest) {
        try {
            BudgetResponse response = budgetService.updateBudget(budgetUpdateRequest);
            return ResponseEntity.ok(response);
        } catch (ChangeSetPersister.NotFoundException e) {
            // Handle not found exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByCategory(@PathVariable("categoryId") Long categoryId)  {
        return ResponseEntity.ok(budgetService.getBudgetsByCategory(categoryId));
    }

    @GetMapping("/category/{categoryId}/amount-greater-than/{amount}")
    public ResponseEntity<List<Budget>> getBudgetsByCategoryAndAmountGreaterThan(
            @PathVariable Long categoryId,
            @PathVariable Double amount
    ) {
        Category category = new Category();
        category.setId(categoryId);
        List<Budget> budgets = budgetService.getBudgetsByCategoryAndAmountGreaterThan(category, amount);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getBudgetsByCategoryUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Budget> budgets = budgetService.getBudgetsByCategoryUser(user);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/user/{userId}/expenses/date")
    public ResponseEntity<List<Budget>> getBudgetsByCategoryUserAndCategoryExpensesDateBetween(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        User user = new User();
        user.setId(userId);

        List<Budget> budgets = budgetService.getBudgetsByCategoryUserAndCategoryExpensesDateBetween(user, startDate, endDate);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/user/{userId}/order-by-amount-desc")
    public ResponseEntity<List<Budget>> getBudgetsByCategoryUserOrderByAmountDesc(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Budget> budgets = budgetService.getBudgetsByCategoryUserOrderByAmountDesc(user);
        return ResponseEntity.ok(budgets);

}


    @GetMapping("/user/{userId}/total-amount-budgeted")
    public ResponseEntity<Double> getTotalAmountBudgetedByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        Double totalAmountBudgeted = budgetService.getTotalAmountBudgetedByUser(user);
        return ResponseEntity.ok(totalAmountBudgeted);
    }
}

