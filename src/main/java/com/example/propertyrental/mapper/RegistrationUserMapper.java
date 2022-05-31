package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationUserMapper {

    private PasswordEncoder passwordEncoder;

    public User toUser(RegistrationRequestDto registrationRequest){
        return  User
                   .builder()
                   .firstName(registrationRequest.getFirstname())
                   .lastName(registrationRequest.getLastname())
                   .userName(registrationRequest.getUsername())
                   .password(passwordEncoder.encode(registrationRequest.getPassword()))
                   .email(registrationRequest.getEmail())
                   .build();
    }
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
