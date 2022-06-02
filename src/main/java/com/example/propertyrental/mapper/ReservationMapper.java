package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.ReservationDto;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Reservation;
import com.example.propertyrental.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationMapper {

    public Reservation toReservation(Property property, User user, ReservationDto reservationDto) {
        return Reservation.builder()
                .property(property)
                .reservationTo(reservationDto.toDate())
                .reservationFrom(reservationDto.fromDate())
                .user(user)
                .build();
    }

}
