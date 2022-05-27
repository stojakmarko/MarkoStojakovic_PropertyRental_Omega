package com.example.propertyrental.service;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.mapper.PropertyMapper;
import com.example.propertyrental.model.Property;
import com.example.propertyrental.model.Status;
import com.example.propertyrental.model.Submission;
import com.example.propertyrental.model.User;
import com.example.propertyrental.repository.PropertyRepositoriy;
import com.example.propertyrental.repository.SubmissionRepository;
import com.example.propertyrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PropertyService {

    private PropertyRepositoriy propertyRepositoriy;
    private PropertyMapper propertyMapper;
    private UserRepository userRepository;
    private SubmissionRepository submissionRepository;


    public Page<PropertyResponseDto> getAllProperties(int page, int size) {
        Page<Property> pageProperties = propertyRepositoriy.findAllByStatus(PageRequest.of(page, size));
        return pageProperties.map(propertyMapper::toPropertyResponseDto);
    }

    public PropertyResponseDto getProperty(UUID id) {
        Property property = propertyRepositoriy.getById(id);
        return propertyMapper.toPropertyResponseDto(property);
    }


    public PropertyResponseDto createProperty(PropertyRequestDto propertyRequestDto, String username) {
        User user = userRepository.findUserByUserName(username);
        Property property = propertyMapper.toProperty(propertyRequestDto, user);
        Submission submission = new Submission();
        submission.setProperty(property);
        submission.setUser(user);
        submission.setStatus(Status.PENDING);
        submission.setCreated(LocalDate.now());
        Property created = propertyRepositoriy.save(property);
        submissionRepository.save(submission);
        return propertyMapper.toPropertyResponseDto(created);
    }
}
