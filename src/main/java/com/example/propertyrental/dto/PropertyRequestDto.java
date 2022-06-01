package com.example.propertyrental.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class PropertyRequestDto {

    @NotEmpty(message = "Property should have name")
    private String name;

    @Positive(message = "Price should be positive")
    private double price;

    private boolean availability;

    @NotEmpty(message = "Property should have location")
    private String location;

    @Positive(message = "Number of bedrooms should be positive")
    private int numOfBedrooms;

    @Positive(message = "Number of sleep place should be positive")
    private int numOfSleepPlace;

    private boolean pool;
    private boolean freeParking;
    private boolean wifi;

}
