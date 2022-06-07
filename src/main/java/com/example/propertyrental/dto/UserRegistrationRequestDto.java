package com.example.propertyrental.dto;

import com.example.propertyrental.validation.FieldsValueMatch;
import com.example.propertyrental.validation.UniqueEmail;
import com.example.propertyrental.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!"
        )
})
public class UserRegistrationRequestDto {


    @Size(min = 3, max = 50, message = "Firstname should have minimum 3 and maximum 50 characters ")
    private String firstname;

    @Size(min = 3, max = 50, message = "Lastname should have minimum 3 and maximum 50 characters ")
    private String lastname;

    @UniqueUsername
    private String username;

    private String password;
    private String verifyPassword;

    @Email(message = "Email should be formatted")
    @UniqueEmail
    private String email;

}
