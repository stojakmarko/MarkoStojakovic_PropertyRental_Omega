package com.example.propertyrental.service;

import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.mapper.UserMapper;
import com.example.propertyrental.model.User;
import com.example.propertyrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserDto> getAllUsers(int page, int size) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
        return userPage.map(userMapper::toUserDTO);
    }

}
