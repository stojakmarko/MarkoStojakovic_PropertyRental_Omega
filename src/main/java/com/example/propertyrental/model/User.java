package com.example.propertyrental.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;

    @ManyToOne
    private UserRole userRole;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<Property> ownProperties;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    private List<Reservation> reservedProperties;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    private List<Submission> submissionProperties;






}
