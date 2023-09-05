package com.Ridoh.ExpenseTrackerApplication.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
        @Schema(
                name = "username "
        )
        private String username;
        @Schema(
                name = "email"
        )
        private String email;
        @Schema(
                name = "password"
        )
        private String password;
}
