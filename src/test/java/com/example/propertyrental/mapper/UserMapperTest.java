package com.example.propertyrental.mapper;

import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
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
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;
    private UserRegistrationRequestDto userRegistrationRequestDto;

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
        userDto = UserDto
                .builder()
                .firstname("test")
                .lastname("test")
                .username("test")
                .email("test")
                .role(UserRole.ROLE_CLIENT.toString())
                .build();

        userRegistrationRequestDto = new UserRegistrationRequestDto("test", "test", "test", "test", "test", "test");

    }


    @Test
    void shouldCreate_UserEntity() {

        User user1 = userMapper.toUser(userRegistrationRequestDto);

        assertEquals(user1.getUserName(), userRegistrationRequestDto.getUsername());
        assertEquals(user1.getFirstName(), userRegistrationRequestDto.getFirstname());
        assertEquals(user1.getEmail(), userRegistrationRequestDto.getEmail());

    }

    @Test
    void shouldCreate_UserDTO() {

        UserDto userDto1 = userMapper.toUserDTO(user);

        assertEquals(userDto1.getUsername(), user.getUserName());
        assertEquals(userDto1.getFirstname(), user.getFirstName());
        assertEquals(userDto1.getRole().toString(), user.getUserRole().toString());
        assertEquals(userDto1.getEmail(), user.getEmail());
    }

}