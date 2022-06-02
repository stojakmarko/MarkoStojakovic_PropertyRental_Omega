package com.example.propertyrental.dto;

import com.example.propertyrental.validation.FieldsValueMatch;
import lombok.Data;

@Data
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!"
        )
})
public class ChangePasswordDto {

    private String newPassword;
    private String verifyPassword;

}
