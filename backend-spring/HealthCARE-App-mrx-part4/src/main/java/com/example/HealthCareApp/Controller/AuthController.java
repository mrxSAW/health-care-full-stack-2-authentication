package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.DTO.Auth.AuthResponse;
import com.example.HealthCareApp.DTO.Auth.LoginRequest;
import com.example.HealthCareApp.DTO.Auth.RegisterRequest;
import com.example.HealthCareApp.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }
}



