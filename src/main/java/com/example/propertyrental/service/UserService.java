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

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RegistrationUserMapper registrationUserMapper;


    public UserDto createClient(RegistrationRequestDto registrationRequest) {
        UserRole userRole = userRoleRepository.findByRole("CLIENT");
        User user = registrationUserMapper.toUser(registrationRequest);
        user.setUserRole(userRole);
        User created = userRepository.save(user);
        return registrationUserMapper.toUserDTO(created);
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

}
