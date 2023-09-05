package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.ExpenseRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Expense;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

public interface ExpenseService {

    Response createExpense(ExpenseRequest expenseRequest) throws ChangeSetPersister.NotFoundException;

    List<Expense> getExpensesByUser(User user);

    List<Expense> getExpensesByCategory(Category category);

    List<Expense> getExpensesByUserAndCategory(User user, Category category);

    List<Expense> getExpensesByUserAndDateRange(User user, Date startDate, Date endDate);

    List<Expense> getExpensesByUserAndAmountGreaterThan(User user, Double amount);

    List<Expense> getExpensesByUserOrderByDateDesc(User user);

    Double getTotalAmountSpentByUser(User user);
}

