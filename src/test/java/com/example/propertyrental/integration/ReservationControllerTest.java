package com.example.propertyrental.integration;

import com.example.propertyrental.dto.PageResponseDto;
import com.example.propertyrental.dto.PropertyResponseDto;
import com.example.propertyrental.dto.ReservationDto;
import com.example.propertyrental.dto.ReservationResponseDto;
import com.example.propertyrental.exception.ApiError;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_ADMIN"})
    public void getAllReservation_withRoleAdmin_statusOK() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/reservations"))
                .andExpect(status().isOk())
                .andReturn();

        PageResponseDto<?> response = TestUtil.asPage(result.getResponse().getContentAsString(), new TypeReference<PageResponseDto<ReservationResponseDto>>() {
        });

        assertEquals(2, response.getTotalElements());
        assertEquals(2, response.getNumberOfElements());
        assertEquals(1, response.getTotalPages());

    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_ADMIN"})
    public void getReservation_withRoleAdmin_validId_statusOK() throws Exception {
        UUID id = UUID.fromString("461aa172-b57d-4b1f-ba85-9d10300f6b3f");

        MvcResult result = mockMvc.perform(get("/api/v1/reservations/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_ADMIN"})
    public void getReservation_withRoleAdmin_InvalidId_statusOK() throws Exception {
        UUID invalidId = UUID.fromString("461aa172-b57d-4b1f-ba85-9");

        MvcResult result = mockMvc.perform(get("/api/v1/reservations/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andReturn();
        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Not found", response.getMessage());

    }


    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_CLIENT"})
    public void getAllReservation_withRoleClient_statusForbidden() throws Exception {

        mockMvc.perform(get("/api/v1/reservations"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void getAllReservation_withInvalidAccessToken_statusUnauthorized() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/reservations")
                        .header("Authorization", "Bearer notValid"))
                .andExpect(status().isUnauthorized())
                .andReturn();
        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Not authorized!", response.getMessage());
    }

    @Test
    public void makeReservation_withoutAccessToken_statusIsUnauthorized() throws Exception {
        UUID propertyId = UUID.fromString("0c54590d-522e-452c-bff2-1e2fe6e869de");
        LocalDate fromDate = LocalDate.of(2022, 6, 13);
        LocalDate toDate = LocalDate.of(2022, 6, 17);
        ReservationDto request = new ReservationDto(propertyId, fromDate, toDate);

        MvcResult result = mockMvc.perform(post("/api/v1/reservations")
                        .header("Authorization", "Bearer notValid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isUnauthorized())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Not authorized!", response.getMessage());


    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void makeReservation_withAccessToken_statusIsCreated() throws Exception {
        UUID propertyId = UUID.fromString("0c54590d-522e-452c-bff2-1e2fe6e869de");
        LocalDate fromDate = LocalDate.of(2022, 6, 13);
        LocalDate toDate = LocalDate.of(2022, 6, 17);
        ReservationDto request = new ReservationDto(propertyId, fromDate, toDate);

        MvcResult result = mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        PropertyResponseDto response = (PropertyResponseDto) TestUtil.asObject(result.getResponse().getContentAsString(), PropertyResponseDto.class);

        assertEquals(propertyId.toString(), response.getId().toString());

    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void makeReservation_withAccessToken_notExistId_statusIsNotFound() throws Exception {
        UUID propertyId = UUID.fromString("0c54590d-522e-452c-bff2-1e2f");
        LocalDate fromDate = LocalDate.of(2022, 6, 13);
        LocalDate toDate = LocalDate.of(2022, 6, 17);
        ReservationDto request = new ReservationDto(propertyId, fromDate, toDate);

        MvcResult result = mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isNotFound())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);

        assertEquals("Property not found", response.getMessage());
        assertEquals("404 NOT_FOUND", response.getStatus().toString());


    }


}
