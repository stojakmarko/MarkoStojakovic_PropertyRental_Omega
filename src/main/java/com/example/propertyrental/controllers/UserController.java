package com.example.propertyrental.controllers;


import com.example.propertyrental.dto.*;
import com.example.propertyrental.security.CustomDetailUserService;
import com.example.propertyrental.security.CustomUserDetails;
import com.example.propertyrental.security.JwtTokenUtil;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {


    private JwtTokenUtil jwt;
    private AuthenticationManager authenticationManager;
    private CustomDetailUserService detailUserServiceService;
    private UserService userService;


    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.username(), authenticationRequestDto.password()));
        CustomUserDetails userDetails = (CustomUserDetails) detailUserServiceService.loadUserByUsername(authenticationRequestDto.username());
        String token = jwt.genarateToken(userDetails.getUser());
        AuthenticationResponseDto responseDto = AuthenticationResponseDto.builder()
                .accessToken(token)
                .build();
        return new ResponseEntity<AuthenticationResponseDto>(responseDto, HttpStatus.OK);

    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody UserRegistrationRequestDto registrationRequest) {
        UserDto userDto = userService.createClient(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto, HttpServletRequest request) {
        MessageResponseDto responseDto = userService.forgotPassword(forgotPasswordDto.getUsername(), request);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, @RequestParam("token") String token) {
        MessageResponseDto responseDto = userService.changePassword(changePasswordDto, token);
        return ResponseEntity.ok(responseDto);
    }

}
