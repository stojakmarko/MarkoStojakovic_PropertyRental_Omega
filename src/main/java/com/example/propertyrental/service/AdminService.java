package com.example.propertyrental.service;

import com.example.propertyrental.dto.ReservationResponseDto;
import com.example.propertyrental.dto.SubmissionDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.mapper.ReservationMapper;
import com.example.propertyrental.mapper.SubmissionMapper;
import com.example.propertyrental.mapper.UserMapper;
import com.example.propertyrental.model.Reservation;
import com.example.propertyrental.model.Submission;
import com.example.propertyrental.model.User;
import com.example.propertyrental.model.UserRole;
import com.example.propertyrental.repository.ReservationRepository;
import com.example.propertyrental.repository.SubmissionRepository;
import com.example.propertyrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final ReservationRepository reservationRepository;
    private final SubmissionMapper submissionMapper;
    private final ReservationMapper reservationMapper;
    private final UserMapper userMapper;

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

    public Page<SubmissionDto> getAllSubmissions(int page, int size) {
        Page<Submission> submissionPage = submissionRepository.findAll(PageRequest.of(page, size));
        return submissionPage.map(submissionMapper::toSubmissionDto);
    }

    public Page<ReservationResponseDto> getAllReservations(int page, int size) {
        Page<Reservation> reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
        return reservationPage.map(reservationMapper::reservationResponseDto);
    }

}
