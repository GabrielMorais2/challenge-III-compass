package com.moraes.gabriel.msusers.controller;

import Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.gabriel.msusers.model.payload.UserAuthenticateRequest;
import com.moraes.gabriel.msusers.model.payload.UserRequest;
import com.moraes.gabriel.msusers.model.payload.UserResponse;
import com.moraes.gabriel.msusers.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    private static final String USER_REQUEST = "Payload/USER_REQUEST.json";
    private static final String USER_RESPONSE = "Payload/USER_RESPONSE.json";
    private static final String USER_AUTHENTICATION_REQUEST = "Payload/USER_AUTHENTICATION_REQUEST.json";


    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }
    @Test
    @WithMockUser(value = "Gabriel")
    public void testRegisterUser() throws Exception {

        UserRequest userRequest = JsonUtils.getObjectFromFile(USER_REQUEST, UserRequest.class);
        UserResponse userResponse = JsonUtils.getObjectFromFile(USER_RESPONSE, UserResponse.class);

        when(userService.registerUser(userRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/v1/users/register")
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userResponse.getName()));
    }

    @Test
    @WithMockUser(value = "Gabriel")
    public void testAuthenticateUser() throws Exception {
        UserAuthenticateRequest userAuthenticateRequest = JsonUtils.getObjectFromFile(USER_AUTHENTICATION_REQUEST, UserAuthenticateRequest.class);

        when(userService.authenticate(userAuthenticateRequest)).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userAuthenticateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully authenticated user"));
    }
}