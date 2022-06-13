package com.example.propertyrental.unit.service;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.exception.NotFoundPropertyException;
import com.example.propertyrental.mapper.PropertyMapper;
import com.example.propertyrental.mapper.SubmissionMapper;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Status;
import com.example.propertyrental.model.Submission;
import com.example.propertyrental.model.User;
import com.example.propertyrental.repository.PropertyRepository;
import com.example.propertyrental.repository.SubmissionRepository;
import com.example.propertyrental.service.PropertyService;
import com.example.propertyrental.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PropertyServiceTest {

    @MockBean
    private PropertyRepository propertyRepository;
    @MockBean
    private PropertyMapper propertyMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private SubmissionRepository submissionRepository;
    @MockBean
    private SubmissionMapper submissionMapper;

    @Autowired
    private PropertyService propertyService;

    private Page<Property> propertyPage;
    private Property property;
    private PropertyResponseDto propertyResponseDto;

    private User user;

    @BeforeEach
    public void setUp() {
        List<Property> list = new ArrayList<>();
        property = Property.builder()
                .id(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"))
                .name("property")
                .location("test")
                .build();
        list.add(property);

        propertyResponseDto = PropertyResponseDto.builder()
                .id(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"))
                .name("property")
                .location("test")
                .build();

        user = User.builder()
                .firstName("test")
                .userName("test")
                .build();

        propertyPage = new PageImpl<Property>(list);
    }


    @Test
    void getAllProperties_willReturnPage_withExistProperties() throws Exception {

        int page = 0;
        int size = 1;

        when(propertyRepository.findAllByStatus(PageRequest.of(page, size))).thenReturn(propertyPage);
        when(propertyMapper.toPropertyResponseDto(propertyPage.getContent().get(0))).thenReturn(propertyResponseDto);

        Page<PropertyResponseDto> pageResponse = propertyService.getAllProperties(page, size);
        assertEquals(propertyPage.getContent().get(0).getName(), pageResponse.getContent().get(0).getName());
        System.out.println(pageResponse.getContent());


    }

    @Test
    void getAllPropertiesSearch_willReturnPageOfAllProperties_withSearchParameter() {

        int page = 0;
        int size = 1;

        when(propertyRepository.findAllSearch(PageRequest.of(page, size), "test")).thenReturn(propertyPage);
        when(propertyMapper.toPropertyResponseDto(propertyPage.getContent().get(0))).thenReturn(propertyResponseDto);

        Page<PropertyResponseDto> pageResponse = propertyService.getAllPropertiesSearch(page, size, "test");
        assertEquals(propertyPage.getContent().get(0).getName(), pageResponse.getContent().get(0).getName());

    }

    @Test
    void getProperty_willReturnProperty_withExistId() {

        when(propertyRepository.findById(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"))).thenReturn(Optional.of(property));
        when(propertyMapper.toPropertyResponseDto(property)).thenReturn(propertyResponseDto);

        PropertyResponseDto responseDtoTest = propertyService.getProperty(property.getId());

        assertEquals(property.getName(), responseDtoTest.getName());
        assertEquals(property.getId(), responseDtoTest.getId());

    }

    @Test
    void getProperty_willThrowException_withNotExistId() {
        when(propertyRepository.findById(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"))).thenReturn(Optional.empty());
        assertThrows(NotFoundPropertyException.class,
                () -> propertyService.getProperty(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d")));

    }

    @Test
    void createProperty_willCreatePropertyAndSubmissionForProperty() {
        PropertyRequestDto propertyRequestDto = PropertyRequestDto.builder()
                .name("test")
                .build();
        Submission submission = Submission.builder()
                .property(property)
                .status(Status.PENDING)
                .build();
        when(userService.findUserByUsername("test")).thenReturn(user);
        when(propertyMapper.toProperty(propertyRequestDto, user)).thenReturn(property);
        when(submissionMapper.toSubmission(property, user)).thenReturn(submission);

        propertyService.createProperty(propertyRequestDto, "test");

        verify(propertyRepository).save(property);
        verify(submissionRepository).save(submission);


    }

    @Test
    void updateProperty_throwException_whenPropertyNotExist() {
        when(propertyRepository.findByIdAndOwner_UserName(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "test")).thenReturn(Optional.empty());

        assertThrows(NotFoundPropertyException.class,
                () -> propertyService.updateProperty(PropertyRequestDto.builder().build(), UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "test"));

    }


}
