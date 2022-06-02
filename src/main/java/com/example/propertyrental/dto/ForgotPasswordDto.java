package com.example.propertyrental.dto;

import com.example.propertyrental.validation.UniqueUsername;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordDto {

    @UniqueUsername
    private String username;

}
