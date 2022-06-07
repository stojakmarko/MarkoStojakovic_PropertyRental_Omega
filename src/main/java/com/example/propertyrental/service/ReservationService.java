package com.example.propertyrental.service;


import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.dto.ReservationDto;
import com.example.propertyrental.dto.ReservationResponseDto;
import com.example.propertyrental.exception.NotFoundPropertyException;
import com.example.propertyrental.mapper.PropertyMapper;
import com.example.propertyrental.mapper.ReservationMapper;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Reservation;
import com.example.propertyrental.model.User;
import com.example.propertyrental.repository.PropertyRepository;
import com.example.propertyrental.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;

    private PropertyRepository propertyRepository;

    private PropertyMapper propertyMapper;

    private UserService userService;


    public PropertyResponseDto createReservation(ReservationDto reservationDto, String username) {
        Property property = propertyRepository.findById(reservationDto.propertyId()).orElseThrow(NotFoundPropertyException::new);
        User user = userService.findUserByUsername(username);
        Reservation reservation = reservationMapper.toReservation(property, user, reservationDto.fromDate(), reservationDto.toDate());
        reservationRepository.save(reservation);
        return propertyMapper.toPropertyResponseDto(property);

    }

    public Page<ReservationResponseDto> getAllReservations(int page, int size) {
        Page<Reservation> reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
        return reservationPage.map(reservationMapper::reservationResponseDto);
    }

    public ReservationResponseDto getReservation(UUID id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return reservationMapper.reservationResponseDto(reservation);
    }

}
