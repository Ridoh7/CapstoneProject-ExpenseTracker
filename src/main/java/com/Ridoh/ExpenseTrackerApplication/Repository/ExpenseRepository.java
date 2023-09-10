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

    // findByUser: Retrieves a list of expenses for a specific user.
    List<Expense> findByUser(User user);

    // findByCategory: Retrieves a list of expenses for a specific category.
    List<Expense> findByCategory(Category category);

    // findByUserAndCategory: Retrieves a list of expenses for a specific user and category.
    List<Expense> findByUserAndCategory(User user, Category category);

    //findByUserAndDateBetween: Retrieves a list of expenses for a specific user within a specified date range.
    List<Expense> findByUserAndDateBetween(User user, Date startDate, Date endDate);

    //findByUserAndAmountGreaterThan: Retrieves a list of expenses for a specific user with amounts greater than a specified value.
    List<Expense> findByUserAndAmountGreaterThan(User user, Double amount);

    //findByUserOrderByDateDesc: Retrieves a list of expenses for a specific user, ordered by date in descending order.
    List<Expense> findByUserOrderByDateDesc(User user);

    //sumAmountByUser: Calculates the total amount of expenses for a specific user.
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user")
    Double sumExpensesAmountByUser(@Param("user") User user);
}
