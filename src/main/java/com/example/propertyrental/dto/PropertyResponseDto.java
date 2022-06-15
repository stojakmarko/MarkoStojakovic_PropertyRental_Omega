package com.example.propertyrental.dto;


import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PropertyResponseDto {
    private UUID id;

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
