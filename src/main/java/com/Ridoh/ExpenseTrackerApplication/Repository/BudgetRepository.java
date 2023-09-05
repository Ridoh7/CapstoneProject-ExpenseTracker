package com.Ridoh.ExpenseTrackerApplication.Repository;
import com.Ridoh.ExpenseTrackerApplication.Entity.Budget;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // findByCategory: Retrieves a list of budgets for a specific category.
    List<Budget> findByCategory(Category category);

    // findByCategory: Retrieves a list of budgets for a specific category.
    List<Budget> findByCategoryAndAmountGreaterThan(Category category, Double amount);

    // findByCategoryUser: Retrieves a list of budgets for categories associated with a specific user.
    List<Budget> findByCategoryUser(User user);

    // findByCategoryAndAmountGreaterThan: Retrieves a list of budgets for a specific category with
    // amounts greater than a specified value.
    List<Budget> findByCategoryUserAndCategoryExpensesDateBetween(User user, Date startDate, Date endDate);

    //    findByCategoryUserOrderByAmountDesc: Retrieves a list of budgets for categories associated with a specific
    //    user, ordered by amount in descending order.
    List<Budget> findByCategoryUserOrderByAmountDesc(User user);

    List<Budget> findByCategoryId(Long categoryId);

    //sumAmountByUser: Calculates the total amount of budget for a specific user.
    @Query("SELECT SUM(b.amount) FROM Budget b WHERE b.user = :user")
    Double sumBudgetedAmountByUser(@Param("user") User user);
}

