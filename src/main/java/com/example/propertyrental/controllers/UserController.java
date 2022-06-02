package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.AuthenticateRequest;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.security.CustomDetailUserService;
import com.example.propertyrental.security.CustomUserDetails;
import com.example.propertyrental.security.JwtTokenUtil;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> authentication(@RequestBody AuthenticateRequest authenticateRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.username(), authenticateRequest.password()));
        CustomUserDetails userDetails = (CustomUserDetails) detailUserServiceService.loadUserByUsername(authenticateRequest.username());
        String token = jwt.genarateToken(userDetails.getUser());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new ResponseEntity<String>(headers, HttpStatus.OK);

    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody UserRegistrationRequestDto registrationRequest) {
        UserDto userDto = userService.createClient(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

}
