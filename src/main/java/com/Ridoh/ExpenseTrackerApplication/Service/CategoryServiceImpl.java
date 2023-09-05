package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.CategoryRequest;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.CategoryRepository;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Response createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .id(categoryRequest.getUserId())
                .description(categoryRequest.getDescription()).build();

        categoryRepository.save(category);

        return Response.builder()
                .responseCode(ResponseUtil.CATEGORY_SUCCESS_CODE)
                .responseMessage(ResponseUtil.CATEGORY_SUCCESS_MESSAGE)
                .build();

    }

    public List<Category> getAllPredefinedCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getCategoriesByUserExpenses(User user) {
        return categoryRepository.findByExpensesUser(user);
    }

    @Override
    public List<Category> getCategoriesByUserExpensesAndDateRange(User user, Date startDate, Date endDate) {
        return categoryRepository.findByExpensesUserAndExpensesDateBetween(user, startDate, endDate);
    }

    @Override
    public List<Category> getCategoriesByBudgetsAmountGreaterThan(Double amount) {
        return categoryRepository.findByBudgetsAmountGreaterThan(amount);
    }

    @Override
    public List<Category> getCategoriesByBudgetsCategoryUser(User user) {
        return categoryRepository.findByBudgetsCategoryUser(user);
    }
}

