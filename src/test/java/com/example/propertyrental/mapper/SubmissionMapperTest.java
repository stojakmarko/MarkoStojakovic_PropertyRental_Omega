package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.SubmissionDto;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Status;
import com.example.propertyrental.model.Submission;
import com.example.propertyrental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubmissionMapperTest {

    @Autowired
    private SubmissionMapper submissionMapper;

    private Property property;
    private User user;
    private Submission submission;
    private Status submissionStatus;
    private String submissionComment;

    @BeforeEach
    public void setUp() {
        user = CreateObjectTest.userEntity();
        property = CreateObjectTest.propertyEntity();
        submissionStatus = Status.PENDING;
        submissionComment = "Test comment";
        submission = createSubmissionEntity();
    }

    @Test
    void createSubmissionEntity_allFieldsOk() {
        Submission submission1 = submissionMapper.toSubmission(property, user);

        assertEquals(Property.class, submission1.getProperty().getClass());
        assertEquals(User.class, submission1.getUser().getClass());
        assertEquals(Submission.class, submission1.getClass());
        assertEquals(property, submission1.getProperty());
        assertEquals(user, submission1.getUser());
    }

    @Test
    void createSubmissionDto_allFieldsOk() {
        SubmissionDto submissionDto = submissionMapper.toSubmissionDto(submission);

        assertEquals(SubmissionDto.class, submissionDto.getClass());
        assertEquals(submission.getProperty().getId(), submissionDto.getPropertyId());
        assertEquals(submission.getCreated(), submissionDto.getCreated());
        assertEquals(submission.getStatus(), submissionDto.getStatus());
    }

    @Test
    void updateSubmission_returnNewInstanceSubmissionEntity_allFieldsOk() {
        Submission submission2 = submissionMapper.updateSubmission(submission, submissionStatus, submissionComment);

        assertEquals(Submission.class, submission2.getClass());
        assertEquals(submissionStatus, submission2.getStatus());
        assertEquals(submissionComment, submission2.getComment());
        assertNotEquals(submission2, submission);

    }

    private Submission createSubmissionEntity() {
        return Submission.builder()
                .property(property)
                .user(user)
                .status(Status.PENDING)
                .created(LocalDate.now())
                .build();
    }

}