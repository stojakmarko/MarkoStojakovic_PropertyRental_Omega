package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.*;
import com.example.propertyrental.security.CustemDetailUserService;
import com.example.propertyrental.security.CustemUserDetails;
import com.example.propertyrental.security.JwtTokenUtil;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {


    private JwtTokenUtil jwt;
    private AuthenticationManager authenticationManager;
    private CustemDetailUserService detailUserServiceService;
    private UserService userService;


    @PostMapping("/sign")
    public ResponseEntity<?> authentication(@RequestBody AuthenticateRequest authenticateRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.username(), authenticateRequest.password()));
        CustemUserDetails userDetails = (CustemUserDetails) detailUserServiceService.loadUserByUsername(authenticateRequest.username());
        String token = jwt.genarateToken(userDetails.getUser());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new ResponseEntity<String>(headers, HttpStatus.OK);

    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
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
