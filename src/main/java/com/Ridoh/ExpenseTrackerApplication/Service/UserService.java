package com.Ridoh.ExpenseTrackerApplication.Service;


import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.DTO.UserRequest;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<Response>allUser();
    Response updateUser(String username, UserRequest userRequest);
    ResponseEntity<Response>fetchUser(Long userId);
    ResponseEntity<Response>nameEnquiry(String username, String email);
}
