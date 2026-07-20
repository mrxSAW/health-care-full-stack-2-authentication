package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.DTO.User.UserGetDTO;
import com.example.HealthCareApp.DTO.User.UserPostDTO;
import com.example.HealthCareApp.DTO.User.UserUpdateDTO;
import com.example.HealthCareApp.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserGetDTO create(@Valid @RequestBody UserPostDTO dto) {
        return service.save(dto);
    }

    @GetMapping
    public Page<UserGetDTO> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public UserGetDTO get(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public UserGetDTO update(@PathVariable int id, @Valid @RequestBody UserUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
