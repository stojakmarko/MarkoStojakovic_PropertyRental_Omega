package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.SubmissionDto;
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

    public SubmissionDto toSubmissionDto(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .comment(submission.getComment() != null ? submission.getComment() : "Not commented")
                .created(submission.getCreated())
                .status(submission.getStatus())
                .propertyId(submission.getProperty().getId())
                .userId(submission.getUser().getId())
                .build();
    }

}
