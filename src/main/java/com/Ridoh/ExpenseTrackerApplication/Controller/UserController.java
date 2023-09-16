package com.Ridoh.ExpenseTrackerApplication.Controller;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.DTO.UserRequest;
import com.Ridoh.ExpenseTrackerApplication.Service.UserService;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("{username}")
    public ResponseEntity<Response> updateUser(@PathVariable String username,
                                               @RequestBody UserRequest userRequest) {
        Response response = userService.updateUser(username, userRequest);

        if (response.getResponseCode().equals(ResponseUtil.USER_SUCCESS_CODE)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (response.getResponseCode().equals(ResponseUtil.USER_NOT_FOUND_CODE)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/AllUsers")
    public List<Response> allUser() {
        return userService.allUser();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response> fetchUser(@PathVariable(name = "userId") Long userId) {
        return userService.fetchUser(userId);
    }

    @GetMapping("/nameEnquiry")
    public ResponseEntity<Response> nameEnquiry(@RequestParam(name = "username") String username, String email) {
        return userService.nameEnquiry(username, email);
    }
}