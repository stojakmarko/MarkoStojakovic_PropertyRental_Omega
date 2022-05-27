package com.example.propertyrental.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class PropertyRequestDto {

    private String name;
    private double price;
    private boolean availability;
    private String location;
    private int numOfBedrooms;
    private int numOfSleepPlace;
    private boolean pool;
    private boolean freeParking;
    private boolean wifi;
}
