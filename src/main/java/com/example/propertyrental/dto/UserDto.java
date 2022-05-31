package com.example.propertyrental.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
}
