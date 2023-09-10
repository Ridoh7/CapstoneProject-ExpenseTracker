package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetResponse;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Budget;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.BudgetRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.CategoryRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.ExpenseRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserRepo userRepo;
    private final CategoryRepository categoryRepository;


    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepo userRepo, UserRepo userRepo1,
                             CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepo = userRepo1;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Response createBudget(BudgetRequest budgetRequest) throws ChangeSetPersister.NotFoundException {
        User user = userRepo.findById(budgetRequest.getUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Category predefinedCategory = categoryRepository.findById(budgetRequest.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Predefined Category not found"));

        String categoryDescription= predefinedCategory.getDescription();

        Budget budget = Budget.builder()
                .amount(budgetRequest.getAmount())
                .user(user)
                .category(predefinedCategory)
                .categoryDescription(categoryDescription)
                .build();

        Budget savedBudget = budgetRepository.save(budget);

        return Response.builder()
                .responseCode(ResponseUtil.BUDGET_SUCCESS_CODE)
                .responseMessage(ResponseUtil.BUDGET_SUCCESS_MESSAGE)
                .build();
    }


    @Override
    public List<BudgetResponse> getBudgetsByCategory(Long categoryId)  {
        Category category = categoryRepository.findById(categoryId).orElseThrow(PersistenceException::new);

        List<BudgetResponse> budget = budgetRepository.findByCategoryId(categoryId).stream().map(budgetList->BudgetResponse.builder()
                .category(budgetList.getCategory())
                .userId(budgetList.getId())
                .amount(budgetList.getAmount())
                .build()).collect(Collectors.toList());
        return budget;
    }


    @Override
    public List<Budget> getBudgetsByCategoryAndAmountGreaterThan(Category category, Double amount) {
        return budgetRepository.findByCategoryAndAmountGreaterThan(category, amount);
    }

    @Override
    public List<Budget> getBudgetsByCategoryUser(User user) {
        return budgetRepository.findByCategoryUser(user);
    }

    @Override
    public List<Budget> getBudgetsByCategoryUserAndCategoryExpensesDateBetween(User user, Date startDate, Date endDate) {
        return budgetRepository.findByCategoryUserAndCategoryExpensesDateBetween(user, startDate, endDate);
    }


    @Override
    public List<Budget> getBudgetsByCategoryUserOrderByAmountDesc(User user) {
        return budgetRepository.findByCategoryUserOrderByAmountDesc(user);
    }

    @Override
    public Double getTotalAmountBudgetedByUser(User user) {
        Double totalAmount = budgetRepository.sumBudgetedAmountByUser(user);
        return totalAmount != null ? totalAmount : 0.0;
    }
}

