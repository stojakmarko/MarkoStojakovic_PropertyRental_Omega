package com.example.propertyrental.integration;

import com.example.propertyrental.dto.PageResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil {

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PageResponseDto<?> asPage(String json, TypeReference<?> typeReference) {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();

            return (PageResponseDto<?>) mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Object asObject(String json, Class<?> type) {

        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
