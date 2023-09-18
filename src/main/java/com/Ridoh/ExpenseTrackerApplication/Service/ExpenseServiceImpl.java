package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.ExpenseRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Email.Service.EmailService;
import com.Ridoh.ExpenseTrackerApplication.Email.dto.EmailDetails;
import com.Ridoh.ExpenseTrackerApplication.Entity.Budget;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Expense;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.BudgetRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.CategoryRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.ExpenseRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import jakarta.persistence.EntityNotFoundException;
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
    private final BudgetServiceImpl budgetService;
    private final BudgetRepository budgetRepository;
    private final EmailService emailService;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepo userRepo, CategoryRepository
            categoryRepository, BudgetServiceImpl budgetService, BudgetRepository budgetRepository, EmailService emailService) {
        this.expenseRepository = expenseRepository;
        this.userRepo = userRepo;
        this.categoryRepository = categoryRepository;
        this.budgetService = budgetService;
        this.budgetRepository = budgetRepository;
        this.emailService = emailService;
    }

    @Override
    public Response createExpense(ExpenseRequest expenseRequest) throws
            ChangeSetPersister.NotFoundException {

        User user= userRepo.findById(expenseRequest.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        Category predefinedCategory = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Predefined category not found"));

        String categoryDescription= predefinedCategory.getDescription();

        Budget budget = budgetRepository.findByCategoryAndUser(predefinedCategory, user);
        if (budget == null) {
            return Response.builder()
                    .responseCode(ResponseUtil.BUDGET_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.BUDGET_NOT_FOUND_MESSAGE)
                    .build();
        }

        Double remainingBudget= budgetService.getTotalAmountBudgetedByUser(user);

        if (expenseRequest.getAmount()>remainingBudget){
            return Response.builder()
                    .responseCode(ResponseUtil.EXPENSE_WARNING_CODE)
                    .responseMessage(ResponseUtil.EXPENSE_WARNING_MESSAGE)
                    .build();
        }

        double newRemainingBudget= remainingBudget-expenseRequest.getAmount();
        budget.setAmount(newRemainingBudget);
        budgetRepository.save(budget);

        Expense expense = Expense.builder()
                .amount(expenseRequest.getAmount())
                .categoryDescription(categoryDescription)
                .category(predefinedCategory)
                .user(user)
                .build();

        expenseRepository.save(expense);
        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Expense Creation")
                .messageBody("Dear " + user.getUsername()+ ": Your expense has been successfully created.\n Your details are: " +
                        "\nExpense Amount: " + expense.getAmount()+" "+
                        "\nExpense Description: " + budget.getCategoryDescription()+" " +
                        "\nBudget categoryId: " + expenseRequest.getCategoryId()+" "+
                        "\nThank you for using Expense Tracker Application that helps in managing your budget and expenses")
                .build();

        emailService.sendSimpleEmail(emailDetails);


        return Response.builder()
                .responseCode(ResponseUtil.EXPENSE_SUCCESS_CODE)
                .responseMessage(ResponseUtil.EXPENSE_SUCCESS_MESSAGE)
                .build();
    }





    @Override
    public List<Expense> getExpensesByUser(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);

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
    public List<Expense> getExpensesByUserAndDateRange(User userId, Date startDate, Date endDate) {
        return expenseRepository.findByUserAndDateBetween(userId, startDate, endDate);
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

    @Override
    public Response deleteExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(()-> new EntityNotFoundException("Expense not Found" +
                "with ID: " + expenseId));
        expenseRepository.delete(expense);
        return Response.builder()
                .responseCode(ResponseUtil.EXPENSE_DELETE_CODE)
                .responseMessage(ResponseUtil.EXPENSE_DELETE_MESSAGE)
                .data(null).build();
    }
}


