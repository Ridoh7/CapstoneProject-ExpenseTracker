package com.Ridoh.ExpenseTrackerApplication.Service;
import com.Ridoh.ExpenseTrackerApplication.DTO.*;
import com.Ridoh.ExpenseTrackerApplication.Entity.Category;
import com.Ridoh.ExpenseTrackerApplication.Entity.Role;
import com.Ridoh.ExpenseTrackerApplication.Entity.User;
import com.Ridoh.ExpenseTrackerApplication.Repository.RoleRepository;
import com.Ridoh.ExpenseTrackerApplication.Repository.UserRepo;
import com.Ridoh.ExpenseTrackerApplication.Security.CustomUserDetailsService;
import com.Ridoh.ExpenseTrackerApplication.Security.JwtTokenProvider;
import com.Ridoh.ExpenseTrackerApplication.Util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    @Autowired
    public AuthServiceImpl(UserRepo userRepo, RoleRepository roleRepository, AuthenticationManager authenticationManager,
                           AuthenticationManager authenticationManager1, CustomUserDetailsService customUserDetailsService,
                           CategoryService categoryService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager1;
        this.categoryService = categoryService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
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

        User user = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        User savedUser = userRepo.save(user);

        return Response.builder()
                .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .email(savedUser.getEmail())
                        .username(savedUser.getUsername())
                        .build()))
                .build();
    }

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

        return Response.builder()
                .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountName(savedAdmin.getFirstName()+" "+ savedAdmin.getLastName())
                        .build()))
                .build();
    }
}
