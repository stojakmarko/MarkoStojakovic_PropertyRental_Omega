package com.example.propertyrental.config;

import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.service.PropertyService;
import com.example.propertyrental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Profile("test")
public class AppCommandRunner implements CommandLineRunner {

    private UserService userService;
    private PropertyService propertyService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {


        User user1 = User
                .builder()
                .userRole(UserRole.ROLE_ADMIN)
                .userName("John")
                .password(passwordEncoder.encode("12345678"))
                .lastName("Johnson")
                .firstName("John")
                .email("john@gmail.com")
                .build();
        userService.addUser(user1);


        User user2 = User
                .builder()
                .userRole(UserRole.ROLE_CLIENT)
                .userName("mark")
                .password(passwordEncoder.encode("mark123"))
                .lastName("mark")
                .firstName("Mark")
                .email("mark@gmail.com")
                .build();

        userService.addUser(user2);

        for (int i = 0; i < 20; i++) {
            Property property = Property
                    .builder()
                    .availability(true)
                    .freeParking(true)
                    .location("street" + i)
                    .numOfBedrooms(2)
                    .numOfSleepPlace(4)
                    .name("property" + i)
                    .owner(user2)
                    .price(100 + i)
                    .wifi(true)
                    .pool(false)
                    .build();
            propertyService.addProperty(property);
        }

    }

}
