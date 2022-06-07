package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.dto.ReservationDto;
import com.example.propertyrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllReservations(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(reservationService.getAllReservations(page, size));
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }


    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @PostMapping
    public ResponseEntity<?> makeReservation(@RequestBody ReservationDto reservationDto, @AuthenticationPrincipal(expression = "username") String username) {
        PropertyResponseDto responseDto = reservationService.createReservation(reservationDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
