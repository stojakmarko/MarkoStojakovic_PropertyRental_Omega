package com.example.propertyrental.validation;

import com.example.propertyrental.model.User;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.findUserByUsername(value);
        return user != null ? false : true;

    }

}
