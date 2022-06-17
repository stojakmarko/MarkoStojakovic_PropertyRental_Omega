package com.example.propertyrental.dto;

import com.example.propertyrental.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {

    private UUID id;
    private String comment;
    private Status status;
    private LocalDate created;
    private UUID userId;
    private UUID propertyId;

}
