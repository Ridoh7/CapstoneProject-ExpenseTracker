package com.Ridoh.ExpenseTrackerApplication.Email.Service;

import com.Ridoh.ExpenseTrackerApplication.Email.dto.EmailDetails;

public interface EmailService {

    String sendSimpleEmail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails details);
}
