package com.baumstaemme.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepo userRepo;
    private final PasswordHashUtil passwordHashUtil;
    private final AuthUtil authUtil;

    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final int MINUTES = 60;

    public AuthController(UserRepo userRepo, PasswordHashUtil passwordHashUtil, AuthUtil authUtil) {
        this.userRepo = userRepo;
        this.passwordHashUtil = passwordHashUtil;
        this.authUtil = authUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        System.out.println(">>> Username: " + loginRequestDto.getUsername());
        System.out.println(">>> Password: " + loginRequestDto.getPassword());
        Optional<User> userOptional = userRepo.findByUsername(loginRequestDto.getUsername());

        Map<String, String> response = new HashMap<>();

        if (userOptional.isPresent() && authUtil.checkPassword(userOptional.get(), loginRequestDto.getPassword())) {
            User user = userOptional.get();
            response.put("status", "Login successful");
            session.setAttribute(USER_SESSION_KEY, user);
            System.out.println(session.getAttribute(USER_SESSION_KEY));
            return ResponseEntity.ok(response);
        }else {
            response.put("status", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequestDto registerRequest, HttpSession session) {
        System.out.println(">>> Registering User: " + registerRequest.getUsername());

        Map<String, String> response = new HashMap<>();

        if (userRepo.existsByUsername(registerRequest.getUsername())) {
            response.put("status", "User already exists");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordHashUtil.hashPassword(registerRequest.getPassword()));
        userRepo.save(user);
        Optional<User> optinalSessionUser = userRepo.findByUsername(registerRequest.getUsername());
        User sessionUser = optinalSessionUser.get();
        session.setAttribute(USER_SESSION_KEY,sessionUser);
        System.out.println(session.getAttribute(USER_SESSION_KEY));
        System.out.println(session);

        response.put("status", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        System.out.println(">>> Logout User: " + session.getId());
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> responseDto = UserUtil.createResponseDto(users);
        return ResponseEntity.ok(responseDto);
    }

}
