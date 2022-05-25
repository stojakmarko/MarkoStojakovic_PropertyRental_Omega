package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void userRegistration(@Valid @RequestBody RegistrationRequestDto registrationRequest){

        userService.createClient(registrationRequest);

    }



}
