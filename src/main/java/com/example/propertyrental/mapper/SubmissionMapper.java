package com.example.propertyrental.mapper;

import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Status;
import com.example.propertyrental.model.Submission;
import com.example.propertyrental.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Builder
@Component
public class SubmissionMapper {

    public Submission toSubmission(Property property, User user) {
        return Submission.builder()
                .property(property)
                .user(user)
                .status(Status.PENDING)
                .created(LocalDate.now())
                .build();
    }
}
