package com.spring.blog.controller;

import com.spring.blog.dto.AuthResponse;
import com.spring.blog.dto.LoginRequest;
import com.spring.blog.dto.RegisterRequest;
import com.spring.blog.model.User;
import com.spring.blog.security.JwtUtil;
import com.spring.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank() ||
            request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Email and password are required"));
        }

        String email = request.getEmail().trim().toLowerCase();
        if (userService.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "User with this email already exists"));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(request.getPassword());
        User savedUser = userService.saveUser(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(token, savedUser.getEmail(), "Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Invalid email or password"));
        }
        // Normalize email to match registration (lowercase)
        String email = loginRequest.getEmail().trim().toLowerCase();
        var userOpt = userService.findByEmail(email);

        if (userOpt.isEmpty()) {
            log.info("Login failed: user not found (check if email exists in DB after registration)");
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Invalid email or password"));
        }

        User user = userOpt.get();
        String storedPassword = user.getPassword();
        boolean passwordValid = storedPassword != null && passwordEncoder.matches(loginRequest.getPassword(), storedPassword);

        if (storedPassword == null) {
            log.warn("Login failed: stored password is null for user - re-register may be needed");
        } else if (!passwordValid) {
            log.info("Login failed: password does not match stored hash");
        }

        if (!passwordValid) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Invalid email or password"));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), "Login successful. Welcome back!"));
    }
}
