package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PropertyMapperTest {

    @Autowired
    private PropertyMapper propertyMapper;

    private Property property;
    private PropertyRequestDto propertyRequestDto;
    private User user;

    @BeforeEach
    public void setUp() {
        user = CreateObjectTest.userEntity();
        property = CreateObjectTest.propertyEntity();
        propertyRequestDto = createPropertyRequestDto();

    }

    @Test
    void createPropertyResponseDTO_allFieldsOk() {
        PropertyResponseDto propertyResponseDto = propertyMapper.toPropertyResponseDto(property);

        assertEquals(propertyResponseDto.getLocation(), property.getLocation());
        assertEquals(propertyResponseDto.getPrice(), property.getPrice());
        assertEquals(propertyResponseDto.getName(), property.getName());
        assertEquals(propertyResponseDto.getClass(), PropertyResponseDto.class);

    }

    @Test
    void createProperty_allFieldsOK() {
        Property property1 = propertyMapper.toProperty(propertyRequestDto, user);

        assertEquals(property1.getOwner(), user);
        assertEquals(property1.getOwner().getClass(), user.getClass());
        assertEquals(property1.getName(), propertyRequestDto.getName());
        assertEquals(property1.getClass(), Property.class);

    }

    @Test
    void updateProperty_allFieldsOK() {
        Property property2 = propertyMapper.updateProperty(property, propertyRequestDto);

        assertEquals(property2.getName(), propertyRequestDto.getName());
        assertEquals(property2.getNumOfBedrooms(), propertyRequestDto.getNumOfBedrooms());
        assertEquals(property2.getClass(), Property.class);
    }

    private PropertyRequestDto createPropertyRequestDto() {
        return PropertyRequestDto.builder()
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

}
