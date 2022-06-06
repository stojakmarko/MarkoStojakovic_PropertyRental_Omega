package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllUsers(page, size));
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUserAdmin(@Valid @RequestBody UserRegistrationRequestDto requestDto) {
        UserDto userDto = adminService.createUserAdmin(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/properties/submissions")
    public ResponseEntity<?> getAllSubmissions(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllSubmissions(page, size));
    }


}
