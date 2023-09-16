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
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Budget findByUserAndCategory(User user, Category category);
    List<Budget> findByCategoryAndAmountGreaterThan(Category category, Double amount);
    List<Budget> findByCategoryUser(User user);
    List<Budget> findByCategoryUserAndCategoryExpensesDateBetween(User user, Date startDate, Date endDate);
    List<Budget> findByCategoryUserOrderByAmountDesc(User user);
    List<Budget> findByCategoryId(Long categoryId);
    @Query("SELECT SUM(b.amount) FROM Budget b WHERE b.user = :user")
    Double sumBudgetedAmountByUser(@Param("user") User user);

    Budget findByCategoryAndUser(Category predefinedCategory, User user);
}

