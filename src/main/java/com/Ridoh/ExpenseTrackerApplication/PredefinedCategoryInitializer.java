package com.Ridoh.ExpenseTrackerApplication;

import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PredefinedCategoryInitializer<C> implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Autowired
    public PredefinedCategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        createPredefinedCategoryIfNotExists("Groceries", "Food and household items");
        createPredefinedCategoryIfNotExists("Transportation", "Travel expenses");
        createPredefinedCategoryIfNotExists("Entertainment", "Leisure and fun activities");
        createPredefinedCategoryIfNotExists("Utilities", "Monthly utility bills");
        createPredefinedCategoryIfNotExists("Shopping", "Retail therapy");
        createPredefinedCategoryIfNotExists("Health", "Medical and wellness expenses");
        createPredefinedCategoryIfNotExists("Education", "Educational courses and materials");
    }

    private void createPredefinedCategoryIfNotExists(String categoryName, String categoryDescription) {
        Category existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory == null) {
            Category category = new Category();
            category.setName(categoryName);
            category.setDescription(categoryDescription);
            categoryRepository.save(category);
        }
    }
}
