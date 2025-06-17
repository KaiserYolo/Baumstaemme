package com.baumstaemme.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public AuthController(UserRepo userRepo, PasswordHashUtil passwordHashUtil, AuthUtil authUtil) {
        this.userRepo = userRepo;
        this.passwordHashUtil = passwordHashUtil;
        this.authUtil = authUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println(">>> Username: " + loginRequestDto.getUsername());
        System.out.println(">>> Password: " + loginRequestDto.getPassword());
        Optional<User> user = userRepo.findByUsername(loginRequestDto.getUsername());

        Map<String, String> response = new HashMap<>();

        if (user.isPresent() && authUtil.checkPassword(user.get(), loginRequestDto.getPassword())) {
            response.put("status", "Login successful");
            return ResponseEntity.ok(response);
        }else {
            response.put("status", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequestDto registerRequest) {
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

        response.put("status", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

}
