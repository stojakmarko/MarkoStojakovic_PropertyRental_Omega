package com.example.propertyrental.service;

import com.example.propertyrental.dto.*;
import com.example.propertyrental.exception.NotFoundPropertyException;
import com.example.propertyrental.mapper.PropertyMapper;
import com.example.propertyrental.mapper.ReservationMapper;
import com.example.propertyrental.mapper.SubmissionMapper;
import com.example.propertyrental.model.*;
import com.example.propertyrental.repository.PropertyRepository;
import com.example.propertyrental.repository.ReservationRepository;
import com.example.propertyrental.repository.SubmissionRepository;
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
public class PropertyService {

    private PropertyRepository propertyRepository;
    private PropertyMapper propertyMapper;
    private SubmissionMapper submissionMapper;
    private ReservationMapper reservationMapper;
    private UserService userService;
    private SubmissionRepository submissionRepository;
    private ReservationRepository reservationRepository;


    public Page<PropertyResponseDto> getAllProperties(int page, int size) {
        Page<Property> pageProperties = propertyRepository.findAllByStatus(PageRequest.of(page, size));
        return pageProperties.map(propertyMapper::toPropertyResponseDto);
    }

    public Page<PropertyResponseDto> getAllPropertiesSearch(int page, int size, String search) {
        Page<Property> pageProperties = propertyRepository.findAllSearch(PageRequest.of(page, size), search);
        return pageProperties.map(propertyMapper::toPropertyResponseDto);
    }

    public PropertyResponseDto getProperty(UUID id) {
        Property property = propertyRepository.findById(id).orElseThrow(NotFoundPropertyException::new);
        return propertyMapper.toPropertyResponseDto(property);
    }


    public PropertyResponseDto createProperty(PropertyRequestDto propertyRequestDto, String username) {
        User user = userService.findUserByUsername(username);
        Property property = propertyMapper.toProperty(propertyRequestDto, user);
        Submission submission = submissionMapper.toSubmission(property, user);
        Property created = propertyRepository.save(property);
        submissionRepository.save(submission);
        return propertyMapper.toPropertyResponseDto(created);
    }

    public PropertyResponseDto updateProperty(PropertyRequestDto propertyRequestDto, UUID id, String username) {
        Property property = propertyRepository.findByIdAndOwner_UserName(id, username).orElseThrow(NotFoundPropertyException::new);
        Property updated = propertyMapper.updateProperty(property, propertyRequestDto);
        propertyRepository.save(updated);
        return propertyMapper.toPropertyResponseDto(updated);

    }

    public void deleteProperty(UUID id, String username) {
        Property property = propertyRepository.findByIdAndOwner_UserName(id, username).orElseThrow(NotFoundPropertyException::new);
        propertyRepository.delete(property);
    }

    public PropertyResponseDto createReservation(ReservationDto reservationDto, String username) {
        Property property = propertyRepository.findById(reservationDto.id()).orElseThrow(NotFoundPropertyException::new);
        User user = userService.findUserByUsername(username);
        Reservation reservation = reservationMapper.toReservation(property, user, reservationDto.fromDate(), reservationDto.toDate());
        reservationRepository.save(reservation);
        return propertyMapper.toPropertyResponseDto(property);

    }

    public SubmissionDto updateSubmission(UUID id, Status status, String comment) {
        Submission submission = submissionRepository.getById(id);
        Submission updated = submissionMapper.updateSubmission(submission, status, comment);
        submissionRepository.save(updated);
        return submissionMapper.toSubmissionDto(updated);

    }

    public Page<SubmissionDto> getAllSubmissions(int page, int size) {
        Page<Submission> submissionPage = submissionRepository.findAll(PageRequest.of(page, size));
        return submissionPage.map(submissionMapper::toSubmissionDto);
    }

    public SubmissionDto getSubmission(UUID id) {
        Submission submission = submissionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return submissionMapper.toSubmissionDto(submission);
    }

    public Page<ReservationResponseDto> getAllReservations(int page, int size) {
        Page<Reservation> reservationPage = reservationRepository.findAll(PageRequest.of(page, size));
        return reservationPage.map(reservationMapper::reservationResponseDto);
    }


    public void addProperty(Property property) {
        propertyRepository.save(property);
    }

}
