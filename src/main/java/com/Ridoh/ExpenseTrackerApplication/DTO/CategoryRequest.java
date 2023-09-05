package com.Ridoh.ExpenseTrackerApplication.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryRequest {
    private String name;
    private String description;
    private Long userId;
}
