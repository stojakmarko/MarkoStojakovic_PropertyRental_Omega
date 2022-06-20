package com.example.propertyrental.unit.mapper;

import com.example.propertyrental.dto.ReservationResponseDto;
import com.example.propertyrental.mapper.ReservationMapper;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Reservation;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationMapperTest {


    @Autowired
    private ReservationMapper reservationMapper;

    private Reservation reservation;
    private Property property;
    private User user;
    private LocalDate reservationFrom;
    private LocalDate reservationTo;

    @BeforeEach
    public void setUp() {

        user = User
                .builder()
                .firstName("test")
                .lastName("test")
                .userName("test")
                .email("test")
                .userRole(UserRole.ROLE_CLIENT)
                .build();
        property = Property.builder()
                .name("property")
                .location("test")
                .price(1000)
                .availability(true)
                .freeParking(true)
                .numOfBedrooms(4)
                .numOfSleepPlace(2)
                .pool(false)
                .wifi(true)
                .owner(user)
                .build();
        reservationFrom = LocalDate.of(2022, 06, 10);
        reservationTo = LocalDate.of(2022, 06, 17);
        reservation = createReservationEntity();

    }

    @Test
    void createReservationEntity_allFieldOk() {

        Reservation reservation1 = reservationMapper.toReservation(property, user, reservationFrom, reservationTo);

        assertEquals(Reservation.class, reservation1.getClass());
        assertEquals(user, reservation1.getUser());
        assertEquals(reservationFrom, reservation1.getReservationFrom());
        assertEquals(reservationTo, reservation1.getReservationTo());

    }

    @Test
    void createReservationResponseDto_allFieldsOk() {

        ReservationResponseDto reservationResponseDto = reservationMapper.reservationResponseDto(reservation);

        assertEquals(ReservationResponseDto.class, reservationResponseDto.getClass());
        assertEquals(reservation.getUser().getId(), reservationResponseDto.getUserId());
        assertEquals(reservation.getProperty().getId(), reservationResponseDto.getPropertyId());

    }

    private Reservation createReservationEntity() {
        return Reservation.builder()
                .property(property)
                .reservationTo(reservationTo)
                .reservationFrom(reservationFrom)
                .user(user)
                .build();
    }

}
