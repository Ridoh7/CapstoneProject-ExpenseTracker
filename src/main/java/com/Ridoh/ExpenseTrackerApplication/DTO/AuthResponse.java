package com.Ridoh.ExpenseTrackerApplication.DTO;

import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private List<Category> predefinedCategories;
}
