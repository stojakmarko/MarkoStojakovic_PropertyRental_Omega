package com.example.propertyrental.mapper;


import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDTO(User user){
        return UserDto
                .builder()
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .username(user.getUserName())
                .email(user.getEmail())
                .build();
    }
}
