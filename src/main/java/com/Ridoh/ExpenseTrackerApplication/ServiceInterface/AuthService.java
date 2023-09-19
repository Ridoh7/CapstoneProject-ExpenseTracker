package com.Ridoh.ExpenseTrackerApplication.ServiceInterface;

import com.Ridoh.ExpenseTrackerApplication.DTO.*;

public interface AuthService {
    AuthResponse login (LoginDto loginDto);

    Response register (RegisterDto registerDto);

    Response registerAdmin(AdminDto adminDto);
}
