package com.example.propertyrental.dto;

import com.example.propertyrental.validation.FieldsValueMatch;
import lombok.Builder;
import lombok.Data;

@Data
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "newPassword",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!"
        )
})
@Builder
public class ChangePasswordDto {

    private String newPassword;
    private String verifyPassword;

}
