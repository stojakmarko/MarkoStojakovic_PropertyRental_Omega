package com.example.propertyrental.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(generator = UUIDGenerator.GENERATOR_NAME)
    @Type(type = "uuid-char")
    private UUID id;

    private String comment;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}
