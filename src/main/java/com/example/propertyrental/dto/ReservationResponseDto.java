package com.example.propertyrental.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {

    private UUID id;
    private UUID userId;
    private UUID propertyId;
    private LocalDate reservationFrom;
    private LocalDate reservationTo;

}
