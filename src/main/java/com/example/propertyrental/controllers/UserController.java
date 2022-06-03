package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.AuthenticationRequestDto;
import com.example.propertyrental.dto.AuthenticationResponseDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.security.CustomDetailUserService;
import com.example.propertyrental.security.CustomUserDetails;
import com.example.propertyrental.security.JwtTokenUtil;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
