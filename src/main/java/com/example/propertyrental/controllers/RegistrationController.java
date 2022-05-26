package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
        UserDto userDto = userService.createClient(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }


}
