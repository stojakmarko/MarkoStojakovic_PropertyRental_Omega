package com.example.propertyrental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private double price;
    private boolean availability;
    private String location;
    private int numOfBedrooms;
    private int numOfSleepPlace;
    private boolean pool;
    private boolean freeParking;
    private boolean wifi;

    @OneToMany(mappedBy = "property")
    private List<Reservation> reservations;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
