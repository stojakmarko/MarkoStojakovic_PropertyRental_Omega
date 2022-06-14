package com.example.propertyrental.integration;

import com.example.propertyrental.dto.ReservationDto;
import org.json.JSONObject;
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
        JSONObject response = createJson(result.getResponse().getContentAsString());
        assertEquals(0, response.get("totalElements"));
        assertEquals(0, response.get("numberOfElements"));
        assertEquals(0, response.get("totalPages"));

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
        JSONObject response = createJson(result.getResponse().getContentAsString());
        assertEquals("Not authorized!", response.getString("message"));
    }

    @Test
    public void makeReservation_withoutAccessToken_statusIsUnauthorized() throws Exception {
        UUID propertyId = UUID.fromString("0c54590d-522e-452c-bff2-1e2fe6e869de");
        LocalDate fromDate = LocalDate.of(2022, 06, 13);
        LocalDate toDate = LocalDate.of(2022, 06, 17);
        ReservationDto request = new ReservationDto(propertyId, fromDate, toDate);

        MvcResult result = mockMvc.perform(post("/api/v1/reservations")
                        .header("Authorization", "Bearer notValid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isUnauthorized())
                .andReturn();

        JSONObject response = createJson(result.getResponse().getContentAsString());
        assertEquals("Not authorized!", response.getString("message"));


    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void makeReservation_withAccessToken_statusIsCreated() throws Exception {
        UUID propertyId = UUID.fromString("0c54590d-522e-452c-bff2-1e2fe6e869de");
        LocalDate fromDate = LocalDate.of(2022, 06, 13);
        LocalDate toDate = LocalDate.of(2022, 06, 17);
        ReservationDto request = new ReservationDto(propertyId, fromDate, toDate);

        MvcResult result = mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject response = createJson(result.getResponse().getContentAsString());

        assertEquals(propertyId.toString(), response.getString("id"));


    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void makeReservation_withAccessToken_notExistId_statusIsNotFound() throws Exception {
        UUID propertyId = UUID.fromString("0c54590d-522e-452c-bff2-1e2f");
        LocalDate fromDate = LocalDate.of(2022, 06, 13);
        LocalDate toDate = LocalDate.of(2022, 06, 17);
        ReservationDto request = new ReservationDto(propertyId, fromDate, toDate);

        MvcResult result = mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(request)))
                .andExpect(status().isNotFound())
                .andReturn();

        JSONObject response = createJson(result.getResponse().getContentAsString());

        assertEquals("Property not found", response.getString("message"));
        assertEquals("NOT_FOUND", response.getString("status"));


    }

    private JSONObject createJson(String string) throws Exception {
        JSONObject json = new JSONObject(string);
        return json;
    }


}
