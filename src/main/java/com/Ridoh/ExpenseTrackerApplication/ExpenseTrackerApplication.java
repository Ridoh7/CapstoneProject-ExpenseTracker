package com.Ridoh.ExpenseTrackerApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Expense Tracker Application",
				description = "Ensuring Effective Expenses and Sustainable Budgeting",
				version = "v1.0",
				contact = @Contact(
						name = "Ridoh Lawal",
						email = "ridohlawal96@gmail.com",
						url=""
				),
				license = @License(
						name = "Expense Tracker Application",
						url = ""
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Expense Tracker Application",
				url = ""
		)
)
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

}
