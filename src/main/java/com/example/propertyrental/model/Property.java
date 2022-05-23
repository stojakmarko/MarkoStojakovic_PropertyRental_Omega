package com.example.propertyrental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(generator = UUIDGenerator.GENERATOR_NAME )
    @Type(type = "uuid-char")
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

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    private List<Reservation> reservations;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
