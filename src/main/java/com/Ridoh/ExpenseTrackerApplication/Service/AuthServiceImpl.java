package com.Ridoh.ExpenseTrackerApplication.Service;

import com.Ridoh.ExpenseTrackerApplication.DTO.*;
import com.Ridoh.ExpenseTrackerApplication.Email.Service.EmailService;
import com.Ridoh.ExpenseTrackerApplication.Email.dto.EmailDetails;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Role;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.RoleRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.Security.JwtTokenProvider;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo, RoleRepository roleRepository, AuthenticationManager authenticationManager,
                           CategoryService categoryService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, EntityManager entityManager, EmailService emailService) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.categoryService = categoryService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public AuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication);
        User user =userRepo.findByUsername(loginDto.getUsername());

        List<Category> predefinedCategories = categoryService.getAllPredefinedCategories();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return AuthResponse.builder()
                .token(jwtTokenProvider.generateToken(authentication))
                .userId(user.getId())
                .predefinedCategories(categoryService.getAllPredefinedCategories())
                .build();
    }
    @Transactional
    @Override
    public Response register(RegisterDto registerDto) {
        boolean isUserExist = userRepo.existsByEmailOrUsername(registerDto.getEmail(), registerDto.getUsername());
        if (isUserExist) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_EXIST_CODE)
                    .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                    .data(null)
                    .build();

        }

        Role role = roleRepository.findByRoleName("USER").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        role=entityManager.merge(role);

        User user = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        User savedUser = userRepo.save(user);

        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Expense Tracker Profile Creation")
                .messageBody("Dear " + user.getUsername()+ ": Your profile has been successfully created.\n Your profile details are: " +
                        "\nUsername: " + user.getUsername()+" "+
                        "\nEmail: " + user.getEmail()+" " +
                        "\nUser ID: " + user.getId()+" "+
                        "\nThank you for using Expense Tracker Application that helps in managing your budget and expenses")
                .build();

        emailService.sendSimpleEmail(emailDetails);



        return Response.builder()
                .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .email(savedUser.getEmail())
                        .username(savedUser.getUsername())
                        .build()))
                .build();
    }

    @Transactional
    @Override
    public Response registerAdmin(AdminDto adminDto) {
        boolean isUserExist=userRepo.existsByEmailOrUsername(adminDto.getEmail(), adminDto.getUsername());
        if (isUserExist) {
            return Response.builder()
                    .responseCode(ResponseUtil.USER_EXIST_CODE)
                    .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                    .data(null)
                    .build();

        }

        Role role = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = User.builder()
                .username(adminDto.getUsername())
                .email(adminDto.getEmail())
                .password(passwordEncoder.encode(adminDto.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        User savedAdmin=userRepo.save(user);

        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Expense Tracker Profile Creation")
                .messageBody("Dear " + user.getUsername()+ ": Your profile has been successfully created.\n Your profile details are: " +
                        "\nUsername: " + user.getUsername()+" "+
                        "\nEmail: " + user.getEmail()+" " +
                        "\nUser ID: " + user.getId()+" "+
                        "\nThank you for using Expense Tracker Application that helps in managing your budget and expenses")
                .build();

        emailService.sendSimpleEmail(emailDetails);


        return Response.builder()
                .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountName(savedAdmin.getFirstName()+" "+ savedAdmin.getLastName())
                        .build()))
                .build();
    }
}
