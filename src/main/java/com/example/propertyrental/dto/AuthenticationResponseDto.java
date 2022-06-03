package com.example.propertyrental.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(String accessToken) {

}
