package com.example.propertyrental.unit.service;

import com.example.propertyrental.dto.ChangePasswordDto;
import com.example.propertyrental.dto.MessageResponseDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.mapper.UserMapper;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.UserRepository;
import com.example.propertyrental.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {


    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    private User user;


    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userName("test")
                .email("marko.stojakovic17@gmail.com")
                .userRole(UserRole.ROLE_CLIENT)
                .build();
    }

    @Test
    public void createClient_willReturn_createdUser() {

        UserRegistrationRequestDto requestDto = UserRegistrationRequestDto.builder()
                .username("test")
                .build();
        UserDto response = UserDto.builder()
                .username("test")
                .role(UserRole.ROLE_CLIENT.toString())
                .build();
        when(userMapper.toUser(requestDto)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(response);
        when(userRepository.save(user)).thenReturn(user);

        UserDto created = userService.createClient(requestDto);

        verify(userRepository).save(user);
        assertEquals(user.getUserName(), created.getUsername());
        assertEquals(user.getUserRole().toString(), created.getRole());


    }

    @Test
    public void changePassword_addNewPassword_toExistUser() {

        ChangePasswordDto request = ChangePasswordDto.builder()
                .newPassword("test")
                .verifyPassword("test")
                .build();

        when(userRepository.userWithPasswordResetToken("token")).thenReturn(Optional.of(user));

        MessageResponseDto responseDto = userService.changePassword(request, "token");

        verify(userRepository).save(user);
        assertEquals("You have successfully change password", responseDto.message());


    }

    @Test
    public void changePassword_throwException_whenToken_isNotValid() {
        ChangePasswordDto request = ChangePasswordDto.builder()
                .newPassword("test")
                .verifyPassword("test")
                .build();
        when(userRepository.userWithPasswordResetToken("token")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> userService.changePassword(request, "token"));
    }

    //    @Test
    public void forgotPassword_sendEmail_whenUserExist() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setServerName("localhost");
        servletRequest.setRequestURI("test");
        when(userRepository.findByUserName("test")).thenReturn(Optional.of(user));


        MessageResponseDto responseDto = userService.forgotPassword("test", servletRequest);

        assertEquals("You have received  an email!", responseDto.message());


    }

    @Test
    public void forgotPassword_throwException_whenUserNotExist() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setServerName("localhost");
        servletRequest.setRequestURI("test");
        when(userRepository.findByUserName("test")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.forgotPassword("test", servletRequest));

    }


}
