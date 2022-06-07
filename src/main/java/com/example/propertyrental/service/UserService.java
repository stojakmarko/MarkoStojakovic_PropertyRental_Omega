package com.example.propertyrental.service;


import com.example.propertyrental.dto.ChangePasswordDto;
import com.example.propertyrental.dto.MessageResponseDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.mapper.UserMapper;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public UserDto createClient(UserRegistrationRequestDto registrationRequest) {
        User user = userMapper.toUser(registrationRequest);
        user.setUserRole(UserRole.ROLE_CLIENT);
        User created = userRepository.save(user);
        return userMapper.toUserDTO(created);
    }

    public MessageResponseDto forgotPassword(String username, HttpServletRequest request) {
        User user = findUserByUsername(username);
        String token = generatePasswordToken();
        String url = linkChangePassword(request);
        addTokenToUser(token, user);
        changePasswordEmailToUser(user, url, token);
        return new MessageResponseDto("You have received  an email!");
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean existUserByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public MessageResponseDto changePassword(ChangePasswordDto changePasswordDto, String token) {
        User user = userRepository.userWithPasswordResetToken(token).orElseThrow(() -> new BadCredentialsException("Bed credentials"));
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return new MessageResponseDto("You have successfully change password");
    }


    public Page<UserDto> getAllUsers(int page, int size) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
        return userPage.map(userMapper::toUserDTO);
    }

    public UserDto createUserAdmin(UserRegistrationRequestDto registrationRequestDto) {
        User user = userMapper.toUser(registrationRequestDto);
        user.setUserRole(UserRole.ROLE_ADMIN);
        User created = userRepository.save(user);
        return userMapper.toUserDTO(created);
    }


    private String generatePasswordToken() {
        return UUID.randomUUID().toString();
    }

    private String linkChangePassword(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "/api/v1/users/changePassword?token=");
    }

    private void addTokenToUser(String token, User user) {
        user.setPasswordResetToken(token);
        userRepository.save(user);
    }

    private void changePasswordEmailToUser(User user, String link, String token) {
        String url = link + token;
        String text = "Click on link to change password \n\n" + url;
        String email = user.getEmail();
        String subject = "Change password";
        emailService.sendSimpleMessage(email, subject, text);
    }

}
