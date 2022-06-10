package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class PropertyMapperTest {

    @Autowired
    private PropertyMapper propertyMapper;

    private Property property;
    private PropertyRequestDto propertyRequestDto;
    private User user;

    @BeforeEach
    public void setUp() {
        user = User
                .builder()
                .firstName("test")
                .lastName("test")
                .userName("test")
                .email("test")
                .userRole(UserRole.ROLE_CLIENT)
                .build();

        property = Property.builder()
                .name("property")
                .location("test")
                .price(1000)
                .availability(true)
                .freeParking(true)
                .numOfBedrooms(4)
                .numOfSleepPlace(2)
                .pool(false)
                .wifi(true)
                .owner(user)
                .build();

        propertyRequestDto = PropertyRequestDto.builder()
                .name("property")
                .location("test")
                .price(1000)
                .availability(true)
                .freeParking(true)
                .numOfBedrooms(4)
                .numOfSleepPlace(2)
                .pool(false)
                .wifi(true)
                .build();

    }

    @Test
    void should_create_PropertyResponseDto() {
        PropertyResponseDto propertyResponseDto = propertyMapper.toPropertyResponseDto(property);
        assertEquals(propertyResponseDto.getLocation(), property.getLocation());
        assertEquals(propertyResponseDto.getPrice(), property.getPrice());
        assertEquals(propertyResponseDto.getName(), property.getName());
        assertEquals(propertyResponseDto.getClass(), PropertyResponseDto.class);

    }

    @Test
    void should_create_Property() {
        Property property1 = propertyMapper.toProperty(propertyRequestDto, user);
        assertEquals(property1.getOwner(), user);
        assertEquals(property1.getOwner().getClass(), user.getClass());
        assertEquals(property1.getName(), propertyRequestDto.getName());
        assertEquals(property1.getClass(), Property.class);

    }

    @Test
    void should_update_Property() {
        Property property2 = propertyMapper.updateProperty(property, propertyRequestDto);
        assertEquals(property2.getName(), propertyRequestDto.getName());
        assertEquals(property2.getNumOfBedrooms(), propertyRequestDto.getNumOfBedrooms());
        assertEquals(property2.getClass(), Property.class);
    }

}