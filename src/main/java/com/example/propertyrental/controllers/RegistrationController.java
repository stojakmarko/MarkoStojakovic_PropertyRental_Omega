package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> userRegistration(@Valid @RequestBody RegistrationRequestDto registrationRequest){

        UserDto userDto = userService.createClient(registrationRequest);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
    }



}
