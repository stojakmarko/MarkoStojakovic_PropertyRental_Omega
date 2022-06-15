package com.example.propertyrental.integration;

import com.example.propertyrental.dto.AuthenticationRequestDto;
import com.example.propertyrental.dto.UserDto;
import com.example.propertyrental.dto.UserRegistrationRequestDto;
import com.example.propertyrental.exception.ApiError;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

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
    public void authentication_withValidCredentials_responseAccessToken_statusIsOk() throws Exception {
        String username = "mark";
        String password = "mark123";
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto(username, password);


        MvcResult result = mockMvc.perform(post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void authentication_withInvalidCredentials_statusIsUnauthorized() throws Exception {
        String username = "test";
        String password = "test";
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto(username, password);


        MvcResult result = mockMvc.perform(post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isUnauthorized())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Bad credentials!", response.getMessage());

    }

    @Test
    public void registrationUser_withValidRequestBody_statusIsCreated() throws Exception {
        UserRegistrationRequestDto requestDto = UserRegistrationRequestDto.builder()
                .username("test-user")
                .email("test@gmail.com")
                .firstname("test-user")
                .lastname("test-user")
                .password("test-pass")
                .verifyPassword("test-pass")
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        UserDto response = (UserDto) TestUtil.asObject(result.getResponse().getContentAsString(), UserDto.class);
        assertEquals(requestDto.getEmail(), response.getEmail());
        assertEquals(requestDto.getUsername(), response.getUsername());
        assertEquals(requestDto.getFirstname(), response.getFirstname());
        assertEquals("ROLE_CLIENT", response.getRole());

    }

    @Test
    public void registrationUser_withInvalid_FirstName_statusIsBadRequest() throws Exception {
        UserRegistrationRequestDto requestDto = UserRegistrationRequestDto.builder()
                .username("test")
                .email("test1@gmail.com")
                .firstname("-")
                .lastname("test-user")
                .password("test-pass")
                .verifyPassword("test-pass")
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("Validation error", response.getMessage());
        assertEquals("Firstname should have minimum 3 and maximum 50 characters ", response.getSubErrors().get(0).getMessage());


    }

    @Test
    public void registrationUser_withInvalid_verifyPassword_statusIsBadRequest() throws Exception {
        UserRegistrationRequestDto requestDto = UserRegistrationRequestDto.builder()
                .username("test-user")
                .email("test@gmail.com")
                .firstname("test-user")
                .lastname("test-user")
                .password("test-pass")
                .verifyPassword("invalidPassword")
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiError response = (ApiError) TestUtil.asObject(result.getResponse().getContentAsString(), ApiError.class);

        assertEquals("Validation error", response.getMessage());
        assertEquals("Passwords do not match!", response.getSubErrors().get(0).getMessage());


    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_CLIENT"})
    public void getAllUsers_withRoleClient_statusForbidden() throws Exception {

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_ADMIN"})
    public void getAllUsers_withRoleAdmin_statusIsOk() throws Exception {

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andReturn();
    }


}
