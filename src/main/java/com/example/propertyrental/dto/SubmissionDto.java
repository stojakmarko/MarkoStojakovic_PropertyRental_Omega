package com.example.propertyrental.dto;

import com.example.propertyrental.model.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class SubmissionDto {

    private UUID id;
    private String comment;
    private Status status;
    private LocalDate created;
    private UUID userId;
    private UUID propertyId;

}
