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

    // findByName: Retrieves a category by its name.
    Category findByName(String name);

    // findByExpensesUser: Retrieves a list of categories associated with expenses for a specific user.
    List<Category> findByExpensesUser(User user);

    // findByExpensesUserAndExpensesDateBetween: Retrieves a list of categories associated with
    // expenses for a specific user within a specified date range.
    List<Category> findByExpensesUserAndExpensesDateBetween(User user, Date startDate, Date endDate);

    // findByBudgetsAmountGreaterThan: Retrieves a list of categories associated with budgets having
    // amounts greater than a specified value.
    List<Category> findByBudgetsAmountGreaterThan(Double amount);

    // findByBudgetsCategoryUser: Retrieves a list of categories associated with budgets for a specific user.
    List<Category> findByBudgetsCategoryUser(User user);

    Category findById(AuthResponse login);
}
