package com.example.propertyrental.config;

import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.PropertyRepositoriy;
import com.example.propertyrental.repository.UserRepository;
import com.example.propertyrental.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AppCommandRunner implements CommandLineRunner {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PropertyRepositoriy propertyRepositoriy;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        UserRole userRole1 = UserRole
                .builder()
                .role("ADMIN")
                .build();
        userRoleRepository.save(userRole1);

        User user1 = User
                .builder()
                .userRole(userRole1)
                .userName("John")
                .password(passwordEncoder.encode("12345678"))
                .lastName("Jhonson")
                .firstName("Jhon")
                .email("jhon@gamail.com")
                .build();
        userRepository.save(user1);

        UserRole userRole2 = UserRole
                .builder()
                .role("CLIENT")
                .build();
        userRoleRepository.save(userRole2);

        User user2 = User
                .builder()
                .userRole(userRole2)
                .userName("mark")
                .password(passwordEncoder.encode("mark123"))
                .lastName("markovic")
                .firstName("Mark")
                .email("mark@gamail.com")
                .build();

        userRepository.save(user2);

        for(int i = 0;i <20;i++){
            Property property = Property
                    .builder()
                    .availability(true)
                    .freeParking(true)
                    .location("dammystreet"+i)
                    .numOfBedrooms(2)
                    .numOfSleepPlace(4)
                    .name("property"+i)
                    .owner(user2)
                    .price(100+i)
                    .wifi(true)
                    .pool(false)
                    .build();
            propertyRepositoriy.save(property);
        }



    }
}
