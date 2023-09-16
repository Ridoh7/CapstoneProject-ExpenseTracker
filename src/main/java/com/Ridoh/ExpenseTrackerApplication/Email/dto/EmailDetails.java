package com.Ridoh.ExpenseTrackerApplication.Email.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {
    private String recipient;
    private String messageBody;
    private String attachment;
    private String subject;
}
