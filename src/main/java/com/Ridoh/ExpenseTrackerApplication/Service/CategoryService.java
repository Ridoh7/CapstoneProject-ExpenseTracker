package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.CategoryRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;

import java.util.Date;
import java.util.List;

public interface CategoryService {

    Response createCategory(CategoryRequest categoryRequest);

    List<Category> getAllPredefinedCategories();

    Category getCategoryByName(String name);

    List<Category> getCategoriesByUserExpenses(User user);

    List<Category> getCategoriesByUserExpensesAndDateRange(User user, Date startDate, Date endDate);

    List<Category> getCategoriesByBudgetsAmountGreaterThan(Double amount);

    List<Category> getCategoriesByBudgetsCategoryUser(User user);

}

