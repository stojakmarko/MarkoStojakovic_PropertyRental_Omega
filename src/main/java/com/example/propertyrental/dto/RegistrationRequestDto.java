package com.example.propertyrental.dto;

import lombok.*;

@Getter
@Setter
public class RegistrationRequestDto {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
}
