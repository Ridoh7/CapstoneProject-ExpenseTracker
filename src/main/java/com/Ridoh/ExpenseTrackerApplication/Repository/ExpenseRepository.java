package com.Ridoh.ExpenseTrackerApplication.Repository;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Expense;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByCategory(Category category);
    List<Expense> findByUserAndCategory(User user, Category category);
    List<Expense> findByUserAndDateBetween(User user, Date startDate, Date endDate);
    List<Expense> findByUserAndAmountGreaterThan(User user, Double amount);
    List<Expense> findByUserOrderByDateDesc(User user);
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user")
    Double sumExpensesAmountByUser(@Param("user") User user);
}
