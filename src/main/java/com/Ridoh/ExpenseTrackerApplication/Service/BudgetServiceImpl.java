package com.Ridoh.ExpenseTrackerApplication.Service;

import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetResponse;
import com.Ridoh.ExpenseTrackerApplication.DTO.BudgetUpdateRequest;
import com.Ridoh.ExpenseTrackerApplication.Email.Service.EmailService;
import com.Ridoh.ExpenseTrackerApplication.Email.dto.EmailDetails;
import com.Ridoh.ExpenseTrackerApplication.Entity.Budget;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.BudgetRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.CategoryRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.ServiceInterface.BudgetService;
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
    EmailService emailService;


    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepo userRepo, UserRepo userRepo1,
                             CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepo = userRepo1;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BudgetResponse createBudget(BudgetRequest budgetRequest) throws ChangeSetPersister.NotFoundException {
        User user = userRepo.findById(budgetRequest.getUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Category predefinedCategory = categoryRepository.findById(budgetRequest.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Predefined Category not found"));

        Budget existingBudget =budgetRepository.findByUserAndCategory(user, predefinedCategory );
        if (existingBudget!=null){
            return BudgetResponse.builder()
                    .responseCode(ResponseUtil.BUDGET_EXIST_CODE)
                    .responseMessage(ResponseUtil.BUDGET_EXIST_MESSAGE)
                    .userId(budgetRequest.getUserId())
                    .category(budgetRequest.getCategoryId())
                    .amount(budgetRequest.getAmount()).build();
        }

        String categoryDescription= predefinedCategory.getDescription();

        Budget budget = Budget.builder()
                .amount(budgetRequest.getAmount())
                .user(user)
                .category(predefinedCategory)
                .categoryDescription(categoryDescription)
                .build();

        Budget savedBudget = budgetRepository.save(budget);

        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Expense Tracker Budget Creation")
                .messageBody("Dear " + user.getUsername()+ ": Your budget has been successfully created.\n Your budget details are: " +
                        "\nBalance: " + budget.getAmount()+" "+
                        "\nBudget Description: " + budget.getCategoryDescription()+" " +
                        "\nBudget categoryId: " + budgetRequest.getCategoryId())
                .build();

        emailService.sendSimpleEmail(emailDetails);

        return BudgetResponse.builder()
                .responseCode(ResponseUtil.BUDGET_SUCCESS_CODE)
                .responseMessage(ResponseUtil.BUDGET_SUCCESS_MESSAGE)
                .amount(budgetRequest.getAmount())
                .category(budgetRequest.getCategoryId())
                .userId(budgetRequest.getUserId())
                .build();
    }

    @Override
    public BudgetResponse updateBudget(BudgetUpdateRequest budgetUpdateRequest) throws ChangeSetPersister.NotFoundException {
        User user = userRepo.findById(budgetUpdateRequest.getUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Category predefinedCategory = categoryRepository.findById(budgetUpdateRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Predefined Category not found"));

        Budget existingBudget = budgetRepository.findByUserAndCategory(user, predefinedCategory);

        if (existingBudget == null) {
            return BudgetResponse.builder()
                    .responseCode(ResponseUtil.BUDGET_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.BUDGET_NOT_FOUND_MESSAGE)
                    .build();
        }

        // Update the existing budget with the new amount
        Double currentAmount = existingBudget.getAmount();
        Double newAmount = budgetUpdateRequest.getNewAmount();
        existingBudget.setAmount(currentAmount + newAmount);
        budgetRepository.save(existingBudget);


        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Expense Tracker Budget Updating")
                .messageBody("Dear " + user.getUsername()+ ":Your budget has been successfully updated.\n Your budget details are: " +
                        "\nBalance: " + existingBudget.getAmount()+" "+
                        "\nBudget Description: " + existingBudget.getCategoryDescription()+" " +
                        "\nBudget categoryId: " + budgetUpdateRequest.getCategoryId())
                .build();

        emailService.sendSimpleEmail(emailDetails);

        return BudgetResponse.builder()
                .responseCode(ResponseUtil.BUDGET_UPDATE_CODE)
                .responseMessage(ResponseUtil.BUDGET_UPDATE_MESSAGE)
                .amount(existingBudget.getAmount())
                .category(existingBudget.getCategory().getId())
                .userId(existingBudget.getUser().getId())
                .build();
    }




    @Override
    public List<BudgetResponse> getBudgetsByCategory(Long categoryId)  {
        Category category = categoryRepository.findById(categoryId).orElseThrow(PersistenceException::new);

        List<BudgetResponse> budget = budgetRepository.findByCategoryId(categoryId).stream().map(budgetList->BudgetResponse.builder()
                .category(budgetList.getCategory().getId())
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

