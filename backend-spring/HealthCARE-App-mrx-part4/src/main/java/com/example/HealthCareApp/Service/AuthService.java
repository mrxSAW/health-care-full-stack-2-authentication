package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.Auth.AuthResponse;
import com.example.HealthCareApp.DTO.Auth.LoginRequest;
import com.example.HealthCareApp.DTO.Auth.RegisterRequest;
import com.example.HealthCareApp.Entity.Role;
import com.example.HealthCareApp.Entity.User;
import com.example.HealthCareApp.Repository.UserRepository;
import com.example.HealthCareApp.Security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        User existing = repo.findByEmail(request.getEmail());

        if (existing != null) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() == null) {
            user.setRole(Role.PATIENT);
        } else {
            user.setRole(request.getRole());
        }

        User saved = repo.save(user);

        String token = jwtService.generateToken(saved);

        return new AuthResponse(token, saved.getId(), saved.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        authenticationManager.authenticate(authToken);

        User user = repo.findByEmail(request.getEmail());

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getId(), user.getRole());
    }
}
