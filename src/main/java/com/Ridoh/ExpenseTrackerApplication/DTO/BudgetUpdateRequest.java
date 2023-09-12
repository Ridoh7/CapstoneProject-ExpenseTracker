package com.Ridoh.ExpenseTrackerApplication.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetUpdateRequest {
    private Double newAmount;
    private Long categoryId;
    private Long userId;
}

