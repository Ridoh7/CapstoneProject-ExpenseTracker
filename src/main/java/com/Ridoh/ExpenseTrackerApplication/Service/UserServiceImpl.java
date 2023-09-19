package com.Ridoh.ExpenseTrackerApplication.Service;

import com.Ridoh.ExpenseTrackerApplication.DTO.Data;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.DTO.UserRequest;
import com.Ridoh.ExpenseTrackerApplication.Email.Service.EmailService;
import com.Ridoh.ExpenseTrackerApplication.Email.dto.EmailDetails;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.ServiceInterface.UserService;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    @Autowired
    EmailService emailService;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<Response> allUser() {
        List<User>usersList=userRepo.findAll();

        List<Response>response=new ArrayList<>();

        for (User user: usersList) {
            response.add(Response.builder()
                    .responseCode(ResponseUtil.USER_FETCH_LIST_CODE)
                    .responseMessage(ResponseUtil.USER_FETCH_LIST_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .email(user.getEmail())
                            .username(user.getUsername())
                            .accountName(user.getFirstName()+" "+user.getLastName())
                            .build()))
                    .build());

        }
        return response;
    }

    @Override
    public Response updateUser(String username, UserRequest userRequest) {
        Optional<User> optionalUser = Optional.ofNullable(userRepo.findByUsername(username));

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(userRequest.getFirstName());
            existingUser.setLastName(userRequest.getLastName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setUsername(userRequest.getUsername());
            existingUser.setPhoneNumber(userRequest.getPhoneNumber());
            existingUser.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
            existingUser.setAddress(userRequest.getAddress());

            userRepo.save(existingUser);

            EmailDetails emailDetails= EmailDetails.builder()
                    .recipient(existingUser.getEmail())
                    .subject("Expense Tracker Profile Update")
                    .messageBody("Dear " + existingUser.getUsername()+ ": Your profile has been successfully updated.\n Your details are: " +
                            "\nUsername: " + existingUser.getUsername()+" "+
                            "\nEmail: " + existingUser.getEmail()+" " +
                            "\nAccount Name: " + existingUser.getFirstName()+ " " + existingUser.getLastName())
                    .build();

            emailService.sendSimpleEmail(emailDetails);


            return Response.builder()
                    .responseCode(ResponseUtil.USER_UPDATE_CODE)
                    .responseMessage(ResponseUtil.USER_UPDATE_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .email(existingUser.getEmail())
                            .username(existingUser.getUsername())
                            .accountName(existingUser.getFirstName() + " " + existingUser.getLastName())
                            .build()))
                    .build();
        } else {

            return Response.builder()
                    .responseCode(ResponseUtil.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }

    }

    @Override
    public ResponseEntity<Response> fetchUser(Long userId) {
        if (!userRepo.existsById(userId)){
            return new ResponseEntity<>(Response.builder()
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
        }
        User user=userRepo.findById(userId).get();
        return new ResponseEntity<>(Response.builder()
                .responseCode(ResponseUtil.USER_ID_FOUND_CODE)
                .responseMessage(ResponseUtil.USERID_FOUND_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountName(user.getFirstName()+" "+user.getLastName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()))
                .build(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> nameEnquiry(String email, String username) {
        boolean isAccountExist= userRepo.existsByEmailOrUsername(email,username);
        if (!isAccountExist) {
            return new ResponseEntity<>(Response.builder()
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

            User user=userRepo.findByUsername(username);
            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtil.USER_EXIST_CODE)
                    .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountName(user.getFirstName()+" "+user.getLastName())
                            .build()))
                    .build(),HttpStatus.FOUND);
    }
}



