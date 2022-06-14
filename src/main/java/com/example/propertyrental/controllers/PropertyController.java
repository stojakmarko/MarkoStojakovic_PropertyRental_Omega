package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.dto.SubmissionDto;
import com.example.propertyrental.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/properties")
@AllArgsConstructor
public class PropertyController {

    private PropertyService propertyService;


    @GetMapping
    public ResponseEntity<Page<PropertyResponseDto>> getAllProperties(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<PropertyResponseDto> pagePropertyResponse = propertyService.getAllProperties(page, size);
        return ResponseEntity.ok(pagePropertyResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PropertyResponseDto>> getAllPropertiesSearch(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size
            , @RequestParam(name = "search", defaultValue = "") String search) {
        Page<PropertyResponseDto> pagePropertyResponse = propertyService.getAllPropertiesSearch(page, size, search);
        return ResponseEntity.ok(pagePropertyResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable("id") UUID id) {
        PropertyResponseDto propertyResponseDto = propertyService.getProperty(id);
        return ResponseEntity.ok(propertyResponseDto);
    }

    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @PostMapping
    public ResponseEntity<?> createProperty(@Valid @RequestBody PropertyRequestDto propertyRequestDto, @AuthenticationPrincipal(expression = "username") String username) {
        PropertyResponseDto responseDto = propertyService.createProperty(propertyRequestDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(@Valid @RequestBody PropertyRequestDto propertyRequestDto, @PathVariable("id") UUID id,
                                                              @AuthenticationPrincipal(expression = "username") String username) {
        PropertyResponseDto responseDto = propertyService.updateProperty(propertyRequestDto, id, username);
        return ResponseEntity.ok().body(responseDto);
    }

    @PreAuthorize(value = "hasAnyRole('CLIENT')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable("id") UUID id,
                               @AuthenticationPrincipal(expression = "username") String username) {
        propertyService.deleteProperty(id, username);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping("/submissions")
    public ResponseEntity<?> getAllSubmissions(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(propertyService.getAllSubmissions(page, size));
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping("/submissions/{id}")
    public ResponseEntity<?> getSubmission(@PathVariable("id") UUID id) {
        SubmissionDto submissionDto = propertyService.getSubmission(id);
        return ResponseEntity.ok(submissionDto);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @PutMapping("/submissions/{id}")
    public ResponseEntity<?> updateSubmission(@PathVariable("id") UUID id, @RequestBody SubmissionDto submissionDto) {

        SubmissionDto updateSubmission = propertyService.updateSubmission(id, submissionDto.getStatus(), submissionDto.getComment());
        return ResponseEntity.ok(updateSubmission);
    }

}
