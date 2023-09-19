package com.Ridoh.ExpenseTrackerApplication.Controller;
import com.Ridoh.ExpenseTrackerApplication.DTO.AdminDto;
import com.Ridoh.ExpenseTrackerApplication.DTO.AuthResponse;
import com.Ridoh.ExpenseTrackerApplication.DTO.LoginDto;
import com.Ridoh.ExpenseTrackerApplication.DTO.RegisterDto;
import com.Ridoh.ExpenseTrackerApplication.Security.JwtTokenProvider;
import com.Ridoh.ExpenseTrackerApplication.ServiceInterface.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Tag(name = "User Account Management APIs")
public class AuthController {
    private AuthService authService;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    @Operation(
            summary = "create new user account",
            description = "registering new user account on the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);

    }

    @Operation(
            summary = "create Admin account",
            description = "registering an admin account on the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/registerAdmin")
    public ResponseEntity<?> register(@RequestBody AdminDto adminDto){
        return new ResponseEntity<>(authService.registerAdmin(adminDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login to the homepage",
            description = "Giving authorization and permission to registered users and admins"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
        AuthResponse authResponse=authService.login(loginDto);
        return ResponseEntity.ok(authResponse);
    }
}
