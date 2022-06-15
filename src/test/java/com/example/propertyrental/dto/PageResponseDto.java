package com.example.propertyrental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponseDto<T> {

    private ArrayList<T> content;
    private int totalElements;
    private int numberOfElements;
    private int totalPages;
}
