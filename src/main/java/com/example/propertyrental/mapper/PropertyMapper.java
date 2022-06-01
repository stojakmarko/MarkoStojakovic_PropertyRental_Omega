package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.dto.ReservationDto;
import com.example.propertyrental.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PropertyMapper {


    public PropertyResponseDto toPropertyResponseDto(Property property) {
        return PropertyResponseDto.builder()
                .id(property.getId())
                .name(property.getName())
                .location(property.getLocation())
                .price(property.getPrice())
                .availability(property.isAvailability())
                .freeParking(property.isFreeParking())
                .numOfBedrooms(property.getNumOfBedrooms())
                .numOfSleepPlace(property.getNumOfSleepPlace())
                .pool(property.isPool())
                .wifi(property.isWifi())
                .build();
    }

    public Property toProperty(PropertyRequestDto propertyRequestDto, User owner) {
        return Property.builder()
                .name(propertyRequestDto.getName())
                .location(propertyRequestDto.getLocation())
                .price(propertyRequestDto.getPrice())
                .availability(propertyRequestDto.isAvailability())
                .freeParking(propertyRequestDto.isFreeParking())
                .numOfBedrooms(propertyRequestDto.getNumOfBedrooms())
                .numOfSleepPlace(propertyRequestDto.getNumOfSleepPlace())
                .pool(propertyRequestDto.isPool())
                .wifi(propertyRequestDto.isWifi())
                .owner(owner)
                .build();
    }

    public void updateProperty(Property property, PropertyRequestDto propertyRequestDto) {
        property.setName(propertyRequestDto.getName());
        property.setLocation(propertyRequestDto.getLocation());
        property.setAvailability(propertyRequestDto.isAvailability());
        property.setFreeParking(propertyRequestDto.isFreeParking());
        property.setNumOfBedrooms(propertyRequestDto.getNumOfBedrooms());
        property.setNumOfSleepPlace(propertyRequestDto.getNumOfSleepPlace());
        property.setPool(propertyRequestDto.isPool());
        property.setPrice(propertyRequestDto.getPrice());
        property.setWifi(propertyRequestDto.isWifi());

    }

    public Submission toSubmission(Property property, User user) {
        return Submission.builder()
                .property(property)
                .user(user)
                .status(Status.PENDING)
                .created(LocalDate.now())
                .build();
    }

    public Reservation toReservation(Property property, User user, ReservationDto reservationDto) {
        return Reservation.builder()
                .property(property)
                .reservationTo(reservationDto.toDate())
                .reservationFrom(reservationDto.fromDate())
                .user(user)
                .build();
    }

}
