package com.example.propertyrental.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ReservationResponseDto {

    private UUID id;
    private UUID userId;
    private UUID propertyId;
    private LocalDate reservationFrom;
    private LocalDate reservationTo;

}
