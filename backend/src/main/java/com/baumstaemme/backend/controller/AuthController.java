package com.baumstaemme.backend.controller;

import com.baumstaemme.backend.dto.LoginRequest;
import com.baumstaemme.backend.entity.User;
import com.baumstaemme.backend.repository.UserRepository;
import com.baumstaemme.backend.services.AuthService;
import com.baumstaemme.backend.services.PasswordHashService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordHashService passwordHashService;
    private final AuthService authService;

    public AuthController(UserRepository userRepo, PasswordHashService passwordHashService, AuthService authService) {
        this.userRepo = userRepo;
        this.passwordHashService = passwordHashService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(">>> Username: " + loginRequest.getUsername());
        System.out.println(">>> Password: " + loginRequest.getPassword());
        Optional<User> user = userRepo.findByUsername(loginRequest.getUsername());
        if (user.isPresent() && authService.checkPassword(user.get(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest registerRequest) {
        System.out.println(">>> Registering User: " + registerRequest.getUsername());
        if (userRepo.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordHashService.hashPassword(registerRequest.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

}
