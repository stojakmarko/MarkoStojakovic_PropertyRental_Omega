package com.example.propertyrental.model;

import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Submission {

    @Id
    @GeneratedValue(generator = UUIDGenerator.GENERATOR_NAME )
    @Type(type = "uuid-char")
    private UUID id;

    private String comment;
    private boolean approvment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
