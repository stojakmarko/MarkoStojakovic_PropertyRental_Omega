package com.example.propertyrental.integration;

import com.example.propertyrental.dto.PageResponseDto;
import com.example.propertyrental.dto.PropertyRequestDto;
import com.example.propertyrental.dto.PropertyResponseDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PropertyControllerTest {

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
    public void getAllProperties_withDefaultSizeAndPage_responseWithStatusOk() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/properties"))
                .andExpect(status().isOk())
                .andReturn();
        PageResponseDto<?> response = TestUtil.asPage(result.getResponse().getContentAsString(), new TypeReference<PageResponseDto<PropertyResponseDto>>() {
        });

        assertEquals(23, response.getTotalElements());
        assertEquals(10, response.getNumberOfElements());
        assertEquals(3, response.getTotalPages());
    }

    @Test
    public void getAllProperties_SizeAndPageInQueryString_responseWithStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/properties?size=5&page=2"))
                .andExpect(status().isOk())
                .andReturn();

        PageResponseDto<?> response = TestUtil.asPage(result.getResponse().getContentAsString(), new TypeReference<PageResponseDto<PropertyResponseDto>>() {
        });

        assertEquals(23, response.getTotalElements());
        assertEquals(5, response.getNumberOfElements());
        assertEquals(5, response.getTotalPages());


    }

    @Test
    public void getAllPropertiesSearch_SearchInQueryString_responseWithStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/properties/search?search=test"))
                .andExpect(status().isOk())
                .andReturn();

        PageResponseDto<?> response = TestUtil.asPage(result.getResponse().getContentAsString(), new TypeReference<PageResponseDto<PropertyResponseDto>>() {
        });

        assertEquals(3, response.getTotalElements());
        assertEquals(3, response.getNumberOfElements());
        assertEquals(1, response.getTotalPages());


    }

    @Test
    public void getProperty_existId_responseProperty_statusOK() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/properties/{id}", "0c54590d-522e-452c-bff2-1e2fe6e869de"))
                .andExpect(status().isOk())
                .andReturn();

        PropertyResponseDto response = (PropertyResponseDto) TestUtil.asObject(result.getResponse().getContentAsString(), PropertyResponseDto.class);

        assertEquals("0c54590d-522e-452c-bff2-1e2fe6e869de", response.getId().toString());

    }

    @Test
    public void getProperty_NotExistId_responseErrorMessage_statusNotFound() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/properties/{id}", "0c54590d-522e-452c-bff2-1e2fe"))
                .andExpect(status().isNotFound())
                .andReturn();
        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);

        assertEquals("Property not found", response.getMessage());
        assertEquals("404 NOT_FOUND", response.getStatus().toString());

    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void createProperty_withAccessToken_statusIsCreated() throws Exception {
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                .name("test-property")
                .location("test-location")
                .price(2000.0)
                .numOfBedrooms(2)
                .numOfSleepPlace(4)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();


        PropertyResponseDto response = (PropertyResponseDto) TestUtil.asObject(result.getResponse().getContentAsString(), PropertyResponseDto.class);
        assertEquals(requestDto.getName(), response.getName());


    }

    @Test
    public void createProperty_withoutAccessToken_statusIsUnauthorized() throws Exception {
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                .name("test-property")
                .location("test-location")
                .price(2000.0)
                .numOfBedrooms(2)
                .numOfSleepPlace(4)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/properties")
                        .header("Authorization", "Bearer notValid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isUnauthorized())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Not authorized!", response.getMessage());


    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void createProperty_withAccessToken_invalidRequestBody_statusBadRequest() throws Exception {
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                .location("test-location")
                .price(2000.0)
                .numOfBedrooms(2)
                .numOfSleepPlace(4)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Validation error", response.getMessage());

    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void deleteProperty_withAccessToken_existId_statusNoContent() throws Exception {

        mockMvc.perform(delete("/api/v1/properties/{id}", "8761b703-27ad-4953-a2af-3eb4bd94fab5"))
                .andExpect(status().isNoContent());


    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void deleteProperty_withAccessToken_NotExistId_statusNotFound() throws Exception {

        mockMvc.perform(delete("/api/v1/properties/{id}", "0c54590d-522e-452c-bff2-1e2fe"))
                .andExpect(status().isNotFound());


    }


    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void updateProperty_withAccessToken_existId_statusOk() throws Exception {
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                .name("updated-property")
                .location("location")
                .price(2000.0)
                .numOfBedrooms(2)
                .numOfSleepPlace(4)
                .build();

        MvcResult result = mockMvc.perform(put("/api/v1/properties/{id}", "2f80213e-1387-46fb-a938-9527bb80d947")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        PropertyResponseDto response = (PropertyResponseDto) TestUtil.asObject(result.getResponse().getContentAsString(), PropertyResponseDto.class);
        assertEquals("updated-property", response.getName());

    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void updateProperty_withAccessToken_NotExistId_statusNotFound() throws Exception {
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                .name("updated-property")
                .location("test-location")
                .price(2000.0)
                .numOfBedrooms(2)
                .numOfSleepPlace(4)
                .build();

        MvcResult result = mockMvc.perform(put("/api/v1/properties/{id}", "8761b703-27ad-4953-a2af-3eb4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isNotFound())
                .andReturn();


        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);

        assertEquals("Property not found", response.getMessage());
        assertEquals("404 NOT_FOUND", response.getStatus().toString());


    }

    @Test
    @WithMockUser(username = "mark", authorities = {"ROLE_CLIENT"})
    public void updateProperty_withAccessToken_invalidRequestBody_statusNotFound() throws Exception {
        PropertyRequestDto requestDto = PropertyRequestDto.builder()
                .location("test-location")
                .price(2000.0)
                .numOfBedrooms(2)
                .numOfSleepPlace(4)
                .build();

        MvcResult result = mockMvc.perform(put("/api/v1/properties/{id}", "8761b703-27ad-4953-a2af-3eb4bd94fab5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Validation error", response.getMessage());

    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_ADMIN"})
    public void getAllSubmission_withRoleAdmin_statusOK() throws Exception {

        mockMvc.perform(get("/api/v1/properties/submissions"))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_CLIENT"})
    public void getAllSubmission_withRoleClient_statusForbidden() throws Exception {

        mockMvc.perform(get("/api/v1/properties/submissions"))
                .andExpect(status().isForbidden())
                .andReturn();
    }


}
