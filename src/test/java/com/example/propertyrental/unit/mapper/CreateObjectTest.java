package com.example.propertyrental.unit.mapper;

import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;

public class CreateObjectTest {

    public static User userEntity() {
        return User
                .builder()
                .firstName("test")
                .lastName("test")
                .userName("test")
                .email("test")
                .userRole(UserRole.ROLE_CLIENT)
                .build();

    }

    public static Property propertyEntity() {
        return Property.builder()
                .name("property")
                .location("test")
                .price(1000)
                .availability(true)
                .freeParking(true)
                .numOfBedrooms(4)
                .numOfSleepPlace(2)
                .pool(false)
                .wifi(true)
                .owner(userEntity())
                .build();

    }


}
