package com.Ridoh.ExpenseTrackerApplication.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetRequest {
    private Double amount;
    private Long categoryId;
    private Long userId;
}
