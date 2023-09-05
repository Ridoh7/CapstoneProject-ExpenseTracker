package com.Ridoh.ExpenseTrackerApplication.Service;

import com.Ridoh.ExpenseTrackerApplication.DTO.ExpenseRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Expense;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.CategoryRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.ExpenseRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final UserRepo userRepo;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepo userRepo, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepo = userRepo;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Response createExpense(ExpenseRequest expenseRequest) throws
            ChangeSetPersister.NotFoundException {

        User user= userRepo.findById(expenseRequest.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        Category predefinedCategory = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Predefined category not found"));

        String categoryDescription= predefinedCategory.getDescription();

        Expense expense = Expense.builder()
                .amount(expenseRequest.getAmount())
                .categoryDescription(categoryDescription)
                .category(predefinedCategory)
                .user(user)
                .build();

        expenseRepository.save(expense);

        return Response.builder()
                .responseCode(ResponseUtil.EXPENSE_SUCCESS_CODE)
                .responseMessage(ResponseUtil.EXPENSE_SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    @Override
    public List<Expense> getExpensesByCategory(Category category) {
        return expenseRepository.findByCategory(category);
    }

    @Override
    public List<Expense> getExpensesByUserAndCategory(User user, Category category) {
        return expenseRepository.findByUserAndCategory(user, category);
    }

    @Override
    public List<Expense> getExpensesByUserAndDateRange(User user, Date startDate, Date endDate) {
        return expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    @Override
    public List<Expense> getExpensesByUserAndAmountGreaterThan(User user, Double amount) {
        return expenseRepository.findByUserAndAmountGreaterThan(user, amount);
    }

    @Override
    public List<Expense> getExpensesByUserOrderByDateDesc(User user) {
        return expenseRepository.findByUserOrderByDateDesc(user);
    }

    @Override
    public Double getTotalAmountSpentByUser(User user) {
        Double totalAmount = expenseRepository.sumExpensesAmountByUser(user);
        return totalAmount != null ? totalAmount : 0.0;
    }
}

