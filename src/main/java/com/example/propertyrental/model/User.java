package com.example.propertyrental.model;


import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(generator =UUIDGenerator.GENERATOR_NAME )
    @Type(type = "uuid-char")
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String passwordRestToken;

    @ManyToOne
    private UserRole userRole;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Property> ownProperties;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    private List<Reservation> reservedProperties;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    private List<Submission> submissionProperties;






}
