package com.Ridoh.ExpenseTrackerApplication.DTO;

import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse {

    private Double amount;
    private Long category;
    private Long userId;
    private String responseCode;
    private String responseMessage;


}
