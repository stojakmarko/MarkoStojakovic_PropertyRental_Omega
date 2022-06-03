package com.example.propertyrental.mapper;

import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Reservation;
import com.example.propertyrental.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Builder
@Component
public class ReservationMapper {

    public Reservation toReservation(Property property, User user, LocalDate fromDate, LocalDate toDate) {
        return Reservation.builder()
                .property(property)
                .reservationTo(toDate)
                .reservationFrom(fromDate)
                .user(user)
                .build();
    }

}
