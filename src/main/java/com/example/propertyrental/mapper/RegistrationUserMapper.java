package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.RegistrationRequestDto;
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
}
