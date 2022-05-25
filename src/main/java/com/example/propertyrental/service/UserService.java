package com.example.propertyrental.service;

import com.example.propertyrental.dto.RegistrationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.mapper.RegistrationUserMapper;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.UserRepository;
import com.example.propertyrental.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RegistrationUserMapper registrationUserMapper;
    

    public UserDto createClient(RegistrationRequestDto registrationRequest) {
        UserRole userRole = userRoleRepository.findUserRoleByRole("CLIENT");
        User user = registrationUserMapper.toUser(registrationRequest);
        user.setUserRole(userRole);
        User created = userRepository.save(user);
        return registrationUserMapper.toUserDTO(created);
    }

}
