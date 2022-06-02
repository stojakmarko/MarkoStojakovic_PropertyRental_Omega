package com.example.propertyrental.service;

import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.mapper.UserMapper;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;


    public UserDto createClient(UserRegistrationRequestDto registrationRequest) {
        User user = userMapper.toUser(registrationRequest);
        user.setUserRole(UserRole.ROLE_CLIENT);
        User created = userRepository.save(user);
        return userMapper.toUserDTO(created);
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

}
