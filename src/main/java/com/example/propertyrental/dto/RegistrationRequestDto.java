package com.example.propertyrental.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationRequestDto {


    @Size(min = 3,max = 50,message = "Firstname should have minimum 3 and maximum 50 characters ")
    private String firstname;
    @Size(min = 3,max = 50,message = "Lastname should have minimum 3 and maximum 50 characters ")
    private String lastname;
    //to do --custom validation for username
    private String username;
    //to do-- validator are password equal
    private String password;
    private String verifypassword;
    @Email
    private String email;
}
