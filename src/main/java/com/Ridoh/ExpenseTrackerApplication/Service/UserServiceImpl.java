package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.Data;
import com.Ridoh.ExpenseTrackerApplication.DTO.Response;
import com.Ridoh.ExpenseTrackerApplication.DTO.UserRequest;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<Response> allUser() {
        List<User>usersList=userRepo.findAll();
        List<Response>response=new ArrayList<>();

        for (User user: usersList) {
            response.add(Response.builder()
                    .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                    .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .email(user.getEmail())
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

            return Response.builder()
                    .responseCode(ResponseUtil.USER_UPDATE_CODE)
                    .responseMessage(ResponseUtil.USER_UPDATE_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .email(existingUser.getEmail())
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
                .responseCode(ResponseUtil.USER_EXIST_CODE)
                .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountName(user.getFirstName()+" "+user.getLastName())
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



