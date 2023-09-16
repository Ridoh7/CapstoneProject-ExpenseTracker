package com.Ridoh.ExpenseTrackerApplication.Repository;

import com.Ridoh.ExpenseTrackerApplication.DTO.AuthResponse;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.PredefinedCategoryInitializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findByExpensesUser(User user);
    List<Category> findByExpensesUserAndExpensesDateBetween(User user, Date startDate, Date endDate);
    List<Category> findByBudgetsAmountGreaterThan(Double amount);
    List<Category> findByBudgetsCategoryUser(User user);
}
