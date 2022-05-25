package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> userRegistration(@RequestBody RegistrationRequestDto registrationRequest) {
        UserDto userDto = userService.createClient(registrationRequest);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/registration").toUriString());
        return ResponseEntity.created(uri).body(userDto);
    }


}
