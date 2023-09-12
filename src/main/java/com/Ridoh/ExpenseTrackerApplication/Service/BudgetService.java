package com.Ridoh.ExpenseTrackerApplication.Service;

import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetResponse;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetUpdateRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Budget;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Date;
import java.util.List;

public interface BudgetService {

    BudgetResponse createBudget(BudgetRequest budgetRequest) throws ChangeSetPersister.NotFoundException;

    BudgetResponse updateBudget(BudgetUpdateRequest budgetUpdateRequest) throws ChangeSetPersister.NotFoundException;

    List<BudgetResponse> getBudgetsByCategory(Long categoryId);

    List<Budget> getBudgetsByCategoryAndAmountGreaterThan(Category category, Double amount);

    List<Budget> getBudgetsByCategoryUser(User user);

    List<Budget> getBudgetsByCategoryUserAndCategoryExpensesDateBetween(User user, Date startDate, Date endDate);

    List<Budget> getBudgetsByCategoryUserOrderByAmountDesc(User user);

    Double getTotalAmountBudgetedByUser(User user);
}

