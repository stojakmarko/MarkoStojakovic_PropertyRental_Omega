package com.example.propertyrental.controllers;

import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable("id") UUID id) {
        PropertyResponseDto propertyResponseDto = propertyService.getProperty(id);
        return ResponseEntity.ok(propertyResponseDto);
    }


    @PostMapping
    public ResponseEntity<?> createProperty(@RequestBody PropertyRequestDto propertyRequestDto, @AuthenticationPrincipal(expression = "username") String username) {
        PropertyResponseDto responseDto = propertyService.createProperty(propertyRequestDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(@RequestBody PropertyRequestDto propertyRequestDto, @PathVariable("id") UUID id,
                                                              @AuthenticationPrincipal(expression = "username") String username) {
        PropertyResponseDto responseDto = propertyService.updateProperty(propertyRequestDto, id, username);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable("id") UUID id,
                               @AuthenticationPrincipal(expression = "username") String username) {
        propertyService.deleteProperty(id, username);
    }


}
