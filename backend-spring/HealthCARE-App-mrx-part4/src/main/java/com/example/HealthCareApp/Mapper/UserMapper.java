package com.example.HealthCareApp.Mapper;

import com.example.HealthCareApp.DTO.User.UserGetDTO;
import com.example.HealthCareApp.DTO.User.UserPostDTO;
import com.example.HealthCareApp.DTO.User.UserUpdateDTO;
import com.example.HealthCareApp.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

    @Mapper(componentModel = "spring")
    public interface UserMapper {

        User toEntity(UserPostDTO dto);

        UserGetDTO toGetDTO(User user);

        void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
    }





