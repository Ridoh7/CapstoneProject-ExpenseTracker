package com.Ridoh.ExpenseTrackerApplication.DTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpenseRequest {
    private Double amount;
    private Long categoryId;
    private Long userId;
}
