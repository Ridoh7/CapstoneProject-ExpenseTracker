package com.Ridoh.ExpenseTrackerApplication.DTO;

import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSummaryDto {
    private double totalAmount;
    private Category category;
}
