package com.example.propertyrental.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationDto(UUID id, LocalDate fromDate, LocalDate toDate) {

}
