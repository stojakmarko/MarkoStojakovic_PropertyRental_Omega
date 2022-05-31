package com.example.propertyrental.controllers;


import com.example.propertyrental.dto.AuthenticateRequest;
import com.example.propertyrental.dto.AuthenticationResponse;
import com.example.propertyrental.security.CustemDetailUserService;
import com.example.propertyrental.security.CustemUserDetails;
import com.example.propertyrental.security.JwtTokenUtil;
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

@RestController
@RequestMapping("/api/v1/sign")
@AllArgsConstructor
public class AuthenticationController {

    private JwtTokenUtil jwt;
    private AuthenticationManager authenticationManager;
    private CustemDetailUserService userService;


    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody AuthenticateRequest authenticateRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.username(), authenticateRequest.password()));
            CustemUserDetails userDetails = (CustemUserDetails) userService.loadUserByUsername(authenticateRequest.username());
            String token = jwt.genarateToken(userDetails.getUser());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            return new ResponseEntity<String>(headers, HttpStatus.OK);

        } catch (Exception e) {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse("Bad credentials");
            return new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.BAD_REQUEST);

        }

    }
}
