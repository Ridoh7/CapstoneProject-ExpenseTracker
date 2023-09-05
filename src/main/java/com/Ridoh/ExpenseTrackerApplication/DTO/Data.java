package com.Ridoh.ExpenseTrackerApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Data {
    public String accountName;
    public String username;
    public String email;


}
