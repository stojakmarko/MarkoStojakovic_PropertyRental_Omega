package com.example.propertyrental.validation;

import com.example.propertyrental.model.User;
import com.example.propertyrental.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findUserByUserName(value);
        if (user != null)
            return false;
        else
            return true;
    }
}
