package com.Ridoh.ExpenseTrackerApplication.Controller;
import com.Ridoh.ExpenseTrackerApplication.Email.Service.EmailService;
import com.Ridoh.ExpenseTrackerApplication.Email.dto.EmailDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@Tag(
        name = "Email Service REST APIs/Endpoint",
        description = "Endpoints for Manipulating Email Services"
)
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String sendSimpleEmail(@RequestBody EmailDetails details) {
        return
                emailService.sendSimpleEmail(details);
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        return emailService.sendMailWithAttachment(details);
    }

}
