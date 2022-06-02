package com.example.propertyrental.service;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.mapper.RegistrationUserMapper;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.UserRepository;
import com.example.propertyrental.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RegistrationUserMapper registrationUserMapper;

    private EmailService emailService;


    public UserDto createClient(RegistrationRequestDto registrationRequest) {
        UserRole userRole = userRoleRepository.findByRole("CLIENT");
        User user = registrationUserMapper.toUser(registrationRequest);
        user.setUserRole(userRole);
        User created = userRepository.save(user);
        return registrationUserMapper.toUserDTO(created);
    }

    public void forgotPassword(String username, HttpServletRequest request) {
        User user = findUserByUsername(username);
        String token = generatePasswordToken();
        String url = linkChangePassword(request);
        addTokenToUser(token, user);
        changePasswordEmailToUser(user,url,token);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
    }

    public boolean existUserByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private String generatePasswordToken() {
        return UUID.randomUUID().toString();
    }

    private String linkChangePassword(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "/api/v1/changePassword?token=");
    }

    private void addTokenToUser(String token, User user) {
        user.setPasswordRestToken(token);
        userRepository.save(user);
    }

    private void changePasswordEmailToUser(User user, String link, String token) {
        String url = link+""+token;
        String text = "Click on link to change password \n\n"+url;
        String email = user.getEmail();
        String subject = "Change password";
        emailService.sendSimpleMessage(email,subject,text);
    }


}
