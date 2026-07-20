package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.User.UserGetDTO;
import com.example.HealthCareApp.DTO.User.UserPostDTO;
import com.example.HealthCareApp.DTO.User.UserUpdateDTO;
import com.example.HealthCareApp.Entity.User;
import com.example.HealthCareApp.Mapper.UserMapper;
import com.example.HealthCareApp.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder, UserMapper mapper) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public UserGetDTO save(UserPostDTO dto) {
        User user = mapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = repo.save(user);
        return mapper.toGetDTO(saved);
    }

    public Page<UserGetDTO> getAll(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toGetDTO);
    }

    public UserGetDTO getById(int id) {
        return repo.findById(id)
                .map(mapper::toGetDTO)
                .orElse(null);
    }

    public UserGetDTO update(int id, UserUpdateDTO dto) {
        User user = repo.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        mapper.updateUserFromDTO(dto, user);



        User updated = repo.save(user);
        return mapper.toGetDTO(updated);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
